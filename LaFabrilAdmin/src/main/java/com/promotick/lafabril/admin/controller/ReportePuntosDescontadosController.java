package com.promotick.lafabril.admin.controller;

import com.promotick.apiclient.utils.file.excel.generator.ExcelBuilder;
import com.promotick.lafabril.admin.util.BaseController;
import com.promotick.lafabril.admin.service.ReporteAdminService;
import com.promotick.lafabril.admin.util.ConstantesAdminView;
import com.promotick.lafabril.common.ConstantesSesion;
import com.promotick.lafabril.model.reporte.ReportePuntosDescontados;
import com.promotick.lafabril.model.util.Datatable;
import com.promotick.lafabril.model.util.FiltroVisitas;
import com.promotick.lafabril.model.util.Util;
import com.promotick.lafabril.model.util.UtilEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("reportes/puntos-descontados")
public class ReportePuntosDescontadosController extends BaseController {

    private ReporteAdminService reporteAdminService;

    @Autowired
    public ReportePuntosDescontadosController(ReporteAdminService reporteAdminService) {
        this.reporteAdminService = reporteAdminService;
    }

    @GetMapping
    public String init() {
        return ConstantesAdminView.VIEW_REPORTE_PUNTOS_DESCONTADOS;
    }

    @ResponseBody
    @PostMapping(value = "listar-puntos-descontados")
    public Datatable listaVisitas(FiltroVisitas filtroVisitas) {
        Datatable datatable = new Datatable();
        filtroVisitas.setTipoOperacion(UtilEnum.TIPO_OPERACION_VISITA.PUNTOS_DESCONTADOS.getCodigo());

        Util.getSession().setAttribute(ConstantesSesion.SESSION_FILTRO_REPORTE_PUNTOS_DESCONTADOS, filtroVisitas);

        Integer total = reporteAdminService.contarVisitas(filtroVisitas);
        datatable.setData(reporteAdminService.listarVisitas(filtroVisitas));
        datatable.setRecordsFiltered(total);
        datatable.setRecordsTotal(total);
        return datatable;
    }

    @RequestMapping(value = "descargar-excel", method = RequestMethod.GET)
    public ModelAndView descargarExcelPedidos() {
        FiltroVisitas filtroVisitas = (FiltroVisitas) Util.getSession().getAttribute(ConstantesSesion.SESSION_FILTRO_REPORTE_PUNTOS_DESCONTADOS);
        filtroVisitas.setLength(-1);

        return new ModelAndView(
                ExcelBuilder.getInstance(ReportePuntosDescontados.class)
                        .setList(ReportePuntosDescontados.parseFromEntities(reporteAdminService.listarVisitas(filtroVisitas)))
                        .buildView()
        );
    }

}
