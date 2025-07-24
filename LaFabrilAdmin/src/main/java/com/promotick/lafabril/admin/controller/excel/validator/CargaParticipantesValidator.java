package com.promotick.lafabril.admin.controller.excel.validator;

import com.promotick.apiclient.utils.file.excel.ExcelFileValidator;
import com.promotick.apiclient.utils.file.validator.BuildValidator;
import com.promotick.lafabril.admin.service.CargaExcelAdminService;
import com.promotick.lafabril.admin.service.ConfiguracionAdminService;
import com.promotick.lafabril.admin.service.EmailAdminService;
import com.promotick.lafabril.admin.service.ParticipanteAdminService;
import com.promotick.lafabril.admin.util.values.TipoEnvioEmailEnum;
import com.promotick.lafabril.common.ConstantesApi;
import com.promotick.lafabril.common.ConstantesCommon;
import com.promotick.lafabril.common.UtilCommon;
import com.promotick.lafabril.model.configuracion.ConfiguracionWeb;
import com.promotick.lafabril.model.util.Auditoria;
import com.promotick.lafabril.model.util.UtilEnum;
import com.promotick.lafabril.model.util.bean.massend.MassendRequest;
import com.promotick.lafabril.model.util.bean.massend.MassendResponse;
import com.promotick.lafabril.model.util.descarga.FormCargaParticipante;
import com.promotick.lafabril.model.web.*;
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

public class CargaParticipantesValidator extends ExcelFileValidator<FormCargaParticipante, Participante> {

    private static final List<Integer> estadosPermitidos;
    private static final List<String> generosPermitidos;
    private static final List<String> estadoCivilPermitidos;
    private static final List<Integer> tipoParticipantesPermitidos;
    private static final List<Integer> categoriasPermitidas;
    private static final List<Integer> regionPermitidas;
    private List<Integer> concesionariosPermitidos = new ArrayList<>();
    private List<Integer> subtipoParticipantesPermitidos = new ArrayList<>();
    private Properties properties;

    static {
        estadosPermitidos = new ArrayList<>();
        generosPermitidos = new ArrayList<>();
        estadoCivilPermitidos = new ArrayList<>();
        tipoParticipantesPermitidos = new ArrayList<>();
        categoriasPermitidas = new ArrayList<>();
        regionPermitidas = new ArrayList<>();
        estadosPermitidos.add(1);
        estadosPermitidos.add(0);
        generosPermitidos.add("M");
        generosPermitidos.add("F");
        estadoCivilPermitidos.add("SOLTERO");
        estadoCivilPermitidos.add("CASADO");
        estadoCivilPermitidos.add("DIVORCIADO");
        tipoParticipantesPermitidos.add(1); //Tradicional
        tipoParticipantesPermitidos.add(2); //Mayorista
        categoriasPermitidas.add(1); // ReyLacteos
        categoriasPermitidas.add(2); // ReyLacteos Mayor
        categoriasPermitidas.add(3); // ReyLacteos VIP
        regionPermitidas.add(1); // Costa
        regionPermitidas.add(2); // Sierra
        regionPermitidas.add(3); // Oriente
        regionPermitidas.add(4); // Insular
    }

    private CargaExcelAdminService cargaExcelAdminService;
    private EmailAdminService emailAdminService;
    private ConfiguracionAdminService configuracionAdminService;
    private ParticipanteAdminService participanteAdminService;

    public CargaParticipantesValidator(CargaExcelAdminService cargaExcelAdminService, EmailAdminService emailAdminService, ConfiguracionAdminService configuracionAdminService, ParticipanteAdminService participanteAdminService, Properties properties) {
        this.cargaExcelAdminService = cargaExcelAdminService;
        this.emailAdminService = emailAdminService;
        this.configuracionAdminService = configuracionAdminService;
        this.participanteAdminService = participanteAdminService;
        this.properties = properties;
    }

    @Override
    public FormCargaParticipante iterator(int rowNumber, Row row) {
        return new FormCargaParticipante()
                .setNombresParticipante(this.parseString(row, 0))
                .setAppaternoParticipante(this.parseString(row,  1))
                .setApmaternoParticipante(this.parseString(row, 2))
                .setEmailParticipante(this.parseString(row, 3))
                .setClave(this.parseString(row, 4))
                .setNroDocumento(UtilCommon.removeDecimals(this.parseString(row, 5)))
                .setTelefonoParticipante(this.parseString(row, 6))
                .setMovilParticipante(this.parseString(row, 7))
                .setEstadoParticipante(UtilCommon.removeDecimals(this.parseString(row, 8)))
                .setIdCatalogo(UtilCommon.removeDecimals(this.parseString(row, 9)))
                .setIdBroker(UtilCommon.removeDecimals(this.parseString(row, 10)))
                .setCiudad(this.parseString(row, 11))
                .setRegional(UtilCommon.removeDecimals(this.parseString(row, 12)))
                .setEstadoCanje(UtilCommon.removeDecimals(this.parseString(row, 13)))
                .setGenero(this.parseString(row, 14))
                .setFechaNacimiento(this.parseString(row, 15))
                .setEstadoCivil(this.parseString(row, 16))
                .setNroHijos(UtilCommon.removeDecimals(this.parseString(row, 17)))
                .setProvincia(this.parseString(row, 18))
                .setRegion(UtilCommon.removeDecimals(this.parseString(row, 19)))
//                .setCiudad(this.parseString(row, 11))
//                .setRepresentante(this.parseString(row, 14))
//                .setTipoParticipante(UtilCommon.removeDecimals(this.parseString(row, 15)))
//                .setCategoria(UtilCommon.removeDecimals(this.parseString(row, 18)))
//                .setRucComercial(this.parseString(row, 15))
//                .setPromocion(this.parseString(row, 17))
//                .setTipoDocumento(UtilCommon.removeDecimals(this.parseString(row, 1))) //new
                ;

    }

    @Override
    public Participante validate(FormCargaParticipante formCargaParticipante, BuildValidator buildValidator) {
        try {

            buildValidator
                    .notEmpty(formCargaParticipante.getNombresParticipante(), "Nombre del participante es obligatorio")
                    .and()
                    .notEmpty(formCargaParticipante.getAppaternoParticipante(), "Apellido paterno es obligatorio")
                    .and()
                    .notEmpty(formCargaParticipante.getEmailParticipante(), "Email es obligatorio")
                    .email(formCargaParticipante.getEmailParticipante(), "Email tiene un formato incorrecto")
                    .and()
                    .notEmpty(formCargaParticipante.getNroDocumento(), "El nro. de documento es obligatorio")
                    .min(formCargaParticipante.getNroDocumento(), 8, "El nro. de documento debe tener al menos 8 caracteres")
                    .and()
                    .notEmpty(formCargaParticipante.getTelefonoParticipante(), "Telefono es obligatorio")
                    .and()
                    .notEmpty(formCargaParticipante.getEstadoParticipante(), "Estado es obligatorio")
                    .numeric(formCargaParticipante.getEstadoParticipante(), "Estado tiene que ser numerico entero")
                    .numericRange(formCargaParticipante.getEstadoParticipante(), estadosPermitidos, "Estado incorrecto, los permitidos son: " + StringUtils.join(estadosPermitidos, " | "))
                    .and()
                    .notEmpty(formCargaParticipante.getRegion(), "Region es obligatorio")
                    .numeric(formCargaParticipante.getRegion(), "Region tiene que ser numerico entero")
                    .numericRange(formCargaParticipante.getRegion(), regionPermitidas, "Region incorrecto, los permitidos son: " + StringUtils.join(regionPermitidas, " | "))
                    .and()
                    .notEmpty(formCargaParticipante.getIdCatalogo(), "Id de catalogo no especificado")
                    .numeric(formCargaParticipante.getIdCatalogo(), "Id de catalogo tiene que ser numerico entero")
                    .positive(formCargaParticipante.getIdCatalogo(), "Id de catalogo tiene que ser numerico positivo")
                    .and()
                    .notEmpty(formCargaParticipante.getIdBroker(), "Id de broker no especificado")
                    .numeric(formCargaParticipante.getIdBroker(), "Id de broker tiene que ser numerico entero")
                    .positive(formCargaParticipante.getIdBroker(), "Id de broker tiene que ser numerico positivo")
                    .and()
//                    .notEmpty(formCargaParticipante.getTipoParticipante(), "Tipo participante es obligatorio")
//                    .numeric(formCargaParticipante.getTipoParticipante(), "Tipo participante tiene que ser numerico entero")
//                    .characterRange(formCargaParticipante.getTipoParticipante(), tipoParticipantesPermitidos, "Tipo participante incorrecto, los permitidos son: " + StringUtils.join(tipoParticipantesPermitidos, " | "))
//                    .and()
                    .notEmpty(formCargaParticipante.getEstadoCanje(), "Estado canje es obligatorio")
                    .numeric(formCargaParticipante.getEstadoCanje(), "Estado canje tiene que ser numerico entero")
                    .numericRange(formCargaParticipante.getEstadoCanje(), estadosPermitidos, "Estado canje incorrecto, los permitidos son: " + StringUtils.join(estadosPermitidos, " | "))
                    .and()
//                    .notEmpty(formCargaParticipante.getCategoria(), "Categoria es obligatorio")
//                    .numeric(formCargaParticipante.getCategoria(), "Categoria tiene que ser numerico entero")
//                    .numericRange(formCargaParticipante.getCategoria(), categoriasPermitidas, "Categoria incorrecta, las permitidas son: " + StringUtils.join(categoriasPermitidas, " | "))
//                    .and()
                    .notEmpty(formCargaParticipante.getGenero(), "Genero es obligatorio")
                    .characterRange(formCargaParticipante.getGenero(), generosPermitidos, "Genero incorrecto, los permitidos son: " + StringUtils.join(generosPermitidos, " | "))
                    .and()
                    .date(formCargaParticipante.getFechaNacimiento(), "dd-MM-yyyy","Fecha nacimiento no tiene el formato correcto")
                    .and()
                    .notEmpty(formCargaParticipante.getEstadoCivil(), "Estado civil es obligatorio")
                    .characterRange(formCargaParticipante.getEstadoCivil(), estadoCivilPermitidos, "Estado civil incorrecto, los permitidos son: " + StringUtils.join(estadoCivilPermitidos, " | "))
//                    .and()
//                    .notEmpty(formCargaParticipante.getTipoDocumento(), "Tipo documento no especificado")
//                    .numeric(formCargaParticipante.getTipoDocumento(), "Tipo documento tiene que ser numerico entero")
//                    .positive(formCargaParticipante.getTipoDocumento(), "Tipo documento tiene que ser numerico positivo")
//                    .and()
//                    .notEmpty(formCargaParticipante.getTipoParticipante(), "Tipo participante no especificado")
//                    .numeric(formCargaParticipante.getTipoParticipante(), "Tipo participante tiene que ser numerico entero")
//                    .positive(formCargaParticipante.getTipoParticipante(), "Tipo participante tiene que ser numerico positivo")
//                    .and()
//                    .notEmpty(formCargaParticipante.getTipoRazonSocial(), "Tipo razon social  no especificado")
//                    .numeric(formCargaParticipante.getTipoRazonSocial(), "Tipo razon social tiene que ser numerico entero")
//                    .positive(formCargaParticipante.getTipoRazonSocial(), "Tipo razon social tiene que ser numerico positivo")
                    .validate();

            if (buildValidator.hasError()) {
                formCargaParticipante.setError(StringUtils.join(buildValidator.getErrors(), ", "));
                return null;
            } else {

                Participante participante = new Participante()
                        .setCatalogo(new Catalogo().setIdCatalogo(Integer.parseInt(formCargaParticipante.getIdCatalogo())))
                        .setNroDocumento(formCargaParticipante.getNroDocumento())
                        .setUsuarioParticipante(formCargaParticipante.getNroDocumento())
                        .setNombresParticipante(formCargaParticipante.getNombresParticipante())
                        .setAppaternoParticipante(formCargaParticipante.getAppaternoParticipante())
                        .setApmaternoParticipante(formCargaParticipante.getApmaternoParticipante())
                        .setEmailParticipante(formCargaParticipante.getEmailParticipante())
                        .setTelefonoParticipante(formCargaParticipante.getTelefonoParticipante())
                        .setMovilParticipante(formCargaParticipante.getMovilParticipante())
                        .setEstadoParticipante(Integer.parseInt(formCargaParticipante.getEstadoParticipante()))
                        .setCedula(formCargaParticipante.getNroDocumento())
                        .setEstadoCanjes(Integer.parseInt(formCargaParticipante.getEstadoCanje()) > 0)
                        .setCiudad(formCargaParticipante.getCiudad())
                        .setRegion(new Region().setIdRegion(Integer.parseInt(formCargaParticipante.getRegion())))
                        .setBroker(new Broker().setIdBroker(Integer.parseInt(formCargaParticipante.getIdBroker())))
                        .setClaveParticipante(formCargaParticipante.getClave())
                        .setGenero(formCargaParticipante.getGenero())
                        .setFechaNacimiento(buildValidator.parseDate(formCargaParticipante.getFechaNacimiento(), "dd-MM-yyyy"))
                        .setEstadoCivil(formCargaParticipante.getEstadoCivil())
                        .setNroHijos(Integer.parseInt(formCargaParticipante.getNroHijos()))
                        .setProvincia(formCargaParticipante.getProvincia())
                        .setRegional(new Regional().setIdRegional(Integer.parseInt(formCargaParticipante.getRegional())))
//                        .setCodCliente(formCargaParticipante.getCodigoCliente())
//                        .setCliente(formCargaParticipante.getCliente())
//                        .setRepresentante(formCargaParticipante.getRepresentante())
//                        .setIdCategoriaParticipante(Integer.parseInt(formCargaParticipante.getCategoria()))
//                        .setTipoDocumento(new TipoDocumento().setIdTipoDocumento(Integer.parseInt(formCargaParticipante.getTipoDocumento())))
//                        .setTipoRazonSocial(new RazonSocial().setIdRazonsocial(Integer.parseInt(formCargaParticipante.getTipoRazonSocial())))
//                        .setRucComercial(formCargaParticipante.getRucComercial())
//                        .setPromocion(formCargaParticipante.getPromocion())
                        ;

                participante.setAuditoria(new Auditoria().setUsuarioCreacion("Carga Excel"));

                Integer result = cargaExcelAdminService.registrarCargaParticipante(participante);

                UtilEnum.CARGA_PARTICIPANTES_RESULTS resultEnum = UtilEnum.CARGA_PARTICIPANTES_RESULTS.getMensageFromCode(result);
                if (!resultEnum.equals(UtilEnum.CARGA_PARTICIPANTES_RESULTS.OK)) {
                    throw new Exception(resultEnum.getMensaje());
                }

                List<Participante> emailsBienvenida = participanteAdminService.listarParticipanteEmail(TipoEnvioEmailEnum.EMAIL_BIENVENIDA.getCodigo()); //test
                for (Participante p : emailsBienvenida) {
                    if (p.getNroDocumento().equals(participante.getNroDocumento())){
                        this.enviarSMSEmail(participante); //test
                    }
                }

                return participante;
            }
        } catch (Exception e) {
            formCargaParticipante.setError(e.getMessage());
            return null;
        }
    }


    private void enviarSMSEmail(Participante participante) {
        try {
            if (participante == null) {
                return;
            }

            ConfiguracionWeb configuracionWeb = configuracionAdminService.obtenerConfiguracionWeb();
            emailAdminService.envioEmailBienvenidaIndividual(participante, configuracionWeb, true);

            if (StringUtils.isEmpty(participante.getMovilParticipante())) {
                System.out.println("SMS: El participante no tiene movil");
                return;
            }

            String url = properties.getProperty(ConstantesApi.SMS_URL);

            MassendRequest massendRequest = new MassendRequest();
            massendRequest.setUser(properties.getProperty(ConstantesApi.SMS_USER));
            massendRequest.setPass(properties.getProperty(ConstantesApi.SMS_PASS));
            massendRequest.setMensajeid(properties.getProperty(ConstantesApi.SMS_ID_BIENVENIDA));
            massendRequest.setCampana("");
            massendRequest.setTelefono(participante.getMovilParticipante());
            massendRequest.setTipo(properties.getProperty(ConstantesApi.SMS_TIPO_VARIABLE));
            massendRequest.setRuta(properties.getProperty(ConstantesApi.SMS_RUTA_MARKETING));
            massendRequest.setDatos(participante.getAppaternoParticipante());

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
//            log.error("Error", e);
        }
    }
}
