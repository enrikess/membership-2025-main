package com.promotick.lafabril.model.encuesta;

import lombok.Data;

import java.io.Serializable;

@Data
public class Pregunta implements Serializable {
    private static final long serialVersionUID = -3042838781251072629L;

    private Integer idPregunta;
    private Integer idEncuesta;
    private String textoPregunta;
}
