package com.promotick.lafabril.dao.web.definition;

import com.promotick.configuration.utils.dao.DaoDefinition;
import com.promotick.lafabril.model.web.ProductoPromocion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;

@Repository
public class ProductoPromocionDaoDefinition extends DaoDefinition<ProductoPromocion> {

    private ProductoDaoDefinition productoDaoDefinition;
    private CatalogoDaoDefinition catalogoDaoDefinition;

    @Autowired
    public ProductoPromocionDaoDefinition(ProductoDaoDefinition productoDaoDefinition, CatalogoDaoDefinition catalogoDaoDefinition) {
        super(ProductoPromocion.class);
        this.productoDaoDefinition = productoDaoDefinition;
        this.catalogoDaoDefinition = catalogoDaoDefinition;
    }

    @Override
    public ProductoPromocion mapRow(ResultSet rs, int rowNumber) throws SQLException {
        ProductoPromocion productoPromocion = super.mapRow(rs, rowNumber);
        if (productoPromocion != null) {
            productoPromocion.setCatalogo(catalogoDaoDefinition.mapRow(rs, rowNumber));
            productoPromocion.setProducto(productoDaoDefinition.mapRow(rs, rowNumber));
        }
        return productoPromocion;
    }
}
