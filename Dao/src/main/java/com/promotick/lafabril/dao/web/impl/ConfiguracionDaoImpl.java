package com.promotick.lafabril.dao.web.impl;

import com.promotick.lafabril.dao.utils.ConstantesAdminDAO;
import com.promotick.lafabril.dao.utils.ConstantesWebDAO;
import com.promotick.lafabril.dao.web.ConfiguracionDao;
import com.promotick.lafabril.dao.web.definition.*;
import com.promotick.lafabril.dao.web.definition.*;
import com.promotick.lafabril.common.ConstantesCommon;
import com.promotick.lafabril.model.configuracion.ConfiguracionBanner;
import com.promotick.lafabril.model.configuracion.ConfiguracionWeb;
import com.promotick.lafabril.model.util.FiltroAcelerador;
import com.promotick.lafabril.model.util.FiltroBanner;
import com.promotick.lafabril.model.web.Acelerador;
import com.promotick.lafabril.model.web.RazonSocial;
import com.promotick.lafabril.model.web.Region;
import com.promotick.lafabril.model.web.TipoDocumento;
import com.promotick.configuration.utils.dao.DaoBuilder;
import com.promotick.configuration.utils.dao.DaoGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.Types;
import java.util.List;

@Repository
public class ConfiguracionDaoImpl extends DaoGenerator implements ConfiguracionDao {

    private final AceleradorDaoDefinition aceleradorDaoDefinition;
    private AceleradorDaoDefinition acceleradorDaoDefinition;
    private ConfiguracionBannerDaoDefinition configuracionBannerDaoDefinition;
    private ConfiguracionWebDaoDefinition configuracionWebDaoDefinition;
    private TipoDocumentoDaoDefinition tipoDocumentoDaoDefinition;
    private RazonSocialDaoDefinition razonSocialDaoDefinition;
    private RegionDaoDefinition regionDaoDefinition;

    @Autowired
    public ConfiguracionDaoImpl(AceleradorDaoDefinition acceleradorDaoDefinition, JdbcTemplate jdbcTemplate, ConfiguracionBannerDaoDefinition configuracionBannerDaoDefinition, ConfiguracionWebDaoDefinition configuracionWebDaoDefinition, TipoDocumentoDaoDefinition tipoDocumentoDaoDefinition, RazonSocialDaoDefinition razonSocialDaoDefinition, RegionDaoDefinition regionDaoDefinition, AceleradorDaoDefinition aceleradorDaoDefinition) {
        super(jdbcTemplate);
        this.configuracionBannerDaoDefinition = configuracionBannerDaoDefinition;
        this.configuracionWebDaoDefinition = configuracionWebDaoDefinition;
        this.tipoDocumentoDaoDefinition = tipoDocumentoDaoDefinition;
        this.regionDaoDefinition = regionDaoDefinition;
        this.razonSocialDaoDefinition = razonSocialDaoDefinition;
        this.acceleradorDaoDefinition = acceleradorDaoDefinition;
        this.aceleradorDaoDefinition = aceleradorDaoDefinition;
    }

    @Override
    public List<ConfiguracionBanner> listarBanner(Integer idConfiguracionBanner, Integer idCatalogo, Integer estadoConfigBanner, Integer tipoBanner) {
        return DaoBuilder.getInstance(this)
                .setLogger(ConstantesCommon.LOGGER)
                .setSchema(ConstantesWebDAO.SCHEMA_NAME)
                .setProcedureName(ConstantesWebDAO.SP_CONFIGURACION_BANNER_LISTAR)
                .addParameter("var_id_configuracion_banner", Types.INTEGER, idConfiguracionBanner)
                .addParameter("var_id_catalogo", Types.INTEGER, idCatalogo)
                .addParameter("var_estado_configuracion", Types.INTEGER, estadoConfigBanner)
                .addParameter("var_tipo_banner", Types.INTEGER, tipoBanner)
                .setDaoDefinition(configuracionBannerDaoDefinition)
                .build();
    }


    @Override
    public List<TipoDocumento> listarTipoDocumentos() {
        return DaoBuilder.getInstance(this)
                .setLogger(ConstantesCommon.LOGGER)
                .setSchema(ConstantesWebDAO.SCHEMA_NAME)
                .setProcedureName(ConstantesWebDAO.SP_TIPO_DOCUMENTO_LISTAR)
                .setDaoDefinition(tipoDocumentoDaoDefinition)
                .build();
    }

    @Override
    public ConfiguracionWeb obtenerConfiguracionWeb() {
        return DaoBuilder.getInstance(this)
                .setLogger(ConstantesCommon.LOGGER)
                .setSchema(ConstantesWebDAO.SCHEMA_NAME)
                .setProcedureName(ConstantesWebDAO.SP_CONFIGURACION_WEB_OBTENER)
                .setDaoDefinition(configuracionWebDaoDefinition)
                .build(ConfiguracionWeb.class);
    }

    @Override
    public Integer bannerFiltroContar(FiltroBanner filtroBanner) {
        return DaoBuilder.getInstance(this)
                .setLogger(ConstantesCommon.LOGGER)
                .setSchema(ConstantesAdminDAO.SCHEMA_NAME)
                .setProcedureName(ConstantesAdminDAO.SP_BANNER_FILTRO_CONTAR)
                .addParameter("var_tipo_banner", Types.INTEGER, filtroBanner.getTipoBanner())
                .setReturnDaoParameter("resultado", Types.INTEGER)
                .build(Integer.class);
    }

    @Override
    public List<ConfiguracionBanner> bannerFiltroListar(FiltroBanner filtroBanner) {
        return DaoBuilder.getInstance(this)
                .setLogger(ConstantesCommon.LOGGER)
                .setSchema(ConstantesAdminDAO.SCHEMA_NAME)
                .setProcedureName(ConstantesAdminDAO.SP_BANNER_FILTRO_LISTAR)
                .addParameter("var_tipo_banner", Types.INTEGER, filtroBanner.getTipoBanner())
                .addParameter("var_orden", Types.INTEGER, filtroBanner.getOrden())
                .setDaoDefinition(configuracionBannerDaoDefinition)
                .build();
    }

    @Override
    public Integer registroBanner(ConfiguracionBanner configuracionBanner) {
        return DaoBuilder.getInstance(this)
                .setLogger(ConstantesCommon.LOGGER)
                .setSchema(ConstantesAdminDAO.SCHEMA_NAME)
                .setProcedureName(ConstantesAdminDAO.SP_BANNER_REGISTRO)
                .addParameter("var_accion", Types.INTEGER, configuracionBanner.getAccion())
                .addParameter("var_id_configuracion_banner", Types.INTEGER, configuracionBanner.getIdConfiguracionBanner())
                .addParameter("var_imagen_banner", Types.VARCHAR, configuracionBanner.getImagenBanner())
                .addParameter("var_texto_uno", Types.VARCHAR, configuracionBanner.getTextoUno())
                .addParameter("var_texto_dos", Types.VARCHAR, configuracionBanner.getTextoDos())
                .addParameter("var_texto_tres", Types.VARCHAR, configuracionBanner.getTextoTres())
                .addParameter("var_texto_cuatro", Types.VARCHAR, configuracionBanner.getTextoCuatro())
                .addParameter("var_texto_cinco", Types.VARCHAR, configuracionBanner.getTextoCinco())
                .addParameter("var_url_boton", Types.VARCHAR, configuracionBanner.getUrlBoton())
                .addParameter("var_texto_boton", Types.VARCHAR, configuracionBanner.getTextoBoton())
                .addParameter("var_orden", Types.INTEGER, configuracionBanner.getOrden())
                .addParameter("var_id_catalogo", Types.INTEGER, configuracionBanner.getIdCatalogo())
                .addParameter("var_tipo_banner", Types.INTEGER, configuracionBanner.getTipoBanner())
                .addParameter("var_url_location", Types.INTEGER, 1)
                .addParameter("var_estado", Types.INTEGER, configuracionBanner.getEstadoConfiguracionBanner())
                .addParameter("var_usuario_creacion", Types.VARCHAR, configuracionBanner.getAuditoria().getUsuarioCreacion())
                .setReturnDaoParameter("resultado", Types.INTEGER)
                .build(Integer.class);
    }

    @Override
    public ConfiguracionBanner obtenerBanner(Integer idConfiguracionBanner) {
        return DaoBuilder.getInstance(this)
                .setLogger(ConstantesCommon.LOGGER)
                .setSchema(ConstantesAdminDAO.SCHEMA_NAME)
                .setProcedureName(ConstantesAdminDAO.SP_CONFIGURACION_BANNER_OBTENER)
                .addParameter("var_id_configuracion_banner", Types.INTEGER, idConfiguracionBanner)
                .setDaoDefinition(configuracionBannerDaoDefinition)
                .build(ConfiguracionBanner.class);
    }

    @Override
    public List<RazonSocial> obtenerRazonesSociales(Integer idCatalogo) {
        return DaoBuilder.getInstance(this)
                .setLogger(ConstantesCommon.LOGGER)
                .setSchema(ConstantesWebDAO.SCHEMA_NAME)
                .setProcedureName(ConstantesWebDAO.SP_RAZONSOCIAL_LISTAR)
                .addParameter("var_id_catalogo", Types.INTEGER, idCatalogo)
                .setDaoDefinition(razonSocialDaoDefinition)
                .build();
    }

    @Override
    public List<Region> obtenerRegiones() {
        return DaoBuilder.getInstance(this)
                .setLogger(ConstantesCommon.LOGGER)
                .setSchema(ConstantesWebDAO.SCHEMA_NAME)
                .setProcedureName(ConstantesWebDAO.SP_REGION_LISTAR)
                .setDaoDefinition(regionDaoDefinition)
                .build();
    }

    @Override
    public Integer actualizarDatosVencimiento(ConfiguracionWeb configuracionWeb) {
        return DaoBuilder.getInstance(this)
                .setLogger(ConstantesCommon.LOGGER)
                .setSchema(ConstantesAdminDAO.SCHEMA_NAME)
                .setProcedureName(ConstantesAdminDAO.SP_CONFIGURACION_WEB_ACTUALIZAR)
                .addParameter("var_fecha_vencimiento", Types.DATE, configuracionWeb.getFechaVencimiento())
                .addParameter("var_hora_vencimiento", Types.INTEGER, configuracionWeb.getHoraVencimiento())
                .setReturnDaoParameter("resultado", Types.INTEGER)
                .build(Integer.class);
    }


    @Override
    public List<Acelerador> aceleradorListar(FiltroAcelerador filtroAcelerador) {
        return DaoBuilder.getInstance(this)
                .setLogger(ConstantesCommon.LOGGER)
                .setSchema(ConstantesAdminDAO.SCHEMA_GENERAL)
                .setProcedureName(ConstantesAdminDAO.SP_ACELERADOR_LISTAR)
                .addParameter("var_id_acelerador", Types.INTEGER, filtroAcelerador.getIdAcelerador())
                .addParameter("var_id_participante", Types.INTEGER, filtroAcelerador.getIdParticipante())
                .setDaoDefinition(aceleradorDaoDefinition)
                .build();
    }

    @Override
    public Integer registroAcelerador(FiltroAcelerador filtroAcelerador) {
        return DaoBuilder.getInstance(this)
                .setLogger(ConstantesCommon.LOGGER)
                .setSchema(ConstantesAdminDAO.SCHEMA_NAME)
                .setProcedureName(ConstantesAdminDAO.SP_ACELERADOR_ACTUALIZAR)
                .addParameter("var_id_acelerador", Types.INTEGER, filtroAcelerador.getIdAcelerador())
                .addParameter("var_id_catalogo", Types.INTEGER, filtroAcelerador.getIdCatalogo())
                .addParameter("var_titulo", Types.VARCHAR, filtroAcelerador.getTitulo())
                .addParameter("var_imagen", Types.VARCHAR, filtroAcelerador.getImagen())
                .addParameter("var_operacion", Types.VARCHAR, filtroAcelerador.getOperacion())
                .addParameter("var_usuario_creacion", Types.VARCHAR, filtroAcelerador.getUsuarioCreacion())
                .addParameter("var_lista_id", Types.VARCHAR, filtroAcelerador.getListaId())

                .setReturnDaoParameter("resultado", Types.INTEGER)
                .build(Integer.class);
    }
}
