package com.promotick.lafabril.model.facturacion;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class CargaProceso implements Serializable {
    private static final long serialVersionUID = -2786595792381363944L;

    private Integer idCargaProceso;
    private Proceso proceso;
    private Carga carga;
    private Integer estadoCargaProceso;
    private Date fechaCreacion;
    private String mensaje;
    private Integer total = 0;
    private Integer correctos = 0;
    private Integer errores = 0;

}
