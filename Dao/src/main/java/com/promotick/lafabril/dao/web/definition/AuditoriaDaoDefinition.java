package com.promotick.lafabril.dao.web.definition;

import com.promotick.lafabril.model.util.Auditoria;
import com.promotick.configuration.utils.dao.DaoDefinition;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;

@Repository
public class AuditoriaDaoDefinition extends DaoDefinition<Auditoria> {

    public AuditoriaDaoDefinition() {
        super(Auditoria.class);
    }

    @Override
    public Auditoria mapRow(ResultSet rs, int rowNumber) throws SQLException {
        Auditoria auditoria = super.mapRow(rs, rowNumber);
        return auditoria;
    }
}
