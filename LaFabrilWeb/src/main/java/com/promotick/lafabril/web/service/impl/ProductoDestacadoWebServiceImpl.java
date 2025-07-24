package com.promotick.lafabril.web.service.impl;

import com.promotick.lafabril.dao.admin.ProductoDestacadoDao;
import com.promotick.lafabril.model.util.FiltroProductoDestacado;
import com.promotick.lafabril.model.web.ProductoDestacado;
import com.promotick.lafabril.web.service.ProductoDestacadoWebService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class ProductoDestacadoWebServiceImpl implements ProductoDestacadoWebService {

    private ProductoDestacadoDao productoDestacadoDao;

    @Autowired
    public ProductoDestacadoWebServiceImpl(ProductoDestacadoDao productoDestacadoDao) {
        this.productoDestacadoDao = productoDestacadoDao;
    }

    @Override
    public List<ProductoDestacado> listarProductoDestacado(FiltroProductoDestacado filtroProductoDestacado) {
        return productoDestacadoDao.listarProductoDestacado(filtroProductoDestacado);
    }

    @Override
    public List<ProductoDestacado> listarProductoDestacadoWeb(FiltroProductoDestacado filtroProductoDestacado) {
        return productoDestacadoDao.listarProductoDestacadoWeb(filtroProductoDestacado);
    }
}
