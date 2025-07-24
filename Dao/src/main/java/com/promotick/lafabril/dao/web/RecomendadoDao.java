package com.promotick.lafabril.dao.web;

import com.promotick.lafabril.model.web.Recomendado;

import java.util.List;

public interface RecomendadoDao {

    Integer registrarRecomendado(Recomendado recomendado);

    List<Recomendado> listarRecomendados(Integer idParticipante);
}
