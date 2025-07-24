package com.promotick.lafabril.web.service;

import com.promotick.lafabril.model.web.Categoria;

import java.util.List;

public interface CategoriaWebService {
    List<Categoria> obtenerCategoriasGeneral(Integer idCatalogo);

    Categoria obtenerCategoria(Integer idCatalogo, Integer idCategoria);
}
