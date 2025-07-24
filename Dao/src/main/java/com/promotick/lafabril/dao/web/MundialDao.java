package com.promotick.lafabril.dao.web;


import com.promotick.lafabril.model.web.ConfiguracionMundial;
import com.promotick.lafabril.model.web.MundialRanking;
import com.promotick.lafabril.model.web.MundialResumen;
import com.promotick.lafabril.model.web.PartidoMundial;

import java.util.List;

public interface MundialDao {
	MundialResumen obtenerResumenMundial(Integer idParticipante);
	List<MundialRanking> obtenerRankingMundial();
	Integer actualizarCategoriaMundial();
	ConfiguracionMundial obtenerConfiguracionMundial();

	List<PartidoMundial> listarFechasPartido();
}


