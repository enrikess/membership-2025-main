package com.promotick.lafabril.dao.admin;

import com.promotick.lafabril.model.admin.Menu;

import java.util.List;

public interface MenuDao {

    List<Menu> listarMenuPorRol(Integer idRol);

    List<Menu> listarMenu();
}
