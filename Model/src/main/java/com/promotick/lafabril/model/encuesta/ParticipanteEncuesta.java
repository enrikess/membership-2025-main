package com.promotick.lafabril.model.encuesta;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class ParticipanteEncuesta implements Serializable {
    private static final long serialVersionUID = 3637809312099616846L;

    private Integer idParticipanteEncuesta;
    private Integer idParticipante;
    private Integer idEncuesta;
    private Integer idCampania;
    private List<ParticipanteEncuestaDetalle> detalles;
}
