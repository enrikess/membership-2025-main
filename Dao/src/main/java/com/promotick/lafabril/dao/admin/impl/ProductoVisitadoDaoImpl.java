package com.promotick.lafabril.dao.admin.impl;

import com.promotick.lafabril.dao.admin.ProductoVisitadoDao;
import com.promotick.lafabril.dao.utils.ConstantesAdminDAO;
import com.promotick.lafabril.dao.utils.ConstantesWebDAO;
import com.promotick.lafabril.dao.web.definition.ProductoVisitadoDaoDefinition;
import com.promotick.lafabril.common.ConstantesCommon;
import com.promotick.lafabril.model.util.FiltroProductoVisitado;
import com.promotick.lafabril.model.web.ProductoVisitado;
import com.promotick.configuration.utils.dao.DaoBuilder;
import com.promotick.configuration.utils.dao.DaoGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.Types;
import java.util.List;

@Repository
public class ProductoVisitadoDaoImpl extends DaoGenerator implements ProductoVisitadoDao {

    private ProductoVisitadoDaoDefinition productoVisitadoDaoDefinition;

    @Autowired
    public ProductoVisitadoDaoImpl(JdbcTemplate jdbcTemplate, ProductoVisitadoDaoDefinition productoVisitadoDaoDefinition) {
        super(jdbcTemplate);
        this.productoVisitadoDaoDefinition = productoVisitadoDaoDefinition;
    }

    @Override
    public List<ProductoVisitado> listarProductoVisitado(FiltroProductoVisitado filtroProductoVisitado) {
        return DaoBuilder.getInstance(this)
                .setLogger(ConstantesCommon.LOGGER)
                .setSchema(ConstantesAdminDAO.SCHEMA_NAME)
                .setProcedureName(ConstantesAdminDAO.SP_PRODUCTO_VISITADO_LISTAR)
                .addParameter("var_id_catalogo", Types.INTEGER, filtroProductoVisitado.getIdCatalogo())
                .addParameter("var_inicio", Types.INTEGER, filtroProductoVisitado.getStart())
                .addParameter("var_tamanio", Types.INTEGER, filtroProductoVisitado.getLength())
                .setDaoDefinition(productoVisitadoDaoDefinition)
                .build();
    }


    @Override
    public List<ProductoVisitado> listarProductoVisitadoWeb(FiltroProductoVisitado filtroProductoVisitado) {
        return DaoBuilder.getInstance(this)
                .setLogger(ConstantesCommon.LOGGER)
                .setSchema(ConstantesWebDAO.SCHEMA_NAME)
                .setProcedureName(ConstantesAdminDAO.SP_PRODUCTO_VISITADO_LISTAR)
                .addParameter("var_id_catalogo", Types.INTEGER, filtroProductoVisitado.getIdCatalogo())
                .addParameter("var_inicio", Types.INTEGER, filtroProductoVisitado.getStart())
                .addParameter("var_tamanio", Types.INTEGER, filtroProductoVisitado.getLength())
                .setDaoDefinition(productoVisitadoDaoDefinition)
                .build();
    }
}
