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
import com.promotick.lafabril.model.util.descarga.FormCargaMetas;
import com.promotick.lafabril.model.util.form.CargaMetas;
import com.promotick.lafabril.model.web.Participante;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Row;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Slf4j
public class CargaMetasValidator extends ExcelFileValidator<FormCargaMetas, CargaMetas> {

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

    public CargaMetasValidator(CargaExcelAdminService cargaExcelAdminService, ParticipanteAdminService participanteAdminService, MensajeAdminService mensajeAdminService, Properties properties, Integer idCarga, ApiSmsService apiSmsService, EmailAdminService emailAdminService, Auditoria auditoria) {
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
    public FormCargaMetas iterator(int rowNumber, Row row) {
        return new FormCargaMetas()
                .setNroDocumento(this.parseString(row, 0))
                .setAnio(this.parseString(row, 1))
                .setMes(this.parseString(row, 2))
                .setMeta(this.parseString(row, 3))
                .setDescripcion(this.parseString(row, 4))
                .setPorcentajeRebate(this.parseString(row, 5))
                .setValorPago(this.parseString(row, 6))
                .setAccionesDeSellout(this.parseString(row, 7))
                .setPremio(this.parseString(row, 8));
    }

    @Override
    public CargaMetas validate(FormCargaMetas formCargaMetas, BuildValidator buildValidator) {
        try {

            buildValidator
                    .notEmpty(formCargaMetas.getNroDocumento(), "Nro de documento es requerido")
                    .and()
                    .notEmpty(formCargaMetas.getAnio(), "Anio es requerido")
                    .numeric(formCargaMetas.getAnio(), "Anio debe ser numerico")
                    .positive(formCargaMetas.getAnio(), "Anio debe ser numerico positivo")
                    .and()
                    .notEmpty(formCargaMetas.getMes(), "Trimestre es requerido")
                    .numeric(formCargaMetas.getMes(), "Trimestre debe ser numerico")
                    .positive(formCargaMetas.getMes(), "Trimestre debe ser numerico positivo")
                    .numericRange(formCargaMetas.getMes(), meses, "Trimestre invalido")
                    .and()
                    .notEmpty(formCargaMetas.getMeta(), "Meta es requerido")
                    .decimal(formCargaMetas.getMeta(), "Meta debe ser numerico decimal")
                    .and()
                    .notEmpty(formCargaMetas.getPremio(), "Premio es requerido")
                    .numeric(formCargaMetas.getPremio(), "Premio debe ser numerico")
                    .and()
                    .notEmpty(formCargaMetas.getPorcentajeRebate(), "Porcentaje de rebate es requerido")
                    .decimal(formCargaMetas.getPorcentajeRebate(), "Porcentaje de rebate debe ser numerico decimal")
                    .and()
                    .notEmpty(formCargaMetas.getValorPago(), "Valor de pago es requerido")
                    .decimal(formCargaMetas.getValorPago(), "Valor de pago debe ser numerico decimal")
                    .and()
                    .notEmpty(formCargaMetas.getDescripcion(), "Descripcion es requerido")
                    .and()
                    .notEmpty(formCargaMetas.getAccionesDeSellout(), "Acciones de sellout es requerido")
                    .validate();

            if (buildValidator.hasError()) {
                formCargaMetas.setError(StringUtils.join(buildValidator.getErrors(), ", "));
                return null;
            } else {

                CargaMetas cargaMetas = new CargaMetas()
                        .setAnio(Integer.parseInt(formCargaMetas.getAnio()))
                        .setMes(Integer.parseInt(formCargaMetas.getMes()))
                        .setNroDocumento(formCargaMetas.getNroDocumento())
                        .setMeta(Double.parseDouble(formCargaMetas.getMeta()))
                        .setDescripcion(formCargaMetas.getDescripcion())
                        .setValorPago(Double.parseDouble(formCargaMetas.getValorPago()))
                        .setIdProductos(formCargaMetas.getAccionesDeSellout())
                        .setPorcentajeRebate(Double.parseDouble(formCargaMetas.getPorcentajeRebate()))
                        .setPuntosPremio(Integer.parseInt(formCargaMetas.getPremio()))
                        .setOperacion("CREAR")
                        .setIdCarga(idCarga)
                        .setUsuarioCreacion(auditoria.getUsuarioCreacion());

                Integer result = cargaExcelAdminService.registrarCargaMetas(cargaMetas);

                UtilEnum.CARGA_METAS_RESULTS resultEnum = UtilEnum.CARGA_METAS_RESULTS.getMensageFromCode(result);
                if (!resultEnum.equals(UtilEnum.CARGA_METAS_RESULTS.OK)) {
                    throw new Exception(resultEnum.getMensaje());
                }

//                this.enviarSMSEmail(cargaMetas.getNroDocumento(), cargaMetas);
                return cargaMetas;
            }
        } catch (Exception e) {
            formCargaMetas.setError(e.getMessage());
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
