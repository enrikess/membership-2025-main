package com.promotick.lafabril.dao.web.impl;

import com.promotick.lafabril.dao.admin.definition.ReporteMarcaExcelDaoDefinition;
import com.promotick.lafabril.dao.utils.ConstantesWebDAO;
import com.promotick.lafabril.dao.web.MarcaDao;
import com.promotick.lafabril.dao.web.definition.MarcaDaoDefinition;
import com.promotick.lafabril.common.ConstantesCommon;
import com.promotick.lafabril.model.reporte.ReporteMarca;
import com.promotick.lafabril.model.util.FiltroMarca;
import com.promotick.lafabril.model.web.Marca;
import com.promotick.configuration.utils.dao.DaoBuilder;
import com.promotick.configuration.utils.dao.DaoGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.Types;
import java.util.List;

@Repository
public class MarcaDaoImpl extends DaoGenerator implements MarcaDao {

    private MarcaDaoDefinition marcaDaoDefinition;
    private ReporteMarcaExcelDaoDefinition reporteMarcaExcelDaoDefinition;

    @Autowired
    public MarcaDaoImpl(JdbcTemplate jdbcTemplate, MarcaDaoDefinition marcaDaoDefinition, ReporteMarcaExcelDaoDefinition reporteMarcaExcelDaoDefinition) {
        super(jdbcTemplate);
        this.marcaDaoDefinition = marcaDaoDefinition;
        this.reporteMarcaExcelDaoDefinition = reporteMarcaExcelDaoDefinition;
    }

    @Override
    public List<Marca> listarMarcas(FiltroMarca filtroMarca) {
        return DaoBuilder.getInstance(this)
                .setLogger(ConstantesCommon.LOGGER)
                .setSchema(ConstantesWebDAO.SCHEMA_NAME)
                .setProcedureName(ConstantesWebDAO.SP_MARCAS_LISTAR)
                .addParameter("var_id_marca", Types.INTEGER, filtroMarca.getIdMarca())
                .addParameter("var_orden", Types.INTEGER, filtroMarca.getOrden())
                .addParameter("var_inicio", Types.INTEGER, filtroMarca.getStart())
                .addParameter("var_tamanio", Types.INTEGER, filtroMarca.getLength())
                .setDaoDefinition(marcaDaoDefinition)
                .build();
    }

    @Override
    public Integer actualizarMarca(Marca marca) {
        return DaoBuilder.getInstance(this)
                .setLogger(ConstantesCommon.LOGGER)
                .setSchema(ConstantesWebDAO.SCHEMA_NAME)
                .setProcedureName(ConstantesWebDAO.SP_MARCA_ACTUALIZAR)
                .addParameter("var_accion", Types.INTEGER, marca.getAccion())
                .addParameter("var_id_marca", Types.INTEGER, marca.getIdMarca())
                .addParameter("var_nombre_marca", Types.VARCHAR, marca.getNombreMarca())
                .addParameter("var_descripcion_corta", Types.VARCHAR, marca.getDescripcionCorta())
                .addParameter("var_descripcion_larga", Types.VARCHAR, marca.getDescripcionLarga())
                .addParameter("var_estado_marca", Types.INTEGER, marca.getEstadoMarca())
                .addParameter("var_usuario_creacion", Types.VARCHAR, marca.getAuditoria().getUsuarioActualizacion())
                .setReturnDaoParameter("resultado", Types.INTEGER)
                .build(Integer.class);
    }

    @Override
    public Integer registroMarca(Marca marca) {
        return DaoBuilder.getInstance(this)
                .setLogger(ConstantesCommon.LOGGER)
                .setSchema(ConstantesWebDAO.SCHEMA_NAME)
                .setProcedureName(ConstantesWebDAO.SP_MARCA_REGISTRAR)
                .addParameter("var_nombre_marca", Types.VARCHAR, marca.getNombreMarca())
                .addParameter("var_descripcion_corta", Types.VARCHAR, marca.getDescripcionCorta())
                .addParameter("var_descripcion_larga", Types.VARCHAR, marca.getDescripcionLarga())
                .addParameter("var_estado_marca", Types.INTEGER, marca.getEstadoMarca())
                .addParameter("var_usuario_creacion", Types.VARCHAR, marca.getAuditoria().getUsuarioCreacion())
                .setReturnDaoParameter("resultado", Types.INTEGER)
                .build(Integer.class);
    }


    @Override
    public Integer contarMarcas() {
        return DaoBuilder.getInstance(this)
                .setLogger(ConstantesCommon.LOGGER)
                .setSchema(ConstantesWebDAO.SCHEMA_NAME)
                .setProcedureName(ConstantesWebDAO.SP_MARCA_CONTAR)
                .setReturnDaoParameter("resultado", Types.INTEGER)
                .build(Integer.class);
    }

    @Override
    public List<Marca> listarMarcasFiltro(FiltroMarca filtroMarca) {
        return DaoBuilder.getInstance(this)
                .setLogger(ConstantesCommon.LOGGER)
                .setSchema(ConstantesWebDAO.SCHEMA_NAME)
                .setProcedureName(ConstantesWebDAO.SP_MARCAS_FILTRO_LISTAR)
                .addParameter("var_id_marca", Types.INTEGER, filtroMarca.getIdMarca())
                .addParameter("var_nombre_marca", Types.VARCHAR, filtroMarca.getNombreMarca())
                .addParameter("var_orden", Types.INTEGER, filtroMarca.getOrden())
                .addParameter("var_inicio", Types.INTEGER, filtroMarca.getStart())
                .addParameter("var_tamanio", Types.INTEGER, filtroMarca.getLength())
                .setDaoDefinition(marcaDaoDefinition)
                .build();
    }

    @Override
    public Integer contarMarcasFiltro(FiltroMarca filtroMarca) {
        return DaoBuilder.getInstance(this)
                .setLogger(ConstantesCommon.LOGGER)
                .setSchema(ConstantesWebDAO.SCHEMA_NAME)
                .setProcedureName(ConstantesWebDAO.SP_MARCA_FILTRO_CONTAR)
                .addParameter("var_id_marca", Types.INTEGER, filtroMarca.getIdMarca())
                .addParameter("var_nombre_marca", Types.VARCHAR, filtroMarca.getNombreMarca())
                .setReturnDaoParameter("resultado", Types.INTEGER)
                .build(Integer.class);
    }

    @Override
    public List<ReporteMarca> reporteMarcas(FiltroMarca filtroMarca) {
        return DaoBuilder.getInstance(this)
                .setLogger(ConstantesCommon.LOGGER)
                .setSchema(ConstantesWebDAO.SCHEMA_NAME)
                .setProcedureName(ConstantesWebDAO.SP_MARCAS_FILTRO_LISTAR)
                .addParameter("var_id_marca", Types.INTEGER, filtroMarca.getIdMarca())
                .addParameter("var_nombre_marca", Types.VARCHAR, filtroMarca.getNombreMarca())
                .addParameter("var_orden", Types.INTEGER, filtroMarca.getOrden())
                .addParameter("var_inicio", Types.INTEGER, filtroMarca.getStart())
                .addParameter("var_tamanio", Types.INTEGER, filtroMarca.getLength())
                .setDaoDefinition(reporteMarcaExcelDaoDefinition)
                .build();
    }

}
