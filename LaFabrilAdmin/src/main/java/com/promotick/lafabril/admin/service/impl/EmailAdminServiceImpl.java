package com.promotick.lafabril.admin.service.impl;

import com.promotick.apiclient.model.AbstractResponse;
import com.promotick.apiclient.model.request.EmailRequest;
import com.promotick.apiclient.model.response.SengridEmail;
import com.promotick.apiclient.service.ApiEmailService;
import com.promotick.lafabril.admin.service.*;
import com.promotick.lafabril.admin.util.CronException;
import com.promotick.lafabril.admin.util.values.TipoCargaEnum;
import com.promotick.lafabril.admin.util.values.TipoEnvioEmailEnum;
import com.promotick.lafabril.admin.service.*;
import com.promotick.lafabril.model.configuracion.ConfiguracionWeb;
import com.promotick.lafabril.model.facturacion.CargaProceso;
import com.promotick.lafabril.model.facturacion.PeriodoParticipante;
import com.promotick.lafabril.model.util.MetaParticipante;
import com.promotick.lafabril.model.util.Util;
import com.promotick.lafabril.model.util.UtilEnum;
import com.promotick.lafabril.model.util.form.CargaMetas;
import com.promotick.lafabril.model.util.form.CargaVentas;
import com.promotick.lafabril.model.web.Participante;
import com.promotick.lafabril.model.web.ParticipanteMeta;
import com.promotick.lafabril.model.web.Producto;
import com.promotick.configuration.properties.PromotickProperties;
import com.promotick.configuration.services.PromotickResourceService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Service;
import org.thymeleaf.util.StringUtils;

import java.io.File;
import java.time.LocalDate;
import java.util.List;

@Slf4j
@Service
@EnableAsync
public class EmailAdminServiceImpl implements EmailAdminService {

    private ParticipanteAdminService participanteAdminService;
    private PromotickResourceService promotickResourceService;
    private PromotickProperties promotickProperties;
    private ConfiguracionAdminService configuracionAdminService;
    private ApiEmailService apiEmailService;
    private PeriodoParticipanteAdminService periodoParticipanteAdminService;
    private FacturaAdminService facturaAdminService;
    private ProductoAdminService productoAdminService;
    private PeriodoAdminService periodoAdminService;

    @Autowired
    public EmailAdminServiceImpl(ParticipanteAdminService participanteAdminService, PromotickResourceService promotickResourceService, PromotickProperties promotickProperties, ConfiguracionAdminService configuracionAdminService, ApiEmailService apiEmailService, PeriodoParticipanteAdminService periodoParticipanteAdminService, FacturaAdminService facturaAdminService, ProductoAdminService productoAdminService, PeriodoAdminService periodoAdminService) {
        this.participanteAdminService = participanteAdminService;
        this.promotickResourceService = promotickResourceService;
        this.promotickProperties = promotickProperties;
        this.configuracionAdminService = configuracionAdminService;
        this.apiEmailService = apiEmailService;
        this.periodoParticipanteAdminService = periodoParticipanteAdminService;
        this.facturaAdminService = facturaAdminService;
        this.productoAdminService = productoAdminService;
        this.periodoAdminService = periodoAdminService;
    }

    @Async
    @Override
    public void envioEmailBienvenida() {
        try {
            List<Participante> emailsBienvenida = participanteAdminService.listarParticipanteEmail(TipoEnvioEmailEnum.EMAIL_BIENVENIDA.getCodigo());

            ConfiguracionWeb configuracionWeb = configuracionAdminService.obtenerConfiguracionWeb();

            for (Participante participante : emailsBienvenida) {
                this.envioEmailBienvenidaIndividual(participante, configuracionWeb, true);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Async
    @Override
    public void envioEmailBienvenidaIndividual(Participante participante, ConfiguracionWeb configuracionWeb, boolean muestraClave) {
        try {

            String path = promotickResourceService.mail("bienvenida-v2.html");
            String header = promotickResourceService.web("img/bg/mail-bienvenida/head.png");
            String fondo = promotickResourceService.web("img/festividades/bienvenida20.png");
            String dominio = promotickProperties.getWeb().getMvc().getContext().getWeb();
            String footer = promotickResourceService.web("img/bg/mail-bienvenida/bot-foot.png");
            String logo = promotickResourceService.web("img/bg/mail-bienvenida/logo.png");
            LocalDate date = LocalDate.now();
            String anio = String.valueOf(date.getYear());

            if (StringUtils.isEmpty(participante.getEmailParticipante())) {
                throw new Exception("El participante no tiene un email");
            }

            if (participante.getCatalogo() != null && !StringUtils.isEmpty(participante.getCatalogo().getImagenBienvenida())) {
                fondo = promotickResourceService.web("img/festividades/" + participante.getCatalogo().getImagenBienvenida());
            }

            Object[] data = {
                    header, //0
                    participante.getNombresParticipante(), //1
                    participante.getUsuarioParticipante(), //2
//                    muestraClave ? participante.getUsuarioParticipante() : "******", //3
                    participante.getClaveParticipante(), //3
                    configuracionWeb.getTelefonoContacto(), //4
                    configuracionWeb.getEmailContacto(), //5
                    logo,   //6
                    footer,  //7
                    dominio, //8
                    participante.getNombresParticipante(), //9
                    anio //10

            };

            this.enviarCorreo(path, data, "Bienvenido a " + configuracionWeb.getAliasCorreo(), participante.getEmailParticipante(), null, configuracionWeb);

            participante.setEmailEnviado(true);
            participante.setEmailObservacion("Email enviado correcto");
        } catch (Exception e) {
            log.error("Ocurrio un error al enviar el email: {}", e.getMessage());
            participante.setEmailEnviado(false);
            participante.setEmailObservacion(e.getMessage());
        } finally {
            participante.setAccion(UtilEnum.MANTENIMIENTO.ENVIO_EMAIL.getCodigo());
            participanteAdminService.actualizarParticipante(participante);
        }
    }

    @Async
    @Override
    public void envioEmailPuntos(TipoEnvioEmailEnum tipoEnvioEmailEnum) {
        try {
            List<Participante> emailsCargaFacturas = participanteAdminService.listarParticipanteEmail(tipoEnvioEmailEnum.getCodigo());

            ConfiguracionWeb configuracionWeb = configuracionAdminService.obtenerConfiguracionWeb();
            String path = promotickResourceService.mail("puntos-posibles.html");
            String cabecera = promotickResourceService.web("img/bg/bg-header-3.png");
            String cabecera2 = promotickResourceService.web("img/bg/bg-header-2.png");
            String dominio = promotickProperties.getWeb().getMvc().getContext().getWeb();

            for (Participante participante : emailsCargaFacturas) {

                MetaParticipante metaParticipante = periodoParticipanteAdminService.obtenerMetaParticipante(participante.getIdParticipante(), UtilEnum.TIPO_META.SEMENSTRAL);

                if (metaParticipante == null) {
                    metaParticipante = new MetaParticipante()
                            .setIsMetaCumplida(true)
                            .setTeFalta(0);
                }
                if (metaParticipante.getIsMetaCumplida()) {
                    path = promotickResourceService.mail("puntos-acreditados.html");
                }

                try {

                    if (StringUtils.isEmpty(participante.getEmailParticipante())) {
                        throw new Exception("El participante no tiene un email");
                    }

                    Object[] data = {
                            participante.getNombresParticipante() + " " + participante.getAppaternoParticipante() + " " + participante.getApmaternoParticipante(), //0
                            cabecera, //1
                            participante.getPuntosDisponibles(), //2
                            dominio, //3
                            participante.getPuntosPosibles(), //4
                            metaParticipante.getTeFalta(), //5
                            cabecera2 //6
                    };

                    this.enviarCorreo(path, data, "Carga de puntos", participante.getEmailParticipante(), null, configuracionWeb);

                    participante.setEmailEnviado(true);
                    participante.setEmailObservacion("Email enviado correcto");
                } catch (Exception e) {
                    participante.setEmailEnviado(false);
                    participante.setEmailObservacion(e.getMessage());
                } finally {

                    switch (tipoEnvioEmailEnum) {
                        case EMAIL_CARGA_FACTURAS:
                            facturaAdminService.facturaEnvioEmail(participante.getIdParticipante(), participante.getEmailEnviado(), participante.getEmailObservacion());
                            break;
                        case EMAIL_CARGA_MANUALES:
                            facturaAdminService.cargaManualEnvioEmail(participante.getIdParticipante(), participante.getEmailEnviado(), participante.getEmailObservacion());
                            break;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Async
    @Override
    public void envioEmailCierrePeriodo() {
        try {
            List<Participante> emailsCierrePeriodo = participanteAdminService.listarParticipanteEmail(TipoEnvioEmailEnum.EMAIL_CIERRE_PERIODO.getCodigo());

            ConfiguracionWeb configuracionWeb = configuracionAdminService.obtenerConfiguracionWeb();
            String path = promotickResourceService.mail("acreditacion.html");
            String header = promotickResourceService.web("img/bg/bg-header-2.png");
            String header2 = promotickResourceService.web("img/bg/bg-header-3.png");
            String dominio = promotickProperties.getWeb().getMvc().getContext().getWeb();

            for (Participante participante : emailsCierrePeriodo) {
                try {
                    if (StringUtils.isEmpty(participante.getEmailParticipante())) {
                        throw new Exception("El participante no tiene un email");
                    }

                    Object[] data = {
                            header, //0
                            header2, //1
                            participante.getNombresParticipante() + " " + participante.getAppaternoParticipante() + " " + participante.getApmaternoParticipante(), //2
                            participante.getPuntosDisponibles(), //3
                            dominio, //4
                            participante.getPeriodoMeta() //5
                    };

                    this.enviarCorreo(path, data, "Cierre de periodo", participante.getEmailParticipante(), null, configuracionWeb);

                    participante.setEmailEnviado(true);
                    participante.setEmailObservacion("Email enviado correcto");
                } catch (Exception e) {
                    participante.setEmailEnviado(false);
                    participante.setEmailObservacion(e.getMessage());
                } finally {
                    participanteAdminService.periodoParticipanteEnvioEmail(participante);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Async
    @Override
    public void envioEmailProductosNuevos() {
        try {
            List<Participante> emailsProductosNuevos = participanteAdminService.listarParticipanteEmail(TipoEnvioEmailEnum.EMAIL_PRODUCTOS_NUEVOS.getCodigo());

            ConfiguracionWeb configuracionWeb = configuracionAdminService.obtenerConfiguracionWeb();
            String path = promotickResourceService.mail("productos-nuevos.html");
            String fondo = promotickResourceService.web("img/bg/bg-fondo-producto.png");
            String header = promotickResourceService.web("img/bg/bg-header-producto.png");
            String dominio = promotickProperties.getWeb().getMvc().getContext().getWeb();

            for (Participante participante : emailsProductosNuevos) {
                try {
                    if (StringUtils.isEmpty(participante.getEmailParticipante())) {
                        throw new Exception("El participante no tiene un email");
                    }

                    List<Producto> nuevos = productoAdminService.productoNuevosListar(participante.getCatalogo().getIdCatalogo(), participante.getPuntosDisponibles());

                    if (nuevos.isEmpty()) {
                        throw new Exception("No hay productos nuevos que enviar");
                    }

                    Object[] data = {
                            header, //0
                            fondo, //1
                            dominio, //2
                            this.construirProductos(nuevos) //3
                    };

                    this.enviarCorreo(path, data, "Nuevos Productos", participante.getEmailParticipante(), null, configuracionWeb);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

//            productoAdminService.productoCatalogoEnvioEmail(true, "Envio correcto");
        } catch (Exception e) {
            log.error("Error enviar email", e);
        }
    }

    @Override
    public void envioEmailRebote(CronException exception, File file, CargaProceso cargaProceso) {
        try {
            String nombreFile = "Rebote-Errores-Carga-Participantes.xlsx";
            String subject = "Observaciones Carga de participantes - La Fabril";
            if (exception.getTipoCargaEnum().equals(TipoCargaEnum.CARGA_VENTAS)) {
                subject = "Observaciones Carga de facturas - La Fabril";
                nombreFile = "Rebote-Errores-Carga-Facturas.xlsx";
            }

            String path = promotickResourceService.mail("email-rebote.html");

            Object[] data = {
                    exception.getTipoCargaEnum().name(),
                    exception.getMessage(),
                    cargaProceso.getTotal(),
                    cargaProceso.getCorrectos(),
                    cargaProceso.getErrores()
            };

            String[] copias = null;
            ConfiguracionWeb configuracionWeb = configuracionAdminService.obtenerConfiguracionWeb();
            if (configuracionWeb.getCopiasRebote() != null) {
                copias = configuracionWeb.getCopiasRebote().split("\\|");
            }

            this.enviarCorreo(path, data, subject, configuracionWeb.getEmailRebote(), copias, configuracionWeb, file, nombreFile);
        } catch (Exception e) {
            log.error("Error al enviar email de rebote", e);
        }
    }

    @Override
    public void envioEmailError(Exception exception, File file, String fileName) {
        try {

            String path = promotickResourceService.mail("email-error.html");
            String className = "Sin Definir";
            String methodName = "Sin Definir";
            int lineNumber = 0;

            if (exception.getStackTrace().length > 0) {
                StackTraceElement stackTraceElement = exception.getStackTrace()[0];
                className = stackTraceElement.getClassName();
                methodName = stackTraceElement.getMethodName();
                lineNumber = stackTraceElement.getLineNumber();
            }

            Object[] data = {
                    exception.getMessage(),
                    className,
                    methodName,
                    lineNumber
            };

            ConfiguracionWeb configuracionWeb = configuracionAdminService.obtenerConfiguracionWeb();

            this.enviarCorreo(path, data, "Error La Fabril", configuracionWeb.getCorreoSistemas(), null, configuracionWeb, file, fileName);
        } catch (Exception e) {
            log.error("Error al enviar email de rebote", e);
        }
    }

    @Override
    public void envioEmailResumen(CargaProceso cargaProceso) {
        try {

            String path = promotickResourceService.mail("email-rebote.html");

            Object[] data = {
                    cargaProceso.getCarga().getNombreArchivo(),
                    (cargaProceso.getErrores() > 0 ? "El proceso tuvo observaciones q se adjuntaron en un correo por separado" : "Se proceso correctamente"),
                    cargaProceso.getTotal(),
                    cargaProceso.getCorrectos(),
                    cargaProceso.getErrores()
            };

            ConfiguracionWeb configuracionWeb = configuracionAdminService.obtenerConfiguracionWeb();

            this.enviarCorreo(path, data, "Procesamiento de archivo", configuracionWeb.getEmailRebote(), null, configuracionWeb);
        } catch (Exception e) {
            log.error("Error al enviar email de resumen", e);
        }
    }

    @Override
    public void envioEmailCumpleanos(Participante participante) {
        try {
            String path = promotickResourceService.mail("cumpleanos-v3.html");

            ConfiguracionWeb configuracionWeb = configuracionAdminService.obtenerConfiguracionWeb();
            String cumple = promotickResourceService.web("img/festividades/cumple.png");
            String header = promotickResourceService.web("img/festividades/header-b.png");
            String footer = promotickResourceService.web("img/festividades/footer-b.png");
            String logo = promotickResourceService.web("img/festividades/logo.png");
            String gif = promotickResourceService.web("img/festividades/saludo.gif");
            String dominio = promotickProperties.getWeb().getMvc().getContext().getWeb();
            LocalDate date = LocalDate.now();
            String anio = String.valueOf(date.getYear());

            if (participante.getCatalogo() != null && !StringUtils.isEmpty(participante.getCatalogo().getImagenCumpleanos())) {
                cumple = promotickResourceService.web("img/festividades/" + participante.getCatalogo().getImagenCumpleanos());
            }

            Object[] data = {
                    header, //0
                    logo, //1
                    footer, //2
                    participante.getNombresParticipante() + " " + participante.getAppaternoParticipante() + " " + participante.getApmaternoParticipante(), //3
                    cumple, //4
                    dominio, //5
                    anio, //6
                    gif //7
            };
            this.enviarCorreo(path, data, "Feliz Cumpleaños", participante.getEmailParticipante(), null, configuracionWeb);
        } catch (Exception e) {
            log.error("Error al enviar email de cumpleanos", e);
        }

    }

    @Override
    public void envioEmailMetaCumplida() {
        try {
            List<PeriodoParticipante> emailsMetaCumplida = periodoAdminService.recalculoMetas();

            ConfiguracionWeb configuracionWeb = configuracionAdminService.obtenerConfiguracionWeb();
            String path = promotickResourceService.mail("acreditacion.html");
            String header = promotickResourceService.web("img/bg/bg-header-2.png");
            String header2 = promotickResourceService.web("img/bg/bg-header-3.png");
            String dominio = promotickProperties.getWeb().getMvc().getContext().getWeb();

            for (PeriodoParticipante periodoParticipante : emailsMetaCumplida) {
                try {
                    Participante participante = periodoParticipante.getParticipante();
                    if (StringUtils.isEmpty(participante.getEmailParticipante())) {
                        throw new Exception("El participante no tiene un email");
                    }

                    Object[] data = {
                            header, //0
                            header2, //1
                            participante.getNombresParticipante() + " " + participante.getAppaternoParticipante() + " " + participante.getApmaternoParticipante(), //2
                            participante.getPuntosDisponibles(), //3
                            dominio, //4
                            periodoParticipante.getPeriodoMeta() //5
                    };

                    this.enviarCorreo(path, data, "Meta cumplida", participante.getEmailParticipante(), null, configuracionWeb);

                    periodoParticipante.setEmailEnviado(true);
                    periodoParticipante.setEmailObservacion("Email enviado correcto");
                } catch (Exception e) {
                    log.error("error envio correo meta cumplida", e);
                    periodoParticipante.setEmailEnviado(false);
                    periodoParticipante.setEmailObservacion(e.getMessage());
                } finally {
                    participanteAdminService.periodoParticipanteEnvioEmail(periodoParticipante);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Async
    @Override
    public void envioEmailSistemas(String mensaje, String titulo) {
        try {

            String path = promotickResourceService.mail("default.html");

            Object[] data = {
                    mensaje
            };

            ConfiguracionWeb configuracionWeb = configuracionAdminService.obtenerConfiguracionWeb();

            this.enviarCorreo(path, data, titulo, configuracionWeb.getCorreoSistemas(), null, configuracionWeb);
        } catch (Exception e) {
            log.error("Error al enviar email Sistemas", e);
        }
    }

    @Async
    @Override
    public void envioEmailPuntosManuales(Participante participante, Integer puntosCargados) {
        try {

            ConfiguracionWeb configuracionWeb = configuracionAdminService.obtenerConfiguracionWeb();
            String path = promotickResourceService.mail("puntos-acreditados-v3.html");
            String header = promotickResourceService.web("img/bg/mail-puntos/header-a.png");
            String footer = promotickResourceService.web("img/bg/mail-puntos/footer-a.png");
            String logo = promotickResourceService.web("img/bg/mail-puntos/logo.png");
            String linea = promotickResourceService.web("img/bg/mail-puntos/linea.png");
            String dominio = promotickProperties.getWeb().getMvc().getContext().getWeb();
            LocalDate date = LocalDate.now();
            String anio = String.valueOf(date.getYear());

            if (StringUtils.isEmpty(participante.getEmailParticipante())) {
                throw new Exception("El participante no tiene un email");
            }

            List<Producto> nuevos = productoAdminService.productoNuevosListar(participante.getCatalogo().getIdCatalogo(), participante.getPuntosDisponibles());

            if (nuevos.isEmpty()) {
                throw new Exception("No hay productos nuevos que enviar");
            }

            Object[] data = {
                    header, //0
                    puntosCargados, //1
                    participante.getPuntosDisponibles(), //2
                    participante.getNombresParticipante() + " " + participante.getAppaternoParticipante() + " " + participante.getApmaternoParticipante(), //3
                    this.construirProductos(nuevos), //4
                    dominio, // 5
                    configuracionWeb.getTelefonoContacto(), //6
                    configuracionWeb.getEmailContacto(), //7
                    footer, //8
                    logo, //9
                    linea, //10
                    anio, //11
            };

            this.enviarCorreo(path, data, "Felicitaciones! Acumulaste " + participante.getPuntosDisponibles() + " puntos", participante.getEmailParticipante(), null, configuracionWeb);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void envioEmailCargaMetas(Participante participante, CargaMetas cargaMetas) {
        try {

            ConfiguracionWeb configuracionWeb = configuracionAdminService.obtenerConfiguracionWeb();
            String path = promotickResourceService.mail("meta-cargada-v2.html");
            String imgBanner = promotickResourceService.web("img/bg/mail-meta/header-a.png");
            String footer = promotickResourceService.web("img/bg/mail-meta/footer-a.png");
            String imgLogo = promotickResourceService.web("img/logos/logo.png");
            String linea = promotickResourceService.web("img/bg/mail-meta/linea.png");
            String dominio = promotickProperties.getWeb().getMvc().getContext().getWeb();
            String imgInferior = promotickResourceService.web("img/bg/mail-meta/enlace.png");
            if (StringUtils.isEmpty(participante.getEmailParticipante())) {
                throw new Exception("El participante no tiene un email");
            }

            Object[] data = {
                    imgBanner, //0
                    cargaMetas.getMeta(), //1
                    imgInferior, //2
                    participante.getNombresParticipante() + " " + participante.getAppaternoParticipante() + " " + participante.getApmaternoParticipante(), //3
                    "", //4
                    dominio, //5
                    Util.nombreMes(cargaMetas.getMes()), //6
                    imgLogo, //7
                    linea, //8
                    footer, //9
            };

            this.enviarCorreo(path, data, "Carga de metas", participante.getEmailParticipante(), null, configuracionWeb);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }



    @Override
    public void envioEmailCargaVentas(Participante participante, CargaVentas cargaVentas, ParticipanteMeta participanteMeta) {

        try {
            ConfiguracionWeb configuracionWeb = configuracionAdminService.obtenerConfiguracionWeb();
            String path = promotickResourceService.mail("venta-cargada-v2.html");
            String imgBanner = promotickResourceService.web("img/bg/mail-venta/header.png");
            String imgLogo = promotickResourceService.web("img/logos/logo.png");
            String linea = promotickResourceService.web("img/bg/mail-venta/linea.png");
            String footer = promotickResourceService.web("img/bg/mail-venta/footer.png");

            if (StringUtils.isEmpty(participante.getEmailParticipante())) {
                throw new Exception("El participante no tiene un email");
            }
            double falta = participanteMeta.getValorMeta() - participanteMeta.getAvance();
            if (falta < 0) {
                falta = 0;
            }

            Object[] data = {
                    imgBanner, //0
                    cargaVentas.getValorVenta(), //1
                    participanteMeta.getNombreMes(), //2
                    participante.getNombresParticipante() + " " + participante.getAppaternoParticipante() + " " + participante.getApmaternoParticipante(), //3
                    participanteMeta.getAvance(), // 4
                    imgLogo, // 5
                    linea, // 6
                    falta, // 7
                    footer, // 8
            };

            this.enviarCorreo(path, data, "Carga de ventas", participante.getEmailParticipante(), null, configuracionWeb);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void enviarCorreo(String pathTemplate, Object[] data, String subject, String destino, String[] copias, ConfiguracionWeb configuracionWeb) throws Exception {
        this.enviarCorreo(pathTemplate, data, subject, destino, copias, configuracionWeb, null, null);
    }

    private void enviarCorreo(String pathTemplate, Object[] data, String subject, String destino, String[] copias, ConfiguracionWeb configuracionWeb, File file, String nombreFile) throws Exception {
        EmailRequest emailRequest = new EmailRequest();
        emailRequest.setTo(destino);
        emailRequest.setFrom(configuracionWeb.getEmailInfo());
        emailRequest.setFromName(configuracionWeb.getAliasCorreo());
        emailRequest.setSubject(subject);
        emailRequest.setPathTemplate(pathTemplate);
        emailRequest.setParameters(data);
        emailRequest.setFile(file);
        emailRequest.setFileName(nombreFile);

        if (copias != null) {
            for (String bcc : copias) {
                emailRequest.addBcc(bcc);
            }
        }

        AbstractResponse<SengridEmail> response = apiEmailService.sendEmail(emailRequest);
        log.info("Response EMAIL TO: " + emailRequest.getTo());
        log.info("Response EMAIL Status: " + response.isStatus());
        log.info("Response EMAILS Message: " + response.getMessage());

        if (!response.isStatus()) {
            throw new Exception(response.getMessage());
        }

    }

    private String construirProductos(List<Producto> productos) {
        StringBuilder stringBuilder = new StringBuilder();
        String dashed = "border-right: 1px dashed #0f72b3;";
        int pos = 1;
        for (Producto producto : productos) {
            stringBuilder.append(String.format("<div style='%s display: inline-block;padding: 15px;vertical-align: top;'>", pos != productos.size() ? dashed : ""))
                    .append(String.format("<img src='%s' alt='' width='100' height='100'>", promotickResourceService.web("img/productos/" + producto.getImagenUno())))
                    .append(String.format("<div style='max-width:100px'><p style='color: #242141;font-size: 13px;text-align: center'>%s</p></div>", producto.getNombreProducto()))
                    .append(String.format("<p style='color: #242141;font-size: 11px;text-align: center'>Puntos: %s</p>", producto.getPuntosProducto()))
                    .append("</div>");
            pos ++;
        }
        return stringBuilder.toString();
    }

    @Override
    public void envioEmailEstadoCanje(Participante participante) {
        try {
            String path = promotickResourceService.mail("estado-canje.html");

            ConfiguracionWeb configuracionWeb = configuracionAdminService.obtenerConfiguracionWeb();
            String header = promotickResourceService.web("img/mail-estado/bg-header-estado.png");
            String logo = promotickResourceService.web("img/mail-estado/logo.png");
            String footer = promotickResourceService.web("img/mail-estado/bg-footer-estado.png");

            Object[] data = {
                    header, //0
                    logo,   //1
                    footer  //3
            };
            this.enviarCorreo(path, data, "Informacion", participante.getEmailParticipante(), null, configuracionWeb);
        } catch (Exception e) {
            log.error("Error al enviar email de bloqueo canjes", e);
        }

    }
}
