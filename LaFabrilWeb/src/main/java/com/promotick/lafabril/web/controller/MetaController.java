package com.promotick.lafabril.web.controller;

import com.promotick.lafabril.common.ConstantesCommon;
import com.promotick.lafabril.common.UtilCommon;
import com.promotick.lafabril.model.configuracion.ConfiguracionBanner;
import com.promotick.lafabril.model.util.MetaParticipante;
import com.promotick.lafabril.model.util.PromotickResult;
import com.promotick.lafabril.model.util.UtilEnum;
import com.promotick.lafabril.model.web.Participante;
import com.promotick.lafabril.web.service.ConfiguracionWebService;
import com.promotick.lafabril.web.service.MetaService;
import com.promotick.lafabril.web.service.PeriodoParticipanteWebService;
import com.promotick.lafabril.web.util.BaseController;
import com.promotick.lafabril.web.util.ConstantesWebView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("mi-resultado")
public class MetaController extends BaseController {

    private PeriodoParticipanteWebService periodoParticipanteWebService;
    private ConfiguracionWebService configuracionWebService;
    private MetaService metaService;

    @Autowired
    public MetaController(MetaService metaService, PeriodoParticipanteWebService periodoParticipanteWebService, ConfiguracionWebService configuracionWebService) {
        this.periodoParticipanteWebService = periodoParticipanteWebService;
        this.configuracionWebService = configuracionWebService;
        this.metaService = metaService;
    }

    @GetMapping
    public String init(Model model, Participante participante) {
        return ConstantesWebView.VIEW_MI_META;
    }

    @ResponseBody
    @GetMapping("viewInformacionMeta/{mes}")
    public List<Object> viewInformacionMeta(@PathVariable Integer mes, Model model, Participante participante) {
        List<Object> resultadoMeta = new ArrayList<>();
        resultadoMeta.add(metaService.obtenerMetaAvanceParticipanteTrimestral(participante.getIdParticipante(), LocalDate.now().getYear(), mes));
        resultadoMeta.add(metaService.obtenerAccionesSelloutParticipante(participante.getIdParticipante(), LocalDate.now().getYear(), mes));
        return resultadoMeta;
    }

    @ResponseBody
    @GetMapping("getMetaParticipante/{tipo}")
    public PromotickResult getMetaParticipante(@PathVariable Integer tipo, Participante participante, PromotickResult promotickResult) {
        try {
            MetaParticipante metaParticipante = periodoParticipanteWebService.obtenerMetaParticipante(participante.getIdParticipante(), UtilEnum.TIPO_META.UNACEM);
            if (metaParticipante == null) {
                throw new Exception("No hay meta");
            }
            promotickResult.setData(metaParticipante);
        } catch (Exception e) {
            promotickResult.setException(e);
        }
        return promotickResult;
    }
}
