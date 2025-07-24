package com.promotick.lafabril.web.controller;

import com.promotick.apiclient.model.AbstractResponse;
import com.promotick.apiclient.model.request.EmailRequest;
import com.promotick.apiclient.model.response.SengridEmail;
import com.promotick.apiclient.service.ApiEmailService;
import com.promotick.lafabril.common.ConstantesCommon;
import com.promotick.lafabril.model.configuracion.ConfiguracionWeb;
import com.promotick.lafabril.model.util.Auditoria;
import com.promotick.lafabril.model.util.Datatable;
import com.promotick.lafabril.model.util.PromotickResult;
import com.promotick.lafabril.model.web.Participante;
import com.promotick.lafabril.model.web.Recomendado;
import com.promotick.lafabril.web.service.RecomendadoWebService;
import com.promotick.lafabril.web.service.UbigeoWebService;
import com.promotick.lafabril.web.util.BaseController;
import com.promotick.lafabril.web.util.ConstantesWebView;
import com.promotick.configuration.services.PromotickResourceService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@Controller
@RequestMapping("recomendado")
public class RecomendadoController extends BaseController {

    private RecomendadoWebService recomendadoWebService;
    private PromotickResourceService promotickResourceService;
    private ApiEmailService apiEmailService;
    private UbigeoWebService ubigeoWebService;

    @Autowired
    public RecomendadoController(RecomendadoWebService recomendadoWebService, PromotickResourceService promotickResourceService, ApiEmailService apiEmailService, UbigeoWebService ubigeoWebService){
        this.recomendadoWebService = recomendadoWebService;
        this.promotickResourceService = promotickResourceService;
        this.apiEmailService = apiEmailService;
        this.ubigeoWebService = ubigeoWebService;
    }

    @GetMapping
    public String inicio(Participante participante) {
        if (!participante.getCatalogo().getTieneReferidos()) {
            return "redirect:/";
        }
        return ConstantesWebView.VIEW_MIS_RECOMENDADO;
    }

    @GetMapping("nuevo")
    public String nuevo(Model model) {
        model.addAttribute("provinciasRecomendado", ubigeoWebService.listarProvincias(ConstantesCommon.COD_PAIS, ConstantesCommon.COD_DEP));
        return ConstantesWebView.VIEW_RECOMENDADO;
    }


    @GetMapping("viewRecomendado")
    public String viewRecomendado(Model model, Participante participante){
//     model.addAttribute("todosRecomendados", recomendadoWebService.listarRecomendados(participante.getIdParticipante()));
     return ConstantesWebView.VIEW_FRAGMENTS_MIS_RECOMENDADOS_DETALLE;
    }

    @ResponseBody
    @GetMapping(value = "listarRecomendados") public Datatable ListarRecomendados (Participante participante){
        List<Recomendado> lista = recomendadoWebService.listarRecomendados(participante.getIdParticipante());
        Datatable datatable = new Datatable();
        datatable.setData(lista);
        datatable.setRecordsFiltered(lista.size());
        datatable.setRecordsTotal(lista.size());
        return  datatable;
    }

    @ResponseBody
    @PostMapping(value = "registrarRecomendado")
    public PromotickResult registrarRecomendado(@RequestBody Recomendado recomendado, Participante participanteSession, Auditoria auditoria, PromotickResult promotickResult, ConfiguracionWeb configuracionWeb) {
        try {
            recomendado.setAuditoria(auditoria);
            recomendado.setParticipante(participanteSession);
            Integer resultado = recomendadoWebService.registrarRecomendado(recomendado);
            if (resultado > 0) {
                enviarEmailRecomendado(recomendado, configuracionWeb);
                promotickResult.setMessage("Se registro el recomendado correctamente");
            }else {
                switch (resultado) {
                    case -1:
                        throw new Exception("El DNI ya se encuentra registrado por otro participante");
                    default:
                        throw new Exception("Ocurrio un inconveniente al procesr su peticion");
                }
            }
        } catch (Exception e) {
            promotickResult.setException(e);
        }
        return promotickResult;
    }

    private void enviarEmailRecomendado(Recomendado recomendado, ConfiguracionWeb configuracionWeb) throws Exception {
        String usuario = recomendado.getParticipante().getUsuarioParticipante();
        String referido = recomendado.getNroDocumentoRecomendado();
        String path = promotickResourceService.mail("recomendado.html");
        String header = promotickResourceService.web("img/bg/mail-recomendado/header.png");
        String footer = promotickResourceService.web("img/bg/mail-recomendado/footer.png");
        String logo   = promotickResourceService.web("img/bg/mail-recomendado/logo.png");

        Object[] data = {
                usuario,
                referido,
                header,
                footer,
                logo
        };

        String destino = configuracionWeb.getCorreoRecomendado();
        String alias = "Recomendado";

//        String[] correos = null;
//        if (configuracionWeb.getCorreoRecomendado() != null) {
//            correos = (configuracionWeb.getCorreoRecomendado()).split("\\|");
//            log.info("configuracionWeb.getCorreoRecomendado()" + configuracionWeb.getCorreoRecomendado());
//        }

        this.enviarCorreo(path, data, destino, configuracionWeb.getEmailInfo(), alias, null);
    }

    private void enviarCorreo(String pathAbsoluteHtml, Object[] data, String destino, String fromName, String aliasContacto, String[] correos) throws Exception {
        EmailRequest emailRequest = new EmailRequest();
        emailRequest.setTo(destino);
        emailRequest.setFrom(fromName);
        emailRequest.setFromName(aliasContacto);
        emailRequest.setSubject("Nuevo Recomendado");
        emailRequest.setPathTemplate(pathAbsoluteHtml);
        emailRequest.setParameters(data);
        emailRequest.setFile(null);

        if (correos != null) {
            for (String bcc : correos) {
                emailRequest.addBcc(bcc);
            }
        }

        AbstractResponse<SengridEmail> response = apiEmailService.sendEmail(emailRequest);
        log.info("Response EMAIL TO: " + emailRequest.getTo());
        log.info("Response EMAIL Status: " + response.isStatus());
        log.info("Response EMAILS Message: " + response.getMessage());

        if (!response.isStatus()) {
            throw new Exception(response.getMessage());
        }

    }
}
