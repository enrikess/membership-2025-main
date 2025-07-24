package com.promotick.lafabril.web.controller;

import com.promotick.apiclient.model.AbstractResponse;
import com.promotick.apiclient.model.request.EmailRequest;
import com.promotick.apiclient.model.response.SengridEmail;
import com.promotick.apiclient.service.ApiEmailService;
import com.promotick.lafabril.common.web.ConstantesWebMessage;
import com.promotick.lafabril.model.configuracion.ConfiguracionWeb;
import com.promotick.lafabril.model.util.Auditoria;
import com.promotick.lafabril.model.util.PromotickResult;
import com.promotick.lafabril.model.util.Util;
import com.promotick.lafabril.model.web.Contacto;
import com.promotick.lafabril.model.web.Participante;
import com.promotick.lafabril.web.service.ContactoWebService;
import com.promotick.lafabril.web.util.BaseController;
import com.promotick.lafabril.web.util.ConstantesWebView;
import com.promotick.configuration.services.PromotickResourceService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


@Slf4j
@Controller
@RequestMapping("contacto")
public class ContactoController extends BaseController {

    private ContactoWebService contactoWebService;
    private ApiEmailService apiEmailService;
    private PromotickResourceService promotickResourceService;

    public ContactoController(ContactoWebService contactoWebService, ApiEmailService apiEmailService, PromotickResourceService promotickResourceService) {
        this.contactoWebService = contactoWebService;
        this.apiEmailService = apiEmailService;
        this.promotickResourceService = promotickResourceService;
    }

    @GetMapping
    public String inicio() {
        return ConstantesWebView.VIEW_CONTACTO;
    }

    @ResponseBody
    @PostMapping(value = "guardarContacto")
    public PromotickResult registroContacto(@RequestBody Contacto contacto, Auditoria auditoria, PromotickResult promotickResult, ConfiguracionWeb configuracionWeb, Participante participante) {
        try {
            contacto.setAuditoria(auditoria);
            contacto.setNombres(participante.getNombresParticipante());
            contacto.setApellidos(participante.getAppaternoParticipante()+" "+participante.getApmaternoParticipante());
            contacto.setNroDocumento(participante.getNroDocumento());
            Integer result = contactoWebService.guardarContacto(contacto);
            if (result > 0) {
                enviarEmailContacto(contacto, configuracionWeb);
                promotickResult.setMessage(Util.getMessage(messageSource, ConstantesWebMessage.MSG_CONTACTO_REGISTRAR_EXITO, null));
            } else {
                throw new Exception(Util.getMessage(messageSource, ConstantesWebMessage.MSG_CONTACTO_REGISTRAR_ERROR));
            }
        } catch (Exception e) {
            promotickResult.setException(e);
        }
        return promotickResult;
    }

    private void enviarEmailContacto(Contacto contacto, ConfiguracionWeb configuracionWeb) throws Exception {
        String pathAbsoluteHtml = promotickResourceService.mail("contacto.html");
        String nombres = contacto.getNombres();
        String apellidos = contacto.getApellidos();
        Object[] data = {
                nombres,
                apellidos,
                "Mensaje: " + contacto.getMensaje(),
                "Email: " + contacto.getEmail(),
                "Telefono: " + contacto.getTelefono(),
                "Celular: " + contacto.getCelular(),
                "Nro. de Documento: "+ contacto.getNroDocumento()
        };

        String destino = configuracionWeb.getDestinatarioContactos();
        String alias = configuracionWeb.getAliasContacto();
        String[] correos = null;
        if (configuracionWeb.getCorreosContactos() != null) {
            correos = (configuracionWeb.getCorreosContactos()).split("\\|");
            log.info("configuracionWeb.getCorreosCopiaOculta()" + configuracionWeb.getCorreosContactos());
        }

        this.enviarCorreo(pathAbsoluteHtml, data, destino, configuracionWeb.getEmailInfo(), alias, correos);
    }

    private void enviarCorreo(String pathAbsoluteHtml, Object[] data, String destino, String fromName, String aliasContacto, String[] correos) throws Exception {
        EmailRequest emailRequest = new EmailRequest();
        emailRequest.setTo(destino);
        emailRequest.setFrom(fromName);
        emailRequest.setFromName(aliasContacto);
        emailRequest.setSubject("Formulario de Contacto");
        emailRequest.setPathTemplate(pathAbsoluteHtml);
        emailRequest.setParameters(data);
        emailRequest.setFile(null);

        if (correos != null) {
            for (String bcc : correos) {
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

}
