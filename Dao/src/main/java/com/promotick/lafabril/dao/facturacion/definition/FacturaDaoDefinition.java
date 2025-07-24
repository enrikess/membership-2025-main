package com.promotick.lafabril.dao.facturacion.definition;

import com.promotick.lafabril.dao.web.definition.ParticipanteDaoDefinition;
import com.promotick.lafabril.model.facturacion.Factura;
import com.promotick.configuration.utils.dao.DaoDefinition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class FacturaDaoDefinition extends DaoDefinition<Factura> {

    private TipoFacturaDaoDefinition tipoFacturaDaoDefinition;
    private ParticipanteDaoDefinition participanteDaoDefinition;
    private PeriodoParticipanteDaoDefinition periodoParticipanteDaoDefinition;

    @Autowired
    public FacturaDaoDefinition(TipoFacturaDaoDefinition tipoFacturaDaoDefinition, ParticipanteDaoDefinition participanteDaoDefinition, PeriodoParticipanteDaoDefinition periodoParticipanteDaoDefinition) {
        super(Factura.class);
        this.tipoFacturaDaoDefinition = tipoFacturaDaoDefinition;
        this.participanteDaoDefinition = participanteDaoDefinition;
        this.periodoParticipanteDaoDefinition = periodoParticipanteDaoDefinition;
    }

    @Override
    public Factura mapRow(ResultSet rs, int rowNumber) throws SQLException {
        Factura factura = super.mapRow(rs, rowNumber);
        if (factura != null) {
            factura.setParticipante(participanteDaoDefinition.mapRow(rs, rowNumber));
            factura.setTipoFactura(tipoFacturaDaoDefinition.mapRow(rs, rowNumber));
            factura.setPeriodoParticipante(periodoParticipanteDaoDefinition.mapRow(rs, rowNumber));
        }
        return factura;
    }
}
