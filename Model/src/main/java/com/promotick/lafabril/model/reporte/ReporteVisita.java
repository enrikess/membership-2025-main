package com.promotick.lafabril.model.reporte;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class ReporteVisita implements Serializable {
    private static final long serialVersionUID = -6225312483018895935L;

    //Comun
    private Integer conteo;

    //Productos
    private Integer idProducto;
    private String codigoWeb;
    private String nombreProducto;
    private Integer puntosProducto;

    //Marcas
    private Integer idMarca;
    private String nombreMarca;

    //Visitas
    private Integer idParticipante;
    private String nombresParticipante;
    private String appaternoParticipante;
    private String apmaternoParticipante;
    private String emailParticipante;
    private String nroDocumento;
    private Integer estadoParticipante;
    private Integer tipoDispositivo;

    //Categoria
    private Integer idCategoria;
    private String nombreCategoria;
    private String nombreSubCategoria;
    private String nombreCatalogo;

    private Integer idCategoriaParent;
    private String fechaCanje;
    private String fechaOperacion;
    private Integer puntosRestados;
    private String descripcion;

    //Fecha
    private Date fechaVisita;
    private Date horaVisita;

}
