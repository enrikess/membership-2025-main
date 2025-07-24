package com.promotick.lafabril.dao.web.definition;

import com.promotick.lafabril.model.web.ProductoNovedoso;
import com.promotick.configuration.utils.dao.DaoDefinition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class ProductoNovedosoDaoDefinition extends DaoDefinition<ProductoNovedoso> {
    private ProductoDaoDefinition productoDaoDefinition;
    private CatalogoDaoDefinition catalogoDaoDefinition;

    @Autowired
    public ProductoNovedosoDaoDefinition(ProductoDaoDefinition productoDaoDefinition, CatalogoDaoDefinition catalogoDaoDefinition) {
        super(ProductoNovedoso.class);
        this.productoDaoDefinition = productoDaoDefinition;
        this.catalogoDaoDefinition = catalogoDaoDefinition;
    }

    @Override
    public ProductoNovedoso mapRow(ResultSet rs, int rowNumber) throws SQLException {
        ProductoNovedoso productoNovedoso = super.mapRow(rs, rowNumber);
        productoNovedoso.setProducto(productoDaoDefinition.mapRow(rs, rowNumber));
        productoNovedoso.setCatalogo(catalogoDaoDefinition.mapRow(rs, rowNumber));
        return productoNovedoso;
    }
}
