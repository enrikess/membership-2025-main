package com.promotick.lafabril.admin.service;

import com.promotick.lafabril.admin.util.CronException;
import com.promotick.lafabril.admin.util.values.TipoEnvioEmailEnum;
import com.promotick.lafabril.model.configuracion.ConfiguracionWeb;
import com.promotick.lafabril.model.facturacion.CargaProceso;
import com.promotick.lafabril.model.util.form.CargaMetas;
import com.promotick.lafabril.model.util.form.CargaVentas;
import com.promotick.lafabril.model.web.Participante;
import com.promotick.lafabril.model.web.ParticipanteMeta;

import java.io.File;

public interface EmailAdminService {
    void envioEmailBienvenida();

    void envioEmailBienvenidaIndividual(Participante participante, ConfiguracionWeb configuracionWeb, boolean muestraClave);

    void envioEmailPuntos(TipoEnvioEmailEnum tipoEnvioEmailEnum);

    void envioEmailCierrePeriodo();

    void envioEmailProductosNuevos();

    void envioEmailRebote(CronException e, File file, CargaProceso cargaProceso);

    void envioEmailError(Exception e, File file, String fileName);

    void envioEmailResumen(CargaProceso cargaProceso);

    void envioEmailCumpleanos(Participante participante);

    void envioEmailMetaCumplida();

    void envioEmailSistemas(String mensaje, String titulo);

    void envioEmailPuntosManuales(Participante participante, Integer puntosCargados);

    void envioEmailCargaMetas(Participante participante, CargaMetas cargaMetas);

    void envioEmailCargaVentas(Participante participante, CargaVentas cargaVentas, ParticipanteMeta participanteMeta);

    void envioEmailEstadoCanje(Participante participante);
}
