package com.promotick.lafabril.model.util;

import lombok.Data;

import java.io.Serializable;

@Data
public class FiltroCategoria implements Serializable {
    private static final long serialVersionUID = -5509202522227464052L;

    private Integer start;
    private Integer length;
    private Integer orden;
    private Integer idCategoria;
    private String nombreCategoria = "";

    public FiltroCategoria() {
        this.orden = 1;
        this.idCategoria = 0;
    }
}
