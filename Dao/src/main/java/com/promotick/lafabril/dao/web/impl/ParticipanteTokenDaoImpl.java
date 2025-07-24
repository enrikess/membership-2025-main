package com.promotick.lafabril.dao.web.impl;

import com.promotick.lafabril.common.ConstantesCommon;
import com.promotick.lafabril.dao.utils.ConstantesWebDAO;
import com.promotick.lafabril.dao.web.ParticipanteTokenDao;
import com.promotick.lafabril.dao.web.definition.ParticipanteDaoDefinition;
import com.promotick.lafabril.model.util.UtilEnum;
import com.promotick.lafabril.model.web.Participante;
import com.promotick.configuration.utils.dao.DaoBuilder;
import com.promotick.configuration.utils.dao.DaoGenerator;
import com.promotick.configuration.utils.dao.DaoParameter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.Types;

@Repository
public class ParticipanteTokenDaoImpl extends DaoGenerator implements ParticipanteTokenDao {

    private static Logger LOGGER = LoggerFactory.getLogger(ParticipanteTokenDaoImpl.class);
    private ParticipanteDaoDefinition participanteDaoDefinition;

    @Autowired
    public ParticipanteTokenDaoImpl(JdbcTemplate jdbcTemplate, ParticipanteDaoDefinition participanteDaoDefinition) {
        super(jdbcTemplate);
        this.participanteDaoDefinition = participanteDaoDefinition;
    }

    @Override
    public Integer generarToken(Integer idParticipante, String token, UtilEnum.TIPO_DISPOSITVO_VISITA tipo_dispositvo) {
        LOGGER.info("### generarToken");
        return DaoBuilder.getInstance(this)
                .setLogger(ConstantesCommon.LOGGER)
                .setSchema(ConstantesWebDAO.SCHEMA_NAME)
                .setProcedureName(ConstantesWebDAO.SP_PARTICIPANTE_TOKEN_GENERAR)
                .addParameter(new DaoParameter("var_id_participante", Types.INTEGER, idParticipante))
                .addParameter(new DaoParameter("var_token", Types.VARCHAR, token))
                .addParameter(new DaoParameter("var_dispositivo", Types.INTEGER, tipo_dispositvo.getCodigo()))
                .setReturnDaoParameter("resultado", Types.INTEGER)
                .build(Integer.class);
    }

    @Override
    public Integer validarToken(String token) {
        return DaoBuilder.getInstance(this)
                .setLogger(ConstantesCommon.LOGGER)
                .setSchema(ConstantesWebDAO.SCHEMA_NAME)
                .setProcedureName(ConstantesWebDAO.SP_PARTICIPANTE_TOKEN_VALIDAR)
                .addParameter(new DaoParameter("var_token", Types.VARCHAR, token))
                .setReturnDaoParameter("resultado", Types.INTEGER)
                .build(Integer.class);
    }

    @Override
    public Participante obtenerParticipanteXToken(String token) {
        return DaoBuilder.getInstance(this)
                .setLogger(ConstantesCommon.LOGGER)
                .setSchema(ConstantesWebDAO.SCHEMA_NAME)
                .setProcedureName(ConstantesWebDAO.SP_PARTICIPANTE_OBTENER_TOKEN)
                .addParameter(new DaoParameter("var_token", Types.VARCHAR, token))
                .setDaoDefinition(participanteDaoDefinition)
                .build(Participante.class);
    }
}
