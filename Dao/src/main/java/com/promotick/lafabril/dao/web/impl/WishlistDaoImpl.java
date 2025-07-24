package com.promotick.lafabril.dao.web.impl;

import com.promotick.lafabril.dao.utils.ConstantesWebDAO;
import com.promotick.lafabril.dao.web.WishlistDao;
import com.promotick.lafabril.dao.web.definition.WishlistDaoDefinition;
import com.promotick.lafabril.common.ConstantesCommon;
import com.promotick.lafabril.model.web.Wishlist;
import com.promotick.configuration.utils.dao.DaoBuilder;
import com.promotick.configuration.utils.dao.DaoGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.Types;
import java.util.List;

@Repository
public class WishlistDaoImpl extends DaoGenerator implements WishlistDao {

    private WishlistDaoDefinition wishlistDaoDefinition;

    @Autowired
    public WishlistDaoImpl(JdbcTemplate jdbcTemplate, WishlistDaoDefinition wishlistDaoDefinition) {
        super(jdbcTemplate);
        this.wishlistDaoDefinition = wishlistDaoDefinition;
    }

    @Override
    public List<Wishlist> listarWishlist(Integer idParticipante) {
        return DaoBuilder.getInstance(this)
                .setLogger(ConstantesCommon.LOGGER)
                .setSchema(ConstantesWebDAO.SCHEMA_NAME)
                .setProcedureName(ConstantesWebDAO.SP_WISHLIST_PARTICIPANTE_LISTAR)
                .addParameter("var_id_participante", Types.INTEGER, idParticipante)
                .setDaoDefinition(wishlistDaoDefinition)
                .build();
    }

    @Override
    public Integer updateWishlist(Wishlist wishlist) {
        return DaoBuilder.getInstance(this)
                .setLogger(ConstantesCommon.LOGGER)
                .setSchema(ConstantesWebDAO.SCHEMA_NAME)
                .setProcedureName(ConstantesWebDAO.SP_WISHLIST_PRODUCTO_UPDATE)
                .addParameter("var_id_participante", Types.INTEGER, wishlist.getParticipante().getIdParticipante())
                .addParameter("var_id_producto", Types.INTEGER, wishlist.getProducto().getIdProducto())
                .addParameter("var_cantidadproducto", Types.INTEGER, wishlist.getCantidadProducto())
                .addParameter("var_accion", Types.INTEGER, wishlist.getAccion())
                .addParameter("var_usuario_actualizacion", Types.VARCHAR, wishlist.getAuditoria().getUsuarioActualizacion())
                .setReturnDaoParameter("resultado", Types.INTEGER)
                .build(Integer.class);
    }

}
