package com.promotick.lafabril.admin.service.impl;

import com.promotick.lafabril.admin.service.PedidoAdminService;
import com.promotick.lafabril.dao.admin.PedidoDao;
import com.promotick.lafabril.model.util.FiltroPedidoNetsuiteError;
import com.promotick.lafabril.model.web.Pedido;
import com.promotick.lafabril.model.web.PedidoDetalle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class PedidoAdminServiceImpl implements PedidoAdminService {

    private PedidoDao pedidoDao;

    @Autowired
    public PedidoAdminServiceImpl(PedidoDao pedidoDao) {
        this.pedidoDao = pedidoDao;
    }

    @Override
    public List<Pedido> pedidosNetsuiteErrorListar(FiltroPedidoNetsuiteError filtroPedidoNetsuiteError) {
        return pedidoDao.pedidosNetsuiteErrorListar(filtroPedidoNetsuiteError);
    }

    @Override
    public Integer pedidosNetsuiteErrorContar(FiltroPedidoNetsuiteError filtroPedidoNetsuiteError) {
        return pedidoDao.pedidosNetsuiteErrorContar(filtroPedidoNetsuiteError);
    }

    @Override
    public Pedido listarPedidoById(Integer idPedido) {
        return pedidoDao.listarPedidoById(idPedido);
    }

    @Override
    @Transactional
    public Integer actualizarPedidoNetsuite(Pedido pedido) {
        return pedidoDao.actualizarPedidoNetsuite(pedido);
    }

    @Override
    public List<PedidoDetalle> listarDetallePedido(Integer idPedido) {
        return pedidoDao.listarDetallePedido(idPedido);
    }

    @Override
    @Transactional
    public Integer actualizarInfoPedidoNetsuite(Pedido pedido) {
        return pedidoDao.actualizarInfoPedidoNetsuite(pedido);
    }

    @Override
    public List<Pedido> listarPedidosNetsuiteReenvio() {
        List<Pedido> pedidos = pedidoDao.listarPedidosNetsuiteReenvio();
        for (Pedido pedido : pedidos) {
            pedido.setPedidoDetalles(pedidoDao.listarDetallePedido(pedido.getIdPedido()));
        }
        return pedidos;
    }
}
