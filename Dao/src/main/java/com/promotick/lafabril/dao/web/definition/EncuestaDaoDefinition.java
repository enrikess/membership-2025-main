package com.promotick.lafabril.dao.web.definition;

import com.promotick.configuration.utils.dao.DaoDefinition;
import com.promotick.lafabril.model.web.Encuesta;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;

@Repository
public class EncuestaDaoDefinition extends DaoDefinition<Encuesta> {

    private PedidoDaoDefinition pedidoDaoDefinition;
    private ParticipanteDaoDefinition participanteDaoDefinition;
    @Autowired
    public EncuestaDaoDefinition(ParticipanteDaoDefinition participanteDaoDefinition,PedidoDaoDefinition pedidoDaoDefinition) {
        super(Encuesta.class);
        this.pedidoDaoDefinition = pedidoDaoDefinition;
        this.participanteDaoDefinition = participanteDaoDefinition;

    }

    @Override
    public Encuesta mapRow(ResultSet rs, int rowNumber) throws SQLException {
        Encuesta encuesta = super.mapRow(rs, rowNumber);
        if (encuesta != null) {
            encuesta.setPedido(pedidoDaoDefinition.mapRow(rs, rowNumber));
            encuesta.setParticipante(participanteDaoDefinition.mapRow(rs, rowNumber));
        }
        return encuesta;
    }
}
