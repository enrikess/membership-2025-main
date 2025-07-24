package com.promotick.lafabril.dao.web.impl;


import com.promotick.lafabril.common.ConstantesCommon;
import com.promotick.lafabril.dao.utils.ConstantesWebDAO;
import com.promotick.lafabril.dao.web.PartidoDao;
import com.promotick.lafabril.dao.web.definition.PartidoMundialDaoDefinition;
import com.promotick.lafabril.dao.web.definition.PronosticoMundialDaoDefinition;
import com.promotick.lafabril.model.web.PartidoMundial;
import com.promotick.lafabril.model.web.PronosticoParticipante;
import com.promotick.configuration.utils.dao.DaoBuilder;
import com.promotick.configuration.utils.dao.DaoGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.Types;
import java.util.List;

@Repository
public class PartidoDaoImpl extends DaoGenerator implements PartidoDao {


	private PartidoMundialDaoDefinition partidoMundialDaoDefinition;
	private PronosticoMundialDaoDefinition pronosticoMundialDaoDefinition;

	@Autowired
	public PartidoDaoImpl(JdbcTemplate jdbcTemplate, PartidoMundialDaoDefinition partidoMundialDaoDefinition, PronosticoMundialDaoDefinition pronosticoMundialDaoDefinition){
		super(jdbcTemplate);
		this.partidoMundialDaoDefinition = partidoMundialDaoDefinition;
		this.pronosticoMundialDaoDefinition = pronosticoMundialDaoDefinition;
	}


	@Override
	public List<PartidoMundial> listarPartidoMundial() {
		return DaoBuilder.getInstance(this)
				.setLogger(ConstantesCommon.LOGGER)
				.setSchema(ConstantesWebDAO.SCHEMA_NAME)
				.setProcedureName(ConstantesWebDAO.SP_MUNDIAL_PARTIDO_LISTAR)
				.setDaoDefinition(partidoMundialDaoDefinition)
				.build();
	}

	@Override
	public List<PartidoMundial> listarPartidoPronosticoMundial() {
		return DaoBuilder.getInstance(this)
				.setLogger(ConstantesCommon.LOGGER)
				.setSchema(ConstantesWebDAO.SCHEMA_NAME)
				.setProcedureName(ConstantesWebDAO.SP_MUNDIAL_PARTIDO_PRONOSTICO_LISTAR)
				.setDaoDefinition(partidoMundialDaoDefinition)
				.build();
	}

	@Override
	public List<PronosticoParticipante> obtenerPronosticoRespuesta(Integer idParticipante) {
		return DaoBuilder.getInstance(this)
				.setLogger(ConstantesCommon.LOGGER)
				.setSchema(ConstantesWebDAO.SCHEMA_NAME)
				.setProcedureName(ConstantesWebDAO.SP_MUNDIAL_PRONOSTICO_RESPUESTA_OBTENER)
				.addParameter("var_id_participante", Types.INTEGER, idParticipante)
				.setDaoDefinition(pronosticoMundialDaoDefinition)
				.build();
	}

	@Override
	public Integer registrarPronosticoParticipante(PronosticoParticipante pronosticoParticipante) {
		return DaoBuilder.getInstance(this)
				.setLogger(ConstantesCommon.LOGGER)
				.setSchema(ConstantesWebDAO.SCHEMA_NAME)
				.setProcedureName(ConstantesWebDAO.SP_MUNDIAL_PRONOSTICO_PARTICIPANTE_REGISTRAR)
				.addParameter("var_id_participante", Types.INTEGER, pronosticoParticipante.getIdParticipante())
				.addParameter("var_id_partido_mundial", Types.INTEGER, pronosticoParticipante.getIdPartidoMundial())
				.addParameter("var_id_pais_mundial_1", Types.INTEGER, pronosticoParticipante.getIdPaisMundial1())
				.addParameter("var_id_pais_mundial_2", Types.INTEGER, pronosticoParticipante.getIdPaisMundial2())
				.addParameter("var_score_pais_1", Types.INTEGER, pronosticoParticipante.getScorePais1())
				.addParameter("var_score_pais_2", Types.INTEGER, pronosticoParticipante.getScorePais2())
				.addParameter("var_usuario_creacion", Types.VARCHAR, pronosticoParticipante.getUsuarioCreacion())
				.setReturnDaoParameter("resultado",Types.INTEGER)
				.build(Integer.class);

	}


}
