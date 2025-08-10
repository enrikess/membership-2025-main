package com.promotick.membership.model.web;

import lombok.Data;

import java.io.Serializable;

@Data
public class CapacitacionParticipanteDetalle implements Serializable {
    private static final long serialVersionUID = 691922157224990113L;

    private Integer idCapacitacionParticipanteDetalle;
    private Integer idCapacitacionParticipantePregunta;
    private CapacitacionRespuesta capacitacionRespuesta;
    private Boolean esCorrecta;
    //
    private CapacitacionPregunta capacitacionPregunta;
}
