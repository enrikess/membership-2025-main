package com.promotick.lafabril.web.controller;

import com.promotick.lafabril.common.ConstantesCommon;
import com.promotick.lafabril.common.ConstantesSesion;
import com.promotick.lafabril.common.UtilCommon;
import com.promotick.lafabril.model.util.Auditoria;
import com.promotick.lafabril.model.util.PromotickResult;
import com.promotick.lafabril.model.util.Util;
import com.promotick.lafabril.model.util.UtilEnum;
import com.promotick.lafabril.model.web.Participante;
import com.promotick.lafabril.web.service.ConfiguracionWebService;
import com.promotick.lafabril.web.service.ParticipanteWebService;
import com.promotick.lafabril.web.service.UbigeoWebService;
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
@RequestMapping("actualizar")
@RequiredArgsConstructor
public class ActualizarDatosController extends BaseController {

    private final ParticipanteWebService participanteWebService;
    private final UbigeoWebService ubigeoWebService;
    private final ConfiguracionWebService configuracionWebService;

    @GetMapping
    public String init(Model model, Participante participante) {

        if (participante == null) {
            Util.getSession().invalidate();
            return "redirect:/login";
        }

        model.addAttribute("tyc", configuracionWebService.obtenerConfiguracionWeb().getTycRegistro());
        model.addAttribute("regiones", configuracionWebService.obtenerRegiones());
        model.addAttribute("provincias", ubigeoWebService.listarProvincias(ConstantesCommon.COD_PAIS, ConstantesCommon.COD_DEP));

        return ConstantesWebView.VIEW_ACTUALIZAR_DATOS;
    }

    @ResponseBody
    @PostMapping("guardarDatos")
    public PromotickResult guardarActualizacion(@RequestBody Participante participante, PromotickResult promotickResult, Auditoria auditoria, HttpServletRequest httpServletRequest, Participante participanteSession) {
        try {
            participante
                    .setIdParticipante(participanteSession.getIdParticipante())
                    .setFechaNacimiento(UtilCommon.stringToDate(participante.getFechaNacimientoString()))
                    .setFechaAniversarioLocal(UtilCommon.stringToDate(participante.getFechaAniversarioLocalString()));

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


}
