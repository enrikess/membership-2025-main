package com.promotick.lafabril.web.controller;

import com.promotick.lafabril.common.ConstantesCommon;
import com.promotick.lafabril.model.util.Auditoria;
import com.promotick.lafabril.model.util.PromotickResult;
import com.promotick.lafabril.model.util.UtilEnum;
import com.promotick.lafabril.model.web.Participante;
import com.promotick.lafabril.web.service.ConfiguracionWebService;
import com.promotick.lafabril.web.service.ParticipanteWebService;
import com.promotick.lafabril.web.service.PetshopWebService;
import com.promotick.lafabril.web.service.UbigeoWebService;
import com.promotick.lafabril.web.util.BaseController;
import com.promotick.lafabril.web.util.ConstantesWebView;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Controller
@RequestMapping("registro")
@RequiredArgsConstructor
public class RegistroController extends BaseController {

    private final ParticipanteWebService participanteWebService;
    private final UbigeoWebService ubigeoWebService;
    private final ConfiguracionWebService configuracionWebService;
    private final PetshopWebService petshopWebService;
    private final BCryptPasswordEncoder passwordEncoder;

    @GetMapping
    public String init(Model model, Participante participante) {
        if (participante != null) {
            return "redirect:/";
        }

        model.addAttribute("tyc", configuracionWebService.obtenerConfiguracionWeb().getTycRegistro());
        model.addAttribute("provincias", ubigeoWebService.listarProvincias(ConstantesCommon.COD_PAIS, ConstantesCommon.COD_DEP));
        model.addAttribute("petshops", petshopWebService.petshopListar());

        return ConstantesWebView.VIEW_REGISTRO;
    }

    @ResponseBody
    @PostMapping("guardarDatos")
    public PromotickResult guardarDatos(@RequestBody Participante participante, PromotickResult promotickResult, Auditoria auditoria) {
        try {
            participante.setClaveParticipante(passwordEncoder.encode(participante.getClaveParticipante()));
            participante.setAuditoria(auditoria);

            Integer resultado = participanteWebService.registroParticipante(participante);

            UtilEnum.REGISTRO_DATOS_RESULTS resultEnum = UtilEnum.REGISTRO_DATOS_RESULTS.getMensageFromCode(resultado);
            if (!resultEnum.equals(UtilEnum.REGISTRO_DATOS_RESULTS.OK)) {
                throw new Exception(resultEnum.getMensaje());
            }

            promotickResult.setMessage("Se registro la informacion correctamente");

        } catch (Exception e) {
            promotickResult.setException(e);
        }
        return promotickResult;
    }


}
