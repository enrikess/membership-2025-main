package com.promotick.lafabril.admin.controller.excel.validator;

import com.promotick.apiclient.utils.file.plain.PlainFileValidator;
import com.promotick.apiclient.utils.file.validator.BuildValidator;
import com.promotick.lafabril.dao.facturacion.CargaProcesoDao;
import com.promotick.lafabril.model.facturacion.CargaProceso;
import com.promotick.lafabril.model.util.descarga.FormBulkFacturas;
import org.springframework.util.StringUtils;

public class BulkFacturasValidator extends PlainFileValidator<FormBulkFacturas, FormBulkFacturas> {

    private CargaProcesoDao cargaProcesoDao;
    private CargaProceso cargaProceso;

    public BulkFacturasValidator(CargaProcesoDao cargaProcesoDao, CargaProceso cargaProceso) {
        this.cargaProcesoDao = cargaProcesoDao;
        this.cargaProceso = cargaProceso;
    }

    @Override
    public FormBulkFacturas iterator(int i, String line) {
        String[] columnas = line.split(",");

        FormBulkFacturas formBulkFacturas = new FormBulkFacturas();
        formBulkFacturas.setFechaInfo(limpiarCampo(columnas[0]));
        formBulkFacturas.setFacturaTipo(limpiarCampo(columnas[1]));
        formBulkFacturas.setCliRuc(limpiarCampo(columnas[2]));
        formBulkFacturas.setFechaEmision(limpiarCampo(columnas[3]));
        formBulkFacturas.setNroFactura(limpiarCampo(columnas[4]));
        formBulkFacturas.setRatioPuntos(limpiarCampo(columnas[5]));
        formBulkFacturas.setMontoFactura(limpiarCampo(columnas[6]));

        return formBulkFacturas;
    }

    @Override
    public FormBulkFacturas validate(FormBulkFacturas formBulkFacturas, BuildValidator buildValidator) {

        try {
            formBulkFacturas.setCargaProceso(cargaProceso);
            Integer result = cargaProcesoDao.bulkFacturasRegistro(formBulkFacturas);
            if (result <= 0) {
                throw new Exception("Error al registrar Factura en la Base de datos");
            }
            return formBulkFacturas;
        } catch (Exception e) {
            formBulkFacturas.setMotivoRechazo(e.getMessage());
            return null;
        }
    }

    private boolean validarColumna(String valor) {
        return !StringUtils.isEmpty(valor) && !valor.trim().equals("''") && !valor.trim().equals("\"\"") && !valor.trim().equals("\" \"");
    }

    private String limpiarValor(String valor) {
        if (valor == null) {
            return "";
        }
        return valor.replace("ï»¿", "");
    }

    private String limpiarCampo(String dato) {
        try {
            return this.validarColumna(this.limpiarValor(dato)) ? this.limpiarValor(dato) : null;
        } catch (Exception e) {
            return null;
        }

    }
}
