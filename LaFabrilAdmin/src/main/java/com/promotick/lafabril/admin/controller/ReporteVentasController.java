package com.promotick.lafabril.admin.controller;

import com.promotick.apiclient.utils.file.excel.generator.ExcelBuilder;
import com.promotick.lafabril.admin.service.CargaExcelAdminService;
import com.promotick.lafabril.admin.service.FacturaAdminService;
import com.promotick.lafabril.admin.util.BaseController;
import com.promotick.lafabril.admin.util.ConstantesAdminView;
import com.promotick.lafabril.common.ConstantesSesion;
import com.promotick.lafabril.model.reporte.ReporteAvance;
import com.promotick.lafabril.model.reporte.ReporteVentas;
import com.promotick.lafabril.model.util.*;
import com.promotick.lafabril.model.util.form.CargaVentas;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.text.SimpleDateFormat;
@Controller
@RequestMapping("reportes/ventas")
public class ReporteVentasController extends BaseController {

    private FacturaAdminService facturaAdminService;
    private CargaExcelAdminService cargaExcelAdminService;

    @Autowired
    public ReporteVentasController(FacturaAdminService facturaAdminService, CargaExcelAdminService cargaExcelAdminService) {
        this.facturaAdminService = facturaAdminService;
        this.cargaExcelAdminService = cargaExcelAdminService;
    }

    @GetMapping
    public String init() {
        return ConstantesAdminView.VIEW_REPORTE_VENTAS;
    }

    @ResponseBody
    @PostMapping(value = "listar-ventas")
    public Datatable listaVentas(FiltroReporteVentas filtroReporteVentas) {
        Datatable datatable = new Datatable();

        Util.getSession().setAttribute(ConstantesSesion.SESSION_FILTRO_REPORTE_VENTAS, filtroReporteVentas);

        Integer total = facturaAdminService.reporteVentasContar(filtroReporteVentas);
        datatable.setData(facturaAdminService.reporteVentasListar(filtroReporteVentas));
        datatable.setRecordsFiltered(total);
        datatable.setRecordsTotal(total);
        return datatable;
    }

    @ResponseBody
    @PostMapping(value = "borrar-ventas")
    public PromotickResult borrarVentas(@RequestBody FiltroReporteVentas filtroReporteVentas, Auditoria auditoria, PromotickResult promotickResult) {
        try {
            CargaVentas cargaVentas = new CargaVentas();
            cargaVentas.setListaIds(filtroReporteVentas.getIdVentas());
            cargaVentas.setUsuarioCreacion(auditoria.getUsuarioCreacion());
            cargaVentas.setOperacion("ELIMINAR");
            cargaExcelAdminService.registrarCargaVentas(cargaVentas);
            this.evaluarResultado(1,  promotickResult, "Eliminado Correctamente");
        } catch (Exception e) {
            promotickResult.setException(e);
        }
        return promotickResult;
    }

    @ResponseBody
    @PostMapping(value = "editar-venta")
    public PromotickResult editarVentas(@RequestBody FiltroReporteVentas filtroReporteVentas, Auditoria auditoria, PromotickResult promotickResult) {
        try {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            CargaVentas cargaVentas = new CargaVentas();
            cargaVentas.setIdParticipanteAvance(filtroReporteVentas.getIdParticipanteVenta());
            cargaVentas.setNroDocumento(filtroReporteVentas.getNroDocumento());
            cargaVentas.setValorVenta(filtroReporteVentas.getValorVenta());
            cargaVentas.setDescripcion(filtroReporteVentas.getDescripcion());
            cargaVentas.setFechaOperacion(formatter.parse(filtroReporteVentas.getFecha()));
            cargaVentas.setUsuarioCreacion(auditoria.getUsuarioCreacion());
            cargaVentas.setOperacion("MODIFICAR");
            Integer resultado = cargaExcelAdminService.registrarCargaVentas(cargaVentas);
            this.evaluarResultado(resultado,  promotickResult, "Editado Correctamente");
        } catch (Exception e) {
            promotickResult.setException(e);
        }
        return promotickResult;
    }

    @GetMapping(value = "descargar-excel")
    public ModelAndView descargarExcelPedidos() {
        FiltroReporteVentas filtroReporteVentas = (FiltroReporteVentas) Util.getSession().getAttribute(ConstantesSesion.SESSION_FILTRO_REPORTE_VENTAS);
        filtroReporteVentas.setLength(-1);

        return new ModelAndView(
                ExcelBuilder.getInstance(ReporteAvance.class)
                        .setList(ReporteAvance.getFromEntities(facturaAdminService.reporteVentasListar(filtroReporteVentas)))
                        .buildView()
        );
    }
}
