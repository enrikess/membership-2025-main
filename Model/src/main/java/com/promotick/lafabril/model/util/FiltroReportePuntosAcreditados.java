package com.promotick.lafabril.model.util;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
public class FiltroReportePuntosAcreditados implements Serializable {

    private static final long serialVersionUID = -1314238751982898230L;
    private String finicio;
    private String ffin;
    private Integer length;
    private Integer start;

}
