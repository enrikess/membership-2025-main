package com.promotick.lafabril.admin.controller;

import com.promotick.apiclient.utils.file.excel.generator.ExcelBuilder;
import com.promotick.lafabril.admin.util.BaseController;
import com.promotick.lafabril.admin.util.Util;
import com.promotick.lafabril.admin.service.ReporteAdminService;
import com.promotick.lafabril.admin.util.ConstantesAdminView;
import com.promotick.lafabril.common.ConstantesSesion;
import com.promotick.lafabril.model.reporte.ReporteFacturasV2;
import com.promotick.lafabril.model.util.Datatable;
import com.promotick.lafabril.model.util.FiltroFacturas;
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
@RequestMapping("reportes/facturas")
@RequiredArgsConstructor
public class ReporteFacturasController extends BaseController {

    private final ReporteAdminService reporteAdminService;

    @GetMapping
    public String init() {
        return ConstantesAdminView.VIEW_REPORTE_FACTURAS;
    }

    @ResponseBody
    @PostMapping(value = "listar")
    public Datatable listat(FiltroFacturas filtroFacturas) {
        Datatable datatable = new Datatable();
        Util.getSession().setAttribute(ConstantesSesion.SESSION_FILTRO_FACTURAS, filtroFacturas);
        Integer total = reporteAdminService.reporteFacturasContar(filtroFacturas);
        datatable.setData(reporteAdminService.reporteFacturasListar(filtroFacturas));
        datatable.setRecordsFiltered(total);
        datatable.setRecordsTotal(total);
        return datatable;
    }

    @GetMapping(value = "descargar-excel")
    public ModelAndView descargarExcel() {

        FiltroFacturas filtroFacturas = (FiltroFacturas) Util.getSession().getAttribute(ConstantesSesion.SESSION_FILTRO_FACTURAS);

        return new ModelAndView(
                ExcelBuilder.getInstance(ReporteFacturasV2.class)
                        .setList(reporteAdminService.reporteFacturasListar(filtroFacturas))
                        .buildView()
        );
    }


}
