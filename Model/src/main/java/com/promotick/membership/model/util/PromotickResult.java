package com.promotick.membership.model.util;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;

@Data
@Slf4j
public class PromotickResult {
    private boolean status = Boolean.TRUE;
    private String message;
    private Object data;
    private Object extra1;
    private Object extra2;
    private Logger logger;
    private boolean showLogger = Boolean.TRUE;

    public void setException(Exception e) {
        this.status = Boolean.FALSE;
        this.message = e.getMessage();
        if (this.showLogger) {
            if (logger != null) {
                logger.error(logger.getName(), e);
            } else {
                log.error(log.getName(), e);
            }
        }
    }

    public void setLogger(Logger logger) {
        this.logger = logger;
    }

    public PromotickResult setShowLogger(boolean showLogger) {
        this.showLogger = showLogger;
        return this;
    }
}
