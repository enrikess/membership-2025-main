package com.promotick.lafabril.web.security;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CustomAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        UsernamePasswordAuthenticationToken authRequest = getAuthRequest(request);
        setDetails(request, authRequest);
        return this.getAuthenticationManager().authenticate(authRequest);

    }

    private UsernamePasswordAuthenticationToken getAuthRequest(HttpServletRequest request) {

        String username = obtainUsername(request);
        String password = obtainPassword(request);
        /*String tipoDocumento = request.getParameter("tipoDocumento");
        String usernameComplete = String.format("%s%s%s", tipoDocumento.trim(), "|", username.trim());*/
        return new UsernamePasswordAuthenticationToken(username, password);
    }

}
