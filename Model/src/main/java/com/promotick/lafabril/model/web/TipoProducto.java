package com.promotick.lafabril.model.web;

import com.promotick.lafabril.model.util.UtilEnum;
import lombok.Data;

import java.io.Serializable;

@Data
public class TipoProducto implements Serializable {
    private static final long serialVersionUID = -3061695340238112563L;

    private Integer idTipoProducto;
    private String nombreTipoProducto;
    private String descripcionTipoProducto;
    private Integer estadoTipoProducto;

    public boolean isRegular() {
        UtilEnum.TIPO_PRODUCTO tp = UtilEnum.TIPO_PRODUCTO.getByCode(this.idTipoProducto);
        return tp != null && tp.equals(UtilEnum.TIPO_PRODUCTO.REGULAR);
    }

    public boolean isPrimax() {
        UtilEnum.TIPO_PRODUCTO tp = UtilEnum.TIPO_PRODUCTO.getByCode(this.idTipoProducto);
        return tp != null && tp.equals(UtilEnum.TIPO_PRODUCTO.PRIMAX);
    }

    public boolean isRecargaCelular() {
        UtilEnum.TIPO_PRODUCTO tp = UtilEnum.TIPO_PRODUCTO.getByCode(this.idTipoProducto);
        return tp != null && tp.equals(UtilEnum.TIPO_PRODUCTO.RECARGA_MOVIL);
    }

    public boolean isRecargaTv() {
        UtilEnum.TIPO_PRODUCTO tp = UtilEnum.TIPO_PRODUCTO.getByCode(this.idTipoProducto);
        return tp != null && tp.equals(UtilEnum.TIPO_PRODUCTO.RECARGA_TV);
    }

    public boolean isColores() {
        UtilEnum.TIPO_PRODUCTO tp = UtilEnum.TIPO_PRODUCTO.getByCode(this.idTipoProducto);
        return tp != null && tp.equals(UtilEnum.TIPO_PRODUCTO.COLORES);
    }

    public boolean isCorreo() {
        UtilEnum.TIPO_PRODUCTO tp = UtilEnum.TIPO_PRODUCTO.getByCode(this.idTipoProducto);
        return tp != null && tp.equals(UtilEnum.TIPO_PRODUCTO.CORREO);
    }
}
