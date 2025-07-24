package com.promotick.lafabril.model.util;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
public class FiltroVisitas implements Serializable {

    private static final long serialVersionUID = 3879693223936209243L;

    private Integer tipoOperacion;
    private Integer tipoDispositivo;
    private Integer idCategoria;
    private Integer idEntidad;
    private Integer idCatalogo;
    private String finicio;
    private String ffin;
    private Integer length;
    private Integer start;

}
