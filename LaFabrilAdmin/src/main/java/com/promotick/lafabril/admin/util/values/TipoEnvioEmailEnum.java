package com.promotick.lafabril.admin.util.values;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum TipoEnvioEmailEnum {
    EMAIL_BIENVENIDA(1),
    EMAIL_CARGA_FACTURAS(2),
    EMAIL_CIERRE_PERIODO(3),
    EMAIL_CARGA_MANUALES(4),
    EMAIL_PRODUCTOS_NUEVOS(5);

    @Getter
    Integer codigo;
}
