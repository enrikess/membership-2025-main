package com.promotick.membership.web.service;

import java.util.List;



public interface RecompensasWebService {
    Boolean probarConexion();
    String obtenerToken();
    String obtenerIdentificadorCache();
    void limpiarIdentificadorCache();
    
    /**
     * Método genérico para hacer consultas con token JWT
     */
    Object hacerConsultaPOST(String endpoint, Object payload, String cedula);
    Object hacerConsultaGET(String endpoint);
        
    /**
     * Método específico para obtener misiones disponibles
     */
    Object obtenerPromociones();
    Object obtenerPromociones(List<String> palabras);


    Object obtenerMisiones();
    Object obtenerMisionesPorId(Number idMision);
    
//    Object logearCedulaJSON(String cedula);
    
    Object registrarMisionRecompensa(Number idMision,Number idRecompensa);
}
