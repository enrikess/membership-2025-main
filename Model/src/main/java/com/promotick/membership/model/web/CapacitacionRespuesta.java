package com.promotick.membership.model.web;

import com.promotick.membership.model.util.BeanBase;
import lombok.Data;

@Data
public class CapacitacionRespuesta extends BeanBase {
    private static final long serialVersionUID = -4499265648100452259L;

    private Integer idCapacitacionRespuesta;
    private Integer idCapacitacion;
    private Integer idCapacitacionPregunta;
    private String respuesta;
    private Boolean esCorrecta;
    private Boolean estadoCapacitacionRespuesta;
    private Integer ordenRespuesta;

    //temp
    private CapacitacionParticipanteDetalle detalleResuelto;
    private Integer idTipoPregunta;

}
