package com.promotick.lafabril.admin.controller;

import com.promotick.lafabril.admin.util.BaseController;
import com.promotick.lafabril.admin.util.Util;
import com.promotick.lafabril.admin.util.ConstantesAdminView;
import com.promotick.lafabril.admin.service.UsuarioAdminService;
import com.promotick.lafabril.common.ConstantesSesion;
import com.promotick.lafabril.model.admin.Rol;
import com.promotick.lafabril.model.admin.Usuario;
import com.promotick.lafabril.model.util.Auditoria;
import com.promotick.lafabril.model.util.PromotickResult;
import com.promotick.lafabril.model.util.UtilEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Controller
@RequestMapping("perfil")
public class PerfilController extends BaseController {

    private BCryptPasswordEncoder passwordEncoder;
    private UsuarioAdminService usuarioAdminService;

    @Autowired
    public PerfilController(BCryptPasswordEncoder passwordEncoder, UsuarioAdminService usuarioAdminService) {
        this.passwordEncoder = passwordEncoder;
        this.usuarioAdminService = usuarioAdminService;
    }

    @GetMapping
    public String init() {
        return ConstantesAdminView.VIEW_PERFIL;
    }

    @ResponseBody
    @PostMapping(value = "cambiar-clave")
    public PromotickResult cambiarClave(@RequestBody Usuario usuario, Usuario usuarioSesion, Auditoria auditoria, PromotickResult promotickResult) {
        try {

            boolean verficiarClave = passwordEncoder.matches(usuario.getClave(), usuarioSesion.getClave());
            if (verficiarClave) {
                String nuevaclave = passwordEncoder.encode(usuario.getNuevaClave());

                Usuario usuarioUpdate = new Usuario()
                        .setIdUsuario(usuarioSesion.getIdUsuario())
                        .setClave(nuevaclave)
                        .setRol(new Rol());

                usuarioUpdate.setAuditoria(auditoria);
                usuarioUpdate.setAccion(UtilEnum.MANTENIMIENTO.CAMBIAR_CLAVE.getCodigo());

                Integer resultado = usuarioAdminService.actualizarUsuario(usuarioUpdate);

                if (resultado > 0) {
                    Util.getSession().setAttribute(ConstantesSesion.SESSION_USUARIO, usuarioSesion.setClave(nuevaclave));
                }

                evaluarResultado(resultado, promotickResult, "Clave cambiada con exito");

            } else {
                throw new Exception("Clave actual es incorrecta");
            }
        } catch (Exception e) {
            promotickResult.setException(e);
        }
        return promotickResult;
    }
}
