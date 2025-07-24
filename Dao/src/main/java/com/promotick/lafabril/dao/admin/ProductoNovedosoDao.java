package com.promotick.lafabril.dao.admin;

import com.promotick.lafabril.model.util.FiltroProductoNovedoso;
import com.promotick.lafabril.model.web.ProductoNovedoso;

import java.util.List;

public interface ProductoNovedosoDao {
    List<ProductoNovedoso> listarProductoNovedoso(FiltroProductoNovedoso filtroProductoNovedoso);
    List<ProductoNovedoso> listarProductoNovedosoWeb(FiltroProductoNovedoso filtroProductoNovedoso);

    Integer contarProductoNovedoso(FiltroProductoNovedoso filtroProductoNovedoso);
}
