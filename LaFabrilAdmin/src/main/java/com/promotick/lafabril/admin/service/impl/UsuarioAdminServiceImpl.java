package com.promotick.lafabril.admin.service.impl;

import com.promotick.lafabril.admin.service.UsuarioAdminService;
import com.promotick.lafabril.dao.admin.MenuDao;
import com.promotick.lafabril.dao.admin.UsuarioDao;
import com.promotick.lafabril.model.admin.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UsuarioAdminServiceImpl implements UsuarioAdminService {

    private UsuarioDao usuarioDao;
    private MenuDao menuDao;

    @Autowired
    public UsuarioAdminServiceImpl(UsuarioDao usuarioDao, MenuDao menuDao) {
        this.usuarioDao = usuarioDao;
        this.menuDao = menuDao;
    }

    @Override
    public Usuario autenticar(Usuario usuario) {
        Usuario objUsuario = usuarioDao.autenticar(usuario);
        if (objUsuario != null) {
            objUsuario.getRol().setMenus(menuDao.listarMenuPorRol(objUsuario.getRol().getIdRol()));
        }
        return objUsuario;
    }

    @Override
    public List<Usuario> listarUsuarios(Integer idUsuario, Integer orden) {
        return usuarioDao.listarUsuarios(idUsuario, orden);
    }

    @Override
    @Transactional
    public Integer actualizarUsuario(Usuario usuario) {
        return usuarioDao.actualizarUsuario(usuario);
    }
}
