package com.promotick.lafabril.model.facturacion;

import com.promotick.lafabril.common.UtilCommon;
import com.promotick.lafabril.model.util.BeanBase;
import com.promotick.lafabril.model.web.Participante;
import lombok.Data;

import java.util.Date;

@Data
public class Factura extends BeanBase {
    private static final long serialVersionUID = -3354905845576819675L;

    private Integer idFactura;
    private TipoFactura tipoFactura;
    private Participante participante;
    private String nroFactura;
    private Double ratioPuntos;
    private Double montoFactura;
    private Integer puntosPosibles;
    private Date fechaGenerado;
    private Date fechaEmision;
    private PeriodoParticipante periodoParticipante;
    private Integer puntosRestados;
    private Boolean emailEnviado;
    private String emailObservacion;

    //Temp
    private String fechaGeneradoString;
    private String fechaEmisionString;
    private Integer puntosAcreditados;

    public String getFechaGeneradoString() {
        if (fechaGenerado != null) {
            fechaGeneradoString = UtilCommon.dateFormat(fechaGenerado, UtilCommon.FORMATO_FECHA_DIA_MES_ANIO);
        }
        return fechaGeneradoString;
    }

    public String getFechaEmisionString() {
        if (fechaEmision != null) {
            fechaEmisionString = UtilCommon.dateFormat(fechaEmision, UtilCommon.FORMATO_FECHA_DIA_MES_ANIO);
        }
        return fechaEmisionString;
    }
}
