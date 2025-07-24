package com.promotick.lafabril.dao.web.definition;

import com.promotick.lafabril.model.configuracion.ConfiguracionWeb;
import com.promotick.configuration.utils.dao.DaoDefinition;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;

@Repository
public class ConfiguracionWebDaoDefinition extends DaoDefinition<ConfiguracionWeb> {
    public ConfiguracionWebDaoDefinition() {
        super(ConfiguracionWeb.class);
    }

    @Override
    public ConfiguracionWeb mapRow(ResultSet rs, int rowNumber) throws SQLException {
        ConfiguracionWeb configuracionWeb = super.mapRow(rs, rowNumber);
        return configuracionWeb;
    }
}
