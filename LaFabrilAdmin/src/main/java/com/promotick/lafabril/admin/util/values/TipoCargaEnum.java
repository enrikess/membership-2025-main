package com.promotick.lafabril.admin.util.values;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum TipoCargaEnum {
    CARGA_PARTICIPANTE(1, "archivos/carga-participantes/", "participantes/input", "participantes/procesados", TipoProcesoEnum.CARGA_PARTICIPANTE),
    CARGA_VENTAS(2, "archivos/carga-ventas/", "facturas/input", "facturas/procesados", TipoProcesoEnum.CARGA_VENTAS);

    @Getter
    Integer id;

    @Getter
    String folder;

    @Getter
    String pathInput;

    @Getter
    String pathProcesado;

    @Getter
    TipoProcesoEnum tipoProcesoEnum;
}
