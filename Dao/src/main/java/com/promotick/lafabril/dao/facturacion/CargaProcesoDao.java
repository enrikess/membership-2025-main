package com.promotick.lafabril.dao.facturacion;

import com.promotick.lafabril.model.facturacion.CargaProceso;
import com.promotick.lafabril.model.util.descarga.FormBulkFacturas;
import com.promotick.lafabril.model.util.descarga.FormBulkParticipantes;

import java.util.List;

public interface CargaProcesoDao {
    Integer registrarCargaProceso(CargaProceso cargaProceso);

    Integer actualizarCargaProceso(CargaProceso cargaProceso);

    Integer bulkParticipantesRegistro(FormBulkParticipantes formBulkParticipantes);

    List<FormBulkParticipantes> bulkParticipantesProcesar(CargaProceso cargaProceso);

    Integer bulkFacturasRegistro(FormBulkFacturas formBulkFacturas);

    List<FormBulkFacturas> bulkFacturasProcesar(CargaProceso cargaProceso);
}
