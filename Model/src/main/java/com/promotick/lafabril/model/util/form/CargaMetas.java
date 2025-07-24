package com.promotick.lafabril.model.util.form;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class CargaMetas implements Serializable {
    private static final long serialVersionUID = 8626876039202294730L;

    private String nroDocumento;
    private Integer idCarga;
    private Double meta;
    private Integer anio;
    private Integer mes;
    private String descripcion;
    private String usuarioCreacion;
    private String operacion;
    private Double valorPago;
    private Double porcentajeRebate;
    private Integer puntosPremio;
    private String idProductos;
    private String listaId;
}
