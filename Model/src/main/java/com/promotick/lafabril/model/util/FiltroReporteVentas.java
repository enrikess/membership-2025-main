package com.promotick.lafabril.model.util;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
public class FiltroReporteVentas implements Serializable {

    private static final long serialVersionUID = 5478187310985522526L;
    private String finicio;
    private String ffin;
    private Integer length;
    private Integer start;
    private String idVentas;
    private String descripcion;
    private String fecha;
    private Double valorVenta;
    private String nroDocumento;
    private Integer idParticipanteVenta;
}
