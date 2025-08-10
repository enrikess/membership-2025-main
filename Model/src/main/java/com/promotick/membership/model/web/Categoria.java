package com.promotick.membership.model.web;

import com.promotick.membership.model.util.BeanBase;
import com.promotick.membership.common.UtilCommon;
import lombok.Data;

import java.util.List;

@Data
public class Categoria extends BeanBase {

    private static final long serialVersionUID = -4267736089267573281L;

    private Integer idCategoria;
    private Integer idCategoriaParent;
    private String nombreCategoria;
    private Integer estadoCategoria;
    private Integer ordenCategoria;
    private String imagenCategoria;
    private String catalogosString;
    private String url;
    private Integer idTipoCategoria;
    private String codigoCategoria;
    private String codigoCategoriaParent;

    private List<Categoria> categoriasHijas;
    private Integer conteoCategoriasHijas;

    public String getUrl() {
        url = "catalogo/" + UtilCommon.parseUrl(idCategoria, nombreCategoria);
        return url;
    }
}
