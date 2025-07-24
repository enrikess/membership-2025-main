package com.promotick.lafabril.model.reporte;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class ReporteMetas implements Serializable {
    private static final long serialVersionUID = -6225312483018895935L;

    private Integer idParticipanteMeta;
    private Integer idCarga;
    private Integer idParticipante;
    private Integer anio;
    private Integer mes;
    private String nroDocumento;
    private Double valorMeta;
    private Date fechaRegistro;
    private Integer estado;
    private String descripcion;
    private String usuarioRegistro;
    private Date fechaActualizacion;
    private String usuarioActualizacion;
    private Integer idTipoMeta;
    private Double porcentajeRebate;
    private Integer puntosPremio;
    private Double valorPago;
    private String idProductos;
    private String fechaProducto;
    private String accionProducto;
    private String descripcionProducto;
    private String cantidadProducto;

}
