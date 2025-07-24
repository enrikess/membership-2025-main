package com.promotick.lafabril.model.admin;


import com.promotick.lafabril.model.util.BeanBase;
import lombok.Data;

@Data
public class Menu extends BeanBase {
    private static final long serialVersionUID = 7627402159143842969L;

    private Integer idMenu;
    private Integer idPadre;
    private String nombreMenu;
    private String urlMenu;
    private String iconoMenu;
    private Integer estadoMenu;
    private Integer ordenMenu;
    private String modulo;
}
