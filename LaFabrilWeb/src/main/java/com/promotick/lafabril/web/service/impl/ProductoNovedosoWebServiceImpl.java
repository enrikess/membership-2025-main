package com.promotick.lafabril.web.service.impl;

import com.promotick.lafabril.dao.admin.ProductoNovedosoDao;
import com.promotick.lafabril.model.util.FiltroProductoNovedoso;
import com.promotick.lafabril.model.web.ProductoNovedoso;
import com.promotick.lafabril.web.service.ProductoNovedosoWebService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class ProductoNovedosoWebServiceImpl implements ProductoNovedosoWebService {

    private ProductoNovedosoDao productoNovedosoDao;

    @Autowired
    public ProductoNovedosoWebServiceImpl(ProductoNovedosoDao productoNovedosoDao) {
        this.productoNovedosoDao = productoNovedosoDao;
    }

    @Override
    public List<ProductoNovedoso> listarProductoNovedoso(FiltroProductoNovedoso filtroProductoNovedoso) {
        return productoNovedosoDao.listarProductoNovedoso(filtroProductoNovedoso);
    }

    @Override
    public List<ProductoNovedoso> listarProductoNovedosoWeb(FiltroProductoNovedoso filtroProductoNovedoso) {
        return productoNovedosoDao.listarProductoNovedosoWeb(filtroProductoNovedoso);
    }
}
