package com.promotick.lafabril.web.service.impl;

import com.promotick.lafabril.dao.admin.ProductoPromocionDao;
import com.promotick.lafabril.model.util.FiltroProductoPromocion;
import com.promotick.lafabril.model.web.ProductoPromocion;
import com.promotick.lafabril.web.service.ProductoPromocionWebService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductoPromocionWebServiceImpl implements ProductoPromocionWebService {

    private ProductoPromocionDao productoPromocionDao;

    @Autowired
    public ProductoPromocionWebServiceImpl(ProductoPromocionDao productoPromocionDao) {
        this.productoPromocionDao = productoPromocionDao;
    }

    @Override
    public List<ProductoPromocion> listarProductoPromocion(FiltroProductoPromocion filtroProductoPromocion) {
        return productoPromocionDao.listarProductoPromocion(filtroProductoPromocion);
    }
    @Override
    public List<ProductoPromocion> listarProductoPromocionWeb(FiltroProductoPromocion filtroProductoPromocion) {
        return productoPromocionDao.listarProductoPromocionWeb(filtroProductoPromocion);
    }
}
