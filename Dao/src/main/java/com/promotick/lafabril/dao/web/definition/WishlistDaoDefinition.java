package com.promotick.lafabril.dao.web.definition;

import com.promotick.lafabril.model.web.Wishlist;
import com.promotick.configuration.utils.dao.DaoDefinition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;

@Repository
public class WishlistDaoDefinition extends DaoDefinition<Wishlist> {

    private ParticipanteDaoDefinition participanteDaoDefinition;
    private ProductoDaoDefinition productoDaoDefinition;

    @Autowired
    public WishlistDaoDefinition(ParticipanteDaoDefinition participanteDaoDefinition, ProductoDaoDefinition productoDaoDefinition) {
        super(Wishlist.class);
        this.participanteDaoDefinition = participanteDaoDefinition;
        this.productoDaoDefinition = productoDaoDefinition;
    }

    @Override
    public Wishlist mapRow(ResultSet rs, int rowNumber) throws SQLException {
        Wishlist wishlist = super.mapRow(rs, rowNumber);
        wishlist.setParticipante(participanteDaoDefinition.mapRow(rs, rowNumber));
        wishlist.setProducto(productoDaoDefinition.mapRow(rs, rowNumber));
        return wishlist;
    }
}
