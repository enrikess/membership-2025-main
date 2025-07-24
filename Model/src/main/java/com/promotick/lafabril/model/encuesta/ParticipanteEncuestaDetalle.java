package com.promotick.lafabril.model.encuesta;

import lombok.Data;

import java.io.Serializable;

@Data
public class ParticipanteEncuestaDetalle implements Serializable {

    private static final long serialVersionUID = -6905136687208889905L;

    private Integer idParticipanteEncuestaDetalle;
    private Integer idParticipanteEncuesta;
    private String textoPregunta;
    private String textoRespuesta;
}
