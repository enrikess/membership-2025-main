package com.promotick.lafabril.model.util.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

@AllArgsConstructor
public enum TipoRecursoEnum {
    NONE(0), TEXTO(1), IMAGEN(2), VIDEO(3), PDF(4);

    @Getter
    final int code;

    public static TipoRecursoEnum getByCode(Integer code) {
        if (code == null) return NONE;
        return Arrays.stream(TipoRecursoEnum.values()).filter(e -> e.getCode() == code).findAny().orElse(NONE);
    }
}
