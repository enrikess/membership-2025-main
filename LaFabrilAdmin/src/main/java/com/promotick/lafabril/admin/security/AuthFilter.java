package com.promotick.lafabril.admin.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AuthFilter extends UsernamePasswordAuthenticationFilter {

    private Logger LOGGER = LoggerFactory.getLogger(getClass());


    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException {
        LOGGER.info("Filtro");
        return super.attemptAuthentication(request, response);
    }

}
