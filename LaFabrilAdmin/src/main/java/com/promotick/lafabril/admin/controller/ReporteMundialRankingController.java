package com.promotick.lafabril.admin.controller;

import com.promotick.apiclient.utils.file.excel.generator.ExcelBuilder;
import com.promotick.lafabril.admin.service.ReporteAdminService;
import com.promotick.lafabril.admin.util.BaseController;
import com.promotick.lafabril.admin.util.ConstantesAdminView;
import com.promotick.lafabril.admin.util.Util;
import com.promotick.lafabril.common.ConstantesSesion;
import com.promotick.lafabril.model.reporte.ReporteMundialPolla;
import com.promotick.lafabril.model.reporte.ReporteMundialRanking;
import com.promotick.lafabril.model.util.Datatable;
import com.promotick.lafabril.model.util.FiltroReporteMundialPolla;
import com.promotick.lafabril.model.util.FiltroReporteMundialRanking;
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
@RequestMapping("reportes/mundial-ranking")
public class ReporteMundialRankingController extends BaseController {

    private ReporteAdminService reporteAdminService;

    @Autowired
    public ReporteMundialRankingController(ReporteAdminService reporteAdminService) {
        this.reporteAdminService = reporteAdminService;
    }

    @GetMapping
    public String init() {
        return ConstantesAdminView.VIEW_REPORTE_MUNDIAL_RANKING;
    }


    @ResponseBody
    @PostMapping(value = "listar")
    public Datatable listarMundialRanking(FiltroReporteMundialRanking filtroReporteMundialRanking) {
        Datatable datatable = new Datatable();
        Util.getSession().setAttribute(ConstantesSesion.SESSION_FILTRO_MUNDIAL_RANKING, filtroReporteMundialRanking);

        Integer total = reporteAdminService.contarReporteMundialRanking(filtroReporteMundialRanking);
        datatable.setData(reporteAdminService.obtenerReporteMundialRanking(filtroReporteMundialRanking));
        datatable.setRecordsFiltered(total);
        datatable.setRecordsTotal(total);
        return datatable;
    }

    @GetMapping(value = "descargar-excel")
    public ModelAndView descargarExcelMundialRanking() {
        FiltroReporteMundialRanking filtroReporteMundialRanking = (FiltroReporteMundialRanking) Util.getSession().getAttribute(ConstantesSesion.SESSION_FILTRO_MUNDIAL_RANKING);
        filtroReporteMundialRanking.setLength(-1);

        return new ModelAndView(
                ExcelBuilder.getInstance(ReporteMundialRanking.class)
                        .setList(reporteAdminService.obtenerReporteMundialRanking(filtroReporteMundialRanking))
                        .buildView()
        );
    }

}
