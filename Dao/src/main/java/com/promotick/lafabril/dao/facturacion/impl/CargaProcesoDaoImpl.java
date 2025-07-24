package com.promotick.lafabril.dao.facturacion.impl;

import com.promotick.lafabril.common.ConstantesCommon;
import com.promotick.lafabril.dao.facturacion.definition.FormBulkParticipantesDaoDefinition;
import com.promotick.lafabril.dao.facturacion.CargaProcesoDao;
import com.promotick.lafabril.dao.facturacion.definition.FormBulkFacturasDaoDefinition;
import com.promotick.lafabril.dao.utils.ConstantesFacturacionDAO;
import com.promotick.lafabril.model.facturacion.CargaProceso;
import com.promotick.lafabril.model.util.descarga.FormBulkFacturas;
import com.promotick.lafabril.model.util.descarga.FormBulkParticipantes;
import com.promotick.configuration.utils.dao.DaoBuilder;
import com.promotick.configuration.utils.dao.DaoGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.Types;
import java.util.List;

@Repository
public class CargaProcesoDaoImpl extends DaoGenerator implements CargaProcesoDao {

    private FormBulkParticipantesDaoDefinition formBulkParticipantesDaoDefinition;
    private FormBulkFacturasDaoDefinition formBulkFacturasDaoDefinition;

    @Autowired
    public CargaProcesoDaoImpl(JdbcTemplate jdbcTemplate, FormBulkParticipantesDaoDefinition formBulkParticipantesDaoDefinition, FormBulkFacturasDaoDefinition formBulkFacturasDaoDefinition) {
        super(jdbcTemplate);
        this.formBulkParticipantesDaoDefinition = formBulkParticipantesDaoDefinition;
        this.formBulkFacturasDaoDefinition = formBulkFacturasDaoDefinition;
    }

    @Override
    public Integer registrarCargaProceso(CargaProceso cargaProceso) {
        return DaoBuilder.getInstance(this)
                .setLogger(ConstantesCommon.LOGGER)
                .setSchema(ConstantesFacturacionDAO.SCHEMA_NAME)
                .setProcedureName(ConstantesFacturacionDAO.SP_CARGA_PROCESO_REGISTRAR)
                .addParameter("var_id_proceso", Types.INTEGER, cargaProceso.getProceso().getIdProceso())
                .addParameter("var_id_carga", Types.INTEGER, cargaProceso.getCarga() != null ? cargaProceso.getCarga().getIdCarga() : null)
                .addParameter("var_estado_carga_proceso", Types.INTEGER, cargaProceso.getEstadoCargaProceso())
                .addParameter("var_mensaje", Types.VARCHAR, cargaProceso.getMensaje())
                .setReturnDaoParameter("resultado", Types.INTEGER)
                .build(Integer.class);
    }

    @Override
    public Integer actualizarCargaProceso(CargaProceso cargaProceso) {
        return DaoBuilder.getInstance(this)
                .setLogger(ConstantesCommon.LOGGER)
                .setSchema(ConstantesFacturacionDAO.SCHEMA_NAME)
                .setProcedureName(ConstantesFacturacionDAO.SP_CARGA_PROCESO_ACTUALIZAR)
                .addParameter("var_id_carga_proceso", Types.INTEGER, cargaProceso.getIdCargaProceso())
                .addParameter("var_id_proceso", Types.INTEGER, cargaProceso.getProceso().getIdProceso())
                .addParameter("var_id_carga", Types.INTEGER, cargaProceso.getCarga() != null ? cargaProceso.getCarga().getIdCarga() : null)
                .addParameter("var_estado_carga_proceso", Types.INTEGER, cargaProceso.getEstadoCargaProceso())
                .addParameter("var_mensaje", Types.VARCHAR, cargaProceso.getMensaje())
                .addParameter("var_total", Types.INTEGER, cargaProceso.getTotal())
                .addParameter("var_correctos", Types.INTEGER, cargaProceso.getCorrectos())
                .addParameter("var_errores", Types.INTEGER, cargaProceso.getErrores())
                .setReturnDaoParameter("resultado", Types.INTEGER)
                .build(Integer.class);
    }

    @Override
    public Integer bulkParticipantesRegistro(FormBulkParticipantes formBulkParticipantes) {
        return DaoBuilder.getInstance(this)
                .setLogger(ConstantesCommon.LOGGER)
                .setSchema(ConstantesFacturacionDAO.SCHEMA_NAME)
                .setProcedureName(ConstantesFacturacionDAO.SP_BULK_PARTICIPANTE_REGISTRO)
                .addParameter("var_id_carga_proceso", Types.INTEGER, formBulkParticipantes.getCargaProceso().getIdCargaProceso())
                .addParameter("var_fecha_info", Types.VARCHAR, formBulkParticipantes.getFechaInfo())
                .addParameter("var_tipo_documento", Types.VARCHAR, formBulkParticipantes.getTipoDocumento())
                .addParameter("var_cli_ruc", Types.VARCHAR, formBulkParticipantes.getCliRuc())
                .addParameter("var_nombres", Types.VARCHAR, formBulkParticipantes.getNombres())
                .addParameter("var_ap_paterno", Types.VARCHAR, formBulkParticipantes.getApPaterno())
                .addParameter("var_ap_materno", Types.VARCHAR, formBulkParticipantes.getApMaterno())
                .addParameter("var_telefono", Types.VARCHAR, formBulkParticipantes.getTelefono())
                .addParameter("var_movil", Types.VARCHAR, formBulkParticipantes.getMovil())
                .addParameter("var_email", Types.VARCHAR, formBulkParticipantes.getEmail())
                .addParameter("var_meta_anual", Types.VARCHAR, formBulkParticipantes.getMetaAnual())
                .addParameter("var_meta_trimestral", Types.VARCHAR, formBulkParticipantes.getMetaTrimestral())
                .addParameter("var_razon_social", Types.VARCHAR, formBulkParticipantes.getRazonSocial())
                .addParameter("var_clicodigo", Types.VARCHAR, formBulkParticipantes.getClicodigo())
                .addParameter("var_cliente_vencido", Types.VARCHAR, formBulkParticipantes.getClienteVencido())
                .setReturnDaoParameter("resultado", Types.INTEGER)
                .build(Integer.class);
    }

    @Override
    public List<FormBulkParticipantes> bulkParticipantesProcesar(CargaProceso cargaProceso) {
        return DaoBuilder.getInstance(this)
                .setLogger(ConstantesCommon.LOGGER)
                .setSchema(ConstantesFacturacionDAO.SCHEMA_NAME)
                .setProcedureName(ConstantesFacturacionDAO.SP_BULK_PARTICIPANTE_PROCESAR)
                .addParameter("var_carga_proceso", Types.INTEGER, cargaProceso.getIdCargaProceso())
                .setDaoDefinition(formBulkParticipantesDaoDefinition)
                .build();
    }

    @Override
    public Integer bulkFacturasRegistro(FormBulkFacturas formBulkFacturas) {
        return DaoBuilder.getInstance(this)
                .setLogger(ConstantesCommon.LOGGER)
                .setSchema(ConstantesFacturacionDAO.SCHEMA_NAME)
                .setProcedureName(ConstantesFacturacionDAO.SP_BULK_FACTURAS_REGISTRO)
                .addParameter("var_id_carga_proceso", Types.INTEGER, formBulkFacturas.getCargaProceso().getIdCargaProceso())
                .addParameter("var_fecha_info", Types.VARCHAR, formBulkFacturas.getFechaInfo())
                .addParameter("var_factura_tipo", Types.VARCHAR, formBulkFacturas.getFacturaTipo())
                .addParameter("var_cli_ruc", Types.VARCHAR, formBulkFacturas.getCliRuc())
                .addParameter("var_fecha_emision", Types.VARCHAR, formBulkFacturas.getFechaEmision())
                .addParameter("var_nro_factura", Types.VARCHAR, formBulkFacturas.getNroFactura())
                .addParameter("var_ratio_puntos", Types.VARCHAR, formBulkFacturas.getRatioPuntos())
                .addParameter("var_monto_factura", Types.VARCHAR, formBulkFacturas.getMontoFactura())
                .setReturnDaoParameter("resultado", Types.INTEGER)
                .build(Integer.class);
    }

    @Override
    public List<FormBulkFacturas> bulkFacturasProcesar(CargaProceso cargaProceso) {
        return DaoBuilder.getInstance(this)
                .setLogger(ConstantesCommon.LOGGER)
                .setSchema(ConstantesFacturacionDAO.SCHEMA_NAME)
                .setProcedureName(ConstantesFacturacionDAO.SP_BULK_FACTURAS_PROCESAR)
                .addParameter("var_carga_proceso", Types.INTEGER, cargaProceso.getIdCargaProceso())
                .setDaoDefinition(formBulkFacturasDaoDefinition)
                .build();
    }
}
