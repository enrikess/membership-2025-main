package com.promotick.lafabril.dao.admin;

import com.promotick.lafabril.model.reporte.*;
import com.promotick.lafabril.model.util.*;
import com.promotick.lafabril.model.web.Pedido;
import com.promotick.lafabril.model.web.Recomendado;

import java.util.List;
import java.util.Map;

public interface ReporteDao {
    List<ReporteVisita> listarVisitas(FiltroVisitas filtroVisitas);

    Integer contarVisitas(FiltroVisitas filtroVisitas);

    List<Map<String, Object>> obtenerEstadoCuenta(FiltroEstadoCuenta filtroEstadoCuenta);

    Integer obtenerEstadoCuentaTotal();

    List<Pedido> listarPedidos(FiltroPedidos filtroPedidos);

    Integer contarPedidos(FiltroPedidos filtroPedidos);

    List<ReportePedido> listarPedidoDetalle(FiltroPedidos filtroPedidos);

    List<Map<String, Object>> obtenerEstadoCuentaCron(FiltroEstadoCuenta filtroEstadoCuenta, boolean test);

    List<ReporteFacturasV2> reporteFacturasListar(FiltroFacturas filtroFacturas);

    Integer reporteFacturasContar(FiltroFacturas filtroFacturas);

    List<ReporteCapacitacion> reporteCapacitacionListar(FiltroReporteCapacitacion filtroReporteCapacitacion);

    Integer reporteCapacitacionContar(FiltroReporteCapacitacion filtroReporteCapacitacion);

    List<ReportePasarela> reportePasarelaListar(FiltroReportePasarela filtroReportePasarela);

    Integer reportePasarelaContar(FiltroReportePasarela filtroReportePasarela);

    List<ReporteDescuento> reporteDescuentoListar(FiltroReporteDescuento filtroReporteDescuento);

    Integer reporteDescuentoContar(FiltroReporteDescuento filtroReporteDescuento);

    List<ReporteMundialConsolidado> obtenerReporteMundialConsolidado(FiltroReporteMundialConsolidado filtro);

    Integer contarReporteMundialConsolidado(FiltroReporteMundialConsolidado filtro);

    List<Map<String,Object>>  obtenerReporteMundialEC(FiltroReporteMundialEC filtro);

    Integer contarReporteMundialEC(FiltroReporteMundialEC filtro);

    List<ReporteMundialPolla> obtenerReporteMundialPolla(FiltroReporteMundialPolla filtro);

    Integer contarReporteMundialPolla(FiltroReporteMundialPolla filtro);

    List<ReporteMundialPronostico> obtenerReporteMundialPronostico(FiltroReporteMundialPronostico filtro);

    Integer contarReporteMundialPronostico(FiltroReporteMundialPronostico filtro);

    List<ReporteMundialRanking> obtenerReporteMundialRanking(FiltroReporteMundialRanking filtro);

    Integer contarReporteMundialRanking(FiltroReporteMundialRanking filtro);

    List<ReporteMundialTrivia>  obtenerReporteMundialTrivia(FiltroReporteMundialTrivia filtro);

    Integer contarReporteMundialTrivia(FiltroReporteMundialTrivia filtro);

    List<Recomendado> listarRecomendados(FiltroRecomendados filtroRecomendados);

    Integer contarRecomendados(FiltroRecomendados filtroRecomendados);

    List<ReporteRecomendado> listarRecomendadoDetalle(FiltroRecomendados filtroRecomendados);

    List<ReporteMetas> listarMetas(FiltroMetas filtroMetas);

    Integer contarMetas(FiltroMetas filtroMetas);

    List<ReporteAcciones> listarAcciones();
}
