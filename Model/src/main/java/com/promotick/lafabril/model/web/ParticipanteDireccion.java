package com.promotick.lafabril.model.web;

import lombok.Data;

import java.io.Serializable;

@Data
public class ParticipanteDireccion implements Serializable {

    private static final long serialVersionUID = 5456646528882234776L;
    private Integer idParticipanteDireccion;
    private Integer idParticipante;
    private Direccion direccion;
    private String tagDireccion;
}
