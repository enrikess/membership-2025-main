package com.promotick.lafabril.model.util;

import lombok.Data;

import java.io.Serializable;

@Data
public class FiltroBanner implements Serializable {

    private static final long serialVersionUID = -2399755164555254417L;
    private Integer orden;
    private Integer tipoBanner;
    private Integer start;
    private Integer length;

}
