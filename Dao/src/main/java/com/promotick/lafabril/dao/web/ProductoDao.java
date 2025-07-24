package com.promotick.lafabril.dao.web;

import com.promotick.lafabril.model.reporte.ReporteProducto;
import com.promotick.lafabril.model.util.FiltroCatalogo;
import com.promotick.lafabril.model.util.FiltroProducto;
import com.promotick.lafabril.model.util.RangoPuntos;
import com.promotick.lafabril.model.web.*;

import java.util.List;

public interface ProductoDao {
    List<Producto> listarProductos(FiltroProducto filtroProducto);

    Integer contarProductos(FiltroProducto filtroProducto);

    Integer actualizarProducto(Producto producto);

    Integer registrarProducto(Producto producto);

    ProductoCatalogo obtenerProductoID(Integer idCatalogo, Integer idProducto, Integer idParticipante);

    List<ProductoCatalogo> listarProductoXCatalogo(FiltroCatalogo filtroCatalogo);

    Integer contarProductoXCatalogo(FiltroCatalogo filtroCatalogo);

    List<ProductoCatalogo> listarProductosInteres(Integer idMarca, Integer idCatalogo);

    RangoPuntos obtenerRangoPuntos(Integer idCatalogo, Integer idCategoria, String buscar);

    ProductoCatalogo obtenerProductoCategoriaID(Integer idCatalogo, Integer idCategoria, Integer idProducto);

    Integer actualizarStock(PedidoDetalle pedidoDetalle, Catalogo catalogo, Producto producto);

    List<ProductoCatalogo> listarProductoCatalogoXProducto(Integer idProducto);

    Integer actualizarStockProductoCatalogo(ProductoCatalogo productoCatalogo);

    Integer actualizarEstadoProductoCatalogo(ProductoCatalogo productoCatalogo);

    Integer registrarProductoCatalogo(ProductoCatalogo productoCatalogo);

    Integer productoCatalogoEnvioEmail(Boolean emailEnviado, String emailObservacion);

    List<Producto> productoNuevosListar(Integer idCatalogo, Integer puntosDisponibles);

    List<ReporteProducto> reporteProductos(FiltroProducto filtroProducto);

    List<TagProducto> listarTags();
}
