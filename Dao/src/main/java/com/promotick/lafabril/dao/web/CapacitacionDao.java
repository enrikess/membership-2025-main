package com.promotick.lafabril.dao.web;

import com.promotick.lafabril.model.util.FiltroCapacitacion;
import com.promotick.lafabril.model.web.*;

import java.util.List;

public interface CapacitacionDao {
    List<Capacitacion> capacitacionesListar(Integer idParticipante);

    Capacitacion capacitacionObtener(Integer idParticipante, Integer idCapacitacion);

    List<CapacitacionRecurso> capacitacionRecursosListar(Integer idCapacitacion);

    List<CapacitacionPregunta> capacitacionPreguntasListar(Integer idCapacitacion);

    List<CapacitacionRespuesta> capacitacionRespuestasListar(Integer idCapacitacion);

    Integer capacitacionParticipanteMantenimiento(CapacitacionParticipante capacitacionParticipante);

    Integer capacitacionParticipanteDetalleRegistrar(CapacitacionParticipanteDetalle capacitacionParticipanteDetalle);

    CapacitacionParticipante capacitacionParticipanteObtener(Integer idParticipante, Integer idCapacitacion);

    List<CapacitacionParticipanteDetalle> capacitacionParticipanteDetalleListar(Integer idParticipante, Integer idCapacitacion);

    Integer guardarCapacitacionParticipantePregunta(CapacitacionParticipantePregunta capacitacionParticipantePregunta);

    List<Capacitacion> capacitacionesFiltroListar(FiltroCapacitacion filtroCapacitacion);

    Integer capacitacionesFiltroContar(FiltroCapacitacion filtroCapacitacion);

    Capacitacion capacitacionAdminObtener(Integer idCapacitacion);

    Integer capacitacionAdminEstadoCambiar(Capacitacion capacitacion);

    Integer capacitacionAdminMantenimiento(Capacitacion capacitacion);

    CapacitacionRecurso capacitacionRecursosAdminObtener(Integer idCapacitacionRecurso);

    void capacitacionRecursosAdminOrdenar(String lista);

    Integer capacitacionRecursosAdminEliminar(Integer idCapacitacionRecurso, Integer idUsuario);

    Integer capacitacionRecursosAdminActivar(Integer idCapacitacionRecurso, Integer idUsuario);

    List<CapacitacionRecurso> capacitacionRecursosAdminListarTodos(Integer idCapacitacion);

    Integer capacitacionRecursoAdminMantenimiento(CapacitacionRecurso capacitacionRecurso);

    List<CapacitacionPregunta> capacitacionPreguntasAdminListar(Integer idCapacitacion);

    List<CapacitacionRespuesta> capacitacionRespuestasAdminListar(Integer idCapacitacion);

    Integer capacitacionAdminPreguntasEstadoCambiar(CapacitacionPregunta capacitacionPregunta);

    void capacitacionPreguntasAdminOrdenar(String lista);

    Integer capacitacionAdminPreguntaActualizar(CapacitacionPregunta capacitacionPregunta);

    CapacitacionPregunta capacitacionPreguntasAdminObtener(Integer idCapacitacionPregunta);

    List<CapacitacionRespuesta> capacitacionRespuestasAdminListarByPregunta(Integer idCapacitacionPregunta);

    Integer capacitacionRespuestaAdminMantenimiento(CapacitacionRespuesta capacitacionRespuesta);

    void capacitacionRespuestasAdminOrdenar(String lista);

    Integer capacitacionPreguntaAdminNuevo(CapacitacionPregunta capacitacionPregunta);

    Integer registrarPuntosCapacitacionParticipante(CapacitacionParticipante capacitacionParticipante);

}
