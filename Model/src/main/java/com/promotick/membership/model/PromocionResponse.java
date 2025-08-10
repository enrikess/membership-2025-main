package com.promotick.membership.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class PromocionResponse {
    private String traceid;
    private boolean success;
    private boolean collection;
    private int count;
    private String error;

    @JsonProperty("data")
    private List<Promocion> promociones;
}
