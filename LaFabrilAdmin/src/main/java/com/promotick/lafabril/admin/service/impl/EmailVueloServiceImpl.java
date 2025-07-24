package com.promotick.lafabril.admin.service.impl;

import com.promotick.apiclient.model.request.EmailRequest;
import com.promotick.apiclient.service.ApiEmailService;
import com.promotick.lafabril.admin.service.*;
import com.promotick.configuration.services.PromotickResourceService;
import com.promotick.lafabril.model.admin.UsuarioPrueba;
import com.promotick.lafabril.model.configuracion.ConfiguracionWeb;
import com.promotick.lafabril.model.web.Direccion;
import com.promotick.lafabril.model.web.Pedido;
import com.promotick.lafabril.model.web.PedidoDetalle;
import com.promotick.lafabril.admin.service.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Slf4j
@Service
public class EmailVueloServiceImpl implements EmailVueloService {

    private PedidoAdminService pedidoAdminService;
    private ConfiguracionAdminService configuracionAdminService;
    private PromotickResourceService promotickResourceService;
    private ParticipanteAdminService participanteAdminService;
    private UsuarioPruebaAdminService usuarioPruebaAdminService;
    private ApiEmailService apiEmailService;
    private UbigeoAdminService ubigeoAdminService;

    @Autowired
    public EmailVueloServiceImpl(PedidoAdminService pedidoAdminService, ConfiguracionAdminService configuracionAdminService, PromotickResourceService promotickResourceService, ParticipanteAdminService participanteAdminService, UsuarioPruebaAdminService usuarioPruebaAdminService, ApiEmailService apiEmailService, UbigeoAdminService ubigeoAdminService) {
        this.pedidoAdminService = pedidoAdminService;
        this.configuracionAdminService = configuracionAdminService;
        this.promotickResourceService = promotickResourceService;
        this.participanteAdminService = participanteAdminService;
        this.usuarioPruebaAdminService = usuarioPruebaAdminService;
        this.apiEmailService = apiEmailService;
        this.ubigeoAdminService = ubigeoAdminService;
    }

    @Override
    public void enviarEmailVuelo(Integer idPedido) {
        try {
            Pedido pedido = pedidoAdminService.listarPedidoById(idPedido);
            if (pedido != null) {
                pedido.setPedidoDetalles(pedidoAdminService.listarDetallePedido(idPedido));
                //pedido.setParticipante(participanteAdminService.obtenerParticipante(pedido.getNroDocumentoPedido()));
                pedido.getDireccion().setUbigeo(ubigeoAdminService.obtenerUbigeoID(pedido.getDireccion().getUbigeo().getIdUbigeo()));

                ConfiguracionWeb configuracionWeb = configuracionAdminService.obtenerConfiguracionWeb();
                String correoDestino = pedido.getEmailPedido();
                String pathAbsoluteHtml = promotickResourceService.mail("gracias_compra.html");
                String imgBanner = promotickResourceService.web("img/" + pedido.getParticipante().getCatalogo().getLogoSegmento());
                String imgLogo = promotickResourceService.web("img/logo2.png");
                String direccion = this.formatoDireccion(pedido.getDireccion());
                String total = pedido.getPuntosTotal().toString();
                String htmlPedidoDetalles = this.obtenerDetalles(pedido.getPedidoDetalles());
                String fecha = this.fechaEnTexto(new Date());

                Object[] data = {
                        imgBanner, //0
                        imgLogo, //1
                        direccion, //2
                        htmlPedidoDetalles, //3
                        total, //4
                        fecha, //5
                        pedido.getIdPedido(), //6
                        pedido.getNombrePedido() + " " + pedido.getApellidoPaternoPedido() + " " + pedido.getApellidoMaternoPedido(), //7
                        pedido.getParticipante().getNroDocumento(), //8
                        (pedido.getTelefonoPedido() == null ? pedido.getMovilPedido() : pedido.getTelefonoPedido()), // 9
                        pedido.getEmailPedido(), //10
                        configuracionWeb.getTelefonoContacto(), //11
                        configuracionWeb.getEmailContacto() //12

                };

                EmailRequest emailRequest = new EmailRequest();
                emailRequest.setTo(correoDestino);
                emailRequest.setFrom(configuracionWeb.getEmailInfo());
                emailRequest.setFromName(configuracionWeb.getAliasCorreo());
                emailRequest.setSubject(configuracionWeb.getAsuntoCanje() + " - " + pedido.getParticipante().getCatalogo().getNombreCatalogo());
                emailRequest.setPathTemplate(pathAbsoluteHtml);
                emailRequest.setParameters(data);
                emailRequest.generateHtml();

                if (!this.esUsuarioPrueba(usuarioPruebaAdminService.listarUsuariosPrueba(null, null), pedido.getParticipante().getIdParticipante())) {
                    if (configuracionWeb.getCorreosCopiaOculta() != null) {
                        String[] correosCopiaOculta = (configuracionWeb.getCorreosCopiaOculta()).split("\\|");
                        for (String bbc : correosCopiaOculta) {
                            emailRequest.addBcc(bbc);
                        }
                    }
                } else {
                    log.info("Usuario de prueba: Solo se envia al correo del pedido: " + correoDestino);
                }

                log.info("SEND EMAIL TO: " + emailRequest.getTo());
                apiEmailService.sendEmailAsync(emailRequest);
            }
        } catch (Exception e) {
            log.error("Error envio de email vuelo", e);
        }
    }

    private boolean esUsuarioPrueba(List<UsuarioPrueba> list, Integer idParticipante) {
        for (UsuarioPrueba usuarioPrueba : list) {
            if (usuarioPrueba.getParticipante().getIdParticipante().intValue() == idParticipante) {
                return true;
            }
        }
        return false;
    }

    private String formatoDireccion(Direccion direccion) {
        String txtDireccion = "";
        try {
            txtDireccion = direccion.getZona().getNombreZona() + " "
                    + direccion.getDireccionCalle() + " "
                    + direccion.getTipoVivienda().getNombreTipoVivienda() + "<br>"
                    + " Referencia: " + direccion.getReferencia() + "<br>"
                    + direccion.getUbigeo().getDistrito() + " - " + direccion.getUbigeo().getProvincia() + " - " + direccion.getUbigeo().getDepartamento();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return txtDireccion;
    }

    private String obtenerDetalles(List<PedidoDetalle> detalles) {
        StringBuilder sb = new StringBuilder();

        for (PedidoDetalle pedidoDetalle : detalles) {

            String imagenDetalle = promotickResourceService.web("img/productos/" + pedidoDetalle.getProducto().getImagenUno());
            String cantidad = pedidoDetalle.getCantidad().toString();
            String subTotal = pedidoDetalle.getPuntosTotal().toString();

            sb.append("<tr>")
                    .append(this.getTD(this.getP("<img src='" + imagenDetalle + "' alt='' width='65px' height='71px'>")))
                    .append(this.getTD(this.obtenerDetalleNombre(pedidoDetalle)))
                    .append(this.getTD(this.getP(cantidad)))
                    .append(this.getTD(this.getP(subTotal + " Pts.")))
                    .append("</tr>");
        }
        return sb.toString();
    }

    private String getP(String content) {
        return "<p style='text-align:center; font-size:16px;color:#000;font-family:Lato, sans-serif;line-height: 20px;margin-top:15px !important; margin-bottom:0px !important;font-weight:400;'>" + content + "</p>";
    }

    private String getTD(String content) {
        return "<td style='padding:10px 0; border-bottom: 1px solid #E5E5E4;'>" + content + "</td>";
    }

    private String obtenerDetalleNombre(PedidoDetalle pedidoDetalle) {
        return this.getP(pedidoDetalle.getNombreTemporal());
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
