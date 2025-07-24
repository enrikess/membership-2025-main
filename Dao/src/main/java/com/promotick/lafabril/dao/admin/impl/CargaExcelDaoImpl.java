package com.promotick.lafabril.dao.admin.impl;

import com.promotick.lafabril.dao.admin.CargaExcelDao;
import com.promotick.lafabril.dao.utils.ConstantesAdminDAO;
import com.promotick.lafabril.dao.utils.ConstantesFacturacionDAO;
import com.promotick.lafabril.common.ConstantesCommon;
import com.promotick.lafabril.model.util.FiltroFaq;
import com.promotick.lafabril.model.util.FiltroTyc;
import com.promotick.lafabril.model.util.form.CargaAcciones;
import com.promotick.lafabril.model.util.form.CargaMetas;
import com.promotick.lafabril.model.util.form.CargaPuntos;
import com.promotick.lafabril.model.util.form.CargaVentas;
import com.promotick.lafabril.model.web.Participante;
import com.promotick.lafabril.model.web.Recomendado;
import com.promotick.configuration.utils.dao.DaoBuilder;
import com.promotick.configuration.utils.dao.DaoGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.Types;

@Repository
public class CargaExcelDaoImpl extends DaoGenerator implements CargaExcelDao {

    @Autowired
    public CargaExcelDaoImpl(JdbcTemplate jdbcTemplate) {
        super(jdbcTemplate);
    }

    @Override
    public Integer registrarCargaPuntos(CargaPuntos cargaPuntos) {
        return DaoBuilder
                .getInstance(this)
                .setLogger(ConstantesCommon.LOGGER)
                .setSchema(ConstantesAdminDAO.SCHEMA_NAME)
                .setProcedureName(ConstantesAdminDAO.SP_CARGA_PUNTOS_EXCEL)
                .addParameter("var_nro_documento", Types.VARCHAR, cargaPuntos.getNroDocumento())
                .addParameter("var_tipo_operacion", Types.INTEGER, cargaPuntos.getTipoOperacion())
                .addParameter("var_monto", Types.INTEGER, cargaPuntos.getMonto())
                .addParameter("var_descripcion", Types.VARCHAR, cargaPuntos.getDescripcion())
                .addParameter("var_id_carga", Types.INTEGER, cargaPuntos.getIdCarga())
                .addParameter("var_fecha_operacion", Types.DATE, cargaPuntos.getFechaOperacion())
                .addParameter("var_usuario_creacion", Types.VARCHAR, cargaPuntos.getUsuarioCreacion())
                .addParameter("var_nro_factura", Types.VARCHAR, cargaPuntos.getNroFactura())
                .setReturnDaoParameter("resultado", Types.INTEGER)
                .build(Integer.class);
    }

    @Override
    public Integer registrarCargaParticipante(Participante participante) {
        return DaoBuilder
                .getInstance(this)
                .setLogger(ConstantesCommon.LOGGER)
                .setSchema(ConstantesAdminDAO.SCHEMA_NAME)
                .setProcedureName(ConstantesAdminDAO.SP_CARGA_PARTICIPANTE_REGISTRAR)
                .addParameter("var_nombres_participante", Types.VARCHAR, participante.getNombresParticipante())
                .addParameter("var_appaterno_participante", Types.VARCHAR, participante.getAppaternoParticipante())
                .addParameter("var_apmaterno_participante", Types.VARCHAR, participante.getApmaternoParticipante())
                .addParameter("var_email", Types.VARCHAR, participante.getEmailParticipante())
                .addParameter("var_clave", Types.VARCHAR, participante.getClaveParticipante())
                .addParameter("var_nro_documento", Types.VARCHAR, participante.getNroDocumento())
                .addParameter("var_telefono", Types.VARCHAR, participante.getTelefonoParticipante())
                .addParameter("var_movil", Types.VARCHAR, participante.getMovilParticipante())
                .addParameter("var_estado", Types.INTEGER, participante.getEstadoParticipante())
                .addParameter("var_id_catalogo", Types.INTEGER, participante.getCatalogo().getIdCatalogo())
                .addParameter("var_id_broker", Types.INTEGER, participante.getBroker().getIdBroker())
                .addParameter("var_ciudad", Types.VARCHAR, participante.getCiudad())
                .addParameter("var_id_region", Types.INTEGER, participante.getRegion().getIdRegion())
                .addParameter("var_estado_canjes", Types.BOOLEAN, participante.getEstadoCanjes())
                .addParameter("var_genero", Types.VARCHAR, participante.getGenero())
                .addParameter("var_fecha_nacimiento", Types.DATE, participante.getFechaNacimiento())
                .addParameter("var_estado_civil", Types.VARCHAR, participante.getEstadoCivil() != null ? participante.getEstadoCivil().toUpperCase() : null)
                .addParameter("var_nro_hijos", Types.INTEGER, participante.getNroHijos())
                .addParameter("var_provincia", Types.VARCHAR, participante.getProvincia())
                .addParameter("var_id_regional", Types.INTEGER, participante.getRegional().getIdRegional())
                .addParameter("var_cedula", Types.VARCHAR, participante.getCedula())
//                .addParameter("var_id_tipo_participante", Types.INTEGER, participante.getIdTipoParticipante()!= null ? participante.getIdTipoParticipante():null)
//                .addParameter("var_cod_cliente", Types.VARCHAR, participante.getCodCliente())
//                .addParameter("var_id_categoria", Types.INTEGER, participante.getIdCategoriaParticipante())
//                .addParameter("var_id_subtipo_participante", Types.INTEGER, participante.getSubtipoParticipante() != null ? participante.getSubtipoParticipante().getIdSubtipoParticipante() : null)
//                .addParameter("var_meta_anual", Types.INTEGER, participante.getMetaAnual())
//                .addParameter("var_id_tipo_documento", Types.INTEGER, participante.getTipoDocumento() != null ? participante.getTipoDocumento().getIdTipoDocumento() : null)
//                .addParameter("var_id_tipo_razon_social", Types.INTEGER, participante.getTipoRazonSocial() != null ? participante.getTipoRazonSocial().getIdRazonsocial() : null)
                .addParameter("var_usuario_creacion", Types.VARCHAR, participante.getAuditoria().getUsuarioCreacion())
                .setReturnDaoParameter("resultado", Types.INTEGER)
                .build(Integer.class);
    }

    @Override
    public Integer registrarCargaMetas(CargaMetas cargaMetas) {
        return DaoBuilder
                .getInstance(this)
                .setLogger(ConstantesCommon.LOGGER)
                .setSchema(ConstantesFacturacionDAO.SCHEMA_NAME)
                .setProcedureName(ConstantesFacturacionDAO.SP_META_TRIMESTRAL_CREAR_ACTUALIZAR)
                .addParameter("var_id_participante_meta", Types.INTEGER, null)
                .addParameter("var_id_participante", Types.INTEGER, null)
                .addParameter("var_id_carga", Types.INTEGER, cargaMetas.getIdCarga())
                .addParameter("var_anio", Types.INTEGER, cargaMetas.getAnio())
                .addParameter("var_mes", Types.INTEGER, cargaMetas.getMes())
                .addParameter("var_nro_documento", Types.VARCHAR, cargaMetas.getNroDocumento())
                .addParameter("var_valor_meta", Types.DOUBLE, cargaMetas.getMeta())
                .addParameter("var_valor_pago", Types.DOUBLE, cargaMetas.getValorPago())
                .addParameter("var_descripcion", Types.VARCHAR, cargaMetas.getDescripcion())
                .addParameter("var_porcentaje_rebate", Types.DOUBLE, cargaMetas.getPorcentajeRebate())
                .addParameter("var_puntos_premio", Types.INTEGER, cargaMetas.getPuntosPremio())
                .addParameter("var_id_productos", Types.VARCHAR, cargaMetas.getIdProductos())
                .addParameter("var_operacion", Types.VARCHAR, cargaMetas.getOperacion())
                .addParameter("var_estado", Types.INTEGER, 1)
                .addParameter("var_usuario_actualizacion", Types.VARCHAR, cargaMetas.getUsuarioCreacion())
                .addParameter("var_lista_id", Types.VARCHAR, cargaMetas.getListaId())
                .setReturnDaoParameter("resultado", Types.INTEGER)
                .build(Integer.class);
    }

    @Override
    public Integer registrarCargaVentas(CargaVentas cargaVentas) {
        return DaoBuilder
                .getInstance(this)
                .setLogger(ConstantesCommon.LOGGER)
                .setSchema(ConstantesFacturacionDAO.SCHEMA_NAME)
                .setProcedureName(ConstantesFacturacionDAO.SP_CARGA_VENTAS_EXCEL_V2)
                .addParameter("var_id_participante_avance", Types.INTEGER, cargaVentas.getIdParticipanteAvance())
                .addParameter("var_nro_documento", Types.VARCHAR, cargaVentas.getNroDocumento())
                .addParameter("var_valor_venta", Types.DOUBLE, cargaVentas.getValorVenta())
                .addParameter("var_fecha_operacion", Types.DATE, cargaVentas.getFechaOperacion())
                .addParameter("var_id_carga", Types.INTEGER, cargaVentas.getIdCarga())
                .addParameter("var_id_periodo", Types.INTEGER, cargaVentas.getIdPeriodo())
                .addParameter("var_descripcion", Types.VARCHAR, cargaVentas.getDescripcion())
                .addParameter("var_usuario_creacion", Types.VARCHAR, cargaVentas.getUsuarioCreacion())
                .addParameter("var_lista_ids", Types.VARCHAR, cargaVentas.getListaIds())
                .addParameter("var_operacion", Types.VARCHAR, cargaVentas.getOperacion())
                .setReturnDaoParameter("resultado", Types.INTEGER)
                .build(Integer.class);
    }

    @Override
    public Integer actualizarTyc(FiltroTyc filtroTyc) {
        return DaoBuilder
                .getInstance(this)
                .setLogger(ConstantesCommon.LOGGER)
                .setSchema(ConstantesFacturacionDAO.SCHEMA_NAME)
                .setProcedureName(ConstantesFacturacionDAO.SP_TYC_ACTUALIZAR)
                .addParameter("var_id_tyc", Types.INTEGER, filtroTyc.getIdTyc())
                .addParameter("var_id_catalogo", Types.INTEGER, filtroTyc.getIdCatalogo())
                .addParameter("var_descripcion", Types.VARCHAR, filtroTyc.getDescripcion())
                .addParameter("var_operacion", Types.VARCHAR, filtroTyc.getOperacion())
                .addParameter("var_usuario_creacion", Types.VARCHAR, filtroTyc.getUsuarioCreacion())
                .addParameter("var_lista_id", Types.VARCHAR, filtroTyc.getListaId())
                .setReturnDaoParameter("resultado", Types.INTEGER)
                .build(Integer.class);
    }

    @Override
    public Integer actualizarFaq(FiltroFaq filtroFaq) {
        return DaoBuilder
                .getInstance(this)
                .setLogger(ConstantesCommon.LOGGER)
                .setSchema(ConstantesFacturacionDAO.SCHEMA_NAME)
                .setProcedureName(ConstantesFacturacionDAO.SP_FAQ_ACTUALIZAR)
                .addParameter("var_id_faq", Types.INTEGER, filtroFaq.getIdFaq())
                .addParameter("var_id_catalogo", Types.INTEGER, filtroFaq.getIdCatalogo())
                .addParameter("var_pregunta", Types.VARCHAR, filtroFaq.getPregunta())
                .addParameter("var_respuesta", Types.VARCHAR, filtroFaq.getRespuesta())
                .addParameter("var_orden", Types.INTEGER, filtroFaq.getOrden())
                .addParameter("var_operacion", Types.VARCHAR, filtroFaq.getOperacion())
                .addParameter("var_usuario_creacion", Types.VARCHAR, filtroFaq.getUsuarioCreacion())
                .addParameter("var_lista_id", Types.VARCHAR, filtroFaq.getListaId())
                .setReturnDaoParameter("resultado", Types.INTEGER)
                .build(Integer.class);
    }

    @Override
    public Integer registrarCargaParticipanteEstado(Participante participante) {
        return DaoBuilder
                .getInstance(this)
                .setLogger(ConstantesCommon.LOGGER)
                .setSchema(ConstantesAdminDAO.SCHEMA_NAME)
                .setProcedureName(ConstantesAdminDAO.SP_CARGA_PARTICIPANTE_ESTADO_REGISTRAR)
                .addParameter("var_nro_documento", Types.VARCHAR, participante.getNroDocumento())
                .addParameter("var_estado", Types.INTEGER, participante.getEstadoParticipante())
                .addParameter("var_estado_canjes", Types.BOOLEAN, participante.getEstadoCanjes())
                .setReturnDaoParameter("resultado", Types.INTEGER)
                .build(Integer.class);
    }

    @Override
    public Integer registrarCargaRecomendadoEstado(Recomendado recomendado) {
        return DaoBuilder
                .getInstance(this)
                .setLogger(ConstantesCommon.LOGGER)
                .setSchema(ConstantesAdminDAO.SCHEMA_NAME)
                .setProcedureName(ConstantesAdminDAO.SP_CARGA_RECOMENDADOS_ESTADO_REGISTRAR)
                .addParameter("var_nro_documento", Types.VARCHAR, recomendado.getNroDocumentoRecomendado())
                .addParameter("var_estado", Types.INTEGER, recomendado.getEstadoRecomendado())
                .setReturnDaoParameter("resultado", Types.INTEGER)
                .build(Integer.class);
    }

    @Override
    public Integer registrarAccionesMetas(CargaAcciones cargaAcciones) {
        return DaoBuilder
                .getInstance(this)
                .setLogger(ConstantesCommon.LOGGER)
                .setSchema(ConstantesFacturacionDAO.SCHEMA_NAME)
                .setProcedureName(ConstantesFacturacionDAO.SP_CARGA_ACCIONES_EXCEL_V2)
                .addParameter("var_id_lafabril_producto", Types.INTEGER, cargaAcciones.getIdLafabrilProducto())
                .addParameter("var_accion", Types.VARCHAR, cargaAcciones.getAccion())
                .addParameter("var_descripcion", Types.VARCHAR, cargaAcciones.getDescripcion())
                .addParameter("var_fecha", Types.DATE, cargaAcciones.getFecha())
                .addParameter("var_cantidad", Types.INTEGER, cargaAcciones.getCantidad())
                .addParameter("var_id_carga", Types.INTEGER, cargaAcciones.getIdCarga())
                .addParameter("var_usuario_creacion", Types.VARCHAR, cargaAcciones.getUsuarioCreacion())
                .addParameter("var_operacion", Types.VARCHAR, cargaAcciones.getOperacion())
                .addParameter("var_lista_id", Types.VARCHAR, cargaAcciones.getListaId())
                .setReturnDaoParameter("resultado", Types.INTEGER)
                .build(Integer.class);
    }
}
