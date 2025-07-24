package com.promotick.lafabril.dao.web.impl;

import com.promotick.lafabril.dao.utils.ConstantesAdminDAO;
import com.promotick.lafabril.dao.web.CatalogoDao;
import com.promotick.lafabril.dao.web.definition.CatalogoDaoDefinition;
import com.promotick.lafabril.common.ConstantesCommon;
import com.promotick.lafabril.model.web.Catalogo;
import com.promotick.configuration.utils.dao.DaoBuilder;
import com.promotick.configuration.utils.dao.DaoGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.Types;
import java.util.List;

@Repository
public class CatalogoDaoImpl extends DaoGenerator implements CatalogoDao {

    private CatalogoDaoDefinition catalogoDaoDefinition;

    @Autowired
    public CatalogoDaoImpl(JdbcTemplate jdbcTemplate, CatalogoDaoDefinition catalogoDaoDefinition) {
        super(jdbcTemplate);
        this.catalogoDaoDefinition = catalogoDaoDefinition;
    }

    @Override
    public List<Catalogo> listarCatalogos() {
        return DaoBuilder.getInstance(this)
                .setLogger(ConstantesCommon.LOGGER)
                .setSchema(ConstantesAdminDAO.SCHEMA_NAME)
                .setProcedureName(ConstantesAdminDAO.SP_CATALOGO_LISTAR)
                .setDaoDefinition(catalogoDaoDefinition)
                .build();
    }

    @Override
    public List<Catalogo> listarCatalogos(Integer idCatalogo, Integer orden) {
        return DaoBuilder.getInstance(this)
                .setLogger(ConstantesCommon.LOGGER)
                .setSchema(ConstantesAdminDAO.SCHEMA_NAME)
                .setProcedureName(ConstantesAdminDAO.SP_CATALOGO_LISTAR)
                .addParameter("var_id_catalogo", Types.INTEGER, idCatalogo)
                .addParameter("var_orden", Types.INTEGER, orden)
                .setDaoDefinition(catalogoDaoDefinition)
                .build();
    }

    @Override
    public Integer actualizarCatalogo(Catalogo catalogo) {
        return DaoBuilder.getInstance(this)
                .setLogger(ConstantesCommon.LOGGER)
                .setSchema(ConstantesAdminDAO.SCHEMA_NAME)
                .setProcedureName(ConstantesAdminDAO.SP_CATALOGO_ACTUALIZAR)
                .addParameter("var_id_catalogo", Types.INTEGER, catalogo.getIdCatalogo())
                .addParameter("var_nombre_catalogo", Types.VARCHAR, catalogo.getNombreCatalogo())
                .addParameter("var_dias_envio", Types.INTEGER, catalogo.getDiasEnvio())
                .addParameter("var_id_catalogo_netsuite", Types.INTEGER, catalogo.getIdCatalogoNetsuite())
                .addParameter("var_codigo_catalogo", Types.VARCHAR, catalogo.getCodigoCatalogo())
                .addParameter("var_usuario_creacion", Types.VARCHAR, catalogo.getAuditoria().getUsuarioCreacion())
                .setReturnDaoParameter("resultado", Types.INTEGER)
                .build(Integer.class);
    }

    @Override
    public Integer actualizarEstadoCatalogo(Integer idCatalogo, Integer estado, Integer tipo) {
        return DaoBuilder.getInstance(this)
                .setLogger(ConstantesCommon.LOGGER)
                .setSchema(ConstantesAdminDAO.SCHEMA_NAME)
                .setProcedureName(ConstantesAdminDAO.SP_CATALOGO_ACTUALIZAR_ESTADO)
                .addParameter("var_id_catalogo", Types.INTEGER, idCatalogo)
                .addParameter("var_estado", Types.INTEGER, estado)
                .addParameter("var_tipo", Types.INTEGER, tipo)
                .setReturnDaoParameter("resultado", Types.INTEGER)
                .build(Integer.class);

    }

    @Override
    public List<Catalogo> listarCatalogoMultiselectSubcategoria(Integer idSubcategoria, Integer tipo) {
        return DaoBuilder.getInstance(this)
                .setLogger(ConstantesCommon.LOGGER)
                .setSchema(ConstantesAdminDAO.SCHEMA_NAME)
                .setProcedureName(ConstantesAdminDAO.SP_CATALOGO_MULTI_LISTAR)
                .addParameter("var_id_subcategoria", Types.INTEGER, idSubcategoria)
                .addParameter("var_tipo", Types.INTEGER, tipo)
                .setDaoDefinition(catalogoDaoDefinition)
                .build();
    }

    @Override
    public Integer actualizarPopupCatalogo(Catalogo catalogo) {
        return DaoBuilder.getInstance(this)
                .setLogger(ConstantesCommon.LOGGER)
                .setSchema(ConstantesAdminDAO.SCHEMA_NAME)
                .setProcedureName(ConstantesAdminDAO.SP_CATALOGO_POPUP_ACTUALIZAR)
                .addParameter("var_id_catalogo", Types.INTEGER, catalogo.getIdCatalogo())
                .addParameter("var_popup_inicio", Types.VARCHAR, catalogo.getPopupInicio())
                .addParameter("var_usuario_creacion", Types.VARCHAR, catalogo.getAuditoria().getUsuarioCreacion())
                .setReturnDaoParameter("resultado", Types.INTEGER)
                .build(Integer.class);
    }

    @Override
    public Integer actualizarBannerFlotanteCatalogo(Catalogo catalogo) {
        return DaoBuilder.getInstance(this)
                .setLogger(ConstantesCommon.LOGGER)
                .setSchema(ConstantesAdminDAO.SCHEMA_NAME)
                .setProcedureName(ConstantesAdminDAO.SP_CATALOGO_BANNER_FLOTANTE_ACTUALIZAR)
                .addParameter("var_id_catalogo", Types.INTEGER, catalogo.getIdCatalogo())
                .addParameter("var_banner_flotante", Types.VARCHAR, catalogo.getBannerFlotante())
                .addParameter("var_url_banner", Types.VARCHAR, catalogo.getUrlBannerFlotante())
                .addParameter("var_usuario_creacion", Types.VARCHAR, catalogo.getAuditoria().getUsuarioCreacion())
                .setReturnDaoParameter("resultado", Types.INTEGER)
                .build(Integer.class);
    }

}
