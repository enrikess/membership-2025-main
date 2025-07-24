package com.promotick.lafabril.model.web;

import lombok.Data;

import java.io.Serializable;

@Data
public class Ubigeo implements Serializable {

    private static final long serialVersionUID = -2678790518675837577L;
    private Integer idUbigeo;
    private String codubigeo;
    private String nombreUbigeo;
    private String codpais;
    private String coddep;
    private String codprov;
    private String coddist;
    private Integer estadoUbigeo;
    private Integer orderUbigeo;

    //Temp
    private String departamento;
    private String provincia;
    private String distrito;

}
