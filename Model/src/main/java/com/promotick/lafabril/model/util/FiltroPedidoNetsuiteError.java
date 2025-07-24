package com.promotick.lafabril.model.util;

import lombok.Data;

import java.io.Serializable;

@Data
public class FiltroPedidoNetsuiteError implements Serializable {

    private static final long serialVersionUID = 8259582621604464790L;
    private Integer start;
    private Integer length;
    private Integer orden;
    private String buscar;
}
