package com.promotick.lafabril.admin.controller;

import com.promotick.lafabril.admin.service.CronAdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("cron")
public class CronController {

    private CronAdminService cronAdminService;

    @Autowired
    public CronController(CronAdminService cronAdminService) {
        this.cronAdminService = cronAdminService;
    }

   /* @GetMapping("carga/participantes")
    public String cronCargaParticipantes() {
        cronAdminService.initCargaParticipantes();
        return "PROCESAMIENTO DE CARGAS PARTICIPANTES INICIADO";
    }*/

    /*@GetMapping("carga/ventas")
    public String cronCargaVentas() {
        cronAdminService.initCargaVentas();
        return "PROCESAMIENTO DE CARGAS VENTAS INICIADO";
    }*/

    /*@GetMapping("cierrePeriodo")
    public String cronCierrePeriodo() {
        cronAdminService.initCierrePeriodo();
        return "PROCESAMIENTO DE CIERRE DE PERIODO INICIADO";
    }*/

    @GetMapping("vencimiento")
    public String cronVencimiento() {
        cronAdminService.initVencimiento();
        return "PROCESAMIENTO DE VENCIMIENTO INICIADO";
    }

    @GetMapping("cumpleanos")
    public String cumpleanos() {
        cronAdminService.initCumpleanos();
        return "PROCESAMIENTO DE CUMPLEAÑOS INICIADO";
    }

    @GetMapping("netsuite/reenvio")
    public String reenvioNetsuite() {
        cronAdminService.initReenvioNetsuite();
        return "PROCESAMIENTO DE REENVIO A NETSUITE INICIADO";
    }

    /*
    @GetMapping("festividades")
    public String festividades() {
        cronAdminService.initEnvioFestividad();
        return "PROCESAMIENTO DE ENVIO DE FESTIVIDADES";
    }
    */
    @GetMapping("estado-cuenta")
    public String cronEstadoCuenta() {
        cronAdminService.initEstadoCuenta(false); //solo participantes de prueba = false
        return "PROCESAMIENTO DE ENVIO ESTADO DE CUENTA";
    }

    @GetMapping("estado-cuenta-test")
    public String cronEstadoCuentaTest() {
        cronAdminService.initEstadoCuenta(true); //solo participantes de prueba = true
        return "PROCESAMIENTO DE ENVIO ESTADO DE CUENTA TEST";
    }

    @GetMapping("restaurar/productos")
    public String cronRestaurarProductos() {
        cronAdminService.initRestaurarProductos();
        return "PROCESAMIENTO PARA RESTAURAR PRODUCTOS";
    }
}
