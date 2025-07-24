package com.promotick.lafabril.model.encuesta;

import lombok.Data;

import java.io.Serializable;

@Data
public class ParticipanteData implements Serializable {
    private static final long serialVersionUID = -4088499944506999105L;

    private Integer idParticipanteData;
    private String nombres;
}
