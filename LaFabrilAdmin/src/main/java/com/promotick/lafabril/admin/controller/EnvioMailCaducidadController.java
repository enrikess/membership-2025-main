package com.promotick.lafabril.admin.controller;

import com.promotick.lafabril.admin.util.BaseController;
import com.promotick.lafabril.admin.service.ConfiguracionAdminService;
import com.promotick.lafabril.admin.util.ConstantesAdminView;
import com.promotick.lafabril.model.configuracion.ConfiguracionWeb;
import com.promotick.lafabril.model.util.Auditoria;
import com.promotick.lafabril.model.util.PromotickResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Controller
@RequestMapping("administracion/envio-caducidad")
public class EnvioMailCaducidadController extends BaseController {

    private ConfiguracionAdminService configuracionAdminService;

    @Autowired
    public EnvioMailCaducidadController(ConfiguracionAdminService configuracionAdminService) {
        this.configuracionAdminService = configuracionAdminService;
    }

    @GetMapping
    public String init(Model model) {
        model.addAttribute("configuracionWeb", configuracionAdminService.obtenerConfiguracionWeb());
        return ConstantesAdminView.VIEW_ENVIO_CADUCIDAD;
    }

    @ResponseBody
    @PostMapping(value = "guardar-caducidad")
    public PromotickResult guardarCaducidad(@RequestBody ConfiguracionWeb configuracionWeb, PromotickResult promotickResult, Auditoria auditoria) {
        try {
//            configuracionWeb.setFechaVencimiento(UtilCommon.stringToDate(configuracionWeb.getFechaVencimientoString()));
//            Integer resultado = configuracionAdminService.actualizarDatosVencimiento(configuracionWeb);
//            this.evaluarResultado(resultado, promotickResult, "Fecha vencimiento guardada correctamente");
        } catch (Exception e) {
            promotickResult.setException(e);
        }
        return promotickResult;
    }

}
