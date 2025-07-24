package com.promotick.lafabril.admin.controller;

import com.promotick.lafabril.admin.util.BaseController;
import com.promotick.lafabril.admin.service.MenuAdminService;
import com.promotick.lafabril.admin.util.ConstantesAdminView;
import com.promotick.lafabril.model.util.*;
import com.promotick.lafabril.admin.service.RolAdminService;
import com.promotick.lafabril.common.ConstantesCommon;
import com.promotick.lafabril.model.admin.Menu;
import com.promotick.lafabril.model.admin.Rol;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@Controller
@RequestMapping("administracion/roles")
public class RolController extends BaseController {

    private RolAdminService rolAdminService;
    private MenuAdminService menuService;

    @Autowired
    public RolController(RolAdminService rolAdminService, MenuAdminService menuService) {
        this.rolAdminService = rolAdminService;
        this.menuService = menuService;
    }

    @GetMapping
    public String init() {
        return ConstantesAdminView.VIEW_ADMINISTRACION_ROLES;
    }

    @GetMapping(value = "registrar")
    public String registrar(Model model) {
        model.addAttribute("objRol", new Rol());
        return ConstantesAdminView.VIEW_ADMINISTRACION_ROLES_DETALLE;
    }

    @GetMapping(value = "editar/{idRol}")
    public String registrar(Model model, @PathVariable("idRol") Integer idRol) {

        List<Rol> lista = rolAdminService.obtenerRoles(new FiltroRoles()
                .setIdRol(idRol)
                .setEstadoRol(ConstantesCommon.ZERO_VALUE)
                .setStart(-1));

        if (lista == null || lista.isEmpty()) {
            return "administracion/roles";
        }

        model.addAttribute("objRol", lista.get(0));
        return ConstantesAdminView.VIEW_ADMINISTRACION_ROLES_DETALLE;
    }

    @ResponseBody
    @RequestMapping(value = "detalle/{idRol}", method = RequestMethod.GET)
    public PromotickResult obtenerRol(Model model, @PathVariable("idRol") Integer idRol, PromotickResult promotickResult) {
        try {
            List<Rol> lista = rolAdminService.obtenerRoles(new FiltroRoles()
                    .setIdRol(idRol)
                    .setEstadoRol(ConstantesCommon.ZERO_VALUE)
                    .setStart(-1));

            if (lista == null || lista.isEmpty()) {
                throw new Exception("Rol no definido");
            }
            promotickResult.setData(lista.get(0));
        } catch (Exception e) {
            promotickResult.setException(e);
        }

        return promotickResult;
    }

    @ResponseBody
    @PostMapping(value = "listar-roles")
    public Datatable listarRoles(FiltroRoles filtroRoles) {
        Integer conteo = rolAdminService.contarRol();
        Datatable datatable = new Datatable();
        datatable.setRecordsTotal(conteo);
        datatable.setRecordsFiltered(conteo);
        datatable.setData(rolAdminService.obtenerRoles(filtroRoles));
        return datatable;
    }

    @ResponseBody
    @GetMapping(value = "listar-menus")
    public PromotickResult listarMenus(PromotickResult promotickResult) {
        try {
            promotickResult.setData(menuService.listarMenu());
        } catch (Exception e) {
            promotickResult.setException(e);
        }
        return promotickResult;
    }

    @ResponseBody
    @PostMapping(value = "listar-menusFaltantes")
    public PromotickResult listarMenusFaltantes(@RequestBody List<Menu> menus, PromotickResult promotickResult) {
        try {
            List<Menu> menusTodos = menuService.listarMenu();

            menusTodos.removeIf(e -> menus.stream()
                    .filter(a -> a.getIdMenu().intValue() == e.getIdMenu())
                    .findAny()
                    .orElse(null) != null);

            promotickResult.setData(menusTodos);
        } catch (Exception e) {
            promotickResult.setException(e);
        }
        return promotickResult;
    }


    @ResponseBody
    @PostMapping(value = "guardarRol")
    public PromotickResult guardarRol(@RequestBody Rol rol, Auditoria auditoria, PromotickResult promotickResult) {
        try {
            if (rol.getIdRol() == null || rol.getIdRol().equals(0)) {
                rol.setAccion(UtilEnum.MANTENIMIENTO.REGISTRAR.getCodigo());
            } else {
                rol.setAccion(UtilEnum.MANTENIMIENTO.ACTUALIZAR.getCodigo());
            }
            rol.setNombreRol(rol.getNombreRol().toUpperCase());
            rol.setEstadoRol(UtilEnum.ESTADO.ACTIVO.getCodigo());
            rol.setAuditoria(auditoria);
            Integer resultado = rolAdminService.actualizarRol(rol);
            this.evaluarResultado(resultado, promotickResult, "Rol guardado correctamente");
        } catch (Exception e) {
            promotickResult.setException(e);
        }
        return promotickResult;
    }

    @ResponseBody
    @PostMapping(value = "actualizar-estado")
    public PromotickResult actualizarEstado(@RequestBody Rol rol, Auditoria auditoria, PromotickResult promotickResult) {
        try {
            if (rol.getEstadoRol().equals(UtilEnum.ESTADO.ACTIVO.getCodigo())) {
                rol.setEstadoRol(UtilEnum.ESTADO.INACTIVO.getCodigo());
            } else if (rol.getEstadoRol().equals(UtilEnum.ESTADO.INACTIVO.getCodigo())) {
                rol.setEstadoRol(UtilEnum.ESTADO.ACTIVO.getCodigo());
            }
            rol.setAccion(UtilEnum.MANTENIMIENTO.CAMBIAR_ESTADO.getCodigo());
            rol.setAuditoria(auditoria);
            Integer resultado = rolAdminService.actualizarRol(rol);
            this.evaluarResultado(resultado, promotickResult, "Rol actualizado correctamente");

        } catch (Exception e) {
            promotickResult.setException(e);
        }
        return promotickResult;
    }

}
