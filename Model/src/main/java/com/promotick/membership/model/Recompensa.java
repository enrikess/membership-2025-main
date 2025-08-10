package com.promotick.membership.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class Recompensa {
    @JsonProperty("re_id_recompensa")
    private long idRecompensa;

    @JsonProperty("re_titulo")
    private String titulo;

    @JsonProperty("re_descripcion")
    private String descripcion;

    @JsonProperty("re_cantidad")
    private double cantidad;

    @JsonProperty("re_id_unidad")
    private long idUnidad;

    @JsonProperty("re_id_estado")
    private long idEstado;

    @JsonProperty("re_fecha_creacion")
    private String fechaCreacion;

    @JsonProperty("re_usuario_creacion")
    private String usuarioCreacion;

    @JsonProperty("ur_id_estado_seleccion")
    private Integer idEstadoSeleccion;
}
