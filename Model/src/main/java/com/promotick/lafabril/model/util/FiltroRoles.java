package com.promotick.lafabril.model.util;

import lombok.Data;

import java.io.Serializable;

@Data
public class FiltroRoles implements Serializable {
    private static final long serialVersionUID = 409688127975870832L;
    private Integer start;
    private Integer length;
    private Integer idRol;
    private Integer estadoRol;

}
