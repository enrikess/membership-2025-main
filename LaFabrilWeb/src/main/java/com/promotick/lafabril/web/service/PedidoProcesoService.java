package com.promotick.lafabril.web.service;


import com.promotick.lafabril.model.web.Pedido;
import com.promotick.lafabril.web.util.ResultadoProcesoPedido;

public interface PedidoProcesoService {
    ResultadoProcesoPedido procesarPedido(Pedido pedido) throws Exception;
}
