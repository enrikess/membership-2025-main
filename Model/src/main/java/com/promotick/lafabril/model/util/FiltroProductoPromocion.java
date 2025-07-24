package com.promotick.lafabril.model.util;

import lombok.Data;

import java.io.Serializable;

@Data
public class FiltroProductoPromocion implements Serializable {

    private static final long serialVersionUID = 1679844539560475451L;

    private Integer start;
    private Integer length;
    private Integer idProducto = 0;
    private Integer idCatalogo = 0;
    private String nombreProducto = "";
    private String categorias = "";
}