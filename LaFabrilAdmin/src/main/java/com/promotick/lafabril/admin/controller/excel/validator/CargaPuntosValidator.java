package com.promotick.lafabril.admin.controller.excel.validator;

import com.promotick.apiclient.service.ApiSmsService;
import com.promotick.apiclient.utils.file.excel.ExcelFileValidator;
import com.promotick.apiclient.utils.file.validator.BuildValidator;
import com.promotick.lafabril.admin.service.CargaExcelAdminService;
import com.promotick.lafabril.admin.service.EmailAdminService;
import com.promotick.lafabril.admin.service.MensajeAdminService;
import com.promotick.lafabril.admin.service.ParticipanteAdminService;
import com.promotick.lafabril.common.ConstantesApi;
import com.promotick.lafabril.common.ConstantesCommon;
import com.promotick.lafabril.model.util.bean.massend.MassendRequest;
import com.promotick.lafabril.model.util.bean.massend.MassendResponse;
import com.promotick.lafabril.model.util.descarga.FormCargaPuntos;
import com.promotick.lafabril.model.util.UtilEnum;
import com.promotick.lafabril.model.util.form.CargaPuntos;
import com.promotick.lafabril.model.web.Participante;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Row;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

@Slf4j
public class CargaPuntosValidator extends ExcelFileValidator<FormCargaPuntos, CargaPuntos> {

    private static final List<Integer> tipoOperacionPermitidos;
    private CargaExcelAdminService cargaExcelAdminService;
    private ParticipanteAdminService participanteAdminService;
    private MensajeAdminService mensajeAdminService;
    private Properties properties;
    private Integer idCarga;
    private ApiSmsService apiSmsService;
    private EmailAdminService emailAdminService;

    static {
        tipoOperacionPermitidos = new ArrayList<>();
        tipoOperacionPermitidos.add(1); // Suma de puntos acreditados
        tipoOperacionPermitidos.add(2); // Descarga de puntos acreditados
        tipoOperacionPermitidos.add(3); // Canje Externo
        tipoOperacionPermitidos.add(4); // Acelerador
        tipoOperacionPermitidos.add(5); // Devolucion
        tipoOperacionPermitidos.add(8); // Campania
        tipoOperacionPermitidos.add(11); // Pronosticos
        tipoOperacionPermitidos.add(12); // Polla
        tipoOperacionPermitidos.add(14); // Referidos
    }

    public CargaPuntosValidator(CargaExcelAdminService cargaExcelAdminService, ParticipanteAdminService participanteAdminService, MensajeAdminService mensajeAdminService, Properties properties, Integer idCarga, ApiSmsService apiSmsService, EmailAdminService emailAdminService) {
        this.cargaExcelAdminService = cargaExcelAdminService;
        this.participanteAdminService = participanteAdminService;
        this.mensajeAdminService = mensajeAdminService;
        this.properties = properties;
        this.idCarga = idCarga;
        this.apiSmsService = apiSmsService;
        this.emailAdminService = emailAdminService;
    }


    @Override
    public FormCargaPuntos iterator(int rowNumber, Row row) {
        return new FormCargaPuntos()
                .setNroDocumento(this.parseString(row, 0))
                .setTipoOperacion(this.parseString(row, 1))
                .setMonto(this.parseString(row, 2))
                .setDescripcion(this.parseString(row, 3))
                .setFechaOperacion(this.parseString(row, 4))
                .setNroFactura(this.parseString(row, 5))
                ;
    }

    @Override
    public CargaPuntos validate(FormCargaPuntos formCargaPuntos, BuildValidator buildValidator) {
        try {

            buildValidator
                    .notEmpty(formCargaPuntos.getNroDocumento(), "Nro de documento es requerido")
                    .and()
                    .notEmpty(formCargaPuntos.getTipoOperacion(), "Tipo de operacion es requerido")
                    .numeric(formCargaPuntos.getTipoOperacion(), "Tipo operacion debe ser numerico")
                    .positive(formCargaPuntos.getTipoOperacion(), "Tipo operacion debe ser entero positivo")
                    .numericRange(formCargaPuntos.getTipoOperacion(), tipoOperacionPermitidos,"Tipo operacion invalido - permitidos (" + StringUtils.join(tipoOperacionPermitidos, "|") + ")")
                    .and()
                    .notEmpty(formCargaPuntos.getMonto(), "Monto de validator es requerido")
                    .numeric(formCargaPuntos.getMonto(), "Monto de validator debe ser numerico")
                    .positive(formCargaPuntos.getMonto(), "Monto de validator debe ser numerico positivo")
                    .and()
                    .notEmpty(formCargaPuntos.getDescripcion(), "Descripcion es requerido")
                    .and()
                    .notEmpty(formCargaPuntos.getFechaOperacion(), "Fecha operacion es requerido")
                    .date(formCargaPuntos.getFechaOperacion(), "dd-MM-yyyy","Fecha operacion no tiene el formato correcto")
                    .validate();

            if (buildValidator.hasError()) {
                formCargaPuntos.setError(StringUtils.join(buildValidator.getErrors(), ", "));
                return null;
            } else {

                CargaPuntos cargaPuntos = new CargaPuntos()
                        .setNroDocumento(formCargaPuntos.getNroDocumento())
                        .setTipoOperacion(Integer.parseInt(formCargaPuntos.getTipoOperacion()))
                        .setMonto(Integer.parseInt(formCargaPuntos.getMonto()))
                        .setDescripcion(formCargaPuntos.getDescripcion())
                        .setFechaOperacion(buildValidator.parseDate(formCargaPuntos.getFechaOperacion(), "dd-MM-yyyy"))
                        .setIdCarga(idCarga)
                        .setNroFactura(formCargaPuntos.getNroFactura())
                        .setUsuarioCreacion("Carga Excel");

                Integer result = cargaExcelAdminService.registrarCargaPuntos(cargaPuntos);

                UtilEnum.CARGA_PUNTOS_RESULTS resultEnum = UtilEnum.CARGA_PUNTOS_RESULTS.getMensageFromCode(result);
                if (!resultEnum.equals(UtilEnum.CARGA_PUNTOS_RESULTS.OK)) {
                    throw new Exception(resultEnum.getMensaje());
                }

                if (cargaPuntos.getTipoOperacion() == 1 || cargaPuntos.getTipoOperacion() == 4 || cargaPuntos.getTipoOperacion() == 8) { // Solo carga de puntos
                   this.enviarSMSEmail(cargaPuntos.getNroDocumento(), cargaPuntos.getMonto()); //test
                }
                return cargaPuntos;
            }
        } catch (Exception e) {
            formCargaPuntos.setError(e.getMessage());
            return null;
        }
    }

    private void enviarSMSEmail(String nroDocument, Integer puntosCargados) {
        try {
            Participante participante = participanteAdminService.obtenerParticipantePorNroDocumento(nroDocument);
            if (participante == null) {
                log.error("SMS: No se encontro al participante");
                return;
            }

            emailAdminService.envioEmailPuntosManuales(participante, puntosCargados);

            if (StringUtils.isEmpty(participante.getMovilParticipante())) {
                log.error("SMS: El participante no tiene movil");
                return;
            }

            String url = properties.getProperty(ConstantesApi.SMS_URL);

            MassendRequest massendRequest = new MassendRequest();
            massendRequest.setUser(properties.getProperty(ConstantesApi.SMS_USER));
            massendRequest.setPass(properties.getProperty(ConstantesApi.SMS_PASS));
            massendRequest.setMensajeid(properties.getProperty(ConstantesApi.SMS_ID_PUNTOS));
            massendRequest.setCampana("");
            massendRequest.setTelefono(participante.getMovilParticipante());
            massendRequest.setTipo(properties.getProperty(ConstantesApi.SMS_TIPO_VARIABLE));
            massendRequest.setRuta(properties.getProperty(ConstantesApi.SMS_RUTA_INFORMATIVO));
            massendRequest.setDatos(participante.getAppaternoParticipante() + "," + puntosCargados + ",1800 CANJES");

            RestTemplate restTemplate = new RestTemplate();
            HttpHeaders headers = new HttpHeaders();
            HttpEntity<MassendRequest> entity = new HttpEntity<>(massendRequest, headers);
            System.out.println("Request send sms-----> " + massendRequest.toString());

            ResponseEntity<MassendResponse> result = restTemplate.exchange(url, HttpMethod.POST,entity, MassendResponse.class );
            if (result.getBody().getRefError().getCod_error().equals(ConstantesCommon.CODIGO_SMS_EXITO))
                System.out.println("Respuesta-----> " + result.getBody().getRefEnvio().getMensaje());
            else
                System.out.println("Respuesta-----> " + result.getBody().getRefError().getErrorinfo());

       } catch (Exception e) {
           log.error("Error", e);
       }
    }

}
