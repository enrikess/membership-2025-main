package com.promotick.lafabril.admin.service.impl;

import com.promotick.lafabril.admin.service.ProductoAdminService;
import com.promotick.lafabril.dao.web.ProductoDao;
import com.promotick.lafabril.model.reporte.ReporteProducto;
import com.promotick.lafabril.model.util.FiltroProducto;
import com.promotick.lafabril.model.web.Catalogo;
import com.promotick.lafabril.model.web.Producto;
import com.promotick.lafabril.model.web.ProductoCatalogo;
import com.promotick.lafabril.model.web.TagProducto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductoAdminServiceImpl implements ProductoAdminService {

    private ProductoDao productoDao;

    @Autowired
    public ProductoAdminServiceImpl(ProductoDao productoDao) {
        this.productoDao = productoDao;
    }

    @Override
    public List<Producto> listarProductos(FiltroProducto filtroProducto) {
        return productoDao.listarProductos(filtroProducto);
    }

    @Override
    @Transactional
    public Integer actualizarEstadoProducto(Producto producto) {
        return productoDao.actualizarProducto(producto);
    }

    @Override
    @Transactional
    public Integer registrarProducto(Producto producto) {
        return productoDao.registrarProducto(producto);
    }

    @Override
    public List<ProductoCatalogo> listarProductoCatalogoXProducto(Integer idProducto) {
        return productoDao.listarProductoCatalogoXProducto(idProducto);
    }

    @Override
    @Transactional
    public Integer actualizarStockProductoCatalogo(ProductoCatalogo productoCatalogo) {
        return productoDao.actualizarStockProductoCatalogo(productoCatalogo);
    }

    @Override
    @Transactional
    public Integer actualizarEstadoProductoCatalogo(ProductoCatalogo productoCatalogo) {
        return productoDao.actualizarEstadoProductoCatalogo(productoCatalogo);
    }

    @Override
    @Transactional
    public Integer registrarProductoCatalogo(ProductoCatalogo productoCatalogo) {
        return productoDao.registrarProductoCatalogo(productoCatalogo);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW, isolation = Isolation.READ_UNCOMMITTED, rollbackFor = Exception.class)
    public Integer procesoRegistrarProducto(Producto producto) throws Exception {
        Integer registroProducto = productoDao.registrarProducto(producto);
        if (registroProducto == null || registroProducto <= 0) {
            throw new Exception("No se pudo registrar el producto");
        }
        List<ProductoCatalogo> list = new ArrayList<>();
        String[] catalogos = producto.getNombreCatalogo().split(";");
        for (String catalogo : catalogos) {
            String[] parts = catalogo.split("\\|");
            list.add(
                    new ProductoCatalogo()
                            .setCatalogo(
                                    new Catalogo()
                                            .setIdCatalogo(Integer.parseInt(parts[0]))
                            ).setProducto(
                            new Producto().setIdProducto(registroProducto))
                            .setStockProductoCatalogo(Integer.parseInt(parts[1])
                            )
            );
        }

        for (ProductoCatalogo productoCatalogo : list) {
            productoCatalogo.setAuditoria(producto.getAuditoria());
            Integer registroPC = productoDao.registrarProductoCatalogo(productoCatalogo);
            if (registroPC == null || registroPC <= 0) {
                throw new Exception("No se pudo registrar el Catalogo para el producto");
            }
        }

        return registroProducto;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW, isolation = Isolation.READ_UNCOMMITTED)
    public Integer productoCatalogoEnvioEmail(Boolean emailEnviado, String emailObservacion) {
        return productoDao.productoCatalogoEnvioEmail(emailEnviado, emailObservacion);
    }

    @Override
    public List<Producto> productoNuevosListar(Integer idCatalogo, Integer puntosDisponibles) {
        return productoDao.productoNuevosListar(idCatalogo, puntosDisponibles);
    }

    @Override
    public Integer contarProductos(FiltroProducto filtroProducto) {
        return productoDao.contarProductos(filtroProducto);
    }

    public List<ReporteProducto> reporteProductos(FiltroProducto filtroProducto) {
        return productoDao.reporteProductos(filtroProducto);
    }

    @Override
    public List<TagProducto> listarTags() {
        return productoDao.listarTags();
    }

}
