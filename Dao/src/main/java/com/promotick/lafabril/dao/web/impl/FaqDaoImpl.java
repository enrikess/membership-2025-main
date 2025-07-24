package com.promotick.lafabril.dao.web.impl;

import com.promotick.lafabril.dao.utils.ConstantesWebDAO;
import com.promotick.lafabril.dao.web.FaqDao;
import com.promotick.lafabril.common.ConstantesCommon;
import com.promotick.lafabril.dao.web.definition.FaqDaoDefinition;
import com.promotick.lafabril.model.web.Faq;
import com.promotick.configuration.utils.dao.DaoBuilder;
import com.promotick.configuration.utils.dao.DaoGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.Types;
import java.util.List;

@Repository
public class FaqDaoImpl extends DaoGenerator implements FaqDao {

    private FaqDaoDefinition faqDaoDefinition;

    @Autowired
    public FaqDaoImpl(JdbcTemplate jdbcTemplate, FaqDaoDefinition faqDaoDefinition) {
        super(jdbcTemplate);
        this.faqDaoDefinition = faqDaoDefinition;
    }

    @Override
    public List<Faq> listarFaq(Integer idFaq, Integer idCatalogo, Integer estadoFaq) {
        return DaoBuilder.getInstance(this)
                .setLogger(ConstantesCommon.LOGGER)
                .setSchema(ConstantesWebDAO.SCHEMA_NAME)
                .setProcedureName(ConstantesWebDAO.SP_FAQ_LISTAR)
                .addParameter("var_id_faq", Types.INTEGER, idFaq)
                .addParameter("var_id_catalogo", Types.INTEGER, idCatalogo)
                .addParameter("var_estado_faq", Types.INTEGER, estadoFaq)
                .setDaoDefinition(faqDaoDefinition)
                .build();
    }
}
