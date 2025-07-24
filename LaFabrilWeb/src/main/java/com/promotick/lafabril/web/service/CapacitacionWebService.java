package com.promotick.lafabril.web.service;

import com.promotick.lafabril.model.web.*;

import java.util.List;

public interface CapacitacionWebService {
    List<Capacitacion> capacitacionesListar(Integer idParticipante);

    Capacitacion capacitacionObtener(Integer idParticipante, Integer idCapacitacion);

    List<CapacitacionRecurso> capacitacionRecursosListar(Integer idCapacitacion);

    List<CapacitacionPregunta> capacitacionPreguntasListar(Integer idCapacitacion);

    CapacitacionParticipante guardarCapacitacionParticipante(Capacitacion capacitacion, Participante participante, CapacitacionParticipante capacitacionParticipante) throws Exception;

    CapacitacionParticipante capacitacionParticipanteObtener(Integer idParticipante, Integer idCapacitacion);
}
