package com.promotick.lafabril.model.util;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
public class FiltroAcciones implements Serializable {

    private static final long serialVersionUID = 5478187310985522526L;
    private Integer idLafabrilProducto;
    private String descripcion;
    private Integer cantidad;
    private String accion;
    private String fecha;
}
