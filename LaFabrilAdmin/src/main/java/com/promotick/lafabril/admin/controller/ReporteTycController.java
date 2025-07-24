package com.promotick.lafabril.admin.controller;

import com.promotick.apiclient.utils.file.excel.generator.ExcelBuilder;
import com.promotick.lafabril.admin.service.CargaExcelAdminService;
import com.promotick.lafabril.admin.service.CatalogoService;
import com.promotick.lafabril.admin.service.FacturaAdminService;
import com.promotick.lafabril.admin.service.TycWebService;
import com.promotick.lafabril.admin.util.BaseController;
import com.promotick.lafabril.admin.util.ConstantesAdminView;
import com.promotick.lafabril.common.ConstantesCommon;
import com.promotick.lafabril.common.ConstantesSesion;
import com.promotick.lafabril.dao.web.CatalogoDao;
import com.promotick.lafabril.model.reporte.ReporteVentas;
import com.promotick.lafabril.model.util.*;
import com.promotick.lafabril.model.util.form.CargaVentas;
import com.promotick.lafabril.model.web.Capacitacion;
import com.promotick.lafabril.model.web.Catalogo;
import com.promotick.lafabril.model.web.Tyc;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Objects;

@Controller
@RequestMapping("reportes/tyc")
public class ReporteTycController extends BaseController {

    private FacturaAdminService facturaAdminService;
    private CargaExcelAdminService cargaExcelAdminService;
    private TycWebService tycWebService;
    private CatalogoService catalogoService;

    @Autowired
    public ReporteTycController(FacturaAdminService facturaAdminService, CatalogoService catalogoService, CargaExcelAdminService cargaExcelAdminService, TycWebService tycWebService) {
        this.facturaAdminService = facturaAdminService;
        this.cargaExcelAdminService = cargaExcelAdminService;
        this.tycWebService = tycWebService;
        this.catalogoService = catalogoService;
    }

    @GetMapping
    public String init(Model model) {
        model.addAttribute("catalogos", catalogoService.listarCatalogos());
        return ConstantesAdminView.VIEW_REPORTE_TYC;
    }

    @ResponseBody
    @GetMapping(value = "catalogos")
    public List<Catalogo> obtenerCatalogosDisponibles(Model model) {
        List<Catalogo> listCatalogo = catalogoService.listarCatalogos();
        List<Tyc> listTyc = tycWebService.listarTyc(ConstantesCommon.ZERO_VALUE, ConstantesCommon.ZERO_VALUE, UtilEnum.ESTADO.ACTIVO.getCodigo());

        listCatalogo.removeIf(item -> listTyc.stream().anyMatch(i -> Objects.equals(i.getCatalogo().getIdCatalogo(), item.getIdCatalogo())));
        return listCatalogo;
    }

    @ResponseBody
    @PostMapping(value = "listar")
    public Datatable listaTyc(FiltroTyc filtroTyc) {
        Datatable datatable = new Datatable();

        Util.getSession().setAttribute(ConstantesSesion.SESSION_FILTRO_REPORTE_TYC, filtroTyc);

        List<Tyc> listTyc = tycWebService.listarTyc(ConstantesCommon.ZERO_VALUE, ConstantesCommon.ZERO_VALUE, UtilEnum.ESTADO.ACTIVO.getCodigo());
        datatable.setData(listTyc);
        datatable.setRecordsFiltered(listTyc.size());
        datatable.setRecordsTotal(listTyc.size());
        return datatable;
    }

    @ResponseBody
    @PostMapping(value = "borrar")
    public PromotickResult borrar(@RequestBody FiltroTyc filtroTyc, Auditoria auditoria, PromotickResult promotickResult) {
        try {
            filtroTyc.setOperacion("ELIMINAR");
            filtroTyc.setUsuarioCreacion(auditoria.getUsuarioCreacion());
            Integer resultado = cargaExcelAdminService.actualizarTyc(filtroTyc);
            this.evaluarResultado(resultado,  promotickResult, "Eliminado Correctamente");
        } catch (Exception e) {
            promotickResult.setException(e);
        }
        return promotickResult;
    }


    @ResponseBody
    @PostMapping(value = "crear")
    public PromotickResult crear(@RequestBody FiltroTyc filtroTyc, Auditoria auditoria, PromotickResult promotickResult) {
        try {
            filtroTyc.setOperacion("CREAR");
            filtroTyc.setUsuarioCreacion(auditoria.getUsuarioCreacion());
            Integer resultado = cargaExcelAdminService.actualizarTyc(filtroTyc);
            this.evaluarResultado(resultado,  promotickResult, "Creado Correctamente");
        } catch (Exception e) {
            promotickResult.setException(e);
        }
        return promotickResult;
    }

    @ResponseBody
    @PostMapping(value = "editar")
    public PromotickResult editar(@RequestBody FiltroTyc filtroTyc, Auditoria auditoria, PromotickResult promotickResult) {
        try {
            filtroTyc.setOperacion("CREAR");
            filtroTyc.setUsuarioCreacion(auditoria.getUsuarioCreacion());
            Integer resultado = cargaExcelAdminService.actualizarTyc(filtroTyc);
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
                ExcelBuilder.getInstance(ReporteVentas.class)
                        .setList(ReporteVentas.getFromEntities(null))
                        .buildView()
        );
    }
}
