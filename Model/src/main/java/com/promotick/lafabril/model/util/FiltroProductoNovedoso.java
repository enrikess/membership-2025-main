package com.promotick.lafabril.model.util;

import lombok.Data;

import java.io.Serializable;

@Data
public class FiltroProductoNovedoso implements Serializable {

    private static final long serialVersionUID = 3882198382678124848L;
    private Integer start;
    private Integer length;
    private Integer idProducto = 0;
    private Integer idCatalogo = 0;
    private String nombreProducto = "";
    private String categorias = "";
}