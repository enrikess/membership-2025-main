package com.promotick.lafabril.model.util;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
public class FiltroFaq implements Serializable {

    private static final long serialVersionUID = 5478187310985522526L;
    private Integer idFaq;
    private Integer idCatalogo;
    private String listaId;
    private String usuarioCreacion;
    private String respuesta;
    private String pregunta;
    private String operacion;
    private Integer orden;
}
