package com.promotick.lafabril.admin.service;

import com.promotick.lafabril.model.facturacion.CargaProceso;
import com.promotick.lafabril.model.util.descarga.FormBulkFacturas;
import com.promotick.lafabril.model.util.descarga.FormBulkParticipantes;

import java.util.List;

public interface CargaProcesoAdminService {
    Integer registrarCargaProceso(CargaProceso cargaProceso);

    Integer actualizarCargaProceso(CargaProceso cargaProceso);

    List<FormBulkParticipantes> bulkParticipantesProcesar(CargaProceso cargaProceso);

    List<FormBulkFacturas> bulkFacturasProcesar(CargaProceso cargaProceso);
}
