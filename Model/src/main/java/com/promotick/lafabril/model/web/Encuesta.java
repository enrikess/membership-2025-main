package com.promotick.lafabril.model.web;

import com.promotick.lafabril.model.util.BeanBase;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class Encuesta extends BeanBase implements Serializable {
    private static final long serialVersionUID = 4228548446093348329L;

    private Integer idEncuesta;
    private Integer idTipoEncuesta;
    private Participante participante;
    private Pedido pedido;
    private Integer estadoEncuesta;
    private List<EncuestaDetalle> detalles;

    //no persiste
    private String nombreTipoEncuesta;
}
