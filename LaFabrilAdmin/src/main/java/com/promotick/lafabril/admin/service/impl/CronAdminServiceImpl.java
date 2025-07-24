package com.promotick.lafabril.admin.service.impl;

import com.promotick.apiclient.model.AbstractResponse;
import com.promotick.apiclient.model.external.ExternalResult;
import com.promotick.apiclient.model.request.EmailRequest;
import com.promotick.apiclient.model.request.S3UploadRequest;
import com.promotick.apiclient.model.response.FileMetadata;
import com.promotick.apiclient.model.response.NetsuiteResponse;
import com.promotick.apiclient.model.response.SengridEmail;
import com.promotick.apiclient.properties.ApiProperties;
import com.promotick.apiclient.service.ApiEmailService;
import com.promotick.apiclient.service.ApiNetsuiteService;
import com.promotick.apiclient.service.ApiS3Service;
import com.promotick.apiclient.service.ApiSFTPService;
import com.promotick.apiclient.utils.file.excel.generator.ExcelBuilder;
import com.promotick.apiclient.utils.file.validator.FileValidatorResult;
import com.promotick.lafabril.admin.service.*;
import com.promotick.lafabril.admin.util.CronException;
import com.promotick.lafabril.admin.util.values.TipoCargaEnum;
import com.promotick.lafabril.admin.util.values.TipoEnvioEmailEnum;
import com.promotick.lafabril.admin.util.values.TipoProcesoEnum;
import com.promotick.lafabril.model.facturacion.*;
import com.promotick.lafabril.model.web.*;
import com.promotick.configuration.properties.PromotickProperties;
import com.promotick.configuration.services.PromotickResourceService;
import com.promotick.lafabril.admin.service.*;
import com.promotick.lafabril.common.ConstantesCommon;
import com.promotick.lafabril.model.configuracion.ConfiguracionWeb;
import com.promotick.lafabril.model.util.FiltroEstadoCuenta;
import com.promotick.lafabril.model.util.descarga.FormBulkFacturas;
import com.promotick.lafabril.model.util.descarga.FormBulkParticipantes;
import com.promotick.lafabril.model.util.descarga.FormCierrePeriodo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Service;

import java.io.File;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
@EnableAsync
public class
CronAdminServiceImpl implements CronAdminService {

    private PeriodoAdminService periodoAdminService;
    private ProcesoAdminService procesoAdminService;
    private CargaAdminService cargaAdminService;
    private CargaProcesoAdminService cargaProcesoAdminService;
    private ApiS3Service apiS3Service;
    private ApiSFTPService apiSFTPService;
    private BulkAdminService bulkAdminService;
    private EmailAdminService emailAdminService;
    private ApiProperties apiProperties;
    private ParticipanteAdminService participanteAdminService;
    private PedidoAdminService pedidoAdminService;
    private UbigeoAdminService ubigeoAdminService;
    private UsuarioPruebaAdminService usuarioPruebaAdminService;
    private ApiNetsuiteService apiNetsuiteService;
    private FestividadesAdminService festividadesAdminService;
    private ApiEmailService apiEmailService;
    private ConfiguracionAdminService configuracionAdminService;
    private PromotickResourceService promotickResourceService;
    private ReporteAdminService reporteAdminService;
    private PromotickProperties promotickProperties;
    private ProductoAdminService productoAdminService;

    @Autowired
    public CronAdminServiceImpl(PeriodoAdminService periodoAdminService, ProcesoAdminService procesoAdminService, CargaAdminService cargaAdminService, CargaProcesoAdminService cargaProcesoAdminService, ApiS3Service apiS3Service, ApiSFTPService apiSFTPService, BulkAdminService bulkAdminService, EmailAdminService emailAdminService, ApiProperties apiProperties, ParticipanteAdminService participanteAdminService, PedidoAdminService pedidoAdminService, UbigeoAdminService ubigeoAdminService, UsuarioPruebaAdminService usuarioPruebaAdminService, ApiNetsuiteService apiNetsuiteService, FestividadesAdminService festividadesAdminService, ApiEmailService apiEmailService, ConfiguracionAdminService configuracionAdminService, PromotickResourceService promotickResourceService, ReporteAdminService reporteAdminService, PromotickProperties promotickProperties, ProductoAdminService productoAdminService) {
        this.periodoAdminService = periodoAdminService;
        this.procesoAdminService = procesoAdminService;
        this.cargaAdminService = cargaAdminService;
        this.cargaProcesoAdminService = cargaProcesoAdminService;
        this.apiS3Service = apiS3Service;
        this.apiSFTPService = apiSFTPService;
        this.bulkAdminService = bulkAdminService;
        this.emailAdminService = emailAdminService;
        this.apiProperties = apiProperties;
        this.participanteAdminService = participanteAdminService;
        this.pedidoAdminService = pedidoAdminService;
        this.ubigeoAdminService = ubigeoAdminService;
        this.usuarioPruebaAdminService = usuarioPruebaAdminService;
        this.apiNetsuiteService = apiNetsuiteService;
        this.festividadesAdminService = festividadesAdminService;
        this.apiEmailService = apiEmailService;
        this.configuracionAdminService = configuracionAdminService;
        this.promotickResourceService = promotickResourceService;
        this.reporteAdminService = reporteAdminService;
        this.promotickProperties = promotickProperties;
        this.productoAdminService = productoAdminService;
    }

    @Async
    @Override
    public void initCargaParticipantes() {
        try {
            log.info("Se inicia carga de participantes");

            if (procesoAdminService.procesoEnEjecucion()) {
                throw new Exception("Actualmente hay un proceso en ejecucion, no se ejecutara la 'CARGA DE PARTICIPANTES'");
            }

            this.validacionPeriodo();
            this.procesoArchivo(TipoCargaEnum.CARGA_PARTICIPANTE);

            //emailAdminService.envioEmailBienvenida();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Async
    @Override
    public void initCargaVentas() {
        try {
            log.info("Se inicia carga de facturas");
            if (procesoAdminService.procesoEnEjecucion()) {
                throw new Exception("Actualmente hay un proceso en ejecucion, no se ejecutara la 'CARGA DE FACTURAS'");
            }
            this.validacionPeriodo();
            this.procesoArchivo(TipoCargaEnum.CARGA_VENTAS);

            emailAdminService.envioEmailPuntos(TipoEnvioEmailEnum.EMAIL_CARGA_FACTURAS);

            emailAdminService.envioEmailMetaCumplida();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Async
    @Override
    public void initCierrePeriodo() {
        try {
            log.info("Se inicia cierre de periodo");
            if (procesoAdminService.procesoEnEjecucion()) {
                throw new Exception("Actualmente hay un proceso en ejecucion, no se ejecutara la 'CIERRE DE PERIODO'");
            }

            this.procesamientoCierrePeriodo();

            emailAdminService.envioEmailCierrePeriodo();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Async
    @Override
    public void initVencimiento() {
        try {
            log.info("Se inicia proceso de vencimiento");
            if (procesoAdminService.procesoEnEjecucion()) {
                throw new Exception("Actualmente hay un proceso en ejecucion, no se ejecutara la 'CIERRE DE PERIODO'");
            }

            Integer vencimiento = procesoAdminService.procesoVencimientoPuntos();
            if (vencimiento == null) {
                log.error("Ocurrio un error al vencer lo puntos");
            } else if (vencimiento == -1) {
                log.info("No es fecha de vencimiento");
            } else {
                log.info("Se vencieron " + vencimiento + " registros");

                emailAdminService.envioEmailSistemas("Se vencieron " + vencimiento + " registros en el proceso de vencimiento", "La Fabril - Vencimiento de puntos");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Async
    @Override
    public void initCumpleanos() {
        try {
            log.info("Se inicia proceso de cumpleanos");
            List<Participante> cumpleanos = participanteAdminService.listarCumpleanos();
            for (Participante participante : cumpleanos) {
                emailAdminService.envioEmailCumpleanos(participante);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Async
    @Override
    public void initReenvioNetsuite() {
        try {
            log.info("Se inicia proceso de reenvio de pedidos rebotados a netsuite");
            List<Pedido> pedidos = pedidoAdminService.listarPedidosNetsuiteReenvio();
            for (Pedido pedido : pedidos) {
                try {
                    this.procesamientoReenvioNetsuite(pedido);
                } catch (Exception e) {
                    log.error("Error reenvio: " + e.getMessage());
                    if (pedido.getNetsuiteResponse() == null) {
                        pedido.setNetsuiteResponse(new NetsuiteResponse().setIsSussess(false).setMensaje(e.getMessage()));
                        pedido.setProcesadoNetsuite(false);
                    }
                } finally {
                    pedidoAdminService.actualizarPedidoNetsuite(pedido);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Async
    @Override
    public void initEnvioFestividad() {
        try {
            ConfiguracionWeb configuracionWeb = configuracionAdminService.obtenerConfiguracionWeb();

            List<Festividades> festividades = festividadesAdminService.obtenerFestividades();
            if (festividades != null && !festividades.isEmpty()) {

                List<Participante> list = participanteAdminService.listarParticipantesFestividades();

                if (list != null && !list.isEmpty()) {

                    for (Festividades festividad : festividades) {

                        EmailRequest emailRequest = new EmailRequest()
                                .setFrom(configuracionWeb.getEmailInfo())
                                .setFromName(configuracionWeb.getAliasCorreo())
                                .setPathTemplate(promotickResourceService.mail("festividades.html"))
                                .setSubject(festividad.getNombreFestividad())
                                .setParameters(new Object[] {
                                        promotickResourceService.web("img/festividades/" + festividad.getNombreImagen())
                                });

                        for (Participante participante : list) {
                            emailRequest.setTo(participante.getEmailParticipante());

                            try {
                                AbstractResponse<SengridEmail> response = apiEmailService.sendEmail(emailRequest);
                                log.info("-------------------------------------------------------");
                                log.info("Response EMAIL TO: " + emailRequest.getTo());
                                log.info("Response EMAIL Status: " + response.isStatus());
                                log.info("Response EMAILS Message: " + response.getMessage());
                            } catch (Exception e) {
                                log.error("Error send email", e);
                            }

                        }

                    }

                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Async
    @Override
    public void initEstadoCuenta(boolean test) {
        try {
            log.info("Se inicia proceso de estado de cuenta");
            DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate date = LocalDate.now();
            LocalDate start = date.minusDays(LocalDate.now().getDayOfMonth()-1);
            LocalDate end = date.minusDays(LocalDate.now().getDayOfMonth()).plusMonths(1);
            Integer anio = date.getYear();
            Integer mes = date.getMonthValue();

            Integer anioEstado = mes == 1? anio-1 : anio;

            FiltroEstadoCuenta fec = new FiltroEstadoCuenta();
            fec.setFinicio(start.format(dateTimeFormatter));
            fec.setFfin(end.format(dateTimeFormatter));

            List<Map<String, Object>> estadoCuentaList = new ArrayList<>();
            estadoCuentaList = reporteAdminService.obtenerEstadoCuentaCron(fec, test);

            ConfiguracionWeb configuracionWeb = configuracionAdminService.obtenerConfiguracionWeb();
            String nombreMes = obtenerNombreMes(mes);
            String periodo = nombreMes.substring(0,3) + "_" + anioEstado.toString();
            System.out.println("periodo en consulta: " + periodo);
            String dominio = promotickProperties.getWeb().getMvc().getContext().getRoot();

            if(estadoCuentaList.size()>0){
                for(Map<String, Object> estadoCuenta:estadoCuentaList){
                    Participante participante = participanteAdminService.obtenerParticipantePorNroDocumento(estadoCuenta.get("Nro Documento").toString());
                    List<Producto> detalleProducto = productoAdminService.productoNuevosListar(participante.getCatalogo().getIdCatalogo(), participante.getPuntosDisponibles());
                    int puntosCampaniaEECC = Integer.parseInt(estadoCuenta.get("Acelerador_" + periodo).toString())  + Integer.parseInt(estadoCuenta.get("Campaña_" + periodo).toString());

                    EmailRequest emailRequest = new EmailRequest()
                            .setFrom(configuracionWeb.getEmailInfo())
                            .setFromName(configuracionWeb.getAliasCorreo())
                            .setPathTemplate(promotickResourceService.mail("estado-cuenta-v4.html"))
                            .setSubject("Estado Cuenta La Fabril")
                            .setParameters(new Object[] {
                                    promotickResourceService.web("img/bg/mail-ec/header.png"),  //0
                                    estadoCuenta.get("Nombre").toString(),                          //1
                                    anioEstado.toString(),                                          //2
                                    nombreMes,                                                      //3
                                    estadoCuenta.get("Acreditado_" + periodo).toString(),           //4
                                    puntosCampaniaEECC,           //5
                                    estadoCuenta.get("Canjeado_" + periodo).toString(),             //6
                                    estadoCuenta.get("Disponibles").toString(),                     //7
                                    this.construirProductos(detalleProducto),                       //8
                                    promotickResourceService.web("img/bg/mail-ec/bg-naranja.png"),  //9
                                    promotickResourceService.web("img/bg/mail-ec/bg-celeste.png"),  //10
                                    promotickResourceService.web("img/bg/mail-ec/bg-morado.png"),  //11
                                    promotickResourceService.web("img/bg/mail-ec/bg-azul.png"),  //12
                                    anio.toString(),                                                //13
                                    promotickResourceService.web("img/bg/mail-ec/bg-eecc.jpeg"),  //14
                                    dominio,                                                        //15
                                    promotickResourceService.web("img/bg/mail-ec/pts-acred.png"), //16
                                    promotickResourceService.web("img/bg/mail-ec/pts-canjeados.png"), //17
                                    promotickResourceService.web("img/bg/mail-ec/total-pts.png"), //18
                                    promotickResourceService.web("img/bg/mail-ec/logo.png"),  //19

                            });

                    emailRequest.setTo(estadoCuenta.get("Email").toString());

                    try {

                        emailRequest.generateHtml();

                        AbstractResponse<SengridEmail> response = apiEmailService.sendEmail(emailRequest);
                        log.info("-------------------------------------------------------");
                        log.info("Response EMAIL TO: " + emailRequest.getTo());
                        log.info("Response EMAIL Status: " + response.isStatus());
                        log.info("Response EMAILS Message: " + response.getMessage());
                    } catch (Exception e) {
                        log.error("Error send email", e);
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void procesoArchivo(TipoCargaEnum tipoCargaEnum) {
        Proceso proceso = null;
        try {

            proceso = this.registroProceso(tipoCargaEnum.getTipoProcesoEnum());

            List<ExternalResult> archivoPendientes = this.getFilesSFTP(tipoCargaEnum);
            for (ExternalResult externalResult : archivoPendientes) {

                CargaProceso cargaProceso = new CargaProceso()
                        .setProceso(proceso)
                        .setMensaje("Correcto")
                        .setEstadoCargaProceso(1);

                boolean envioEmailResumen = Boolean.TRUE;

                try {
                    cargaProceso = this.registroCargaProceso(cargaProceso);

                    if (cargaAdminService.existenciaCarga(externalResult.getFullName())) {
                        envioEmailResumen = Boolean.FALSE;
                        throw new Exception("El archivo " + externalResult.getFullName() + " ya fue cargado anteriormente");
                    }

                    FileMetadata cargaArchivo = this.envioS3(externalResult.getFullName(), proceso.getIdProceso(), tipoCargaEnum, externalResult.getFile());

                    Carga carga = new Carga()
                            .setIdResource(cargaArchivo.getIdResource())
                            .setTipoCarga(new TipoCarga().setIdTipoCarga(tipoCargaEnum.getId()))
                            .setBucket(cargaArchivo.getBucket())
                            .setNombreArchivo(cargaArchivo.getFullName())
                            .setFolderArchivo(tipoCargaEnum.getFolder());

                    carga = this.registroCarga(carga);
                    cargaProceso.setCarga(carga);

                    this.actualizarCargaProceso(cargaProceso);

                    switch (tipoCargaEnum) {
                        case CARGA_PARTICIPANTE:
                            this.procesamientoParticipantes(tipoCargaEnum, externalResult, cargaProceso);
                            break;
                        case CARGA_VENTAS:
                            this.procesamientoFacturas(tipoCargaEnum, externalResult, cargaProceso);
                            break;
                    }
                } catch (CronException e) {
                    log.error("CronException carga procesamiento archivo", e);
                    cargaProceso.setMensaje(e.getMessage());
                    cargaProceso.setEstadoCargaProceso(-1);

                    //Generacion de archivo de error / envio email
                    ExcelBuilder.Builder excelBuilder = null;
                    switch (e.getTipoCargaEnum()) {
                        case CARGA_PARTICIPANTE:

                            excelBuilder = ExcelBuilder.getInstance(FormBulkParticipantes.class)
                                    .setList(e.getFormBulkParticipantesErrores())
                                    .setPath(apiProperties.getClient().getDirTemporal());

                            break;
                        case CARGA_VENTAS:

                            excelBuilder = ExcelBuilder.getInstance(FormBulkFacturas.class)
                                    .setList(e.getFormBulkFacturasErrores())
                                    .setPath(apiProperties.getClient().getDirTemporal());

                            break;
                    }
                    if (excelBuilder != null) {
                        File file = excelBuilder.buildFile();
                        if (!file.exists()) {
                            log.error("No se pudo crear el archivo de rebote");
                        } else {
                            emailAdminService.envioEmailRebote(e, file, cargaProceso);
                        }
                    }
                } catch (Exception e) {
                    log.error("Exception carga procesamiento archivo", e);
                    cargaProceso.setMensaje(e.getMessage());
                    cargaProceso.setEstadoCargaProceso(-1);
                    emailAdminService.envioEmailError(e, null, null);
                } finally {
                    if (cargaProceso.getIdCargaProceso() != null) {
                        this.actualizarCargaProceso(cargaProceso);
                        if (envioEmailResumen) {
                            emailAdminService.envioEmailResumen(cargaProceso);
                        }
                    }
                }
            }

            proceso.setEstadoProceso(1);
            proceso.setMensaje("Correcto");
        } catch (Exception e) {
            e.printStackTrace();
            if (proceso != null) {
                proceso.setEstadoProceso(-1);
                proceso.setMensaje(e.getMessage());
            }
            emailAdminService.envioEmailError(e, null, null);
        } finally {
            if (proceso != null) {
                procesoAdminService.finalizarProceso(proceso);
            }
        }
    }

    private void procesamientoParticipantes(TipoCargaEnum tipoCargaEnum, ExternalResult externalResult, CargaProceso cargaProceso) throws Exception {
        FileValidatorResult<FormBulkParticipantes, FormBulkParticipantes> result = bulkAdminService.procesarCargaParticipantes(externalResult.getPathFile(), cargaProceso);
        cargaProceso.setTotal(result.getTotalCountRows());

        if (result.getErrorCountRows() > 0) {
            cargaProceso.setCorrectos(result.getSuccessfulCountRows());
            cargaProceso.setErrores(result.getErrorCountRows());
            throw new CronException("Hubo " + result.getErrorCountRows() + " errores al leer el archivo ('" + externalResult.getFullName() + "'), el archivo no proceso ningun registro por seguridad")
                    .setFormBulkParticipantesErrores(result.errorRows)
                    .setTipoCargaEnum(tipoCargaEnum);
        }

        List<FormBulkParticipantes> procesar = cargaProcesoAdminService.bulkParticipantesProcesar(cargaProceso);
        cargaProceso.setCorrectos(result.getTotalCountRows() - procesar.size());
        cargaProceso.setErrores(procesar.size());

        if (!procesar.isEmpty()) {
            throw new CronException("Se encontraron errores en el procesamiento del archivo: " + externalResult.getFullName())
                    .setFormBulkParticipantesErrores(procesar)
                    .setTipoCargaEnum(tipoCargaEnum);
        }
    }

    private void procesamientoFacturas(TipoCargaEnum tipoCargaEnum, ExternalResult externalResult, CargaProceso cargaProceso) throws Exception {
        FileValidatorResult<FormBulkFacturas, FormBulkFacturas> result = bulkAdminService.procesarCargaFacturas(externalResult.getPathFile(), cargaProceso);
        cargaProceso.setTotal(result.getTotalCountRows());

        if (result.getErrorCountRows() > 0) {
            cargaProceso.setCorrectos(result.getSuccessfulCountRows());
            cargaProceso.setErrores(result.getErrorCountRows());
            throw new CronException("Hubo " + result.getErrorCountRows() + " errores al leer el archivo ('" + externalResult.getFullName() + "'), el archivo no proceso ningun registro por seguridad")
                    .setFormBulkFacturasErrores(result.errorRows)
                    .setTipoCargaEnum(tipoCargaEnum);
        }

        List<FormBulkFacturas> procesar = cargaProcesoAdminService.bulkFacturasProcesar(cargaProceso);
        cargaProceso.setCorrectos(result.getTotalCountRows() - procesar.size());
        cargaProceso.setErrores(procesar.size());

        if (!procesar.isEmpty()) {
            throw new CronException("Se encontraron los siguientes errores en el procesamiento del archivo: " + externalResult.getFullName())
                    .setFormBulkFacturasErrores(procesar)
                    .setTipoCargaEnum(tipoCargaEnum);
        }
    }

    private void procesamientoCierrePeriodo() {
        Proceso proceso = null;
        try {
            proceso = this.registroProceso(TipoProcesoEnum.CIERRE_PERIODO);

            List<PeriodoParticipante> periodoParticipantesError = periodoAdminService.cierrePeriodo();

            if (!periodoParticipantesError.isEmpty()) {
                ExcelBuilder.Builder excelBuilder = ExcelBuilder.getInstance(FormCierrePeriodo.class)
                        .setList(FormCierrePeriodo.getEntities(periodoParticipantesError))
                        .setPath(apiProperties.getClient().getDirTemporal());

                File file = excelBuilder.buildFile();
                if (file.exists()) {
                    emailAdminService.envioEmailError(new Exception("Error al cierre de periodo"), file, "Detalle cierre de periodo");
                } else {
                    log.error("No se pudo crear el archivo detalle de error");
                }
            }

            proceso.setEstadoProceso(1);
            proceso.setMensaje("Correcto");
        } catch (Exception e) {
            e.printStackTrace();
            if (proceso != null) {
                proceso.setEstadoProceso(-1);
                proceso.setMensaje(e.getMessage());
            }
        } finally {
            if (proceso != null) {
                procesoAdminService.finalizarProceso(proceso);
            }
        }
    }

    private void procesamientoReenvioNetsuite(Pedido pedido) throws Exception{

        Ubigeo ubigeo = ubigeoAdminService.obtenerUbigeoID(pedido.getDireccion().getUbigeo().getIdUbigeo());
        if (ubigeo == null) {
            throw new Exception("No se pudo extraer la informacion del ubigeo");
        }
        pedido.getDireccion().setUbigeo(ubigeo);

        if (!usuarioPruebaAdminService.usuariosPrueba().contains(pedido.getParticipante().getNroDocumento())) {

            AbstractResponse<NetsuiteResponse> response = apiNetsuiteService.registerNetsuite(pedido.parseNetsuiteRequest());

            if (!response.isStatus()) {
                throw new Exception(response.getMessage());
            }

            NetsuiteResponse netsuiteResponse = response.getData();
            pedido.setNetsuiteResponse(netsuiteResponse);

            if (netsuiteResponse != null) {
                NetsuiteResponse.Error pedidoDuplicado = null;

                if (netsuiteResponse.getErrorList() != null) {
                    pedidoDuplicado = netsuiteResponse.getErrorList().stream()
                            .filter(err -> err.getCodigo().equals(ConstantesCommon.CODIGO_PEDIDO_DUPLICADO)) // Pedido duplicado
                            .findAny()
                            .orElse(null);
                }

                if (pedidoDuplicado == null && !netsuiteResponse.getIsSussess()) {
                    pedido.setProcesadoNetsuite(false);
                    throw new Exception(netsuiteResponse.getMensaje());
                }

                pedido.setProcesadoNetsuite(true);
            }

        } else {
            pedido.setProcesadoNetsuite(true);
            pedido.setNetsuiteResponse(new NetsuiteResponse().setIsSussess(true).setMensaje("Pedido de prueba, no se envia a netsuite"));
            throw new Exception(pedido.getNetsuiteResponse().getMensaje());
        }
    }

    private void validacionPeriodo() throws Exception {
        Integer validacionPeriodo = periodoAdminService.validacionPeriodo();
        if (validacionPeriodo == null || validacionPeriodo <= 0) {
            throw new Exception("El periodo no pudo ser creado");
        }
    }

    private Proceso registroProceso(TipoProcesoEnum tipoProcesoEnum) throws Exception {
        Integer registro = procesoAdminService.registrarProceso(tipoProcesoEnum.name());
        if (registro == null || registro <= 0) {
            throw new Exception("No se pudo registrar el proceso");
        }
        return new Proceso().setIdProceso(registro);
    }

    private Carga registroCarga(Carga carga) throws Exception {
        Integer registro = cargaAdminService.registroCarga(carga);
        if (registro == null || registro <= 0) {
            throw new Exception("No se pudo registrar la carga");
        }
        return carga.setIdCarga(registro);
    }

    private CargaProceso registroCargaProceso(CargaProceso cargaProceso) throws Exception {
        Integer registro = cargaProcesoAdminService.registrarCargaProceso(cargaProceso);
        if (registro == null || registro <= 0) {
            throw new Exception("No se pudo registrar carga proceso");
        }
        return cargaProceso.setIdCargaProceso(registro);
    }

    private void actualizarCargaProceso(CargaProceso cargaProceso) throws Exception {
        Integer registro = cargaProcesoAdminService.actualizarCargaProceso(cargaProceso);
        if (registro == null || registro <= 0) {
            throw new Exception("No se pudo actualizar carga proceso");
        }
    }

    private FileMetadata envioS3(String nombre, Integer idProceso, TipoCargaEnum tipoCargaEnum, File file) throws Exception {
        S3UploadRequest s3UploadRequest = new S3UploadRequest()
                .setKey(nombre)
                .setFolderName(tipoCargaEnum.getFolder())
                .setPublic(Boolean.FALSE)
                .setEntity(idProceso)
                .setType(tipoCargaEnum.name())
                .setFile(file);

        AbstractResponse<FileMetadata> resultUploadS3 = apiS3Service.uploadFileS3(s3UploadRequest);

        if (!resultUploadS3.isStatus()) {
            throw new Exception(String.format("%s: %s(%s)", tipoCargaEnum.name(), "S3ApiServer", resultUploadS3.getMessage()));
        }
        return resultUploadS3.getData();
    }

    private List<ExternalResult> getFilesSFTP(TipoCargaEnum tipoCargaEnum) throws Exception {
        AbstractResponse<List<ExternalResult>> result = apiSFTPService.getFiles(true, tipoCargaEnum.getPathInput(), tipoCargaEnum.getPathProcesado());
        if (!result.isStatus()) {
            throw new Exception("No se pudo extraer los archivos del SFTP");
        }
        return result.getData();
    }

    private String obtenerNombreMes(Integer numeroMes){
        String nombreMes = "";
        switch (numeroMes){
            case 1 : nombreMes = "Enero"; break;
            case 2 : nombreMes = "Febrero"; break;
            case 3 : nombreMes = "Marzo"; break;
            case 4 : nombreMes = "Abril"; break;
            case 5 : nombreMes = "Mayo"; break;
            case 6 : nombreMes = "Junio"; break;
            case 7 : nombreMes = "Julio"; break;
            case 8 : nombreMes = "Agosto"; break;
            case 9 : nombreMes = "Setiembre"; break;
            case 10 : nombreMes = "Octubre"; break;
            case 11 : nombreMes = "Noviembre"; break;
            case 12 : nombreMes = "Diciembre"; break;
        }
        return nombreMes;
    }

    private String construirProductos(List<Producto> productos) {
        StringBuilder stringBuilder = new StringBuilder();
        String container = "<div style='display: flex;justify-content: center; margin-top: 20px;'>";
        Integer limit = 150;
        int pos = 0;
        for (Producto producto : productos) {
            String formattedDescripcion = "";
            if(StringUtils.isNotEmpty(producto.getDescripcionProducto())){
                if(producto.getDescripcionProducto().length() > limit){
                    formattedDescripcion = producto.getDescripcionProducto().substring(0,limit) + "...";
                }
            }
            stringBuilder.append(String.format("%s",(pos % 2 == 0 || pos == 0 ? container : "")))
                    .append("<div style='width: 50%; padding: 0 10px;'>")
                    .append("<table><tr><td>")
                    .append(String.format("<img src='%s' alt='' style='width:120px;height:120px'>", promotickResourceService.web("img/productos/" + producto.getImagenUno())))
                    .append("</td><td>")
                    .append(String.format("<p style='margin: 5px'>%s</p>",producto.getNombreProducto()))
                    .append(String.format("<p style='margin: 5px;font-weight: 600'>%s puntos</p>",producto.getPuntosProducto()))
                    .append(String.format("<p style='font-size: 11px;margin: 5px'>%s</p>",formattedDescripcion))
                    .append("</td></tr></table></div>")
                    .append(String.format("%s",(pos % 2 != 0 || pos + 1 >= productos.size() ? "</div>" : "")));

            pos ++;
        }
        return stringBuilder.toString();
    }

    @Override
    public void initRestaurarProductos() {
        procesoAdminService.initRestaurarProductos();
    }

}
