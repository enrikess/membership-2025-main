package com.promotick.lafabril.admin.service;

import com.promotick.lafabril.model.reporte.ReporteCategoria;
import com.promotick.lafabril.model.util.FiltroCategoria;
import com.promotick.lafabril.model.web.Categoria;

import java.util.List;

public interface CategoriaAdminService {
    List<Categoria> obtenerCategoriasAdmin(FiltroCategoria filtroCategoria);

    Integer registroCategoria(Categoria categoria);

    List<Categoria> obtenerSubcategorias(Integer idCategoria);

    Integer actualizacionOrdenSubcategoria(String cadena);

    Integer actualizacionSubcategoria(Categoria subCategoria);

    List<Categoria> listarSubcategoria(Integer idSubcategoria);

    List<Categoria> obtenerCategoriasGeneral(Integer idCatalogo);

    List<ReporteCategoria> reporteCategorias(FiltroCategoria filtroCategoria);
}
