package com.promotick.lafabril.model.web;

import com.promotick.lafabril.model.util.BeanBase;
import lombok.Data;

@Data
public class Marca extends BeanBase {

    private static final long serialVersionUID = 9180127928813539034L;

    private Integer idMarca;
    private String nombreMarca;
    private String descripcionCorta;
    private String descripcionLarga;
    private String imagenLogo;
    private Integer estadoMarca;
    private String paginaweb;
    private String codigoMarca;

}
