package com.promotick.lafabril.admin.service.impl;

import com.promotick.apiclient.utils.file.validator.FileValidatorResult;
import com.promotick.lafabril.admin.controller.excel.validator.BulkFacturasValidator;
import com.promotick.lafabril.admin.controller.excel.validator.BulkParticipantesValidator;
import com.promotick.lafabril.admin.service.BulkAdminService;
import com.promotick.lafabril.admin.service.CatalogoService;
import com.promotick.lafabril.dao.facturacion.CargaProcesoDao;
import com.promotick.lafabril.dao.web.CatalogoDao;
import com.promotick.lafabril.model.facturacion.CargaProceso;
import com.promotick.lafabril.model.util.descarga.FormBulkFacturas;
import com.promotick.lafabril.model.util.descarga.FormBulkParticipantes;
import com.promotick.lafabril.model.web.Catalogo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;

@Service
public class CatalogoServiceImpl implements CatalogoService {

    private CatalogoDao catalogoDao;

    @Autowired
    public CatalogoServiceImpl(CatalogoDao catalogoDao) {
        this.catalogoDao = catalogoDao;
    }


    @Override
    public List<Catalogo> listarCatalogos() {
        return catalogoDao.listarCatalogos();
    }
}
