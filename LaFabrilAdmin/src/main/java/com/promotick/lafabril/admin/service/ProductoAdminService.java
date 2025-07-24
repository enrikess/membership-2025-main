package com.promotick.lafabril.admin.service;

import com.promotick.lafabril.model.reporte.ReporteProducto;
import com.promotick.lafabril.model.util.FiltroProducto;
import com.promotick.lafabril.model.web.Producto;
import com.promotick.lafabril.model.web.ProductoCatalogo;
import com.promotick.lafabril.model.web.TagProducto;

import java.util.List;

public interface ProductoAdminService {
    List<Producto> listarProductos(FiltroProducto filtroProducto);

    Integer contarProductos(FiltroProducto filtroProducto);

    Integer actualizarEstadoProducto(Producto producto);

    Integer registrarProducto(Producto producto);

    List<ProductoCatalogo> listarProductoCatalogoXProducto(Integer idProducto);

    Integer actualizarStockProductoCatalogo(ProductoCatalogo productoCatalogo);

    Integer actualizarEstadoProductoCatalogo(ProductoCatalogo productoCatalogo);

    Integer registrarProductoCatalogo(ProductoCatalogo productoCatalogo);

    Integer procesoRegistrarProducto(Producto producto) throws Exception;

    Integer productoCatalogoEnvioEmail(Boolean emailEnviado, String emailObservacion);

    List<Producto> productoNuevosListar(Integer idCatalogo, Integer puntosDisponibles);

    List<ReporteProducto> reporteProductos(FiltroProducto filtroProducto);

    List<TagProducto> listarTags();
}
