package com.promotick.lafabril.dao.admin;

import com.promotick.lafabril.model.admin.Rol;
import com.promotick.lafabril.model.admin.RolUrl;
import com.promotick.lafabril.model.util.FiltroRoles;

import java.util.List;

public interface RolDao {

    List<RolUrl> obtenerRolesUrl();

    List<Rol> obtenerRoles(FiltroRoles filtroRoles);

    Integer actualizarRol(Rol rol);

    Integer contarRol();
}
