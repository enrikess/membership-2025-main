package com.promotick.lafabril.admin.service;

import com.promotick.lafabril.model.util.FiltroProductoPromocion;
import com.promotick.lafabril.model.web.ProductoPromocion;

import java.util.List;

public interface ProductoPromocionAdminService {
    List<ProductoPromocion> listarProductoPromocion(FiltroProductoPromocion filtroProductoPromocion);

    Integer contarProductoPromocion(FiltroProductoPromocion filtroBusqueda);

    Integer eliminarProductoPromocion(Integer idProductoPromocion);

    Integer registrarProductoPromocion(ProductoPromocion productoPromocion);
}
