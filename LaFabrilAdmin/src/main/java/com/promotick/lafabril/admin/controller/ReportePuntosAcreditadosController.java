package com.promotick.lafabril.admin.controller;

import com.promotick.apiclient.utils.file.excel.generator.ExcelBuilder;
import com.promotick.lafabril.admin.service.FacturaAdminService;
import com.promotick.lafabril.admin.util.BaseController;
import com.promotick.lafabril.admin.util.ConstantesAdminView;
import com.promotick.lafabril.common.ConstantesSesion;
import com.promotick.lafabril.model.reporte.ReportePuntosAcreditados;
import com.promotick.lafabril.model.util.Datatable;
import com.promotick.lafabril.model.util.FiltroReportePuntosAcreditados;
import com.promotick.lafabril.model.util.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("reportes/puntos-acreditados")
public class ReportePuntosAcreditadosController extends BaseController {

    private FacturaAdminService facturaAdminService;

    @Autowired
    public ReportePuntosAcreditadosController(FacturaAdminService facturaAdminService) {
        this.facturaAdminService = facturaAdminService;
    }

    @GetMapping
    public String init() {
        return ConstantesAdminView.VIEW_REPORTE_PUNTOS_ACREDITADOS;
    }

    @ResponseBody
    @PostMapping(value = "listar-puntos-acreditados")
    public Datatable listaPuntosAcreditados(FiltroReportePuntosAcreditados filtroReportePuntosAcreditados) {
        Datatable datatable = new Datatable();

        Util.getSession().setAttribute(ConstantesSesion.SESSION_FILTRO_REPORTE_PUNTOS_ACREDITADOS, filtroReportePuntosAcreditados);

        Integer total = facturaAdminService.reportePuntosAcreditadosContar(filtroReportePuntosAcreditados);
        datatable.setData(facturaAdminService.reportePuntosAcreditadosListar(filtroReportePuntosAcreditados));
        datatable.setRecordsFiltered(total);
        datatable.setRecordsTotal(total);
        return datatable;
    }

    @GetMapping(value = "descargar-excel")
    public ModelAndView descargarExcelPedidos() {
        FiltroReportePuntosAcreditados filtroReportePuntosAcreditados = (FiltroReportePuntosAcreditados) Util.getSession().getAttribute(ConstantesSesion.SESSION_FILTRO_REPORTE_PUNTOS_ACREDITADOS);
        filtroReportePuntosAcreditados.setLength(-1);

        return new ModelAndView(
                ExcelBuilder.getInstance(ReportePuntosAcreditados.class)
                        .setList(ReportePuntosAcreditados.getFromEntities(facturaAdminService.reportePuntosAcreditadosListar(filtroReportePuntosAcreditados)))
                        .buildView()
        );
    }
}
