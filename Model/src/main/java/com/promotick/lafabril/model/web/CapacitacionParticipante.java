package com.promotick.lafabril.model.web;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Data
public class CapacitacionParticipante implements Serializable {
    private static final long serialVersionUID = -2372218015544004826L;

    private Integer idCapacitacionParticipante;
    private Integer idCapacitacion;
    private Integer idParticipante;
    private Integer cantidadPreguntas = 0;
    private Integer cantidadCorrectas = 0;
    private Integer cantidadErroneas = 0;
    private Integer calificacion = 0;
    private Boolean aprobado;
    private String preguntasRaw;
    private List<CapacitacionParticipanteDetalle> detalles = new ArrayList<>();

    //Temp
    private List<CapacitacionPregunta> preguntasRespondidas = new ArrayList<>();

    public void preguntasRawObject(Object obj) {
        try {
            preguntasRaw = new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Optional<Capacitacion> getCapacitacionByRaw() {
        try {
            if (StringUtils.isEmpty(preguntasRaw)) return Optional.empty();
            return Optional.of(
                    new ObjectMapper()
                            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
                            .readValue(preguntasRaw, Capacitacion.class));
        } catch (Exception e) {
            e.printStackTrace();
            return Optional.empty();
        }
    }
}
