package com.promotick.lafabril.model.web;

import lombok.Data;

import java.io.Serializable;

@Data
public class CargaWeb implements Serializable {

    private static final long serialVersionUID = -3532880880440971659L;

    private Integer idCarga;
    private Integer idTipoCarga;
    private String archivo;
    private String archivoError;
    private Integer correctos;
    private Integer errores;
    private Integer total;
    private Boolean estado;
    private String usuarioCarga;
    private String comentario;
}
