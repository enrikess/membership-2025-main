package com.promotick.lafabril.dao.facturacion.impl;

import com.promotick.lafabril.dao.facturacion.definition.FacturaDaoDefinition;
import com.promotick.lafabril.dao.facturacion.definition.MetaFacturacionDaoDefinition;
import com.promotick.lafabril.common.ConstantesCommon;
import com.promotick.lafabril.dao.facturacion.definition.FacturaAgrupadoDaoDefinition;
import com.promotick.lafabril.dao.facturacion.FacturaDao;
import com.promotick.lafabril.dao.facturacion.definition.ParticipanteAvanceDaoDefinition;
import com.promotick.lafabril.dao.utils.ConstantesAdminDAO;
import com.promotick.lafabril.dao.utils.ConstantesFacturacionDAO;
import com.promotick.lafabril.model.facturacion.Factura;
import com.promotick.lafabril.model.facturacion.ParticipanteAvance;
import com.promotick.lafabril.model.util.*;
import com.promotick.configuration.utils.dao.DaoBuilder;
import com.promotick.configuration.utils.dao.DaoGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.Types;
import java.util.List;

@Repository
public class FacturaDaoImpl extends DaoGenerator implements FacturaDao {

    private FacturaAgrupadoDaoDefinition facturaAgrupadoDaoDefinition;
    private MetaFacturacionDaoDefinition metaFacturacionDaoDefinition;
    private FacturaDaoDefinition facturaDaoDefinition;
    private ParticipanteAvanceDaoDefinition participanteAvanceDaoDefinition;

    @Autowired
    public FacturaDaoImpl(JdbcTemplate jdbcTemplate, ParticipanteAvanceDaoDefinition participanteAvanceDaoDefinition, FacturaAgrupadoDaoDefinition facturaAgrupadoDaoDefinition, MetaFacturacionDaoDefinition metaFacturacionDaoDefinition, FacturaDaoDefinition facturaDaoDefinition) {
        super(jdbcTemplate);
        this.facturaAgrupadoDaoDefinition = facturaAgrupadoDaoDefinition;
        this.metaFacturacionDaoDefinition = metaFacturacionDaoDefinition;
        this.facturaDaoDefinition = facturaDaoDefinition;
        this.participanteAvanceDaoDefinition = participanteAvanceDaoDefinition;
    }

    @Override
    public List<FacturaAgrupado> listarFacturacionAgrupada(Integer idParticipante, Integer anio) {
        return DaoBuilder.getInstance(this)
                .setLogger(ConstantesCommon.LOGGER)
                .setSchema(ConstantesFacturacionDAO.SCHEMA_NAME)
                .setProcedureName(ConstantesFacturacionDAO.SP_FACTURA_REPORTE_AGRUPADO)
                .addParameter("var_anio", Types.INTEGER, anio)
                .addParameter("var_id_participante", Types.INTEGER, idParticipante)
                .setDaoDefinition(facturaAgrupadoDaoDefinition)
                .build();
    }

    @Override
    public MetaFacturacion obtenerMetaFacturacionParticipante(Integer idParticipante, Integer anio, Integer trimestre) {
        return DaoBuilder.getInstance(this)
                .setLogger(ConstantesCommon.LOGGER)
                .setSchema(ConstantesFacturacionDAO.SCHEMA_NAME)
                .setProcedureName(ConstantesFacturacionDAO.SP_PERIODO_META_OBTENER)
                .addParameter("var_id_participante", Types.INTEGER, idParticipante)
                .addParameter("var_anio", Types.INTEGER, anio)
                .addParameter("var_trimestre", Types.INTEGER, trimestre)
                .setDaoDefinition(metaFacturacionDaoDefinition)
                .build(MetaFacturacion.class);
    }

    @Override
    public List<Factura> listarFacturasParticipante(FiltroFacturaParticipante filtroFacturaParticipante) {
        return DaoBuilder.getInstance(this)
                .setLogger(ConstantesCommon.LOGGER)
                .setSchema(ConstantesFacturacionDAO.SCHEMA_NAME)
                .setProcedureName(ConstantesFacturacionDAO.SP_FACTURA_PARTICIPANTE_LISTAR)
                .addParameter("var_id_participante", Types.INTEGER, filtroFacturaParticipante.getParticipante().getIdParticipante())
                .addParameter("var_tamanio", Types.INTEGER, filtroFacturaParticipante.getLength())
                .addParameter("var_inicio", Types.INTEGER, filtroFacturaParticipante.getStart())
                .addParameter("var_nro_factura", Types.VARCHAR, filtroFacturaParticipante.getNroFactura())
                .setDaoDefinition(facturaDaoDefinition)
                .build();
    }

    @Override
    public Integer contarFacturasParticipante(FiltroFacturaParticipante filtroFacturaParticipante) {
        return DaoBuilder.getInstance(this)
                .setLogger(ConstantesCommon.LOGGER)
                .setSchema(ConstantesFacturacionDAO.SCHEMA_NAME)
                .setProcedureName(ConstantesFacturacionDAO.SP_FACTURA_PARTICIPANTE_CONTAR)
                .addParameter("var_id_participante", Types.INTEGER, filtroFacturaParticipante.getParticipante().getIdParticipante())
                .addParameter("var_nro_factura", Types.VARCHAR, filtroFacturaParticipante.getNroFactura())
                .setReturnDaoParameter("resultado", Types.INTEGER)
                .build(Integer.class);
    }

    @Override
    public List<ParticipanteAvance> reporteVentasListar(FiltroReporteVentas filtroReporteVentas) {
        return DaoBuilder.getInstance(this)
                .setLogger(ConstantesCommon.LOGGER)
                .setSchema(ConstantesAdminDAO.SCHEMA_NAME)
                .setProcedureName(ConstantesAdminDAO.SP_REPORTE_VENTAS_LISTAR)
                .addParameter("var_finicio", Types.VARCHAR, filtroReporteVentas.getFinicio())
                .addParameter("var_ffin", Types.VARCHAR, filtroReporteVentas.getFfin())
                .addParameter("var_inicio", Types.INTEGER, filtroReporteVentas.getStart())
                .addParameter("var_tamanio", Types.INTEGER, filtroReporteVentas.getLength())
                .setDaoDefinition(participanteAvanceDaoDefinition)
                .build();
    }

    @Override
    public Integer reporteVentasContar(FiltroReporteVentas filtroReporteVentas) {
        return DaoBuilder.getInstance(this)
                .setLogger(ConstantesCommon.LOGGER)
                .setSchema(ConstantesAdminDAO.SCHEMA_NAME)
                .setProcedureName(ConstantesAdminDAO.SP_REPORTE_VENTAS_CONTAR)
                .addParameter("var_finicio", Types.VARCHAR, filtroReporteVentas.getFinicio())
                .addParameter("var_ffin", Types.VARCHAR, filtroReporteVentas.getFfin())
                .setReturnDaoParameter("resultado", Types.INTEGER)
                .build(Integer.class);
    }

    @Override
    public List<Factura> reportePuntosAcreditadosListar(FiltroReportePuntosAcreditados filtroReportePuntosAcreditados) {
        return DaoBuilder.getInstance(this)
                .setLogger(ConstantesCommon.LOGGER)
                .setSchema(ConstantesAdminDAO.SCHEMA_NAME)
                .setProcedureName(ConstantesAdminDAO.SP_REPORTE_PUNTOS_ACREDITADOS_LISTAR)
                .addParameter("var_finicio", Types.VARCHAR, filtroReportePuntosAcreditados.getFinicio())
                .addParameter("var_ffin", Types.VARCHAR, filtroReportePuntosAcreditados.getFfin())
                .addParameter("var_inicio", Types.INTEGER, filtroReportePuntosAcreditados.getStart())
                .addParameter("var_tamanio", Types.INTEGER, filtroReportePuntosAcreditados.getLength())
                .setDaoDefinition(facturaDaoDefinition)
                .build();
    }

    @Override
    public Integer reportePuntosAcreditadosContar(FiltroReportePuntosAcreditados filtroReportePuntosAcreditados) {
        return DaoBuilder.getInstance(this)
                .setLogger(ConstantesCommon.LOGGER)
                .setSchema(ConstantesAdminDAO.SCHEMA_NAME)
                .setProcedureName(ConstantesAdminDAO.SP_REPORTE_PUNTOS_ACREDITADOS_CONTAR)
                .addParameter("var_finicio", Types.VARCHAR, filtroReportePuntosAcreditados.getFinicio())
                .addParameter("var_ffin", Types.VARCHAR, filtroReportePuntosAcreditados.getFfin())
                .setReturnDaoParameter("resultado", Types.INTEGER)
                .build(Integer.class);
    }

    @Override
    public Integer facturaEnvioEmail(Integer idParticipante, Boolean emailEnviado, String emailObservacion) {
        return DaoBuilder.getInstance(this)
                .setLogger(ConstantesCommon.LOGGER)
                .setSchema(ConstantesFacturacionDAO.SCHEMA_NAME)
                .setProcedureName(ConstantesFacturacionDAO.SP_FACTURA_ENVIO_EMAIL)
                .addParameter("var_id_participante", Types.INTEGER, idParticipante)
                .addParameter("var_email_enviado", Types.BOOLEAN, emailEnviado)
                .addParameter("var_email_observacion", Types.VARCHAR, emailObservacion)
                .setReturnDaoParameter("resultado", Types.INTEGER)
                .build(Integer.class);
    }

    @Override
    public Integer cargaManualEnvioEmail(Integer idParticipante, Boolean emailEnviado, String emailObservacion) {
        return DaoBuilder.getInstance(this)
                .setLogger(ConstantesCommon.LOGGER)
                .setSchema(ConstantesFacturacionDAO.SCHEMA_NAME)
                .setProcedureName(ConstantesFacturacionDAO.SP_CARGA_MANUAL_ENVIO_EMAIL)
                .addParameter("var_id_participante", Types.INTEGER, idParticipante)
                .addParameter("var_email_enviado", Types.BOOLEAN, emailEnviado)
                .addParameter("var_email_observacion", Types.VARCHAR, emailObservacion)
                .setReturnDaoParameter("resultado", Types.INTEGER)
                .build(Integer.class);
    }

    @Override
    public Integer reporteTycActualizar(FiltroTyc filtroTyc) {
        return DaoBuilder.getInstance(this)
                .setLogger(ConstantesCommon.LOGGER)
                .setSchema(ConstantesAdminDAO.SCHEMA_FACTURACION)
                .setProcedureName(ConstantesAdminDAO.SP_TYC_ACTUALIZAR)
                .addParameter("var_id_catalogo", Types.VARCHAR, filtroTyc.getIdCatalogo())
                .addParameter("var_descripcion", Types.VARCHAR, filtroTyc.getDescripcion())
                .addParameter("var_usuario_creacion", Types.INTEGER, filtroTyc.getUsuarioCreacion())
                .addParameter("var_lista_id", Types.INTEGER, filtroTyc.getListaId())
                .setReturnDaoParameter("resultado", Types.INTEGER)
                .build(Integer.class);
    }
}
