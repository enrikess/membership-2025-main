package com.promotick.lafabril.dao.facturacion.definition;

import com.promotick.lafabril.model.facturacion.TipoFactura;
import com.promotick.configuration.utils.dao.DaoDefinition;
import org.springframework.stereotype.Component;

@Component
public class TipoFacturaDaoDefinition extends DaoDefinition<TipoFactura> {
    public TipoFacturaDaoDefinition() {
        super(TipoFactura.class);
    }
}
