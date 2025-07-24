package com.promotick.lafabril.model.web;

import lombok.Data;

import java.io.Serializable;

@Data
public class Festividades implements Serializable {
    private static final long serialVersionUID = 3106609631826946893L;

    private Integer idFestividad;
    private String nombreFestividad;
    private Integer mesFestividad;
    private Integer diaFestividad;
    private String nombreImagen;
    private Integer estadofestividad;
}
