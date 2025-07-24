package com.promotick.lafabril.admin.controller.excel.validator;

import com.promotick.apiclient.utils.file.plain.PlainFileValidator;
import com.promotick.apiclient.utils.file.validator.BuildValidator;
import com.promotick.lafabril.dao.facturacion.CargaProcesoDao;
import com.promotick.lafabril.model.facturacion.CargaProceso;
import com.promotick.lafabril.model.util.descarga.FormBulkParticipantes;
import org.springframework.util.StringUtils;

public class BulkParticipantesValidator extends PlainFileValidator<FormBulkParticipantes, FormBulkParticipantes> {

    private CargaProcesoDao cargaProcesoDao;
    private CargaProceso cargaProceso;

    public BulkParticipantesValidator(CargaProcesoDao cargaProcesoDao, CargaProceso cargaProceso) {
        this.cargaProcesoDao = cargaProcesoDao;
        this.cargaProceso = cargaProceso;
    }

    @Override
    public FormBulkParticipantes iterator(int i, String line) {
        String[] columnas = line.split(",");

        FormBulkParticipantes formBulkParticipantes = new FormBulkParticipantes();
        formBulkParticipantes.setFechaInfo(limpiarCampo(columnas[0]));
        formBulkParticipantes.setTipoDocumento(limpiarCampo(columnas[1]));
        formBulkParticipantes.setCliRuc(limpiarCampo(columnas[2]));
        formBulkParticipantes.setNombres(limpiarCampo(columnas[3]));
        formBulkParticipantes.setApPaterno(limpiarCampo(columnas[4]));
        formBulkParticipantes.setApMaterno(limpiarCampo(columnas[5]));
        formBulkParticipantes.setTelefono(limpiarCampo(columnas[6]));
        formBulkParticipantes.setMovil(limpiarCampo(columnas[7]));
        formBulkParticipantes.setEmail(limpiarCampo(columnas[8]));
        formBulkParticipantes.setMetaTrimestral(limpiarCampo(columnas[9]));
        formBulkParticipantes.setMetaAnual(limpiarCampo(columnas[10]));
        formBulkParticipantes.setRazonSocial(limpiarCampo(columnas[11]));
        formBulkParticipantes.setClicodigo(limpiarCampo(columnas[12]));
        formBulkParticipantes.setClienteVencido(limpiarCampo(columnas[13]));

        return formBulkParticipantes;
    }

    @Override
    public FormBulkParticipantes validate(FormBulkParticipantes formBulkParticipantes, BuildValidator buildValidator) {

        try {
            formBulkParticipantes.setCargaProceso(cargaProceso);
            Integer result = cargaProcesoDao.bulkParticipantesRegistro(formBulkParticipantes);
            if (result <= 0) {
                throw new Exception("Error al registrar Participante en la Base de datos");
            }
            return formBulkParticipantes;
        } catch (Exception e) {
            formBulkParticipantes.setMotivoRechazo(e.getMessage());
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
