package com.promotick.lafabril.dao.web;


import com.promotick.lafabril.model.web.AlternativaRespuesta;
import com.promotick.lafabril.model.web.Trivia;
import com.promotick.lafabril.model.web.TriviaResumen;

import java.util.List;

public interface TriviaDao {
	List<Trivia> obtenerTrivia(Integer idParticipante);
	List<AlternativaRespuesta> obtenerTriviaRespuesta(Integer idParticipante, Integer idTriviaGrupo);
	TriviaResumen obtenerTriviaResumen(Integer idParticipante, Integer idTriviaGrupo);
	Integer registrarTriviaRespuesta(Integer idParticipante, Integer idTriviaMundial, Integer idRespuesta, String descripcionRespuesta, Integer acertoRespuesta, String usuarioCreacion, Integer idTriviaGrupoParticipante);
	Integer registrarTriviaGrupo(Integer idParticipante, Integer idTriviaGrupo, String estadoTriviaGrupoParticipante, Integer cantidadRespuestasCorrectas, Integer cantidadPreguntas, String usuarioModificacion);
}


