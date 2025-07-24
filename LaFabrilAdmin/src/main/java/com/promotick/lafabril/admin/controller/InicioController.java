package com.promotick.lafabril.admin.controller;

import com.promotick.lafabril.admin.util.BaseController;
import com.promotick.lafabril.admin.util.ConstantesAdminView;
import com.promotick.lafabril.admin.service.DashboardAdminService;
import com.promotick.lafabril.model.admin.Dashboard;
import com.promotick.lafabril.model.util.PromotickResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Slf4j
@Controller
@RequestMapping(value = "/")
public class InicioController extends BaseController {

    private DashboardAdminService dashboardAdminService;

    @Autowired
    public InicioController(DashboardAdminService dashboardAdminService) {
        this.dashboardAdminService = dashboardAdminService;
    }

    @GetMapping
    public String inicio(ModelMap model) {
        Dashboard dashboard = dashboardAdminService.obtenerDashboard();
        model.addAttribute("cantidadParticipantes", dashboard.getCantidadParticipantes());
        model.addAttribute("cantidadCanjes", dashboard.getCantidadCanjes());
        model.addAttribute("cantidadVisitas", dashboard.getCantidadVisitas());
        return ConstantesAdminView.VIEW_DASHBOARD;
    }


    @ResponseBody
    @GetMapping(value = "obtenerGraficoVisitas/{fecha}")
    public PromotickResult obtenerGraficoVisitas(@PathVariable("fecha") String fecha, PromotickResult promotickResult) {
        try {
            promotickResult.setData(dashboardAdminService.graficoVisitas(fecha));
        } catch (Exception e) {
            promotickResult.setException(e);
        }
        return promotickResult;
    }

}
