package com.promotick.lafabril.web.service.impl;

import com.promotick.lafabril.dao.web.TriviaDao;
import com.promotick.lafabril.model.web.AlternativaRespuesta;
import com.promotick.lafabril.model.web.Trivia;
import com.promotick.lafabril.model.web.TriviaParticipante;
import com.promotick.lafabril.model.web.TriviaResumen;
import com.promotick.lafabril.web.service.TriviaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
public class TriviaServiceImpl implements TriviaService {

	private TriviaDao triviaDao;

	@Autowired
	public TriviaServiceImpl(TriviaDao triviaDao) {
		this.triviaDao = triviaDao;
	}

	@Override
	public List<Trivia> obtenerTrivia(Integer idParticipante) {
		return triviaDao.obtenerTrivia(idParticipante);
	}

	@Override
	public List<AlternativaRespuesta> obtenerTriviaRespuesta(Integer idParticipante, Integer idTriviaGrupo) {
		return triviaDao.obtenerTriviaRespuesta(idParticipante, idTriviaGrupo);
	}

	@Override
	public TriviaResumen obtenerTriviaResumen(Integer idParticipante, Integer idTriviaGrupo) {
		return triviaDao.obtenerTriviaResumen(idParticipante, idTriviaGrupo);
	}

	@Override
	@Transactional
	public Integer registrarTriviaParticipante(TriviaParticipante triviaParticipante) {
		Integer respuesta = triviaDao.registrarTriviaGrupo(triviaParticipante.getIdParticipante(), triviaParticipante.getIdTriviaGrupo(), triviaParticipante.getEstadoTriviaGrupoParticipante(), triviaParticipante.getCantidadRespuestasCorrectas(),
				triviaParticipante.getCantidadPreguntas(), triviaParticipante.getUsuarioModificacion());
		for (AlternativaRespuesta alternativaRespuesta : triviaParticipante.getListAlternativaRespuesta()) {
			triviaDao.registrarTriviaRespuesta(triviaParticipante.getIdParticipante(), alternativaRespuesta.getIdTriviaMundial(), alternativaRespuesta.getIdRespuesta(), alternativaRespuesta.getDescripcionRespuesta(), alternativaRespuesta.getAcertoRespuesta(), triviaParticipante.getUsuarioModificacion(), respuesta);
		}
		return respuesta;
	}


}
