package com.promotick.lafabril.model.util;

import lombok.Data;

import java.io.Serializable;

@Data
public class FiltroCatalogo implements Serializable {

    private static final long serialVersionUID = -7504804311154164934L;
    private Integer orden;
    private Integer pagina;
    private Integer idCatalogo;
    private String buscar;
    private Integer categoria;
    private Integer rangoMin;
    private Integer rangoMax;
    private Integer imagenEnvioExpress;

}
