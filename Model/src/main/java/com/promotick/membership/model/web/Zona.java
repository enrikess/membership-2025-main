package com.promotick.membership.model.web;

import lombok.Data;

import java.io.Serializable;

@Data
public class Zona implements Serializable {
    private static final long serialVersionUID = 4228548446093348329L;

    private Integer idZona;
    private String nombreZona;
    private String descripcionZona;
    private Integer estadoZona;
    private Integer ordenZona;

}
