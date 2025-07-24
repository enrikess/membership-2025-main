package com.promotick.lafabril.model.facturacion;

import com.promotick.lafabril.model.util.BeanBase;
import com.promotick.lafabril.model.web.Participante;
import lombok.Data;

@Data
public class PeriodoParticipante extends BeanBase {
    private static final long serialVersionUID = 1327904541004177430L;

    private Integer idPeriodoParticipante;
    private Periodo periodo;
    private Participante participante;
    private Double periodoMeta;
    private Integer estadoPeriodoParticipante;
    private Boolean emailEnviado;
    private String emailObservacion;

}
