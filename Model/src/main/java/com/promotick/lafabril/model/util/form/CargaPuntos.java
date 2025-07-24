package com.promotick.lafabril.model.util.form;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class CargaPuntos implements Serializable {
    private static final long serialVersionUID = 8626876039202294730L;

    private String nroDocumento;
    private Integer tipoOperacion;
    private Integer monto;
    private String descripcion;
    private Date fechaOperacion;
    private String usuarioCreacion;
    private Integer idCarga;
    private String nroFactura;
}
