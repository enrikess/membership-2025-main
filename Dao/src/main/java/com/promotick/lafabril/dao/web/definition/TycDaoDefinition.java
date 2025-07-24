package com.promotick.lafabril.dao.web.definition;

import com.promotick.lafabril.model.web.Tyc;
import com.promotick.configuration.utils.dao.DaoDefinition;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;

@Repository("TycDaoDefinition")
public class TycDaoDefinition extends DaoDefinition<Tyc> {

    private CatalogoDaoDefinition catalogoDaoDefinition;
    private AuditoriaDaoDefinition auditoriaDaoDefinition;

    public TycDaoDefinition(CatalogoDaoDefinition catalogoDaoDefinition, AuditoriaDaoDefinition auditoriaDaoDefinition) {
        super(Tyc.class);
        this.catalogoDaoDefinition = catalogoDaoDefinition;
        this.auditoriaDaoDefinition = auditoriaDaoDefinition;
    }

    @Override
    public Tyc mapRow(ResultSet rs, int rowNumber) throws SQLException {
        Tyc tyc = super.mapRow(rs, rowNumber);
        tyc.setCatalogo(catalogoDaoDefinition.mapRow(rs, rowNumber));
        tyc.setAuditoria(auditoriaDaoDefinition.mapRow(rs, rowNumber));
        return tyc;
    }

}


