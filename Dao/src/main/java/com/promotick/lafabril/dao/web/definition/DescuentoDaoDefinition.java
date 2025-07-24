package com.promotick.lafabril.dao.web.definition;

import com.promotick.lafabril.model.web.Campania;
import com.promotick.lafabril.model.web.Descuento;
import com.promotick.configuration.utils.dao.DaoDefinition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;

@Repository
public class DescuentoDaoDefinition extends DaoDefinition<Descuento> {
    private CatalogoDaoDefinition catalogoDaoDefinition;
    private TipoDescuentoDaoDefinition tipoDescuentoDaoDefinition;

    @Autowired
    public DescuentoDaoDefinition(CatalogoDaoDefinition catalogoDaoDefinition, TipoDescuentoDaoDefinition tipoDescuentoDaoDefinition) {
        super(Descuento.class);
        this.catalogoDaoDefinition = catalogoDaoDefinition;
        this.tipoDescuentoDaoDefinition = tipoDescuentoDaoDefinition;
    }

    @Override
    public Descuento mapRow(ResultSet rs, int rowNumber) throws SQLException {
        Descuento descuento = super.mapRow(rs, rowNumber);
        if (descuento != null){
            descuento.setCatalogo(catalogoDaoDefinition.mapRow(rs, rowNumber));
            descuento.setTipoDescuento(tipoDescuentoDaoDefinition.mapRow(rs, rowNumber));
        }
        return descuento;
    }
}
