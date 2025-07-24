package com.promotick.lafabril.admin.controller;

import com.promotick.apiclient.utils.file.excel.generator.ExcelBuilder;
import com.promotick.lafabril.admin.util.BaseController;
import com.promotick.lafabril.admin.util.Util;
import com.promotick.lafabril.admin.service.ReporteAdminService;
import com.promotick.lafabril.admin.util.ConstantesAdminView;
import com.promotick.lafabril.common.ConstantesSesion;
import com.promotick.lafabril.model.reporte.ReporteVisitaParticipante;
import com.promotick.lafabril.model.reporte.ReporteVisitaParticipanteDetallado;
import com.promotick.lafabril.model.util.Datatable;
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
@RequestMapping("reportes/visitas")
public class ReporteVisitasController extends BaseController {

    private ReporteAdminService reporteAdminService;

    @Autowired
    public ReporteVisitasController(ReporteAdminService reporteAdminService) {
        this.reporteAdminService = reporteAdminService;
    }

    @GetMapping
    public String init() {
        return ConstantesAdminView.VIEW_REPORTE_VISITAS;
    }


    @ResponseBody
    @PostMapping(value = "listar-visitas")
    public Datatable listaVisitas(FiltroVisitas filtroVisitas) {
        Datatable datatable = new Datatable();
        filtroVisitas.setTipoOperacion(UtilEnum.TIPO_OPERACION_VISITA.VISITA_WEB.getCodigo());
        filtroVisitas.setTipoDispositivo(UtilEnum.TIPO_DISPOSITVO_VISITA.WEB.getCodigo());

        Util.getSession().setAttribute(ConstantesSesion.SESSION_FILTRO_VISITA, filtroVisitas);

        Integer total = reporteAdminService.contarVisitas(filtroVisitas);
        datatable.setData(reporteAdminService.listarVisitas(filtroVisitas));
        datatable.setRecordsFiltered(total);
        datatable.setRecordsTotal(total);
        return datatable;
    }

    @GetMapping(value = "descargar-excel")
    public ModelAndView descargarExcelPedidos() {
        FiltroVisitas filtroVisitas = (FiltroVisitas) Util.getSession().getAttribute(ConstantesSesion.SESSION_FILTRO_VISITA);
        filtroVisitas.setLength(-1);

        return new ModelAndView(
                ExcelBuilder.getInstance(ReporteVisitaParticipante.class)
                        .setList(ReporteVisitaParticipante.getFromEntities(reporteAdminService.listarVisitas(filtroVisitas)))
                        .buildView()
        );
    }

    @GetMapping(value = "descargar-excel-detallado")
    public ModelAndView descargaExcelDetallado() {
        FiltroVisitas filtroVisitas = (FiltroVisitas) Util.getSession().getAttribute(ConstantesSesion.SESSION_FILTRO_VISITA);
        filtroVisitas.setLength(-1);
        filtroVisitas.setTipoOperacion(UtilEnum.TIPO_OPERACION_VISITA.VISITA_WEB_DETALLADO.getCodigo());

        return new ModelAndView(
                ExcelBuilder.getInstance(ReporteVisitaParticipanteDetallado.class)
                        .setList(ReporteVisitaParticipanteDetallado.getFromEntities(reporteAdminService.listarVisitas(filtroVisitas)))
                        .buildView()
        );
    }

}
