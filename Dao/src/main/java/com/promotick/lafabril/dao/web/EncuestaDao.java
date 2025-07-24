package com.promotick.lafabril.dao.web;

import com.promotick.lafabril.model.util.FiltroEncuesta;
import com.promotick.lafabril.model.web.Encuesta;
import com.promotick.lafabril.model.web.EncuestaDetalle;

import java.util.List;

public interface EncuestaDao {
    Integer registrarEncuesta(Encuesta encuesta);
    Integer registrarEncuestaDetalle(EncuestaDetalle encuestaDetalle);
    Integer encuestaPedidoObtener(Integer idParticipante);
    List<EncuestaDetalle> listarResultadosEncuesta(FiltroEncuesta filtroEncuesta);
    Integer contarResultadosEncuesta(FiltroEncuesta filtroEncuesta);
    Integer omitirEncuesta(Integer idPedido);
}
