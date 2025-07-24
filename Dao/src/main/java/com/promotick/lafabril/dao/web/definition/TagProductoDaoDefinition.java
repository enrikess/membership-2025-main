package com.promotick.lafabril.dao.web.definition;

import com.promotick.configuration.utils.dao.DaoDefinition;
import com.promotick.lafabril.model.web.TagProducto;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;

@Repository
public class TagProductoDaoDefinition extends DaoDefinition<TagProducto> {

    public TagProductoDaoDefinition() {
        super(TagProducto.class);
    }

    @Override
    public TagProducto mapRow(ResultSet rs, int rowNumber) throws SQLException {
        TagProducto tagProducto = super.mapRow(rs, rowNumber);
        return tagProducto;
    }
}
