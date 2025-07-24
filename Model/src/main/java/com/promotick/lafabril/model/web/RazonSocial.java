package com.promotick.lafabril.model.web;

import com.promotick.lafabril.model.util.BeanBase;
import lombok.Data;

@Data
public class RazonSocial extends BeanBase {
    private static final long serialVersionUID = -7415371435095320907L;

    private Integer idRazonsocial;
    private String nombreRazonsocial;
    private Integer estadoRazonsocial;

}
