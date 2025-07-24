package com.promotick.lafabril.model.web;

import com.promotick.lafabril.model.util.BeanBase;
import lombok.Data;

@Data
public class MundialResumen extends BeanBase {

    private static final long serialVersionUID = 1906745071212444225L;

    private Integer cantidadTriviasExitosas;
    private Integer cantidadPuntosMundial;
    private Integer cantidadPronosticos;
    private String categoriaParticipante;
    private Integer cantidadPuntosNivel;
    private Integer cantidadPuntosRestantes;
    private String categoriaParticipanteSiguiente;
    private Integer cantidadPuntosNivelSiguiente;
    private Boolean pollaVigencia;


}
