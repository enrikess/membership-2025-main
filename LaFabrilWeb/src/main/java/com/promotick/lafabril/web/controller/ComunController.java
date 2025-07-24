package com.promotick.lafabril.web.controller;

import com.promotick.lafabril.common.ConstantesCommon;
import com.promotick.lafabril.model.util.FiltroAcelerador;
import com.promotick.lafabril.model.util.PromotickResult;
import com.promotick.lafabril.model.util.UtilEnum;
import com.promotick.lafabril.model.web.Acelerador;
import com.promotick.lafabril.model.web.Participante;
import com.promotick.lafabril.web.service.ConfiguracionService;
import com.promotick.lafabril.web.service.FaqWebService;
import com.promotick.lafabril.web.service.TycWebService;
import com.promotick.lafabril.web.service.UbigeoWebService;
import com.promotick.lafabril.web.util.ConstantesWebView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class ComunController {

    private FaqWebService faqWebService;
    private TycWebService tycWebService;
    private UbigeoWebService ubigeoWebService;
    private ConfiguracionService configuracionService;

    @Autowired
    public ComunController(FaqWebService faqWebService, TycWebService tycWebService, UbigeoWebService ubigeoWebService, ConfiguracionService configuracionService) {
        this.faqWebService = faqWebService;
        this.tycWebService = tycWebService;
        this.ubigeoWebService = ubigeoWebService;
        this.configuracionService = configuracionService;
    }

    @GetMapping(value = "terminos-y-condiciones")
    public String tyc(Model model, Participante participante) {
        model.addAttribute("tycs", tycWebService.listarTyc(ConstantesCommon.ZERO_VALUE, participante.getCatalogo().getIdCatalogo(), UtilEnum.ESTADO.ACTIVO.getCodigo()));
        return ConstantesWebView.VIEW_TYC;
    }

    @GetMapping(value = "preguntas-frecuentes")
    public String preguntasFerecuentes(Model model, Participante participante) {
        model.addAttribute("faqs", faqWebService.listarFaq(ConstantesCommon.ZERO_VALUE, participante.getCatalogo().getIdCatalogo(), UtilEnum.ESTADO.ACTIVO.getCodigo()));
        return ConstantesWebView.VIEW_FAQ;
    }

    @GetMapping(value = "aceleradores")
    public String acelerador(Model model, Participante participante) {
        FiltroAcelerador filtroAcelerador = new FiltroAcelerador();
        filtroAcelerador.setIdParticipante(participante.getIdParticipante());
        List<Acelerador> aceleradores = configuracionService.aceleradorListar(filtroAcelerador);
        Acelerador acelerador = null;
        if (aceleradores != null && !aceleradores.isEmpty()) {
            acelerador = aceleradores.get(0);
        }
        model.addAttribute("acelerador", acelerador);
        return ConstantesWebView.VIEW_ACELERADORES;
    }

    @ResponseBody
    @GetMapping("obtenerDepartamentos")
    public PromotickResult getDepartamentos(PromotickResult promotickResult) {
        try {
            promotickResult.setData(ubigeoWebService.listarDepartamentos(ConstantesCommon.COD_PAIS));
            promotickResult.setStatus(Boolean.TRUE);
        } catch (Exception e) {
            promotickResult.setException(e);
        }
        return promotickResult;
    }

    @ResponseBody
    @GetMapping("obtenerProvincias/{coddep}")
    public PromotickResult getProvincias(@PathVariable String coddep, PromotickResult promotickResult) {
        try {
            promotickResult.setData(ubigeoWebService.listarProvincias(ConstantesCommon.COD_PAIS, coddep));
            promotickResult.setStatus(Boolean.TRUE);
        } catch (Exception e) {
            promotickResult.setException(e);
        }
        return promotickResult;
    }

    @ResponseBody
    @GetMapping("obtenerDistritos/{coddep}/{codprov}")
    public PromotickResult getDistrito(@PathVariable String coddep, @PathVariable String codprov, PromotickResult promotickResult) {
        try {
            promotickResult.setData(ubigeoWebService.listarDistritos(ConstantesCommon.COD_PAIS, coddep, codprov));
            promotickResult.setStatus(Boolean.TRUE);
        } catch (Exception e) {
            promotickResult.setException(e);
        }
        return promotickResult;
    }

}



