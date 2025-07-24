package com.promotick.lafabril.model.admin;

import lombok.Data;

import java.io.Serializable;

@Data
public class RolUrl implements Serializable {
    private static final long serialVersionUID = 6605918502029881201L;

    private String urlMenu;
    private String nombreRol;

}
