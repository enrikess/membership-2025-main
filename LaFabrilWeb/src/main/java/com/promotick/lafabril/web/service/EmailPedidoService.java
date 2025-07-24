package com.promotick.lafabril.web.service;

import com.promotick.lafabril.model.web.Pedido;

public interface EmailPedidoService {
    void enviarEmailPedido(Pedido pedido, Integer puntosDisponibles) throws Exception;
}
