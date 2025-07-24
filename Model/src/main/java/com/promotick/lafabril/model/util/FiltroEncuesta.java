package com.promotick.lafabril.model.util;

import lombok.Data;

import java.io.Serializable;

@Data
public class FiltroEncuesta implements Serializable {

    private static final long serialVersionUID = -5716010105157835288L;

    private String finicio;
    private String ffin;
    private String buscar;
    private Integer length;
    private Integer start;

}
