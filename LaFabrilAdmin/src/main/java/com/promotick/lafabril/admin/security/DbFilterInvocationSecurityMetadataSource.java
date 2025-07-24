package com.promotick.lafabril.admin.security;

import com.promotick.apiclient.properties.ApiProperties;
import com.promotick.lafabril.admin.service.RolAdminService;
import com.promotick.lafabril.common.ConstantesSesion;
import com.promotick.lafabril.model.admin.RolUrl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
public class DbFilterInvocationSecurityMetadataSource implements FilterInvocationSecurityMetadataSource {

    @Autowired
    private RolAdminService rolAdminService;

    @Autowired
    private ApiProperties apiProperties;

    @Override
    @SuppressWarnings("unchecked")
    public Collection<ConfigAttribute> getAttributes(Object object) throws IllegalArgumentException {

        try {
            FilterInvocation filterInvocation = (FilterInvocation) object;
            String url = filterInvocation.getRequestUrl();

            if (apiProperties.getClient().getLogger()) {
                log.info("Request url: " + url);
            }

            LinkedHashMap<String, List<String>> map = (LinkedHashMap<String, List<String>>) filterInvocation.getRequest().getSession().getAttribute(ConstantesSesion.SESSION_MAPA_ROLES_URL);

            if (map == null) {
                map = rolesToMap(rolAdminService.obtenerRolesUrl());
                filterInvocation.getRequest().getSession().setAttribute(ConstantesSesion.SESSION_MAPA_ROLES_URL, map);
            }

            List<String> roles = getRoles(map, url);

            if (apiProperties.getClient().getLogger()) {
                log.info("Rol asociado a url: " + roles);
                log.info("------------------");
            }

            if (roles.isEmpty()) {
                throw new Exception("Roles vacios");
            }

            return SecurityConfig.createList(roles.toArray(new String[0]));
        } catch (Exception e) {
            log.error("DbFilterInvocationSecurityMetadataSource: " + e.getMessage());
            return null;
        }

    }

    @Override
    public Collection<ConfigAttribute> getAllConfigAttributes() {
        return null;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return true;
    }


    private List<String> getRoles(LinkedHashMap<String, List<String>> mapa, String url) {

        Map.Entry<String, List<String>> dqdwq = mapa.entrySet().stream()
                .filter(e -> {
                    if (url.equals("/")) {
                        return url.equals(e.getKey());
                    } else {
                        return url.startsWith(e.getKey()) && !e.getKey().equals("/");
                    }
                })
                .findAny()
                .orElse(null);

        if (dqdwq == null) {
            return new ArrayList<>();
        }
        return dqdwq.getValue();
    }


    private LinkedHashMap<String, List<String>> rolesToMap(List<RolUrl> roleActions) {
        return roleActions.stream()
                .collect(
                        Collectors.groupingBy(
                                RolUrl::getUrlMenu,
                                LinkedHashMap::new,
                                Collectors.mapping(e ->
                                                SpringUtil.PREFIJO_ROL + e.getNombreRol(),
                                        Collectors.toList()
                                )
                        )
                );
    }

}
