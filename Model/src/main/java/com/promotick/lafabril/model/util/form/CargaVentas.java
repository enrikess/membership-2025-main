package com.promotick.lafabril.model.util.form;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class CargaVentas  implements Serializable {

    private static final long serialVersionUID = 8626876039202294730L;

    private Integer idParticipanteAvance;
    private String nroDocumento;
    private Double valorVenta;
    private Date fechaOperacion;
    private Integer idCarga;
    private Integer idPeriodo;
    private String descripcion;
    private String usuarioCreacion;
    private String listaIds;
    private String operacion;
}
