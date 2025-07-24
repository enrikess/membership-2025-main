package com.promotick.lafabril.admin.controller;

import com.promotick.lafabril.admin.util.BaseController;
import com.promotick.lafabril.admin.service.CargaExcelAdminService;
import com.promotick.lafabril.admin.service.EmailVueloService;
import com.promotick.lafabril.admin.util.ConstantesAdminView;
import com.promotick.lafabril.model.util.Auditoria;
import com.promotick.lafabril.model.util.PromotickResult;
import com.promotick.lafabril.model.util.UtilEnum;
import com.promotick.lafabril.model.util.form.CargaPuntos;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@Controller
@RequestMapping("vuelos")
public class VueloController extends BaseController {

    private CargaExcelAdminService cargaExcelAdminService;
    private EmailVueloService emailVueloService;

    @Autowired
    public VueloController(CargaExcelAdminService cargaExcelAdminService, EmailVueloService emailVueloService) {
        this.cargaExcelAdminService = cargaExcelAdminService;
        this.emailVueloService = emailVueloService;
    }

    @GetMapping
    public String init() {
        return ConstantesAdminView.VIEW_VUELOS;
    }

    @GetMapping("registro")
    public String registro() {
        return ConstantesAdminView.VIEW_VUELOS_REGISTRO;
    }

    @ResponseBody
    @PostMapping("registrar")
    public PromotickResult registrarVuelo(@RequestBody CargaPuntos cargaPuntos, PromotickResult promotickResult, Auditoria auditoria) {
        try {
            cargaPuntos.setTipoOperacion(3); // Canje externo
            cargaPuntos.setFechaOperacion(new Date());
            cargaPuntos.setIdCarga(0);
            cargaPuntos.setUsuarioCreacion(auditoria.getUsuarioCreacion());

            Integer result = cargaExcelAdminService.registrarCargaPuntos(cargaPuntos);
            UtilEnum.CARGA_PUNTOS_RESULTS resultEnum = UtilEnum.CARGA_PUNTOS_RESULTS.getMensageFromCode(result);
            if (!resultEnum.equals(UtilEnum.CARGA_PUNTOS_RESULTS.OK)) {
                throw new Exception(resultEnum.getMensaje());
            }

            promotickResult.setMessage("Se registro el vuelo correctamente y se descontaron los puntos solicitados al participante!");
            emailVueloService.enviarEmailVuelo(result);
        } catch (Exception e) {
            promotickResult.setException(e);
        }
        return promotickResult;
    }
}
