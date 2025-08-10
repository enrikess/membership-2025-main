package com.promotick.membership.model.web;

import lombok.Data;

import java.io.Serializable;

@Data
public class TipoParticipante implements Serializable {
    private static final long serialVersionUID = -8035153450120395431L;

    private Integer idTipoParticipante;
    private String nombreTipoParticipante;
}
