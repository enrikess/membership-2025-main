package com.promotick.lafabril.model.web;

import com.promotick.lafabril.model.util.BeanBase;
import lombok.Data;

@Data
public class EncuestaDetalle extends BeanBase {
    private static final long serialVersionUID = -3428348088085988615L;
    private Integer idEncuestaDetalle;
    private Encuesta encuesta;
    private String pregunta;
    private String respuesta;
    private Integer estadoEncuestaDetalle;
}
