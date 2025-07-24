package com.promotick.lafabril.admin.service;

import com.promotick.lafabril.model.util.FiltroPedidoNetsuiteError;
import com.promotick.lafabril.model.web.Pedido;
import com.promotick.lafabril.model.web.PedidoDetalle;

import java.util.List;

public interface PedidoAdminService {
    List<Pedido> pedidosNetsuiteErrorListar(FiltroPedidoNetsuiteError filtroPedidoNetsuiteError);

    Integer pedidosNetsuiteErrorContar(FiltroPedidoNetsuiteError filtroPedidoNetsuiteError);

    Pedido listarPedidoById(Integer idPedido);

    Integer actualizarPedidoNetsuite(Pedido pedido);

    List<PedidoDetalle> listarDetallePedido(Integer idPedido);

    Integer actualizarInfoPedidoNetsuite(Pedido pedido);

    List<Pedido> listarPedidosNetsuiteReenvio();
}
