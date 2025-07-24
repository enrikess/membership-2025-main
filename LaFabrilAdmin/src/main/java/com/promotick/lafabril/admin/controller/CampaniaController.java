package com.promotick.lafabril.admin.controller;

import com.promotick.lafabril.admin.service.CampaniaAdminService;
import com.promotick.lafabril.admin.service.CatalogoAdminService;
import com.promotick.lafabril.admin.util.BaseController;
import com.promotick.lafabril.admin.util.ConstantesAdminView;
import com.promotick.lafabril.common.ConstantesCommon;
import com.promotick.lafabril.common.UtilCommon;
import com.promotick.lafabril.model.util.Auditoria;
import com.promotick.lafabril.model.util.Datatable;
import com.promotick.lafabril.model.util.PromotickResult;
import com.promotick.lafabril.model.util.UtilEnum;
import com.promotick.lafabril.model.web.Campania;
import com.promotick.lafabril.model.web.Catalogo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@Controller
@RequestMapping("administracion/campania")
public class CampaniaController extends BaseController {

    private CampaniaAdminService campaniaAdminService;

    @Autowired
    public CampaniaController(CampaniaAdminService campaniaAdminService) {
        this.campaniaAdminService = campaniaAdminService;
    }

    @GetMapping
    public String init() {
        return ConstantesAdminView.VIEW_ADMINISTRACION_CAMPANIAS;
    }

    @GetMapping(value = "editar/{idCampania}")
    public String registrar(@PathVariable("idCampania") Integer idCampania, Model model) {

        List<Campania> campanias = campaniaAdminService.listarCampanias(idCampania, 1);
        if (campanias .isEmpty()) {
            return "redirect:/administracion/campania";
        }
        model.addAttribute("objCampania", campanias .get(0));

        return ConstantesAdminView.VIEW_ADMINISTRACION_CAMPANIAS_DETALLE;
    }

    @GetMapping(value = "registrar")
    public String registrar(Model model) {
        model.addAttribute("objCampania", new Campania());
        return ConstantesAdminView.VIEW_ADMINISTRACION_CAMPANIAS_DETALLE;
    }

    @ResponseBody
    @GetMapping(value = "listar")
    public Datatable listarCampanias() {
        List<Campania> lista = campaniaAdminService.listarCampanias(ConstantesCommon.ZERO_VALUE, 1);
        Datatable datatable = new Datatable();
        datatable.setData(lista);
        datatable.setRecordsFiltered(lista.size());
        datatable.setRecordsTotal(lista.size());
        return datatable;
    }

    @ResponseBody
    @PostMapping(value = "guardar-campania")
    public PromotickResult guardarCampania(@RequestBody Campania campania, PromotickResult promotickResult, Auditoria auditoria) {
        try {
            campania.setFechaInicio(UtilCommon.stringToDate(campania.getFechaInicioString()));
            campania.setFechaFin(UtilCommon.stringToDate(campania.getFechaFinString()));

            campania.setAuditoria(auditoria);
            Integer resultado = campaniaAdminService.actualizarCampania(campania);
            this.evaluarResultado(resultado, promotickResult, "Campaña guardada correctamente");
        } catch (Exception e) {
            promotickResult.setException(e);
        }
        return promotickResult;
    }

    @ResponseBody
    @PostMapping(value = "actualizar-estado")
    public PromotickResult actualizarEstado(@RequestBody Campania campania, PromotickResult promotickResult) {
        try {

            if (campania.getEstadoCampania().equals(UtilEnum.ESTADO.ACTIVO.getCodigo())) {
                campania.setEstadoCampania(UtilEnum.ESTADO.INACTIVO.getCodigo());
            } else if (campania.getEstadoCampania().equals(UtilEnum.ESTADO.INACTIVO.getCodigo())) {
                campania.setEstadoCampania(UtilEnum.ESTADO.ACTIVO.getCodigo());
            }
            Integer resultado = campaniaAdminService.actualizarEstadoCampania(campania.getIdCampania(), campania.getEstadoCampania());
            evaluarResultado(resultado, promotickResult, "campania actualizada correctamente");
        } catch (Exception e) {
            promotickResult.setException(e);
        }
        return promotickResult;
    }
}
