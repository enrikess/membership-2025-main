package com.promotick.membership.web.controller;

import com.promotick.membership.model.DetalleMisionDto;
import com.promotick.membership.model.Login;
import com.promotick.membership.model.LoginDto;
import com.promotick.membership.model.Promocion;
import com.promotick.membership.web.service.LoginService;
import com.promotick.membership.web.service.MisionService;
import com.promotick.membership.web.service.PromocionService;
import com.promotick.membership.web.util.BaseController;
import com.promotick.membership.web.util.ConstantesWebView;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@Controller
@RequestMapping("/mision")
public class MisionController extends BaseController {
    @Autowired
    private LoginService loginService;
    @Autowired
    private MisionService misionService;

    @GetMapping("/prueba/{id_mision}")
    @ResponseBody
    public DetalleMisionDto buscarMisionesId(@PathVariable("id_mision") long idMision) {
        DetalleMisionDto mision = misionService.obtenerMisionesPorId(idMision);
        
        return mision;
    }

    /**
     * Endpoint para mostrar la vista HTML
     */
    @GetMapping("/{id_mision}")
    public String misionesPorId(@PathVariable("id_mision") long idMision, Model model) {
        log.info("🔗 GET /mision/" + idMision + " - Iniciando carga de misión");
        
        DetalleMisionDto mision = misionService.obtenerMisionesPorId(idMision);
        log.info("📋 Misión obtenida: " + (mision != null ? "OK" : "NULL"));
        log.info("📋 Descripción: " + (mision != null ? mision.getDescripcion() : "N/A"));
        
        model.addAttribute("mision", mision);
        model.addAttribute("id_mision", idMision); // Agregar el ID de misión al modelo
        
        // Obtener la cédula desde la variable global del LoginService
        String cedula = loginService.obtenerUsuario();
        log.info("🔍 DEBUG: Valor obtenido de loginService.obtenerUsuario(): " + cedula);
        
        if (cedula != null && !cedula.isEmpty()) {
            model.addAttribute("cedula", cedula);
            log.info("✅ Cédula agregada al modelo: " + cedula);
        } else {
            log.info("ℹ️ No hay usuario logueado en la variable global");
            log.info("ℹ️ DEBUG: No se agregará cédula al modelo");
        }
        
        log.info("🎯 Retornando vista: " + ConstantesWebView.VIEW_RECOMPENSAS_DETALLE_MISION);
        return ConstantesWebView.VIEW_RECOMPENSAS_DETALLE_MISION;
    }


    /**
     * Endpoint para registrar
     */
    @PostMapping(value = "/registrar")
    @ResponseBody
    public String registarMision(@RequestParam("misionId") long misionId, @RequestParam("recompensaId") long recompensaId) {
        log.info("📞 POST /registarMision llamado");
        String response = misionService.registrarMisionRecompensa(misionId, recompensaId);
        return response;
    }
}
