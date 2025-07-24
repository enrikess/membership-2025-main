package com.promotick.lafabril.admin.controller.excel.validator;

import com.promotick.apiclient.utils.file.excel.ExcelFileValidator;
import com.promotick.apiclient.utils.file.validator.BuildValidator;
import com.promotick.lafabril.admin.service.CargaExcelAdminService;
import com.promotick.lafabril.admin.service.EmailAdminService;
import com.promotick.lafabril.admin.service.ParticipanteAdminService;
import com.promotick.lafabril.common.UtilCommon;
import com.promotick.lafabril.model.util.UtilEnum;
import com.promotick.lafabril.model.util.descarga.FormCargaParticipanteEstado;
import com.promotick.lafabril.model.web.Participante;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Row;

import java.util.ArrayList;
import java.util.List;

public class CargaParticipantesEstadosValidator extends ExcelFileValidator<FormCargaParticipanteEstado, Participante> {

    private static final List<Integer> estadosPermitidos;


    static {
        estadosPermitidos = new ArrayList<>();
        estadosPermitidos.add(1);
        estadosPermitidos.add(0);
    }

    private CargaExcelAdminService cargaExcelAdminService;
    private ParticipanteAdminService participanteAdminService;
    private EmailAdminService emailAdminService;

    public CargaParticipantesEstadosValidator(CargaExcelAdminService cargaExcelAdminService, ParticipanteAdminService participanteAdminService, EmailAdminService emailAdminService) {
        this.cargaExcelAdminService = cargaExcelAdminService;
        this.participanteAdminService = participanteAdminService;
        this.emailAdminService = emailAdminService;
    }

    @Override
    public FormCargaParticipanteEstado iterator(int rowNumber, Row row) {
        return new FormCargaParticipanteEstado()
                .setNroDocumento(UtilCommon.removeDecimals(this.parseString(row, 0)))
                .setEstadoParticipante(UtilCommon.removeDecimals(this.parseString(row, 1)))
                .setEstadoCanje(UtilCommon.removeDecimals(this.parseString(row, 2)))
                ;

    }

    @Override
    public Participante validate(FormCargaParticipanteEstado formCargaParticipanteEstado, BuildValidator buildValidator) {
        try {

            buildValidator
                    .notEmpty(formCargaParticipanteEstado.getNroDocumento(), "El nro. de documento es obligatorio")
                    .min(formCargaParticipanteEstado.getNroDocumento(), 8, "El nro. de documento debe tener al menos 8 caracteres")
                    .and()
                    .notEmpty(formCargaParticipanteEstado.getEstadoParticipante(), "Estado es obligatorio")
                    .numeric(formCargaParticipanteEstado.getEstadoParticipante(), "Estado tiene que ser numerico entero")
                    .numericRange(formCargaParticipanteEstado.getEstadoParticipante(), estadosPermitidos, "Estado incorrecto, los permitidos son: " + StringUtils.join(estadosPermitidos, " | "))
                    .and()
                    .notEmpty(formCargaParticipanteEstado.getEstadoCanje(), "Estado canje es obligatorio")
                    .numeric(formCargaParticipanteEstado.getEstadoCanje(), "Estado canje tiene que ser numerico entero")
                    .numericRange(formCargaParticipanteEstado.getEstadoCanje(), estadosPermitidos, "Estado canje incorrecto, los permitidos son: " + StringUtils.join(estadosPermitidos, " | "))
                    .validate();

            if (buildValidator.hasError()) {
                formCargaParticipanteEstado.setError(StringUtils.join(buildValidator.getErrors(), ", "));
                return null;
            } else {

                Participante participante = new Participante()
                        .setNroDocumento(formCargaParticipanteEstado.getNroDocumento())
                        .setEstadoParticipante(Integer.parseInt(formCargaParticipanteEstado.getEstadoParticipante()))
                        .setEstadoCanjes(Integer.parseInt(formCargaParticipanteEstado.getEstadoCanje()) > 0)
                        ;

                Integer result = cargaExcelAdminService.registrarCargaParticipanteEstado(participante);

                Participante participanteDB = participanteAdminService.obtenerParticipantePorNroDocumento(participante.getNroDocumento());

                if (Integer.parseInt(formCargaParticipanteEstado.getEstadoCanje()) == 0 && participanteDB.getEstadoCanjes()) {
                    emailAdminService.envioEmailEstadoCanje(participanteDB); //test
                }

                UtilEnum.CARGA_PARTICIPANTES_RESULTS resultEnum = UtilEnum.CARGA_PARTICIPANTES_RESULTS.getMensageFromCode(result);
                if (!resultEnum.equals(UtilEnum.CARGA_PARTICIPANTES_RESULTS.OK)) {
                    throw new Exception(resultEnum.getMensaje());
                }
                return participante;
            }
        } catch (Exception e) {
            formCargaParticipanteEstado.setError(e.getMessage());
            return null;
        }
    }
}
