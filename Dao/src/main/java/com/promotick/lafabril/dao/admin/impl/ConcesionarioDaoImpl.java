package com.promotick.lafabril.dao.admin.impl;

import com.promotick.lafabril.dao.admin.ConcesionarioDao;
import com.promotick.lafabril.dao.utils.ConstantesAdminDAO;
import com.promotick.lafabril.dao.web.definition.ConcesionarioDaoDefinition;
import com.promotick.configuration.utils.dao.DaoBuilder;
import com.promotick.configuration.utils.dao.DaoGenerator;
import com.promotick.lafabril.common.ConstantesCommon;
import com.promotick.lafabril.model.web.Concesionario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ConcesionarioDaoImpl extends DaoGenerator implements ConcesionarioDao {

    private ConcesionarioDaoDefinition concesionarioDaoDefinition;

    @Autowired
    public ConcesionarioDaoImpl(JdbcTemplate jdbcTemplate, ConcesionarioDaoDefinition concesionarioDaoDefinition) {
        super(jdbcTemplate);
        this.concesionarioDaoDefinition = concesionarioDaoDefinition;
    }

    @Override
    public List<Concesionario> listarConcesionarios() {
        return DaoBuilder.getInstance(this)
                .setLogger(ConstantesCommon.LOGGER)
                .setSchema(ConstantesAdminDAO.SCHEMA_NAME)
                .setProcedureName(ConstantesAdminDAO.SP_CONCESIONARIO_LISTAR)
                .setDaoDefinition(concesionarioDaoDefinition)
                .build();
    }
}
