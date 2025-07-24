package com.promotick.lafabril.dao.web;


import com.promotick.lafabril.model.web.PaisMundial;
import com.promotick.lafabril.model.web.PollaParticipante;

import java.util.List;

public interface PollaDao {
	List<PaisMundial> listarPaisMundial();
	Integer registrarPollaParticipante(PollaParticipante pollaParticipante);
	List<PollaParticipante> obtenerPollaParticipanteResumen(Integer idParticipante);
}


