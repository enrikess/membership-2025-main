package com.promotick.lafabril.web.controller;

import com.promotick.lafabril.common.ConstantesSesion;
import com.promotick.lafabril.model.util.Util;
import com.promotick.lafabril.model.web.Descuento;
import com.promotick.lafabril.model.web.Participante;
import com.promotick.lafabril.model.web.ParticipanteDireccion;
import com.promotick.lafabril.model.web.Pedido;
import com.promotick.lafabril.common.ConstantesCommon;
import com.promotick.lafabril.model.util.PromotickResult;
import com.promotick.lafabril.web.service.DireccionWebService;
import com.promotick.lafabril.web.service.ParticipanteWebService;
import com.promotick.lafabril.web.service.ProductoWebService;
import com.promotick.lafabril.web.service.UbigeoWebService;
import com.promotick.lafabril.web.util.BaseController;
import com.promotick.lafabril.web.util.ConstantesWebView;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.thymeleaf.util.StringUtils;

@Slf4j
@Controller
@RequestMapping("checkout")
@RequiredArgsConstructor
public class CheckoutController extends BaseController {

    private final UbigeoWebService ubigeoWebService;
    private final DireccionWebService direccionWebService;
    private final ProductoWebService productoWebService;

    @GetMapping
    public String inicio() {
        return ConstantesWebView.VIEW_CHECKOUT;
    }

    @GetMapping("viewCheckout")
    public String viewCheckout() {
        return ConstantesWebView.VIEW_FRAGMENTS_CHECKOUT;
    }

    @GetMapping("viewResumenCheckout")
    public String viewResumenCheckout(Pedido pedido, Participante participante, Model model) {
        model.addAttribute("mensajeStock", productoWebService.validarStock(pedido, participante.getCatalogo(), participante.getIdParticipante()));
        model.addAttribute("dvCompra", participante.getPuntosDisponibles() - pedido.getPuntosTotal() > 0);
        return ConstantesWebView.VIEW_FRAGMENTS_CHECKOUT_RESUMEN;
    }

    @GetMapping("viewInfoCheckout")
    public String viewInfoCheckout() {
        return ConstantesWebView.VIEW_FRAGMENTS_CHECKOUT_INFO;
    }

    @GetMapping("viewDireccionCheckout")
    public String viewDireccionCheckout(Model model, Participante participante) {
        model.addAttribute("provincias", ubigeoWebService.listarProvincias(ConstantesCommon.COD_PAIS, ConstantesCommon.COD_DEP));

        if (!StringUtils.isEmpty(participante.getDireccion().getUbigeo().getCodprov())) {
            model.addAttribute("ciudades", ubigeoWebService.listarDistritos(ConstantesCommon.COD_PAIS, ConstantesCommon.COD_DEP, participante.getDireccion().getUbigeo().getCodprov()));
        }
        model.addAttribute("zonas", direccionWebService.listarZonas());
        model.addAttribute("tipoViviendas", direccionWebService.listarTipoViviendas());
        return ConstantesWebView.VIEW_FRAGMENTS_CHECKOUT_DIRECCION;
    }

    @GetMapping("viewPuntosCheckout")
    public String viewPuntosCheckout(Model model, Pedido pedido) {
        model.addAttribute("pedido", pedido);
        return ConstantesWebView.VIEW_FRAGMENTS_CHECKOUT_PUNTOS;
    }

    @GetMapping("viewGraciasCheckout")
    public String viewGraciasCheckout() {
        return ConstantesWebView.VIEW_FRAGMENTS_CHECKOUT_GRACIAS;
    }

    @ResponseBody
    @GetMapping("validarResumenStock")
    public PromotickResult validarStock(PromotickResult promotickResult, Pedido pedido, Participante participante) {
        try {
            String mensaje = productoWebService.validarStock(pedido, participante.getCatalogo(), participante.getIdParticipante());
            if (mensaje != null) {
                throw new Exception(mensaje);
            }
        } catch (Exception e) {
            promotickResult.setException(e);
        }
        return promotickResult;
    }

    @GetMapping("viewDireccionesCheckout")
    public String viewDireccionesCheckout(Participante participante, Model model) {
        model.addAttribute("direcciones", direccionWebService.listarDireccionesParticipante(participante.getIdParticipante()));
        return ConstantesWebView.VIEW_FRAGMENTS_CHECKOUT_DIRECCIONES;
    }

    @GetMapping("viewDireccionFormCheckout/{idParticipanteDireccion}")
    public String viewDireccionFormCheckout(@PathVariable Integer idParticipanteDireccion, Participante participante, Model model) {
        ParticipanteDireccion participanteDireccion = null;
        if (idParticipanteDireccion != 0) {
            participanteDireccion = direccionWebService.listarDireccionesParticipante(participante.getIdParticipante()).stream().filter(pd -> pd.getIdParticipanteDireccion().intValue() == idParticipanteDireccion).findAny().orElse(null);
            if (participanteDireccion != null) {
                model.addAttribute("ciudades", ubigeoWebService.listarDistritos(ConstantesCommon.COD_PAIS, ConstantesCommon.COD_DEP, participanteDireccion.getDireccion().getUbigeo().getCodprov()));
            }
        }
        model.addAttribute("direccion", participanteDireccion);
        model.addAttribute("provincias", ubigeoWebService.listarProvincias(ConstantesCommon.COD_PAIS, ConstantesCommon.COD_DEP));
        model.addAttribute("zonas", direccionWebService.listarZonas());
        model.addAttribute("tipoViviendas", direccionWebService.listarTipoViviendas());
        return ConstantesWebView.VIEW_FRAGMENTS_CHECKOUT_DIRECCIONES_FORM;
    }

    @GetMapping("viewEncuestaProcesoCanje")
    public String viewEncuestaProcesoCanje() {
        return ConstantesWebView.VIEW_FRAGMENTS_ENCUESTA_PROCESO_CANJE;
    }

    @GetMapping("viewCompraPuntos")
    public String viewCompraPuntos() {
        return ConstantesWebView.VIEW_FRAGMENTS_COMPRA_PUNTOS;
    }

}
