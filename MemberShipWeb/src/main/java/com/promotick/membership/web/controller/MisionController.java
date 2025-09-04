package com.promotick.membership.web.controller;

import com.promotick.membership.model.DetalleMisionDto;
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
        DetalleMisionDto mision = misionService.obtenerMisionesPorId(idMision);
        model.addAttribute("mision", mision);
        return ConstantesWebView.VIEW_RECOMPENSAS_DETALLE_MISION;
    }
}
