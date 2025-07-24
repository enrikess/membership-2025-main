package com.promotick.lafabril.model.util.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

@AllArgsConstructor
public enum TipoPreguntaEnum {
    NONE(0), SELECCION_SIMPLE(1), SELECCION_MULTIPLE(2);

    @Getter
    final int code;

    public static TipoPreguntaEnum getByCode(Integer code) {
        if (code == null) return NONE;
        return Arrays.stream(TipoPreguntaEnum.values()).filter(e -> e.getCode() == code).findAny().orElse(NONE);
    }
}
