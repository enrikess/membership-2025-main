package com.promotick.lafabril.model.util;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
public class FiltroMetas implements Serializable {

    private static final long serialVersionUID = 3879693223936209243L;

    private Integer trimestre;
    private Integer anio;
    private String numeroDocumento;
    private String finicio;
    private String ffin;
    private String idMetas;
    private Integer length;
    private Integer start;

}
