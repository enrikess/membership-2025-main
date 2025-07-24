package com.promotick.lafabril.web.security;

import com.promotick.lafabril.common.ConstantesCommon;
import com.promotick.lafabril.model.web.Participante;
import com.promotick.lafabril.web.service.ParticipanteWebService;
import lombok.RequiredArgsConstructor;
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
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final ParticipanteWebService participanteWebService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        String[] data = username.split("\\|");
        Participante participante = participanteWebService.loginParticipante(ConstantesCommon.ID_TIPO_DOC, data[0]);
        if (participante == null) {
            throw new UsernameNotFoundException("Credenciales Incorrectas");
        }

        Set<GrantedAuthority> setAuths = new HashSet<>(0);

        setAuths.add(new SimpleGrantedAuthority(SpringUtil.PREFIJO_ROL + "USER"));

        List<GrantedAuthority> authorities = new ArrayList<>(setAuths);

        return new SpringUser(
                participante,
                participante.getUsuarioParticipante(),
                participante.getClaveParticipante(),
                participante.getEstadoParticipante() == -2 || participante.getEstadoParticipante() >= 0,
                participante.getEstadoParticipante() != 0,
                participante.getEstadoParticipante() != -2,
                Boolean.TRUE,
                authorities
        );
    }
}
