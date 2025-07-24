package com.promotick.lafabril.admin.controller;

import com.promotick.apiclient.utils.file.excel.generator.ExcelBuilder;
import com.promotick.lafabril.admin.service.EncuestaEmpleadoAdminService;
import com.promotick.lafabril.admin.service.ReporteAdminService;
import com.promotick.lafabril.admin.util.BaseController;
import com.promotick.lafabril.admin.util.ConstantesAdminView;
import com.promotick.lafabril.admin.util.Util;
import com.promotick.lafabril.common.ConstantesSesion;
import com.promotick.lafabril.model.reporte.ReporteDescuento;
import com.promotick.lafabril.model.reporte.ReporteEncuestaEmpleados;
import com.promotick.lafabril.model.util.Datatable;
import com.promotick.lafabril.model.util.FiltroReporteDescuento;
import com.promotick.lafabril.model.util.FiltroReporteEncuestaEmpleados;
import lombok.RequiredArgsConstructor;
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
@RequiredArgsConstructor
@RequestMapping("reportes/encuesta-empleados")
public class ReporteEncuestaEmpleadoController extends BaseController {

    private final EncuestaEmpleadoAdminService encuestaEmpleadoAdminService;

    @GetMapping
    public String init() {
        return ConstantesAdminView.VIEW_REPORTE_ENCUESTA_EMPLEADOS;
    }


    @ResponseBody
    @PostMapping(value = "listar")
    public Datatable listar(FiltroReporteEncuestaEmpleados filtroReporteEncuestaEmpleados) {
        Datatable datatable = new Datatable();
        Util.getSession().setAttribute(ConstantesSesion.SESSION_FILTRO_ENCUESTA_EMPLEADOS, filtroReporteEncuestaEmpleados);

        Integer total = encuestaEmpleadoAdminService.reporteEncuestaEmpleadosContar(filtroReporteEncuestaEmpleados);
        datatable.setData(encuestaEmpleadoAdminService.reporteEncuestaEmpleadosListar(filtroReporteEncuestaEmpleados));
        datatable.setRecordsFiltered(total);
        datatable.setRecordsTotal(total);
        return datatable;
    }

    @GetMapping(value = "descargar-excel")
    public ModelAndView descargarExcel() {
        FiltroReporteEncuestaEmpleados filtroReporteEncuestaEmpleados = (FiltroReporteEncuestaEmpleados) Util.getSession().getAttribute(ConstantesSesion.SESSION_FILTRO_ENCUESTA_EMPLEADOS);
        filtroReporteEncuestaEmpleados.setStart(0);
        filtroReporteEncuestaEmpleados.setLength(Integer.MAX_VALUE);
        return new ModelAndView(
                ExcelBuilder.getInstance(ReporteEncuestaEmpleados.class)
                        .setList(encuestaEmpleadoAdminService.reporteEncuestaEmpleadosListar(filtroReporteEncuestaEmpleados))
                        .buildView()
        );
    }

}
