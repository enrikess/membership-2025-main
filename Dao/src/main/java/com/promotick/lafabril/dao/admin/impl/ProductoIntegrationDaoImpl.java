package com.promotick.lafabril.dao.admin.impl;

import com.promotick.lafabril.common.ConstantesCommon;
import com.promotick.lafabril.dao.admin.ProductoIntegrationDao;
import com.promotick.lafabril.dao.utils.ConstantesAdminDAO;
import com.promotick.lafabril.model.web.Producto;
import com.promotick.lafabril.model.web.ProductoCatalogo;
import com.promotick.configuration.utils.dao.DaoBuilder;
import com.promotick.configuration.utils.dao.DaoGenerator;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.Types;

@Repository
public class ProductoIntegrationDaoImpl extends DaoGenerator implements ProductoIntegrationDao {

    public ProductoIntegrationDaoImpl(JdbcTemplate jdbcTemplate) {
        super(jdbcTemplate);
    }

    @Override
    public Integer actualizarProducto(Producto producto) {
        return DaoBuilder.getInstance(this)
                .setLogger(ConstantesCommon.LOGGER)
                .setSchema(ConstantesAdminDAO.SCHEMA_NAME)
                .setProcedureName(ConstantesAdminDAO.SP_PRODUCTO_ACTUALIZAR_INTEGRATION)
                .addParameter("var_accion", Types.INTEGER, producto.getAccion())
                .addParameter("var_codigo_producto", Types.VARCHAR, producto.getCodigoProducto())
                .addParameter("var_codigo_web", Types.VARCHAR, producto.getCodigoWeb())
                .addParameter("var_nombre_producto", Types.VARCHAR, producto.getNombreProducto())
                .addParameter("var_descripcion_producto", Types.VARCHAR, producto.getDescripcionProducto())
                .addParameter("var_codigo_marca", Types.VARCHAR, producto.getMarca() != null ? producto.getMarca().getCodigoMarca(): "")
                .addParameter("var_codigo_categoria", Types.VARCHAR, producto.getCategoria() != null ? producto.getCategoria().getCodigoCategoria() : "")
                .addParameter("var_imagen_uno", Types.VARCHAR, producto.getImagenUno())
                .addParameter("var_imagen_detalle", Types.VARCHAR, producto.getImagenDetalle())
                .addParameter("var_puntos_producto", Types.INTEGER, producto.getPuntosProducto())
                .addParameter("var_precio_producto", Types.DOUBLE, producto.getPrecioProducto())
                .addParameter("var_estado_producto", Types.INTEGER, producto.getEstadoProducto())
                .addParameter("var_terminos_producto", Types.VARCHAR, producto.getTerminosProducto())
                .addParameter("var_especificaciones_producto", Types.VARCHAR, producto.getEspecificacionesProducto())
                .addParameter("var_usuario", Types.VARCHAR, "")
                .addParameter("var_id_tipo_producto", Types.INTEGER, producto.getTipoProducto() != null ? producto.getTipoProducto().getIdTipoProducto() : null)
                .addParameter("var_stock_producto", Types.INTEGER, -1)
                .addParameter("var_id_netsuite", Types.VARCHAR, producto.getIdNetsuite())
                .addParameter("var_codigo_catalogo", Types.VARCHAR, producto.getCodigoCatalogo())
                .addParameter("var_tags", Types.VARCHAR, producto.getTags())
                .addParameter("var_puntos_regular", Types.INTEGER, producto.getPuntosRegular())
                .addParameter("var_id_tag_producto", Types.INTEGER, producto.getTagProducto() != null  ? producto.getTagProducto().getIdTagProducto() : null)
                .addParameter("var_nombre_tag", Types.VARCHAR, producto.getNombreTag())
                .addParameter("var_imagen_envio_express", Types.INTEGER, producto.getImagenEnvioExpress())
                .setReturnDaoParameter("resultado", Types.INTEGER)
                .build(Integer.class);
    }

    @Override
    public Integer registrarProductoCatalogo(ProductoCatalogo productoCatalogo) {
        return DaoBuilder.getInstance(this)
                .setLogger(ConstantesCommon.LOGGER)
                .setSchema(ConstantesAdminDAO.SCHEMA_NAME)
                .setProcedureName(ConstantesAdminDAO.SP_PRODUCTO_CATALOGO_REGISTRAR_INTEGRATION)
                .addParameter("var_codigo_catalogo", Types.VARCHAR, productoCatalogo.getCatalogo().getCodigoCatalogo())
                .addParameter("var_id_producto", Types.INTEGER, productoCatalogo.getProducto().getIdProducto())
                .setReturnDaoParameter("resultado", Types.INTEGER)
                .build(Integer.class);
    }
}
