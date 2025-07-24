package com.promotick.lafabril.dao.web.impl;

import com.promotick.configuration.utils.dao.DaoBuilder;
import com.promotick.configuration.utils.dao.DaoGenerator;
import com.promotick.lafabril.dao.utils.ConstantesWebDAO;
import com.promotick.lafabril.dao.web.FestividadesDao;
import com.promotick.lafabril.dao.web.definition.FestividadesDaoDefinition;
import com.promotick.lafabril.common.ConstantesCommon;
import com.promotick.lafabril.model.web.Festividades;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class FestividadesDaoImpl extends DaoGenerator implements FestividadesDao {

    private FestividadesDaoDefinition festividadesDaoDefinition;

    @Autowired
    public FestividadesDaoImpl(JdbcTemplate jdbcTemplate, FestividadesDaoDefinition festividadesDaoDefinition) {
        super(jdbcTemplate);
        this.festividadesDaoDefinition = festividadesDaoDefinition;
    }

    @Override
    public List<Festividades> obtenerFestividades() {
        return DaoBuilder.getInstance(this)
                .setLogger(ConstantesCommon.LOGGER)
                .setSchema(ConstantesWebDAO.SCHEMA_NAME)
                .setProcedureName(ConstantesWebDAO.SP_LISTAR_FESTIVIDADES)
                .setDaoDefinition(festividadesDaoDefinition)
                .build();
    }
}
