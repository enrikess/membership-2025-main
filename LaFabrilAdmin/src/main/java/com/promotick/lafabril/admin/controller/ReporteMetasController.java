package com.promotick.lafabril.admin.controller;

import com.promotick.apiclient.utils.file.excel.generator.ExcelBuilder;
import com.promotick.lafabril.admin.service.CargaExcelAdminService;
import com.promotick.lafabril.admin.service.ReporteAdminService;
import com.promotick.lafabril.admin.util.BaseController;
import com.promotick.lafabril.admin.util.ConstantesAdminView;
import com.promotick.lafabril.admin.util.Util;
import com.promotick.lafabril.common.ConstantesSesion;
import com.promotick.lafabril.model.facturacion.Carga;
import com.promotick.lafabril.model.reporte.ReporteMetaExcel;
import com.promotick.lafabril.model.reporte.ReporteVisitaParticipante;
import com.promotick.lafabril.model.reporte.ReporteVisitaParticipanteDetallado;
import com.promotick.lafabril.model.util.*;
import com.promotick.lafabril.model.util.form.CargaMetas;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Slf4j
@Controller
@RequestMapping("reportes/metas")
public class ReporteMetasController extends BaseController {

    private ReporteAdminService reporteAdminService;
    private CargaExcelAdminService cargaExcelAdminService;

    @Autowired
    public ReporteMetasController(ReporteAdminService reporteAdminService, CargaExcelAdminService cargaExcelAdminService) {
        this.reporteAdminService = reporteAdminService;
        this.cargaExcelAdminService = cargaExcelAdminService;
    }

    @GetMapping
    public String init() {
        return ConstantesAdminView.VIEW_REPORTE_METAS;
    }


    @ResponseBody
    @PostMapping(value = "listar-metas")
    public Datatable listarMetas(FiltroMetas filtroMetas) {
        Datatable datatable = new Datatable();

        Util.getSession().setAttribute(ConstantesSesion.SESSION_FILTRO_METAS, filtroMetas);

        Integer total = reporteAdminService.contarMetas(filtroMetas);
        datatable.setData(reporteAdminService.listarMetas(filtroMetas));
        datatable.setRecordsFiltered(total);
        datatable.setRecordsTotal(total);
        return datatable;
    }

    @ResponseBody
    @PostMapping(value = "borrar-metas")
    public PromotickResult borrarMetas(@RequestBody FiltroMetas filtroMetas, PromotickResult promotickResult) {
        try {
            CargaMetas cargaMetas = new CargaMetas();
            cargaMetas.setListaId(filtroMetas.getIdMetas());
            cargaMetas.setOperacion("ELIMINAR");
            cargaExcelAdminService.registrarCargaMetas(cargaMetas);
            this.evaluarResultado(1,  promotickResult, "Eliminado Correctamente");
        } catch (Exception e) {
            promotickResult.setException(e);
        }
        return promotickResult;
    }

    @GetMapping(value = "descargar-excel")
    public ModelAndView descargarExcelPedidos() {
        FiltroMetas filtroMetas = (FiltroMetas) Util.getSession().getAttribute(ConstantesSesion.SESSION_FILTRO_METAS);
        filtroMetas.setLength(999999999);
        filtroMetas.setStart(0);

        return new ModelAndView(
                ExcelBuilder.getInstance(ReporteMetaExcel.class)
                        .setList(ReporteMetaExcel.getFromEntities(reporteAdminService.listarMetas(filtroMetas)))
                        .buildView()
        );
    }

    @GetMapping(value = "descargar-excel-detallado")
    public ModelAndView descargaExcelDetallado() {
        FiltroVisitas filtroVisitas = (FiltroVisitas) Util.getSession().getAttribute(ConstantesSesion.SESSION_FILTRO_METAS);
        filtroVisitas.setLength(-1);
        filtroVisitas.setTipoOperacion(UtilEnum.TIPO_OPERACION_VISITA.VISITA_WEB_DETALLADO.getCodigo());

        return new ModelAndView(
                ExcelBuilder.getInstance(ReporteVisitaParticipanteDetallado.class)
                        .setList(ReporteVisitaParticipanteDetallado.getFromEntities(reporteAdminService.listarVisitas(filtroVisitas)))
                        .buildView()
        );
    }

}
