package com.promotick.lafabril.dao.admin;

import com.promotick.lafabril.model.util.FiltroProductoVisitado;
import com.promotick.lafabril.model.web.ProductoVisitado;

import java.util.List;

public interface ProductoVisitadoDao {
    List<ProductoVisitado> listarProductoVisitado(FiltroProductoVisitado filtroProductoVisitado);
    List<ProductoVisitado> listarProductoVisitadoWeb(FiltroProductoVisitado filtroProductoVisitado);
}
