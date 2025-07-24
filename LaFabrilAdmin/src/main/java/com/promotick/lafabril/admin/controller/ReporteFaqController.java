package com.promotick.lafabril.admin.controller;

import com.promotick.apiclient.utils.file.excel.generator.ExcelBuilder;
import com.promotick.lafabril.admin.service.*;
import com.promotick.lafabril.admin.util.BaseController;
import com.promotick.lafabril.admin.util.ConstantesAdminView;
import com.promotick.lafabril.common.ConstantesCommon;
import com.promotick.lafabril.common.ConstantesSesion;
import com.promotick.lafabril.model.reporte.ReporteVentas;
import com.promotick.lafabril.model.util.*;
import com.promotick.lafabril.model.web.Catalogo;
import com.promotick.lafabril.model.web.Faq;
import com.promotick.lafabril.model.web.Tyc;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Objects;

@Controller
@RequestMapping("reportes/faq")
public class ReporteFaqController extends BaseController {

    private FacturaAdminService facturaAdminService;
    private CargaExcelAdminService cargaExcelAdminService;
    private CatalogoService catalogoService;
    private FaqService faqService;

    @Autowired
    public ReporteFaqController(FacturaAdminService facturaAdminService, CatalogoService catalogoService, CargaExcelAdminService cargaExcelAdminService, FaqService faqService) {
        this.facturaAdminService = facturaAdminService;
        this.cargaExcelAdminService = cargaExcelAdminService;
        this.faqService = faqService;
        this.catalogoService = catalogoService;
    }

    @GetMapping
    public String init(Model model) {
        model.addAttribute("catalogos", catalogoService.listarCatalogos());
        return ConstantesAdminView.VIEW_REPORTE_FAQ;
    }

    @ResponseBody
    @PostMapping(value = "listar")
    public Datatable listaTyc(FiltroFaq filtroFaq) {
        Datatable datatable = new Datatable();

        Util.getSession().setAttribute(ConstantesSesion.SESSION_FILTRO_REPORTE_TYC, filtroFaq);

        List<Faq> listFaq = faqService.listarFaq(ConstantesCommon.ZERO_VALUE, filtroFaq.getIdCatalogo(), UtilEnum.ESTADO.ACTIVO.getCodigo());
        datatable.setData(listFaq);
        datatable.setRecordsFiltered(listFaq.size());
        datatable.setRecordsTotal(listFaq.size());
        return datatable;
    }

    @ResponseBody
    @PostMapping(value = "borrar")
    public PromotickResult borrar(@RequestBody FiltroFaq filtroFaq, Auditoria auditoria, PromotickResult promotickResult) {
        try {
            filtroFaq.setOperacion("ELIMINAR");
            filtroFaq.setUsuarioCreacion(auditoria.getUsuarioCreacion());
            Integer resultado = cargaExcelAdminService.actualizarFaq(filtroFaq);
            this.evaluarResultado(resultado,  promotickResult, "Eliminado Correctamente");
        } catch (Exception e) {
            promotickResult.setException(e);
        }
        return promotickResult;
    }


    @ResponseBody
    @PostMapping(value = "crear")
    public PromotickResult crear(@RequestBody FiltroFaq filtroFaq, Auditoria auditoria, PromotickResult promotickResult) {
        try {
            filtroFaq.setOperacion("CREAR");
            filtroFaq.setUsuarioCreacion(auditoria.getUsuarioCreacion());
            Integer resultado = cargaExcelAdminService.actualizarFaq(filtroFaq);
            this.evaluarResultado(resultado,  promotickResult, "Creado Correctamente");
        } catch (Exception e) {
            promotickResult.setException(e);
        }
        return promotickResult;
    }

    @ResponseBody
    @PostMapping(value = "editar")
    public PromotickResult editar(@RequestBody FiltroFaq filtroFaq, Auditoria auditoria, PromotickResult promotickResult) {
        try {
            filtroFaq.setOperacion("CREAR");
            filtroFaq.setUsuarioCreacion(auditoria.getUsuarioCreacion());
            Integer resultado = cargaExcelAdminService.actualizarFaq(filtroFaq);
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
