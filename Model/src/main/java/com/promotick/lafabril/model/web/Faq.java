package com.promotick.lafabril.model.web;

import com.promotick.lafabril.model.util.BeanBase;
import lombok.Data;

@Data
public class Faq extends BeanBase {

    private static final long serialVersionUID = -8768369964834990576L;

    private Integer idFaq;
    private String pregunta;
    private String respuesta;
    private Integer estadoFaq;
    private Integer orden;
    private Catalogo catalogo;

}
