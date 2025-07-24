package com.promotick.lafabril.web.service.impl;

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
import com.promotick.apiclient.properties.ApiProperties;
import com.promotick.apiclient.service.ApiEmailService;
import com.promotick.apiclient.service.ApiS3Service;
import com.promotick.lafabril.common.ConstantesSesion;
import com.promotick.lafabril.dao.web.CapacitacionDao;
import com.promotick.lafabril.dao.web.ConfiguracionDao;
import com.promotick.lafabril.model.configuracion.ConfiguracionWeb;
import com.promotick.lafabril.model.util.Util;
import com.promotick.lafabril.model.util.UtilEnum;
import com.promotick.lafabril.model.web.*;
import com.promotick.lafabril.web.service.CapacitacionWebService;
import com.promotick.configuration.services.PromotickResourceService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.io.FileOutputStream;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CapacitacionWebServiceImpl implements CapacitacionWebService {

    private static final Logger LOGGER = LoggerFactory.getLogger(CapacitacionWebServiceImpl.class);
    private CapacitacionDao capacitacionDao;
    private ApiEmailService apiEmailService;
    private PromotickResourceService promotickResourceService;
    private ConfiguracionDao configuracionDao;
    private ApiS3Service apiS3Service;
    private ApiProperties apiProperties;

    @Autowired
    public CapacitacionWebServiceImpl(CapacitacionDao capacitacionDao, ApiEmailService apiEmailService, PromotickResourceService promotickResourceService, ConfiguracionDao configuracionDao, ApiS3Service apiS3Service, ApiProperties apiProperties){
        this.capacitacionDao = capacitacionDao;
        this.apiEmailService = apiEmailService;
        this.promotickResourceService = promotickResourceService;
        this.configuracionDao = configuracionDao;
        this.apiS3Service = apiS3Service;
        this.apiProperties = apiProperties;
    }

    @Override
    public List<Capacitacion> capacitacionesListar(Integer idParticipante) {
        return capacitacionDao.capacitacionesListar(idParticipante);
    }

    @Override
    public Capacitacion capacitacionObtener(Integer idParticipante, Integer idCapacitacion) {
        return capacitacionDao.capacitacionObtener(idParticipante, idCapacitacion);
    }

    @Override
    public List<CapacitacionRecurso> capacitacionRecursosListar(Integer idCapacitacion) {
        return capacitacionDao.capacitacionRecursosListar(idCapacitacion);
    }

    @Override
    public List<CapacitacionPregunta> capacitacionPreguntasListar(Integer idCapacitacion) {
        List<CapacitacionPregunta> preguntas = capacitacionDao.capacitacionPreguntasListar(idCapacitacion);
        if (preguntas.isEmpty()) {
            return preguntas;
        } else {
            List<CapacitacionRespuesta> respuestas = capacitacionDao.capacitacionRespuestasListar(idCapacitacion);
            return preguntas.stream()
                    .peek(p -> p.setRespuestas(
                            respuestas.stream()
                                    .filter(r -> r.getIdCapacitacionPregunta().intValue() == p.getIdCapacitacionPregunta())
                                    .collect(Collectors.toList())
                    )).collect(Collectors.toList());
        }
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_UNCOMMITTED, rollbackFor = Exception.class)
    public CapacitacionParticipante guardarCapacitacionParticipante(Capacitacion capacitacion, Participante participante, CapacitacionParticipante capacitacionParticipante) throws Exception {
        capacitacionParticipante.setIdParticipante(participante.getIdParticipante());
        capacitacionParticipante.preguntasRawObject(capacitacion);
        //Registro de Cabecera
        Integer registroCapacitacionParticipante = capacitacionDao.capacitacionParticipanteMantenimiento(capacitacionParticipante);
        if (registroCapacitacionParticipante == null || registroCapacitacionParticipante <= 0) {
            throw new Exception("Ocurrio un error al guardar la capacitacion del participante");
        }
        capacitacionParticipante.setIdCapacitacionParticipante(registroCapacitacionParticipante);

        this.validarPreguntas(capacitacion.getPreguntas(), capacitacionParticipante);

        capacitacionParticipante.setCantidadCorrectas((int) capacitacionParticipante.getPreguntasRespondidas().stream().filter(CapacitacionPregunta::isPreguntaResueltaCorrecto).count());
        capacitacionParticipante.setCantidadErroneas((int) capacitacionParticipante.getPreguntasRespondidas().stream().filter(r -> !r.isPreguntaResueltaCorrecto()).count());
        capacitacionParticipante.setCalificacion(capacitacionParticipante.getCantidadCorrectas());
        capacitacionDao.capacitacionParticipanteMantenimiento(capacitacionParticipante);
        if (capacitacionParticipante.getCalificacion() > 7){
            capacitacionDao.registrarPuntosCapacitacionParticipante(capacitacionParticipante);
            this.buildDiploma(participante, capacitacion);
            this.enviarEmailGanador(participante, capacitacion);
        }
        return capacitacionParticipante;
    }

    private void buildDiploma(Participante participante, Capacitacion capacitacion) {
        try {
            String diplomaHtml;
            LocalDate fechaActual = LocalDate.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd 'de' MMMM 'del' yyyy", new Locale("es"));
            String fechaFormateada = fechaActual.format(formatter);

            diplomaHtml =   "<html lang='en'><head><meta http-equiv='Content-Type' content='text/html; charset=UTF-8'></head><body>" +
                            "<table align='center' border='0' cellpadding='0' cellspacing='0' width='842' style=';box-shadow: 0 0 9px 2px #C7C7C7; margin: 0 auto;font-family: Arial,sans-serif;'><tbody>" +
                            String.format("<tr><td style=''><img src='%s' alt=''>", promotickResourceService.web("img/bg/mail-capacitacion/diploma.png")) +
                            String.format("<p style='font-size: 30px;color: #001d85;margin-left: 55px;margin-top: 0;'>%s</p>", participante.getNombresParticipante() + " " + participante.getAppaternoParticipante() + " " + participante.getApmaternoParticipante()) +
                            "</td></tr>" +
                            String.format("\t<tr><td style='background: #001d85'><p style='margin-left: 55px;color: #fff'>Por haber finalizado con éxito la capacitación en: %s</p></td></tr>", capacitacion.getNombreCapacitacion()) +
                            String.format("<tr><td style='background: #001d85 url(%s)'>", promotickResourceService.web("img/bg/mail-capacitacion/foot2.png")) +
                            String.format("<p style='color: #fff;margin: 58px 0 35px 55px'>%s</p>", fechaFormateada) +
                            "</td></tr>" +
                            "</tbody></table></body></html>";

            String folderName = this.htmlToPdf(diplomaHtml);

            if (folderName.equals("-1")){
                throw new Exception("No se pudo generar el pdf");
            }

            Util.getSession().setAttribute(ConstantesSesion.SESSION_DIPLOMA_FILNAME, folderName);

        }catch (Exception e){
            e.printStackTrace();
        }

    }

    @Override
    public CapacitacionParticipante capacitacionParticipanteObtener(Integer idParticipante, Integer idCapacitacion) {
        CapacitacionParticipante capacitacionParticipante = capacitacionDao.capacitacionParticipanteObtener(idParticipante, idCapacitacion);
        if (capacitacionParticipante != null) {
            capacitacionParticipante.setDetalles(capacitacionDao.capacitacionParticipanteDetalleListar(idParticipante, idCapacitacion));
        }
        return capacitacionParticipante;
    }

    private void validarPreguntas(List<CapacitacionPregunta> preguntasDB, CapacitacionParticipante capacitacionParticipante) throws Exception {

        for (CapacitacionPregunta preguntaCliente : capacitacionParticipante.getPreguntasRespondidas()) {

            CapacitacionParticipantePregunta capacitacionParticipantePregunta = new CapacitacionParticipantePregunta()
                    .setIdCapacitacionParticipante(capacitacionParticipante.getIdCapacitacionParticipante())
                    .setIdCapacitacionPregunta(preguntaCliente.getIdCapacitacionPregunta());

            Integer idCapacitacionParticipantePregunta = capacitacionDao.guardarCapacitacionParticipantePregunta(capacitacionParticipantePregunta);
            capacitacionParticipantePregunta.setIdCapacitacionParticipantePregunta(idCapacitacionParticipantePregunta);

            List<CapacitacionRespuesta> respuestasCorrectasDB = preguntasDB.stream()
                    .filter(p -> p.getIdCapacitacionPregunta().intValue() == preguntaCliente.getIdCapacitacionPregunta())
                    .map(CapacitacionPregunta::getRespuestas)
                    .flatMap(Collection::stream)
                    .filter(CapacitacionRespuesta::getEsCorrecta)
                    .collect(Collectors.toList());


            int correcto = 0;
            int errores = 0;
            for (CapacitacionRespuesta respuestaCliente : preguntaCliente.getRespuestas()) {
                Optional<CapacitacionRespuesta> respuestaCorrecta = respuestasCorrectasDB.stream().filter(r -> r.getIdCapacitacionRespuesta().intValue() == respuestaCliente.getIdCapacitacionRespuesta()).findAny();
                if (respuestaCorrecta.isPresent()) {
                    respuestaCliente.setEsCorrecta(true);
                    correcto++;
                } else {
                    respuestaCliente.setEsCorrecta(false);
                    errores++;
                }
                this.guardarDetalle(respuestaCliente, capacitacionParticipantePregunta);
            }

            preguntaCliente.setPreguntaResueltaCorrecto(errores == 0 && respuestasCorrectasDB.size() == correcto);

            capacitacionParticipantePregunta.setResultadoPregunta(preguntaCliente.isPreguntaResueltaCorrecto());
            capacitacionDao.guardarCapacitacionParticipantePregunta(capacitacionParticipantePregunta);
        }
    }

    private void guardarDetalle(CapacitacionRespuesta respuesta, CapacitacionParticipantePregunta capacitacionParticipantePregunta) throws Exception {
        CapacitacionParticipanteDetalle detalle = new CapacitacionParticipanteDetalle()
                .setIdCapacitacionParticipantePregunta(capacitacionParticipantePregunta.getIdCapacitacionParticipantePregunta())
                .setCapacitacionRespuesta(respuesta)
                .setEsCorrecta(respuesta.getEsCorrecta());
        Integer registro = capacitacionDao.capacitacionParticipanteDetalleRegistrar(detalle);
        if (registro == null || registro <= 0) {
            throw new Exception("No se pudo registrar la respuesta del participante");
        }
    }

    private void enviarEmailGanador(Participante participante, Capacitacion capacitacion) throws Exception {
        ConfiguracionWeb configuracionWeb = configuracionDao.obtenerConfiguracionWeb();
        String nombres = participante.getNombresParticipante() + " " + participante.getAppaternoParticipante() + " " + participante.getApmaternoParticipante();
        String pathAbsoluteHtml = promotickResourceService.mail("capacitacion_aprobada.html");
        String header = promotickResourceService.web("img/bg/mail-capacitacion/header1.png");
        String footer = promotickResourceService.web("img/bg/mail-capacitacion/foot.png");
        String logo   = promotickResourceService.web("img/bg/mail-capacitacion/feli.png");

        Object[] data = {
                nombres, //0
                capacitacion.getNombreCapacitacion(), //1
                capacitacion.getPuntosCapacitacion(), //2
                participante.getPuntosDisponibles() + capacitacion.getPuntosCapacitacion(), //3
                header,
                footer,
                logo,
                Util.getSession().getAttribute(ConstantesSesion.SESSION_DIPLOMA_FILNAME)
        };

        Util.getSession().setAttribute(ConstantesSesion.SESSION_DIPLOMA_FILNAME, null);

        String correoDestino = participante.getEmailParticipante();
        String alias = "Capacitacion Aprobada";
        String asunto = "Capacitacion Aprobada";
        try {

            EmailRequest emailRequest = new EmailRequest();
            emailRequest.setTo(correoDestino);
            emailRequest.setFrom(configuracionWeb.getEmailInfo());
            emailRequest.setFromName(alias);
            emailRequest.setSubject(asunto);
            emailRequest.setPathTemplate(pathAbsoluteHtml);
            emailRequest.setParameters(data);
            emailRequest.generateHtml();
            LOGGER.info("SEND EMAIL TO: " + emailRequest.getTo());

            apiEmailService.sendEmailAsync(emailRequest);
        }catch (Exception e){
            LOGGER.error("Error envio de email", e);
        }
    }

    private String htmlToPdf(String processedHtml) {

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

        try {

            PdfDocument pdfDocument = new PdfDocument(new PdfWriter(byteArrayOutputStream));
            pdfDocument.setDefaultPageSize(PageSize.A4.rotate());

            DefaultFontProvider defaultFont = new DefaultFontProvider(false, true, false);

            ConverterProperties converterProperties = new ConverterProperties();
            converterProperties.setFontProvider(defaultFont);

            HtmlConverter.convertToPdf(processedHtml, pdfDocument, converterProperties);

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
}
