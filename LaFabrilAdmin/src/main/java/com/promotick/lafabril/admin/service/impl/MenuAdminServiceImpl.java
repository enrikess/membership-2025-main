package com.promotick.lafabril.admin.service.impl;

import com.promotick.lafabril.admin.service.MenuAdminService;
import com.promotick.lafabril.dao.admin.MenuDao;
import com.promotick.lafabril.model.admin.Menu;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MenuAdminServiceImpl implements MenuAdminService {

    private MenuDao menuDao;

    @Autowired
    public MenuAdminServiceImpl(MenuDao menuDao) {
        this.menuDao = menuDao;
    }

    @Override
    public List<Menu> listarMenuPorRol(Integer idRol) {
        return menuDao.listarMenuPorRol(idRol);
    }

    @Override
    public List<Menu> listarMenu() {
        return menuDao.listarMenu();
    }

}
