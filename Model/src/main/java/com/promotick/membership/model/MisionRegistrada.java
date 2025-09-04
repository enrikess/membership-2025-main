package com.promotick.membership.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class MisionRegistrada {
    @JsonProperty("mu_id_usuario")
    private long idUsuario;
    
    @JsonProperty("mu_id_mision")
    private long idMision;
    
    @JsonProperty("mu_fecha_registro")
    private LocalDateTime fechaRegistro;
    
    @JsonProperty("mu_estado_registro")
    private int estadoRegistro;

    @JsonProperty("mu_progreso")
    private double progreso;
    
    @JsonProperty("mu_fecha_notificacion")
    private LocalDateTime fechaNotificacion;
    
    @JsonProperty("mu_fecha_creacion")
    private LocalDateTime fechaCreacion;
    
    @JsonProperty("mu_usuario_creacion")
    private String usuarioCreacion;
    
    @JsonProperty("mu_estado")
    private int estado;
}
