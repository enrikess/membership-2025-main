package com.promotick.lafabril.dao.web;

import com.promotick.lafabril.model.reporte.ReporteCategoria;
import com.promotick.lafabril.model.util.FiltroCategoria;
import com.promotick.lafabril.model.web.Categoria;

import java.util.List;

public interface CategoriaDao {
    List<Categoria> obtenerCategoriasAdmin(FiltroCategoria filtroCategoria);

    List<Categoria> obtenerSubcategorias(Integer idCategoria);

    Integer registroCategoria(Categoria categoria);

    Integer actualizacionOrdenSubcategoria(String cadena);

    Integer actualizacionSubcategoria(Categoria subCategoria);

    List<Categoria> obtenerCategoriasGeneral(Integer idCatalogo);

    List<Categoria> listarSubCategoria(Integer idSubcategoria);

    Categoria obtenerCategoria(Integer idCatalogo, Integer idCategoria);

    List<Categoria> listarCategoriasHijas(Integer idCatalogo, Integer idCategoriaPadre);

    List<ReporteCategoria> reporteCategorias(FiltroCategoria filtroCategoria);
}
