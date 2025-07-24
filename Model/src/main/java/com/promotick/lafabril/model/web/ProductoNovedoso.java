package com.promotick.lafabril.model.web;

import com.promotick.lafabril.model.util.BeanBase;
import lombok.Data;

@Data
public class ProductoNovedoso extends BeanBase {


    private static final long serialVersionUID = 1712287006810656338L;
    private Integer idProductoNovedoso;
    private Producto producto;
    private Catalogo catalogo;
    private Integer estadoProductoNovedoso;

}
