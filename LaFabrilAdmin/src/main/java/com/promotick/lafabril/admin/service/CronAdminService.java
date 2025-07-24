package com.promotick.lafabril.admin.service;

public interface CronAdminService {
    void initCargaParticipantes();

    void initCargaVentas();

    void initCierrePeriodo();

    void initVencimiento();

    void initCumpleanos();

    void initReenvioNetsuite();

    void initEnvioFestividad();

    void initEstadoCuenta(boolean test);

    void initRestaurarProductos();

}
