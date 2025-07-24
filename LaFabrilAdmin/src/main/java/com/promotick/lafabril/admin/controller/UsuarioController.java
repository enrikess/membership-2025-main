package com.promotick.lafabril.admin.controller;

import com.promotick.lafabril.admin.util.BaseController;
import com.promotick.lafabril.admin.util.ConstantesAdminView;
import com.promotick.lafabril.model.util.*;
import com.promotick.lafabril.admin.service.RolAdminService;
import com.promotick.lafabril.admin.service.UsuarioAdminService;
import com.promotick.lafabril.common.ConstantesCommon;
import com.promotick.lafabril.model.admin.Rol;
import com.promotick.lafabril.model.admin.Usuario;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.thymeleaf.util.StringUtils;

import java.util.List;

@Slf4j
@Controller
@RequestMapping("administracion/usuarios")
public class UsuarioController extends BaseController {

    private BCryptPasswordEncoder passwordEncoder;
    private UsuarioAdminService usuarioAdminService;
    private RolAdminService rolAdminService;

    @Autowired
    public UsuarioController(BCryptPasswordEncoder passwordEncoder, UsuarioAdminService usuarioAdminService, RolAdminService rolAdminService) {
        this.passwordEncoder = passwordEncoder;
        this.usuarioAdminService = usuarioAdminService;
        this.rolAdminService = rolAdminService;
    }

    @GetMapping
    public String inicio() {
        return ConstantesAdminView.VIEW_ADMINISTRACION_USUARIOS;
    }

    @GetMapping(value = "registrar")
    public String registrar(Model model) {
        List<Rol> roles = rolAdminService.obtenerRoles(
                new FiltroRoles()
                        .setIdRol(ConstantesCommon.ZERO_VALUE)
                        .setEstadoRol(UtilEnum.ESTADO.ACTIVO.getCodigo())
                        .setStart(-1)
        );
        model.addAttribute("roles", roles);
        model.addAttribute("objUsuario", new Usuario());
        return ConstantesAdminView.VIEW_ADMINISTRACION_USUARIOS_DETALLE;
    }

    @GetMapping(value = "{idUsuario}")
    public String registrar(@PathVariable("idUsuario") Integer idUsuario, Model model, Usuario usuario) {
        List<Usuario> usuarios = usuarioAdminService.listarUsuarios(idUsuario, 1);
        if (usuarios.isEmpty()) {
            return "redirect:/administracion/usuarios";
        }
        model.addAttribute("objUsuario", usuarios.get(0));

        List<Rol> roles = rolAdminService.obtenerRoles(new FiltroRoles()
                .setIdRol(ConstantesCommon.ZERO_VALUE)
                .setEstadoRol(UtilEnum.ESTADO.ACTIVO.getCodigo())
                .setStart(-1));
        model.addAttribute("roles", roles);

        return ConstantesAdminView.VIEW_ADMINISTRACION_USUARIOS_DETALLE;
    }


    @ResponseBody
    @GetMapping(value = "listar-usuarios/{orden}")
    public Datatable listarUsuarios(@PathVariable("orden") Integer orden) {
        List<Usuario> lista = usuarioAdminService.listarUsuarios(ConstantesCommon.ZERO_VALUE, orden);
        Datatable datatable = new Datatable();
        datatable.setData(lista);
        datatable.setRecordsTotal(lista.size());
        datatable.setRecordsFiltered(lista.size());
        return datatable;
    }

    @ResponseBody
    @PostMapping(value = "guardar-usuario")
    public PromotickResult guardarUsuario(@RequestBody Usuario usuario, Auditoria auditoria, Usuario usuarioSesion, PromotickResult promotickResult) {
        try {
            if (usuario.getIdUsuario() == null || usuario.getIdUsuario().equals(0)) {
                usuario.setAccion(UtilEnum.MANTENIMIENTO.REGISTRAR.getCodigo());
            } else {
                usuario.setAccion(UtilEnum.MANTENIMIENTO.ACTUALIZAR.getCodigo());
            }

            if (!StringUtils.isEmpty(usuario.getClave())) {
                usuario.setClave(passwordEncoder.encode(usuario.getClave()));
            }

            usuario.setAuditoria(auditoria);

            Integer resultado = usuarioAdminService.actualizarUsuario(usuario);
            evaluarResultado(resultado, promotickResult, "Usuario procesado correctamente");
        } catch (Exception e) {
            promotickResult.setException(e);
        }
        return promotickResult;
    }

    @ResponseBody
    @PostMapping(value = "actualizar-estado")
    public PromotickResult actualizarEstado(@RequestBody Usuario usuario, Auditoria auditoria, PromotickResult promotickResult) {
        log.info("actualizar-estado");
        try {
            if (usuario.getEstadoUsuario().equals(UtilEnum.ESTADO.ACTIVO.getCodigo())) {
                usuario.setEstadoUsuario(UtilEnum.ESTADO.INACTIVO.getCodigo());
            } else if (usuario.getEstadoUsuario().equals(UtilEnum.ESTADO.INACTIVO.getCodigo())) {
                usuario.setEstadoUsuario(UtilEnum.ESTADO.ACTIVO.getCodigo());
            }
            usuario.setAccion(UtilEnum.MANTENIMIENTO.CAMBIAR_ESTADO.getCodigo());
            usuario.setAuditoria(auditoria);
            Integer resultado = usuarioAdminService.actualizarUsuario(usuario);
            this.evaluarResultado(resultado, promotickResult, "Estado de usuario cambiado correctamente");

        } catch (Exception e) {
            promotickResult.setException(e);
        }
        return promotickResult;
    }

}
