package com.promotick.lafabril.dao.facturacion.definition;

import com.promotick.lafabril.model.facturacion.TipoPeriodo;
import com.promotick.configuration.utils.dao.DaoDefinition;
import org.springframework.stereotype.Component;

@Component
public class TipoPeriodoDaoDefinition extends DaoDefinition<TipoPeriodo> {
    public TipoPeriodoDaoDefinition() {
        super(TipoPeriodo.class);
    }
}
