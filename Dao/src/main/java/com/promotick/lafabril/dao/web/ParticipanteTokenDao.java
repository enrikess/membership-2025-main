package com.promotick.lafabril.dao.web;


import com.promotick.lafabril.model.util.UtilEnum;
import com.promotick.lafabril.model.web.Participante;

public interface ParticipanteTokenDao {

    Integer generarToken(Integer idParticipante, String token, UtilEnum.TIPO_DISPOSITVO_VISITA tipo_dispositvo);

    Integer validarToken(String token);

    Participante obtenerParticipanteXToken(String token);
}
