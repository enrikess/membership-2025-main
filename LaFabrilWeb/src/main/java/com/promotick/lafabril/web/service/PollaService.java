package com.promotick.lafabril.web.service;

import com.promotick.lafabril.model.web.PaisMundial;
import com.promotick.lafabril.model.web.PollaParticipante;

import java.util.List;


public interface PollaService {

	List<PaisMundial> listarPaisMundial();
	Integer registrarPollaParticipante(List<PollaParticipante> listPollaParticipante);
	List<PollaParticipante> obtenerPollaParticipanteResumen(Integer idParticipante);
}
