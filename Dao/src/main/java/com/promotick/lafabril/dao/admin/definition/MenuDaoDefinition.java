package com.promotick.lafabril.dao.admin.definition;

import com.promotick.lafabril.model.admin.Menu;
import com.promotick.configuration.utils.dao.DaoDefinition;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class MenuDaoDefinition extends DaoDefinition<Menu> {

    public MenuDaoDefinition() {
        super(Menu.class);
    }

    @Override
    public Menu mapRow(ResultSet rs, int rowNumber) throws SQLException {
        Menu menu = super.mapRow(rs, rowNumber);
        return menu;
    }

}
