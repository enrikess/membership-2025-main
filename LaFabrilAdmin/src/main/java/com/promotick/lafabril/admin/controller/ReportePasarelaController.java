package com.promotick.lafabril.admin.controller;

import com.promotick.apiclient.utils.file.excel.generator.ExcelBuilder;
import com.promotick.lafabril.admin.service.ReporteAdminService;
import com.promotick.lafabril.admin.util.BaseController;
import com.promotick.lafabril.admin.util.ConstantesAdminView;
import com.promotick.lafabril.admin.util.Util;
import com.promotick.lafabril.common.ConstantesSesion;
import com.promotick.lafabril.model.reporte.ReportePasarela;
import com.promotick.lafabril.model.reporte.ReporteVisitaParticipante;
import com.promotick.lafabril.model.reporte.ReporteVisitaParticipanteDetallado;
import com.promotick.lafabril.model.util.Datatable;
import com.promotick.lafabril.model.util.FiltroReportePasarela;
import com.promotick.lafabril.model.util.FiltroVisitas;
import com.promotick.lafabril.model.util.UtilEnum;
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
@RequestMapping("reportes/pasarela")
public class ReportePasarelaController extends BaseController {

    private ReporteAdminService reporteAdminService;

    @Autowired
    public ReportePasarelaController(ReporteAdminService reporteAdminService) {
        this.reporteAdminService = reporteAdminService;
    }

    @GetMapping
    public String init() {
        return ConstantesAdminView.VIEW_REPORTE_PASARELA;
    }


    @ResponseBody
    @PostMapping(value = "listar-pasarela")
    public Datatable listaPasarela(FiltroReportePasarela filtroReportePasarela) {
        Datatable datatable = new Datatable();
        Util.getSession().setAttribute(ConstantesSesion.SESSION_FILTRO_PASARELA, filtroReportePasarela);

        Integer total = reporteAdminService.reportePasarelaContar(filtroReportePasarela);
        datatable.setData(reporteAdminService.reportePasarelaListar(filtroReportePasarela));
        datatable.setRecordsFiltered(total);
        datatable.setRecordsTotal(total);
        return datatable;
    }

    @GetMapping(value = "descargar-excel")
    public ModelAndView descargarExcelPedidos() {
        FiltroReportePasarela filtroReportePasarela = (FiltroReportePasarela) Util.getSession().getAttribute(ConstantesSesion.SESSION_FILTRO_PASARELA);

        return new ModelAndView(
                ExcelBuilder.getInstance(ReportePasarela.class)
                        .setList(reporteAdminService.reportePasarelaListar(filtroReportePasarela))
                        .buildView()
        );
    }

}
