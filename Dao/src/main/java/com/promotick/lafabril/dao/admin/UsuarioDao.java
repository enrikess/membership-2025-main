package com.promotick.lafabril.dao.admin;

import com.promotick.lafabril.model.admin.Usuario;

import java.util.List;

public interface UsuarioDao {

    Usuario autenticar(Usuario usuario);

    List<Usuario> listarUsuarios(Integer idUsuario, Integer orden);

    Integer actualizarUsuario(Usuario usuario);
}
