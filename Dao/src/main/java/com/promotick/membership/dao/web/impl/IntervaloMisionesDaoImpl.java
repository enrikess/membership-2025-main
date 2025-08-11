package com.promotick.membership.dao.web.impl;

import com.promotick.configuration.utils.dao.DaoBuilder;
import com.promotick.configuration.utils.dao.DaoGenerator;
import com.promotick.membership.dao.utils.ConstantesWebDAO;
import com.promotick.membership.dao.web.IntervaloMisionesDao;
import com.promotick.membership.model.web.IntervaloMisiones;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.List;

@Repository
public class IntervaloMisionesDaoImpl extends DaoGenerator implements IntervaloMisionesDao {

    private final JdbcTemplate jdbcTemplate;

    public IntervaloMisionesDaoImpl(JdbcTemplate jdbcTemplate) {
        super(jdbcTemplate);
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public IntervaloMisiones obtenerIntervaloMisiones() {
        try {
            // Usar stored procedure para obtener intervalo_time
            Long intervaloTime = DaoBuilder.getInstance(this)
                    .setLogger(ConstantesWebDAO.LOGGER)
                    .setSchema(ConstantesWebDAO.SCHEMA_NAME)
                    .setProcedureName(ConstantesWebDAO.SP_INTERVALO_MISIONES)
                    .setReturnDaoParameter("resultado", Types.BIGINT)
                    .build(Long.class);
            
            System.out.println("✅ Valor obtenido del stored procedure: " + intervaloTime);
            
            // Crear IntervaloMisiones con el intervalo obtenido
            return IntervaloMisiones.builder()
                    .id(1L)
                    .timer(intervaloTime != null ? intervaloTime : 0L)
                    .build();
                    
        } catch (Exception e) {
            System.err.println("⚠️ Error con stored procedure, usando consulta SQL directa: " + e.getMessage());
            
            // Si falla el SP, usar consulta SQL directa
            try {
                String sql = "SELECT intervalo_time FROM sch_general.tb_configuracion_web ORDER BY id_configuracion_web ASC LIMIT 1";
                Long intervaloTime = jdbcTemplate.queryForObject(sql, Long.class);
                
                System.out.println("✅ Valor obtenido de consulta SQL directa: " + intervaloTime);
                
                return IntervaloMisiones.builder()
                        .id(1L)
                        .timer(intervaloTime != null ? intervaloTime : 0L)
                        .build();
                        
            } catch (Exception sqlException) {
                System.err.println("⚠️ Error en consulta SQL: " + sqlException.getMessage());
                // Retornar valor por defecto
                return IntervaloMisiones.builder()
                        .id(1L)
                        .timer(86400000L) // 24 horas por defecto
                        .build();
            }
        }
    }
}