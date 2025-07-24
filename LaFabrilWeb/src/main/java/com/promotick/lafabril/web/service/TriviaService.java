package com.promotick.lafabril.web.service;

import com.promotick.lafabril.model.web.AlternativaRespuesta;
import com.promotick.lafabril.model.web.Trivia;
import com.promotick.lafabril.model.web.TriviaParticipante;
import com.promotick.lafabril.model.web.TriviaResumen;

import java.util.List;


public interface TriviaService {

	List<Trivia> obtenerTrivia(Integer idParticipante);
	List<AlternativaRespuesta> obtenerTriviaRespuesta(Integer idParticipante, Integer idTriviaGrupo);
	TriviaResumen obtenerTriviaResumen(Integer idParticipante, Integer idTriviaGrupo);
	Integer registrarTriviaParticipante(TriviaParticipante triviaParticipante);
}
