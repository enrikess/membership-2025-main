package com.promotick.lafabril.web.service;

import com.promotick.lafabril.model.web.Pedido;
import com.promotick.lafabril.model.web.PedidoDetalle;

import java.util.List;

public interface PedidoWebService {
    List<PedidoDetalle> listarDetallePedido(Integer idPedido);

    Pedido listarPedidoById(Integer idPedido);

    Integer validarDescuento(String descuento, Integer idParticipante);

    Integer obtenerMontoDescuento(Integer idDescuento, Integer montoTotal);
}
