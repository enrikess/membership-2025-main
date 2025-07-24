package com.promotick.lafabril.web.service.impl;

import com.promotick.lafabril.dao.admin.ProductoVisitadoDao;
import com.promotick.lafabril.model.util.FiltroProductoVisitado;
import com.promotick.lafabril.model.web.ProductoVisitado;
import com.promotick.lafabril.web.service.ProductoVisitadoWebService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductoVisitadoWebServiceImpl implements ProductoVisitadoWebService {

    private ProductoVisitadoDao productoVisitadoDao;

    @Autowired
    public ProductoVisitadoWebServiceImpl(ProductoVisitadoDao productoVisitadoDao) {
        this.productoVisitadoDao = productoVisitadoDao;
    }

    @Override
    public List<ProductoVisitado> listarProductoVisitado(FiltroProductoVisitado filtroProductoVisitado) {
        return productoVisitadoDao.listarProductoVisitado(filtroProductoVisitado);
    }

    @Override
    public List<ProductoVisitado> listarProductoVisitadoWeb(FiltroProductoVisitado filtroProductoVisitado) {
        return productoVisitadoDao.listarProductoVisitadoWeb(filtroProductoVisitado);
    }
}
