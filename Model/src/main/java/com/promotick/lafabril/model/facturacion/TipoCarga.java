package com.promotick.lafabril.model.facturacion;

import lombok.Data;

import java.io.Serializable;

@Data
public class TipoCarga implements Serializable {
    private static final long serialVersionUID = -2561126852446881847L;

    private Integer idTipoCarga;
    private String nombreTipoCarga;
    private Integer estadoTipoCarga;

}
