package com.promotick.lafabril.dao.admin.impl;

import com.promotick.lafabril.dao.admin.ProductoNovedosoDao;
import com.promotick.lafabril.dao.utils.ConstantesAdminDAO;
import com.promotick.lafabril.dao.utils.ConstantesWebDAO;
import com.promotick.lafabril.dao.web.definition.ProductoNovedosoDaoDefinition;
import com.promotick.lafabril.common.ConstantesCommon;
import com.promotick.lafabril.model.util.FiltroProductoNovedoso;
import com.promotick.lafabril.model.web.ProductoNovedoso;
import com.promotick.configuration.utils.dao.DaoBuilder;
import com.promotick.configuration.utils.dao.DaoGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.Types;
import java.util.List;

@Repository
public class ProductoNovedosoDaoImpl extends DaoGenerator implements ProductoNovedosoDao {

    private ProductoNovedosoDaoDefinition productoNovedosoDaoDefinition;

    @Autowired
    public ProductoNovedosoDaoImpl(JdbcTemplate jdbcTemplate, ProductoNovedosoDaoDefinition productoNovedosoDaoDefinition) {
        super(jdbcTemplate);
        this.productoNovedosoDaoDefinition = productoNovedosoDaoDefinition;
    }

    @Override
    public List<ProductoNovedoso> listarProductoNovedoso(FiltroProductoNovedoso filtroProductoNovedoso) {
        return DaoBuilder.getInstance(this)
                .setLogger(ConstantesCommon.LOGGER)
                .setSchema(ConstantesAdminDAO.SCHEMA_NAME)
                .setProcedureName(ConstantesAdminDAO.SP_PRODUCTO_NOVEDOSO_LISTAR)
                .addParameter("var_id_producto", Types.INTEGER, filtroProductoNovedoso.getIdProducto())
                .addParameter("var_id_catalogo", Types.INTEGER, filtroProductoNovedoso.getIdCatalogo())
                .addParameter("var_categorias", Types.VARCHAR, filtroProductoNovedoso.getCategorias())
                .addParameter("var_inicio", Types.INTEGER, filtroProductoNovedoso.getStart())
                .addParameter("var_tamanio", Types.INTEGER, filtroProductoNovedoso.getLength())
                .setDaoDefinition(productoNovedosoDaoDefinition)
                .build();
    }


    @Override
    public List<ProductoNovedoso> listarProductoNovedosoWeb(FiltroProductoNovedoso filtroProductoNovedoso) {
        return DaoBuilder.getInstance(this)
                .setLogger(ConstantesCommon.LOGGER)
                .setSchema(ConstantesWebDAO.SCHEMA_NAME)
                .setProcedureName(ConstantesAdminDAO.SP_PRODUCTO_NOVEDOSO_LISTAR)
                .addParameter("var_id_producto", Types.INTEGER, filtroProductoNovedoso.getIdProducto())
                .addParameter("var_id_catalogo", Types.INTEGER, filtroProductoNovedoso.getIdCatalogo())
                .addParameter("var_categorias", Types.VARCHAR, filtroProductoNovedoso.getCategorias())
                .addParameter("var_inicio", Types.INTEGER, filtroProductoNovedoso.getStart())
                .addParameter("var_tamanio", Types.INTEGER, filtroProductoNovedoso.getLength())
                .setDaoDefinition(productoNovedosoDaoDefinition)
                .build();
    }

    @Override
    public Integer contarProductoNovedoso(FiltroProductoNovedoso filtroProductoNovedoso) {
        return DaoBuilder.getInstance(this)
                .setLogger(ConstantesCommon.LOGGER)
                .setSchema(ConstantesAdminDAO.SCHEMA_NAME)
                .setProcedureName(ConstantesAdminDAO.SP_PRODUCTO_NOVEDOSO_CONTAR)
                .addParameter("var_id_producto", Types.INTEGER, filtroProductoNovedoso.getIdProducto())
                .addParameter("var_id_catalogo", Types.INTEGER, filtroProductoNovedoso.getIdCatalogo())
                .addParameter("var_categorias", Types.VARCHAR, filtroProductoNovedoso.getCategorias())
                .setReturnDaoParameter("resultado", Types.INTEGER)
                .build(Integer.class);
    }
}
