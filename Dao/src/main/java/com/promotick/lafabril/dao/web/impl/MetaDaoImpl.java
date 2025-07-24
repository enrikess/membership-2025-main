package com.promotick.lafabril.dao.web.impl;

import com.promotick.configuration.utils.dao.DaoBuilder;
import com.promotick.configuration.utils.dao.DaoGenerator;
import com.promotick.configuration.utils.dao.DaoParameter;

import com.promotick.lafabril.dao.utils.ConstantesWebDAO;
import com.promotick.lafabril.dao.web.MetaDao;
import com.promotick.lafabril.dao.web.definition.AccionSelloutDaoDefinition;
import com.promotick.lafabril.dao.web.definition.MetaAvanceTrimestralDaoDefinition;
import com.promotick.lafabril.model.web.AccionSellout;
import com.promotick.lafabril.model.web.MetaAvanceTrimestral;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.Types;
import java.util.Collections;
import java.util.List;

@Repository
public class MetaDaoImpl extends DaoGenerator implements MetaDao {

	private Logger LOGGER = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private MetaAvanceTrimestralDaoDefinition metaAvanceTrimestralDaoDefinition;
	@Autowired
	private AccionSelloutDaoDefinition accionSelloutDaoDefinition;

	@Autowired
	public MetaDaoImpl(JdbcTemplate jdbcTemplate) {
		super(jdbcTemplate);
	}


	@Override
	public List<MetaAvanceTrimestral> obtenerMetaAvanceParticipanteTrimestral(Integer idParticipante, Integer anio, Integer trimestre) {

		return DaoBuilder
				.getInstance(this)
				.setSchema(ConstantesWebDAO.SCHEMA_NAME_API)
				.setProcedureName(ConstantesWebDAO.SP_PARTICIPANTE_META_AVANCE_OBTENER)
				.addParameter(new DaoParameter("var_id_participante", Types.INTEGER, idParticipante))
				.addParameter(new DaoParameter("var_anio", Types.INTEGER, anio))
				.addParameter(new DaoParameter("var_trimestre", Types.INTEGER, trimestre))
				.setDaoDefinition(metaAvanceTrimestralDaoDefinition)
				.build();
	}

	@Override
	public List<AccionSellout> obtenerAccionesSelloutParticipante(Integer idParticipante, Integer anio, Integer trimestre) {
		return DaoBuilder
				.getInstance(this)
				.setSchema(ConstantesWebDAO.SCHEMA_NAME)
				.setProcedureName(ConstantesWebDAO.SP_META_TRIMESTRAL_OBTENER)
				.addParameter(new DaoParameter("var_trimestre", Types.INTEGER, trimestre))
				.addParameter(new DaoParameter("var_anio", Types.INTEGER, anio))
				.addParameter(new DaoParameter("var_id_participante", Types.INTEGER, idParticipante))
				.setDaoDefinition(accionSelloutDaoDefinition)
				.build();
	}
}
