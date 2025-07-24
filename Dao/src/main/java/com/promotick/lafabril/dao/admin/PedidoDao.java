package com.promotick.lafabril.dao.admin;

import com.promotick.lafabril.model.util.FiltroPedidoNetsuiteError;
import com.promotick.lafabril.model.util.UtilEnum;
import com.promotick.lafabril.model.web.Pedido;
import com.promotick.lafabril.model.web.PedidoDetalle;

import java.util.List;

public interface PedidoDao {
    List<PedidoDetalle> listarDetallePedido(Integer idPedido);

    Integer registrarPedido(Pedido pedido);

    Integer registrarDetallePedido(PedidoDetalle pedidoDetalle);

    Integer registrarCanje(Pedido pedido, UtilEnum.TIPO_ORIGEN origen);

    Pedido listarPedidoById(Integer idPedido);

    Integer actualizarPedidoNetsuite(Pedido pedido);

    List<Pedido> pedidosNetsuiteErrorListar(FiltroPedidoNetsuiteError filtroPedidoNetsuiteError);

    Integer pedidosNetsuiteErrorContar(FiltroPedidoNetsuiteError filtroPedidoNetsuiteError);

    Integer actualizarInfoPedidoNetsuite(Pedido pedido);

    List<Pedido> listarPedidosNetsuiteReenvio();

    Integer validarDescuento(String descuento, Integer idParticipante);

    Integer obtenerMontoDescuento(Integer idDescuento, Integer montoTotal);
}
