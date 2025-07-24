package com.promotick.lafabril.admin.service;

import com.promotick.apiclient.utils.file.validator.FileValidatorResult;
import com.promotick.lafabril.model.facturacion.CargaProceso;
import com.promotick.lafabril.model.util.descarga.FormBulkFacturas;
import com.promotick.lafabril.model.util.descarga.FormBulkParticipantes;

public interface BulkAdminService {
    FileValidatorResult<FormBulkParticipantes, FormBulkParticipantes> procesarCargaParticipantes(String path, CargaProceso cargaProceso) throws Exception;

    FileValidatorResult<FormBulkFacturas, FormBulkFacturas> procesarCargaFacturas(String path, CargaProceso cargaProceso) throws Exception;
}
