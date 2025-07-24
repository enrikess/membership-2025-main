package com.promotick.lafabril.model.util;

import lombok.Data;

import java.io.Serializable;

@Data
public class FiltroFacturas implements Serializable {

    private static final long serialVersionUID = -5716010105157835288L;

    private String finicio;
    private String ffin;
    private Integer length;
    private Integer start;
    private String buscar;

}
