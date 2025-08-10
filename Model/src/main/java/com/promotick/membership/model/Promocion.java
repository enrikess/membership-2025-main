package com.promotick.membership.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class Promocion {


    @JsonProperty("pr_id_promocion")
    private long idPromocion;

    @JsonProperty("pr_ruc_comercio")
    private String rucComercio;

    @JsonProperty("pr_nombre_comercio")
    private String nombreComercio;

    @JsonProperty("pr_titulo")
    private String titulo;

    @JsonProperty("pr_descripcion")
    private String descripcion;

    @JsonProperty("pr_valido_hasta")
    private String validoHasta; // Se puede usar LocalDateTime si prefieres

    @JsonProperty("pr_fecha_creacion")
    private String fechaCreacion;

    @JsonProperty("pr_usuario_creacion")
    private String usuarioCreacion;

    @JsonProperty("pr_estado")
    private String estado;

    @JsonProperty("pr_id_ciudad")
    private long idCiudad;

    @JsonProperty("pr_id_categoria")
    private long idCategoria;
}
