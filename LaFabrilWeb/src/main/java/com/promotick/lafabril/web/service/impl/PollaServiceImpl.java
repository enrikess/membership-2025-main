package com.promotick.lafabril.web.service.impl;


import com.promotick.lafabril.dao.web.PollaDao;
import com.promotick.lafabril.model.web.PaisMundial;
import com.promotick.lafabril.model.web.PollaParticipante;
import com.promotick.lafabril.web.service.PollaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class PollaServiceImpl implements PollaService {

	private PollaDao pollaDao;

	@Autowired
	public PollaServiceImpl(PollaDao pollaDao) {
		this.pollaDao = pollaDao;
	}

	@Override
	public List<PaisMundial> listarPaisMundial() {
		return pollaDao.listarPaisMundial();
	}

	@Override
	@Transactional
	public Integer registrarPollaParticipante(List<PollaParticipante> listPollaParticipante) {
		for (PollaParticipante pollaParticipante : listPollaParticipante) {
			pollaDao.registrarPollaParticipante(pollaParticipante);
		}
		return 1;
	}

	@Override
	public List<PollaParticipante> obtenerPollaParticipanteResumen(Integer idParticipante) {
		return pollaDao.obtenerPollaParticipanteResumen(idParticipante);
	}
}
