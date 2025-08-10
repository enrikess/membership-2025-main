package com.promotick.membership.model.web;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Log {

    private static final long serialVersionUID = 1L;
    private Long idLogAccion;
    private String usuario;
    private String accion;
    private String detalle;
    private LocalDateTime fecha;
    private String headerJson;
    private String bodyJson;
    private String ip;
    private String ruta;
}