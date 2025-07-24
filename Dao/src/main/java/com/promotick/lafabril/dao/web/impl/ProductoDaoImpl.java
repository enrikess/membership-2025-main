package com.promotick.lafabril.dao.web.impl;

import com.promotick.lafabril.dao.utils.ConstantesAdminDAO;
import com.promotick.lafabril.dao.utils.ConstantesFacturacionDAO;
import com.promotick.lafabril.dao.utils.ConstantesWebDAO;
import com.promotick.lafabril.dao.web.definition.ProductoDaoDefinition;
import com.promotick.lafabril.dao.web.definition.TagProductoDaoDefinition;
import com.promotick.lafabril.dao.web.ProductoDao;
import com.promotick.lafabril.common.ConstantesCommon;
import com.promotick.lafabril.dao.admin.definition.ReporteProductoExcelDaoDefinition;
import com.promotick.lafabril.dao.web.definition.ProductoCatalogoDaoDefinition;
import com.promotick.lafabril.dao.web.definition.RangoPuntosDaoDefinition;
import com.promotick.lafabril.model.reporte.ReporteProducto;
import com.promotick.lafabril.model.util.FiltroCatalogo;
import com.promotick.lafabril.model.util.FiltroProducto;
import com.promotick.lafabril.model.util.RangoPuntos;
import com.promotick.lafabril.model.web.*;
import com.promotick.configuration.utils.dao.DaoBuilder;
import com.promotick.configuration.utils.dao.DaoGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.Types;
import java.util.List;

@Repository
public class ProductoDaoImpl extends DaoGenerator implements ProductoDao {

    private ProductoDaoDefinition productoDaoDefinition;
    private TagProductoDaoDefinition tagProductoDaoDefinition;
    private RangoPuntosDaoDefinition rangoPuntosDaoDefinition;
    private ProductoCatalogoDaoDefinition productoCatalogoDaoDefinition;
    private ReporteProductoExcelDaoDefinition reporteProductoExcelDaoDefinition;

    @Autowired
    public ProductoDaoImpl(JdbcTemplate jdbcTemplate, TagProductoDaoDefinition tagProductoDaoDefinition,ProductoDaoDefinition productoDaoDefinition, RangoPuntosDaoDefinition rangoPuntosDaoDefinition, ProductoCatalogoDaoDefinition productoCatalogoDaoDefinition, ReporteProductoExcelDaoDefinition reporteProductoExcelDaoDefinition) {
        super(jdbcTemplate);
        this.productoDaoDefinition = productoDaoDefinition;
        this.tagProductoDaoDefinition = tagProductoDaoDefinition;
        this.rangoPuntosDaoDefinition = rangoPuntosDaoDefinition;
        this.productoCatalogoDaoDefinition = productoCatalogoDaoDefinition;
        this.reporteProductoExcelDaoDefinition = reporteProductoExcelDaoDefinition;
    }

    @Override
    public List<Producto> listarProductos(FiltroProducto filtroProducto) {
        return DaoBuilder.getInstance(this)
                .setLogger(ConstantesCommon.LOGGER)
                .setSchema(ConstantesAdminDAO.SCHEMA_NAME)
                .setProcedureName(ConstantesAdminDAO.SP_PRODUCTOS_LISTAR)
                .addParameter("var_id_producto", Types.INTEGER, filtroProducto.getIdProducto())
                .addParameter("var_id_catalogo", Types.INTEGER, filtroProducto.getIdCatalogo())
                .addParameter("var_tamanio", Types.INTEGER, filtroProducto.getLength())
                .addParameter("var_inicio", Types.INTEGER, filtroProducto.getStart())
                .addParameter("var_buscar", Types.VARCHAR, filtroProducto.getBuscar())
                .setDaoDefinition(productoDaoDefinition)
                .build();
    }

    @Override
    public Integer actualizarProducto(Producto producto) {
        return DaoBuilder.getInstance(this)
                .setLogger(ConstantesCommon.LOGGER)
                .setSchema(ConstantesAdminDAO.SCHEMA_NAME)
                .setProcedureName(ConstantesAdminDAO.SP_PRODUCTO_ACTUALIZAR)
                .addParameter("var_accion", Types.INTEGER, producto.getAccion())
                .addParameter("var_id_producto", Types.INTEGER, producto.getIdProducto())
                .addParameter("var_codigo_web", Types.VARCHAR, producto.getCodigoWeb())
                .addParameter("var_nombre_producto", Types.VARCHAR, producto.getNombreProducto())
                .addParameter("var_descripcion_producto", Types.VARCHAR, producto.getDescripcionProducto())
                .addParameter("var_id_marca", Types.INTEGER, producto.getMarca() != null ? producto.getMarca().getIdMarca() : null)
                .addParameter("var_id_categoria", Types.INTEGER, producto.getCategoria() != null ? producto.getCategoria().getIdCategoria() : null)
                .addParameter("var_imagen_uno", Types.VARCHAR, producto.getImagenUno())
                .addParameter("var_imagen_detalle", Types.VARCHAR, producto.getImagenDetalle())
                .addParameter("var_puntos_producto", Types.INTEGER, producto.getPuntosProducto())
                .addParameter("var_precio_producto", Types.DOUBLE, producto.getPrecioProducto())
                .addParameter("var_estado_producto", Types.INTEGER, producto.getEstadoProducto())
                .addParameter("var_terminos_producto", Types.VARCHAR, producto.getTerminosProducto())
                .addParameter("var_especificaciones_producto", Types.VARCHAR, producto.getEspecificacionesProducto())
                .addParameter("var_usuario", Types.VARCHAR, producto.getAuditoria().getUsuarioActualizacion())
                .addParameter("var_id_tipo_producto", Types.INTEGER, producto.getTipoProducto() != null ? producto.getTipoProducto().getIdTipoProducto() : null)
                .setReturnDaoParameter("resultado", Types.INTEGER)
                .build(Integer.class);
    }

    @Override
    public Integer registrarProducto(Producto producto) {
        return DaoBuilder.getInstance(this)
                .setLogger(ConstantesCommon.LOGGER)
                .setSchema(ConstantesAdminDAO.SCHEMA_NAME)
                .setProcedureName(ConstantesAdminDAO.SP_PRODUCTO_REGISTRAR)
                .addParameter("var_accion", Types.INTEGER, producto.getAccion())
                .addParameter("var_id_producto", Types.INTEGER, producto.getIdProducto())
                .addParameter("var_codigo_web", Types.VARCHAR, producto.getCodigoWeb())
                .addParameter("var_nombre_producto", Types.VARCHAR, producto.getNombreProducto())
                .addParameter("var_descripcion_producto", Types.VARCHAR, producto.getDescripcionProducto())
                .addParameter("var_id_marca", Types.INTEGER, producto.getMarca() != null ? producto.getMarca().getIdMarca() : null)
                .addParameter("var_id_categoria", Types.INTEGER, producto.getCategoria() != null ? producto.getCategoria().getIdCategoria() : null)
                .addParameter("var_imagen_uno", Types.VARCHAR, producto.getImagenUno())
                .addParameter("var_imagen_detalle", Types.VARCHAR, producto.getImagenDetalle())
                .addParameter("var_puntos_producto", Types.INTEGER, producto.getPuntosProducto())
                .addParameter("var_precio_producto", Types.DOUBLE, producto.getPrecioProducto())
                .addParameter("var_estado_producto", Types.INTEGER, producto.getEstadoProducto())
                .addParameter("var_terminos_producto", Types.VARCHAR, producto.getTerminosProducto())
                .addParameter("var_especificaciones_producto", Types.VARCHAR, producto.getEspecificacionesProducto())
                .addParameter("var_usuario", Types.VARCHAR, producto.getAuditoria().getUsuarioActualizacion())
                .addParameter("var_id_tipo_producto", Types.INTEGER, producto.getTipoProducto() != null ? producto.getTipoProducto().getIdTipoProducto() : null)
                .addParameter("var_stock_producto", Types.INTEGER, producto.getStockProducto())
                .addParameter("var_id_netsuite", Types.VARCHAR, producto.getIdNetsuite())
                .addParameter("var_id_catalogo", Types.INTEGER, producto.getIdCatalogo())
                .addParameter("var_tags", Types.VARCHAR, producto.getTags())
                .addParameter("var_puntos_regular", Types.INTEGER, producto.getPuntosRegular())
                .addParameter("var_id_tag_producto", Types.INTEGER, producto.getTagProducto() != null  ? producto.getTagProducto().getIdTagProducto() : null)
                .addParameter("var_nombre_tag", Types.VARCHAR, producto.getNombreTag())//
                .addParameter("var_imagen_movil", Types.VARCHAR, producto.getImagenMovil())
                .addParameter("var_imagen_movil_destacada", Types.VARCHAR, producto.getImagenMovilDestacada())
                .addParameter("var_imagen_movil_detalle", Types.VARCHAR, producto.getImagenMovilDetalle())
                .addParameter("var_imagen_movil_detalle_dos", Types.VARCHAR, producto.getImagenMovilDetalleDos())
                .addParameter("var_imagen_movil_detalle_tres", Types.VARCHAR, producto.getImagenMovilDetalleTres())
                .addParameter("var_imagen_destacada", Types.VARCHAR, producto.getImagenDestacada())
                .addParameter("var_imagen_express", Types.INTEGER, producto.getImagenEnvioExpress())
                .addParameter("var_pdf_aden", Types.VARCHAR, producto.getPdfAden())
                .setReturnDaoParameter("resultado", Types.INTEGER)
                .build(Integer.class);
    }

    @Override
    public ProductoCatalogo obtenerProductoID(Integer idCatalogo, Integer idProducto, Integer idParticipante) {
        return DaoBuilder.getInstance(this)
                .setLogger(ConstantesCommon.LOGGER)
                .setSchema(ConstantesWebDAO.SCHEMA_NAME)
                .setProcedureName(ConstantesWebDAO.SP_PRODUCTO_OBTENER)
                .addParameter("var_id_catalogo", Types.INTEGER, idCatalogo)
                .addParameter("var_id_producto", Types.INTEGER, idProducto)
                .addParameter("var_id_participante", Types.INTEGER, idParticipante)
                .setDaoDefinition(productoCatalogoDaoDefinition)
                .build(ProductoCatalogo.class);
    }

    @Override
    public List<ProductoCatalogo> listarProductoXCatalogo(FiltroCatalogo filtroCatalogo) {
        return DaoBuilder.getInstance(this)
                .setLogger(ConstantesCommon.LOGGER)
                .setSchema(ConstantesWebDAO.SCHEMA_NAME)
                .setProcedureName(ConstantesWebDAO.SP_PRODUCTO_CATALOGO_LISTAR)
                .addParameter("var_id_catalogo", Types.INTEGER, filtroCatalogo.getIdCatalogo())
                .addParameter("var_id_categoria", Types.INTEGER, filtroCatalogo.getCategoria())
                .addParameter("var_orden", Types.INTEGER, filtroCatalogo.getOrden())
                .addParameter("var_buscar", Types.VARCHAR, filtroCatalogo.getBuscar())
                .addParameter("var_pagina", Types.INTEGER, filtroCatalogo.getPagina())
                .addParameter("var_rango_min", Types.INTEGER, filtroCatalogo.getRangoMin())
                .addParameter("var_rango_max", Types.INTEGER, filtroCatalogo.getRangoMax())
                .addParameter("var_imagen_envio_express", Types.INTEGER, filtroCatalogo.getImagenEnvioExpress())
                .setDaoDefinition(productoCatalogoDaoDefinition)
                .build();
    }

    @Override
    public Integer contarProductoXCatalogo(FiltroCatalogo filtroCatalogo) {
        return DaoBuilder.getInstance(this)
                .setLogger(ConstantesCommon.LOGGER)
                .setSchema(ConstantesWebDAO.SCHEMA_NAME)
                .setProcedureName(ConstantesWebDAO.SP_PRODUCTO_CATALOGO_CONTAR)
                .addParameter("var_id_catalogo", Types.INTEGER, filtroCatalogo.getIdCatalogo())
                .addParameter("var_id_categoria", Types.INTEGER, filtroCatalogo.getCategoria())
                .addParameter("var_buscar", Types.VARCHAR, filtroCatalogo.getBuscar())
                .addParameter("var_rango_min", Types.INTEGER, filtroCatalogo.getRangoMin())
                .addParameter("var_rango_max", Types.INTEGER, filtroCatalogo.getRangoMax())
                .addParameter("var_pagina", Types.INTEGER, filtroCatalogo.getPagina())
                .addParameter("var_imagen_envio_express", Types.INTEGER, filtroCatalogo.getImagenEnvioExpress())
                .setReturnDaoParameter("resultado", Types.INTEGER)
                .build(Integer.class);
    }

    @Override
    public List<ProductoCatalogo> listarProductosInteres(Integer idMarca, Integer idCatalogo) {
        return DaoBuilder.getInstance(this)
                .setLogger(ConstantesCommon.LOGGER)
                .setSchema(ConstantesWebDAO.SCHEMA_NAME)
                .setProcedureName(ConstantesWebDAO.SP_PRODUCTO_CATALOGO_INTERES_LISTAR)
                .addParameter("var_id_catalogo", Types.INTEGER, idCatalogo)
                .addParameter("var_id_marca", Types.INTEGER, idMarca)
                .setDaoDefinition(productoCatalogoDaoDefinition)
                .build();
    }

    @Override
    public RangoPuntos obtenerRangoPuntos(Integer idCatalogo, Integer idCategoria, String buscar) {
        return DaoBuilder.getInstance(this)
                .setLogger(ConstantesCommon.LOGGER)
                .setSchema(ConstantesWebDAO.SCHEMA_NAME)
                .setProcedureName(ConstantesWebDAO.SP_PRODUCTO_OBTENER_RANGO_PUNTOS)
                .addParameter("var_id_catalogo", Types.INTEGER, idCatalogo)
                .addParameter("var_id_categoria", Types.INTEGER, idCategoria)
                .addParameter("var_buscar", Types.VARCHAR, buscar)
                .setDaoDefinition(rangoPuntosDaoDefinition)
                .build(RangoPuntos.class);
    }

    @Override
    public ProductoCatalogo obtenerProductoCategoriaID(Integer idCatalogo, Integer idCategoria, Integer idProducto) {
        return DaoBuilder.getInstance(this)
                .setLogger(ConstantesCommon.LOGGER)
                .setSchema(ConstantesWebDAO.SCHEMA_NAME)
                .setProcedureName(ConstantesWebDAO.SP_PRODUCTO_OBTENER_CATEGORIA)
                .addParameter("var_id_catalogo", Types.INTEGER, idCatalogo)
                .addParameter("var_id_categoria", Types.INTEGER, idCategoria)
                .addParameter("var_id_producto", Types.INTEGER, idProducto)
                .setDaoDefinition(productoCatalogoDaoDefinition)
                .build(ProductoCatalogo.class);
    }

    @Override
    public Integer actualizarStock(PedidoDetalle pedidoDetalle, Catalogo catalogo, Producto producto) {
        return DaoBuilder.getInstance(this)
                .setLogger(ConstantesCommon.LOGGER)
                .setSchema(ConstantesWebDAO.SCHEMA_NAME)
                .setProcedureName(ConstantesWebDAO.SP_PRODUCTO_ACTUALIZAR_STOCK)
                .addParameter("var_id_pedido_detalle", Types.INTEGER, pedidoDetalle.getIdPedidoDetalle())
                .addParameter("var_id_catalogo", Types.INTEGER, catalogo.getIdCatalogo())
                .addParameter("var_id_producto", Types.INTEGER, producto.getIdProducto())
                .setReturnDaoParameter("resultado", Types.INTEGER)
                .build(Integer.class);
    }

    @Override
    public List<ProductoCatalogo> listarProductoCatalogoXProducto(Integer idProducto) {
        return DaoBuilder.getInstance(this)
                .setLogger(ConstantesCommon.LOGGER)
                .setSchema(ConstantesAdminDAO.SCHEMA_NAME)
                .setProcedureName(ConstantesAdminDAO.SP_PRODUCTO_CATALOGO_POR_PRODUCTO)
                .addParameter("var_id_producto", Types.INTEGER, idProducto)
                .setDaoDefinition(productoCatalogoDaoDefinition)
                .build();
    }

    @Override
    public Integer actualizarStockProductoCatalogo(ProductoCatalogo productoCatalogo) {
        return DaoBuilder.getInstance(this)
                .setLogger(ConstantesCommon.LOGGER)
                .setSchema(ConstantesAdminDAO.SCHEMA_NAME)
                .setProcedureName(ConstantesAdminDAO.SP_PRODUCTO_CATALOGO_ACTUALIZAR_STOCK)
                .addParameter("var_id_producto_catalogo", Types.INTEGER, productoCatalogo.getIdProductoCatalogo())
                .addParameter("var_stock", Types.INTEGER, productoCatalogo.getStockProductoCatalogo())
                .addParameter("var_usuario_creacion", Types.VARCHAR, productoCatalogo.getAuditoria().getUsuarioActualizacion())
                .setReturnDaoParameter("resultado", Types.INTEGER)
                .build(Integer.class);
    }

    @Override
    public Integer actualizarEstadoProductoCatalogo(ProductoCatalogo productoCatalogo) {
        return DaoBuilder.getInstance(this)
                .setLogger(ConstantesCommon.LOGGER)
                .setSchema(ConstantesAdminDAO.SCHEMA_NAME)
                .setProcedureName(ConstantesAdminDAO.SP_PRODUCTO_CATALOGO_ACTUALIZAR_ESTADO)
                .addParameter("var_id_producto_catalogo", Types.INTEGER, productoCatalogo.getIdProductoCatalogo())
                .addParameter("var_estado_producto_catalogo", Types.INTEGER, productoCatalogo.getEstadoProductoCatalogo())
                .addParameter("var_usuario_creacion", Types.VARCHAR, productoCatalogo.getAuditoria().getUsuarioActualizacion())
                .setReturnDaoParameter("resultado", Types.INTEGER)
                .build(Integer.class);
    }

    @Override
    public Integer registrarProductoCatalogo(ProductoCatalogo productoCatalogo) {
        return DaoBuilder.getInstance(this)
                .setLogger(ConstantesCommon.LOGGER)
                .setSchema(ConstantesAdminDAO.SCHEMA_NAME)
                .setProcedureName(ConstantesAdminDAO.SP_PRODUCTO_CATALOGO_REGISTRAR)
                .addParameter("var_id_catalogo", Types.INTEGER, productoCatalogo.getCatalogo().getIdCatalogo())
                .addParameter("var_id_producto", Types.INTEGER, productoCatalogo.getProducto().getIdProducto())
                .addParameter("var_stock_producto_catalogo", Types.INTEGER, productoCatalogo.getStockProductoCatalogo())
                .addParameter("var_usuario_registro", Types.VARCHAR, productoCatalogo.getAuditoria().getUsuarioCreacion())
                .setReturnDaoParameter("resultado", Types.INTEGER)
                .build(Integer.class);
    }

    @Override
    public Integer productoCatalogoEnvioEmail(Boolean emailEnviado, String emailObservacion) {
        return DaoBuilder.getInstance(this)
                .setLogger(ConstantesCommon.LOGGER)
                .setSchema(ConstantesFacturacionDAO.SCHEMA_NAME)
                .setProcedureName(ConstantesFacturacionDAO.SP_PRODUCTO_CATALOGO_ENVIO_EMAIL)
                .addParameter("var_email_enviado", Types.BOOLEAN, emailEnviado)
                .addParameter("var_email_observacion", Types.VARCHAR, emailObservacion)
                .setReturnDaoParameter("resultado", Types.INTEGER)
                .build(Integer.class);
    }

    @Override
    public List<Producto> productoNuevosListar(Integer idCatalogo, Integer puntosDisponibles) {
        return DaoBuilder.getInstance(this)
                .setLogger(ConstantesCommon.LOGGER)
                .setSchema(ConstantesFacturacionDAO.SCHEMA_NAME)
                .setProcedureName(ConstantesFacturacionDAO.SP_PRODUCTO_NUEVOS_LISTAR)
                .addParameter("var_id_catalogo", Types.INTEGER, idCatalogo)
                .addParameter("var_puntos_disponibles", Types.INTEGER, puntosDisponibles)
                .setDaoDefinition(productoDaoDefinition)
                .build();
    }

    @Override
    public Integer contarProductos(FiltroProducto filtroProducto) {
        return DaoBuilder.getInstance(this)
                .setLogger(ConstantesCommon.LOGGER)
                .setSchema(ConstantesAdminDAO.SCHEMA_NAME)
                .setProcedureName(ConstantesAdminDAO.SP_PRODUCTO_CONTAR)
                .addParameter("var_buscar", Types.VARCHAR, filtroProducto.getBuscar())
                .setReturnDaoParameter("resultado", Types.INTEGER)
                .build(Integer.class);
    }

    @Override
    public List<ReporteProducto> reporteProductos(FiltroProducto filtroProducto) {
        return DaoBuilder.getInstance(this)
                .setLogger(ConstantesCommon.LOGGER)
                .setSchema(ConstantesAdminDAO.SCHEMA_NAME)
                .setProcedureName(ConstantesAdminDAO.SP_PRODUCTOS_REPORTE_EXCEL)
                .setDaoDefinition(reporteProductoExcelDaoDefinition)
                .build();
    }

    @Override
    public List<TagProducto> listarTags() {
        return DaoBuilder.getInstance(this)
                .setLogger(ConstantesCommon.LOGGER)
                .setSchema(ConstantesAdminDAO.SCHEMA_NAME)
                .setProcedureName(ConstantesAdminDAO.SP_TAG_PRODUCTO_LISTAR)
                .setDaoDefinition(tagProductoDaoDefinition)
                .build();
    }

}
