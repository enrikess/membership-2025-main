package com.promotick.membership.web;

import com.promotick.configuration.annotations.*;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@EnablePromotickWeb
@EnablePromotickTask
@EnablePromotickDatasource
@PromotickApplication(
        propertyEnvironment = "propertiesLandingMR",
        properties = {
                "application-membership-ip.yml",
                "application-membership-db.yml",
                "application-membership-api.yml"
        },
        scanBasePackages = "com.promotick.membership"
)
public class MemberShipApplication extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(MemberShipApplication.class, args);
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(MemberShipApplication.class);
    }
}
