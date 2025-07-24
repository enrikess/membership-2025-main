package com.promotick.lafabril.dao.web.impl;

import com.promotick.configuration.utils.dao.DaoBuilder;
import com.promotick.configuration.utils.dao.DaoGenerator;
import com.promotick.lafabril.dao.utils.ConstantesAdminDAO;
import com.promotick.lafabril.dao.web.TipoParticipanteDao;
import com.promotick.lafabril.common.ConstantesCommon;
import com.promotick.lafabril.dao.web.definition.TipoParticipanteDaoDefinition;
import com.promotick.lafabril.model.web.TipoParticipante;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class TipoParticipanteDaoImpl extends DaoGenerator implements TipoParticipanteDao {

    private TipoParticipanteDaoDefinition tipoParticipanteDaoDefinition;

    @Autowired
    public TipoParticipanteDaoImpl(JdbcTemplate jdbcTemplate, TipoParticipanteDaoDefinition tipoParticipanteDaoDefinition) {
        super(jdbcTemplate);
        this.tipoParticipanteDaoDefinition = tipoParticipanteDaoDefinition;
    }

    @Override
    public List<TipoParticipante> listarTipoParticipantes() {
        return DaoBuilder.getInstance(this)
                .setLogger(ConstantesCommon.LOGGER)
                .setSchema(ConstantesAdminDAO.SCHEMA_NAME)
                .setProcedureName(ConstantesAdminDAO.SP_TIPO_PARTICIPANTE_LISTAR)
                .setDaoDefinition(tipoParticipanteDaoDefinition)
                .build();
    }
}
