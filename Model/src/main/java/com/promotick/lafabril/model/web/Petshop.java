package com.promotick.lafabril.model.web;

import lombok.Data;

import java.io.Serializable;

@Data
public class Petshop implements Serializable {
    private static final long serialVersionUID = 8555692291619212413L;

    private Integer idPetshop;
    private String nombrePetshop;
    private String estadoPetshop;
}
