package com.promotick.lafabril.admin.service.impl;

import com.promotick.lafabril.admin.service.ProductoDestacadoAdminService;
import com.promotick.lafabril.dao.admin.ProductoDestacadoDao;
import com.promotick.lafabril.model.util.FiltroProductoDestacado;
import com.promotick.lafabril.model.web.ProductoDestacado;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ProductoDestacadoAdminServiceImpl implements ProductoDestacadoAdminService {

    private ProductoDestacadoDao productoDestacadoDao;

    @Autowired
    public ProductoDestacadoAdminServiceImpl(ProductoDestacadoDao productoDestacadoDao) {
        this.productoDestacadoDao = productoDestacadoDao;
    }

    @Override
    public List<ProductoDestacado> listarProductoDestacado(FiltroProductoDestacado filtroProductoDestacado) {
        return productoDestacadoDao.listarProductoDestacado(filtroProductoDestacado);
    }

    @Override
    public List<ProductoDestacado> listarProductoCategoria(FiltroProductoDestacado filtroBusqueda) {
        return productoDestacadoDao.listarProductoCategoria(filtroBusqueda);
    }

    @Override
    @Transactional
    public Integer registrarProductoDestacado(ProductoDestacado productoDestacado) {
        return productoDestacadoDao.registrarProductoDestacado(productoDestacado);
    }

    @Override
    public Integer contarProductoDestacado(FiltroProductoDestacado filtroBusqueda) {
        return productoDestacadoDao.contarProductoDestacado(filtroBusqueda);
    }

    @Override
    public Integer contarProductoCategoria(FiltroProductoDestacado filtroBusqueda) {
        return productoDestacadoDao.contarProductoCategoria(filtroBusqueda);
    }

    @Override
    @Transactional
    public Integer eliminarProductoDestacado(Integer idProductoDestacado) {
        return productoDestacadoDao.eliminarProductoDestacado(idProductoDestacado);
    }
}
