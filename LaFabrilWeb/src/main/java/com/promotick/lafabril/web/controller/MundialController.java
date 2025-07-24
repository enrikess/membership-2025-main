package com.promotick.lafabril.web.controller;

import com.promotick.lafabril.common.ConstantesCommon;
import com.promotick.lafabril.common.ConstantesSesion;
import com.promotick.lafabril.common.UtilCommon;
import com.promotick.lafabril.model.util.Auditoria;
import com.promotick.lafabril.model.util.PromotickResult;
import com.promotick.lafabril.model.util.Util;
import com.promotick.lafabril.model.util.UtilEnum;
import com.promotick.lafabril.model.web.*;
import com.promotick.lafabril.web.service.*;
import com.promotick.lafabril.web.util.BaseController;
import com.promotick.lafabril.web.util.ConstantesWebView;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Controller
@RequestMapping("mundial")
@RequiredArgsConstructor
public class MundialController extends BaseController {

    private final ParticipanteWebService participanteWebService;
    private final UbigeoWebService ubigeoWebService;
    private final ConfiguracionWebService configuracionWebService;
    private final MundialService mundialService;
    private final PartidoService partidoService;
    private final PollaService pollaService;
    private final TriviaService triviaService;

    private static final Logger LOGGER = LoggerFactory.getLogger(MundialController.class);

    @GetMapping
    public String init(Model model, Participante participante) {

        if (participante == null) {
            Util.getSession().invalidate();
            return "redirect:/login";
        }

        if (!participante.getCatalogo().getTieneMundial()) {
            return "redirect:/";
        }

        ConfiguracionMundial configuracionMundial = mundialService.obtenerConfiguracionMundial();

        Boolean esCatalogo = true;

        if (configuracionMundial.getCatalogos() != null) {
            for (String catalogo : configuracionMundial.getCatalogos().split(",")) {
                if (Integer.parseInt(catalogo) == participante.getCatalogo().getIdCatalogo()) {
                    esCatalogo = false;
                }
            }
        }

        if (esCatalogo) {
            return "redirect:/";
        }

        model.addAttribute("resumenMundial", mundialService.obtenerResumenMundial(participante.getIdParticipante()));
        model.addAttribute("configuracionMundial", configuracionMundial);

        return ConstantesWebView.VIEW_MUNDIAL;
    }

    @ResponseBody
    @PostMapping("guardarDatos")
    public PromotickResult guardarActualizacion(@RequestBody Participante participante, PromotickResult promotickResult, Auditoria auditoria, HttpServletRequest httpServletRequest, Participante participanteSession) {
        try {
            participante
                    .setIdParticipante(participanteSession.getIdParticipante())
                    .setFechaNacimiento(UtilCommon.stringToDate(participante.getFechaNacimientoString()));

            participante.setAuditoria(auditoria);

            Integer resultado = participanteWebService.actualizarDatosParticipante(participante);

            UtilEnum.ACTUALIZACION_DATOS_RESULTS resultEnum = UtilEnum.ACTUALIZACION_DATOS_RESULTS.getMensageFromCode(resultado);
            if (!resultEnum.equals(UtilEnum.ACTUALIZACION_DATOS_RESULTS.OK)) {
                throw new Exception(resultEnum.getMensaje());
            }

            Participante participanteDB = participanteWebService.loginParticipante(participanteSession.getTipoDocumento().getIdTipoDocumento(), participanteSession.getNroDocumento());

            httpServletRequest.getSession()
                    .setAttribute(
                            ConstantesSesion.SESSION_PARTICIPANTE,
                            participanteDB
                    );

            httpServletRequest.getSession()
                    .setAttribute(
                            ConstantesSesion.SESSION_ACTUALIZACION_DATOS,
                            Boolean.FALSE
                    );
            promotickResult.setMessage("Se actualizo la informacion correctamente");

        } catch (Exception e) {
            promotickResult.setException(e);
        }
        return promotickResult;
    }

    @ResponseBody
    @GetMapping("obtenerPolla")
    public PromotickResult obtenerPolla(PromotickResult promotickResult, Participante participante){
        PollaResult pollaResult = new PollaResult();
        MundialResumen mundialResumen = mundialService.obtenerResumenMundial(participante.getIdParticipante());
        List<PollaParticipante> resumenPollaParticipante = pollaService.obtenerPollaParticipanteResumen(participante.getIdParticipante());

        if (!mundialResumen.getPollaVigencia() && resumenPollaParticipante.size() == 0) {
            pollaResult.setEstadoPollaParticipante("EXP");
            return promotickResult.setData(pollaResult);
        }

        List<PartidoMundial> listaPartidoMundial = partidoService.listarPartidoMundial();
        List<PaisMundial> listaPaisMundial = pollaService.listarPaisMundial();
        pollaResult.setEstadoPollaParticipante("PEN");
        pollaResult.setResult(new Object[]{ listaPaisMundial, listaPartidoMundial });
        if (resumenPollaParticipante.size() > 0) {
            pollaResult.setEstadoPollaParticipante("FIN");
            pollaResult.setResult(new Object[]{ resumenPollaParticipante, listaPaisMundial, listaPartidoMundial });
        }

        promotickResult.setData(pollaResult);

        return promotickResult;
    }

    @ResponseBody
    @GetMapping("obtenerTrivia")
    public PromotickResult obtenerTrivia(PromotickResult promotickResult, Participante participante) {
        TriviaResult triviaResult = new TriviaResult();
        List respuesta = new ArrayList();
        List<Trivia> listTrivia = triviaService.obtenerTrivia(participante.getIdParticipante());
        if (listTrivia != null && listTrivia.size() > 0 && ("INI".equals(listTrivia.get(0).getEstadoTriviaGrupoParticipante()) || "FIN".equals(listTrivia.get(0).getEstadoTriviaGrupoParticipante()))) {
            respuesta.add(triviaService.obtenerTriviaRespuesta(participante.getIdParticipante(), listTrivia.get(0).getIdTriviaGrupoMundial()));
            respuesta.add(triviaService.obtenerTriviaResumen(participante.getIdParticipante(), listTrivia.get(0).getIdTriviaGrupoMundial()));
            respuesta.add(listTrivia);
            triviaResult.setResult(respuesta);
            triviaResult.setEstadoTriviaParticipante(listTrivia.get(0).getEstadoTriviaGrupoParticipante());
            return promotickResult.setData(triviaResult);
        }

        respuesta.add(listTrivia);
        triviaResult.setEstadoTriviaParticipante("PEN");
        triviaResult.setResult(respuesta);

        promotickResult.setData(triviaResult);
        return promotickResult;

    }

    @ResponseBody
    @GetMapping("obtenerResumenTrivia")
    public PromotickResult obtenerResumenTrivia(PromotickResult promotickResult, Participante participante) {
        return promotickResult.setData(triviaService.obtenerTrivia(participante.getIdParticipante()));
    }

    //    @ResponseBody
    @GetMapping("obtenerRanking")
    public String obtenerRanking(PromotickResult promotickResult, Model model) {
        ConfiguracionMundial configuracionMundial = mundialService.obtenerConfiguracionMundial();
        model.addAttribute("configuracionMundial", configuracionMundial);
        model.addAttribute("objRankingMundial", mundialService.obtenerRankingMundial());

//        return promotickResult.setData(mundialService.obtenerRankingMundial());
        return ConstantesWebView.VIEW_FRAGMENTS_RANKING_MUNDIAL;
    }

    @PostMapping("matchActivo")
    public String matchActivo(@RequestBody List<PartidoMundial> partidosDiaActivo, PromotickResult promotickResult, Model model) {
        List<PartidoMundial> partidosResult = new ArrayList<>();
        for(int i = 0; i < partidosDiaActivo.size() && i < 2; i++){
            PartidoMundial partidoMundial = new PartidoMundial();
            partidoMundial.setOrden(partidosDiaActivo.get(i).getOrden());
            partidoMundial.setGrupoPartido(partidosDiaActivo.get(i).getGrupoPartido());
//            partidoMundial.setGrupoPartido(partidosDiaActivo.get(i).getGrupoPartido()?partidosDiaActivo.get(i).getGrupoPartido():partidosDiaActivo.get(i).getCodigoPartido());
//            partidoMundial.setDia(partidosDiaActivo.get(i).getOrden());
            partidoMundial.setNombrePaisMundial1(partidosDiaActivo.get(i).getNombrePaisMundial1());
            partidoMundial.setNombrePaisMundial2(partidosDiaActivo.get(i).getNombrePaisMundial2());
            partidoMundial.setImagenPaisMundial1(partidosDiaActivo.get(i).getImagenPaisMundial1());
            partidoMundial.setImagenPaisMundial2(partidosDiaActivo.get(i).getImagenPaisMundial2());

            partidosResult.add(partidoMundial);
        }
        model.addAttribute("objPartidoMundial", partidosResult);

        return ConstantesWebView.VIEW_FRAGMENTS_MATCH_INICIO_MUNDIAL;
    }

    @PostMapping("partidosPronosticos")
    public String partidosPronosticos(@RequestBody List<PartidoMundial> partidosPronosticos, Model model) {
        List<PartidoMundial> partidosResult = new ArrayList<>();
        for(int i = 0; i < partidosPronosticos.size() && i < 2; i++){
            PartidoMundial partidoMundial = partidosPronosticos.get(i);

            partidosResult.add(partidoMundial);
        }
        model.addAttribute("objPartidoMundial", partidosResult);

        return ConstantesWebView.VIEW_FRAGMENTS_MATCH_PRONOSTICO_MUNDIAL;
    }

    @ResponseBody
    @GetMapping("obtenerPronostico")
    public PromotickResult obtenerPronostico(PromotickResult promotickResult, Participante participante) {
        List<PartidoMundial> listPartidoMundial =  partidoService.listarPartidoPronosticoMundial();
        List<PronosticoParticipante> listPronosticoParticipante =  partidoService.obtenerPronosticoRespuesta(participante.getIdParticipante());
        PronosticoResult pronosticoResult = new PronosticoResult();
        pronosticoResult.setResult(new Object[]{ listPartidoMundial, listPronosticoParticipante });

        promotickResult.setData(pronosticoResult);
        return promotickResult;
    }

//    @ResponseBody
//    @RequestMapping(value = "registrarPronosticoParticipante", method = RequestMethod.POST)
//    public Integer registrarPronosticoParticipante(@RequestBody List<PronosticoParticipante> listPronosticoParticipante) {
//        for (PronosticoParticipante pronosticoParticipante: listPronosticoParticipante) {
//            pronosticoParticipante.setUsuarioCreacion(Util.obtenerParticipanteLogin().getEmailParticipante());
//            pronosticoParticipante.setIdParticipante(Util.obtenerParticipanteLogin().getIdParticipante());
//        }
//
//        return partidoService.registrarPronosticoParticipante(listPronosticoParticipante);
//    }
//
/*    @ResponseBody
    @RequestMapping(value = "registrarTriviaParticipante", method = RequestMethod.POST)
    public TriviaResumen registrarTriviaParticipante(@RequestBody TriviaParticipante triviaParticipante) {
        Participante participante = (Participante) Util.getSession().getAttribute(ConstantesSesion.SESSION_PARTICIPANTE);
        triviaParticipante.setUsuarioModificacion(participante.getEmailParticipante());
        triviaParticipante.setIdParticipante(participante.getIdParticipante());
        Integer resultado = triviaService.registrarTriviaParticipante(triviaParticipante);
        if (resultado > 0 && "FIN".equals(triviaParticipante.getEstadoTriviaGrupoParticipante())) {
            return triviaService.obtenerTriviaResumen(participante.getIdParticipante(), triviaParticipante.getIdTriviaGrupo());
        }
        return new TriviaResumen();
    }*/
//
//    @ResponseBody
//    @RequestMapping(value = "registrarPollaParticipante", method = RequestMethod.POST)
//    public Integer registrarPollaParticipante(@RequestBody List<PollaParticipante> listPollaParticipante) {
//        for (PollaParticipante pollaParticipante: listPollaParticipante) {
//            pollaParticipante.setUsuarioCreacion(Util.obtenerParticipanteLogin().getEmailParticipante());
//            pollaParticipante.setIdParticipante(Util.obtenerParticipanteLogin().getIdParticipante());
//        }
//
//        return pollaService.registrarPollaParticipante(listPollaParticipante);
//    }
}
