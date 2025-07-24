package com.promotick.lafabril.admin.controller;

import com.promotick.apiclient.utils.file.excel.generator.ExcelBuilder;
import com.promotick.lafabril.admin.util.BaseController;
import com.promotick.lafabril.admin.util.Util;
import com.promotick.lafabril.admin.service.ReporteAdminService;
import com.promotick.lafabril.admin.util.ConstantesAdminView;
import com.promotick.lafabril.common.ConstantesSesion;
import com.promotick.lafabril.model.reporte.ReporteCapacitacion;
import com.promotick.lafabril.model.util.Datatable;
import com.promotick.lafabril.model.util.FiltroReporteCapacitacion;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@Slf4j
@Controller
@RequestMapping("capacitaciones/reporte")
@RequiredArgsConstructor
public class CapacitacionReporteController extends BaseController {

    private final ReporteAdminService reporteAdminService;

    @GetMapping
    public String init() {
        return ConstantesAdminView.VIEW_CAPACITACIONES_REPORTE;
    }

    @ResponseBody
    @PostMapping(value = "listar")
    public Datatable listar(FiltroReporteCapacitacion filtroReporteCapacitacion) {
        Datatable datatable = new Datatable();
        Util.getSession().setAttribute(ConstantesSesion.SESSION_FILTRO_REPORTE_CAPACITACION, filtroReporteCapacitacion);
        Integer total = reporteAdminService.reporteCapacitacionContar(filtroReporteCapacitacion);
        datatable.setData(reporteAdminService.reporteCapacitacionListar(filtroReporteCapacitacion));
        datatable.setRecordsFiltered(total);
        datatable.setRecordsTotal(total);
        return datatable;
    }

    @GetMapping(value = "descargar-excel")
    public ModelAndView descargarExcel() {
        FiltroReporteCapacitacion filtroReporteCapacitacion = (FiltroReporteCapacitacion) Util.getSession().getAttribute(ConstantesSesion.SESSION_FILTRO_REPORTE_CAPACITACION);
        filtroReporteCapacitacion.setStart(0);
        filtroReporteCapacitacion.setLength(Integer.MAX_VALUE);
        return new ModelAndView(
                ExcelBuilder.getInstance(ReporteCapacitacion.class)
                        .setList(reporteAdminService.reporteCapacitacionListar(filtroReporteCapacitacion))
                        .buildView()
        );
    }


}
