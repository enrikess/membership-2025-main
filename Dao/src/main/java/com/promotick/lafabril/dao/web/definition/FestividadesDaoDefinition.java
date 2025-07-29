package com.promotick.lafabril.dao.web.definition;

import com.promotick.configuration.utils.dao.DaoDefinition;
import com.promotick.lafabril.model.web.Festividades;
import org.springframework.stereotype.Component;

@Component
public class FestividadesDaoDefinition extends DaoDefinition<Festividades> {
    public FestividadesDaoDefinition() {
        super(Festividades.class);
    }
}