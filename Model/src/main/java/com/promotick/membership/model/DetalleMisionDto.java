package com.promotick.membership.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DetalleMisionDto {
    private long idMision;
    private String descripcion;
    private String fechaFin;
    private boolean registrada;
    private List<Recompensa> recompensas;
    private double progreso;
}
