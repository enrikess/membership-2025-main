package com.promotick.lafabril.admin.controller;

import com.promotick.apiclient.utils.file.excel.generator.ExcelBuilder;
import com.promotick.lafabril.admin.service.MarcaAdminService;
import com.promotick.lafabril.admin.util.BaseController;
import com.promotick.lafabril.admin.util.ConstantesAdminView;
import com.promotick.lafabril.model.util.*;
import com.promotick.lafabril.model.reporte.ReporteMarca;
import com.promotick.lafabril.model.web.Marca;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Slf4j
@Controller
@RequestMapping("catalogos/marcas")
public class MarcaController extends BaseController {

    private MarcaAdminService marcaAdminService;

    @Autowired
    public MarcaController(MarcaAdminService marcaAdminService) {
        this.marcaAdminService = marcaAdminService;
    }

    @GetMapping
    public String init() {
        return ConstantesAdminView.VIEW_MARCAS;
    }

    @GetMapping(value = "registrar")
    public String registrar(Model model) {
        model.addAttribute("objMarca", new Marca());
        return ConstantesAdminView.VIEW_MARCAS_DETALLE;
    }

    @GetMapping(value = "{idMarca}")
    public String registrar(@PathVariable("idMarca") Integer idMarca, Model model) {
        List<Marca> marcas = marcaAdminService.listarMarcas(new FiltroMarca().setIdMarca(idMarca));
        if (marcas.isEmpty()) {
            return "redirect:/administracion/marcas";
        }
        model.addAttribute("objMarca", marcas.get(0));

        return ConstantesAdminView.VIEW_MARCAS_DETALLE;
    }

    @ResponseBody
    @PostMapping(value = "listar-marcas")
    public Datatable listarMarcas(FiltroMarca filtroMarca) {
        Integer conteo = marcaAdminService.contarMarcasFiltro(filtroMarca);
        Datatable datatable = new Datatable();
        datatable.setData(marcaAdminService.listarMarcasFiltro(filtroMarca));
        datatable.setRecordsFiltered(conteo);
        datatable.setRecordsTotal(conteo);
        return datatable;
    }

    @ResponseBody
    @PostMapping(value = "guardar-marca")
    public PromotickResult guardarMarca(@RequestBody Marca marca, Auditoria auditoria, PromotickResult promotickResult) {
        try {
            if (marca.getIdMarca() == null || marca.getIdMarca().equals(0)) {
                marca.setAccion(UtilEnum.MANTENIMIENTO.REGISTRAR.getCodigo());
            } else {
                marca.setAccion(UtilEnum.MANTENIMIENTO.ACTUALIZAR.getCodigo());
            }
            marca.setAuditoria(auditoria);
            Integer resultado = marcaAdminService.actualizarMarca(marca);
            this.evaluarResultado(resultado, promotickResult, "Marca registrada con exito");

        } catch (Exception e) {
            promotickResult.setException(e);
        }
        return promotickResult;
    }

    @ResponseBody
    @RequestMapping(value = "actualizar-estado", method = RequestMethod.POST)
    public PromotickResult actualizarEstado(@RequestBody Marca marca, Auditoria auditoria, PromotickResult promotickResult) {
        try {
            if (marca.getEstadoMarca().equals(UtilEnum.ESTADO.ACTIVO.getCodigo())) {
                marca.setEstadoMarca(UtilEnum.ESTADO.INACTIVO.getCodigo());
            } else if (marca.getEstadoMarca().equals(UtilEnum.ESTADO.INACTIVO.getCodigo())) {
                marca.setEstadoMarca(UtilEnum.ESTADO.ACTIVO.getCodigo());
            }
            marca.setAccion(UtilEnum.MANTENIMIENTO.CAMBIAR_ESTADO.getCodigo());
            marca.setAuditoria(auditoria);

            Integer resultado = marcaAdminService.actualizarMarca(marca);
            this.evaluarResultado(resultado, promotickResult, "Estado de la marca se cambio con exito");

        } catch (Exception e) {
            promotickResult.setException(e);
        }
        return promotickResult;
    }

    @RequestMapping(value = "descargar-excel", method = RequestMethod.GET)
    public ModelAndView descargarExcelMarcas() {
        return new ModelAndView(
                ExcelBuilder.getInstance(ReporteMarca.class)
                        .setList(marcaAdminService.reporteMarcas(new FiltroMarca()))
                        .buildView()
        );
    }

}
