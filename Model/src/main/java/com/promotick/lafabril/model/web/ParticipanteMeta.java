package com.promotick.lafabril.model.web;

import lombok.Data;

import java.io.Serializable;

@Data
public class ParticipanteMeta implements Serializable {
    private static final long serialVersionUID = -4209624542289560645L;

    private Integer idParticipanteMeta;
    private Integer idCarga;
    private Integer idParticipante;
    private Integer anio;
    private Integer mes;
    private String nroDocumento;
    private Double valorMeta;
    private Boolean estado;
    private String descripcion;

    //Temp
    private String nombreMes;
    private Double avance;

    public Double getDiferencia() {
        if (avance == null || avance == 0) return valorMeta;
        double diferencia = valorMeta - avance;
        return diferencia < 0 ? 0 : diferencia;
    }
}
