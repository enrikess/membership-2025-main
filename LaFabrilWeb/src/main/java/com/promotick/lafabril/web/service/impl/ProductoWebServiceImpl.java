package com.promotick.lafabril.web.service.impl;

import com.promotick.lafabril.common.ConstantesSesion;
import com.promotick.lafabril.dao.web.ProductoDao;
import com.promotick.lafabril.model.util.FiltroCatalogo;
import com.promotick.lafabril.model.util.RangoPuntos;
import com.promotick.lafabril.model.util.Util;
import com.promotick.lafabril.model.web.Catalogo;
import com.promotick.lafabril.model.web.Pedido;
import com.promotick.lafabril.model.web.PedidoDetalle;
import com.promotick.lafabril.model.web.ProductoCatalogo;
import com.promotick.lafabril.web.service.ProductoWebService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductoWebServiceImpl implements ProductoWebService {

    private ProductoDao productoDao;

    @Autowired
    public ProductoWebServiceImpl(ProductoDao productoDao) {
        this.productoDao = productoDao;
    }

    @Override
    public ProductoCatalogo obtenerProductoID(Integer idCatalogo, Integer idProducto, Integer idParticipante) {
        return productoDao.obtenerProductoID(idCatalogo, idProducto, idParticipante);
    }

    @Override
    public List<ProductoCatalogo> listarProductoXCatalogo(FiltroCatalogo filtroCatalogo) {
        return productoDao.listarProductoXCatalogo(filtroCatalogo);
    }

    @Override
    public Integer contarProductoXCatalogo(FiltroCatalogo filtroCatalogo) {
        return productoDao.contarProductoXCatalogo(filtroCatalogo);
    }

    @Override
    public List<ProductoCatalogo> listarProductosInteres(Integer idMarca, Integer idCatalogo) {
        return productoDao.listarProductosInteres(idMarca, idCatalogo);
    }

    @Override
    public RangoPuntos obtenerRangoPuntos(Integer idCatalogo, Integer idCategoria, String buscar) {
        return productoDao.obtenerRangoPuntos(idCatalogo, idCategoria, buscar);
    }

    @Override
    public ProductoCatalogo obtenerProductoCategoriaID(Integer idCatalogo, Integer idCategoria, Integer idProducto) {
        return productoDao.obtenerProductoCategoriaID(idCatalogo, idCategoria, idProducto);
    }

    @Override
    public String validarStock(Pedido pedido, Catalogo catalogo, Integer idParticipante) {
        for (PedidoDetalle pedidoDetalle : pedido.getPedidoDetalles()) {
            ProductoCatalogo productoCatalogo = productoDao.obtenerProductoID(catalogo.getIdCatalogo(), pedidoDetalle.getProducto().getIdProducto(), idParticipante);
            pedidoDetalle.setProducto(productoCatalogo.getProducto());
            Util.getSession().setAttribute(ConstantesSesion.SESSION_PEDIDO, pedido);
            if (!productoCatalogo.isTieneStockCatalogo() || !productoCatalogo.isTieneStock()) {
                return String.format("El producto: %s no tiene stock", productoCatalogo.getProducto().getCodigoWeb());
            }
            if (productoCatalogo.getStockProductoCatalogo() != -1 && pedidoDetalle.getCantidad() > productoCatalogo.getStockProductoCatalogo()) {
                return String.format("Solo tenemos %s de stock del producto %s para su catalogo", productoCatalogo.getStockProductoCatalogo(), productoCatalogo.getProducto().getCodigoWeb());
            }
            if (productoCatalogo.getProducto().getStockProducto() != -1 && pedidoDetalle.getCantidad() > productoCatalogo.getProducto().getStockProducto()) {
                return String.format("Solo tenemos %s de stock del producto %s", productoCatalogo.getProducto().getStockProducto(), productoCatalogo.getProducto().getCodigoWeb());
            }
            if ((productoCatalogo.getConFreciencia() && pedidoDetalle.getCantidad() > productoCatalogo.getCantidadFrecuencia()) || (productoCatalogo.getConFreciencia() && productoCatalogo.getConsumidoParticipante() >= productoCatalogo.getCantidadFrecuencia())) {
                return String.format("El Producto %s solo puede ser canjeado %s %s", productoCatalogo.getProducto().getCodigoWeb(), productoCatalogo.getCantidadFrecuencia(), this.getFrecuencia(productoCatalogo));
            }
        }
        return null;
    }

    private String getFrecuencia(ProductoCatalogo productoCatalogo) {
        boolean isPlural = false;
        if (productoCatalogo.getFrecuencia() > 1) {
            isPlural = true;
        }

        String cad = "";

        switch (productoCatalogo.getTiempo().trim().toLowerCase()) {
            case "hour":
                cad = "hora" + (isPlural ? "s" : "");
                break;
            case "day":
                cad = "dia" + (isPlural ? "s" : "");
                break;
            case "week":
                cad = "semana" + (isPlural ? "s" : "");
                break;
            case "month":
                cad = "mes" + (isPlural ? "es" : "");
                break;
            case "year":
                cad = "año" + (isPlural ? "s" : "");
                break;
        }

        if (StringUtils.isEmpty(cad)) {
            return "";
        }
        return "en " + productoCatalogo.getFrecuencia() + " " + cad;
    }
}
