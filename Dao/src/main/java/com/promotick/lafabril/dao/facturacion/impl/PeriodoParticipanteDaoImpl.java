package com.promotick.lafabril.dao.facturacion.impl;

import com.promotick.lafabril.dao.facturacion.definition.MetaParticipanteDaoDefinition;
import com.promotick.lafabril.dao.facturacion.PeriodoParticipanteDao;
import com.promotick.lafabril.dao.utils.ConstantesFacturacionDAO;
import com.promotick.lafabril.common.ConstantesCommon;
import com.promotick.lafabril.dao.utils.ConstantesWebDAO;
import com.promotick.lafabril.model.util.MetaParticipante;
import com.promotick.lafabril.model.util.UtilEnum;
import com.promotick.configuration.utils.dao.DaoBuilder;
import com.promotick.configuration.utils.dao.DaoGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.Types;
import java.util.Collections;
import java.util.Date;
import java.util.List;

@Repository
public class PeriodoParticipanteDaoImpl extends DaoGenerator implements PeriodoParticipanteDao {

    private MetaParticipanteDaoDefinition metaParticipanteDaoDefinition;

    @Autowired
    public PeriodoParticipanteDaoImpl(JdbcTemplate jdbcTemplate, MetaParticipanteDaoDefinition metaParticipanteDaoDefinition) {
        super(jdbcTemplate);
        this.metaParticipanteDaoDefinition = metaParticipanteDaoDefinition;
    }

    @Override
    public MetaParticipante obtenerMetaParticipante(Integer idParticipante, UtilEnum.TIPO_META tipoMeta) {
        return DaoBuilder.getInstance(this)
                .setLogger(ConstantesCommon.LOGGER)
                .setSchema(ConstantesFacturacionDAO.SCHEMA_NAME)
                .setProcedureName(ConstantesFacturacionDAO.SP_PARTICIPANTE_OBTENER_META)
                .addParameter("var_id_participante", Types.INTEGER, idParticipante)
                .addParameter("var_tipo", Types.INTEGER, tipoMeta.getValue())
                .setDaoDefinition(metaParticipanteDaoDefinition)
                .build(MetaParticipante.class);
    }

    @Override
    public MetaParticipante obtenerMetaParticipante(Integer idParticipante, UtilEnum.TIPO_META tipoMeta, Date fecha) {
        return DaoBuilder.getInstance(this)
                .setLogger(ConstantesCommon.LOGGER)
                .setSchema(ConstantesFacturacionDAO.SCHEMA_NAME)
                .setProcedureName(ConstantesFacturacionDAO.SP_PARTICIPANTE_OBTENER_META)
                .addParameter("var_id_participante", Types.INTEGER, idParticipante)
                .addParameter("var_tipo", Types.INTEGER, tipoMeta.getValue())
                .addParameter("var_fecha", Types.DATE, fecha)
                .setDaoDefinition(metaParticipanteDaoDefinition)
                .build(MetaParticipante.class);
    }

    @Override
    public List<MetaParticipante> obtenerMetaParticipanteNuevo(Integer idParticipante, Integer mes) {
        return DaoBuilder.getInstance(this)
                .setLogger(ConstantesCommon.LOGGER)
                .setSchema(ConstantesWebDAO.SCHEMA_NAME)
                .setProcedureName(ConstantesWebDAO.SP_PATICIPANTE_META_OBTENER)
                .addParameter("var_id_participante", Types.INTEGER, idParticipante)
                .addParameter("var_mes", Types.INTEGER, mes)
                .setDaoDefinition(metaParticipanteDaoDefinition)
                .build();
    }
}
