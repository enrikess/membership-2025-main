package com.promotick.lafabril.dao.web.definition;

import com.promotick.lafabril.model.web.ProductoVisitado;
import com.promotick.configuration.utils.dao.DaoDefinition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class ProductoVisitadoDaoDefinition extends DaoDefinition<ProductoVisitado> {
    private ProductoDaoDefinition productoDaoDefinition;
    private CatalogoDaoDefinition catalogoDaoDefinition;

    @Autowired
    public ProductoVisitadoDaoDefinition(ProductoDaoDefinition productoDaoDefinition, CatalogoDaoDefinition catalogoDaoDefinition) {
        super(ProductoVisitado.class);
        this.productoDaoDefinition = productoDaoDefinition;
        this.catalogoDaoDefinition = catalogoDaoDefinition;
    }

    @Override
    public ProductoVisitado mapRow(ResultSet rs, int rowNumber) throws SQLException {
        ProductoVisitado productoVisitado = super.mapRow(rs, rowNumber);
        productoVisitado.setProducto(productoDaoDefinition.mapRow(rs, rowNumber));
        productoVisitado.setCatalogo(catalogoDaoDefinition.mapRow(rs, rowNumber));
        return productoVisitado;
    }
}
