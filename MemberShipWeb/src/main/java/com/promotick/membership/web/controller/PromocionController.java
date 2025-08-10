package com.promotick.membership.web.controller;

import com.promotick.membership.model.BusquedaPromocionDto;
import com.promotick.membership.model.Promocion;
import com.promotick.membership.web.service.PromocionService;
import com.promotick.membership.web.util.BaseController;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Slf4j
@Controller
@RequestMapping("/promociones")
public class PromocionController extends BaseController {
    @Autowired
    PromocionService promocionService;

    @PostMapping("/buscar")
    @ResponseBody
    public List<Promocion> buscarPromociones(@RequestBody BusquedaPromocionDto busquedaDto) {
        List<Promocion> promocionesFiltradas = promocionService.obtenerPromociones(busquedaDto.getPalabras());
        return promocionesFiltradas;
    }
}
