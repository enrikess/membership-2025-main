package com.promotick.lafabril.dao.web;

import com.promotick.lafabril.model.web.Catalogo;

import java.util.List;

public interface CatalogoDao {

    List<Catalogo> listarCatalogos();

    List<Catalogo> listarCatalogos(Integer idCatalogo, Integer orden);

    Integer actualizarCatalogo(Catalogo catalogo);

    Integer actualizarEstadoCatalogo(Integer idCatalogo, Integer estado, Integer tipo);

    List<Catalogo> listarCatalogoMultiselectSubcategoria(Integer idSubcategoria, Integer tipo);

    Integer actualizarPopupCatalogo(Catalogo catalogo);

    Integer actualizarBannerFlotanteCatalogo(Catalogo catalogo);
}
