package com.promotick.lafabril.web.controller;

import com.ibm.icu.text.SimpleDateFormat;
import com.ibm.icu.util.Calendar;
import com.promotick.apiclient.model.request.EmailRequest;
import com.promotick.apiclient.service.ApiEmailService;
import com.promotick.lafabril.model.web.Pasajero;
import com.promotick.lafabril.web.service.ConfiguracionWebService;
import com.promotick.lafabril.web.service.CotizacionWebService;
import com.promotick.lafabril.web.util.BaseController;
import com.promotick.lafabril.web.util.ConstantesWebView;
import com.promotick.configuration.services.PromotickResourceService;
import com.promotick.lafabril.model.configuracion.ConfiguracionWeb;
import com.promotick.lafabril.model.util.PromotickResult;
import com.promotick.lafabril.model.web.Cotizacion;
import com.promotick.lafabril.model.web.Participante;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("cotizacion")
public class CotizacionController extends BaseController {

    private CotizacionWebService cotizacionWebService;
    private ApiEmailService apiEmailService;
    private PromotickResourceService promotickResourceService;
    private ConfiguracionWebService configuracionWebService;

    @Autowired
    public CotizacionController(CotizacionWebService cotizacionWebService, ApiEmailService apiEmailService, PromotickResourceService promotickResourceService, ConfiguracionWebService configuracionWebService) {
        this.cotizacionWebService = cotizacionWebService;
        this.apiEmailService = apiEmailService;
        this.promotickResourceService = promotickResourceService;
        this.configuracionWebService = configuracionWebService;
    }

    @GetMapping
    public String init(Model model) {
        Date hoy = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(hoy);
        calendar.add(Calendar.DATE, 2);
        hoy = calendar.getTime();

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String fechaMinima = sdf.format(hoy);
        model.addAttribute("fechaMinima", fechaMinima);

        return ConstantesWebView.VIEW_COTIZACION;
    }


    @ResponseBody
    @PostMapping(value = "registrarCotizacion")
    public PromotickResult RegistrarCotizacion(@RequestBody Cotizacion cotizacion, PromotickResult promotickResult, ConfiguracionWeb configuracionWeb, Participante participante) {
        try {
            Integer registro = cotizacionWebService.registrarCotizacion(cotizacion);
            if (registro == null || registro <= 0) {
                throw new Exception("No se pudo registrar la cotizacion");
            }
            promotickResult.setMessage("Se envio la cotizacion exitosamente");
            this.enviarEmail(cotizacion, configuracionWeb, participante);
        } catch (Exception e) {
            promotickResult.setException(e);
        }
        return promotickResult;
    }

    private void enviarEmail(Cotizacion cotizacion, ConfiguracionWeb configuracionWeb, Participante participante) {
        try {

            String imgLogo = promotickResourceService.web("img/logo2.png");
            String imgBanner = promotickResourceService.web("img/header-viajes.png");
            String iconoDestino = promotickResourceService.web("img/destino.png");
            String iconoFechas = promotickResourceService.web("img/calendario.png");
            String iconoPasajeros = promotickResourceService.web("img/grupo-de-personas.png");

            String htmlPasajeros = "";

            for (Pasajero pasajero : cotizacion.getListaPasajeros()) {
                htmlPasajeros += "<p style=\"color:#484454;font-weight: 300;margin:0 0 10px\"><span style=\"color:#767A7C;font-weight:400;\">Nombres: </span> "+pasajero.getNombrePasajero()+"</p>";
                htmlPasajeros += "<p style=\"color:#484454;font-weight: 300;margin:0 0 10px\"><span style=\"color:#767A7C;font-weight:400;\">Apellidos: </span> "+pasajero.getApellidoPasajero()+"</p>";
                htmlPasajeros += "<p style=\"color:#484454;font-weight: 300;margin:0 0 10px\"><span style=\"color:#767A7C;font-weight:400;\">N° Documento: </span> "+pasajero.getNroDocumento()+"</p>";
                htmlPasajeros += "<p style=\"color:#484454;font-weight: 300;margin:0 0 10px\"><span style=\"color:#767A7C;font-weight:400;\">Fecha de nacimiento: </span> "+pasajero.getFechaNacimiento()+"</p>";
                htmlPasajeros += "<p style=\"color:#484454;font-weight: 300;margin:0 0 10px\"><span style=\"color:#767A7C;font-weight:400;\">Personas mayores (+65): </span> "+(pasajero.getTerceraEdad()==1?"Si":"No")+"</p>";
                htmlPasajeros += "<p style=\"color:#484454;font-weight: 300;margin:0 0 10px\"><span style=\"color:#767A7C;font-weight:400;\">Persona con discapacidad: </span> "+(pasajero.getDiscapacitado()==1?"Si":"No")+"</p>";
                htmlPasajeros += "<hr>";
            }

            Object[] data = {
                    imgBanner, //0
                    imgLogo, //1

                    participante.getNombresParticipante() + " " + participante.getAppaternoParticipante() + " " + participante.getApmaternoParticipante(), //2
                    participante.getNroDocumento(), //3
                    cotizacion.getCorreo(), //4
                    cotizacion.getTelefono(), //5

                    cotizacion.getOrigen(), //6
                    cotizacion.getDestino(), //7
                    cotizacion.getClase(), //8

                    cotizacion.getFechaIda(), //9
                    (cotizacion.getFechaVuelta().equals("") ? "NO APLICA" : cotizacion.getFechaVuelta()), //10

                    htmlPasajeros, //11

                    iconoDestino, //12
                    iconoFechas, //13
                    iconoPasajeros //14

            };

            EmailRequest emailRequest = new EmailRequest()
                    .setFrom(configuracionWeb.getEmailInfo())
                    .setTo(cotizacion.getCorreo())
                    .setFromName(configuracionWeb.getAliasCorreo())
                    .setSubject("Cotizacion de viaje")
                    .addBcc(configuracionWeb.getEmailCotizacion())
                    .setPathTemplate(promotickResourceService.mail("cotizacion-mail-v2.html"))
                    .setParameters(data);

            apiEmailService.sendEmailAsync(emailRequest);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @GetMapping("viewPasajerosCotizacion/{npasajeros}")
    public String viewPasajerosCotizacion(@PathVariable Integer npasajeros, Participante participante, Model model) {
        List <Pasajero> pasajeros = new ArrayList<>();
        int i = 1;
        while (npasajeros >= 1){
            pasajeros.add(new Pasajero().setIdPasajero(i));
            npasajeros--;
            i++;
        }
        model.addAttribute("pasajeros", pasajeros);
        model.addAttribute("tipodocumentos", configuracionWebService.obtenerTipoDocumentos());
        return ConstantesWebView.VIEW_FRAGMENTS_COTIZACION_PASAJEROS;
    }
}
