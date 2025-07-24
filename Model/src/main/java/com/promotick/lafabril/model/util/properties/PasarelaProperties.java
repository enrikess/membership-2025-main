package com.promotick.lafabril.model.util.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component("pasarelaProperties")
@ConfigurationProperties("pasarela")
public class PasarelaProperties {

    private PasarelaConfigurationProperties configuration;

    @Data
    public static class PasarelaConfigurationProperties {
        private String key;
        private String currency;
        private String code;
        private String conversion;
        private String mode;

    }

//    @Data
//    public static class PaymentezMethodsProperties {
//        private String refund;
//    }
}
