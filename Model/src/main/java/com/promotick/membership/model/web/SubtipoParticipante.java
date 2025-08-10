package com.promotick.membership.model.web;

import lombok.Data;

import java.io.Serializable;

@Data
public class SubtipoParticipante implements Serializable {
    private static final long serialVersionUID = 5067814348148495877L;

    private Integer idSubtipoParticipante;
    private String nombreSubtipoParticipante;
    private Integer estadoSubtipoParticipante;
}
