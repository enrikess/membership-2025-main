package com.promotick.lafabril.web.service;

import com.promotick.lafabril.model.util.FiltroCatalogo;
import com.promotick.lafabril.model.util.RangoPuntos;
import com.promotick.lafabril.model.web.Catalogo;
import com.promotick.lafabril.model.web.Pedido;
import com.promotick.lafabril.model.web.ProductoCatalogo;

import java.util.List;

public interface ProductoWebService {
    ProductoCatalogo obtenerProductoID(Integer idCatalogo, Integer idProducto, Integer idParticipante);

    List<ProductoCatalogo> listarProductoXCatalogo(FiltroCatalogo filtroCatalogo);

    Integer contarProductoXCatalogo(FiltroCatalogo filtroCatalogo);

    List<ProductoCatalogo> listarProductosInteres(Integer idMarca, Integer idCatalogo);

    RangoPuntos obtenerRangoPuntos(Integer idCatalogo, Integer idCategoria, String buscar);

    ProductoCatalogo obtenerProductoCategoriaID(Integer idCatalogo, Integer idCategoria, Integer idProducto);

    String validarStock(Pedido pedido, Catalogo catalogo, Integer idParticipante);
}
