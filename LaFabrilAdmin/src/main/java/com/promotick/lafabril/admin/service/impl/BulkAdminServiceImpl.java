package com.promotick.lafabril.admin.service.impl;

import com.promotick.apiclient.utils.file.validator.FileValidatorResult;
import com.promotick.lafabril.admin.controller.excel.validator.BulkFacturasValidator;
import com.promotick.lafabril.admin.controller.excel.validator.BulkParticipantesValidator;
import com.promotick.lafabril.admin.service.BulkAdminService;
import com.promotick.lafabril.dao.facturacion.CargaProcesoDao;
import com.promotick.lafabril.model.facturacion.CargaProceso;
import com.promotick.lafabril.model.util.descarga.FormBulkFacturas;
import com.promotick.lafabril.model.util.descarga.FormBulkParticipantes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class BulkAdminServiceImpl implements BulkAdminService {

    private CargaProcesoDao cargaProcesoDao;

    @Autowired
    public BulkAdminServiceImpl(CargaProcesoDao cargaProcesoDao) {
        this.cargaProcesoDao = cargaProcesoDao;
    }

    @Override
    @Transactional
    public FileValidatorResult<FormBulkParticipantes, FormBulkParticipantes> procesarCargaParticipantes(String path, CargaProceso cargaProceso) throws Exception {
        BulkParticipantesValidator bulkParticipantesValidator = new BulkParticipantesValidator(cargaProcesoDao, cargaProceso);
        return bulkParticipantesValidator.build(path);
    }

    @Override
    @Transactional
    public FileValidatorResult<FormBulkFacturas, FormBulkFacturas> procesarCargaFacturas(String path, CargaProceso cargaProceso) throws Exception {
        BulkFacturasValidator bulkFacturasValidator = new BulkFacturasValidator(cargaProcesoDao, cargaProceso);
        return bulkFacturasValidator.build(path);
    }
}
