package com.promotick.lafabril.web.service;


import com.promotick.lafabril.model.web.PartidoMundial;
import com.promotick.lafabril.model.web.PronosticoParticipante;

import java.util.List;

public interface PartidoService {

	List<PartidoMundial> listarPartidoMundial();
	List<PartidoMundial> listarPartidoPronosticoMundial();
	Integer registrarPronosticoParticipante(List<PronosticoParticipante> listPronosticoParticipante);
	List<PronosticoParticipante> obtenerPronosticoRespuesta(Integer idParticipante);
}
