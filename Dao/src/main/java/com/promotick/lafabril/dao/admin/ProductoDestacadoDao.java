package com.promotick.lafabril.dao.admin;

import com.promotick.lafabril.model.util.FiltroProductoDestacado;
import com.promotick.lafabril.model.web.ProductoDestacado;

import java.util.List;

public interface ProductoDestacadoDao {
    List<ProductoDestacado> listarProductoDestacado(FiltroProductoDestacado filtroProductoDestacado);
    List<ProductoDestacado> listarProductoDestacadoWeb(FiltroProductoDestacado filtroProductoDestacado);

    List<ProductoDestacado> listarProductoCategoria(FiltroProductoDestacado filtroBusqueda);

    Integer registrarProductoDestacado(ProductoDestacado productoDestacado);

    Integer contarProductoDestacado(FiltroProductoDestacado filtroBusqueda);

    Integer contarProductoCategoria(FiltroProductoDestacado filtroBusqueda);

    Integer eliminarProductoDestacado(Integer idProductoDestacado);

}
