package com.promotick.lafabril.dao.web.definition;

import com.promotick.lafabril.dao.facturacion.definition.FacturaDaoDefinition;
import com.promotick.lafabril.model.web.ParticipanteTransaccion;
import com.promotick.configuration.utils.dao.DaoDefinition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class ParticipanteTransaccionDaoDefinition extends DaoDefinition<ParticipanteTransaccion> {

    private ParticipanteDaoDefinition participanteDaoDefinition;
    private AuditoriaDaoDefinition auditoriaDaoDefinition;
    private FacturaDaoDefinition facturaDaoDefinition;

    @Autowired
    public ParticipanteTransaccionDaoDefinition(ParticipanteDaoDefinition participanteDaoDefinition, AuditoriaDaoDefinition auditoriaDaoDefinition, FacturaDaoDefinition facturaDaoDefinition) {
        super(ParticipanteTransaccion.class);
        this.participanteDaoDefinition = participanteDaoDefinition;
        this.auditoriaDaoDefinition = auditoriaDaoDefinition;
        this.facturaDaoDefinition = facturaDaoDefinition;
    }

    @Override
    public ParticipanteTransaccion mapRow(ResultSet rs, int rowNumber) throws SQLException {
        ParticipanteTransaccion participanteTransaccion = super.mapRow(rs, rowNumber);
        if (participanteTransaccion != null) {
            participanteTransaccion.setAuditoria(auditoriaDaoDefinition.mapRow(rs, rowNumber));
            participanteTransaccion.setParticipante(participanteDaoDefinition.mapRow(rs, rowNumber));
            participanteTransaccion.setFactura(facturaDaoDefinition.mapRow(rs, rowNumber));
        }
        return participanteTransaccion;
    }
}
