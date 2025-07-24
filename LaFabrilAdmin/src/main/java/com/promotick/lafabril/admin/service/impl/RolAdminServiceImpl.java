package com.promotick.lafabril.admin.service.impl;

import com.promotick.lafabril.admin.service.RolAdminService;
import com.promotick.lafabril.dao.admin.MenuDao;
import com.promotick.lafabril.dao.admin.RolDao;
import com.promotick.lafabril.model.admin.Menu;
import com.promotick.lafabril.model.admin.Rol;
import com.promotick.lafabril.model.admin.RolUrl;
import com.promotick.lafabril.model.util.FiltroRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class RolAdminServiceImpl implements RolAdminService {

    private RolDao rolDao;
    private MenuDao menuDao;

    @Autowired
    public RolAdminServiceImpl(RolDao rolDao, MenuDao menuDao) {
        this.rolDao = rolDao;
        this.menuDao = menuDao;
    }

    @Override
    public List<RolUrl> obtenerRolesUrl() {
        return rolDao.obtenerRolesUrl();
    }

    @Override
    public List<Rol> obtenerRoles(FiltroRoles filtroRoles) {
        List<Rol> roles = rolDao.obtenerRoles(filtroRoles);
        for (Rol rol : roles) {
            List<Menu> menus = menuDao.listarMenuPorRol(rol.getIdRol());
            rol.setMenus(menus);
        }
        return roles;
    }

    @Override
    @Transactional
    public Integer actualizarRol(Rol rol) {
        return rolDao.actualizarRol(rol);
    }

    @Override
    public Integer contarRol() {
        return rolDao.contarRol();
    }
}
