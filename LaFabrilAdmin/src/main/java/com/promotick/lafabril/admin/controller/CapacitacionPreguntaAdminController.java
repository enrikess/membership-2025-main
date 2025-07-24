package com.promotick.lafabril.admin.controller;

import com.promotick.lafabril.admin.service.CapacitacionAdminService;
import com.promotick.lafabril.admin.util.ConstantesAdminView;
import com.promotick.lafabril.model.util.Auditoria;
import com.promotick.lafabril.model.util.Datatable;
import com.promotick.lafabril.model.util.FiltroCapacitacion;
import com.promotick.lafabril.model.util.PromotickResult;
import com.promotick.lafabril.model.web.Capacitacion;
import com.promotick.lafabril.model.web.CapacitacionPregunta;
import com.promotick.lafabril.model.web.CapacitacionRespuesta;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Controller
@RequestMapping("capacitaciones/preguntas")
@RequiredArgsConstructor
public class CapacitacionPreguntaAdminController {

    private final CapacitacionAdminService capacitacionAdminService;

    @GetMapping
    public String init(@RequestParam(value = "idCapacitacion", required = false) Integer idCapacitacion, Model model) {
        if (idCapacitacion != null) {
            Capacitacion capacitacion = capacitacionAdminService.capacitacionAdminObtener(idCapacitacion);
            if (capacitacion == null) {
                return "redirect:/capacitaciones/preguntas";
            }
            model.addAttribute("capacitacion", capacitacion);
        }
        model.addAttribute("capacitaciones", capacitacionAdminService.capacitacionesFiltroListar(FiltroCapacitacion.empty()));
        return ConstantesAdminView.VIEW_CAPACITACIONES_PREGUNTAS;
    }

    @ResponseBody
    @PostMapping("listar")
    public Datatable listarPreguntas(@RequestParam(required = false) Integer idCapacitacion) {
        Datatable datatable = new Datatable();
        if (idCapacitacion != null && idCapacitacion != 0) {
            List<CapacitacionPregunta> capacitacionPreguntaList = capacitacionAdminService.capacitacionPreguntasAdminListar(idCapacitacion);
            datatable.setRecordsTotal(capacitacionPreguntaList.size());
            datatable.setRecordsFiltered(capacitacionPreguntaList.size());
            datatable.setData(capacitacionPreguntaList);
        } else {
            datatable.setRecordsTotal(0);
            datatable.setRecordsFiltered(0);
            datatable.setData(new ArrayList<>());
        }
        return datatable;
    }

    @ResponseBody
    @PostMapping(value = "actualizar-estado")
    public PromotickResult actualizarEstado(@RequestBody CapacitacionPregunta capacitacionPregunta, PromotickResult promotickResult, Auditoria auditoria) {
        try {
            capacitacionPregunta.setEstadoCapacitacionPregunta(!capacitacionPregunta.getEstadoCapacitacionPregunta());
            capacitacionPregunta.setAuditoria(auditoria);
            Integer resultado = capacitacionAdminService.capacitacionAdminPreguntasEstadoCambiar(capacitacionPregunta);
            if (resultado == null || resultado <= 0) {
                throw new Exception("Ocurrio un error al cambiar el estado de la pregunta");
            }
            promotickResult.setMessage("Se cambio de estado correctamente");
        } catch (Exception e) {
            promotickResult.setException(e);
        }
        return promotickResult;
    }

    @ResponseBody
    @PostMapping("guardarOrden")
    public PromotickResult guardarOrden(@RequestBody Capacitacion capacitacion, PromotickResult promotickResult) {
        try {
            String lista = capacitacion.getPreguntas().stream()
                    .map(r -> r.getIdCapacitacionPregunta() + "," + r.getOrdenPregunta())
                    .collect(Collectors.joining(";"));
            capacitacionAdminService.capacitacionPreguntasAdminOrdenar(lista);
        } catch (Exception e) {
            promotickResult.setException(e);
        }
        return promotickResult;
    }

    @ResponseBody
    @PostMapping("guardarOrdenRespuestas")
    public PromotickResult guardarOrdenRespuestas(@RequestBody CapacitacionPregunta capacitacionPregunta, PromotickResult promotickResult) {
        try {
            String lista = capacitacionPregunta.getRespuestas().stream()
                    .map(r -> r.getIdCapacitacionRespuesta() + "," + r.getOrdenRespuesta())
                    .collect(Collectors.joining(";"));
            capacitacionAdminService.capacitacionRespuestasAdminOrdenar(lista);
        } catch (Exception e) {
            promotickResult.setException(e);
        }
        return promotickResult;
    }

    @PostMapping("viewCapacitacionPreguntaForm")
    public String viewCapacitacionPreguntaForm(@RequestBody CapacitacionPregunta capacitacionPregunta, Model model) {
        boolean esNuevo = capacitacionPregunta.getIdCapacitacionPregunta() == null;
        model.addAttribute("pregunta", capacitacionPregunta);
        model.addAttribute("esNuevo", esNuevo);
        return ConstantesAdminView.VIEW_FRAGMENTS_CAPACITACIONES_PREGUNTAS_FORM;
    }

    @GetMapping("viewCapacitacionRespuestaForm/{idTipoPregunta}/{esNuevoPregunta}")
    public String viewCapacitacionRespuestaForm(@PathVariable Integer idTipoPregunta, @PathVariable Boolean esNuevoPregunta, Model model) {
        model.addAttribute("idTipoPregunta", idTipoPregunta);
        model.addAttribute("respuesta", new CapacitacionRespuesta());
        model.addAttribute("esNuevoPregunta", esNuevoPregunta);
        model.addAttribute("esNuevoRespuesta", true);
        return ConstantesAdminView.VIEW_FRAGMENTS_CAPACITACIONES_RESPUESTA_FORM;
    }

    @ResponseBody
    @PostMapping("preguntaActualizar")
    public PromotickResult preguntaActualizar(@RequestBody CapacitacionPregunta capacitacionPregunta, Auditoria auditoria, PromotickResult promotickResult) {
        try {
            capacitacionPregunta.setAuditoria(auditoria);

            Integer actualizacion = capacitacionAdminService.capacitacionAdminPreguntaActualizar(capacitacionPregunta);
            if (actualizacion == null || actualizacion <= 0) {
                throw new Exception("Ocurrio un error al actualizar la pregunta");
            }

        } catch (Exception e) {
            promotickResult.setException(e);
        } finally {
            promotickResult.setData(capacitacionAdminService.capacitacionPreguntasAdminObtener(capacitacionPregunta.getIdCapacitacionPregunta()));
        }
        return promotickResult;
    }

    @ResponseBody
    @PostMapping("respuestaMantenimiento")
    public PromotickResult respuestaMantenimiento(@RequestBody CapacitacionRespuesta capacitacionRespuesta, Auditoria auditoria, PromotickResult promotickResult) {
        try {
            capacitacionRespuesta.setAuditoria(auditoria);

            Integer actualizacion = capacitacionAdminService.capacitacionRespuestaAdminMantenimiento(capacitacionRespuesta);
            if (actualizacion == null || actualizacion <= 0) {
                throw new Exception("Ocurrio un error al guardar la informacion de la respuesta");
            }
        } catch (Exception e) {
            promotickResult.setException(e);
        } finally {
            promotickResult.setData(capacitacionAdminService.capacitacionPreguntasAdminObtener(capacitacionRespuesta.getIdCapacitacionPregunta()));
        }
        return promotickResult;
    }

    @ResponseBody
    @PostMapping("nuevaPregunta")
    public PromotickResult nuevaPregunta(@RequestBody CapacitacionPregunta capacitacionPregunta, Auditoria auditoria, PromotickResult promotickResult) {
        try {
            capacitacionPregunta.setAuditoria(auditoria);
            capacitacionAdminService.capacitacionPreguntaAdminNuevo(capacitacionPregunta);
        } catch (Exception e) {
            promotickResult.setException(e);
        }
        return promotickResult;
    }
}
