package com.promotick.lafabril.admin.controller;

import com.promotick.apiclient.utils.file.excel.generator.ExcelBuilder;
import com.promotick.lafabril.admin.service.MundialAdminService;
import com.promotick.lafabril.admin.service.ReporteAdminService;
import com.promotick.lafabril.admin.util.BaseController;
import com.promotick.lafabril.admin.util.ConstantesAdminView;
import com.promotick.lafabril.admin.util.Util;
import com.promotick.lafabril.common.ConstantesSesion;
import com.promotick.lafabril.model.reporte.ReporteMundialPolla;
import com.promotick.lafabril.model.reporte.ReporteMundialPronostico;
import com.promotick.lafabril.model.util.Datatable;
import com.promotick.lafabril.model.util.FiltroReporteMundialPolla;
import com.promotick.lafabril.model.util.FiltroReporteMundialPronostico;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@Slf4j
@Controller
@RequestMapping("reportes/mundial-pronostico")
public class ReporteMundialPronosticoController extends BaseController {

    private ReporteAdminService reporteAdminService;
    private MundialAdminService mundialAdminService;

    @Autowired
    public ReporteMundialPronosticoController(ReporteAdminService reporteAdminService, MundialAdminService mundialAdminService) {
        this.reporteAdminService = reporteAdminService;
        this.mundialAdminService = mundialAdminService;
    }

    @GetMapping
    public String init(Model model) {
        model.addAttribute("fechasPartidoPronostico", mundialAdminService.listarFechasPartido());
        return ConstantesAdminView.VIEW_REPORTE_MUNDIAL_PRONOSTICO;
    }


    @ResponseBody
    @PostMapping(value = "listar")
    public Datatable listarMundialPronostico(FiltroReporteMundialPronostico filtroReporteMundialPronostico) {
        Datatable datatable = new Datatable();
        Util.getSession().setAttribute(ConstantesSesion.SESSION_FILTRO_MUNDIAL_PRONOSTICO, filtroReporteMundialPronostico);

        Integer total = reporteAdminService.contarReporteMundialPronostico(filtroReporteMundialPronostico);
        datatable.setData(reporteAdminService.obtenerReporteMundialPronostico(filtroReporteMundialPronostico));
        datatable.setRecordsFiltered(total);
        datatable.setRecordsTotal(total);
        return datatable;
    }

    @GetMapping(value = "descargar-excel")
    public ModelAndView descargarExcelMundialPronostico() {
        FiltroReporteMundialPronostico filtroReporteMundialPronostico = (FiltroReporteMundialPronostico) Util.getSession().getAttribute(ConstantesSesion.SESSION_FILTRO_MUNDIAL_PRONOSTICO);
        filtroReporteMundialPronostico.setLength(-1);

        return new ModelAndView(
                ExcelBuilder.getInstance(ReporteMundialPronostico.class)
                        .setList(reporteAdminService.obtenerReporteMundialPronostico(filtroReporteMundialPronostico))
                        .buildView()
        );
    }

}
