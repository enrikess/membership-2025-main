package com.promotick.lafabril.web.service;

import com.promotick.lafabril.model.encuesta.Campania;
import com.promotick.lafabril.model.encuesta.ParticipanteData;
import com.promotick.lafabril.model.encuesta.ParticipanteEncuesta;
import com.promotick.lafabril.model.encuesta.Pregunta;

import java.util.List;
import java.util.Optional;

public interface EncuestaEmpleadosWebService {
    List<ParticipanteData> participanteDataListar();

    Optional<Campania> campaniaObtener(Integer idParticipante);

    List<Pregunta> preguntasByEncuesta(Integer idEncuesta);

    void participanteEncuestaGuardar(ParticipanteEncuesta participanteEncuesta);
}
