package com.promotick.lafabril.dao.web.definition;

import com.promotick.configuration.utils.dao.DaoDefinition;
import com.promotick.lafabril.model.web.CapacitacionParticipanteDetalle;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class CapacitacionParticipanteDetalleDaoDefinition extends DaoDefinition<CapacitacionParticipanteDetalle> {

    private final CapacitacionPreguntaDaoDefinition capacitacionPreguntaDaoDefinition;
    private final CapacitacionRespuestaDaoDefinition capacitacionRespuestaDaoDefinition;


    public CapacitacionParticipanteDetalleDaoDefinition(CapacitacionPreguntaDaoDefinition capacitacionPreguntaDaoDefinition, CapacitacionRespuestaDaoDefinition capacitacionRespuestaDaoDefinition) {
        super(CapacitacionParticipanteDetalle.class);
        this.capacitacionPreguntaDaoDefinition = capacitacionPreguntaDaoDefinition;
        this.capacitacionRespuestaDaoDefinition = capacitacionRespuestaDaoDefinition;
    }

    @Override
    public CapacitacionParticipanteDetalle mapRow(@NonNull ResultSet rs, int rowNumber) throws SQLException {
        CapacitacionParticipanteDetalle detalle = super.mapRow(rs, rowNumber);
        if (detalle != null) {
            detalle.setCapacitacionPregunta(capacitacionPreguntaDaoDefinition.mapRow(rs, rowNumber));
            detalle.setCapacitacionRespuesta(capacitacionRespuestaDaoDefinition.mapRow(rs, rowNumber));
        }
        return detalle;
    }
}
