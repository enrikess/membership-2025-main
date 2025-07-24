package com.promotick.lafabril.dao.admin.impl;

import com.promotick.lafabril.common.ConstantesCommon;
import com.promotick.lafabril.dao.admin.MarcaIntegrationDao;
import com.promotick.lafabril.dao.utils.ConstantesAdminDAO;
import com.promotick.lafabril.dao.web.definition.MarcaDaoIntegrationDefinition;
import com.promotick.lafabril.model.web.Marca;
import com.promotick.configuration.utils.dao.DaoBuilder;
import com.promotick.configuration.utils.dao.DaoGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.Types;

@Repository
public class MarcaIntegrationDaoImpl extends DaoGenerator implements MarcaIntegrationDao {

    private MarcaDaoIntegrationDefinition marcaDaoIntegrationDefinition;

    @Autowired
    public MarcaIntegrationDaoImpl(JdbcTemplate jdbcTemplate, MarcaDaoIntegrationDefinition marcaDaoIntegrationDefinition) {
        super(jdbcTemplate);
        this.marcaDaoIntegrationDefinition = marcaDaoIntegrationDefinition;
    }

    @Override
    public Integer actualizarMarca(Marca marca) {
        return DaoBuilder.getInstance(this)
                .setLogger(ConstantesCommon.LOGGER)
                .setSchema(ConstantesAdminDAO.SCHEMA_NAME)
                .setProcedureName(ConstantesAdminDAO.SP_MARCA_ACTUALIZAR_INTEGRATION)
                .addParameter("var_accion", Types.INTEGER, marca.getAccion())
                .addParameter("var_nombre_marca", Types.VARCHAR, marca.getNombreMarca())
                .addParameter("var_descripcion_corta", Types.VARCHAR, marca.getDescripcionCorta())
                .addParameter("var_descripcion_larga", Types.VARCHAR, marca.getDescripcionLarga())
                .addParameter("var_estado_marca", Types.INTEGER, marca.getEstadoMarca())
                .addParameter("var_codigo_marca", Types.VARCHAR, marca.getCodigoMarca())
                .setReturnDaoParameter("resultado", Types.INTEGER)
                .build(Integer.class);
    }

}
