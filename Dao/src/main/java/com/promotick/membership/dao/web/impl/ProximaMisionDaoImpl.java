package com.promotick.membership.dao.web.impl;

import com.promotick.configuration.utils.dao.DaoBuilder;
import com.promotick.configuration.utils.dao.DaoGenerator;
import com.promotick.membership.dao.utils.ConstantesWebDAO;
import com.promotick.membership.dao.web.ProximaMisionDao;
import com.promotick.membership.model.web.ProximaMision;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.List;

@Repository
public class ProximaMisionDaoImpl extends DaoGenerator implements ProximaMisionDao {

    private final JdbcTemplate jdbcTemplate;

    public ProximaMisionDaoImpl(JdbcTemplate jdbcTemplate) {
        super(jdbcTemplate);
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public ProximaMision obtenerProximaMisionConFechaMayor() {
        try {
            // Primero intentamos con stored procedure
            return DaoBuilder.getInstance(this)
                    .setLogger(ConstantesWebDAO.LOGGER)
                    .setSchema(ConstantesWebDAO.SCHEMA_NAME)
                    .setProcedureName(ConstantesWebDAO.SP_PROXIMA_MISION_FECHA_MAYOR)
                    .setReturnDaoParameter("resultado", Types.OTHER)
                    .build(ProximaMision.class);
        } catch (Exception e) {
            System.err.println("⚠️ Error con stored procedure, usando consulta SQL directa: " + e.getMessage());
            
            // Si falla el SP, usar consulta SQL directa
            try {
                String sql = "SELECT id, fecha FROM public.proxima_mision ORDER BY fecha DESC LIMIT 1";
                List<ProximaMision> resultados = jdbcTemplate.query(sql, new ProximaMisionRowMapper());
                return resultados.isEmpty() ? null : resultados.get(0);
            } catch (Exception sqlException) {
                System.err.println("⚠️ Error en consulta SQL: " + sqlException.getMessage());
                return null;
            }
        }
    }

    private static class ProximaMisionRowMapper implements RowMapper<ProximaMision> {
        @Override
        public ProximaMision mapRow(ResultSet rs, int rowNum) throws SQLException {
            ProximaMision proximaMision = new ProximaMision();
            proximaMision.setId(rs.getLong("id"));
            proximaMision.setFecha(rs.getTimestamp("fecha").toLocalDateTime());
            return proximaMision;
        }
    }
}
