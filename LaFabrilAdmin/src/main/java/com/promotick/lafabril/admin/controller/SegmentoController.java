package com.promotick.lafabril.admin.controller;

import com.promotick.lafabril.admin.util.BaseController;
import com.promotick.lafabril.admin.util.ConstantesAdminView;
import com.promotick.lafabril.admin.service.CatalogoAdminService;
import com.promotick.lafabril.common.ConstantesCommon;
import com.promotick.lafabril.model.util.Auditoria;
import com.promotick.lafabril.model.util.Datatable;
import com.promotick.lafabril.model.util.PromotickResult;
import com.promotick.lafabril.model.util.UtilEnum;
import com.promotick.lafabril.model.web.Catalogo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@Controller
@RequestMapping("administracion/segmentos")
public class SegmentoController extends BaseController {

    private CatalogoAdminService catalogoAdminService;

    @Autowired
    public SegmentoController(CatalogoAdminService catalogoAdminService) {
        this.catalogoAdminService = catalogoAdminService;
    }

    @GetMapping
    public String init() {
        return ConstantesAdminView.VIEW_ADMINISTRACION_SEGMENTOS;
    }

    @GetMapping(value = "editar/{idSegmento}")
    public String registrar(@PathVariable("idSegmento") Integer idSegmento, Model model) {

        List<Catalogo> catalogos = catalogoAdminService.listarCatalogos(idSegmento, 1);
        if (catalogos.isEmpty()) {
            return "redirect:/administracion/segmentos";
        }
        model.addAttribute("objSegmento", catalogos.get(0));

        return ConstantesAdminView.VIEW_ADMINISTRACION_SEGMENTOS_DETALLE;
    }

    @GetMapping(value = "registrar")
    public String registrar(Model model) {
        model.addAttribute("objSegmento", new Catalogo());
        return ConstantesAdminView.VIEW_ADMINISTRACION_SEGMENTOS_DETALLE;
    }

    @ResponseBody
    @GetMapping(value = "listar/{orden}")
    public Datatable listarSegmentos(@PathVariable("orden") Integer orden) {
        List<Catalogo> lista = catalogoAdminService.listarCatalogos(ConstantesCommon.ZERO_VALUE, orden);
        Datatable datatable = new Datatable();
        datatable.setData(lista);
        datatable.setRecordsFiltered(lista.size());
        datatable.setRecordsTotal(lista.size());
        return datatable;
    }

    @ResponseBody
    @PostMapping(value = "guardar-segmento")
    public PromotickResult guardarCatalogo(@RequestBody Catalogo catalogo, PromotickResult promotickResult, Auditoria auditoria) {
        try {
            catalogo.setAuditoria(auditoria);
            Integer resultado = catalogoAdminService.actualizarCatalogo(catalogo);
            this.evaluarResultado(resultado, promotickResult, "Segmento guardado correctamente");
        } catch (Exception e) {
            promotickResult.setException(e);
        }
        return promotickResult;
    }

    @ResponseBody
    @PostMapping(value = "actualizar-estado")
    public PromotickResult actualizarEstado(@RequestBody Catalogo catalogo, PromotickResult promotickResult) {
        try {
            Integer tipoActualizar = UtilEnum.TIPO_ACTUALIZAR.CATALOGO.getCodigo();
            if (catalogo.getEstadoCatalogo().equals(UtilEnum.ESTADO.ACTIVO.getCodigo())) {
                catalogo.setEstadoCatalogo(UtilEnum.ESTADO.INACTIVO.getCodigo());
            } else if (catalogo.getEstadoCatalogo().equals(UtilEnum.ESTADO.INACTIVO.getCodigo())) {
                catalogo.setEstadoCatalogo(UtilEnum.ESTADO.ACTIVO.getCodigo());
            }
            Integer resultado = catalogoAdminService.actualizarEstadoCatalogo(catalogo.getIdCatalogo(), catalogo.getEstadoCatalogo(), tipoActualizar);
            evaluarResultado(resultado, promotickResult, "Segmento actualizado correctamente");
        } catch (Exception e) {
            promotickResult.setException(e);
        }
        return promotickResult;
    }
}
