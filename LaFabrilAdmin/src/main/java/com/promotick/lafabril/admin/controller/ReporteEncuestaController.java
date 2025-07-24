package com.promotick.lafabril.admin.controller;

import com.promotick.apiclient.utils.file.excel.generator.ExcelBuilder;
import com.promotick.lafabril.admin.util.BaseController;
import com.promotick.lafabril.admin.util.Util;
import com.promotick.lafabril.admin.service.ReporteAdminService;
import com.promotick.lafabril.admin.util.ConstantesAdminView;
import com.promotick.lafabril.common.ConstantesSesion;
import com.promotick.lafabril.model.reporte.ReporteResultadoEncuesta;
import com.promotick.lafabril.model.util.Datatable;
import com.promotick.lafabril.model.util.FiltroEncuesta;
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
@RequestMapping("reportes/encuesta")
public class ReporteEncuestaController extends BaseController {

    private ReporteAdminService reporteAdminService;

    @Autowired
    public ReporteEncuestaController(ReporteAdminService reporteAdminService) {
        this.reporteAdminService = reporteAdminService;
    }

    @GetMapping
    public String init() {
        return ConstantesAdminView.VIEW_REPORTE_ENCUESTAS;
    }


    @ResponseBody
    @PostMapping(value = "listar-detalles")
    public Datatable listaVisitas(FiltroEncuesta filtroEncuesta) {
        Datatable datatable = new Datatable();

        Util.getSession().setAttribute(ConstantesSesion.SESSION_FILTRO_ENCUESTA, filtroEncuesta);

        Integer total = reporteAdminService.contarResultadosEncuesta(filtroEncuesta);
        datatable.setData(reporteAdminService.listarResultadosEncuesta(filtroEncuesta));
        datatable.setRecordsFiltered(total);
        datatable.setRecordsTotal(total);
        return datatable;
    }

    @GetMapping(value = "descargar-excel")
    public ModelAndView descargarExcelPedidos() {
        FiltroEncuesta filtroEncuesta = (FiltroEncuesta) Util.getSession().getAttribute(ConstantesSesion.SESSION_FILTRO_ENCUESTA);
        filtroEncuesta.setLength(-1);

        return new ModelAndView(
                ExcelBuilder.getInstance(ReporteResultadoEncuesta.class)
                        .setList(ReporteResultadoEncuesta.getFromEntities(reporteAdminService.listarResultadosEncuesta(filtroEncuesta)))
                        .buildView()
        );
    }

}
