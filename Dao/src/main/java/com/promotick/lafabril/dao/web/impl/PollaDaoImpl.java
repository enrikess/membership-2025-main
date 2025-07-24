package com.promotick.lafabril.dao.web.impl;

import com.promotick.lafabril.common.ConstantesCommon;
import com.promotick.lafabril.dao.utils.ConstantesWebDAO;
import com.promotick.lafabril.dao.web.PollaDao;
import com.promotick.lafabril.dao.web.definition.PaisMundialDaoDefinition;
import com.promotick.lafabril.dao.web.definition.PollaParticipanteDaoDefinition;
import com.promotick.lafabril.model.web.PaisMundial;
import com.promotick.lafabril.model.web.PollaParticipante;
import com.promotick.configuration.utils.dao.DaoBuilder;
import com.promotick.configuration.utils.dao.DaoGenerator;
import com.promotick.configuration.utils.dao.DaoParameter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.Types;
import java.util.List;


@Repository
public class PollaDaoImpl extends DaoGenerator implements PollaDao {

	private PaisMundialDaoDefinition paisMundialDaoDefinition;
	private PollaParticipanteDaoDefinition pollaParticipanteDaoDefinition;

	@Autowired
	public PollaDaoImpl(JdbcTemplate jdbcTemplate, PaisMundialDaoDefinition paisMundialDaoDefinition, PollaParticipanteDaoDefinition pollaParticipanteDaoDefinition){
		super(jdbcTemplate);
		this.paisMundialDaoDefinition = paisMundialDaoDefinition;
		this.pollaParticipanteDaoDefinition = pollaParticipanteDaoDefinition;
	}

	@Override
	public List<PaisMundial> listarPaisMundial() {
		return DaoBuilder.getInstance(this)
				.setLogger(ConstantesCommon.LOGGER)
				.setSchema(ConstantesWebDAO.SCHEMA_NAME)
				.setProcedureName(ConstantesWebDAO.SP_MUNDIAL_PAIS_LISTAR)
				.setDaoDefinition(paisMundialDaoDefinition)
				.build();
	}

	@Override
	public Integer registrarPollaParticipante(PollaParticipante pollaParticipante) {
		return DaoBuilder.getInstance(this)
				.setLogger(ConstantesCommon.LOGGER)
				.setSchema(ConstantesWebDAO.SCHEMA_NAME)
				.setProcedureName(ConstantesWebDAO.SP_MUNDIAL_POLLA_PARTICIPANTE_REGISTRAR)
				.addParameter("var_id_participante", Types.INTEGER, pollaParticipante.getIdParticipante())
				.addParameter("var_id_pais_mundial", Types.INTEGER, pollaParticipante.getIdPaisMundial())
				.addParameter("var_id_pais_mundial_1", Types.INTEGER, pollaParticipante.getIdPaisMundial1())
				.addParameter("var_id_pais_mundial_2", Types.INTEGER, pollaParticipante.getIdPaisMundial2())
				.addParameter("var_nombre_pais", Types.VARCHAR, pollaParticipante.getNombrePais())
				.addParameter("var_resultado_codigo", Types.VARCHAR, pollaParticipante.getResultadoCodigo())
				.addParameter("var_usuario_creacion", Types.VARCHAR, pollaParticipante.getUsuarioCreacion())
				.setReturnDaoParameter("resultado",Types.INTEGER)
				.build(Integer.class);
	}

	@Override
	public List<PollaParticipante> obtenerPollaParticipanteResumen(Integer idParticipante) {
		return DaoBuilder.getInstance(this)
				.setLogger(ConstantesCommon.LOGGER)
				.setSchema(ConstantesWebDAO.SCHEMA_NAME)
				.setProcedureName(ConstantesWebDAO.SP_MUNDIAL_POLLA_PARTICIPANTE_RESUMEN)
				.addParameter("var_id_participante", Types.INTEGER, idParticipante)
				.setDaoDefinition(pollaParticipanteDaoDefinition)
				.build();
	}

}
