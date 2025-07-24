package com.promotick.lafabril.admin.controller.excel.validator;

import com.promotick.apiclient.service.ApiSmsService;
import com.promotick.apiclient.utils.file.excel.ExcelFileValidator;
import com.promotick.apiclient.utils.file.validator.BuildValidator;
import com.promotick.lafabril.admin.service.CargaExcelAdminService;
import com.promotick.lafabril.admin.service.EmailAdminService;
import com.promotick.lafabril.admin.service.MensajeAdminService;
import com.promotick.lafabril.admin.service.ParticipanteAdminService;
import com.promotick.lafabril.model.util.Auditoria;
import com.promotick.lafabril.model.util.UtilEnum;
import com.promotick.lafabril.model.util.descarga.FormCargaAcciones;
import com.promotick.lafabril.model.util.descarga.FormCargaMetas;
import com.promotick.lafabril.model.util.form.CargaAcciones;
import com.promotick.lafabril.model.util.form.CargaMetas;
import com.promotick.lafabril.model.web.Participante;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Row;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Slf4j
public class CargaAccionesValidator extends ExcelFileValidator<FormCargaAcciones, CargaAcciones> {

    private CargaExcelAdminService cargaExcelAdminService;
    private ParticipanteAdminService participanteAdminService;
    private MensajeAdminService mensajeAdminService;
    private Properties properties;
    private Integer idCarga;
    private ApiSmsService apiSmsService;
    private EmailAdminService emailAdminService;
    private Auditoria auditoria;
    private static List<Integer> meses;
    static {
        meses = new ArrayList<>();
        meses.addAll(IntStream.rangeClosed(1, 3).boxed().collect(Collectors.toList()));
    }

    public CargaAccionesValidator(CargaExcelAdminService cargaExcelAdminService, ParticipanteAdminService participanteAdminService, MensajeAdminService mensajeAdminService, Properties properties, Integer idCarga, ApiSmsService apiSmsService, EmailAdminService emailAdminService, Auditoria auditoria) {
        this.cargaExcelAdminService = cargaExcelAdminService;
        this.participanteAdminService = participanteAdminService;
        this.mensajeAdminService = mensajeAdminService;
        this.properties = properties;
        this.idCarga = idCarga;
        this.apiSmsService = apiSmsService;
        this.emailAdminService = emailAdminService;
        this.auditoria = auditoria;
    }


    @Override
    public FormCargaAcciones iterator(int rowNumber, Row row) {
        return new FormCargaAcciones()
                .setFecha(this.parseString(row, 0))
                .setAccion(this.parseString(row, 1))
                .setDescripcion(this.parseString(row, 2))
                .setCantidad(this.parseString(row, 3));
    }

    @Override
    public CargaAcciones validate(FormCargaAcciones formCargaAcciones, BuildValidator buildValidator) {
        try {

            buildValidator
                    .notEmpty(formCargaAcciones.getFecha(), "Fecha es requerido")
                    .date(formCargaAcciones.getFecha(), "dd-MM-yyyy","Fecha no tiene el formato correcto")
                    .and()
                    .notEmpty(formCargaAcciones.getDescripcion(), "Descripcion es requerido")
                    .and()
                    .notEmpty(formCargaAcciones.getAccion(), "Accion es requerido")
                    .and()
                    .notEmpty(formCargaAcciones.getCantidad(), "Cantidad es requerido")
                    .numeric(formCargaAcciones.getCantidad(), "Cantidad debe ser numerico")
                    .validate();

            if (buildValidator.hasError()) {
                formCargaAcciones.setError(StringUtils.join(buildValidator.getErrors(), ", "));
                return null;
            } else {

                SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
                CargaAcciones cargaAcciones = new CargaAcciones()
                        .setAccion(formCargaAcciones.getAccion())
                        .setDescripcion(formCargaAcciones.getDescripcion())
                        .setFecha(formatter.parse(formCargaAcciones.getFecha()))
                        .setCantidad(Integer.parseInt(formCargaAcciones.getCantidad()))
                        .setOperacion("CREAR")
                        .setIdCarga(idCarga)
                        .setUsuarioCreacion(auditoria.getUsuarioCreacion());

                Integer result = cargaExcelAdminService.registrarAccionesMetas(cargaAcciones);

                UtilEnum.CARGA_METAS_RESULTS resultEnum = UtilEnum.CARGA_METAS_RESULTS.getMensageFromCode(result);
                if (!resultEnum.equals(UtilEnum.CARGA_METAS_RESULTS.OK)) {
                    throw new Exception(resultEnum.getMensaje());
                }

//                this.enviarSMSEmail(cargaMetas.getNroDocumento(), cargaMetas);
                return cargaAcciones;
            }
        } catch (Exception e) {
            formCargaAcciones.setError(e.getMessage());
            return null;
        }
    }

    private void enviarSMSEmail(String nroDocument, CargaMetas cargaMetas) {
        try {
            Participante participante = participanteAdminService.obtenerParticipantePorNroDocumento(nroDocument);
            emailAdminService.envioEmailCargaMetas(participante, cargaMetas);
       } catch (Exception e) {
           log.error("Error", e);
       }
    }

}
