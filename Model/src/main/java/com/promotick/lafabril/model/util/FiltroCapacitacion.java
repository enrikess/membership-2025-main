package com.promotick.lafabril.model.util;

import lombok.Data;

import java.io.Serializable;

@Data
public class FiltroCapacitacion implements Serializable {

    private static final long serialVersionUID = 8731119839050226304L;
    private Integer length;
    private Integer start;
    private String buscar;

    public static FiltroCapacitacion empty() {
        return new FiltroCapacitacion()
                .setLength(Integer.MAX_VALUE)
                .setStart(0);
    }
}
