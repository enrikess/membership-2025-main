package com.promotick.lafabril.model.admin;

import com.promotick.lafabril.model.util.BeanBase;
import lombok.Data;

import java.util.List;

@Data
public class Rol extends BeanBase {

    private static final long serialVersionUID = 7286618958431970445L;
    private Integer idRol;
    private String nombreRol;
    private String descripcionRol;
    private Integer estadoRol;
    private List<Menu> menus;
    private Boolean esSuperUsuario;

    //Temp
    private String idMenus;

}
