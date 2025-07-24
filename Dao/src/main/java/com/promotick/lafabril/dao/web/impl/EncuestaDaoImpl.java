package com.promotick.lafabril.dao.web.impl;

import com.promotick.configuration.utils.dao.DaoBuilder;
import com.promotick.configuration.utils.dao.DaoGenerator;
import com.promotick.lafabril.dao.utils.ConstantesAdminDAO;
import com.promotick.lafabril.dao.utils.ConstantesWebDAO;
import com.promotick.lafabril.dao.web.EncuestaDao;
import com.promotick.lafabril.dao.web.definition.EncuestaDetalleDaoDefinition;
import com.promotick.lafabril.common.ConstantesCommon;
import com.promotick.lafabril.model.util.FiltroEncuesta;
import com.promotick.lafabril.model.web.Encuesta;
import com.promotick.lafabril.model.web.EncuestaDetalle;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.Types;
import java.util.List;

@Repository
public class EncuestaDaoImpl extends DaoGenerator implements EncuestaDao {


    private final EncuestaDetalleDaoDefinition encuestaDetalleDaoDefinition;

    public EncuestaDaoImpl(JdbcTemplate jdbcTemplate,EncuestaDetalleDaoDefinition encuestaDetalleDaoDefinition) {
        super(jdbcTemplate);
        this.encuestaDetalleDaoDefinition = encuestaDetalleDaoDefinition;
    }

    @Override
    public Integer registrarEncuesta(Encuesta encuesta) {
        return DaoBuilder.getInstance(this)
                .setLogger(ConstantesCommon.LOGGER)
                .setSchema(ConstantesWebDAO.SCHEMA_NAME)
                .setProcedureName(ConstantesWebDAO.SP_ENCUESTA_REGISTRAR)
                .addParameter("var_id_tipo_encuesta", Types.INTEGER, encuesta.getIdTipoEncuesta())
                .addParameter("var_id_participante", Types.INTEGER, encuesta.getParticipante().getIdParticipante())
                .addParameter("var_id_pedido", Types.INTEGER, encuesta.getPedido() != null ? encuesta.getPedido().getIdPedido() : null)
                .setReturnDaoParameter("resultado", Types.INTEGER)
                .build(Integer.class);
    }

    @Override
    public Integer registrarEncuestaDetalle(EncuestaDetalle encuestaDetalle) {
        return DaoBuilder.getInstance(this)
                .setLogger(ConstantesCommon.LOGGER)
                .setSchema(ConstantesWebDAO.SCHEMA_NAME)
                .setProcedureName(ConstantesWebDAO.SP_ENCUESTA_REGISTRAR_DETALLE)
                .addParameter("var_id_encuesta", Types.INTEGER, encuestaDetalle.getEncuesta().getIdEncuesta())
                .addParameter("var_pregunta", Types.VARCHAR, encuestaDetalle.getPregunta())
                .addParameter("var_respuesta", Types.VARCHAR, encuestaDetalle.getRespuesta())
                .setReturnDaoParameter("resultado", Types.INTEGER)
                .build(Integer.class);
    }

    @Override
    public Integer encuestaPedidoObtener(Integer idParticipante) {
        return DaoBuilder.getInstance(this)
                .setLogger(ConstantesCommon.LOGGER)
                .setSchema(ConstantesWebDAO.SCHEMA_NAME)
                .setProcedureName(ConstantesWebDAO.SP_ENCUESTA_PEDIDO_OBTENER)
                .addParameter("var_id_participante", Types.INTEGER, idParticipante)
                .setReturnDaoParameter("resultado", Types.INTEGER)
                .build(Integer.class);
    }

    @Override
    public List<EncuestaDetalle> listarResultadosEncuesta(FiltroEncuesta filtroEncuesta) {
        return DaoBuilder.getInstance(this)
                .setLogger(ConstantesCommon.LOGGER)
                .setSchema(ConstantesAdminDAO.SCHEMA_NAME)
                .setProcedureName(ConstantesAdminDAO.SP_ENCUESTA_DETALLE_LISTAR)
                .addParameter("var_fecha_inicio", Types.VARCHAR, filtroEncuesta.getFinicio())
                .addParameter("var_fecha_fin", Types.VARCHAR, filtroEncuesta.getFfin())
                .addParameter("var_buscar", Types.VARCHAR, filtroEncuesta.getBuscar())
                .addParameter("var_inicio", Types.INTEGER, filtroEncuesta.getStart())
                .addParameter("var_tamanio", Types.INTEGER, filtroEncuesta.getLength())
                .setDaoDefinition(encuestaDetalleDaoDefinition)
                .build();
    }

    @Override
    public Integer contarResultadosEncuesta(FiltroEncuesta filtroEncuesta) {
        return DaoBuilder.getInstance(this)
                .setLogger(ConstantesCommon.LOGGER)
                .setSchema(ConstantesAdminDAO.SCHEMA_NAME)
                .setProcedureName(ConstantesAdminDAO.SP_ENCUESTA_DETALLE_CONTAR)
                .addParameter("var_fecha_inicio", Types.VARCHAR, filtroEncuesta.getFinicio())
                .addParameter("var_fecha_fin", Types.VARCHAR, filtroEncuesta.getFfin())
                .addParameter("var_buscar", Types.VARCHAR, filtroEncuesta.getBuscar())
                .setReturnDaoParameter("resultado", Types.INTEGER)
                .build(Integer.class);
    }

    @Override
    public Integer omitirEncuesta(Integer idPedido) {
        return DaoBuilder.getInstance(this)
                .setLogger(ConstantesCommon.LOGGER)
                .setSchema(ConstantesWebDAO.SCHEMA_NAME)
                .setProcedureName(ConstantesWebDAO.SP_ENCUESTA_OMITIR)
                .addParameter("var_id_pedido", Types.INTEGER, idPedido)
                .setReturnDaoParameter("resultado", Types.INTEGER)
                .build(Integer.class);
    }
}
