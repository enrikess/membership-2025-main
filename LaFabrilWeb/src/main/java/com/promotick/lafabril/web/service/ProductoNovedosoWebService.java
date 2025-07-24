package com.promotick.lafabril.web.service;

import com.promotick.lafabril.model.util.FiltroProductoNovedoso;
import com.promotick.lafabril.model.web.ProductoNovedoso;

import java.util.List;

public interface ProductoNovedosoWebService {
    List<ProductoNovedoso> listarProductoNovedoso(FiltroProductoNovedoso filtroProductoNovedoso);
    List<ProductoNovedoso> listarProductoNovedosoWeb(FiltroProductoNovedoso filtroProductoNovedoso);
}
