package com.promotick.membership.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class Mision {
    @JsonProperty("mi_id_mision")
    private long idMision;

    @JsonProperty("mi_titulo")
    private String titulo;

    @JsonProperty("mi_descripcion")
    private String descripcion;

    @JsonProperty("mi_fecha_registro_inicio")
    private String fechaRegistroInicio;

    @JsonProperty("mi_fecha_registro_fin")
    private String fechaRegistroFin;

    @JsonProperty("mi_duracion_dias_mision")
    private int duracionDiasMision;

    @JsonProperty("mi_estado")
    private int estado;

    @JsonProperty("mi_objetivo")
    private String objetivo;

    @JsonProperty("mi_unidad_medida")
    private String unidadMedida;

    @JsonProperty("mi_tipo_mision")
    private String tipoMision;

    @JsonProperty("mi_fecha_creacion")
    private String fechaCreacion;

    @JsonProperty("mi_usuario_creacion")
    private String usuarioCreacion;

    @JsonProperty("recompensas")
    private List<Recompensa> recompensas;
}
