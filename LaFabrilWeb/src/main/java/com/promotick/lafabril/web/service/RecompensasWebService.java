package com.promotick.lafabril.web.service;

import java.util.List;

//import com.promotick.lafabril.model.web.SubtipoParticipante;


public interface RecompensasWebService {
    Boolean probarConexion();
    String obtenerToken();
    String obtenerIdentificadorCache();
    void limpiarIdentificadorCache();
    
    /**
     * Método genérico para hacer consultas con token JWT
     */
    Object hacerConsultaPOST(String endpoint, Object payload);
    Object hacerConsultaGET(String endpoint);
        
    /**
     * Método específico para obtener misiones disponibles
     */
    Object obtenerPromociones();
    Object obtenerPromociones(List<String> palabras);


    Object obtenerMisiones();
    Object obtenerMisionesPorId(Number idMision);

    Object logearCedulaJSON(String cedula);
    
}
