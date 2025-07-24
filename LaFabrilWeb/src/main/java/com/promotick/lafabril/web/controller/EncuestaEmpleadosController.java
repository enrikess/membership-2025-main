package com.promotick.lafabril.web.controller;

import com.promotick.lafabril.model.encuesta.ParticipanteEncuesta;
import com.promotick.lafabril.model.util.PromotickResult;
import com.promotick.lafabril.model.web.Participante;
import com.promotick.lafabril.web.service.EncuestaEmpleadosWebService;
import com.promotick.lafabril.web.util.ConstantesWebView;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("encuesta-empleados")
public class EncuestaEmpleadosController {

    private final EncuestaEmpleadosWebService encuestaEmpleadosWebService;

    @GetMapping
    public String init(Participante participante, Model model) {
        encuestaEmpleadosWebService.campaniaObtener(participante.getIdParticipante()).ifPresent(c -> model.addAttribute("campania", c));
        model.addAttribute("catalogo", participante.getCatalogo());
        return ConstantesWebView.VIEW_ENCUESTA_EMPLEADOS;
    }

    @GetMapping("viewEncuestaPreguntas/{idEncuesta}")
    public String viewEncuestaPreguntas(@PathVariable Integer idEncuesta, Model model) {
        model.addAttribute("participantes", encuestaEmpleadosWebService.participanteDataListar());
        model.addAttribute("preguntas", encuestaEmpleadosWebService.preguntasByEncuesta(idEncuesta));
        return ConstantesWebView.VIEW_FRAGMENTS_ENCUESTA_EMPLEADOS_PREGUNTAS;
    }

    @GetMapping("viewGracias")
    public String viewGracias() {
        return ConstantesWebView.VIEW_FRAGMENTS_ENCUESTA_EMPLEADOS_GRACIAS;
    }

    @ResponseBody
    @PostMapping("guardarEncuesta")
    public PromotickResult guardarEncuesta(@RequestBody ParticipanteEncuesta participanteEncuesta, PromotickResult promotickResult, Participante participante) {
        try {
            participanteEncuesta.setIdParticipante(participante.getIdParticipante());
            encuestaEmpleadosWebService.participanteEncuestaGuardar(participanteEncuesta);
        } catch (Exception e) {
            promotickResult.setException(e);
        }
        return promotickResult;
    }
}
