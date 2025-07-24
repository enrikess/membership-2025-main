package com.promotick.lafabril.dao.admin.impl;

import com.promotick.lafabril.common.ConstantesCommon;
import com.promotick.lafabril.dao.admin.CampaniaIntegrationDao;
import com.promotick.lafabril.dao.utils.ConstantesAdminDAO;
import com.promotick.lafabril.dao.utils.ConstantesWebDAO;
import com.promotick.lafabril.dao.web.definition.CampaniaIntegrationDaoDefinition;
import com.promotick.lafabril.model.web.Producto;
import com.promotick.configuration.utils.dao.DaoBuilder;
import com.promotick.configuration.utils.dao.DaoGenerator;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.Types;

@Repository
public class CampaniaIntegrationDaoImpl extends DaoGenerator implements CampaniaIntegrationDao {
    public CampaniaIntegrationDaoImpl(JdbcTemplate jdbcTemplate, CampaniaIntegrationDaoDefinition campaniaIntegrationDaoDefinition) {
        super(jdbcTemplate);
        this.campaniaIntegrationDaoDefinition = campaniaIntegrationDaoDefinition;
    }

    private CampaniaIntegrationDaoDefinition campaniaIntegrationDaoDefinition;

    @Override
    public Integer actualizarCampania(Producto producto) {
        return DaoBuilder.getInstance(this)
                .setLogger(ConstantesCommon.LOGGER)
                .setSchema(ConstantesAdminDAO.SCHEMA_NAME)
                .setProcedureName(ConstantesAdminDAO.SP_CAMPANIA_ACTUALIZAR_INTEGRATION)
                .addParameter("var_codigo_producto", Types.VARCHAR, producto.getCodigoProducto())
                .addParameter("var_codigo_categoria", Types.VARCHAR, producto.getCategoria() != null ? producto.getCategoria().getCodigoCategoria() : "")
                .addParameter("var_puntos", Types.INTEGER, producto.getPuntosProducto())
                .addParameter("var_nombre_etiqueta", Types.VARCHAR, producto.getNombreTag())
                .setReturnDaoParameter("resultado", Types.INTEGER)
                .build(Integer.class);
    }
}
