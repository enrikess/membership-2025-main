package com.promotick.lafabril.model.util;

import lombok.Data;

import java.io.Serializable;

@Data
public class FiltroEstadoCuenta implements Serializable {

    private static final long serialVersionUID = 3638027957478524131L;

    private String finicio;
    private String ffin;
    private Integer length;
    private Integer start;

}
