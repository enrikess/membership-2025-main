package com.promotick.lafabril.dao.admin.definition;

import com.promotick.lafabril.model.admin.GraficoVisitas;
import com.promotick.configuration.utils.dao.DaoDefinition;
import org.apache.logging.log4j.util.Strings;
import org.springframework.stereotype.Component;
import org.thymeleaf.util.StringUtils;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

@Component
public class GraficoVisitasDaoDefinition extends DaoDefinition<GraficoVisitas> {
    public GraficoVisitasDaoDefinition() {
        super(GraficoVisitas.class);
    }

    @Override
    public GraficoVisitas mapRow(ResultSet rs, int rowNumber) throws SQLException {

        GraficoVisitas graficoVisitas = new GraficoVisitas();

        ResultSetMetaData rsmd = rs.getMetaData();
        int columns = rsmd.getColumnCount();

        for (int x = 1; x <= columns; ++x) {
            graficoVisitas.getColumnas().add(this.clearColumn(rsmd.getColumnName(x)));
            graficoVisitas.getValores().add(rs.getInt(x));
        }

        return graficoVisitas;
    }

    private String clearColumn(String cad) {
        if (StringUtils.isEmpty(cad)) {
            return Strings.EMPTY;
        }
        return cad.replace("day_", "").replace("_", "/");
    }
}
