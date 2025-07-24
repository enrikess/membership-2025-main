package com.promotick.lafabril.model.reporte;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class ReporteAcciones implements Serializable {
    private static final long serialVersionUID = -6225312483018895935L;

    private Integer idLafabrilProducto;
    private Date fechaProducto;
    private String descripcion;
    private String accionProducto;
    private Integer cantidadProducto;

}
