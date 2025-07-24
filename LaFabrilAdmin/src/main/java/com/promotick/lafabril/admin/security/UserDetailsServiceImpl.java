package com.promotick.lafabril.admin.security;

import com.promotick.lafabril.admin.service.UsuarioAdminService;
import com.promotick.lafabril.model.admin.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service("userDetailsService")
public class UserDetailsServiceImpl implements UserDetailsService {

    private UsuarioAdminService usuarioAdminService;

    @Autowired
    public UserDetailsServiceImpl(UsuarioAdminService usuarioAdminService) {
        this.usuarioAdminService = usuarioAdminService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Usuario usuario = usuarioAdminService.autenticar(new Usuario().setUsuario(username));
        if (usuario == null) {
            throw new UsernameNotFoundException("Credenciales Incorrectas");
        }

        Set<GrantedAuthority> setAuths = new HashSet<>(0);
        setAuths.add(new SimpleGrantedAuthority(SpringUtil.PREFIJO_ROL + usuario.getRol().getNombreRol()));

        List<GrantedAuthority> authorities = new ArrayList<>(setAuths);

        return new SpringUser(
                usuario,
                usuario.getUsuario(),
                usuario.getClave(),
                Boolean.TRUE,
                Boolean.TRUE,
                Boolean.TRUE,
                Boolean.TRUE,
                authorities
        );
    }
}
