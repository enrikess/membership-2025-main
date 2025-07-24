package com.promotick.lafabril.model.encuesta;

import lombok.Data;

import java.io.Serializable;

@Data
public class Campania implements Serializable {
    private static final long serialVersionUID = -1862318654846482164L;
    private Integer idCampania;
    private Encuesta encuesta;
    private String nombreCampania;
    private String fechaInicioString;
    private String fechaFinString;
    private Boolean estadoCampania;
}
