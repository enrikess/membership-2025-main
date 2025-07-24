package com.promotick.lafabril.web;

import com.promotick.lafabril.web.session.SessionArgumentResolver;
import com.promotick.lafabril.web.session.SessionInterceptor;
import com.promotick.configuration.annotations.*;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@EnablePromotickWeb
@EnablePromotickTask
@EnablePromotickDatasource
@EnablePromotickSecurity
@PromotickApplication(
        propertyEnvironment = "propertiesLandingMR",
        properties = {
                "application-lafabril-ip.yml",
                "application-lafabril-db.yml",
                "application-lafabril-api.yml",
                "application-recompensas-api.yml"
        },
        scanBasePackages = "com.promotick.lafabril",
        methodArguments = SessionArgumentResolver.class,
        interceptors = SessionInterceptor.class
)
public class LaFabrilWebApplication extends SpringBootServletInitializer {


    public static void main(String[] args) {
        SpringApplication.run(LaFabrilWebApplication.class, args);
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(LaFabrilWebApplication.class);
    }
}
