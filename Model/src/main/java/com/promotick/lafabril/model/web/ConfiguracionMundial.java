package com.promotick.lafabril.model.web;

import com.promotick.lafabril.model.util.BeanBase;
import lombok.Data;

import java.util.Date;

@Data
public class ConfiguracionMundial extends BeanBase {

    private static final long serialVersionUID = -1051612224394753264L;

    private Integer idConfiguracionMundial;
    private Integer duracionPreguntaTrivia;
    private Date fechaInicioPolla;
    private Date fechaFinPolla;
    private String imagenUsuario;
    private String catalogos;
    private Integer puntosTrivia;
}
