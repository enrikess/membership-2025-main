package com.promotick.lafabril.admin.service;

import com.promotick.lafabril.model.admin.Usuario;

import java.util.List;

public interface UsuarioAdminService {
    Usuario autenticar(Usuario usuario);

    List<Usuario> listarUsuarios(Integer idUsuario, Integer orden);

    Integer actualizarUsuario(Usuario usuario);
}
