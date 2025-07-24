package com.promotick.lafabril.admin.controller;

import com.promotick.lafabril.admin.service.CampaniaAdminService;
import com.promotick.lafabril.admin.service.CatalogoAdminService;
import com.promotick.lafabril.admin.service.DescuentoAdminService;
import com.promotick.lafabril.admin.util.BaseController;
import com.promotick.lafabril.admin.util.ConstantesAdminView;
import com.promotick.lafabril.common.ConstantesCommon;
import com.promotick.lafabril.model.util.Auditoria;
import com.promotick.lafabril.model.util.Datatable;
import com.promotick.lafabril.model.util.PromotickResult;
import com.promotick.lafabril.model.util.UtilEnum;
import com.promotick.lafabril.model.web.Campania;
import com.promotick.lafabril.model.web.Descuento;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@Controller
@RequestMapping("administracion/descuento")
public class DescuentoController extends BaseController {

    private DescuentoAdminService descuentoAdminService;
    private CatalogoAdminService catalogoAdminService;

    @Autowired
    public DescuentoController(DescuentoAdminService descuentoAdminService, CatalogoAdminService catalogoAdminService) {
        this.descuentoAdminService = descuentoAdminService;
        this.catalogoAdminService = catalogoAdminService;
    }

    @GetMapping
    public String init() {
        return ConstantesAdminView.VIEW_ADMINISTRACION_DESCUENTOS;
    }

    @GetMapping(value = "editar/{idDescuento}")
    public String registrar(@PathVariable("idDescuento") Integer idDescuento, Model model) {

        List<Descuento> descuentos = descuentoAdminService.listarDescuentos(idDescuento, 1);
        if (descuentos .isEmpty()) {
            return "redirect:/administracion/descuento";
        }
        model.addAttribute("objDescuento", descuentos .get(0));
        model.addAttribute("catalogosList", catalogoAdminService.listarCatalogos());
        return ConstantesAdminView.VIEW_ADMINISTRACION_DESCUENTOS_DETALLE;
    }

    @GetMapping(value = "registrar")
    public String registrar(Model model) {
        model.addAttribute("objDescuento", new Descuento());
        model.addAttribute("catalogosList", catalogoAdminService.listarCatalogos());
        return ConstantesAdminView.VIEW_ADMINISTRACION_DESCUENTOS_DETALLE;
    }

    @ResponseBody
    @GetMapping(value = "listar")
    public Datatable listarDescuentos() {
        List<Descuento> lista = descuentoAdminService.listarDescuentos(ConstantesCommon.ZERO_VALUE, 1);
        Datatable datatable = new Datatable();
        datatable.setData(lista);
        datatable.setRecordsFiltered(lista.size());
        datatable.setRecordsTotal(lista.size());
        return datatable;
    }

    @ResponseBody
    @PostMapping(value = "guardar-descuento")
    public PromotickResult guardarDescuento(@RequestBody Descuento descuento, PromotickResult promotickResult, Auditoria auditoria) {
        try {
            descuento.setAuditoria(auditoria);
            Integer resultado = descuentoAdminService.actualizarDescuento(descuento);
            this.evaluarResultado(resultado, promotickResult, "Segmento guardado correctamente");
        } catch (Exception e) {
            promotickResult.setException(e);
        }
        return promotickResult;
    }

    @ResponseBody
    @PostMapping(value = "actualizar-estado")
    public PromotickResult actualizarEstado(@RequestBody Descuento descuento, PromotickResult promotickResult) {
        try {

            if (descuento.getEstadoDescuento().equals(UtilEnum.ESTADO.ACTIVO.getCodigo())) {
                descuento.setEstadoDescuento(UtilEnum.ESTADO.INACTIVO.getCodigo());
            } else if (descuento.getEstadoDescuento().equals(UtilEnum.ESTADO.INACTIVO.getCodigo())) {
                descuento.setEstadoDescuento(UtilEnum.ESTADO.ACTIVO.getCodigo());
            }
            Integer resultado = descuentoAdminService.actualizarEstadoDescuento(descuento.getIdDescuento(), descuento.getEstadoDescuento());
            evaluarResultado(resultado, promotickResult, "campania actualizada correctamente");
        } catch (Exception e) {
            promotickResult.setException(e);
        }
        return promotickResult;
    }
}
