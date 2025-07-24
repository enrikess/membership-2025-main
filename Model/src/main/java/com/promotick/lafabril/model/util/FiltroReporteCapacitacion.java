package com.promotick.lafabril.model.util;

import lombok.Data;

import java.io.Serializable;

@Data
public class FiltroReporteCapacitacion implements Serializable {

    private static final long serialVersionUID = 7132328102425399641L;
    private Integer length;
    private Integer start;
    private String buscar;
    private String fechaInicio;
    private String fechaFin;
}
