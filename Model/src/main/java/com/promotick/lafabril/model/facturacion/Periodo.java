package com.promotick.lafabril.model.facturacion;

import com.promotick.lafabril.model.util.BeanBase;
import lombok.Data;

import java.util.Date;

@Data
public class Periodo extends BeanBase {
    private static final long serialVersionUID = 2636591822577431881L;

    private Integer idPeriodo;
    private TipoPeriodo tipoPeriodo;
    private String nombrePeriodo;
    private Integer datoPeriodo;
    private Date fechaInicio;
    private Date fechaFin;
    private Integer estadoPeriodo;
    private Integer periodoAnio;

}
