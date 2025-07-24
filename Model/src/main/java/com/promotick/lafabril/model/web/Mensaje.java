package com.promotick.lafabril.model.web;

import lombok.Data;

import java.io.Serializable;

@Data
public class Mensaje implements Serializable {
    private static final long serialVersionUID = 2491396533924662066L;

    private Integer idSmsMensaje;
    private String mensaje;
    private boolean estado;
    private Integer tipoCarga;
}
