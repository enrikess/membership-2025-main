package com.promotick.membership.model.web;

import com.promotick.membership.model.util.BeanBase;
import lombok.Data;

@Data
public class Direccion extends BeanBase {

    private static final long serialVersionUID = 8948645213008366186L;

    private Integer idDireccion;
    private String direccionCalle;
    private String referencia;
    private Integer tipo;
    private Ubigeo ubigeo;
    private Zona zona;
    private TipoVivienda tipoVivienda;

    //Temp
    private String nroDocumento;
    private String _strTipoDireccion;
    private Integer _intTipoDireccion;

    private String tipoDireccion;



}
