package com.promotick.lafabril.admin.controller.excel.validator;

import com.promotick.apiclient.utils.file.excel.ExcelFileValidator;
import com.promotick.apiclient.utils.file.validator.BuildValidator;
import com.promotick.lafabril.admin.service.DireccionAdminService;
import com.promotick.lafabril.common.UtilCommon;
import com.promotick.lafabril.model.util.Auditoria;
import com.promotick.lafabril.model.util.UtilEnum;
import com.promotick.lafabril.model.util.descarga.FormCargaDirecciones;
import com.promotick.lafabril.model.web.Direccion;
import com.promotick.lafabril.model.web.TipoVivienda;
import com.promotick.lafabril.model.web.Ubigeo;
import com.promotick.lafabril.model.web.Zona;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Row;

import java.util.ArrayList;
import java.util.List;

public class CargaDireccionesValidator extends ExcelFileValidator<FormCargaDirecciones, Direccion> {

    private List<Integer> zonasPermitidas = new ArrayList<>();
    private List<Integer> tipoViviendasPermitidas = new ArrayList<>();
    private DireccionAdminService direccionAdminService;
    private Auditoria auditoria;

    public CargaDireccionesValidator(DireccionAdminService direccionAdminService, List<Zona> zonas, List<TipoVivienda> viviendas, Auditoria auditoria) {
        this.direccionAdminService = direccionAdminService;
        this.auditoria = auditoria;
        for (Zona zona : zonas) {
            this.zonasPermitidas.add(zona.getIdZona());
        }

        for (TipoVivienda tv : viviendas) {
            this.tipoViviendasPermitidas.add(tv.getIdTipoVivienda());
        }
    }

    @Override
    public FormCargaDirecciones iterator(int rowNumber, Row row) {
        return new FormCargaDirecciones()
                .setNroDocumento(this.parseString(row, 0))
                .setDireccion(this.parseString(row, 1))
                .setReferencia(this.parseString(row, 2))
                .setIdZona(UtilCommon.removeDecimals(this.parseString(row, 3)))
                .setIdTipoVivienda(UtilCommon.removeDecimals(this.parseString(row, 4)))
                .setCodigoUbigeo(this.parseString(row, 5));
    }

    @Override
    public Direccion validate(FormCargaDirecciones formCargaDirecciones, BuildValidator buildValidator) {
        try {
            buildValidator
                    .notEmpty(formCargaDirecciones.getNroDocumento(), "Nro de documento es requerido")
                    .and()
                    .notEmpty(formCargaDirecciones.getDireccion(), "Direccion es requerido")
                    .and()
                    .notEmpty(formCargaDirecciones.getReferencia(), "Referencia es requerido")
                    .and()
                    .numeric(formCargaDirecciones.getIdZona(), "Id Zona tiene que ser numerico entero")
                    .positive(formCargaDirecciones.getIdZona(), "Id Zona tiene que ser numerico positivo")
                    .numericRange(formCargaDirecciones.getIdZona(), zonasPermitidas, "ID Zona incorrecto, los permitidos son: " + StringUtils.join(zonasPermitidas, " | "))
                    .and()
                    .numeric(formCargaDirecciones.getIdTipoVivienda(), "Id Tipo Vivienda tiene que ser numerico entero")
                    .positive(formCargaDirecciones.getIdTipoVivienda(), "Id Tipo Vivienda tiene que ser numerico positivo")
                    .numericRange(formCargaDirecciones.getIdTipoVivienda(), tipoViviendasPermitidas, "ID Tipo Vivienda incorrecto, los permitidos son: " + StringUtils.join(tipoViviendasPermitidas, " | "))
                    .and()
                    .notEmpty(formCargaDirecciones.getCodigoUbigeo(), "Codigo Ubigeo es requerido")
                    .validate();

            if (buildValidator.hasError()) {
                formCargaDirecciones.setError(StringUtils.join(buildValidator.getErrors(), ", "));
                return null;
            } else {
                Direccion direccion = new Direccion()
                        .setNroDocumento(formCargaDirecciones.getNroDocumento())
                        .setDireccionCalle(formCargaDirecciones.getDireccion())
                        .setReferencia(formCargaDirecciones.getReferencia())
                        .setUbigeo(new Ubigeo().setCodubigeo(formCargaDirecciones.getCodigoUbigeo()))
                        .setTipoVivienda(new TipoVivienda().setIdTipoVivienda(Integer.parseInt(formCargaDirecciones.getIdTipoVivienda())))
                        .setZona(new Zona().setIdZona(Integer.parseInt(formCargaDirecciones.getIdZona())));
                direccion.setAuditoria(auditoria);

                Integer resultado = direccionAdminService.registroDireccionCarga(direccion);

                UtilEnum.CARGA_DIRECCIONES_RESULTS resultEnum = UtilEnum.CARGA_DIRECCIONES_RESULTS.getMensageFromCode(resultado);
                if (!resultEnum.equals(UtilEnum.CARGA_DIRECCIONES_RESULTS.OK)) {
                    throw new Exception(resultEnum.getMensaje());
                }
                return direccion;
            }
        } catch (Exception e) {
            formCargaDirecciones.setError(e.getMessage());
            return null;
        }
    }
}
