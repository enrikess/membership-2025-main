package com.promotick.lafabril.model.facturacion;

import lombok.Data;

import java.io.Serializable;

@Data
public class TipoPeriodo implements Serializable {
    private static final long serialVersionUID = 5388666918984796691L;

    private Integer idTipoPeriodo;
    private String nombreTipoPeriodo;
    private Integer estadoTipoPeriodo;

}
