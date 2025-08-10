package com.promotick.membership.model.web;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.promotick.membership.model.util.BeanBase;
import com.promotick.membership.model.util.enums.TipoPreguntaEnum;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CapacitacionPregunta extends BeanBase {
    private static final long serialVersionUID = 9051903501426633798L;

    private Integer idCapacitacionPregunta;
    private Integer idCapacitacion;
    private Integer idTipoPregunta;
    private String pregunta;
    private Boolean estadoCapacitacionPregunta;
    private Integer ordenPregunta;
    private Integer posicion;
    private List<CapacitacionRespuesta> respuestas = new ArrayList<>();

    //temp
    private boolean preguntaResueltaCorrecto;
    private Integer conteoRespuestas;

    public TipoPreguntaEnum getTipoPreguntaEnum() {
        return TipoPreguntaEnum.getByCode(idTipoPregunta);
    }

    public boolean esSeleccionSimple() {
        return getTipoPreguntaEnum().equals(TipoPreguntaEnum.SELECCION_SIMPLE);
    }

    public boolean esSeleccionMultiple() {
        return getTipoPreguntaEnum().equals(TipoPreguntaEnum.SELECCION_MULTIPLE);
    }
}
