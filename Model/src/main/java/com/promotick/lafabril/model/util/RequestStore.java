package com.promotick.lafabril.model.util;

import com.promotick.lafabril.model.web.Participante;

public class RequestStore {
    private Participante participante;

    public Participante getParticipante() {
        return participante;
    }

    public RequestStore setParticipante(Participante participante) {
        this.participante = participante;
        return this;
    }
}
