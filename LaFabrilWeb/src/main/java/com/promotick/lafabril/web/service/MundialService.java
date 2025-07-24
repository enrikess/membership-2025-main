package com.promotick.lafabril.web.service;


import com.promotick.lafabril.model.web.ConfiguracionMundial;
import com.promotick.lafabril.model.web.MundialRanking;
import com.promotick.lafabril.model.web.MundialResumen;
import java.util.List;

public interface MundialService {

	MundialResumen obtenerResumenMundial(Integer idParticipante);
	List<MundialRanking> obtenerRankingMundial();
	Integer actualizarCategoriaMundial();
	ConfiguracionMundial obtenerConfiguracionMundial();
}
