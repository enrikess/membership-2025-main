package com.promotick.lafabril.admin.controller.excel.validator;

import com.promotick.apiclient.utils.file.validator.BuildValidator;
import com.promotick.lafabril.admin.controller.excel.form.MyExcelFileValidator;
import com.promotick.lafabril.admin.service.CargaExcelAdminService;
import com.promotick.lafabril.admin.service.EmailAdminService;
import com.promotick.lafabril.admin.service.ParticipanteAdminService;
import com.promotick.lafabril.common.UtilCommon;
import com.promotick.lafabril.model.util.UtilEnum;
import com.promotick.lafabril.model.util.descarga.FormCargaRecomendadoEstado;
import com.promotick.lafabril.model.web.Recomendado;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Row;

import java.util.ArrayList;
import java.util.List;

public class CargaRecomendadosEstadosValidator extends MyExcelFileValidator<FormCargaRecomendadoEstado, Recomendado> {

    private static final List<Integer> estadosPermitidos;


    static {
        estadosPermitidos = new ArrayList<>();
        estadosPermitidos.add(1);
        estadosPermitidos.add(0);
    }

    private CargaExcelAdminService cargaExcelAdminService;
    private ParticipanteAdminService participanteAdminService;
    private EmailAdminService emailAdminService;

    public CargaRecomendadosEstadosValidator(CargaExcelAdminService cargaExcelAdminService, ParticipanteAdminService participanteAdminService, EmailAdminService emailAdminService) {
        this.cargaExcelAdminService = cargaExcelAdminService;
        this.participanteAdminService = participanteAdminService;
        this.emailAdminService = emailAdminService;
    }

    @Override
    public FormCargaRecomendadoEstado iterator(int rowNumber, Row row) {
        return new FormCargaRecomendadoEstado()
                .setNroDocumento(UtilCommon.removeDecimals(this.parseString(row, 0)))
                .setEstadoRecomendado(UtilCommon.removeDecimals(this.parseString(row, 1)))
                ;

    }

    @Override
    public Recomendado validate(FormCargaRecomendadoEstado formCargaRecomendadoEstado, BuildValidator buildValidator) {
        try {

            buildValidator
                    .notEmpty(formCargaRecomendadoEstado.getNroDocumento(), "El nro. de documento es obligatorio")
                    .min(formCargaRecomendadoEstado.getNroDocumento(), 8, "El nro. de documento debe tener al menos 8 caracteres")
                    .and()
                    .notEmpty(formCargaRecomendadoEstado.getEstadoRecomendado(), "Estado es obligatorio")
                    .numeric(formCargaRecomendadoEstado.getEstadoRecomendado(), "Estado tiene que ser numerico entero")
                    .numericRange(formCargaRecomendadoEstado.getEstadoRecomendado(), estadosPermitidos, "Estado incorrecto, los permitidos son: " + StringUtils.join(estadosPermitidos, " | "))
                    .validate();

            if (buildValidator.hasError()) {
                formCargaRecomendadoEstado.setError(StringUtils.join(buildValidator.getErrors(), ", "));
                return null;
            } else {

                Recomendado recomendado = new Recomendado()
                        .setNroDocumentoRecomendado(formCargaRecomendadoEstado.getNroDocumento())
                        .setEstadoRecomendado(Integer.parseInt(formCargaRecomendadoEstado.getEstadoRecomendado()))
                        ;

                Integer result = cargaExcelAdminService.registrarCargaRecomendadoEstado(recomendado);

                UtilEnum.CARGA_PARTICIPANTES_RESULTS resultEnum = UtilEnum.CARGA_PARTICIPANTES_RESULTS.getMensageFromCode(result);
                if (!resultEnum.equals(UtilEnum.CARGA_PARTICIPANTES_RESULTS.OK)) {
                    throw new Exception(resultEnum.getMensaje());
                }
                return recomendado;
            }
        } catch (Exception e) {
            formCargaRecomendadoEstado.setError(e.getMessage());
            return null;
        }
    }
}
