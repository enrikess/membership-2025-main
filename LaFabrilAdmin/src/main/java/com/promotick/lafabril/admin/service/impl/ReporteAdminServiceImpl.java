package com.promotick.lafabril.admin.service.impl;

import com.promotick.lafabril.admin.service.ReporteAdminService;
import com.promotick.lafabril.model.reporte.*;
import com.promotick.lafabril.model.util.*;
import com.promotick.lafabril.dao.admin.PedidoDao;
import com.promotick.lafabril.dao.admin.ReporteDao;
import com.promotick.lafabril.dao.web.EncuestaDao;
import com.promotick.lafabril.dao.web.ParticipanteDao;
import com.promotick.lafabril.model.web.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Map;

@Service
public class ReporteAdminServiceImpl implements ReporteAdminService {

    private ReporteDao reporteDao;
    private PedidoDao pedidoDao;
    private ParticipanteDao participanteDao;
    private EncuestaDao encuestaDao;

    @Autowired
    public ReporteAdminServiceImpl(ReporteDao reporteDao,EncuestaDao encuestaDao, PedidoDao pedidoDao, ParticipanteDao participanteDao) {
        this.reporteDao = reporteDao;
        this.pedidoDao = pedidoDao;
        this.participanteDao = participanteDao;
        this.encuestaDao = encuestaDao;
    }

    @Override
    public List<ReporteVisita> listarVisitas(FiltroVisitas filtroVisitas) {
        return reporteDao.listarVisitas(filtroVisitas);
    }

    @Override
    public Integer contarVisitas(FiltroVisitas filtroVisitas) {
        return reporteDao.contarVisitas(filtroVisitas);
    }

    @Override
    public List<Map<String, Object>> obtenerEstadoCuenta(FiltroEstadoCuenta filtroEstadoCuenta) {
        return reporteDao.obtenerEstadoCuenta(filtroEstadoCuenta);
    }

    @Override
    public Integer obtenerEstadoCuentaTotal() {
        return reporteDao.obtenerEstadoCuentaTotal();
    }

    @Override
    public List<Pedido> listarPedidos(FiltroPedidos filtroPedidos) {
        return reporteDao.listarPedidos(filtroPedidos);
    }

    @Override
    public Integer contarPedidos(FiltroPedidos filtroPedidos) {
        return reporteDao.contarPedidos(filtroPedidos);
    }

    @Override
    public List<ReportePedido> listarPedidoDetalle(FiltroPedidos filtroPedidos) {
        return reporteDao.listarPedidoDetalle(filtroPedidos);
    }

    @Override
    public List<PedidoDetalle> listarDetallePedido(Integer idPedido) {
        return pedidoDao.listarDetallePedido(idPedido);
    }

    @Override
    public List<ParticipanteTransaccion> listarPuntos(FiltroParticipanteTransaccion filtroParticipanteTransaccion) {
        return participanteDao.obtenerMisPuntos(filtroParticipanteTransaccion);
    }

    @Override
    public Integer contarPuntos(FiltroParticipanteTransaccion filtroParticipanteTransaccion) {
        return participanteDao.contarMisPuntos(filtroParticipanteTransaccion);
    }

    @Override
    public List<EncuestaDetalle> listarResultadosEncuesta(FiltroEncuesta filtroEncuesta) {
        return encuestaDao.listarResultadosEncuesta(filtroEncuesta);
    }

    @Override
    public Integer contarResultadosEncuesta(FiltroEncuesta filtroEncuesta) {
        return encuestaDao.contarResultadosEncuesta(filtroEncuesta);
    }

    @Override
    public List<Map<String, Object>> obtenerEstadoCuentaCron(FiltroEstadoCuenta filtroEstadoCuenta, boolean test) {
        return reporteDao.obtenerEstadoCuentaCron(filtroEstadoCuenta, test);
    }

    @Override
    public List<ReporteFacturasV2> reporteFacturasListar(FiltroFacturas filtroFacturas) {
        return reporteDao.reporteFacturasListar(filtroFacturas);
    }

    @Override
    public Integer reporteFacturasContar(FiltroFacturas filtroFacturas) {
        return reporteDao.reporteFacturasContar(filtroFacturas);
    }

    @Override
    public List<ReporteCapacitacion> reporteCapacitacionListar(FiltroReporteCapacitacion filtroReporteCapacitacion) {
        return reporteDao.reporteCapacitacionListar(filtroReporteCapacitacion);
    }

    @Override
    public Integer reporteCapacitacionContar(FiltroReporteCapacitacion filtroReporteCapacitacion) {
        return reporteDao.reporteCapacitacionContar(filtroReporteCapacitacion);
    }

    @Override
    public List<ReportePasarela> reportePasarelaListar(FiltroReportePasarela filtroReportePasarela) {
        return reporteDao.reportePasarelaListar(filtroReportePasarela);
    }

    @Override
    public Integer reportePasarelaContar(FiltroReportePasarela filtroReportePasarela) {
        return reporteDao.reportePasarelaContar(filtroReportePasarela);
    }

    @Override
    public List<ReporteDescuento> reporteDescuentoListar(FiltroReporteDescuento filtroReporteDescuento) {
        return reporteDao.reporteDescuentoListar(filtroReporteDescuento);
    }

    @Override
    public Integer reporteDescuentoContar(FiltroReporteDescuento filtroReporteDescuento) {
        return reporteDao.reporteDescuentoContar(filtroReporteDescuento);
    }

    @Override
    public List<ReporteMundialConsolidado> obtenerReporteMundialConsolidado(FiltroReporteMundialConsolidado filtro) {
        return reporteDao.obtenerReporteMundialConsolidado(filtro);
    }

    @Override
    public Integer contarReporteMundialConsolidado(FiltroReporteMundialConsolidado filtro) {
        return reporteDao.contarReporteMundialConsolidado(filtro);
    }

    @Override
    public List<Map<String, Object>> obtenerReporteMundialEC(FiltroReporteMundialEC filtro) {
        return reporteDao.obtenerReporteMundialEC(filtro);
    }

    @Override
    public Integer contarReporteMundialEC(FiltroReporteMundialEC filtro) {
        return reporteDao.contarReporteMundialEC(filtro);
    }

    @Override
    public List<ReporteMundialPolla> obtenerReporteMundialPolla(FiltroReporteMundialPolla filtro) {
        return reporteDao.obtenerReporteMundialPolla(filtro);
    }

    @Override
    public Integer contarReporteMundialPolla(FiltroReporteMundialPolla filtro) {
        return reporteDao.contarReporteMundialPolla(filtro);
    }

    @Override
    public List<ReporteMundialPronostico> obtenerReporteMundialPronostico(FiltroReporteMundialPronostico filtro) {
        return reporteDao.obtenerReporteMundialPronostico(filtro);
    }

    @Override
    public Integer contarReporteMundialPronostico(FiltroReporteMundialPronostico filtro) {
        return reporteDao.contarReporteMundialPronostico(filtro);
    }

    @Override
    public List<ReporteMundialRanking> obtenerReporteMundialRanking(FiltroReporteMundialRanking filtro) {
        return reporteDao.obtenerReporteMundialRanking(filtro);
    }

    @Override
    public Integer contarReporteMundialRanking(FiltroReporteMundialRanking filtro) {
        return reporteDao.contarReporteMundialRanking(filtro);
    }

    @Override
    public List<ReporteMundialTrivia> obtenerReporteMundialTrivia(FiltroReporteMundialTrivia filtro) {
        return reporteDao.obtenerReporteMundialTrivia(filtro);
    }

    @Override
    public Integer contarReporteMundialTrivia(FiltroReporteMundialTrivia filtro) {
        return reporteDao.contarReporteMundialTrivia(filtro);
    }

    @Override
    public List<Recomendado> listarRecomendados(FiltroRecomendados filtroRecomendados) {
        return reporteDao.listarRecomendados(filtroRecomendados);
    }

    @Override
    public Integer contarRecomendados(FiltroRecomendados filtroRecomendados) {
        return reporteDao.contarRecomendados(filtroRecomendados);
    }

    @Override
    public List<ReporteRecomendado> listarRecomendadoDetalle(FiltroRecomendados filtroRecomendados) {
        return reporteDao.listarRecomendadoDetalle(filtroRecomendados);
    }

    @Override
    public List<ReporteMetas> listarMetas(FiltroMetas filtroMetas) {
        return reporteDao.listarMetas(filtroMetas);
    }

    @Override
    public Integer contarMetas(FiltroMetas filtroMetas) {
        return reporteDao.contarMetas(filtroMetas);
    }

    @Override
    public List<ReporteAcciones> listarAcciones() {
        return reporteDao.listarAcciones();
    }
}
