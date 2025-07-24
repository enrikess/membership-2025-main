package com.promotick.lafabril.model.util;

import com.promotick.lafabril.model.web.Participante;
import lombok.Data;

import java.io.Serializable;

@Data
public class FiltroParticipanteTransaccion implements Serializable {
    private static final long serialVersionUID = -8344800847471840465L;
    private Integer start;
    private Integer length;
    private Integer tipo;
    private String finicio;
    private String ffin;
    private Participante participante;
}
