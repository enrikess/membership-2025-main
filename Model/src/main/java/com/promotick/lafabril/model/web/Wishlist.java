package com.promotick.lafabril.model.web;

import com.promotick.lafabril.model.util.Auditoria;
import com.promotick.lafabril.model.util.BeanBase;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Wishlist extends BeanBase {
    private static final long serialVersionUID = -3711090564262004585L;

    private Integer idWishlist;
    private Participante participante;
    private Producto producto;
    private Integer estadoWishlist;
    private Integer cantidadProducto;

    public Wishlist(Auditoria auditoria) {
        this.setAuditoria(auditoria);
    }
}
