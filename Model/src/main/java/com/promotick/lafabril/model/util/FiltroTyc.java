package com.promotick.lafabril.model.util;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
public class FiltroTyc implements Serializable {

    private static final long serialVersionUID = 5478187310985522526L;
    private Integer idTyc;
    private Integer idCatalogo;
    private String listaId;
    private String usuarioCreacion;
    private String descripcion;
    private String operacion;
}
