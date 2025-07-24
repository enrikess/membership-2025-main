package com.promotick.lafabril.model.web;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.promotick.lafabril.model.util.BeanBase;
import lombok.Data;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Capacitacion extends BeanBase {
    private static final long serialVersionUID = 7692632419505971742L;

    private Integer idCapacitacion;
    private String nombreCapacitacion;
    private String descripcionCapacitacion;
    private Date fechaInicio;
    private Date fechaFin;
    private String imagenUno;
    private String imagenDetalle;
    private Boolean estadoCapacitacion;
    private Boolean estadoCuestionario;
    private List<CapacitacionRecurso> recursos = new ArrayList<>();
    private List<CapacitacionPregunta> preguntas = new ArrayList<>();
    private Integer idCatalogo;
    private Integer puntosCapacitacion;

    //Temp
    private String fechaInicioString;
    private String fechaFinString;
    private String disponibleHasta;
    private Integer conteoPreguntas;
    private Integer conteoRecursos;
    private Boolean resuelto;
    private String estadoCapacitacionString;
    private String nombreCatalogo;
}
