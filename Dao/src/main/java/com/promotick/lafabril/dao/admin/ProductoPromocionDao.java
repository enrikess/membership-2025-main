package com.promotick.lafabril.dao.admin;

import com.promotick.lafabril.model.util.FiltroProductoPromocion;
import com.promotick.lafabril.model.web.ProductoPromocion;

import java.util.List;

public interface ProductoPromocionDao {
    List<ProductoPromocion> listarProductoPromocion(FiltroProductoPromocion filtroProductoPromocion);
    List<ProductoPromocion> listarProductoPromocionWeb(FiltroProductoPromocion filtroProductoPromocion);

    Integer contarProductoPromocion(FiltroProductoPromocion filtroBusqueda);

    Integer eliminarProductoPromocion(Integer idProductoPromocion);

    Integer registrarProductoPromocion(ProductoPromocion productoPromocion);

}
