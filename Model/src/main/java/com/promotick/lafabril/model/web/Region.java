package com.promotick.lafabril.model.web;

import com.promotick.lafabril.model.util.BeanBase;
import lombok.Data;

@Data
public class Region extends BeanBase {
    private static final long serialVersionUID = -7415371435095320907L;

    private Integer idRegion;
    private String nombreRegion;

}
