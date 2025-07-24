package com.promotick.lafabril.dao.web.impl;

import com.promotick.lafabril.common.ConstantesCommon;
import com.promotick.lafabril.dao.utils.ConstantesWebDAO;
import com.promotick.lafabril.dao.web.RecomendadoDao;
import com.promotick.lafabril.dao.web.definition.RecomendadoDaoDefinition;
import com.promotick.lafabril.model.web.Recomendado;
import com.promotick.configuration.utils.dao.DaoBuilder;
import com.promotick.configuration.utils.dao.DaoGenerator;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.Types;
import java.util.List;

@Repository
public class RecomendadoDaoImpl extends DaoGenerator implements RecomendadoDao {

    private RecomendadoDaoDefinition recomendadoDaoDefinition;

    public RecomendadoDaoImpl(JdbcTemplate jdbcTemplate, RecomendadoDaoDefinition recomendadoDaoDefinition) {
        super(jdbcTemplate);
        this.recomendadoDaoDefinition = recomendadoDaoDefinition;
    }

    @Override
    public Integer registrarRecomendado(Recomendado recomendado) {
        return DaoBuilder.getInstance(this)
                .setLogger(ConstantesCommon.LOGGER)
                .setSchema(ConstantesWebDAO.SCHEMA_NAME)
                .setProcedureName(ConstantesWebDAO.SP_REGISTRAR_RECOMENDADO)
                .addParameter("var_nro_doc", Types.VARCHAR, recomendado.getNroDocumentoRecomendado())
                .addParameter("var_nombre",Types.VARCHAR, recomendado.getNombresRecomendado())
                .addParameter("var_appaterno", Types.VARCHAR, recomendado.getAppaternoRecomendado())
                .addParameter("var_apmaterno", Types.VARCHAR, recomendado.getApmaternoRecomendado())
                .addParameter("var_telefono",Types.VARCHAR, recomendado.getTelefonoRecomendado())
                .addParameter("var_movil", Types.VARCHAR, recomendado.getCelularRecomendado())
                .addParameter("var_email",Types.VARCHAR,recomendado.getEmailRecomendado())
                .addParameter("var_tiempo_compra", Types.VARCHAR, recomendado.getTiempoCompra())
                .addParameter("var_financiamiento", Types.VARCHAR, recomendado.getFinanciamiento())
                .addParameter("var_provincia",Types.VARCHAR, recomendado.getProvincia())
                .addParameter("var_ciudad", Types.VARCHAR, recomendado.getCiudad())
                .addParameter("var_observacion", Types.VARCHAR, recomendado.getObservacionRecomendado())
                .addParameter("var_usuario_creacion", Types.VARCHAR, recomendado.getAuditoria().getUsuarioCreacion())
                .addParameter("var_id_participante", Types.INTEGER, recomendado.getParticipante().getIdParticipante())
                .setReturnDaoParameter("resultado",Types.INTEGER)
                .build(Integer.class);

    }

    @Override
    public List<Recomendado> listarRecomendados(Integer idParticipante) {
        return DaoBuilder.getInstance(this)
                .setLogger(ConstantesCommon.LOGGER)
                .setSchema(ConstantesWebDAO.SCHEMA_NAME)
                .setProcedureName(ConstantesWebDAO.SP_LISTAR_RECOMENDADO)
                .addParameter("var_id_participante", Types.INTEGER, idParticipante)
                .setDaoDefinition(recomendadoDaoDefinition)
                .build();
    }


}
