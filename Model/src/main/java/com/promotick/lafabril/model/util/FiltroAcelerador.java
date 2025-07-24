package com.promotick.lafabril.model.util;

import lombok.Data;

import java.io.Serializable;

@Data
public class FiltroAcelerador implements Serializable {

    private static final long serialVersionUID = -2399755164555254417L;
    private Integer idParticipante;
    private Integer idAcelerador;
    private String titulo;
    private String imagen;
    private Integer idCatalogo;
    private String operacion;
    private String listaId;
    private String usuarioCreacion;
}
