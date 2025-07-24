package com.promotick.lafabril.model.web;

import com.promotick.lafabril.model.util.BeanBase;
import lombok.Data;

import java.util.Date;

@Data
public class Campania extends BeanBase {

    private static final long serialVersionUID = -5129288387374452850L;

    private Integer idCampania;
    private String nombreCampania;
    private Date fechaInicio;
    private Date fechaFin;
    private Integer numGanadores;
    private Integer valorPuntos;
    private Integer estadoCampania;

    //no persiste
    private String fechaInicioString;
    private String fechaFinString;


}
