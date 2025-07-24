package com.promotick.lafabril.model.util;

import lombok.Data;

import java.io.Serializable;

@Data
public class MetaFacturacion implements Serializable {
    private static final long serialVersionUID = 633874315024732650L;

    private Integer periodoAnio;
    private Double metaAnual;
    private Double metaTrimestral;

}
