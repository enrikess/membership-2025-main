package com.promotick.lafabril.model.admin;

import com.promotick.lafabril.model.util.BeanBase;
import lombok.Data;

@Data
public class Usuario extends BeanBase {

    private static final long serialVersionUID = -5764491740006850532L;

    private Integer idUsuario;
    private Rol rol;
    private String usuario;
    private String clave;
    private String nombresUsuario;
    private String apellidoPaterno;
    private String apellidoMaterno;
    private String correoUsuario;
    private Integer estadoUsuario;

    //Temp
    private String nuevaClave;
}
