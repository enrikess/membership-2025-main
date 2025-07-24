package com.promotick.lafabril.admin.service;

import com.promotick.lafabril.model.facturacion.Carga;
import com.promotick.lafabril.model.web.CargaWeb;

public interface CargaAdminService {
    Integer registroCarga(Carga carga);

    boolean existenciaCarga(String nombreArchivo);

    Integer registroCargaWeb(CargaWeb cargaWeb);

    Integer actualizarCargaWeb(CargaWeb cargaWeb);
}
