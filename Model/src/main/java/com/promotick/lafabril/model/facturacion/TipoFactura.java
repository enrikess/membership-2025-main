package com.promotick.lafabril.model.facturacion;

import lombok.Data;

import java.io.Serializable;

@Data
public class TipoFactura implements Serializable {
    private static final long serialVersionUID = -5573827225892604468L;

    private Integer idTipoFactura;
    private String nombreTipoFactura;
    private String codigoTipoFactura;
    private Integer estadoTipoFactura;

}
