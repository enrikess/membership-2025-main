package com.promotick.membership.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class MisionRegistrada {
    @JsonProperty("mu_id_mision")
    private long idMision;

    @JsonProperty("mu_progreso")
    private double progreso;

}
