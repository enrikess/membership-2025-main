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
import com.promotick.lafabril.model.util.descarga.FormCargaVentas;
import com.promotick.lafabril.model.util.form.CargaVentas;
import com.promotick.lafabril.model.web.Participante;
import com.promotick.lafabril.model.web.ParticipanteMeta;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Row;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Slf4j
public class CargaVentasValidator extends ExcelFileValidator<FormCargaVentas, CargaVentas> {

    private CargaExcelAdminService cargaExcelAdminService;
    private ParticipanteAdminService participanteAdminService;
    private MensajeAdminService mensajeAdminService;
    private Properties properties;
    private Integer idCarga;
    private ApiSmsService apiSmsService;
    private EmailAdminService emailAdminService;
    private Auditoria auditoria;
    private static List<Integer> periodos;
    static {
        periodos = new ArrayList<>();
        periodos.addAll(IntStream.rangeClosed(1, 3).boxed().collect(Collectors.toList()));
    }


    public CargaVentasValidator(CargaExcelAdminService cargaExcelAdminService, ParticipanteAdminService participanteAdminService, MensajeAdminService mensajeAdminService, Properties properties, Integer idCarga, ApiSmsService apiSmsService, EmailAdminService emailAdminService, Auditoria auditoria) {
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
    public FormCargaVentas iterator(int rowNumber, Row row) {
        return new FormCargaVentas()
                .setNroDocumento(this.parseString(row, 0))
                .setValorVenta(this.parseString(row, 1))
                .setFechaOperacion(this.parseString(row, 2))
                .setDescripcion(this.parseString(row, 3));
    }

    @Override
    public CargaVentas validate(FormCargaVentas formCargaVentas, BuildValidator buildValidator) {
        try {

            buildValidator
                    .notEmpty(formCargaVentas.getNroDocumento(), "Nro de documento es requerido")
                     .and()
                    .notEmpty(formCargaVentas.getValorVenta(), "Valor Venta es requerido")
                    .decimal(formCargaVentas.getValorVenta(), "Valor Venta debe ser numerico decimal")
                    .and()
                    .notEmpty(formCargaVentas.getDescripcion(), "Descripcion es requerido")
                    .and()
                    .notEmpty(formCargaVentas.getFechaOperacion(), "Fecha operacion es requerido")
                    .date(formCargaVentas.getFechaOperacion(), "dd-MM-yyyy","Fecha operacion no tiene el formato correcto")
                    .validate();

            if (buildValidator.hasError()) {
                formCargaVentas.setError(StringUtils.join(buildValidator.getErrors(), ", "));
                return null;
            } else {

                CargaVentas cargaVentas = new CargaVentas()
                        .setNroDocumento(formCargaVentas.getNroDocumento())
                        .setValorVenta(Double.parseDouble(formCargaVentas.getValorVenta()))
                        .setFechaOperacion(buildValidator.parseDate(formCargaVentas.getFechaOperacion(), "dd-MM-yyyy"))
                        .setIdCarga(idCarga)
                        .setIdPeriodo(0)
                        .setOperacion("CREAR")
                        .setDescripcion(formCargaVentas.getDescripcion())
                        .setUsuarioCreacion(auditoria.getUsuarioCreacion());

                Integer result = cargaExcelAdminService.registrarCargaVentas(cargaVentas);

                UtilEnum.CARGA_VENTAS_RESULTS resultEnum = UtilEnum.CARGA_VENTAS_RESULTS.getMensageFromCode(result);
                if (!resultEnum.equals(UtilEnum.CARGA_VENTAS_RESULTS.OK)) {
                    throw new Exception(resultEnum.getMensaje());
                }

                this.enviarSMSEmail(cargaVentas.getNroDocumento(), cargaVentas, formCargaVentas);
                return cargaVentas;
            }
        } catch (Exception e) {
            formCargaVentas.setError(e.getMessage());
            return null;
        }
    }

    private void enviarSMSEmail(String nroDocument, CargaVentas cargaVentas, FormCargaVentas formCargaVentas) {
        try {
            Participante participante = participanteAdminService.obtenerParticipantePorNroDocumento(nroDocument);

            ParticipanteMeta participanteMeta = participanteAdminService.participanteMetaObtenerByAvance(participante.getIdParticipante(), cargaVentas.getFechaOperacion());
            if (participanteMeta != null) {
                emailAdminService.envioEmailCargaVentas(participante, cargaVentas, participanteMeta);
            } else {
                log.info("El participante no tiene meta para el periodo {}", formCargaVentas.getFechaOperacion());
            }

       } catch (Exception e) {
           log.error("Error", e);
       }
    }

}
