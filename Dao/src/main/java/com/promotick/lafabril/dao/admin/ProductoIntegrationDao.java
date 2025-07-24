package com.promotick.lafabril.dao.admin;

import com.promotick.lafabril.model.web.Producto;
import com.promotick.lafabril.model.web.ProductoCatalogo;

public interface ProductoIntegrationDao {

    Integer actualizarProducto(Producto producto);

    Integer registrarProductoCatalogo(ProductoCatalogo productoCatalogo);
}
