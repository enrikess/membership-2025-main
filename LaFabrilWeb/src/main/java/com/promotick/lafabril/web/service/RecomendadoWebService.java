package com.promotick.lafabril.web.service;

import com.promotick.lafabril.model.web.Recomendado;

import java.util.List;

public interface RecomendadoWebService {

    List<Recomendado> listarRecomendados(Integer idParticipante);

    Integer registrarRecomendado(Recomendado recomendado);
}
