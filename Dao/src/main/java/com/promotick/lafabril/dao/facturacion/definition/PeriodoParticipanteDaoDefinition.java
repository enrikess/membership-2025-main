package com.promotick.lafabril.dao.facturacion.definition;

import com.promotick.lafabril.dao.web.definition.ParticipanteDaoDefinition;
import com.promotick.lafabril.model.facturacion.PeriodoParticipante;
import com.promotick.configuration.utils.dao.DaoDefinition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class PeriodoParticipanteDaoDefinition extends DaoDefinition<PeriodoParticipante> {

    private PeriodoDaoDefinition periodoDaoDefinition;
    private ParticipanteDaoDefinition participanteDaoDefinition;

    @Autowired
    public PeriodoParticipanteDaoDefinition(PeriodoDaoDefinition periodoDaoDefinition, ParticipanteDaoDefinition participanteDaoDefinition) {
        super(PeriodoParticipante.class);
        this.periodoDaoDefinition = periodoDaoDefinition;
        this.participanteDaoDefinition = participanteDaoDefinition;
    }

    @Override
    public PeriodoParticipante mapRow(ResultSet rs, int rowNumber) throws SQLException {
        PeriodoParticipante periodoParticipante = super.mapRow(rs, rowNumber);
        if (periodoParticipante != null) {
            periodoParticipante.setPeriodo(periodoDaoDefinition.mapRow(rs, rowNumber));
            periodoParticipante.setParticipante(participanteDaoDefinition.mapRow(rs, rowNumber));
        }
        return periodoParticipante;
    }
}
