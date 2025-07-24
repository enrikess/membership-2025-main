package com.promotick.lafabril.model.facturacion;

import com.promotick.lafabril.common.UtilCommon;
import com.promotick.lafabril.model.util.BeanBase;
import com.promotick.lafabril.model.web.Participante;
import lombok.Data;

import java.util.Date;

@Data
public class ParticipanteAvance extends BeanBase {
    private static final long serialVersionUID = -3354905845576819675L;

    private Integer idParticipanteAvance;
    private Integer idCarga;
    private Integer idParticipante;
    private String nroDocumento;
    private Double valorVenta = 0.0;
    private Date fechaOperacion;
    private Date fechaRegistro;
    private Integer estado = 1;
    private String usuarioRegistro;
    private Integer idPeriodo;
    private String descripcion;
    private Integer idProducto;
    private Integer mes;
    private Integer anio;

}
