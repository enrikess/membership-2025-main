package com.promotick.lafabril.model.util;

import lombok.Data;

import java.io.Serializable;

@Data
public class FiltroProducto implements Serializable {

    private static final long serialVersionUID = 409688127975870832L;
    private Integer start;
    private Integer length;
    private Integer idProducto = 0;
    private Integer idCatalogo = 0;
    private String buscar;

}
