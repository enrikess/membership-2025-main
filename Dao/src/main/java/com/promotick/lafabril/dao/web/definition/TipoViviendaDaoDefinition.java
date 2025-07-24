package com.promotick.lafabril.dao.web.definition;

import com.promotick.lafabril.model.web.TipoVivienda;
import com.promotick.configuration.utils.dao.DaoDefinition;
import org.springframework.stereotype.Component;

@Component
public class TipoViviendaDaoDefinition extends DaoDefinition<TipoVivienda> {
    public TipoViviendaDaoDefinition() {
        super(TipoVivienda.class);
    }
}
