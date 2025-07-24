package com.promotick.lafabril.dao.admin.impl;

import com.promotick.lafabril.dao.admin.ProductoPromocionDao;
import com.promotick.lafabril.dao.utils.ConstantesAdminDAO;
import com.promotick.lafabril.dao.utils.ConstantesWebDAO;
import com.promotick.lafabril.dao.web.definition.ProductoPromocionDaoDefinition;
import com.promotick.configuration.utils.dao.DaoBuilder;
import com.promotick.configuration.utils.dao.DaoGenerator;
import com.promotick.lafabril.common.ConstantesCommon;
import com.promotick.lafabril.model.util.FiltroProductoPromocion;
import com.promotick.lafabril.model.web.ProductoPromocion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.Types;
import java.util.List;

@Repository
public class ProductoPromocionDaoImpl extends DaoGenerator implements ProductoPromocionDao {

    private ProductoPromocionDaoDefinition productoPromocionDaoDefinition;

    @Autowired
    public ProductoPromocionDaoImpl(ProductoPromocionDaoDefinition productoPromocionDaoDefinition, JdbcTemplate jdbcTemplate) {
        super(jdbcTemplate);
        this.productoPromocionDaoDefinition = productoPromocionDaoDefinition;
    }


    @Override
    public List<ProductoPromocion> listarProductoPromocion(FiltroProductoPromocion filtroProductoPromocion) {
        return DaoBuilder.getInstance(this)
                .setLogger(ConstantesCommon.LOGGER)
                .setSchema(ConstantesAdminDAO.SCHEMA_NAME)
                .setProcedureName(ConstantesAdminDAO.SP_PRODUCTO_PROMOCION_LISTAR)
                .addParameter("var_id_producto", Types.INTEGER, filtroProductoPromocion.getIdProducto())
                .addParameter("var_id_catalogo", Types.INTEGER, filtroProductoPromocion.getIdCatalogo())
                .addParameter("var_categorias", Types.VARCHAR, filtroProductoPromocion.getCategorias())
                .addParameter("var_inicio", Types.INTEGER, filtroProductoPromocion.getStart())
                .addParameter("var_tamanio", Types.INTEGER, filtroProductoPromocion.getLength())
                .setDaoDefinition(productoPromocionDaoDefinition)
                .build();
    }

    @Override
    public List<ProductoPromocion> listarProductoPromocionWeb(FiltroProductoPromocion filtroProductoPromocion) {
        return DaoBuilder.getInstance(this)
                .setLogger(ConstantesCommon.LOGGER)
                .setSchema(ConstantesWebDAO.SCHEMA_NAME)
                .setProcedureName(ConstantesAdminDAO.SP_PRODUCTO_PROMOCION_LISTAR)
                .addParameter("var_id_producto", Types.INTEGER, filtroProductoPromocion.getIdProducto())
                .addParameter("var_id_catalogo", Types.INTEGER, filtroProductoPromocion.getIdCatalogo())
                .addParameter("var_categorias", Types.VARCHAR, filtroProductoPromocion.getCategorias())
                .addParameter("var_inicio", Types.INTEGER, filtroProductoPromocion.getStart())
                .addParameter("var_tamanio", Types.INTEGER, filtroProductoPromocion.getLength())
                .setDaoDefinition(productoPromocionDaoDefinition)
                .build();
    }

    @Override
    public Integer contarProductoPromocion(FiltroProductoPromocion filtroBusqueda) {
        return DaoBuilder.getInstance(this)
                .setLogger(ConstantesCommon.LOGGER)
                .setSchema(ConstantesAdminDAO.SCHEMA_NAME)
                .setProcedureName(ConstantesAdminDAO.SP_PRODUCTO_PROMOCION_CONTAR)
                .addParameter("var_id_producto", Types.INTEGER, filtroBusqueda.getIdProducto())
                .addParameter("var_id_catalogo", Types.INTEGER, filtroBusqueda.getIdCatalogo())
                .addParameter("var_categorias", Types.VARCHAR, filtroBusqueda.getCategorias())
                .setReturnDaoParameter("resultado", Types.INTEGER)
                .build(Integer.class);
    }

    @Override
    public Integer eliminarProductoPromocion(Integer idProductoPromocion) {
        return DaoBuilder.getInstance(this)
                .setLogger(ConstantesCommon.LOGGER)
                .setSchema(ConstantesAdminDAO.SCHEMA_NAME)
                .setProcedureName(ConstantesAdminDAO.SP_PRODUCTO_PROMOCION_ELIMINAR)
                .addParameter("var_id_producto_promocion", Types.INTEGER, idProductoPromocion)
                .setReturnDaoParameter("resultado", Types.INTEGER)
                .build(Integer.class);
    }

    @Override
    public Integer registrarProductoPromocion(ProductoPromocion productoPromocion) {
        return DaoBuilder.getInstance(this)
                .setLogger(ConstantesCommon.LOGGER)
                .setSchema(ConstantesAdminDAO.SCHEMA_NAME)
                .setProcedureName(ConstantesAdminDAO.SP_PRODUCTO_PROMOCION_REGISTRAR)
                .addParameter("var_id_producto", Types.INTEGER, productoPromocion.getProducto().getIdProducto())
                .addParameter("var_id_catalogo", Types.INTEGER, productoPromocion.getCatalogo().getIdCatalogo())
                .addParameter("var_estado_producto_promocion", Types.INTEGER, productoPromocion.getProducto().getEstadoProducto())
                .addParameter("var_usuario_creacion", Types.VARCHAR, productoPromocion.getAuditoria().getUsuarioCreacion())
                .setReturnDaoParameter("resultado", Types.INTEGER)
                .build(Integer.class);
    }
}
