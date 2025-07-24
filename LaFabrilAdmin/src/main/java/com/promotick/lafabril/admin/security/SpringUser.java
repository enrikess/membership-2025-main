package com.promotick.lafabril.admin.security;

import com.promotick.lafabril.model.admin.Usuario;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

@Data
public class SpringUser extends User {

    private static final long serialVersionUID = -3412180338469178220L;

    private Usuario usuario;

    public SpringUser(Usuario usuario,
                      String username,
                      String password,
                      boolean enabled,
                      boolean accountNonExpired,
                      boolean credentialsNonExpired,
                      boolean accountNonLocked,
                      Collection<? extends GrantedAuthority> authorities) {
        super(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
        this.usuario = usuario;
    }
}