package com.promotick.lafabril.dao.facturacion;

import com.promotick.lafabril.model.facturacion.Proceso;

public interface ProcesoDao {
    Integer registrarProceso(String tipoProceso);

    Integer finalizarProceso(Proceso proceso);

    Boolean procesoEnEjecucion();

    Integer procesoVencimientoPuntos();

    void initRestaurarProductos();
}
