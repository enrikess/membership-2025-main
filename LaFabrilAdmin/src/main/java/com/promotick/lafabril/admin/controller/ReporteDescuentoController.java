package com.promotick.lafabril.admin.controller;

import com.promotick.apiclient.utils.file.excel.generator.ExcelBuilder;
import com.promotick.lafabril.admin.service.ReporteAdminService;
import com.promotick.lafabril.admin.util.BaseController;
import com.promotick.lafabril.admin.util.ConstantesAdminView;
import com.promotick.lafabril.admin.util.Util;
import com.promotick.lafabril.common.ConstantesSesion;
import com.promotick.lafabril.model.reporte.ReporteDescuento;
import com.promotick.lafabril.model.reporte.ReportePasarela;
import com.promotick.lafabril.model.util.Datatable;
import com.promotick.lafabril.model.util.FiltroReporteDescuento;
import com.promotick.lafabril.model.util.FiltroReportePasarela;
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
@RequestMapping("reportes/descuentos")
public class ReporteDescuentoController extends BaseController {

    private ReporteAdminService reporteAdminService;

    @Autowired
    public ReporteDescuentoController(ReporteAdminService reporteAdminService) {
        this.reporteAdminService = reporteAdminService;
    }

    @GetMapping
    public String init() {
        return ConstantesAdminView.VIEW_REPORTE_DESCUENTO;
    }


    @ResponseBody
    @PostMapping(value = "listar")
    public Datatable listarDescuentos(FiltroReporteDescuento filtroReporteDescuento) {
        Datatable datatable = new Datatable();
        Util.getSession().setAttribute(ConstantesSesion.SESSION_FILTRO_DESCUENTO, filtroReporteDescuento);

        Integer total = reporteAdminService.reporteDescuentoContar(filtroReporteDescuento);
        datatable.setData(reporteAdminService.reporteDescuentoListar(filtroReporteDescuento));
        datatable.setRecordsFiltered(total);
        datatable.setRecordsTotal(total);
        return datatable;
    }

    @GetMapping(value = "descargar-excel")
    public ModelAndView descargarExcelDescuentos() {
        FiltroReporteDescuento filtroReporteDescuento = (FiltroReporteDescuento) Util.getSession().getAttribute(ConstantesSesion.SESSION_FILTRO_DESCUENTO);

        return new ModelAndView(
                ExcelBuilder.getInstance(ReporteDescuento.class)
                        .setList(reporteAdminService.reporteDescuentoListar(filtroReporteDescuento))
                        .buildView()
        );
    }

}
