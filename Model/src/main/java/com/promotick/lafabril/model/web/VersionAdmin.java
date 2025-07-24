package com.promotick.lafabril.model.web;

import lombok.Data;

@Data
public class VersionAdmin {

    private static final long serialVersionUID = 5647268078084312291L;

    private Integer idVersionAdmin;
    private String versionTienda;
//    private TipoDispositivo tipoDispositivo;
    private Integer isTest;
    private String descripcion;
    private String comentario;
    private Integer estado;


}
