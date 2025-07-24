package com.promotick.lafabril.web.controller;

import com.promotick.lafabril.model.web.Participante;
import com.promotick.lafabril.web.service.ParticipanteWebService;
import com.promotick.lafabril.web.util.BaseController;
import com.promotick.lafabril.web.util.ConstantesWebView;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("meta")
@RequiredArgsConstructor
public class MiMetaController extends BaseController {

    private final ParticipanteWebService participanteWebService;

    @GetMapping
    public String init(Model model, Participante participante) {
        if (!participante.getCatalogo().getViewMiMeta()) {
            return "redirect:/";
        }
        model.addAttribute("meta", participanteWebService.participanteMetaObtener(participante.getIdParticipante(), 1));
        model.addAttribute("metaAnual", participanteWebService.participanteMetaObtener(participante.getIdParticipante(), 0));
        return ConstantesWebView.VIEW_MI_META_V2;
    }

}
