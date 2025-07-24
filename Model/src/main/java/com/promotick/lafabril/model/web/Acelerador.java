package com.promotick.lafabril.model.web;

import com.promotick.lafabril.model.util.BeanBase;
import lombok.Data;

@Data
public class Acelerador extends BeanBase {

    private static final long serialVersionUID = -8768369964834990576L;

    private Integer idAcelerador;
    private Catalogo catalogo;
    private String imagen;
    private String texto;
}
