package com.promotick.lafabril.dao.facturacion.definition;

import com.promotick.lafabril.model.util.MetaFacturacion;
import com.promotick.configuration.utils.dao.DaoDefinition;
import org.springframework.stereotype.Component;

@Component
public class MetaFacturacionDaoDefinition extends DaoDefinition<MetaFacturacion> {
    public MetaFacturacionDaoDefinition() {
        super(MetaFacturacion.class);
    }
}
