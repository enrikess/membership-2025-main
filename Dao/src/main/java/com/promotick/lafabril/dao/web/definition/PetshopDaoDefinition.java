package com.promotick.lafabril.dao.web.definition;

import com.promotick.configuration.utils.dao.DaoDefinition;
import com.promotick.lafabril.model.web.Petshop;
import org.springframework.stereotype.Component;

@Component
public class PetshopDaoDefinition extends DaoDefinition<Petshop> {
    public PetshopDaoDefinition() {
        super(Petshop.class);
    }
}
