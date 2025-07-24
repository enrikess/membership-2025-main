package com.promotick.lafabril.dao.web.impl;

import com.promotick.lafabril.common.ConstantesCommon;
import com.promotick.lafabril.dao.utils.ConstantesWebDAO;
import com.promotick.lafabril.dao.web.TriviaDao;
import com.promotick.lafabril.dao.web.definition.AlternativaRespuestaDaoDefinition;
import com.promotick.lafabril.dao.web.definition.TriviaDaoDefinition;
import com.promotick.lafabril.dao.web.definition.TriviaResumenDaoDefinition;
import com.promotick.lafabril.model.web.AlternativaRespuesta;
import com.promotick.lafabril.model.web.Trivia;
import com.promotick.lafabril.model.web.TriviaResumen;
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
public class TriviaDaoImpl extends DaoGenerator implements TriviaDao {

	private TriviaDaoDefinition triviaDaoDefinition;
	private AlternativaRespuestaDaoDefinition alternativaRespuestaDaoDefinition;
	private TriviaResumenDaoDefinition triviaResumenDaoDefinition;

	@Autowired
	public TriviaDaoImpl(JdbcTemplate jdbcTemplate, TriviaDaoDefinition triviaDaoDefinition, AlternativaRespuestaDaoDefinition alternativaRespuestaDaoDefinition, TriviaResumenDaoDefinition triviaResumenDaoDefinition){
		super(jdbcTemplate);
		this.triviaDaoDefinition = triviaDaoDefinition;
		this.alternativaRespuestaDaoDefinition = alternativaRespuestaDaoDefinition;
		this.triviaResumenDaoDefinition = triviaResumenDaoDefinition;
	}

	@Override
	public List<Trivia> obtenerTrivia(Integer idParticipante) {
		return DaoBuilder.getInstance(this)
				.setLogger(ConstantesCommon.LOGGER)
				.setSchema(ConstantesWebDAO.SCHEMA_NAME)
				.setProcedureName(ConstantesWebDAO.SP_MUNDIAL_TRIVIA_OBTENER)
				.addParameter("var_id_participante", Types.INTEGER, idParticipante)
				.setDaoDefinition(triviaDaoDefinition)
				.build();
	}

	@Override
	public List<AlternativaRespuesta> obtenerTriviaRespuesta(Integer idParticipante, Integer idTriviaGrupo) {
		return DaoBuilder.getInstance(this)
				.setLogger(ConstantesCommon.LOGGER)
				.setSchema(ConstantesWebDAO.SCHEMA_NAME)
				.setProcedureName(ConstantesWebDAO.SP_MUNDIAL_TRIVIA_RESPUESTA_OBTENER)
				.addParameter("var_id_participante", Types.INTEGER, idParticipante)
				.addParameter("var_id_trivia_grupo", Types.INTEGER, idTriviaGrupo)
				.setDaoDefinition(alternativaRespuestaDaoDefinition)
				.build();
	}


	@Override
	public TriviaResumen obtenerTriviaResumen(Integer idParticipante, Integer idTriviaGrupo) {
		return DaoBuilder.getInstance(this)
				.setLogger(ConstantesCommon.LOGGER)
				.setSchema(ConstantesWebDAO.SCHEMA_NAME)
				.setProcedureName(ConstantesWebDAO.SP_MUNDIAL_TRIVIA_RESUMEN_OBTENER)
				.addParameter("var_id_participante", Types.INTEGER, idParticipante)
				.addParameter("var_id_trivia_grupo", Types.INTEGER, idTriviaGrupo)
				.setDaoDefinition(triviaResumenDaoDefinition)
				.build(TriviaResumen.class);
	}


	@Override
	public Integer registrarTriviaRespuesta(Integer idParticipante, Integer idTriviaMundial, Integer idRespuesta, String descripcionRespuesta, Integer acertoRespuesta, String usuarioCreacion, Integer idTriviaGrupoParticipante) {
		return DaoBuilder.getInstance(this)
				.setLogger(ConstantesCommon.LOGGER)
				.setSchema(ConstantesWebDAO.SCHEMA_NAME)
				.setProcedureName(ConstantesWebDAO.SP_MUNDIAL_TRIVIA_RESPUESTA_REGISTRAR)
				.addParameter("var_id_participante", Types.INTEGER, idParticipante)
				.addParameter("var_id_trivia_mundial", Types.INTEGER, idTriviaMundial)
				.addParameter("var_id_respuesta", Types.INTEGER, idRespuesta)
				.addParameter("var_descripcion_respuesta", Types.VARCHAR, descripcionRespuesta)
				.addParameter("var_acerto_respuesta", Types.INTEGER, acertoRespuesta)
				.addParameter("var_usuario_creacion", Types.VARCHAR, usuarioCreacion)
				.addParameter("var_id_trivia_grupo_participante", Types.INTEGER, idTriviaGrupoParticipante)
				.setReturnDaoParameter("resultado", Types.INTEGER)
				.build(Integer.class);
	}

    @Override
    public Integer registrarTriviaGrupo(Integer idParticipante, Integer idTriviaGrupo, String estadoTriviaGrupoParticipante, Integer cantidadRespuestasCorrectas, Integer cantidadPreguntas, String usuarioModificacion) {
        return DaoBuilder.getInstance(this)
				.setLogger(ConstantesCommon.LOGGER)
				.setSchema(ConstantesWebDAO.SCHEMA_NAME)
                .setProcedureName(ConstantesWebDAO.SP_MUNDIAL_TRIVIA_ESTADO_ACTUALIZAR)
                .addParameter("var_id_participante", Types.INTEGER, idParticipante)
                .addParameter("var_id_trivia_grupo", Types.INTEGER, idTriviaGrupo)
                .addParameter("var_estado_trivia_grupo_participante", Types.VARCHAR, estadoTriviaGrupoParticipante)
                .addParameter("var_cantidad_respuestas_correctas", Types.INTEGER, cantidadRespuestasCorrectas)
                .addParameter("var_cantidad_preguntas", Types.INTEGER, cantidadPreguntas)
                .addParameter("var_usuario_modificacion", Types.VARCHAR, usuarioModificacion)
                .setReturnDaoParameter("resultado", Types.INTEGER)
                .build(Integer.class);
    }

}
