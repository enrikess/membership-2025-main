package com.promotick.lafabril.dao.web.impl;

import com.promotick.configuration.utils.dao.DaoBuilder;
import com.promotick.configuration.utils.dao.DaoGenerator;
import com.promotick.lafabril.common.ConstantesCommon;
import com.promotick.lafabril.dao.utils.ConstantesWebDAO;
import com.promotick.lafabril.dao.web.PetshopDao;
import com.promotick.lafabril.dao.web.definition.PetshopDaoDefinition;
import com.promotick.lafabril.model.web.Petshop;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class PetshopDaoImpl extends DaoGenerator implements PetshopDao {

    private final PetshopDaoDefinition petshopDaoDefinition;

    public PetshopDaoImpl(JdbcTemplate jdbcTemplate, PetshopDaoDefinition petshopDaoDefinition) {
        super(jdbcTemplate);
        this.petshopDaoDefinition = petshopDaoDefinition;
    }

    @Override
    public List<Petshop> petshopListar() {
        return DaoBuilder.getInstance(this)
                .setLogger(ConstantesCommon.LOGGER)
                .setSchema(ConstantesWebDAO.SCHEMA_NAME)
                .setProcedureName(ConstantesWebDAO.SP_PETSHOP_LISTAR)
                .setDaoDefinition(petshopDaoDefinition)
                .build();
    }
}
