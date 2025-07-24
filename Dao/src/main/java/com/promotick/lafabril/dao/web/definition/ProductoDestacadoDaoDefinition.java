package com.promotick.lafabril.dao.web.definition;

import com.promotick.lafabril.model.web.ProductoDestacado;
import com.promotick.configuration.utils.dao.DaoDefinition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;

@Repository
public class ProductoDestacadoDaoDefinition extends DaoDefinition<ProductoDestacado> {

    private ProductoDaoDefinition productoDaoDefinition;
    private CatalogoDaoDefinition catalogoDaoDefinition;

    @Autowired
    public ProductoDestacadoDaoDefinition(ProductoDaoDefinition productoDaoDefinition, CatalogoDaoDefinition catalogoDaoDefinition) {
        super(ProductoDestacado.class);
        this.productoDaoDefinition = productoDaoDefinition;
        this.catalogoDaoDefinition = catalogoDaoDefinition;
    }

    @Override
    public ProductoDestacado mapRow(ResultSet rs, int rowNumber) throws SQLException {
        ProductoDestacado productoCatalogo = super.mapRow(rs, rowNumber);
        if (productoCatalogo != null) {
            productoCatalogo.setCatalogo(catalogoDaoDefinition.mapRow(rs, rowNumber));
            productoCatalogo.setProducto(productoDaoDefinition.mapRow(rs, rowNumber));
        }
        return productoCatalogo;
    }
}
