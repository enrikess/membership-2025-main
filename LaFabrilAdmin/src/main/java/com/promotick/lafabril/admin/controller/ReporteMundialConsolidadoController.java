package com.promotick.lafabril.admin.controller;

import com.promotick.apiclient.utils.file.excel.generator.ExcelBuilder;
import com.promotick.lafabril.admin.service.ReporteAdminService;
import com.promotick.lafabril.admin.util.BaseController;
import com.promotick.lafabril.admin.util.ConstantesAdminView;
import com.promotick.lafabril.admin.util.Util;
import com.promotick.lafabril.common.ConstantesSesion;
import com.promotick.lafabril.model.reporte.ReporteDescuento;
import com.promotick.lafabril.model.reporte.ReporteMundialConsolidado;
import com.promotick.lafabril.model.util.Datatable;
import com.promotick.lafabril.model.util.FiltroReporteDescuento;
import com.promotick.lafabril.model.util.FiltroReporteMundialConsolidado;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@Slf4j
@Controller
@RequestMapping("reportes/mundial-consolidado")
public class ReporteMundialConsolidadoController extends BaseController {

    private ReporteAdminService reporteAdminService;

    @Autowired
    public ReporteMundialConsolidadoController(ReporteAdminService reporteAdminService) {
        this.reporteAdminService = reporteAdminService;
    }

    @GetMapping
    public String init() {
        return ConstantesAdminView.VIEW_REPORTE_MUNDIAL_CONSOLIDADO;
    }


    @ResponseBody
    @PostMapping(value = "listar")
    public Datatable listarMundialConsolidado(FiltroReporteMundialConsolidado filtroReporteMundialConsolidado) {
        Datatable datatable = new Datatable();
        Util.getSession().setAttribute(ConstantesSesion.SESSION_FILTRO_MUNDIAL_CONSOLIDADO, filtroReporteMundialConsolidado);

        Integer total = reporteAdminService.contarReporteMundialConsolidado(filtroReporteMundialConsolidado);
        datatable.setData(reporteAdminService.obtenerReporteMundialConsolidado(filtroReporteMundialConsolidado));
        datatable.setRecordsFiltered(total);
        datatable.setRecordsTotal(total);
        return datatable;
    }

    @GetMapping(value = "descargar-excel")
    public ModelAndView descargarExcelMundialConsolidado() {
        FiltroReporteMundialConsolidado filtroReporteMundialConsolidado = (FiltroReporteMundialConsolidado) Util.getSession().getAttribute(ConstantesSesion.SESSION_FILTRO_MUNDIAL_CONSOLIDADO);
        filtroReporteMundialConsolidado.setLength(-1);

        return new ModelAndView(
                ExcelBuilder.getInstance(ReporteMundialConsolidado.class)
                        .setList(reporteAdminService.obtenerReporteMundialConsolidado(filtroReporteMundialConsolidado))
                        .buildView()
        );
    }

}
