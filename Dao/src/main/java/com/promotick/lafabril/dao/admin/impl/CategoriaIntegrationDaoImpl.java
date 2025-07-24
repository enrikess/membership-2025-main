package com.promotick.lafabril.dao.admin.impl;

import com.promotick.lafabril.common.ConstantesCommon;
import com.promotick.lafabril.dao.admin.CategoriaIntegrationDao;
import com.promotick.lafabril.dao.utils.ConstantesAdminDAO;
import com.promotick.lafabril.dao.web.definition.CategoriaIntegrationDaoDefinition;
import com.promotick.lafabril.model.web.Categoria;
import com.promotick.configuration.utils.dao.DaoBuilder;
import com.promotick.configuration.utils.dao.DaoGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.Types;

@Repository
public class CategoriaIntegrationDaoImpl extends DaoGenerator implements CategoriaIntegrationDao {

    private CategoriaIntegrationDaoDefinition categoriaIntegrationDaoDefinition;

    @Autowired
    public CategoriaIntegrationDaoImpl(JdbcTemplate jdbcTemplate, CategoriaIntegrationDaoDefinition categoriaIntegrationDaoDefinition ) {
        super(jdbcTemplate);
        this.categoriaIntegrationDaoDefinition = categoriaIntegrationDaoDefinition;
    }

    @Override
    public Integer actualizarCategoria(Categoria categoria) {
        return DaoBuilder.getInstance(this)
                .setLogger(ConstantesCommon.LOGGER)
                .setSchema(ConstantesAdminDAO.SCHEMA_NAME)
                .setProcedureName(ConstantesAdminDAO.SP_CATEGORIA_ACTUALIZAR_INTEGRATION)
                .addParameter("var_accion", Types.INTEGER, categoria.getAccion())
                .addParameter("var_codigo_categoria", Types.VARCHAR, categoria.getCodigoCategoria())
                .addParameter("var_nombre_categoria", Types.VARCHAR, categoria.getNombreCategoria())
                .addParameter("var_estado_categoria", Types.INTEGER, categoria.getEstadoCategoria())
                .addParameter("var_orden_categoria", Types.INTEGER, categoria.getOrdenCategoria())
                .addParameter("var_id_tipo_categoria", Types.INTEGER, categoria.getIdCategoria())
                .addParameter("var_codigo_categoria_parent", Types.VARCHAR, categoria.getCodigoCategoriaParent())
                .setReturnDaoParameter("resultado", Types.INTEGER)
                .build(Integer.class);
    }
}
