package com.promotick.lafabril.admin.service;

import com.promotick.lafabril.model.util.FiltroCapacitacion;
import com.promotick.lafabril.model.web.Capacitacion;
import com.promotick.lafabril.model.web.CapacitacionPregunta;
import com.promotick.lafabril.model.web.CapacitacionRecurso;
import com.promotick.lafabril.model.web.CapacitacionRespuesta;

import java.util.List;

public interface CapacitacionAdminService {
    List<Capacitacion> capacitacionesFiltroListar(FiltroCapacitacion filtroCapacitacion);

    Integer capacitacionesFiltroContar(FiltroCapacitacion filtroCapacitacion);

    Capacitacion capacitacionAdminObtener(Integer idCapacitacion);

    Integer capacitacionAdminEstadoCambiar(Capacitacion capacitacion);

    Integer capacitacionAdminMantenimiento(Capacitacion capacitacion);

    List<CapacitacionRecurso> capacitacionRecursosListar(Integer idCapacitacion);

    List<CapacitacionPregunta> capacitacionPreguntasListar(Integer idCapacitacion);

    CapacitacionRecurso capacitacionRecursosAdminObtener(Integer idCapacitacionRecurso);

    void capacitacionRecursosAdminOrdenar(String lista);

    Integer capacitacionRecursosAdminEliminar(Integer idCapacitacionRecurso, Integer idUsuario);

    Integer capacitacionRecursosAdminActivar(Integer idCapacitacionRecurso, Integer idUsuario);

    List<CapacitacionRecurso> capacitacionRecursosAdminListarTodos(Integer idCapacitacion);

    Integer capacitacionRecursoAdminMantenimiento(CapacitacionRecurso capacitacionRecurso);

    List<CapacitacionPregunta> capacitacionPreguntasAdminListar(Integer idCapacitacion);

    Integer capacitacionAdminPreguntasEstadoCambiar(CapacitacionPregunta capacitacionPregunta);

    void capacitacionPreguntasAdminOrdenar(String lista);

    Integer capacitacionAdminPreguntaActualizar(CapacitacionPregunta capacitacionPregunta);

    CapacitacionPregunta capacitacionPreguntasAdminObtener(Integer idCapacitacionPregunta);

    Integer capacitacionRespuestaAdminMantenimiento(CapacitacionRespuesta capacitacionRespuesta);

    void capacitacionRespuestasAdminOrdenar(String lista);

    void capacitacionPreguntaAdminNuevo(CapacitacionPregunta capacitacionPregunta) throws Exception;

}
