package com.promotick.lafabril.model.util;

import com.promotick.lafabril.common.UtilCommon;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class Auditoria implements Serializable {
    private static final long serialVersionUID = 7769593235648986918L;

    private String usuarioCreacion;
    private String usuarioActualizacion;
    private Date fechaCreacion;
    private Date fechaActualizacion;
    private Integer idUsuarioCreacion;
    private Integer idUsuarioActualizacion;

    private String fechaCreacionString;
    private String fechaActualizacionString;

    public String getFechaCreacionString() {
        if (fechaCreacion != null) {
            fechaCreacionString = UtilCommon.dateFormat(fechaCreacion, UtilCommon.FORMATO_FECHA_DIA_MES_ANIO);
        }
        return fechaCreacionString;
    }

    public String getFechaActualizacionString() {
        if (fechaActualizacion != null) {
            fechaActualizacionString = UtilCommon.dateFormat(fechaActualizacion, UtilCommon.FORMATO_FECHA_DIA_MES_ANIO);
        }
        return fechaActualizacionString;
    }
}
