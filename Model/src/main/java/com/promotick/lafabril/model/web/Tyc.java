package com.promotick.lafabril.model.web;

import com.promotick.lafabril.model.util.BeanBase;
import lombok.Data;

@Data
public class Tyc extends BeanBase {

    private static final long serialVersionUID = 8453193382861647945L;

    private Integer idTyc;
    private Catalogo catalogo;
    private String tituloTyc;
    private String descripcionTyc;
    private Integer estado_tyc;
    private Integer orden;

}
