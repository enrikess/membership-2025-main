package com.promotick.lafabril.admin.service;

import com.promotick.lafabril.model.facturacion.Proceso;

public interface ProcesoAdminService {
    Integer registrarProceso(String tipoProceso);

    Integer finalizarProceso(Proceso proceso);

    Boolean procesoEnEjecucion();

    Integer procesoVencimientoPuntos();

    void initRestaurarProductos();
}
