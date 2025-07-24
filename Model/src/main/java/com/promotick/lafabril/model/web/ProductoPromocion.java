package com.promotick.lafabril.model.web;

import com.promotick.lafabril.model.util.BeanBase;
import lombok.Data;

@Data
public class ProductoPromocion extends BeanBase {

    private static final long serialVersionUID = 1337789955518918256L;

    private Integer idProductoPromocion;
    private Producto producto;
    private Catalogo catalogo;
    private Integer estadoProductoPromocion;

}
