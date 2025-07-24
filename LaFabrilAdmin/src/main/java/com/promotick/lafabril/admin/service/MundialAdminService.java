package com.promotick.lafabril.admin.service;

import com.promotick.lafabril.model.web.Mensaje;
import com.promotick.lafabril.model.web.PartidoMundial;

import java.util.List;

public interface MundialAdminService {
    List<PartidoMundial> listarFechasPartido();
}
