package com.promotick.lafabril.admin;

import com.promotick.lafabril.admin.session.SessionArgumentResolver;
import com.promotick.lafabril.admin.session.SessionInterceptor;
import com.promotick.configuration.annotations.*;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@EnablePromotickWeb
@EnablePromotickTask
@EnablePromotickDatasource
@EnablePromotickSecurity
@PromotickApplication(
        propertyEnvironment = "propertiesHomeLaFabrilEcuador",
        properties = {
                "application-lafabril-ip.yml",
                "application-lafabril-db.yml",
                "application-lafabril-api.yml"
        },
        scanBasePackages = "com.promotick.lafabril",
        methodArguments = SessionArgumentResolver.class,
        interceptors = SessionInterceptor.class
)
public class LaFabrilAdminApplication extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(LaFabrilAdminApplication.class, args);
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(LaFabrilAdminApplication.class);
    }
}
