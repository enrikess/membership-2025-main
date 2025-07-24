package com.promotick.lafabril.dao.web.definition;

import com.promotick.lafabril.model.configuracion.ConfiguracionBanner;
import com.promotick.configuration.utils.dao.DaoDefinition;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;

@Repository
public class ConfiguracionBannerDaoDefinition extends DaoDefinition<ConfiguracionBanner> {
    private CatalogoDaoDefinition catalogoDaoDefinition;

    public ConfiguracionBannerDaoDefinition(CatalogoDaoDefinition catalogoDaoDefinition) {
        super(ConfiguracionBanner.class);
        this.catalogoDaoDefinition = catalogoDaoDefinition;
    }

    @Override
    public ConfiguracionBanner mapRow(ResultSet rs, int rowNumber) throws SQLException {
        ConfiguracionBanner configBanner = super.mapRow(rs, rowNumber);
        configBanner.setCatalogo(catalogoDaoDefinition.mapRow(rs, rowNumber));
        return configBanner;
    }
}
