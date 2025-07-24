package com.promotick.lafabril.dao.web.definition;

import com.promotick.lafabril.model.web.PedidoDetalle;
import com.promotick.configuration.utils.dao.DaoDefinition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class PedidoDetalleDaoDefinition extends DaoDefinition<PedidoDetalle> {

    private ProductoDaoDefinition productoDaoDefinition;
    private AuditoriaDaoDefinition auditoriaDaoDefinition;

    @Autowired
    public PedidoDetalleDaoDefinition(ProductoDaoDefinition productoDaoDefinition, AuditoriaDaoDefinition auditoriaDaoDefinition) {
        super(PedidoDetalle.class);
        this.productoDaoDefinition = productoDaoDefinition;
        this.auditoriaDaoDefinition = auditoriaDaoDefinition;
    }

    @Override
    public PedidoDetalle mapRow(ResultSet rs, int rowNumber) throws SQLException {
        PedidoDetalle pedidoDetalle = super.mapRow(rs, rowNumber);
        pedidoDetalle.setProducto(productoDaoDefinition.mapRow(rs, rowNumber));
        pedidoDetalle.setAuditoria(auditoriaDaoDefinition.mapRow(rs, rowNumber));
        return pedidoDetalle;
    }
}
