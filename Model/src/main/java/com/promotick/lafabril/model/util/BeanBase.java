package com.promotick.lafabril.model.util;

import lombok.Data;

import java.io.Serializable;

@Data
public class BeanBase implements Serializable {

    private static final long serialVersionUID = -222854150551310239L;

    private Auditoria auditoria;
    private Integer accion;

}
