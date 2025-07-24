package com.promotick.lafabril.model.util;

import lombok.Data;

import java.io.Serializable;

@Data
public class FiltroProductoVisitado implements Serializable {

    private static final long serialVersionUID = 3882198382678124848L;
    private Integer start = 0;
    private Integer length = 5;
    private Integer idCatalogo = 0;
}