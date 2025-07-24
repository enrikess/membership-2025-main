package com.promotick.lafabril.admin.service;

import com.promotick.lafabril.model.admin.Menu;

import java.util.List;

public interface MenuAdminService {
    List<Menu> listarMenuPorRol(Integer idRol);

    List<Menu> listarMenu();
}
