package com.promotick.lafabril.admin.service.impl;

import com.promotick.lafabril.admin.service.CapacitacionAdminService;
import com.promotick.lafabril.dao.web.CapacitacionDao;
import com.promotick.lafabril.model.util.FiltroCapacitacion;
import com.promotick.lafabril.model.web.Capacitacion;
import com.promotick.lafabril.model.web.CapacitacionPregunta;
import com.promotick.lafabril.model.web.CapacitacionRecurso;
import com.promotick.lafabril.model.web.CapacitacionRespuesta;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CapacitacionAdminServiceImpl implements CapacitacionAdminService {

    private final CapacitacionDao capacitacionDao;

    @Override
    public List<Capacitacion> capacitacionesFiltroListar(FiltroCapacitacion filtroCapacitacion) {
        return capacitacionDao.capacitacionesFiltroListar(filtroCapacitacion);
    }

    @Override
    public Integer capacitacionesFiltroContar(FiltroCapacitacion filtroCapacitacion) {
        return capacitacionDao.capacitacionesFiltroContar(filtroCapacitacion);
    }

    @Override
    public Capacitacion capacitacionAdminObtener(Integer idCapacitacion) {
        return capacitacionDao.capacitacionAdminObtener(idCapacitacion);
    }

    @Override
    @Transactional
    public Integer capacitacionAdminEstadoCambiar(Capacitacion capacitacion) {
        return capacitacionDao.capacitacionAdminEstadoCambiar(capacitacion);
    }

    @Override
    @Transactional
    public Integer capacitacionAdminMantenimiento(Capacitacion capacitacion) {
        return capacitacionDao.capacitacionAdminMantenimiento(capacitacion);
    }

    @Override
    public List<CapacitacionRecurso> capacitacionRecursosListar(Integer idCapacitacion) {
        return capacitacionDao.capacitacionRecursosListar(idCapacitacion);
    }

    @Override
    public List<CapacitacionPregunta> capacitacionPreguntasListar(Integer idCapacitacion) {
        List<CapacitacionPregunta> preguntas = capacitacionDao.capacitacionPreguntasListar(idCapacitacion);
        if (preguntas.isEmpty()) {
            return preguntas;
        } else {
            List<CapacitacionRespuesta> respuestas = capacitacionDao.capacitacionRespuestasListar(idCapacitacion);
            return preguntas.stream()
                    .peek(p -> p.setRespuestas(
                            respuestas.stream()
                                    .filter(r -> r.getIdCapacitacionPregunta().intValue() == p.getIdCapacitacionPregunta())
                                    .collect(Collectors.toList())
                    )).collect(Collectors.toList());
        }
    }

    @Override
    public CapacitacionRecurso capacitacionRecursosAdminObtener(Integer idCapacitacionRecurso) {
        return capacitacionDao.capacitacionRecursosAdminObtener(idCapacitacionRecurso);
    }

    @Override
    @Transactional
    public void capacitacionRecursosAdminOrdenar(String lista) {
        capacitacionDao.capacitacionRecursosAdminOrdenar(lista);
    }

    @Override
    @Transactional
    public Integer capacitacionRecursosAdminEliminar(Integer idCapacitacionRecurso, Integer idUsuario) {
        return capacitacionDao.capacitacionRecursosAdminEliminar(idCapacitacionRecurso, idUsuario);
    }

    @Override
    @Transactional
    public Integer capacitacionRecursosAdminActivar(Integer idCapacitacionRecurso, Integer idUsuario) {
        return capacitacionDao.capacitacionRecursosAdminActivar(idCapacitacionRecurso, idUsuario);
    }

    @Override
    public List<CapacitacionRecurso> capacitacionRecursosAdminListarTodos(Integer idCapacitacion) {
        return capacitacionDao.capacitacionRecursosAdminListarTodos(idCapacitacion);
    }

    @Override
    @Transactional
    public Integer capacitacionRecursoAdminMantenimiento(CapacitacionRecurso capacitacionRecurso) {
        return capacitacionDao.capacitacionRecursoAdminMantenimiento(capacitacionRecurso);
    }

    @Override
    public List<CapacitacionPregunta> capacitacionPreguntasAdminListar(Integer idCapacitacion) {
        List<CapacitacionPregunta> preguntas = capacitacionDao.capacitacionPreguntasAdminListar(idCapacitacion);
        if (preguntas.isEmpty()) {
            return preguntas;
        } else {
            List<CapacitacionRespuesta> respuestas = capacitacionDao.capacitacionRespuestasAdminListar(idCapacitacion);
            return preguntas.stream()
                    .peek(p -> p.setRespuestas(
                            respuestas.stream()
                                    .filter(r -> r.getIdCapacitacionPregunta().intValue() == p.getIdCapacitacionPregunta())
                                    .collect(Collectors.toList())
                    )).collect(Collectors.toList());
        }
    }

    @Override
    @Transactional
    public Integer capacitacionAdminPreguntasEstadoCambiar(CapacitacionPregunta capacitacionPregunta) {
        return capacitacionDao.capacitacionAdminPreguntasEstadoCambiar(capacitacionPregunta);
    }

    @Override
    @Transactional
    public void capacitacionPreguntasAdminOrdenar(String lista) {
        capacitacionDao.capacitacionPreguntasAdminOrdenar(lista);
    }

    @Override
    @Transactional
    public Integer capacitacionAdminPreguntaActualizar(CapacitacionPregunta capacitacionPregunta) {
        return capacitacionDao.capacitacionAdminPreguntaActualizar(capacitacionPregunta);
    }

    @Override
    public CapacitacionPregunta capacitacionPreguntasAdminObtener(Integer idCapacitacionPregunta) {
        CapacitacionPregunta capacitacionPregunta = capacitacionDao.capacitacionPreguntasAdminObtener(idCapacitacionPregunta);
        if (capacitacionPregunta != null) {
            capacitacionPregunta.setRespuestas(capacitacionDao.capacitacionRespuestasAdminListarByPregunta(idCapacitacionPregunta));
        }
        return capacitacionPregunta;
    }

    @Override
    @Transactional
    public Integer capacitacionRespuestaAdminMantenimiento(CapacitacionRespuesta capacitacionRespuesta) {
        return capacitacionDao.capacitacionRespuestaAdminMantenimiento(capacitacionRespuesta);
    }

    @Override
    @Transactional
    public void capacitacionRespuestasAdminOrdenar(String lista) {
        capacitacionDao.capacitacionRespuestasAdminOrdenar(lista);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_UNCOMMITTED, rollbackFor = Exception.class)
    public void capacitacionPreguntaAdminNuevo(CapacitacionPregunta capacitacionPregunta) throws Exception {
        Integer idCapacitacionPregunta = capacitacionDao.capacitacionPreguntaAdminNuevo(capacitacionPregunta);
        if (idCapacitacionPregunta == null || idCapacitacionPregunta <= 0) {
            throw new Exception("No se pudo guardar la informacion de la pregunta");
        }
        capacitacionPregunta.setIdCapacitacionPregunta(idCapacitacionPregunta);

        for (CapacitacionRespuesta capacitacionRespuesta : capacitacionPregunta.getRespuestas()) {
            capacitacionRespuesta.setIdCapacitacionPregunta(capacitacionPregunta.getIdCapacitacionPregunta());
            capacitacionRespuesta.setAuditoria(capacitacionPregunta.getAuditoria());

            Integer idCapacitacionRespuesta = capacitacionDao.capacitacionRespuestaAdminMantenimiento(capacitacionRespuesta);
            if (idCapacitacionRespuesta == null || idCapacitacionRespuesta <= 0) {
                throw new Exception("No se pudo guardar la informacion de una respuesta");
            }
        }
    }
}
