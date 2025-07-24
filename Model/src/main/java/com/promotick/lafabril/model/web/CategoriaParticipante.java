package com.promotick.lafabril.model.web;

import lombok.Data;

import java.io.Serializable;

@Data
public class CategoriaParticipante implements Serializable {
    private static final long serialVersionUID = 5067814348148495877L;

    private Integer idCategoriaParticipante;
    private String nombreCategoriaParticipante;
    private Integer estadoSCategoriaParticipante;
}
