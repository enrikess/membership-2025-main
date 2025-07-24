package com.promotick.lafabril.web.controller;

import com.promotick.lafabril.model.util.PromotickResult;
import com.promotick.lafabril.model.web.Capacitacion;
import com.promotick.lafabril.model.web.CapacitacionParticipante;
import com.promotick.lafabril.model.web.CapacitacionParticipanteDetalle;
import com.promotick.lafabril.model.web.Participante;
import com.promotick.lafabril.web.service.CapacitacionWebService;
import com.promotick.lafabril.web.util.BaseController;
import com.promotick.lafabril.web.util.ConstantesWebView;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequestMapping("capacitacion")
@RequiredArgsConstructor
public class CapacitacionWebController extends BaseController {

    private final CapacitacionWebService capacitacionWebService;

    @GetMapping
    public String init(Model model, Participante participante) {
        model.addAttribute("capacitaciones", capacitacionWebService.capacitacionesListar(participante.getIdParticipante()));
        return ConstantesWebView.VIEW_CAPACITACIONES;
    }

    @GetMapping("{idCapacitacion}")
    public String detalle(@PathVariable Integer idCapacitacion, Model model, Participante participante) {
        Capacitacion capacitacion = capacitacionWebService.capacitacionObtener(participante.getIdParticipante(), idCapacitacion);
        if (capacitacion == null) {
            return "redirect:/capacitacion";
        }

        capacitacion.setRecursos(capacitacionWebService.capacitacionRecursosListar(capacitacion.getIdCapacitacion()));

        model.addAttribute("capacitacion", capacitacion);
        return ConstantesWebView.VIEW_CAPACITACIONES_DETALLE;
    }

    @GetMapping("{idCapacitacion}/cuestionario")
    public String cuestionario(@PathVariable Integer idCapacitacion, Model model, Participante participante) {
        Capacitacion capacitacion = capacitacionWebService.capacitacionObtener(participante.getIdParticipante(), idCapacitacion);
        if (capacitacion == null || capacitacion.getConteoPreguntas() == 0) {
            return "redirect:/capacitacion/" + idCapacitacion;
        }

        if (capacitacion.getResuelto()) {
            CapacitacionParticipante capacitacionParticipante = capacitacionWebService.capacitacionParticipanteObtener(participante.getIdParticipante(), idCapacitacion);
            Optional<Capacitacion> capacitacionRawOptional = capacitacionParticipante.getCapacitacionByRaw();
            if (!capacitacionRawOptional.isPresent()) {
                return "redirect:/capacitacion/" + idCapacitacion;
            }
            Capacitacion capacitacionRaw = capacitacionRawOptional.get();
            capacitacionRaw.getPreguntas().forEach(p -> {
                p.getRespuestas().forEach(r -> {
                    Optional<CapacitacionParticipanteDetalle> capacitacionParticipanteDetalleOptional = capacitacionParticipante.getDetalles().stream()
                            .filter(cp -> cp.getCapacitacionPregunta().getIdCapacitacionPregunta().intValue() == p.getIdCapacitacionPregunta()
                                    && cp.getCapacitacionRespuesta().getIdCapacitacionRespuesta().intValue() == r.getIdCapacitacionRespuesta())
                            .findAny();

                    capacitacionParticipanteDetalleOptional.ifPresent(r::setDetalleResuelto);
                });
            });
            model.addAttribute("capacitacionParticipante", capacitacionParticipante);
            model.addAttribute("capacitacion", capacitacionRaw);
            return ConstantesWebView.VIEW_CAPACITACIONES_CUESTIONARIO_VIEW;
        } else {
            capacitacion.setPreguntas(capacitacionWebService.capacitacionPreguntasListar(idCapacitacion));
            model.addAttribute("capacitacion", capacitacion);
            return ConstantesWebView.VIEW_CAPACITACIONES_CUESTIONARIO;
        }
    }

    @ResponseBody
    @PostMapping("guardarCuestionario")
    public PromotickResult guardarCuestionario(@RequestBody CapacitacionParticipante capacitacionParticipante, PromotickResult promotickResult, Participante participante) {
        try {
            Capacitacion capacitacion = capacitacionWebService.capacitacionObtener(participante.getIdParticipante(), capacitacionParticipante.getIdCapacitacion());
            if (capacitacion == null || capacitacion.getConteoPreguntas() == 0) {
                throw new Exception("La capacitacion se encuentra deshabilitada o no existe");
            }
            capacitacionParticipante.setCantidadPreguntas(capacitacion.getConteoPreguntas());

            capacitacion.setPreguntas(capacitacionWebService.capacitacionPreguntasListar(capacitacionParticipante.getIdCapacitacion()));
            promotickResult.setData(capacitacionWebService.guardarCapacitacionParticipante(capacitacion, participante, capacitacionParticipante));

        } catch (Exception e) {
            e.printStackTrace();
            promotickResult.setException(e);
        }
        return promotickResult;
    }
}
