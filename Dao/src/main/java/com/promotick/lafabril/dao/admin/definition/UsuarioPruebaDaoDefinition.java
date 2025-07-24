package com.promotick.lafabril.dao.admin.definition;

import com.promotick.lafabril.dao.web.definition.ParticipanteDaoDefinition;
import com.promotick.lafabril.model.admin.UsuarioPrueba;
import com.promotick.configuration.utils.dao.DaoDefinition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class UsuarioPruebaDaoDefinition extends DaoDefinition<UsuarioPrueba> {

    private ParticipanteDaoDefinition participanteDaoDefinition;

    @Autowired
    public UsuarioPruebaDaoDefinition(ParticipanteDaoDefinition participanteDaoDefinition) {
        super(UsuarioPrueba.class);
        this.participanteDaoDefinition = participanteDaoDefinition;
    }

    @Override
    public UsuarioPrueba mapRow(ResultSet rs, int rowNumber) throws SQLException {
        UsuarioPrueba usuarioPrueba = super.mapRow(rs, rowNumber);
        usuarioPrueba.setParticipante(participanteDaoDefinition.mapRow(rs, rowNumber));
        return usuarioPrueba;
    }
}
