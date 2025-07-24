package com.promotick.lafabril.dao.web.definition;

import com.promotick.lafabril.model.web.Categoria;
import com.promotick.configuration.utils.dao.DaoDefinition;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class CategoriaDaoDefinition extends DaoDefinition<Categoria> {

    public CategoriaDaoDefinition() {
        super(Categoria.class);
    }

    @Override
    public Categoria mapRow(ResultSet rs, int rowNumber) throws SQLException {
        Categoria categoria = super.mapRow(rs, rowNumber);
        return categoria;
    }
}
