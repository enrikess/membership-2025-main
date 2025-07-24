package com.promotick.lafabril.dao.web.definition;

import com.promotick.lafabril.model.web.Pedido;
import com.promotick.configuration.utils.dao.DaoDefinition;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class PedidoDaoDefinition extends DaoDefinition<Pedido> {

    private ParticipanteDaoDefinition participanteDaoDefinition;
    private AuditoriaDaoDefinition auditoriaDaoDefinition;
    private DireccionDaoDefinition direccionDaoDefinition;
    private TipoDocumentoDaoDefinition tipoDocumentoDaoDefinition;

    public PedidoDaoDefinition(ParticipanteDaoDefinition participanteDaoDefinition, AuditoriaDaoDefinition auditoriaDaoDefinition, DireccionDaoDefinition direccionDaoDefinition, TipoDocumentoDaoDefinition tipoDocumentoDaoDefinition) {
        super(Pedido.class);
        this.participanteDaoDefinition = participanteDaoDefinition;
        this.auditoriaDaoDefinition = auditoriaDaoDefinition;
        this.direccionDaoDefinition = direccionDaoDefinition;
        this.tipoDocumentoDaoDefinition = tipoDocumentoDaoDefinition;
    }

    @Override
    public Pedido mapRow(ResultSet rs, int rowNumber) throws SQLException {
        Pedido pedido = super.mapRow(rs, rowNumber);
        if (pedido != null) {
            pedido.setParticipante(participanteDaoDefinition.mapRow(rs, rowNumber));
            pedido.setAuditoria(auditoriaDaoDefinition.mapRow(rs, rowNumber));
            pedido.setDireccion(direccionDaoDefinition.mapRow(rs, rowNumber));
            pedido.setTipoDocumento(tipoDocumentoDaoDefinition.mapRow(rs, rowNumber));
        }
        return pedido;
    }
}
