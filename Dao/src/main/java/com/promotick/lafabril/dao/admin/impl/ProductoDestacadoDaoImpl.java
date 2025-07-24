package com.promotick.lafabril.dao.admin.impl;

import com.promotick.lafabril.dao.admin.ProductoDestacadoDao;
import com.promotick.lafabril.dao.utils.ConstantesAdminDAO;
import com.promotick.lafabril.dao.utils.ConstantesWebDAO;
import com.promotick.lafabril.dao.web.definition.ProductoDestacadoDaoDefinition;
import com.promotick.lafabril.common.ConstantesCommon;
import com.promotick.lafabril.model.util.FiltroProductoDestacado;
import com.promotick.lafabril.model.web.ProductoDestacado;
import com.promotick.configuration.utils.dao.DaoBuilder;
import com.promotick.configuration.utils.dao.DaoGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.Types;
import java.util.List;

@Repository
public class ProductoDestacadoDaoImpl extends DaoGenerator implements ProductoDestacadoDao {

    private ProductoDestacadoDaoDefinition productoDestacadoDaoDefinition;

    @Autowired
    public ProductoDestacadoDaoImpl(ProductoDestacadoDaoDefinition productoDestacadoDaoDefinition, JdbcTemplate jdbcTemplate) {
        super(jdbcTemplate);
        this.productoDestacadoDaoDefinition = productoDestacadoDaoDefinition;
    }

    @Override
    public Integer registrarProductoDestacado(ProductoDestacado productoDestacado) {
        return DaoBuilder.getInstance(this)
                .setLogger(ConstantesCommon.LOGGER)
                .setSchema(ConstantesAdminDAO.SCHEMA_NAME)
                .setProcedureName(ConstantesAdminDAO.SP_PRODUCTO_CATEGORIA_REGISTRAR)
                .addParameter("var_id_producto", Types.INTEGER, productoDestacado.getProducto().getIdProducto())
                .addParameter("var_id_catalogo", Types.INTEGER, productoDestacado.getCatalogo().getIdCatalogo())
                .addParameter("var_estado_producto_destacado", Types.INTEGER, productoDestacado.getProducto().getEstadoProducto())
                .addParameter("var_usuario_creacion", Types.VARCHAR, productoDestacado.getAuditoria().getUsuarioCreacion())
                .setReturnDaoParameter("resultado", Types.INTEGER)
                .build(Integer.class);
    }

    @Override
    public Integer eliminarProductoDestacado(Integer idProductoDestacado) {
        return DaoBuilder.getInstance(this)
                .setLogger(ConstantesCommon.LOGGER)
                .setSchema(ConstantesAdminDAO.SCHEMA_NAME)
                .setProcedureName(ConstantesAdminDAO.SP_PRODUCTO_CATEGORIA_ELIMINAR)
                .addParameter("var_id_producto_destacado", Types.INTEGER, idProductoDestacado)
                .setReturnDaoParameter("resultado", Types.INTEGER)
                .build(Integer.class);
    }

    @Override
    public List<ProductoDestacado> listarProductoDestacado(FiltroProductoDestacado filtroBusqueda) {
        return DaoBuilder.getInstance(this)
                .setLogger(ConstantesCommon.LOGGER)
                .setSchema(ConstantesAdminDAO.SCHEMA_NAME)
                .setProcedureName(ConstantesAdminDAO.SP_PRODUCTO_DESTACADOS_LISTAR)
                .addParameter("var_id_producto", Types.INTEGER, filtroBusqueda.getIdProducto())
                .addParameter("var_id_catalogo", Types.INTEGER, filtroBusqueda.getIdCatalogo())
                .addParameter("var_categorias", Types.VARCHAR, filtroBusqueda.getCategorias())
                .addParameter("var_inicio", Types.INTEGER, filtroBusqueda.getStart())
                .addParameter("var_tamanio", Types.INTEGER, filtroBusqueda.getLength())
                .setDaoDefinition(productoDestacadoDaoDefinition)
                .build();
    }

    @Override
    public List<ProductoDestacado> listarProductoDestacadoWeb(FiltroProductoDestacado filtroBusqueda) {
        return DaoBuilder.getInstance(this)
                .setLogger(ConstantesCommon.LOGGER)
                .setSchema(ConstantesWebDAO.SCHEMA_NAME)
                .setProcedureName(ConstantesAdminDAO.SP_PRODUCTO_DESTACADOS_LISTAR)
                .addParameter("var_id_producto", Types.INTEGER, filtroBusqueda.getIdProducto())
                .addParameter("var_id_catalogo", Types.INTEGER, filtroBusqueda.getIdCatalogo())
                .addParameter("var_categorias", Types.VARCHAR, filtroBusqueda.getCategorias())
                .addParameter("var_inicio", Types.INTEGER, filtroBusqueda.getStart())
                .addParameter("var_tamanio", Types.INTEGER, filtroBusqueda.getLength())
                .setDaoDefinition(productoDestacadoDaoDefinition)
                .build();
    }

    @Override
    public Integer contarProductoDestacado(FiltroProductoDestacado filtroBusqueda) {
        return DaoBuilder.getInstance(this)
                .setLogger(ConstantesCommon.LOGGER)
                .setSchema(ConstantesAdminDAO.SCHEMA_NAME)
                .setProcedureName(ConstantesAdminDAO.SP_PRODUCTO_DESTACADOS_CONTAR)
                .addParameter("var_id_producto", Types.INTEGER, filtroBusqueda.getIdProducto())
                .addParameter("var_id_catalogo", Types.INTEGER, filtroBusqueda.getIdCatalogo())
                .addParameter("var_categorias", Types.VARCHAR, filtroBusqueda.getCategorias())
                .setReturnDaoParameter("resultado", Types.INTEGER)
                .build(Integer.class);
    }

    @Override
    public List<ProductoDestacado> listarProductoCategoria(FiltroProductoDestacado filtroBusqueda) {
        return DaoBuilder.getInstance(this)
                .setLogger(ConstantesCommon.LOGGER)
                .setSchema(ConstantesAdminDAO.SCHEMA_NAME)
                .setProcedureName(ConstantesAdminDAO.SP_PRODUCTO_CATALOGO_LISTADO)
                .addParameter("var_id_catalogo", Types.INTEGER, filtroBusqueda.getIdCatalogo())
                .addParameter("var_nombre_producto", Types.VARCHAR, filtroBusqueda.getNombreProducto())
                .addParameter("var_inicio", Types.INTEGER, filtroBusqueda.getStart())
                .addParameter("var_tamanio", Types.INTEGER, filtroBusqueda.getLength())
                .setDaoDefinition(productoDestacadoDaoDefinition)
                .build();
    }

    @Override
    public Integer contarProductoCategoria(FiltroProductoDestacado filtroBusqueda) {
        return DaoBuilder.getInstance(this)
                .setLogger(ConstantesCommon.LOGGER)
                .setSchema(ConstantesAdminDAO.SCHEMA_NAME)
                .setProcedureName(ConstantesAdminDAO.SP_PRODUCTO_CATALOGO_CONTAR)
                .addParameter("var_id_catalogo", Types.INTEGER, filtroBusqueda.getIdCatalogo())
                .addParameter("var_nombre_producto", Types.VARCHAR, filtroBusqueda.getNombreProducto())
                .setReturnDaoParameter("resultado", Types.INTEGER)
                .build(Integer.class);
    }

}
