package com.promotick.lafabril.admin.controller.excel.validator;

import com.promotick.apiclient.utils.file.excel.ExcelFileValidator;
import com.promotick.apiclient.utils.file.validator.BuildValidator;
import com.promotick.lafabril.admin.service.ParticipanteAdminService;
import com.promotick.lafabril.common.UtilCommon;
import com.promotick.lafabril.model.util.descarga.FormCargaParticipanteActivacion;
import com.promotick.lafabril.model.web.Participante;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Row;

import java.util.ArrayList;
import java.util.List;

@Slf4j
public class CargaParticipantesActivacionValidator extends ExcelFileValidator<FormCargaParticipanteActivacion, Participante> {

    private static final List<Integer> estadosPermitidos;

    static {
        estadosPermitidos = new ArrayList<>();
        estadosPermitidos.add(1);
        estadosPermitidos.add(0);
    }

    private final ParticipanteAdminService participanteAdminService;

    public CargaParticipantesActivacionValidator(ParticipanteAdminService participanteAdminService) {
        this.participanteAdminService = participanteAdminService;
    }

    @Override
    public FormCargaParticipanteActivacion iterator(int rowNumber, Row row) {
        return new FormCargaParticipanteActivacion()
                .setNroDocumento(UtilCommon.removeDecimals(this.parseString(row, 0)))
                .setActivacion(UtilCommon.removeDecimals(this.parseString(row, 1)))
                ;

    }

    @Override
    public Participante validate(FormCargaParticipanteActivacion formCargaParticipanteActivacion, BuildValidator buildValidator) {
        try {

            buildValidator
                    .notEmpty(formCargaParticipanteActivacion.getNroDocumento(), "El nro. de documento es obligatorio")
                    .min(formCargaParticipanteActivacion.getNroDocumento(), 8, "El nro. de documento debe tener al menos 8 caracteres")
                    .and()
                    .notEmpty(formCargaParticipanteActivacion.getActivacion(), "Activacion es obligatorio")
                    .numeric(formCargaParticipanteActivacion.getActivacion(), "Activacion tiene que ser numerico entero")
                    .numericRange(formCargaParticipanteActivacion.getActivacion(), estadosPermitidos, "Activacion incorrecto, los permitidos son: " + StringUtils.join(estadosPermitidos, " | "))
                    .validate();

            if (buildValidator.hasError()) {
                formCargaParticipanteActivacion.setError(StringUtils.join(buildValidator.getErrors(), ", "));
                return null;
            } else {

                Participante participante = new Participante()
                        .setNroDocumento(formCargaParticipanteActivacion.getNroDocumento())
                        .setAprobar(Integer.parseInt(formCargaParticipanteActivacion.getActivacion()) == 1)
                        ;

                Integer result = participanteAdminService.participanteAprobarByDocumento(participante);
                if (result == null || result <= 0) {
                    throw new Exception("El participante con nro documento :" + formCargaParticipanteActivacion.getNroDocumento() + " no existe o se encuentra deshabilitado");
                }
                return participante;
            }
        } catch (Exception e) {
            log.error("Error en el proceso {}", e.getMessage());
            formCargaParticipanteActivacion.setError(e.getMessage());
            return null;
        }
    }
}
