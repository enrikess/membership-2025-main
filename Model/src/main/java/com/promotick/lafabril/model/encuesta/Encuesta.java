package com.promotick.lafabril.model.encuesta;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class Encuesta implements Serializable {
    private static final long serialVersionUID = -1425051901213551289L;

    private Integer idEncuesta;
    private String nombreEncuesta;
    private Boolean estadoEncuesta;
    private List<Pregunta> preguntas;
}
