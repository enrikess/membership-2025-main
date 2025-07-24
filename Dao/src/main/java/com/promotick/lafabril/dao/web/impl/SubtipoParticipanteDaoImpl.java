package com.promotick.lafabril.dao.web.impl;

import com.promotick.configuration.utils.dao.DaoBuilder;
import com.promotick.configuration.utils.dao.DaoGenerator;
import com.promotick.lafabril.dao.utils.ConstantesWebDAO;
import com.promotick.lafabril.dao.web.SubtipoParticipanteDao;
import com.promotick.lafabril.common.ConstantesCommon;
import com.promotick.lafabril.dao.web.definition.SubtipoParticipanteDaoDefinition;
import com.promotick.lafabril.model.web.SubtipoParticipante;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class SubtipoParticipanteDaoImpl extends DaoGenerator implements SubtipoParticipanteDao {

    private SubtipoParticipanteDaoDefinition subtipoParticipanteDaoDefinition;

    @Autowired
    public SubtipoParticipanteDaoImpl(JdbcTemplate jdbcTemplate, SubtipoParticipanteDaoDefinition subtipoParticipanteDaoDefinition) {
        super(jdbcTemplate);
        this.subtipoParticipanteDaoDefinition = subtipoParticipanteDaoDefinition;
    }

    @Override
    public List<SubtipoParticipante> listarSubtiposParticipante() {
        return DaoBuilder.getInstance(this)
                .setLogger(ConstantesCommon.LOGGER)
                .setSchema(ConstantesWebDAO.SCHEMA_NAME)
                .setProcedureName(ConstantesWebDAO.SP_SUB_TIPO_PARTICIPANTE_LISTAR)
                .setDaoDefinition(subtipoParticipanteDaoDefinition)
                .build();
    }
}
