package com.promotick.lafabril.model.web;

import lombok.Data;

import java.io.Serializable;

@Data
public class TipoVivienda implements Serializable {
    private static final long serialVersionUID = -8550353757149107755L;

    private Integer idTipoVivienda;
    private String nombreTipoVivienda;
    private String descripcionTipoVivienda;
    private Integer estadoTipoVivienda;
    private Integer ordenTipoVivienda;

}
