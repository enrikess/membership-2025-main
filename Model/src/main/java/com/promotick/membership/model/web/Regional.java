package com.promotick.membership.model.web;

import com.promotick.membership.model.util.BeanBase;
import lombok.Data;

@Data
public class Regional extends BeanBase {
    private static final long serialVersionUID = -7415371435095320907L;

    private Integer idRegional;
    private String nombreRegional;
    private Integer estadoRegional;

}
