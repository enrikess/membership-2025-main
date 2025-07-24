package com.promotick.lafabril.dao.web.definition;

import com.promotick.configuration.utils.dao.DaoDefinition;
import com.promotick.lafabril.model.web.ParticipanteDireccion;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class ParticipanteDireccionDaoDefinition extends DaoDefinition<ParticipanteDireccion> {

    private final DireccionDaoDefinition direccionDaoDefinition;

    public ParticipanteDireccionDaoDefinition(DireccionDaoDefinition direccionDaoDefinition) {
        super(ParticipanteDireccion.class);
        this.direccionDaoDefinition = direccionDaoDefinition;
    }

    @Override
    public ParticipanteDireccion mapRow(@NonNull ResultSet rs, int rowNumber) throws SQLException {
        ParticipanteDireccion participanteDireccion = super.mapRow(rs, rowNumber);
        if (participanteDireccion != null) {
            participanteDireccion.setDireccion(direccionDaoDefinition.mapRow(rs, rowNumber));
        }
        return participanteDireccion;
    }
}
