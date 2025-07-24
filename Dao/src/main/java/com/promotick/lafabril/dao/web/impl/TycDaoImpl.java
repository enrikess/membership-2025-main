package com.promotick.lafabril.dao.web.impl;

import com.promotick.lafabril.dao.utils.ConstantesWebDAO;
import com.promotick.lafabril.dao.web.TycDao;
import com.promotick.lafabril.dao.web.definition.TycDaoDefinition;
import com.promotick.lafabril.common.ConstantesCommon;
import com.promotick.lafabril.model.web.Tyc;
import com.promotick.configuration.utils.dao.DaoBuilder;
import com.promotick.configuration.utils.dao.DaoGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.Types;
import java.util.List;

@Repository
public class TycDaoImpl extends DaoGenerator implements TycDao {

    private TycDaoDefinition tycDaoDefinition;

    @Autowired
    public TycDaoImpl(JdbcTemplate jdbcTemplate, TycDaoDefinition tycDaoDefinition) {
        super(jdbcTemplate);
        this.tycDaoDefinition = tycDaoDefinition;
    }

    @Override
    public List<Tyc> listarTyc(Integer idTyc, Integer idCatalogo, Integer estadoTyc) {
        return DaoBuilder.getInstance(this)
                .setLogger(ConstantesCommon.LOGGER)
                .setSchema(ConstantesWebDAO.SCHEMA_NAME)
                .setProcedureName(ConstantesWebDAO.SP_TYC_LISTAR)
                .addParameter("var_id_tyc", Types.INTEGER, idTyc)
                .addParameter("var_id_catalogo", Types.INTEGER, idCatalogo)
                .addParameter("var_estado_tyc", Types.INTEGER, estadoTyc)
                .setDaoDefinition(tycDaoDefinition)
                .build();
    }
}
