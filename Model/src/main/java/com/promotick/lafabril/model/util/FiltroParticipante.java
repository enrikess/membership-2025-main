package com.promotick.lafabril.model.util;

import lombok.Data;

import java.io.Serializable;

@Data
public class FiltroParticipante implements Serializable {

    private static final long serialVersionUID = 2186708083164120112L;
    private String buscar;
    private Integer orden;
    private Integer idCatalogo;
    private Integer start;
    private Integer length;

}
