package com.promotick.lafabril.model.util.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum TipoEncuestaEnum {
    ENCUESTA_PEDIDO(1), ENCUESTA_PROCESO_CANJE(2);
    @Getter
    int code;
}
