package com.promotick.lafabril.dao.web.definition;

import com.promotick.configuration.utils.dao.DaoDefinition;
import com.promotick.lafabril.model.web.Acelerador;
import com.promotick.lafabril.model.web.Faq;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;

@Repository
public class AceleradorDaoDefinition extends DaoDefinition<Acelerador> {
    private CatalogoDaoDefinition catalogoDaoDefinition;

    public AceleradorDaoDefinition(CatalogoDaoDefinition catalogoDaoDefinition) {
        super(Acelerador.class);
        this.catalogoDaoDefinition = catalogoDaoDefinition;
    }

    @Override
    public Acelerador mapRow(ResultSet rs, int rowNumber) throws SQLException {
        Acelerador acelerador = super.mapRow(rs, rowNumber);
        acelerador.setCatalogo(catalogoDaoDefinition.mapRow(rs, rowNumber));
        return acelerador;
    }

}


