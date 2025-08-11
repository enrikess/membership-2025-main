package com.promotick.membership.web.service.impl;

import com.promotick.membership.dao.web.ProximaMisionDao;
import com.promotick.membership.model.web.ProximaMision;
import com.promotick.membership.web.service.LogService;
import com.promotick.membership.web.service.ProximaMisionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class ProximaMisionServiceImpl implements ProximaMisionService {
    
    @Autowired
    private ProximaMisionDao proximaMisionDao;
    
    @Autowired
    private LogService logService;

    @Override
    public ProximaMision obtenerProximaMisionConFechaMayor() {
        try {
            ProximaMision proximaMision = proximaMisionDao.obtenerProximaMisionConFechaMayor();
            log.info("✅ Próxima misión obtenida: " + (proximaMision != null ? proximaMision.getFecha() : "No encontrada"));
            return proximaMision;
        } catch (Exception e) {
            log.error("❌ Error obteniendo próxima misión con fecha mayor: " + e.getMessage());
            logService.generarLog("SELECT", "Error obteniendo próxima misión: " + e.getMessage(), "", null, "");
            return null;
        }
    }
}
