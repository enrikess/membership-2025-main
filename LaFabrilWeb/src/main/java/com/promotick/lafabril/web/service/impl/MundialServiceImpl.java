package com.promotick.lafabril.web.service.impl;

import com.promotick.lafabril.dao.web.MundialDao;
import com.promotick.lafabril.model.web.ConfiguracionMundial;
import com.promotick.lafabril.model.web.MundialRanking;
import com.promotick.lafabril.model.web.MundialResumen;
import com.promotick.lafabril.web.service.MundialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
public class MundialServiceImpl implements MundialService {

	private MundialDao mundialDao;

	@Autowired
	public MundialServiceImpl(MundialDao mundialDao) {
		this.mundialDao = mundialDao;
	}

	@Override
	public MundialResumen obtenerResumenMundial(Integer idParticipante) {
		return mundialDao.obtenerResumenMundial(idParticipante);
	}

	@Override
	public List<MundialRanking> obtenerRankingMundial() {
		return mundialDao.obtenerRankingMundial();
	}

	@Override
	@Transactional
	public Integer actualizarCategoriaMundial() {
		return mundialDao.actualizarCategoriaMundial();
	}

	@Override
	public ConfiguracionMundial obtenerConfiguracionMundial() {
		return mundialDao.obtenerConfiguracionMundial();
	}
}
