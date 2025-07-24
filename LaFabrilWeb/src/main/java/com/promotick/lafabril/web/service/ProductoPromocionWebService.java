package com.promotick.lafabril.web.service;

import com.promotick.lafabril.model.util.FiltroProductoPromocion;
import com.promotick.lafabril.model.web.ProductoPromocion;

import java.util.List;

public interface ProductoPromocionWebService {
    List<ProductoPromocion> listarProductoPromocion(FiltroProductoPromocion filtroProductoPromocion);
    List<ProductoPromocion> listarProductoPromocionWeb(FiltroProductoPromocion filtroProductoPromocion);
}
