package com.promotick.lafabril.web.service.impl;

import com.promotick.lafabril.dao.admin.PedidoDao;
import com.promotick.lafabril.model.web.Pedido;
import com.promotick.lafabril.model.web.PedidoDetalle;
import com.promotick.lafabril.web.service.PedidoWebService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PedidoWebServiceImpl implements PedidoWebService {

    private PedidoDao pedidoDao;

    @Autowired
    public PedidoWebServiceImpl(PedidoDao pedidoDao) {
        this.pedidoDao = pedidoDao;
    }

    @Override
    public List<PedidoDetalle> listarDetallePedido(Integer idPedido) {
        return pedidoDao.listarDetallePedido(idPedido);
    }

    @Override
    public Pedido listarPedidoById(Integer idPedido) {
        return pedidoDao.listarPedidoById(idPedido);
    }

    @Override
    public Integer validarDescuento(String descuento, Integer idParticipante) {
        return pedidoDao.validarDescuento(descuento,idParticipante);
    }

    @Override
    public Integer obtenerMontoDescuento(Integer idDescuento, Integer montoTotal) {
        return pedidoDao.obtenerMontoDescuento(idDescuento,montoTotal);
    }

}
