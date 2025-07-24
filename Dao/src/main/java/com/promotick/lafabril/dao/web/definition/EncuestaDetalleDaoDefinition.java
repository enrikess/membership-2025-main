package com.promotick.lafabril.dao.web.definition;

import com.promotick.configuration.utils.dao.DaoDefinition;
import com.promotick.lafabril.model.web.EncuestaDetalle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;

@Repository
public class EncuestaDetalleDaoDefinition extends DaoDefinition<EncuestaDetalle> {

    private EncuestaDaoDefinition encuestaDaoDefinition;
    private AuditoriaDaoDefinition auditoriaDaoDefinition;
    @Autowired
    public EncuestaDetalleDaoDefinition(EncuestaDaoDefinition encuestaDaoDefinition,AuditoriaDaoDefinition auditoriaDaoDefinition) {
        super(EncuestaDetalle.class);
        this.encuestaDaoDefinition = encuestaDaoDefinition;
        this.auditoriaDaoDefinition = auditoriaDaoDefinition;

    }

    @Override
    public EncuestaDetalle mapRow(ResultSet rs, int rowNumber) throws SQLException {
        EncuestaDetalle encuesta = super.mapRow(rs, rowNumber);
        if (encuesta != null) {
            encuesta.setEncuesta(encuestaDaoDefinition.mapRow(rs, rowNumber));
            encuesta.setAuditoria(auditoriaDaoDefinition.mapRow(rs, rowNumber));
        }
        return encuesta;
    }
}
