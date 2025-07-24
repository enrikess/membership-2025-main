package com.promotick.lafabril.dao.web.definition;

import com.promotick.lafabril.model.web.Recomendado;
import com.promotick.configuration.utils.dao.DaoDefinition;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class RecomendadoDaoDefinition extends DaoDefinition<Recomendado> {

    private AuditoriaDaoDefinition auditoriaDaoDefinition;
    private ParticipanteDaoDefinition participanteDaoDefinition;

    public RecomendadoDaoDefinition(AuditoriaDaoDefinition auditoriaDaoDefinition, ParticipanteDaoDefinition participanteDaoDefinition) {
        super(Recomendado.class);
        this.auditoriaDaoDefinition = auditoriaDaoDefinition;
        this.participanteDaoDefinition = participanteDaoDefinition;
    }


    public Recomendado mapRow(ResultSet rs, int rowNumber) throws SQLException {
        Recomendado recomendado = super.mapRow(rs, rowNumber);
        if (recomendado != null){
            recomendado.setAuditoria(auditoriaDaoDefinition.mapRow(rs, rowNumber));
            recomendado.setParticipante(participanteDaoDefinition.mapRow(rs, rowNumber));
        }
        return recomendado;
    }
}
