package com.promotick.lafabril.web.service;

import com.promotick.lafabril.model.web.Encuesta;

public interface EncuestaWebService {
    void registrarEncuestaRegular(Encuesta encuesta);
    void registrarEncuestaTransactional(Encuesta encuesta);
    Integer encuestaPedidoObtener(Integer idParticipante);
    Integer omitirEncuesta(Integer idPedido);
}
