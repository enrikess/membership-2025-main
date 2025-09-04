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
public class IntervaloMisiones {
    private Long id;
    private Long timer; // Intervalo en milisegundos
}