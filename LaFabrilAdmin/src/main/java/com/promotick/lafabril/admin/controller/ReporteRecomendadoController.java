package com.promotick.lafabril.admin.controller;

import com.promotick.apiclient.utils.file.excel.generator.ExcelBuilder;
import com.promotick.lafabril.admin.service.ReporteAdminService;
import com.promotick.lafabril.admin.util.BaseController;
import com.promotick.lafabril.admin.util.ConstantesAdminView;
import com.promotick.lafabril.admin.util.Util;
import com.promotick.lafabril.common.ConstantesSesion;
import com.promotick.lafabril.model.reporte.ReporteRecomendado;
import com.promotick.lafabril.model.util.Datatable;
import com.promotick.lafabril.model.util.FiltroRecomendados;
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
@RequestMapping( "reportes/recomendados")
public class ReporteRecomendadoController extends BaseController {

    private ReporteAdminService reporteAdminService;

    @Autowired
    public ReporteRecomendadoController(ReporteAdminService reporteAdminService){
        this.reporteAdminService = reporteAdminService;
    }

    @GetMapping
    public String init(){
        return ConstantesAdminView.VIEW_REPORTE_RECOMENDADOS;
    }

    @ResponseBody
    @PostMapping(value = "listar-recomendados")
    public Datatable listarRecomendados(FiltroRecomendados filtroRecomendados) {
        Datatable datatable =  new Datatable();
        Util.getSession().setAttribute(ConstantesSesion.SESSION_FILTRO_RECOMENDADOS, filtroRecomendados);
        Integer total = reporteAdminService.contarRecomendados(filtroRecomendados);
        datatable.setData(reporteAdminService.listarRecomendados(filtroRecomendados));
        datatable.setRecordsFiltered(total);
        datatable.setRecordsTotal(total);
        return datatable;
    }

    @GetMapping(value = "descargar-excel")
    public ModelAndView descargarExcelRecomendados(){

        FiltroRecomendados filtroRecomendados = (FiltroRecomendados) Util.getSession().getAttribute(ConstantesSesion.SESSION_FILTRO_RECOMENDADOS);

        return new ModelAndView(
                ExcelBuilder.getInstance(ReporteRecomendado.class)
                        .setList(reporteAdminService.listarRecomendadoDetalle(filtroRecomendados))
                        .buildView()
        );
    }


}
