package com.promotick.lafabril.web.service;

import com.promotick.lafabril.model.util.FiltroProductoVisitado;
import com.promotick.lafabril.model.web.ProductoVisitado;

import java.util.List;

public interface ProductoVisitadoWebService {
    List<ProductoVisitado> listarProductoVisitado(FiltroProductoVisitado filtroProductoVisitado);
    List<ProductoVisitado> listarProductoVisitadoWeb(FiltroProductoVisitado filtroProductoVisitado);
}
