package com.promotick.lafabril.dao.web.definition;

import com.promotick.lafabril.model.web.Producto;
import com.promotick.configuration.utils.dao.DaoDefinition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class ProductoDaoDefinition extends DaoDefinition<Producto> {

    private MarcaDaoDefinition marcaDaoDefinition;
    private TagProductoDaoDefinition tagProductoDaoDefinition;
    private TipoProductoDaoDefinition tipoProductoDaoDefinition;
    private CategoriaDaoDefinition categoriaDaoDefinition;

    @Autowired
    public ProductoDaoDefinition(TagProductoDaoDefinition tagProductoDaoDefinition,MarcaDaoDefinition marcaDaoDefinition, TipoProductoDaoDefinition tipoProductoDaoDefinition, CategoriaDaoDefinition categoriaDaoDefinition) {
        super(Producto.class);
        this.marcaDaoDefinition = marcaDaoDefinition;
        this.tipoProductoDaoDefinition = tipoProductoDaoDefinition;
        this.categoriaDaoDefinition = categoriaDaoDefinition;
        this.tagProductoDaoDefinition = tagProductoDaoDefinition;
    }

    @Override
    public Producto mapRow(ResultSet rs, int rowNumber) throws SQLException {
        Producto producto = super.mapRow(rs, rowNumber);
        if (producto != null) {
            producto.setCategoria(categoriaDaoDefinition.mapRow(rs, rowNumber));
            producto.setMarca(marcaDaoDefinition.mapRow(rs, rowNumber));
            producto.setTipoProducto(tipoProductoDaoDefinition.mapRow(rs, rowNumber));
            producto.setTagProducto(tagProductoDaoDefinition.mapRow(rs, rowNumber));
        }
        return producto;
    }
}
