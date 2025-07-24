package com.promotick.lafabril.web.controller;

import com.promotick.lafabril.common.ConstantesSesion;
import com.promotick.lafabril.model.configuracion.ConfiguracionWeb;
import com.promotick.lafabril.model.util.PromotickResult;
import com.promotick.lafabril.model.util.Util;
import com.promotick.lafabril.model.web.Encuesta;
import com.promotick.lafabril.model.web.Participante;
import com.promotick.lafabril.web.service.EncuestaWebService;
import com.promotick.lafabril.web.util.BaseController;
import com.promotick.lafabril.web.util.ConstantesWebView;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@Controller
@RequestMapping("encuesta")
@RequiredArgsConstructor
public class EncuestaController extends BaseController {

    private final EncuestaWebService encuestaWebService;

    @GetMapping
    public String init(Model model, Participante participante) {
        if (participante == null) {
            Util.getSession().invalidate();
            return "redirect:/login";
        }

        ConfiguracionWeb configuracionWeb = (ConfiguracionWeb) Util.getSession().getAttribute(ConstantesSesion.SESSION_CONFIGURACION_WEB);

        Integer idPedido = (Integer) Util.getSession().getAttribute(ConstantesSesion.SESSION_ENCUESTA_PENDIENTE);
        Integer encuesta = configuracionWeb.getEncuestaEstado();
        if (idPedido != null && encuesta != null && encuesta > 0) {
            model.addAttribute("idPedido", idPedido);
            return ConstantesWebView.VIEW_ENCUESTA;
        } else {
            return "redirect:/";
        }

    }

    @ResponseBody
    @PostMapping("guardarDatos")
    public PromotickResult guardarActualizacion(@RequestBody Encuesta encuesta, PromotickResult promotickResult, HttpServletRequest httpServletRequest, Participante participanteSession) {
        try {

            encuesta.setParticipante(new Participante().setIdParticipante(participanteSession.getIdParticipante()));
            encuestaWebService.registrarEncuestaTransactional(encuesta);

            httpServletRequest.getSession()
                    .setAttribute(
                            ConstantesSesion.SESSION_ENCUESTA_PENDIENTE,
                            null
                    );
            promotickResult.setMessage("Se resolvio la encuesta");

        } catch (Exception e) {
            promotickResult.setException(e);
        }
        return promotickResult;
    }

    @ResponseBody
    @PostMapping("saltarEncuesta")
    public PromotickResult saltarEncuesta(PromotickResult promotickResult, HttpServletRequest httpServletRequest) {
        try {

            Integer idPedidoEncuesta = (Integer) httpServletRequest.getSession().getAttribute(ConstantesSesion.SESSION_ENCUESTA_PENDIENTE);
            encuestaWebService.omitirEncuesta(idPedidoEncuesta);

            httpServletRequest.getSession()
                    .setAttribute(
                            ConstantesSesion.SESSION_ENCUESTA_PENDIENTE,
                            null
                    );
            promotickResult.setMessage("Se salto la encuesta");

        } catch (Exception e) {
            promotickResult.setException(e);
        }
        return promotickResult;
    }

}
