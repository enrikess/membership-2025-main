package com.promotick.lafabril.web.service.impl;


import com.promotick.lafabril.dao.web.PartidoDao;
import com.promotick.lafabril.model.web.PartidoMundial;
import com.promotick.lafabril.model.web.PronosticoParticipante;
import com.promotick.lafabril.web.service.PartidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class PartidoServiceImpl implements PartidoService {

	private PartidoDao partidoDao;

	@Autowired
	public PartidoServiceImpl(PartidoDao partidoDao) {
		this.partidoDao = partidoDao;
	}

	@Override
	public List<PartidoMundial> listarPartidoMundial() {
		return partidoDao.listarPartidoMundial();
	}

	@Override
	public List<PartidoMundial> listarPartidoPronosticoMundial() {
		return partidoDao.listarPartidoPronosticoMundial();
	}

	@Override
	@Transactional
	public Integer registrarPronosticoParticipante(List<PronosticoParticipante> listPronosticoParticipante) {

		for (PronosticoParticipante pronosticoParticipante : listPronosticoParticipante) {
			partidoDao.registrarPronosticoParticipante(pronosticoParticipante);
		}
		return 1;
	}

	@Override
	public List<PronosticoParticipante> obtenerPronosticoRespuesta(Integer idParticipante) {
		return partidoDao.obtenerPronosticoRespuesta(idParticipante);
	}
}
