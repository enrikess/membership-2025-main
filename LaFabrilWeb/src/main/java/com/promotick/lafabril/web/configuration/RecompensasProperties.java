package com.promotick.lafabril.web.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "recompensas.configuration")
public class RecompensasProperties {
    
    private String url;
    private String grantType;
    private String clientId;
    private String clientSecret;
    private Integer timeout;
    private Jwt jwt;
    
    public static class Jwt {
        private String secret;
        private Long expiration;
        private String issuer;
        
        public String getSecret() {
            return secret;
        }
        
        public void setSecret(String secret) {
            this.secret = secret;
        }
        
        public Long getExpiration() {
            return expiration;
        }
        
        public void setExpiration(Long expiration) {
            this.expiration = expiration;
        }
        
        public String getIssuer() {
            return issuer;
        }
        
        public void setIssuer(String issuer) {
            this.issuer = issuer;
        }
    }
    
    public String getUrl() {
        return url;
    }
    
    public void setUrl(String url) {
        this.url = url;
    }

    public String getGrantType() {
        return grantType;
    }
    
    public void setGrantType(String grantType) {
        this.grantType = grantType;
    }
    
    public String getClientId() {
        return clientId;
    }
    
    public void setClientId(String clientId) {
        this.clientId = clientId;
    }
    
    public String getClientSecret() {
        return clientSecret;
    }
    
    public void setClientSecret(String clientSecret) {
        this.clientSecret = clientSecret;
    }
    
    public Integer getTimeout() {
        return timeout;
    }
    
    public void setTimeout(Integer timeout) {
        this.timeout = timeout;
    }
    
    public Jwt getJwt() {
        return jwt;
    }
    
    public void setJwt(Jwt jwt) {
        this.jwt = jwt;
    }
}
