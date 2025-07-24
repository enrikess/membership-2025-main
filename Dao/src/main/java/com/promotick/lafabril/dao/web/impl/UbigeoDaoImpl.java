package com.promotick.lafabril.dao.web.impl;

import com.promotick.lafabril.dao.utils.ConstantesAdminDAO;
import com.promotick.lafabril.dao.utils.ConstantesWebDAO;
import com.promotick.lafabril.dao.web.UbigeoDao;
import com.promotick.lafabril.dao.web.definition.TipoViviendaDaoDefinition;
import com.promotick.lafabril.dao.web.definition.ZonaDaoDefinition;
import com.promotick.lafabril.common.ConstantesCommon;
import com.promotick.lafabril.dao.web.definition.UbigeoDaoDefinition;
import com.promotick.lafabril.model.web.TipoVivienda;
import com.promotick.lafabril.model.web.Ubigeo;
import com.promotick.configuration.utils.dao.DaoBuilder;
import com.promotick.configuration.utils.dao.DaoGenerator;
import com.promotick.lafabril.model.web.Zona;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.Types;
import java.util.List;

@Repository
public class UbigeoDaoImpl extends DaoGenerator implements UbigeoDao {

    private UbigeoDaoDefinition ubigeoDaoDefinition;
    private ZonaDaoDefinition zonaDaoDefinition;
    private TipoViviendaDaoDefinition tipoViviendaDaoDefinition;

    @Autowired
    public UbigeoDaoImpl(JdbcTemplate jdbcTemplate, UbigeoDaoDefinition ubigeoDaoDefinition, ZonaDaoDefinition zonaDaoDefinition, TipoViviendaDaoDefinition tipoViviendaDaoDefinition) {
        super(jdbcTemplate);
        this.ubigeoDaoDefinition = ubigeoDaoDefinition;
        this.zonaDaoDefinition = zonaDaoDefinition;
        this.tipoViviendaDaoDefinition = tipoViviendaDaoDefinition;
    }

    @Override
    public List<Ubigeo> listarDepartamentos(String codPais) {
        return DaoBuilder.getInstance(this)
                .setLogger(ConstantesCommon.LOGGER)
                .setSchema(ConstantesWebDAO.SCHEMA_NAME)
                .setProcedureName(ConstantesWebDAO.SP_UBIGEO_DEPARTAMENTOS)
                .addParameter("var_codpais", Types.VARCHAR, codPais)
                .setDaoDefinition(ubigeoDaoDefinition)
                .build();
    }

    @Override
    public List<Ubigeo> listarProvincias(String codPais, String codDepartamento) {
        return DaoBuilder.getInstance(this)
                .setLogger(ConstantesCommon.LOGGER)
                .setSchema(ConstantesWebDAO.SCHEMA_NAME)
                .setProcedureName(ConstantesWebDAO.SP_UBIGEO_PROVINCIAS)
                .addParameter("var_codpais", Types.VARCHAR, codPais)
                .addParameter("var_coddep", Types.VARCHAR, codDepartamento)
                .setDaoDefinition(ubigeoDaoDefinition)
                .build();
    }

    @Override
    public List<Ubigeo> listarDistritos(String codPais, String codDepartamento, String codProvincia) {
        return DaoBuilder.getInstance(this)
                .setLogger(ConstantesCommon.LOGGER)
                .setSchema(ConstantesWebDAO.SCHEMA_NAME)
                .setProcedureName(ConstantesWebDAO.SP_UBIGEO_DISTRITOS)
                .addParameter("var_codpais", Types.VARCHAR, codPais)
                .addParameter("var_coddep", Types.VARCHAR, codDepartamento)
                .addParameter("var_codprov", Types.VARCHAR, codProvincia)
                .setDaoDefinition(ubigeoDaoDefinition)
                .build();
    }

    @Override
    public Ubigeo obtenerUbigeoID(Integer idUbigeo) {
        return DaoBuilder.getInstance(this)
                .setLogger(ConstantesCommon.LOGGER)
                .setSchema(ConstantesWebDAO.SCHEMA_NAME)
                .setProcedureName(ConstantesWebDAO.SP_UBIGEO_OBTENER_ID)
                .addParameter("var_id_ubigeo", Types.INTEGER, idUbigeo)
                .setDaoDefinition(ubigeoDaoDefinition)
                .build(Ubigeo.class);
    }

    @Override
    public List<Ubigeo> listarUbigeos(String codpais) {
        return DaoBuilder.getInstance(this)
                .setLogger(ConstantesCommon.LOGGER)
                .setSchema(ConstantesAdminDAO.SCHEMA_NAME)
                .setProcedureName(ConstantesAdminDAO.SP_UBIGEO_LISTAR)
                .addParameter("var_codpais", Types.VARCHAR, codpais)
                .setDaoDefinition(ubigeoDaoDefinition)
                .build();
    }

    @Override
    public List<Zona> listarZonas() {
        return DaoBuilder.getInstance(this)
                .setLogger(ConstantesCommon.LOGGER)
                .setSchema(ConstantesAdminDAO.SCHEMA_NAME)
                .setProcedureName(ConstantesAdminDAO.SP_UBIGEO_ZONA_LISTAR)
                .setDaoDefinition(zonaDaoDefinition)
                .build();
    }

    @Override
    public List<TipoVivienda> listarTipoVivienda() {
        return DaoBuilder.getInstance(this)
                .setLogger(ConstantesCommon.LOGGER)
                .setSchema(ConstantesAdminDAO.SCHEMA_NAME)
                .setProcedureName(ConstantesAdminDAO.SP_UBIGEO_TIPO_VIVIENDA_LISTAR)
                .setDaoDefinition(tipoViviendaDaoDefinition)
                .build();
    }
}
