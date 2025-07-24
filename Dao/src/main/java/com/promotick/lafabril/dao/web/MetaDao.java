package com.promotick.lafabril.dao.web;

import com.promotick.lafabril.model.web.AccionSellout;
import com.promotick.lafabril.model.web.MetaAvanceTrimestral;

import java.util.List;

public interface MetaDao {

	List<MetaAvanceTrimestral> obtenerMetaAvanceParticipanteTrimestral(Integer idParticipante, Integer anio, Integer trimestre);
	List<AccionSellout> obtenerAccionesSelloutParticipante(Integer idParticipante, Integer anio, Integer trimestre);

}
