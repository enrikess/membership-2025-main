package com.promotick.lafabril.dao.web.definition;

import com.promotick.lafabril.model.web.Faq;
import com.promotick.configuration.utils.dao.DaoDefinition;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;

@Repository
public class FaqDaoDefinition extends DaoDefinition<Faq> {
    private CatalogoDaoDefinition catalogoDaoDefinition;

    public FaqDaoDefinition(CatalogoDaoDefinition catalogoDaoDefinition) {
        super(Faq.class);
        this.catalogoDaoDefinition = catalogoDaoDefinition;
    }

    @Override
    public Faq mapRow(ResultSet rs, int rowNumber) throws SQLException {
        Faq faq = super.mapRow(rs, rowNumber);
        faq.setCatalogo(catalogoDaoDefinition.mapRow(rs, rowNumber));
        return faq;
    }

}


