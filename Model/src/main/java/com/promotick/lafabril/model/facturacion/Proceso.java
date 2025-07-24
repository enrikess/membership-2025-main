package com.promotick.lafabril.model.facturacion;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class Proceso implements Serializable {
    private static final long serialVersionUID = 7006242470782573790L;

    private Integer idProceso;
    private Date fechaInicioProceso;
    private Date fechaFinProceso;
    private String tipoProceso;
    private Integer estadoProceso;
    private String mensaje;
}
