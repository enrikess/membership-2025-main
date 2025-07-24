package com.promotick.lafabril.model.util;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
public class FiltroReporteMundialEC implements Serializable {

    private static final long serialVersionUID = -8334958740727239060L;
    private Integer start;
    private Integer length;
    private String fechaInicio;
    private String fechaFin;
    private Integer tipoReporte;
    private Integer order;
    private String buscar;
    private Integer id;


}
