package com.promotick.lafabril.model.web;

import com.promotick.lafabril.model.facturacion.Factura;
import com.promotick.lafabril.model.util.BeanBase;
import com.promotick.lafabril.common.UtilCommon;
import lombok.Data;

import java.util.Date;

@Data
public class ParticipanteTransaccion extends BeanBase {
    private static final long serialVersionUID = 1449365964776199821L;

    private Integer idParticipanteTransaccion;
    private Participante participante;
    private Integer valorPuntos;
    private Integer saldoPuntos;
    private String descripcion;
    private Integer idEntidad;
    private Integer idOrigen;
    private Integer idEstadoTransaccion;
    private Date fechaOperacion;
    private Factura factura;

    private String fechaOperacionString;

    public String getFechaOperacionString() {
        if (fechaOperacion != null) {
            fechaOperacionString = UtilCommon.dateFormat(fechaOperacion, UtilCommon.FORMATO_FECHA_DIA_MES_ANIO);
        }
        return fechaOperacionString;
    }
}
