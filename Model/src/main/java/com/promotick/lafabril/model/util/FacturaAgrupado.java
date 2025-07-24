package com.promotick.lafabril.model.util;

import lombok.Data;

import java.io.Serializable;

@Data
public class FacturaAgrupado implements Serializable {
    private static final long serialVersionUID = 7843294958396641411L;

    private String nombreMes;
    private Double montoFactura;
    private Integer puntosPosibles;
    private Double descuentos;
    private Integer puntosAcreditados;
    private Integer metaAnual;
    private Integer metaMensual;

}
