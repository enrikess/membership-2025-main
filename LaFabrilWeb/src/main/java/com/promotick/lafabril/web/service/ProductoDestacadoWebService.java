package com.promotick.lafabril.web.service;

import com.promotick.lafabril.model.util.FiltroProductoDestacado;
import com.promotick.lafabril.model.web.ProductoDestacado;

import java.util.List;

public interface ProductoDestacadoWebService {
    List<ProductoDestacado> listarProductoDestacado(FiltroProductoDestacado filtroProductoDestacado);
    List<ProductoDestacado> listarProductoDestacadoWeb(FiltroProductoDestacado filtroProductoDestacado);
}
