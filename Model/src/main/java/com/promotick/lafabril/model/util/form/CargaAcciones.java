package com.promotick.lafabril.model.util.form;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class CargaAcciones implements Serializable {
    private static final long serialVersionUID = 8626876039202294730L;

    private Integer idLafabrilProducto;
    private Integer idCarga;
    private String usuarioCreacion;
    private String operacion;
    private String descripcion;
    private Integer cantidad;
    private String accion;
    private Date fecha;
    private String listaId;
}
