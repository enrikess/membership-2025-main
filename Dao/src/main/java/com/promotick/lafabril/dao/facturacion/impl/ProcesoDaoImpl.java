package com.promotick.lafabril.dao.facturacion.impl;

import com.promotick.lafabril.dao.facturacion.ProcesoDao;
import com.promotick.lafabril.dao.utils.ConstantesAdminDAO;
import com.promotick.lafabril.dao.utils.ConstantesFacturacionDAO;
import com.promotick.lafabril.common.ConstantesCommon;
import com.promotick.lafabril.model.facturacion.Proceso;
import com.promotick.configuration.utils.dao.DaoBuilder;
import com.promotick.configuration.utils.dao.DaoGenerator;
import com.promotick.configuration.utils.dao.DaoResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.Types;

@Repository
public class ProcesoDaoImpl extends DaoGenerator implements ProcesoDao {

    @Autowired
    public ProcesoDaoImpl(JdbcTemplate jdbcTemplate) {
        super(jdbcTemplate);
    }

    @Override
    public Integer registrarProceso(String tipoProceso) {
        return DaoBuilder.getInstance(this)
                .setLogger(ConstantesCommon.LOGGER)
                .setSchema(ConstantesFacturacionDAO.SCHEMA_NAME)
                .setProcedureName(ConstantesFacturacionDAO.SP_PROCESO_REGISTRAR)
                .addParameter("var_tipo_proceso", Types.VARCHAR, tipoProceso)
                .setReturnDaoParameter("resultado", Types.INTEGER)
                .build(Integer.class);
    }

    @Override
    public Integer finalizarProceso(Proceso proceso) {
        return DaoBuilder.getInstance(this)
                .setLogger(ConstantesCommon.LOGGER)
                .setSchema(ConstantesFacturacionDAO.SCHEMA_NAME)
                .setProcedureName(ConstantesFacturacionDAO.SP_PROCESO_FINALIZAR)
                .addParameter("var_id_proceso", Types.INTEGER, proceso.getIdProceso())
                .addParameter("var_estado_proceso", Types.INTEGER, proceso.getEstadoProceso())
                .addParameter("var_mensaje", Types.VARCHAR, proceso.getMensaje())
                .setReturnDaoParameter("resultado", Types.INTEGER)
                .build(Integer.class);
    }

    @Override
    public Boolean procesoEnEjecucion() {
        return DaoBuilder.getInstance(this)
                .setLogger(ConstantesCommon.LOGGER)
                .setSchema(ConstantesFacturacionDAO.SCHEMA_NAME)
                .setProcedureName(ConstantesFacturacionDAO.SP_PROCESO_EN_EJECUCION)
                .setReturnDaoParameter("resultado", Types.BOOLEAN)
                .build(Boolean.class);
    }

    @Override
    public Integer procesoVencimientoPuntos() {
        return DaoBuilder.getInstance(this)
                .setLogger(ConstantesCommon.LOGGER)
                .setSchema(ConstantesFacturacionDAO.SCHEMA_NAME)
                .setProcedureName(ConstantesFacturacionDAO.SP_PROCESO_VENCIMIENTO)
                .setReturnDaoParameter("resultado", Types.INTEGER)
                .build(Integer.class);
    }

    @Override
    public void initRestaurarProductos() {
        DaoBuilder.getInstance(this)
                .setLogger(ConstantesCommon.LOGGER)
                .setSchema(ConstantesAdminDAO.SCHEMA_NAME)
                .setProcedureName(ConstantesAdminDAO.SP_RESTAURAR_PRODUCTOS)
                .setReturnDaoParameter("resultado", Types.INTEGER)
                .build(DaoResult.class);
    }
}
