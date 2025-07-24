package com.promotick.lafabril.dao.admin;

import com.promotick.lafabril.model.util.FiltroFaq;
import com.promotick.lafabril.model.util.FiltroTyc;
import com.promotick.lafabril.model.util.form.CargaAcciones;
import com.promotick.lafabril.model.util.form.CargaMetas;
import com.promotick.lafabril.model.util.form.CargaPuntos;
import com.promotick.lafabril.model.util.form.CargaVentas;
import com.promotick.lafabril.model.web.Participante;
import com.promotick.lafabril.model.web.Recomendado;

public interface CargaExcelDao {
    Integer registrarCargaPuntos(CargaPuntos cargaPuntos);

    Integer registrarCargaParticipante(Participante participante);

    Integer registrarCargaMetas(CargaMetas cargaMetas);

    Integer registrarCargaVentas(CargaVentas cargaVentas);

    Integer registrarCargaParticipanteEstado(Participante participante);

    Integer registrarCargaRecomendadoEstado(Recomendado recomendado);

    Integer actualizarTyc(FiltroTyc filtroTyc);

    Integer actualizarFaq(FiltroFaq filtroFaq);

    Integer registrarAccionesMetas(CargaAcciones cargaAcciones);
}
