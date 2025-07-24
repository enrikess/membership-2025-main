package com.promotick.lafabril.model.web;

import lombok.Data;

import java.io.Serializable;

@Data
public class ConfigBannerMovil implements Serializable {

    private static final long serialVersionUID = 7038286144460712233L;
    private Integer idConfiguracionBannerMovil;
    private String imagenBannerMovil;
    private Integer tipoBannerMovil;
    private Integer maxVisitas;
    private Integer tipoRedirect;
    private String urlRedirect;
    private Integer idEntidadRedirect;
    private Integer idCatalogo;
    private String nombreCatalogo;
    private String nombreProducto;
    private Integer estadoBannerMovil;
    private Integer accion;

}
