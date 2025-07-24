package com.promotick.lafabril.admin.service.impl;

import com.promotick.lafabril.admin.service.ProductoPromocionAdminService;
import com.promotick.lafabril.dao.admin.ProductoPromocionDao;
import com.promotick.lafabril.model.util.FiltroProductoPromocion;
import com.promotick.lafabril.model.web.ProductoPromocion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ProductoPromocionAdminServiceImpl implements ProductoPromocionAdminService {

    private ProductoPromocionDao productoPromocionDao;

    @Autowired
    public ProductoPromocionAdminServiceImpl(ProductoPromocionDao productoPromocionDao) {
        this.productoPromocionDao = productoPromocionDao;
    }


    @Override
    public List<ProductoPromocion> listarProductoPromocion(FiltroProductoPromocion filtroProductoPromocion) {
        return productoPromocionDao.listarProductoPromocion(filtroProductoPromocion);
    }

    @Override
    public Integer contarProductoPromocion(FiltroProductoPromocion filtroBusqueda) {
        return productoPromocionDao.contarProductoPromocion(filtroBusqueda);
    }

    @Transactional
    @Override
    public Integer eliminarProductoPromocion(Integer idProductoPromocion) {
        return productoPromocionDao.eliminarProductoPromocion(idProductoPromocion);
    }

    @Transactional
    @Override
    public Integer registrarProductoPromocion(ProductoPromocion productoPromocion) {
        return productoPromocionDao.registrarProductoPromocion(productoPromocion);
    }
}
