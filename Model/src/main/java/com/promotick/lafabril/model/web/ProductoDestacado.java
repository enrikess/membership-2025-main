package com.promotick.lafabril.model.web;

import com.promotick.lafabril.model.util.BeanBase;
import lombok.Data;

@Data
public class ProductoDestacado extends BeanBase {

    private static final long serialVersionUID = 1337789955518918256L;

    private Integer idProductoDestacado;
    private Producto producto;
    private Catalogo catalogo;
    private Integer estadoProductoDestacado;

}
