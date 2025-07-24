package com.promotick.lafabril.dao.web.definition;

import com.promotick.lafabril.model.web.ProductoCatalogo;
import com.promotick.configuration.utils.dao.DaoDefinition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class ProductoCatalogoDaoDefinition extends DaoDefinition<ProductoCatalogo> {

    private ProductoDaoDefinition productoDaoDefinition;
    private CatalogoDaoDefinition catalogoDaoDefinition;

    @Autowired
    public ProductoCatalogoDaoDefinition(ProductoDaoDefinition productoDaoDefinition, CatalogoDaoDefinition catalogoDaoDefinition) {
        super(ProductoCatalogo.class);
        this.productoDaoDefinition = productoDaoDefinition;
        this.catalogoDaoDefinition = catalogoDaoDefinition;
    }

    @Override
    public ProductoCatalogo mapRow(ResultSet rs, int rowNumber) throws SQLException {
        ProductoCatalogo productoCatalogo = super.mapRow(rs, rowNumber);
        if (productoCatalogo != null) {
            productoCatalogo.setCatalogo(catalogoDaoDefinition.mapRow(rs, rowNumber));
            productoCatalogo.setProducto(productoDaoDefinition.mapRow(rs, rowNumber));
        }
        return productoCatalogo;
    }
}
