package com.promotick.lafabril.dao.web.impl;

import com.promotick.lafabril.common.ConstantesCommon;
import com.promotick.lafabril.dao.utils.ConstantesAdminDAO;
import com.promotick.lafabril.dao.web.CampaniaDao;
import com.promotick.lafabril.dao.web.CatalogoDao;
import com.promotick.lafabril.dao.web.definition.CampaniaDaoDefinition;
import com.promotick.lafabril.dao.web.definition.CatalogoDaoDefinition;
import com.promotick.lafabril.model.web.Campania;
import com.promotick.lafabril.model.web.Catalogo;
import com.promotick.configuration.utils.dao.DaoBuilder;
import com.promotick.configuration.utils.dao.DaoGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.Types;
import java.util.List;

@Repository
public class CampaniaDaoImpl extends DaoGenerator implements CampaniaDao {

    private CampaniaDaoDefinition campaniaDaoDefinition;

    @Autowired
    public CampaniaDaoImpl(JdbcTemplate jdbcTemplate, CampaniaDaoDefinition campaniaDaoDefinition) {
        super(jdbcTemplate);
        this.campaniaDaoDefinition = campaniaDaoDefinition;
    }

    @Override
    public List<Campania> listarCampanias() {
        return DaoBuilder.getInstance(this)
                .setLogger(ConstantesCommon.LOGGER)
                .setSchema(ConstantesAdminDAO.SCHEMA_NAME)
                .setProcedureName(ConstantesAdminDAO.SP_CAMPANIA_LISTAR)
                .setDaoDefinition(campaniaDaoDefinition)
                .build();
    }

    @Override
    public List<Campania> listarCampanias(Integer idCampania, Integer orden) {
        return DaoBuilder.getInstance(this)
                .setLogger(ConstantesCommon.LOGGER)
                .setSchema(ConstantesAdminDAO.SCHEMA_NAME)
                .setProcedureName(ConstantesAdminDAO.SP_CAMPANIA_LISTAR)
                .addParameter("var_id_campania", Types.INTEGER, idCampania)
                .addParameter("var_orden", Types.INTEGER, orden)
                .setDaoDefinition(campaniaDaoDefinition)
                .build();
    }

    @Override
    public Integer actualizarCampania(Campania campania) {
        return DaoBuilder.getInstance(this)
                .setLogger(ConstantesCommon.LOGGER)
                .setSchema(ConstantesAdminDAO.SCHEMA_NAME)
                .setProcedureName(ConstantesAdminDAO.SP_CAMPANIA_ACTUALIZAR)
                .addParameter("var_id_campania", Types.INTEGER, campania.getIdCampania())
                .addParameter("var_nombre_campania", Types.VARCHAR, campania.getNombreCampania())
                .addParameter("var_fecha_inicio", Types.DATE, campania.getFechaInicio())
                .addParameter("var_fecha_fin", Types.DATE, campania.getFechaFin())
                .addParameter("var_num_ganadores", Types.INTEGER, campania.getNumGanadores())
                .addParameter("var_valor_puntos", Types.INTEGER, campania.getValorPuntos())
                .addParameter("var_usuario_creacion", Types.VARCHAR, campania.getAuditoria().getUsuarioCreacion())
                .setReturnDaoParameter("resultado", Types.INTEGER)
                .build(Integer.class);
    }

    @Override
    public Integer actualizarEstadoCampania(Integer idCampania, Integer estado) {
        return DaoBuilder.getInstance(this)
                .setLogger(ConstantesCommon.LOGGER)
                .setSchema(ConstantesAdminDAO.SCHEMA_NAME)
                .setProcedureName(ConstantesAdminDAO.SP_CAMPANIA_ACTUALIZAR_ESTADO)
                .addParameter("var_id_campania", Types.INTEGER, idCampania)
                .addParameter("var_estado", Types.INTEGER, estado)
                .setReturnDaoParameter("resultado", Types.INTEGER)
                .build(Integer.class);

    }

}
