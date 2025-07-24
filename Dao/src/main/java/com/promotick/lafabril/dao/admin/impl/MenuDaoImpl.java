package com.promotick.lafabril.dao.admin.impl;

import com.promotick.lafabril.dao.admin.MenuDao;
import com.promotick.lafabril.dao.admin.definition.MenuDaoDefinition;
import com.promotick.lafabril.dao.utils.ConstantesAdminDAO;
import com.promotick.lafabril.common.ConstantesCommon;
import com.promotick.lafabril.model.admin.Menu;
import com.promotick.configuration.utils.dao.DaoBuilder;
import com.promotick.configuration.utils.dao.DaoGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.Types;
import java.util.List;

@Repository
public class MenuDaoImpl extends DaoGenerator implements MenuDao {

    private MenuDaoDefinition menuDaoDefinition;

    @Autowired
    public MenuDaoImpl(JdbcTemplate jdbcTemplate, MenuDaoDefinition menuDaoDefinition) {
        super(jdbcTemplate);
        this.menuDaoDefinition = menuDaoDefinition;
    }


    @Override
    public List<Menu> listarMenuPorRol(Integer idRol) {
        return DaoBuilder.getInstance(this)
                .setLogger(ConstantesCommon.LOGGER)
                .setSchema(ConstantesAdminDAO.SCHEMA_NAME)
                .setProcedureName(ConstantesAdminDAO.SP_MENU_LISTAR_POR_IDROL)
                .addParameter("var_id_rol", Types.INTEGER, idRol)
                .setDaoDefinition(menuDaoDefinition)
                .build();
    }

    @Override
    public List<Menu> listarMenu() {
        return DaoBuilder.getInstance(this)
                .setLogger(ConstantesCommon.LOGGER)
                .setSchema(ConstantesAdminDAO.SCHEMA_NAME)
                .setProcedureName(ConstantesAdminDAO.SP_MENU_LISTAR)
                .setDaoDefinition(menuDaoDefinition)
                .build();
    }


}
