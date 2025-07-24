package com.promotick.lafabril.model.web;

import lombok.Data;

import java.io.Serializable;

@Data
public class CapacitacionParticipantePregunta implements Serializable {
    private static final long serialVersionUID = -5376002325019479375L;

    private Integer idCapacitacionParticipantePregunta;
    private Integer idCapacitacionParticipante;
    private Integer idCapacitacionPregunta;
    private String pregunta;
    private Boolean resultadoPregunta;
}
