package com.promotick.lafabril.model.util;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
public class FiltroReporteEncuestaEmpleados implements Serializable {

    private static final long serialVersionUID = 7232777702305591696L;
    private String finicio;
    private String ffin;
    private String buscar;
    private Integer length;
    private Integer start;

}
