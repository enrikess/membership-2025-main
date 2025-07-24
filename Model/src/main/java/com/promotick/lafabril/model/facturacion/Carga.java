package com.promotick.lafabril.model.facturacion;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class Carga implements Serializable {
    private static final long serialVersionUID = 1643102113383138639L;

    private Integer idCarga;
    private TipoCarga tipoCarga;
    private Integer idResource;
    private String nombreArchivo;
    private String folderArchivo;
    private Integer estadoCarga;
    private Date fechaCreacion;
    private String bucket;
}
