package com.promotick.lafabril.dao.admin.definition;

import com.promotick.lafabril.model.admin.Usuario;
import com.promotick.configuration.utils.dao.DaoDefinition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class UsuarioDaoDefinition extends DaoDefinition<Usuario> {

    private RolDaoDefinition rolDaoDefinition;

    @Autowired
    public UsuarioDaoDefinition(RolDaoDefinition rolDaoDefinition) {
        super(Usuario.class);
        this.rolDaoDefinition = rolDaoDefinition;
    }

    @Override
    public Usuario mapRow(ResultSet rs, int rowNumber) throws SQLException {
        Usuario usuario = super.mapRow(rs, rowNumber);
        usuario.setRol(rolDaoDefinition.mapRow(rs, rowNumber));
        return usuario;
    }

}