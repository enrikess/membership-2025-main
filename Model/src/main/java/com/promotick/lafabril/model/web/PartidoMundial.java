package com.promotick.lafabril.model.web;


import com.promotick.lafabril.model.util.BeanBase;
import lombok.Data;

import java.util.Date;

@Data
public class PartidoMundial extends BeanBase {

    private static final long serialVersionUID = 1L;

    private Integer idPartidoMundial;
    private String descripcionPartido;
    private String codigoPartido;
    private Integer idPaisMundial1;
    private String nombrePaisMundial1;
    private String imagenPaisMundial1;
    private Integer idPaisMundial2;
    private String nombrePaisMundial2;
    private String imagenPaisMundial2;
    private Date fechaInicio;
    private Date fechaFin;
    private Date fechaPartido;
    private String fechaPartidoFormat;
    private String anio;
    private String dia;
    private String mes;
    private String partidoEstado;
    private String orden;
    private String etapaPartido;
    private String grupoPartido;

    private String fechaPartidoCodigo;
    private String respuesta;
    private String scorePais1;
    private String scorePais2;
    private Integer indice;


}
