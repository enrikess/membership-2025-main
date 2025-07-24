package com.promotick.lafabril.web.controller;

import com.itextpdf.html2pdf.ConverterProperties;
import com.itextpdf.html2pdf.HtmlConverter;
import com.itextpdf.html2pdf.resolver.font.DefaultFontProvider;
import com.itextpdf.io.source.ByteArrayOutputStream;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.promotick.apiclient.model.AbstractResponse;
import com.promotick.apiclient.model.request.EmailRequest;
import com.promotick.apiclient.model.request.S3UploadRequest;
import com.promotick.apiclient.model.response.FileMetadata;
import com.promotick.apiclient.model.response.SengridEmail;
import com.promotick.apiclient.properties.ApiProperties;
import com.promotick.apiclient.service.ApiEmailService;
import com.promotick.apiclient.service.ApiS3Service;
import com.promotick.lafabril.common.web.ConstantesWebMessage;
import com.promotick.lafabril.model.configuracion.ConfiguracionWeb;
import com.promotick.lafabril.model.util.*;
import com.promotick.lafabril.model.web.Participante;
import com.promotick.lafabril.model.web.TransaccionToken;
import com.promotick.lafabril.web.service.ConfiguracionWebService;
import com.promotick.lafabril.web.service.ParticipanteWebService;
import com.promotick.lafabril.web.service.TransaccionTokenWebService;
import com.promotick.lafabril.web.util.BaseController;
import com.promotick.lafabril.web.util.ConstantesWebView;
import com.promotick.configuration.properties.PromotickProperties;
import com.promotick.configuration.services.PromotickResourceService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.FileOutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Date;
import java.util.Objects;
import java.util.UUID;

@Slf4j
@Controller
@RequestMapping("recuperar")
public class RecuperarCuentaController extends BaseController {

    private BCryptPasswordEncoder encoder;
    private ParticipanteWebService participanteWebService;
    private TransaccionTokenWebService transaccionTokenWebService;
    private ApiEmailService apiEmailService;
    private ConfiguracionWebService configuracionWebService;
    private PromotickProperties promotickProperties;
    private PromotickResourceService promotickResourceService;
//    private ApiS3Service apiS3Service;
//    private ApiProperties apiProperties;

    @Autowired
    public RecuperarCuentaController(BCryptPasswordEncoder encoder, ParticipanteWebService participanteWebService, TransaccionTokenWebService transaccionTokenWebService, ApiEmailService apiEmailService, ConfiguracionWebService configuracionWebService, PromotickProperties promotickProperties, PromotickResourceService promotickResourceService) {
        this.encoder = encoder;
        this.participanteWebService = participanteWebService;
        this.transaccionTokenWebService = transaccionTokenWebService;
        this.apiEmailService = apiEmailService;
        this.configuracionWebService = configuracionWebService;
        this.promotickProperties = promotickProperties;
        this.promotickResourceService = promotickResourceService;
//        this.apiS3Service = apiS3Service;
//        this.apiProperties = apiProperties;
    }

    @GetMapping
    public String recuperar(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (!(auth instanceof AnonymousAuthenticationToken)) {
            return "redirect:/";
        }
        model.addAttribute("configuracionWeb", configuracionWebService.obtenerConfiguracionWeb());
        return ConstantesWebView.VIEW_RECUPERA;
    }

    @GetMapping(value = "formularioRestaurarClave/{token}")
    public String formularioRestaurarClave(@PathVariable("token") String token, Model model) {
        try {
            token = this.decode(token);
            model.addAttribute("token", token);

            if (transaccionTokenWebService.existeTransaccionToken(token)) {
                return ConstantesWebView.VIEW_CAMBIA_CLAVE;
            }

            throw new Exception();
        } catch (Exception e) {
            return ConstantesWebView.VIEW_CAMBIA_CLAVE_ERROR;
        }
    }

    @ResponseBody
    @PostMapping(value = "solicitudRestaurar")
    public PromotickResult solicitudRestaurar(@RequestBody Participante participanteForm, Auditoria auditoria, PromotickResult promotickResult) {

        try {
            String email = participanteForm.getEmailParticipante();
            Participante participante = participanteWebService.obtenerParticipanteByEmail(email);
            if (participante == null) {
                throw new Exception("Participante no se encuentra registrado");
            }

            /*
            String html;

            html =  "<html lang='en'><head><meta http-equiv='Content-Type' content='text/html; charset=UTF-8'></head><body>" +
                    "<table align='center' border='0' cellpadding='0' cellspacing='0' width='842' style=';box-shadow: 0 0 9px 2px #C7C7C7; margin: 0 auto;font-family: Arial,sans-serif;'><tbody>" +
                    String.format("<tr><td style=''><img src='%s' alt=''>", promotickResourceService.web("img/bg/mail-capacitacion/diploma.png")) +
                    String.format("<p style='font-size: 30px;color: #001d85;margin-left: 55px;margin-top: 0;'>%s</p>", participante.getNombresParticipante() + " " + participante.getAppaternoParticipante() + " " + participante.getApmaternoParticipante()) +
                    "</td></tr>" +
                    String.format("\t<tr><td style='background: #001d85'><p style='margin-left: 55px;color: #fff'>Por haber finalizado con éxito la capacitación en: %s</p></td></tr>", "Nombre Capa") +
                    String.format("<tr><td style='background: #001d85 url(%s)'>", promotickResourceService.web("img/bg/mail-capacitacion/foot2.png")) +
                    String.format("<p style='color: #fff;margin: 58px 0 35px 55px'>%s</p>", "21 de Marzo del 2023") +
                    "</td></tr>" +
                    "</tbody></table></body></html>";

            String folderName = this.htmlToPdf(html);

            if (folderName.equals("-1")){
                throw new Exception("No se pudo generar el pdf");
            }

            File file = new File(folderName);
            */

            String token = this.codificar(participante.getIdParticipante(), this.fechaActual());
            String paramToken = this.encode(token);

            Integer transaccion = transaccionTokenWebService.guardarTransaccionToken(new TransaccionToken(auditoria)
                    .setIdEntidad(participante.getIdParticipante())
                    .setToken(token)
                    .setTipoTransaccionToken(1));

            if (transaccion == null || transaccion <= 0) {
                throw new Exception("No se pudo generar el Token de recuperacion");
            }

            /* envio mail */
            String path = promotickProperties.getWeb().getMvc().getContext().getWeb() + "/recuperar/formularioRestaurarClave/" + paramToken;
            String pathAbsoluteHtml = promotickResourceService.mail("recupera-clave-mail.html");
            String imgBanner = promotickResourceService.web("img/bg/bg-header-recuperar.png");
            String imgLogo = promotickResourceService.web("img/logo.png");

            Object[] data = {imgBanner, imgLogo, path};

            ConfiguracionWeb configuracionWeb = configuracionWebService.obtenerConfiguracionWeb();

            try {
                EmailRequest emailRequest = new EmailRequest()
                        .setTo(email)
                        .setFrom(configuracionWeb.getEmailInfo())
                        .setFromName(configuracionWeb.getAliasCorreo())
                        .setSubject("Restaurar Clave")
                        .setPathTemplate(pathAbsoluteHtml)
                        .setParameters(data);

                emailRequest.generateHtml();
                AbstractResponse<SengridEmail> result = apiEmailService.sendEmail(emailRequest);
                log.info("Response EMAIL TO: " + emailRequest.getTo());

                if (!result.isStatus()) {
                    throw new Exception(result.getMessage());
                }
            } catch (Exception e) {
                throw new Exception(Util.getMessage(messageSource, ConstantesWebMessage.MSG_RECUPERAR_ERROR_EMAIL));
            }

            promotickResult
                    .setMessage(Util.getMessage(messageSource, ConstantesWebMessage.MSG_SOLICITUD_RECUPERAR_CLAVE_EXITO, null))
                    .setStatus(Boolean.TRUE);

        } catch (Exception e) {
            promotickResult.setException(e);
        }

        return promotickResult;
    }

    @ResponseBody
    @PostMapping(value = "restaurarClave")
    public PromotickResult restaurarClave(@RequestBody CambiarClave cambiarClave, PromotickResult promotickResult) {

        try {
            Participante participante = new Participante();
            participante.setClaveParticipante(encoder.encode(cambiarClave.getClave()));
            participante.setTransaccionToken(new TransaccionToken().setToken(cambiarClave.getToken()));
            participante.setAccion(4);

            Integer result = participanteWebService.actualizarParticipante(participante);

            if (result >= 0) {
                promotickResult
                        .setStatus(Boolean.TRUE)
                        .setMessage(Util.getMessage(messageSource, ConstantesWebMessage.MSG_PARTICIPANTE_ACTUALIZAR_CLAVE_EXITO));
            } else {
                throw new Exception(Util.getMessage(messageSource, ConstantesWebMessage.MSG_PARTICIPANTE_ACTUALIZAR_CLAVE_ERROR));
            }
        } catch (Exception e) {
            promotickResult
                    .setStatus(Boolean.FALSE)
                    .setMessage(e.getMessage());
        }

        return promotickResult;
    }

    private String codificar(Integer idParticipante, String fecha) {
        String encriptar = idParticipante + "|" + fecha;
        return encoder.encode(encriptar);
    }

    private String fechaActual() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        Date date = new Date();
        return dateFormat.format(date);
    }

    private String encode(String cadena) {
        Base64.Encoder base64 = Base64.getEncoder();
        return new String(base64.encode(cadena.getBytes()));
    }

    private String decode(String cadena) {
        Base64.Decoder base64 = Base64.getDecoder();
        return new String(base64.decode(cadena.getBytes()));
    }

    /*
    private String htmlToPdf(String processedHtml) {

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

        try {

            PdfDocument pdfDocument = new PdfDocument(new PdfWriter(byteArrayOutputStream));
            pdfDocument.setDefaultPageSize(PageSize.A4.rotate());

//            PdfWriter pdfwriter = new PdfWriter(byteArrayOutputStream);

            DefaultFontProvider defaultFont = new DefaultFontProvider(false, true, false);

            ConverterProperties converterProperties = new ConverterProperties();

            converterProperties.setFontProvider(defaultFont);

            HtmlConverter.convertToPdf(processedHtml, pdfDocument, converterProperties);

//            FileOutputStream fout = new FileOutputStream("D:/PRUEBA/employee.pdf");
//            File file = new File(promotickResourceService.web("pdf/test.pdf"));
            String folderName = UUID.randomUUID().toString().replace("-", "") + ".pdf";
            File file = new File(apiProperties.getClient().getDirTemporal() + "/" + folderName);
            FileOutputStream fout = new FileOutputStream(file);

            byteArrayOutputStream.writeTo(fout);
            byteArrayOutputStream.close();

            S3UploadRequest s3UploadRequest = new S3UploadRequest();
            s3UploadRequest.setFile(file);
            s3UploadRequest.setFolderName(UtilEnum.TIPO_CARGA.CARGA_CAPACITACION.getFolder());
            s3UploadRequest.setKey(folderName);
            s3UploadRequest.setPublic(true);
            s3UploadRequest.setType(UtilEnum.TIPO_CARGA.CARGA_CAPACITACION.name());
            s3UploadRequest.setEntity(1);
            s3UploadRequest.setMediaType(MediaType.APPLICATION_PDF);
            AbstractResponse<FileMetadata> resultUpload = apiS3Service.uploadFileS3(s3UploadRequest);
            if (resultUpload.isStatus()) {
                return resultUpload.getData().getUrl();
            }

            byteArrayOutputStream.flush();
            fout.close();

            return folderName;

        } catch(Exception ex) {
            ex.printStackTrace();
        }

        return "-1";
    }
    */
}
