package com.promotick.lafabril.web.controller;

import com.promotick.apiclient.model.request.EmailRequest;
import com.promotick.apiclient.service.ApiEmailService;
import com.promotick.lafabril.common.ConstantesApi;
import com.promotick.lafabril.common.ConstantesSesion;
import com.promotick.lafabril.common.UtilCommon;
import com.promotick.lafabril.model.configuracion.ConfiguracionWeb;
import com.promotick.lafabril.model.util.PromotickResult;
import com.promotick.lafabril.model.util.Util;
import com.promotick.lafabril.model.web.Participante;
import com.promotick.lafabril.model.web.Payment;
import com.promotick.lafabril.model.web.Pedido;
import com.promotick.lafabril.model.web.PedidoDetalle;
import com.promotick.lafabril.web.service.ConfiguracionWebService;
import com.promotick.lafabril.web.service.PaqueteService;
import com.promotick.lafabril.web.service.ParticipanteWebService;
import com.promotick.lafabril.web.util.BaseController;
import com.promotick.configuration.services.PromotickResourceService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.UUID;

@Controller
@RequestMapping("payment")
public class PaymentController extends BaseController {

	private PromotickResourceService promotickResourceService;
	private ApiEmailService apiEmailService;
	private ParticipanteWebService participanteWebService;
	private ConfiguracionWebService configuracionWebService;
	private PaqueteService paqueteService;

	private Logger LOGGER = LoggerFactory.getLogger(getClass());

    public PaymentController(PromotickResourceService promotickResourceService, ApiEmailService apiEmailService, ParticipanteWebService participanteWebService, ConfiguracionWebService configuracionWebService, PaqueteService paqueteService){
    	this.promotickResourceService = promotickResourceService;
    	this.apiEmailService = apiEmailService;
    	this.participanteWebService = participanteWebService;
    	this.configuracionWebService = configuracionWebService;
    	this.paqueteService = paqueteService;
	}

	@ResponseBody
	@RequestMapping(value = "getUrlV2", method = RequestMethod.GET)
	public PromotickResult getUrlV2(HttpServletRequest request, Participante participanteSession, PromotickResult promotickResult) {
		try {
			LOGGER.info("#getUrlV2");

			Integer puntosFaltante = obtenerPuntosFaltantes();

			DecimalFormatSymbols otherSymbols = new DecimalFormatSymbols(Locale.getDefault());
			otherSymbols.setDecimalSeparator('.');
			otherSymbols.setGroupingSeparator(',');

			DecimalFormat df = new DecimalFormat("0.00", otherSymbols);
			ConfiguracionWeb configuracionWeb = configuracionWebService.obtenerConfiguracionWeb();

			String application_code = properties.getProperty(ConstantesApi.PASARELA_CODE);
			String uid = participanteSession.getNroDocumento();
			String auth_timestamp = UtilCommon.dateFormat("yyyyMMddhhmmss");
			String dev_reference = UUID.randomUUID().toString();
//			String product_description = obtenerPuntosFaltantes()+"_puntos";
			String product_description = puntosFaltante+"_puntos";
//			String product_code =obtenerPuntosFaltantes().toString();
			String product_code =puntosFaltante.toString();

			Double monto = obtenerMonto(puntosFaltante);
			Double iva = monto * configuracionWeb.getIva() / 100;

			String product_amount = df.format(monto);
			String application_key = properties.getProperty(ConstantesApi.PASARELA_KEY);
			String application_mode = properties.getProperty(ConstantesApi.PASARELA_MODE);

			Payment payment = new Payment();
			payment.setApplication_code(application_code);
			payment.setUid(uid);
			payment.setAuth_timestamp(auth_timestamp);
			payment.setDev_reference(dev_reference);
			payment.setProduct_description(product_description);
			payment.setProduct_code(product_code);
			payment.setProduct_amount(product_amount);
			payment.setApplication_key(application_key);
			payment.setIdParticipante(participanteSession.getIdParticipante());
			payment.setPuntos(puntosFaltante);
			payment.setApplication_mode(application_mode);
			payment.setBuyer_email(participanteSession.getEmailParticipante());
			payment.setBuyer_phone(participanteSession.getTelefonoParticipante());
			payment.setCurrency(properties.getProperty(ConstantesApi.PASARELA_CURRENCY));
			payment.setIva(df.format(iva));
			payment.setMontoMasIva(df.format(monto + iva));
			payment.setIvaPorcetaje(df.format(configuracionWeb.getIva()));

			String plainText="application_code="+application_code+"&dev_reference="+dev_reference
					+"&product_amount="+product_amount+"&product_code="+product_code
					+"&product_description="+product_description+"&uid="+uid+"&"+auth_timestamp+"&"+application_key;

			payment.setPlain_text(plainText);
			payment.setProduct_amount(payment.getMontoMasIva());
			guardarPago(payment);

			request.getSession().setAttribute(dev_reference, payment);

			Pedido pedidoSession = (Pedido) Util.getSession().getAttribute(ConstantesSesion.SESSION_PEDIDO);
			payment.setPuntosSubTotal(pedidoSession.getPuntosTotal());
			payment.setPuntosParticipante(participanteSession.getPuntosDisponibles());

			promotickResult.setData(payment);
		} catch (Exception e) {
			promotickResult.setException(e);
		}
		return promotickResult;
	}

	@ResponseBody
	@RequestMapping(value = "responsePay", method = RequestMethod.POST)
	public PromotickResult responsePay(@RequestBody Payment paymentResponse, HttpServletRequest request, PromotickResult promotickResult, Participante participanteSession) {
		try {
			Payment payment = (Payment) request.getSession().getAttribute(paymentResponse.getDev_reference());
			if (payment == null) {
				throw new Exception("Sesion del pago no encontrado");
			}

			payment.setAuthorization_code(paymentResponse.getAuthorization_code());
			payment.setCarrier(paymentResponse.getCarrier());
			payment.setTransaction_id(paymentResponse.getTransaction_id());
			payment.setPaid_date(paymentResponse.getPaid_date());
			payment.setStatus(paymentResponse.getStatus() != null ? (paymentResponse.getStatus().equals("success") ? "1" : "-1") : "-1");
			payment.setStatus_detail(paymentResponse.getStatus_detail());
			payment.setTransaction_reference(paymentResponse.getTransaction_reference());
			payment.setUser_id(payment.getUid());
			payment.setUsd_amount(payment.getMontoMasIva());

			Participante part = new Participante();
			System.out.println("UID>>>>" + payment.getUser_id());
			part.setUsuarioParticipante(payment.getUser_id());
			part.setNroDocumento(payment.getUser_id());

			payment.setBuyer_first_name(participanteSession.getNombresParticipante());
			payment.setBuyer_last_name(participanteSession.getAppaternoParticipante() + " " + participanteSession.getApmaternoParticipante());
			payment.setBuyer_email(participanteSession.getEmailParticipante());
			payment.setBuyer_phone(participanteSession.getTelefonoParticipante());
			payment.setCurrency(properties.getProperty(ConstantesApi.PASARELA_CURRENCY));


			//Envio al server
			Integer proc = paqueteService.actualizarPayment(payment);
			if(proc == -1) {
				throw new Exception("La transaccion ya fue procesada anteriormente");
			}else {

				if (payment.getStatus().equals("1") && payment.getStatus_detail().equals("3")) {

					String imgBanner = promotickResourceService.web("img/bg/mail-compra/banner_compras.png");
					String fechaActual = UtilCommon.dateFormat(UtilCommon.FORMATO_FECHA_DIA_MES_ANIO_2);
					ConfiguracionWeb configuracionWeb = configuracionWebService.obtenerConfiguracionWeb();

					Object data[] = {
							imgBanner,
							fechaActual,
							participanteSession.getNombresParticipante() + " " + participanteSession.getAppaternoParticipante(),
							participanteSession.getNroDocumento(),
							participanteSession.getEmailParticipante(),
							participanteSession.getTelefonoParticipante(),
							payment.getTransaction_id(),
							payment.getProduct_description().replaceAll("_", ""),
							1,
							payment.getUsd_amount() + " USD",
							payment.getUsd_amount() + " USD",
							proc,
							payment.getAuthorization_code(),
							payment.getIva()
					};

					String pathAbsoluteHtml = promotickResourceService.mail("gracias_compra_puntos.html");

					String[] correos = null;

					String correoCopia = "";

					if (configuracionWeb.getCorreosCopiaOculta() != null) {
						correos = configuracionWeb.getCorreosCopiaOculta().split("\\|");
						for(int i=0;i<correos.length;i++)
							correoCopia+=correos[i]+";";
					}

					this.obtenerPuntosCliente();
					enviarMail(pathAbsoluteHtml, data, participanteSession.getEmailParticipante(),correoCopia , "clubpichincha@promotick.com", "Compra de Puntos", "Compra de Puntos");
				} else {
					promotickResult.setStatus(Boolean.FALSE);
					promotickResult.setMessage("Consumo rechazado (" + paymentResponse.getStatus() + "): " + paymentResponse.getMessage());
					return promotickResult;
				}
				promotickResult.setStatus(Boolean.TRUE);
				promotickResult.setMessage(payment.getDev_reference());
			}


		} catch (Exception e) {
			promotickResult
					.setStatus(Boolean.FALSE)
					.setMessage(e.getMessage());
		}
		return promotickResult;
	}
	
	private Integer guardarPago(Payment payment) {

		Integer proc = paqueteService.registrarPayment(payment);
		return proc;
	}

	@RequestMapping(value = "failure", method = RequestMethod.GET)
	public String failure(Model model) {
		LOGGER.info("#getUrl");
		
		return "failure";
	}
	
	@RequestMapping(value = "confirmacion/", method = RequestMethod.GET)
	public String confirmacion(Model model) {
		LOGGER.info("#getUrl");
		return "failure";
	}

	private Integer obtenerPuntosFaltantes() {
		Pedido pedidoSession = (Pedido) Util.getSession().getAttribute(ConstantesSesion.SESSION_PEDIDO);
		Integer puntosCliente = obtenerPuntosCliente();
		Integer puntosTotal = 0;
		for (PedidoDetalle detalle : pedidoSession.getPedidoDetalles()) {
			puntosTotal += (detalle.getProducto().getPuntosProducto() * detalle.getCantidad());
		}
		Integer puntosFaltantes =  puntosTotal - puntosCliente;
		if(puntosFaltantes > puntosCliente/2) {
			puntosFaltantes = puntosCliente/2;
		}
		return puntosFaltantes;
	}
	private Double obtenerMonto(Integer puntosTotal) {
		Double conversion = Double.parseDouble(properties.getProperty(ConstantesApi.PASARELA_CONVERSION));
		return conversion * puntosTotal;
	}

	private Integer obtenerPuntosCliente() {
		Integer puntos = 0;
		if (Util.getSession().getAttribute(ConstantesSesion.SESSION_PARTICIPANTE) != null) {
			Participante participanteSession = (Participante) Util.getSession().getAttribute(ConstantesSesion.SESSION_PARTICIPANTE);

			participanteSession = participanteWebService.loginParticipante(participanteSession.getTipoDocumento().getIdTipoDocumento(), participanteSession.getNroDocumento());

			Util.getSession().setAttribute(ConstantesSesion.SESSION_PARTICIPANTE, participanteSession);
			puntos = participanteSession.getPuntosDisponibles();
		}
		return puntos;
	}


	@RequestMapping(value="confirmacion/{idsesion}", method = RequestMethod.GET)
	public String respuesta(@PathVariable("idsesion") String idsesion, Model model, HttpServletRequest request){
		Payment payment = (Payment) request.getSession().getAttribute(idsesion);
		
		if(payment == null){
			return "redirect:/checkout";
		}
		
		model.addAttribute("puntos",payment.getPuntos());
		model.addAttribute("precio",payment.getMontoMasIva());
		Util.getSession().removeAttribute(idsesion);
		return "paquete.respuesta";
	}


	private boolean enviarMail(String pathAbsoluteHtml,
							   Object[] data,
							   String destino,
							   String destinoCopia,
							   String fromName,
							   String aliasContacto,
							   String aliasCorreo) {
		boolean enviado = false;
		try {

			EmailRequest emailRequest = new EmailRequest();
			emailRequest.setTo(destino);
			emailRequest.setFrom(fromName);
			emailRequest.setFromName(aliasCorreo);
			emailRequest.setSubject(aliasContacto);
			emailRequest.setPathTemplate(pathAbsoluteHtml);
			emailRequest.setParameters(data);
			emailRequest.generateHtml();
			if (!StringUtils.isEmpty(destinoCopia)) emailRequest.addBcc(destinoCopia);

			LOGGER.info("Response EMAIL TO: " + emailRequest.getTo());
			apiEmailService.sendEmailAsync(emailRequest);
			enviado = true;

		} catch (Exception e) {
			LOGGER.error("Error", e);
			enviado = false;
		}
		return enviado;
	}

}
