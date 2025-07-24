package com.promotick.lafabril.dao.web;


import com.promotick.lafabril.model.web.PartidoMundial;
import com.promotick.lafabril.model.web.PronosticoParticipante;

import java.util.List;

public interface PartidoDao {
	List<PartidoMundial> listarPartidoMundial();
	List<PartidoMundial> listarPartidoPronosticoMundial();
	Integer registrarPronosticoParticipante(PronosticoParticipante pronosticoParticipante);
	List<PronosticoParticipante> obtenerPronosticoRespuesta(Integer idParticipante);
}


