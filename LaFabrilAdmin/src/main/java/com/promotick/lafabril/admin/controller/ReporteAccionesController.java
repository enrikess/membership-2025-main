package com.promotick.lafabril.admin.controller;

import com.promotick.apiclient.utils.file.excel.generator.ExcelBuilder;
import com.promotick.lafabril.admin.service.CargaExcelAdminService;
import com.promotick.lafabril.admin.service.ReporteAdminService;
import com.promotick.lafabril.admin.util.BaseController;
import com.promotick.lafabril.admin.util.ConstantesAdminView;
import com.promotick.lafabril.admin.util.Util;
import com.promotick.lafabril.common.ConstantesCommon;
import com.promotick.lafabril.common.ConstantesSesion;
import com.promotick.lafabril.model.reporte.ReporteAcciones;
import com.promotick.lafabril.model.reporte.ReporteAccionesExcel;
import com.promotick.lafabril.model.reporte.ReporteMetaExcel;
import com.promotick.lafabril.model.reporte.ReporteVisitaParticipanteDetallado;
import com.promotick.lafabril.model.util.*;
import com.promotick.lafabril.model.util.form.CargaAcciones;
import com.promotick.lafabril.model.util.form.CargaMetas;
import com.promotick.lafabril.model.web.Faq;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.text.SimpleDateFormat;
import java.util.List;

@Slf4j
@Controller
@RequestMapping("reportes/acciones")
public class ReporteAccionesController extends BaseController {

    private ReporteAdminService reporteAdminService;
    private CargaExcelAdminService cargaExcelAdminService;

    @Autowired
    public ReporteAccionesController(ReporteAdminService reporteAdminService, CargaExcelAdminService cargaExcelAdminService) {
        this.reporteAdminService = reporteAdminService;
        this.cargaExcelAdminService = cargaExcelAdminService;
    }

    @GetMapping
    public String init() {
        return ConstantesAdminView.VIEW_REPORTE_ACCIONES;
    }


    @ResponseBody
    @PostMapping(value = "listar-acciones")
    public Datatable listarMetas() {
        Datatable datatable = new Datatable();

        List<ReporteAcciones> listarAcciones = reporteAdminService.listarAcciones();
        datatable.setData(listarAcciones);
        datatable.setRecordsFiltered(listarAcciones.size());
        datatable.setRecordsTotal(listarAcciones.size());
        return datatable;
    }

    @ResponseBody
    @PostMapping(value = "editar")
    public PromotickResult editar(@RequestBody FiltroAcciones filtroAcciones, Auditoria auditoria, PromotickResult promotickResult) {
        try {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            CargaAcciones cargaAcciones = new CargaAcciones();
            cargaAcciones.setCantidad(filtroAcciones.getCantidad());
            cargaAcciones.setDescripcion(filtroAcciones.getDescripcion());
            cargaAcciones.setFecha(formatter.parse(filtroAcciones.getFecha()));
            cargaAcciones.setIdLafabrilProducto(filtroAcciones.getIdLafabrilProducto());
            cargaAcciones.setAccion(filtroAcciones.getAccion());
            cargaAcciones.setOperacion("CREAR");
            cargaAcciones.setUsuarioCreacion(auditoria.getUsuarioCreacion());
            Integer resultado = cargaExcelAdminService.registrarAccionesMetas(cargaAcciones);
            this.evaluarResultado(resultado,  promotickResult, "Editado Correctamente");
        } catch (Exception e) {
            promotickResult.setException(e);
        }
        return promotickResult;
    }

    @ResponseBody
    @PostMapping(value = "borrar")
    public PromotickResult borrar(@RequestBody FiltroMetas filtroMetas, Auditoria auditoria, PromotickResult promotickResult) {
        try {
            CargaAcciones cargaAcciones = new CargaAcciones();
            cargaAcciones.setListaId(filtroMetas.getIdMetas());
            cargaAcciones.setOperacion("ELIMINAR");
            cargaAcciones.setUsuarioCreacion(auditoria.getUsuarioCreacion());
            cargaExcelAdminService.registrarAccionesMetas(cargaAcciones);
            this.evaluarResultado(1,  promotickResult, "Eliminado Correctamente");
        } catch (Exception e) {
            promotickResult.setException(e);
        }
        return promotickResult;
    }

    @GetMapping(value = "descargar-excel")
    public ModelAndView descargarExcelPedidos() {
        return new ModelAndView(
                ExcelBuilder.getInstance(ReporteAccionesExcel.class)
                        .setList(ReporteAccionesExcel.getFromEntities(reporteAdminService.listarAcciones()))
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
