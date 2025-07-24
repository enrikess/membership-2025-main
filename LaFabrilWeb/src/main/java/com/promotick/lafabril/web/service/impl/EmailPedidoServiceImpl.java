package com.promotick.lafabril.web.service.impl;

import com.promotick.apiclient.model.request.EmailRequest;
import com.promotick.apiclient.service.ApiEmailService;
import com.promotick.lafabril.web.service.EmailPedidoService;
import com.promotick.lafabril.web.service.UsuarioPruebaWebService;
import com.promotick.lafabril.common.ConstantesSesion;
import com.promotick.lafabril.dao.web.ConfiguracionDao;
import com.promotick.lafabril.model.admin.UsuarioPrueba;
import com.promotick.lafabril.model.configuracion.ConfiguracionWeb;
import com.promotick.lafabril.model.util.Util;
import com.promotick.lafabril.model.web.Direccion;
import com.promotick.lafabril.model.web.Pedido;
import com.promotick.lafabril.model.web.PedidoDetalle;
import com.promotick.configuration.services.PromotickResourceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
public class EmailPedidoServiceImpl implements EmailPedidoService {

    private static final Logger LOGGER = LoggerFactory.getLogger(EmailPedidoServiceImpl.class);
    private ConfiguracionDao configuracionDao;
    private UsuarioPruebaWebService usuarioPruebaWebService;
    private ApiEmailService apiEmailService;
    private PromotickResourceService promotickResourceService;

    @Autowired
    public EmailPedidoServiceImpl(ConfiguracionDao configuracionDao, UsuarioPruebaWebService usuarioPruebaWebService, ApiEmailService apiEmailService, PromotickResourceService promotickResourceService) {
        this.configuracionDao = configuracionDao;
        this.usuarioPruebaWebService = usuarioPruebaWebService;
        this.apiEmailService = apiEmailService;
        this.promotickResourceService = promotickResourceService;
    }

    @Override
    public void enviarEmailPedido(Pedido pedido, Integer puntosDisponibles) throws Exception {
        String correoDestino = pedido.getEmailPedido();

        /* envio mail */
        String pathAbsoluteHtml = promotickResourceService.mail("gracias_compra_v5.html");
        String header = promotickResourceService.web("img/bg/mail-canje/header-c.png");
        String borde = promotickResourceService.web("img/bg/mail-canje/borde.png");
        String tabla = promotickResourceService.web("img/bg/mail-canje/tabla.png");
        String premia = promotickResourceService.web("img/bg/mail-canje/fondo-textura.png");
        String bgfecha = promotickResourceService.web("img/bg/mail-canje/bg-fecha.png");
        String call = promotickResourceService.web("img/bg/mail-canje/call.png");
        String footer = promotickResourceService.web("img/bg/mail-canje/footer-c.png");
        String direccion = this.formatoDireccion(pedido.getDireccion());
        String total = pedido.getPuntosTotal().toString();
        String htmlPedidoDetalles = this.obtenerDetalles(pedido.getPedidoDetalles());
        String fecha = this.fechaEnTexto(new Date());
        ConfiguracionWeb configuracionWeb = configuracionDao.obtenerConfiguracionWeb();
        String logo = promotickResourceService.web("img/bg/mail-canje/logo.png");
        LocalDate date = LocalDate.now();
        String anio = String.valueOf(date.getYear());
        String referencia = pedido.getDireccion().getReferencia();

        Object[] data = {
                header, //0
                logo, //1
                tabla, //2
                direccion, //3
                htmlPedidoDetalles, //4
                total, //5
                fecha, // 6
                pedido.getIdPedido().toString(), //7
                pedido.getEmailPedido(), //8
                pedido.getNombrePedido() + " " + pedido.getApellidoPaternoPedido() + " " + pedido.getApellidoMaternoPedido(), //9
                pedido.getParticipante().getNroDocumento(), //10
                (pedido.getTelefonoPedido() == null ? pedido.getMovilPedido() : pedido.getTelefonoPedido()), //11
                pedido.getEmailPedido(), //12
                premia, //13
                configuracionWeb.getEmailContacto(), //14
                configuracionWeb.getTelefonoContacto(), //15
                logo, //16
                anio, //17
                referencia, //18
        };


        try {

            EmailRequest emailRequest = new EmailRequest();
            emailRequest.setTo(correoDestino);
            emailRequest.setFrom(configuracionWeb.getEmailInfo());
            emailRequest.setFromName(configuracionWeb.getAliasCorreo());
            emailRequest.setSubject(configuracionWeb.getAsuntoCanje());
            emailRequest.setPathTemplate(pathAbsoluteHtml);
            emailRequest.setParameters(data);
            emailRequest.generateHtml();

            Util.getSession().setAttribute(ConstantesSesion.SESSION_IMPRESION, emailRequest.getTemplate());

            if (!this.esUsuarioPrueba(usuarioPruebaWebService.listarUsuariosPrueba(null, null), pedido.getParticipante().getIdParticipante())) {
                if (configuracionWeb.getCorreosCopiaOculta() != null) {
                    String[] correosCopiaOculta = (configuracionWeb.getCorreosCopiaOculta()).split("\\|");
                    for (String bbc : correosCopiaOculta) {
                        emailRequest.addBcc(bbc);
                    }
                }
            } else {
                LOGGER.info("Usuario de prueba: Solo se envia al correo del pedido: " + correoDestino);
            }

            LOGGER.info("SEND EMAIL TO: " + emailRequest.getTo());
            Util.getSession().setAttribute(ConstantesSesion.SESSION_PARTICIPANTE, pedido.getParticipante().setPuntosDisponibles(puntosDisponibles));

            apiEmailService.sendEmailAsync(emailRequest);

        } catch (Exception e) {
            LOGGER.error("Error envio de email", e);
        }

    }

    private String formatoDireccion(Direccion direccion) {
        String txtDireccion = "";
        try {
            txtDireccion = direccion.getZona().getNombreZona() + " "
                    + direccion.getDireccionCalle() + " "
                    + direccion.getTipoVivienda().getNombreTipoVivienda() + "<br>"
//                    + " Referencia: " + direccion.getReferencia() + "<br>"
                    + direccion.getUbigeo().getDistrito() + " - " + direccion.getUbigeo().getProvincia() + " - " + direccion.getUbigeo().getDepartamento();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return txtDireccion;
    }

    private boolean esUsuarioPrueba(List<UsuarioPrueba> list, Integer idParticipante) {
        for (UsuarioPrueba usuarioPrueba : list) {
            if (usuarioPrueba.getParticipante().getIdParticipante().intValue() == idParticipante) {
                return true;
            }
        }
        return false;
    }

    private String obtenerDetalles(List<PedidoDetalle> detalles) {
        StringBuilder sb = new StringBuilder();

        for (PedidoDetalle pedidoDetalle : detalles) {

            String imagenDetalle = promotickResourceService.web("img/productos/" + pedidoDetalle.getProducto().getImagenUno());
            String cantidad = pedidoDetalle.getCantidad().toString();
            String subTotal = pedidoDetalle.getPuntosTotal().toString();

            sb.append("<tr>")
                    .append(this.getTD(this.getP("<img src='" + imagenDetalle + "' alt='' width='65px' height='71px'>", 400)))
                    .append(this.getTD(this.obtenerDetalleNombre(pedidoDetalle)))
                    .append(this.getTD(this.getP(cantidad, 600)))
                    .append(this.getTD(this.getP(subTotal + " Pts.", 600)))
                    .append("</tr>");
        }
        return sb.toString();
    }

    private String obtenerDetalleNombre(PedidoDetalle pedidoDetalle) {
        StringBuilder stringBuilder = new StringBuilder()
                .append(this.getP(pedidoDetalle.getProducto().getNombreProducto(), 400))
                .append(this.getSpan(this.getP("Cod. " + pedidoDetalle.getProducto().getCodigoWeb(), 400)));

        if (pedidoDetalle.getProducto().getTipoProducto().isRecargaCelular()) {
            stringBuilder.append(this.getP("Recarga celular: " + pedidoDetalle.getNroCelular(), 400));
        }
        if (pedidoDetalle.getProducto().getTipoProducto().isRecargaTv()) {
            stringBuilder.append(this.getP("Recarga TV: " + pedidoDetalle.getNroDecodificador(), 400));
        }
        if (pedidoDetalle.getProducto().getTipoProducto().isColores()) {
            stringBuilder.append(this.getP("Color: " + pedidoDetalle.getColor1() + (pedidoDetalle.getColor2() != null ? ", " + pedidoDetalle.getColor2() : ""), 400));
        }
        if (pedidoDetalle.getProducto().getTipoProducto().isCorreo()) {
            stringBuilder.append(this.getP("Correo: " + pedidoDetalle.getCorreo(), 400));
        }
        return stringBuilder.toString();
    }

    private String getP(String content, Integer weight) {
        return "<p style='text-align:center; font-size:14px;color:#000;line-height: 20px;margin-top:0px !important; margin-bottom:0px !important;font-weight:" + weight + ";'>" + content + "</p>";
    }

    private String getTD(String content) {
        return "<td style='padding:10px 0; border-top:1px'>" + content + "</td>";
    }

    private String getSpan(String content) {
        return "<span style='font-size:14px;color:rgb(128,128,128);font-family:Lato,sans-serif;font-weight:400'>" + content + "</span>";
    }

    private String fechaEnTexto(Date d) {
        String fecha = "";
        Calendar c = Calendar.getInstance();
        c.setTime(d);
        fecha += c.get(Calendar.DAY_OF_MONTH) + " de ";
        switch (c.get(Calendar.MONTH)) {
            case Calendar.JANUARY:
                fecha += " Enero ";
                break;
            case Calendar.FEBRUARY:
                fecha += " Febrero ";
                break;
            case Calendar.MARCH:
                fecha += " Marzo ";
                break;
            case Calendar.APRIL:
                fecha += " Abril ";
                break;
            case Calendar.MAY:
                fecha += " Mayo ";
                break;
            case Calendar.JUNE:
                fecha += " Junio ";
                break;
            case Calendar.JULY:
                fecha += " Julio ";
                break;
            case Calendar.AUGUST:
                fecha += " Agosto ";
                break;
            case Calendar.SEPTEMBER:
                fecha += " Septiembre ";
                break;
            case Calendar.OCTOBER:
                fecha += " Octubre ";
                break;
            case Calendar.NOVEMBER:
                fecha += " Noviembre ";
                break;
            case Calendar.DECEMBER:
                fecha += " Diciembre ";
                break;
        }
        fecha += "del " + c.get(Calendar.YEAR);
        return fecha;
    }
}
