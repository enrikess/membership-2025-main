package com.promotick.lafabril.dao.admin.impl;

import com.promotick.lafabril.dao.admin.ReporteDao;
import com.promotick.lafabril.dao.admin.definition.*;
import com.promotick.lafabril.dao.utils.ConstantesAdminDAO;
import com.promotick.lafabril.dao.web.definition.PedidoDaoDefinition;
import com.promotick.lafabril.dao.admin.definition.*;
import com.promotick.lafabril.common.ConstantesCommon;
import com.promotick.lafabril.dao.web.definition.RecomendadoDaoDefinition;
import com.promotick.lafabril.model.reporte.*;
import com.promotick.lafabril.model.util.*;
import com.promotick.lafabril.model.web.Pedido;
import com.promotick.lafabril.model.web.Recomendado;
import com.promotick.configuration.utils.dao.DaoBuilder;
import com.promotick.configuration.utils.dao.DaoGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.Types;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@Repository
public class ReporteDaoImpl extends DaoGenerator implements ReporteDao {

    private final ReporteAccionesDaoDefinition reporteAccionesDaoDefinition;
    private final ReporteVisitasDaoDefinition reporteVisitasDaoDefinition;
    private final ReporteMetasDaoDefinition reporteMetasDaoDefinition;
    private final PedidoDaoDefinition pedidoDaoDefinition;
    private final ReportePedidoDaoDefinition reportePedidoDaoDefinition;
    private final ReporteEstadoCuentaDaoDefinition reporteEstadoCuentaDaoDefinition;
    private final ReporteFacturasV2DaoDefinition reporteFacturasV2DaoDefinition;
    private final ReporteCapacitacionDaoDefinition reporteCapacitacionDaoDefinition;
    private final ReportePasarelaDaoDefinition reportePasarelaDaoDefinition;
    private final ReporteDescuentoDaoDefinition reporteDescuentoDaoDefinition;
    private final ReporteMundialConsolidadoDaoDefinition reporteMundialConsolidadoDaoDefinition;
    private final ReporteMundialECDaoDefinition reporteMundialECDaoDefinition;
    private final ReporteMundialPollaDaoDefinition reporteMundialPollaDaoDefinition;
    private final ReporteMundialPronosticoDaoDefinition reporteMundialPronosticoDaoDefinition;
    private final ReporteMundialRankingDaoDefinition reporteMundialRankingDaoDefinition;
    private final ReporteMundialTriviaDaoDefinition reporteMundialTriviaDaoDefinition;
    private final RecomendadoDaoDefinition recomendadoDaoDefinition;
    private final ReporteRecomendadoDaoDefinition reporteRecomendadoDaoDefinition;

    @Autowired
    public ReporteDaoImpl(JdbcTemplate jdbcTemplate, ReporteAccionesDaoDefinition reporteAccionesDaoDefinition, ReporteMetasDaoDefinition reporteMetasDaoDefinition, ReporteVisitasDaoDefinition reporteVisitasDaoDefinition, PedidoDaoDefinition pedidoDaoDefinition, ReportePedidoDaoDefinition reportePedidoDaoDefinition, ReporteEstadoCuentaDaoDefinition reporteEstadoCuentaDaoDefinition, ReporteFacturasV2DaoDefinition reporteFacturasV2DaoDefinition, ReporteCapacitacionDaoDefinition reporteCapacitacionDaoDefinition, ReportePasarelaDaoDefinition reportePasarelaDaoDefinition, ReporteDescuentoDaoDefinition reporteDescuentoDaoDefinition, ReporteMundialConsolidadoDaoDefinition reporteMundialConsolidadoDaoDefinition, ReporteMundialECDaoDefinition reporteMundialECDaoDefinition, ReporteMundialPollaDaoDefinition reporteMundialPollaDaoDefinition, ReporteMundialPronosticoDaoDefinition reporteMundialPronosticoDaoDefinition, ReporteMundialRankingDaoDefinition reporteMundialRankingDaoDefinition, ReporteMundialTriviaDaoDefinition reporteMundialTriviaDaoDefinition, RecomendadoDaoDefinition recomendadoDaoDefinition, ReporteRecomendadoDaoDefinition reporteRecomendadoDaoDefinition) {
        super(jdbcTemplate);
        this.reporteVisitasDaoDefinition = reporteVisitasDaoDefinition;
        this.pedidoDaoDefinition = pedidoDaoDefinition;
        this.reportePedidoDaoDefinition = reportePedidoDaoDefinition;
        this.reporteEstadoCuentaDaoDefinition = reporteEstadoCuentaDaoDefinition;
        this.reporteFacturasV2DaoDefinition = reporteFacturasV2DaoDefinition;
        this.reporteCapacitacionDaoDefinition = reporteCapacitacionDaoDefinition;
        this.reportePasarelaDaoDefinition = reportePasarelaDaoDefinition;
        this.reporteDescuentoDaoDefinition = reporteDescuentoDaoDefinition;
        this.reporteMundialConsolidadoDaoDefinition = reporteMundialConsolidadoDaoDefinition;
        this.reporteMundialECDaoDefinition = reporteMundialECDaoDefinition;
        this.reporteMundialPollaDaoDefinition = reporteMundialPollaDaoDefinition;
        this.reporteMundialPronosticoDaoDefinition = reporteMundialPronosticoDaoDefinition;
        this.reporteMundialRankingDaoDefinition = reporteMundialRankingDaoDefinition;
        this.reporteMundialTriviaDaoDefinition = reporteMundialTriviaDaoDefinition;
        this.recomendadoDaoDefinition = recomendadoDaoDefinition;
        this.reporteRecomendadoDaoDefinition = reporteRecomendadoDaoDefinition;
        this.reporteMetasDaoDefinition = reporteMetasDaoDefinition;
        this.reporteAccionesDaoDefinition = reporteAccionesDaoDefinition;
    }

    @Override
    public List<ReporteVisita> listarVisitas(FiltroVisitas filtroVisitas) {
        return DaoBuilder.getInstance(this)
                .setLogger(ConstantesCommon.LOGGER)
                .setSchema(ConstantesAdminDAO.SCHEMA_NAME)
                .setProcedureName(ConstantesAdminDAO.SP_REPORTE_VISITAS_LISTAR)
                .addParameter("var_tipo_operacion", Types.INTEGER, filtroVisitas.getTipoOperacion())
                .addParameter("var_tipo_dispositivo", Types.INTEGER, filtroVisitas.getTipoDispositivo())
                .addParameter("var_id_categoria", Types.INTEGER, filtroVisitas.getIdCategoria())
                .addParameter("var_id_entidad", Types.INTEGER, filtroVisitas.getIdEntidad())
                .addParameter("var_id_catalogo", Types.INTEGER, filtroVisitas.getIdCatalogo())
                .addParameter("var_finicio", Types.VARCHAR, filtroVisitas.getFinicio())
                .addParameter("var_ffin", Types.VARCHAR, filtroVisitas.getFfin())
                .addParameter("var_inicio", Types.INTEGER, filtroVisitas.getStart())
                .addParameter("var_tamanio", Types.INTEGER, filtroVisitas.getLength())
                .setDaoDefinition(reporteVisitasDaoDefinition)
                .build();
    }

    @Override
    public Integer contarVisitas(FiltroVisitas filtroVisitas) {
        return DaoBuilder.getInstance(this)
                .setLogger(ConstantesCommon.LOGGER)
                .setSchema(ConstantesAdminDAO.SCHEMA_NAME)
                .setProcedureName(ConstantesAdminDAO.SP_REPORTE_VISITAS_CONTAR)
                .addParameter("var_tipo_operacion", Types.INTEGER, filtroVisitas.getTipoOperacion())
                .addParameter("var_tipo_dispositivo", Types.INTEGER, filtroVisitas.getTipoDispositivo())
                .addParameter("var_id_categoria", Types.INTEGER, filtroVisitas.getIdCategoria())
                .addParameter("var_id_entidad", Types.INTEGER, filtroVisitas.getIdEntidad())
                .addParameter("var_id_catalogo", Types.INTEGER, filtroVisitas.getIdCatalogo())
                .addParameter("var_finicio", Types.VARCHAR, filtroVisitas.getFinicio())
                .addParameter("var_ffin", Types.VARCHAR, filtroVisitas.getFfin())
                .setReturnDaoParameter("resultado", Types.INTEGER)
                .build(Integer.class);
    }

    @Override
    public List<Map<String, Object>> obtenerEstadoCuenta(FiltroEstadoCuenta filtroEstadoCuenta) {
        return DaoBuilder.getInstance(this)
                .setLogger(ConstantesCommon.LOGGER)
                .setSchema(ConstantesAdminDAO.SCHEMA_NAME)
                .setProcedureName(ConstantesAdminDAO.SP_PARTICIPANTE_OBTENER_ESTADO_CUENTA)
                .addParameter("var_fecha_inicio", Types.VARCHAR, filtroEstadoCuenta.getFinicio())
                .addParameter("var_fecha_fin", Types.VARCHAR, filtroEstadoCuenta.getFfin())
                .addParameter("var_inicio", Types.INTEGER, filtroEstadoCuenta.getStart())
                .addParameter("var_tamanio", Types.INTEGER, filtroEstadoCuenta.getLength())
                .setDaoDefinition(reporteEstadoCuentaDaoDefinition)
                .build();
    }

    @Override
    public Integer obtenerEstadoCuentaTotal() {
        return DaoBuilder.getInstance(this)
                .setLogger(ConstantesCommon.LOGGER)
                .setSchema(ConstantesAdminDAO.SCHEMA_NAME)
                .setProcedureName(ConstantesAdminDAO.SP_PARTICIPANTE_OBTENER_ESTADO_CUENTA_TOTAL)
                .setReturnDaoParameter("resultado", Types.INTEGER)
                .build(Integer.class);
    }

    @Override
    public List<Pedido> listarPedidos(FiltroPedidos filtroPedidos) {
        return DaoBuilder.getInstance(this)
                .setLogger(ConstantesCommon.LOGGER)
                .setSchema(ConstantesAdminDAO.SCHEMA_NAME)
                .setProcedureName(ConstantesAdminDAO.SP_REPORTE_PEDIDO_LISTAR)
                .addParameter("var_finicio", Types.VARCHAR, filtroPedidos.getFinicio())
                .addParameter("var_ffin", Types.VARCHAR, filtroPedidos.getFfin())
                .addParameter("var_inicio", Types.INTEGER, filtroPedidos.getStart())
                .addParameter("var_tamanio", Types.INTEGER, filtroPedidos.getLength())
                .setDaoDefinition(pedidoDaoDefinition)
                .build();

    }

    @Override
    public Integer contarPedidos(FiltroPedidos filtroPedidos) {
        return DaoBuilder.getInstance(this)
                .setLogger(ConstantesCommon.LOGGER)
                .setSchema(ConstantesAdminDAO.SCHEMA_NAME)
                .setProcedureName(ConstantesAdminDAO.SP_REPORTE_PEDIDO_CONTAR)
                .addParameter("var_finicio", Types.VARCHAR, filtroPedidos.getFinicio())
                .addParameter("var_ffin", Types.VARCHAR, filtroPedidos.getFfin())
                .setReturnDaoParameter("resultado", Types.INTEGER)
                .build(Integer.class);
    }

    @Override
    public List<ReportePedido> listarPedidoDetalle(FiltroPedidos filtroPedidos) {
        return DaoBuilder.getInstance(this)
                .setLogger(ConstantesCommon.LOGGER)
                .setSchema(ConstantesAdminDAO.SCHEMA_NAME)
                .setProcedureName(ConstantesAdminDAO.SP_REPORTE_PEDIDO_DETALLE_LISTAR_BY_FECHAS)
                .addParameter("var_finicio", Types.VARCHAR, filtroPedidos.getFinicio())
                .addParameter("var_ffin", Types.VARCHAR, filtroPedidos.getFfin())
                .setDaoDefinition(reportePedidoDaoDefinition)
                .build();

    }

    @Override
    public List<Map<String, Object>> obtenerEstadoCuentaCron(FiltroEstadoCuenta filtroEstadoCuenta, boolean test) {
        return DaoBuilder.getInstance(this)
                .setLogger(ConstantesCommon.LOGGER)
                .setSchema(ConstantesAdminDAO.SCHEMA_NAME)
                .setProcedureName(ConstantesAdminDAO.SP_PARTICIPANTE_OBTENER_ESTADO_CUENTA_CRON)
                .addParameter("var_fecha_inicio", Types.VARCHAR, filtroEstadoCuenta.getFinicio())
                .addParameter("var_fecha_fin", Types.VARCHAR, filtroEstadoCuenta.getFfin())
                .addParameter("var_inicio", Types.INTEGER, filtroEstadoCuenta.getStart())
                .addParameter("var_tamanio", Types.INTEGER, filtroEstadoCuenta.getLength())
                .addParameter("var_test", Types.BOOLEAN, test)
                .setDaoDefinition(reporteEstadoCuentaDaoDefinition)
                .build();
    }

    @Override
    public List<ReporteFacturasV2> reporteFacturasListar(FiltroFacturas filtroFacturas) {
        return DaoBuilder.getInstance(this)
                .setLogger(ConstantesCommon.LOGGER)
                .setSchema(ConstantesAdminDAO.SCHEMA_NAME)
                .setProcedureName(ConstantesAdminDAO.SP_REPORTE_FACTURAS_LISTAR)
                .addParameter("var_buscar", Types.VARCHAR, filtroFacturas.getBuscar())
                .addParameter("var_finicio", Types.VARCHAR, filtroFacturas.getFinicio())
                .addParameter("var_ffin", Types.VARCHAR, filtroFacturas.getFfin())
                .addParameter("var_inicio", Types.INTEGER, filtroFacturas.getStart())
                .addParameter("var_tamanio", Types.INTEGER, filtroFacturas.getLength())
                .setDaoDefinition(reporteFacturasV2DaoDefinition)
                .build();
    }

    @Override
    public Integer reporteFacturasContar(FiltroFacturas filtroFacturas) {
        return DaoBuilder.getInstance(this)
                .setLogger(ConstantesCommon.LOGGER)
                .setSchema(ConstantesAdminDAO.SCHEMA_NAME)
                .setProcedureName(ConstantesAdminDAO.SP_REPORTE_FACTURAS_CONTAR)
                .addParameter("var_buscar", Types.VARCHAR, filtroFacturas.getBuscar())
                .addParameter("var_finicio", Types.VARCHAR, filtroFacturas.getFinicio())
                .addParameter("var_ffin", Types.VARCHAR, filtroFacturas.getFfin())
                .setReturnDaoParameter("resultado", Types.INTEGER)
                .build(Integer.class);
    }

    @Override
    public List<ReporteCapacitacion> reporteCapacitacionListar(FiltroReporteCapacitacion filtroReporteCapacitacion) {
        return DaoBuilder.getInstance(this)
                .setLogger(ConstantesCommon.LOGGER)
                .setSchema(ConstantesAdminDAO.SCHEMA_NAME)
                .setProcedureName(ConstantesAdminDAO.SP_CAPACITACION_REPORTE_LISTAR)
                .addParameter("var_buscar", Types.VARCHAR, filtroReporteCapacitacion.getBuscar())
                .addParameter("var_finicio", Types.VARCHAR, filtroReporteCapacitacion.getFechaInicio())
                .addParameter("var_ffin", Types.VARCHAR, filtroReporteCapacitacion.getFechaFin())
                .addParameter("var_inicio", Types.INTEGER, filtroReporteCapacitacion.getStart())
                .addParameter("var_tamanio", Types.INTEGER, filtroReporteCapacitacion.getLength())
                .setDaoDefinition(reporteCapacitacionDaoDefinition)
                .build();
    }

    @Override
    public Integer reporteCapacitacionContar(FiltroReporteCapacitacion filtroReporteCapacitacion) {
        return DaoBuilder.getInstance(this)
                .setLogger(ConstantesCommon.LOGGER)
                .setSchema(ConstantesAdminDAO.SCHEMA_NAME)
                .setProcedureName(ConstantesAdminDAO.SP_CAPACITACION_REPORTE_CONTAR)
                .addParameter("var_buscar", Types.VARCHAR, filtroReporteCapacitacion.getBuscar())
                .addParameter("var_finicio", Types.VARCHAR, filtroReporteCapacitacion.getFechaInicio())
                .addParameter("var_ffin", Types.VARCHAR, filtroReporteCapacitacion.getFechaFin())
                .setReturnDaoParameter("resultado", Types.INTEGER)
                .build(Integer.class);
    }


    @Override
    public List<ReportePasarela> reportePasarelaListar(FiltroReportePasarela filtroReportePasarela) {
        return DaoBuilder.getInstance(this)
                .setLogger(ConstantesCommon.LOGGER)
                .setSchema(ConstantesAdminDAO.SCHEMA_NAME)
                .setProcedureName(ConstantesAdminDAO.SP_REPORTE_PASARELA_LISTAR)
                .addParameter("var_finicio", Types.VARCHAR, filtroReportePasarela.getFinicio())
                .addParameter("var_ffin", Types.VARCHAR, filtroReportePasarela.getFfin())
                .addParameter("var_inicio", Types.INTEGER, filtroReportePasarela.getStart())
                .addParameter("var_tamanio", Types.INTEGER, filtroReportePasarela.getLength())
                .setDaoDefinition(reportePasarelaDaoDefinition)
                .build();
    }

    @Override
    public Integer reportePasarelaContar(FiltroReportePasarela filtroReportePasarela) {
        return DaoBuilder.getInstance(this)
                .setLogger(ConstantesCommon.LOGGER)
                .setSchema(ConstantesAdminDAO.SCHEMA_NAME)
                .setProcedureName(ConstantesAdminDAO.SP_REPORTE_PASARELA_CONTAR)
                .addParameter("var_finicio", Types.VARCHAR, filtroReportePasarela.getFinicio())
                .addParameter("var_ffin", Types.VARCHAR, filtroReportePasarela.getFfin())
                .setReturnDaoParameter("resultado", Types.INTEGER)
                .build(Integer.class);
    }

    @Override
    public List<ReporteDescuento> reporteDescuentoListar(FiltroReporteDescuento filtroReporteDescuento) {
        return DaoBuilder.getInstance(this)
                .setLogger(ConstantesCommon.LOGGER)
                .setSchema(ConstantesAdminDAO.SCHEMA_NAME)
                .setProcedureName(ConstantesAdminDAO.SP_REPORTE_DESCUENTO_LISTAR)
                .addParameter("var_finicio", Types.VARCHAR, filtroReporteDescuento.getFinicio())
                .addParameter("var_ffin", Types.VARCHAR, filtroReporteDescuento.getFfin())
                .addParameter("var_inicio", Types.INTEGER, filtroReporteDescuento.getStart())
                .addParameter("var_tamanio", Types.INTEGER, filtroReporteDescuento.getLength())
                .setDaoDefinition(reportePasarelaDaoDefinition)
                .build();
    }

    @Override
    public Integer reporteDescuentoContar(FiltroReporteDescuento filtroReporteDescuento) {
        return DaoBuilder.getInstance(this)
                .setLogger(ConstantesCommon.LOGGER)
                .setSchema(ConstantesAdminDAO.SCHEMA_NAME)
                .setProcedureName(ConstantesAdminDAO.SP_REPORTE_DESCUENTO_CONTAR)
                .addParameter("var_finicio", Types.VARCHAR, filtroReporteDescuento.getFinicio())
                .addParameter("var_ffin", Types.VARCHAR, filtroReporteDescuento.getFfin())
                .setReturnDaoParameter("resultado", Types.INTEGER)
                .build(Integer.class);
    }

    @Override
    public List<ReporteMundialConsolidado> obtenerReporteMundialConsolidado(FiltroReporteMundialConsolidado filtro) {
        return DaoBuilder.getInstance(this)
                .setLogger(ConstantesCommon.LOGGER)
                .setSchema(ConstantesAdminDAO.SCHEMA_NAME)
                .setProcedureName(ConstantesAdminDAO.SP_REPORTE_MUNDIAL_CONSOLIDADO_LISTAR)
                .addParameter("var_inicio", Types.INTEGER, filtro.getStart())
                .addParameter("var_tamanio", Types.INTEGER, filtro.getLength())
                .setDaoDefinition(reporteMundialConsolidadoDaoDefinition)
                .build();
    }

    @Override
    public Integer contarReporteMundialConsolidado(FiltroReporteMundialConsolidado filtro) {
        return DaoBuilder.getInstance(this)
                .setLogger(ConstantesCommon.LOGGER)
                .setSchema(ConstantesAdminDAO.SCHEMA_NAME)
                .setProcedureName(ConstantesAdminDAO.SP_REPORTE_MUNDIAL_CONSOLIDADO_CONTAR)
                .setReturnDaoParameter("resultado", Types.INTEGER)
                .build(Integer.class);
    }

    @Override
    public List<Map<String, Object>> obtenerReporteMundialEC(FiltroReporteMundialEC filtro) {
        return DaoBuilder.getInstance(this)
                .setLogger(ConstantesCommon.LOGGER)
                .setSchema(ConstantesAdminDAO.SCHEMA_NAME)
                .setProcedureName(ConstantesAdminDAO.SP_REPORTE_MUNDIAL_ESTADO_CUENTA)
                .addParameter("var_fecha_inicio", Types.VARCHAR, filtro.getFechaInicio())
                .addParameter("var_fecha_fin", Types.VARCHAR, filtro.getFechaFin())
                .addParameter("var_inicio", Types.INTEGER, filtro.getStart())
                .addParameter("var_tamanio", Types.INTEGER, filtro.getLength())
                .setDaoDefinition(reporteMundialECDaoDefinition)
                .build();
    }

    @Override
    public Integer contarReporteMundialEC(FiltroReporteMundialEC filtro) {
        return DaoBuilder.getInstance(this)
                .setLogger(ConstantesCommon.LOGGER)
                .setSchema(ConstantesAdminDAO.SCHEMA_NAME)
                .setProcedureName(ConstantesAdminDAO.SP_REPORTE_MUNDIAL_ESTADO_CUENTA_TOTAL)
                .setReturnDaoParameter("resultado", Types.INTEGER)
                .build(Integer.class);
    }

    @Override
    public List<ReporteMundialPolla> obtenerReporteMundialPolla(FiltroReporteMundialPolla filtro) {
        return DaoBuilder.getInstance(this)
                .setLogger(ConstantesCommon.LOGGER)
                .setSchema(ConstantesAdminDAO.SCHEMA_NAME)
                .setProcedureName(ConstantesAdminDAO.SP_REPORTE_MUNDIAL_POLLA_LISTAR)
                .addParameter("var_inicio", Types.INTEGER, filtro.getStart())
                .addParameter("var_tamanio", Types.INTEGER, filtro.getLength())
                .addParameter("var_buscar", Types.VARCHAR, filtro.getNroDocumento() == null ? "" : filtro.getNroDocumento())
                .setDaoDefinition(reporteMundialPollaDaoDefinition)
                .build();
    }

    @Override
    public Integer contarReporteMundialPolla(FiltroReporteMundialPolla filtro) {
        return DaoBuilder.getInstance(this)
                .setLogger(ConstantesCommon.LOGGER)
                .setSchema(ConstantesAdminDAO.SCHEMA_NAME)
                .setProcedureName(ConstantesAdminDAO.SP_REPORTE_MUNDIAL_POLLA_CONTAR)
                .addParameter("var_buscar", Types.VARCHAR, filtro.getNroDocumento() == null ? "" : filtro.getNroDocumento())
                .setReturnDaoParameter("resultado", Types.INTEGER)
                .build(Integer.class);
    }

    @Override
    public List<ReporteMundialPronostico> obtenerReporteMundialPronostico(FiltroReporteMundialPronostico filtro) {
        return DaoBuilder.getInstance(this)
                .setLogger(ConstantesCommon.LOGGER)
                .setSchema(ConstantesAdminDAO.SCHEMA_NAME)
                .setProcedureName(ConstantesAdminDAO.SP_REPORTE_MUNDIAL_PRONOSTICO_LISTAR)
                .addParameter("var_inicio", Types.INTEGER, filtro.getStart())
                .addParameter("var_tamanio", Types.INTEGER, filtro.getLength())
                .addParameter("var_fecha_partido", Types.VARCHAR, filtro.getFechaPartido() == null ? "" : filtro.getFechaPartido())
                .setDaoDefinition(reporteMundialPronosticoDaoDefinition)
                .build();
    }

    @Override
    public Integer contarReporteMundialPronostico(FiltroReporteMundialPronostico filtro) {
        return DaoBuilder.getInstance(this)
                .setLogger(ConstantesCommon.LOGGER)
                .setSchema(ConstantesAdminDAO.SCHEMA_NAME)
                .setProcedureName(ConstantesAdminDAO.SP_REPORTE_MUNDIAL_PRONOSTICO_CONTAR)
                .addParameter("var_fecha_partido", Types.VARCHAR, filtro.getFechaPartido() == null ? "" : filtro.getFechaPartido())
                .setReturnDaoParameter("resultado", Types.INTEGER)
                .build(Integer.class);
    }

    @Override
    public List<ReporteMundialRanking> obtenerReporteMundialRanking(FiltroReporteMundialRanking filtro) {
        return DaoBuilder.getInstance(this)
                .setLogger(ConstantesCommon.LOGGER)
                .setSchema(ConstantesAdminDAO.SCHEMA_NAME)
                .setProcedureName(ConstantesAdminDAO.SP_REPORTE_MUNDIAL_RANKING_LISTAR)
                .addParameter("var_inicio", Types.INTEGER, filtro.getStart())
                .addParameter("var_tamanio", Types.INTEGER, filtro.getLength())
                .setDaoDefinition(reporteMundialRankingDaoDefinition)
                .build();
    }

    @Override
    public Integer contarReporteMundialRanking(FiltroReporteMundialRanking filtro) {
        return DaoBuilder.getInstance(this)
                .setLogger(ConstantesCommon.LOGGER)
                .setSchema(ConstantesAdminDAO.SCHEMA_NAME)
                .setProcedureName(ConstantesAdminDAO.SP_REPORTE_MUNDIAL_RANKING_CONTAR)
                .setReturnDaoParameter("resultado", Types.INTEGER)
                .build(Integer.class);
    }

    @Override
    public List<ReporteMundialTrivia> obtenerReporteMundialTrivia(FiltroReporteMundialTrivia filtro) {
        return DaoBuilder.getInstance(this)
                .setLogger(ConstantesCommon.LOGGER)
                .setSchema(ConstantesAdminDAO.SCHEMA_NAME)
                .setProcedureName(ConstantesAdminDAO.SP_REPORTE_MUNDIAL_TRIVIA_LISTAR)
                .addParameter("var_inicio", Types.INTEGER, filtro.getStart())
                .addParameter("var_tamanio", Types.INTEGER, filtro.getLength())
                .addParameter("var_nro_trivia", Types.INTEGER, filtro.getNroTrivia() == null ? -1 : filtro.getNroTrivia())
                .setDaoDefinition(reporteMundialTriviaDaoDefinition)
                .build();
    }

    @Override
    public Integer contarReporteMundialTrivia(FiltroReporteMundialTrivia filtro) {
        return DaoBuilder.getInstance(this)
                .setLogger(ConstantesCommon.LOGGER)
                .setSchema(ConstantesAdminDAO.SCHEMA_NAME)
                .setProcedureName(ConstantesAdminDAO.SP_REPORTE_MUNDIAL_TRIVIA_CONTAR)
                .addParameter("var_nro_trivia", Types.INTEGER, filtro.getNroTrivia() == null ? "" : filtro.getNroTrivia())
                .setReturnDaoParameter("resultado", Types.INTEGER)
                .build(Integer.class);
    }

    @Override
    public List<Recomendado> listarRecomendados(FiltroRecomendados filtroRecomendados) {
        return DaoBuilder.getInstance(this)
                .setLogger(ConstantesCommon.LOGGER)
                .setSchema(ConstantesAdminDAO.SCHEMA_NAME)
                .setProcedureName(ConstantesAdminDAO.SP_REPORTE_RECOMENDADO_LISTAR)
                .addParameter("var_finicio", Types.VARCHAR, filtroRecomendados.getFinicio())
                .addParameter("var_ffin", Types.VARCHAR, filtroRecomendados.getFfin())
                .addParameter("var_inicio", Types.INTEGER, filtroRecomendados.getStart())
                .addParameter("var_tamanio", Types.INTEGER, filtroRecomendados.getLength())
                .setDaoDefinition(recomendadoDaoDefinition)
                .build();
    }

    @Override
    public Integer contarRecomendados(FiltroRecomendados filtroRecomendados) {
        return DaoBuilder.getInstance(this)
                .setLogger(ConstantesCommon.LOGGER)
                .setSchema(ConstantesAdminDAO.SCHEMA_NAME)
                .setProcedureName(ConstantesAdminDAO.SP_REPORTE_RECOMENDADO_CONTAR)
                .addParameter("var_finicio", Types.VARCHAR, filtroRecomendados.getFinicio())
                .addParameter("var_ffin", Types.VARCHAR, filtroRecomendados.getFfin())
                .setReturnDaoParameter("resultado", Types.INTEGER)
                .build(Integer.class);
    }

    @Override
    public List<ReporteRecomendado> listarRecomendadoDetalle(FiltroRecomendados filtroRecomendados) {
        return DaoBuilder.getInstance(this)
                .setLogger(ConstantesCommon.LOGGER)
                .setSchema(ConstantesAdminDAO.SCHEMA_NAME)
                .setProcedureName(ConstantesAdminDAO.SP_REPORTE_RECOMENDADO_LISTAR_BY_FECHAS)
                .addParameter("var_finicio", Types.VARCHAR, filtroRecomendados.getFinicio())
                .addParameter("var_ffin", Types.VARCHAR, filtroRecomendados.getFfin())
                .setDaoDefinition(reporteRecomendadoDaoDefinition)
                .build();

    }

    @Override
    public List<ReporteMetas> listarMetas(FiltroMetas filtroMetas) {
        return DaoBuilder.getInstance(this)
                .setLogger(ConstantesCommon.LOGGER)
                .setSchema(ConstantesAdminDAO.SCHEMA_NAME)
                .setProcedureName(ConstantesAdminDAO.SP_META_TRIMESTRAL_LISTAR)
                .addParameter("var_trimestre", Types.INTEGER, filtroMetas.getTrimestre())
                .addParameter("var_anio", Types.INTEGER, filtroMetas.getAnio())
                .addParameter("var_nro_documento", Types.VARCHAR, filtroMetas.getNumeroDocumento())
                .addParameter("var_fecha_inicio", Types.VARCHAR, filtroMetas.getFinicio())
                .addParameter("var_fecha_fin", Types.VARCHAR, filtroMetas.getFfin())
                .addParameter("var_tamanio", Types.INTEGER, filtroMetas.getLength())
                .addParameter("var_inicio", Types.INTEGER, filtroMetas.getStart())
                .setDaoDefinition(reporteMetasDaoDefinition)
                .build();
    }

    @Override
    public Integer contarMetas(FiltroMetas filtroMetas) {
        return DaoBuilder.getInstance(this)
                .setLogger(ConstantesCommon.LOGGER)
                .setSchema(ConstantesAdminDAO.SCHEMA_NAME)
                .setProcedureName(ConstantesAdminDAO.SP_META_TRIMESTRAL_CONTAR)
                .addParameter("var_trimestre", Types.INTEGER, filtroMetas.getTrimestre())
                .addParameter("var_anio", Types.INTEGER, filtroMetas.getAnio())
                .addParameter("var_nro_documento", Types.VARCHAR, filtroMetas.getNumeroDocumento())
                .addParameter("var_fecha_inicio", Types.VARCHAR, filtroMetas.getFinicio())
                .addParameter("var_fecha_fin", Types.VARCHAR, filtroMetas.getFfin())
                .setReturnDaoParameter("resultado", Types.INTEGER)
                .build(Integer.class);
    }

    @Override
    public List<ReporteAcciones> listarAcciones() {
        return DaoBuilder.getInstance(this)
                .setLogger(ConstantesCommon.LOGGER)
                .setSchema(ConstantesAdminDAO.SCHEMA_NAME)
                .setProcedureName(ConstantesAdminDAO.SP_ACCIONES_LISTAR)
                .setDaoDefinition(reporteAccionesDaoDefinition)
                .build();
    }
}
