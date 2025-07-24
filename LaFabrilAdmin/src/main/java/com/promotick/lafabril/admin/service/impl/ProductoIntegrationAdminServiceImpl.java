package com.promotick.lafabril.admin.service.impl;

import com.promotick.lafabril.admin.service.ProductoIntegrationAdminService;
import com.promotick.lafabril.dao.admin.ProductoIntegrationDao;
import com.promotick.lafabril.model.web.Catalogo;
import com.promotick.lafabril.model.web.Producto;
import com.promotick.lafabril.model.web.ProductoCatalogo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductoIntegrationAdminServiceImpl implements ProductoIntegrationAdminService {

    private ProductoIntegrationDao productoIntegrationDao;

    public ProductoIntegrationAdminServiceImpl(ProductoIntegrationDao productoIntegrationDao) {
        this.productoIntegrationDao = productoIntegrationDao;
    }

    @Override
    @Transactional
    public Integer actualizarProducto(Producto producto) throws Exception {
        Integer registroProducto = productoIntegrationDao.actualizarProducto(producto);
        if (registroProducto == null || registroProducto <= 0) {
            throw new Exception("No se pudo registrar el producto");
        }
        List<ProductoCatalogo> list = new ArrayList<>();
        if (producto.getCodigoCatalogo() != null) {
            String[] catalogos = producto.getCodigoCatalogo().split(",");
            for (String catalogo : catalogos) {
                list.add(
                        new ProductoCatalogo()
                                .setCatalogo(
                                        new Catalogo()
                                                .setCodigoCatalogo(catalogo)
                                ).setProducto(
                                new Producto().setIdProducto(registroProducto))
                );
            }

            for (ProductoCatalogo productoCatalogo : list) {
                productoCatalogo.setAuditoria(producto.getAuditoria());
                Integer registroPC = productoIntegrationDao.registrarProductoCatalogo(productoCatalogo);
                if (registroPC == null || registroPC <= 0) {
                    throw new Exception("No se pudo registrar el Catalogo para el producto");
                }
            }
        }
        return registroProducto;
    }
}
