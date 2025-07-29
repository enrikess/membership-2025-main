package com.promotick.lafabril.dao.web.definition;

import com.promotick.configuration.utils.dao.DaoDefinition;
import com.promotick.lafabril.model.web.Log;
import org.springframework.stereotype.Component;

@Component
public class LogDaoDefinition extends DaoDefinition<Log> {
    public LogDaoDefinition() {
        super(Log.class);
    }
}