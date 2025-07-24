package com.promotick.lafabril.dao.facturacion.impl;

import com.promotick.lafabril.common.ConstantesCommon;
import com.promotick.lafabril.dao.facturacion.CargaDao;
import com.promotick.lafabril.dao.utils.ConstantesFacturacionDAO;
import com.promotick.lafabril.dao.utils.ConstantesWebDAO;
import com.promotick.lafabril.model.facturacion.Carga;
import com.promotick.configuration.utils.dao.DaoBuilder;
import com.promotick.configuration.utils.dao.DaoGenerator;
import com.promotick.lafabril.model.web.CargaWeb;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.Types;

@Repository
public class CargaDaoImpl extends DaoGenerator implements CargaDao {

    @Autowired
    public CargaDaoImpl(JdbcTemplate jdbcTemplate) {
        super(jdbcTemplate);
    }

    @Override
    public Integer registroCarga(Carga carga) {
        return DaoBuilder.getInstance(this)
                .setLogger(ConstantesCommon.LOGGER)
                .setSchema(ConstantesFacturacionDAO.SCHEMA_NAME)
                .setProcedureName(ConstantesFacturacionDAO.SP_CARGA_REGISTRAR)
                .addParameter("var_id_tipo_carga", Types.INTEGER, carga.getTipoCarga().getIdTipoCarga())
                .addParameter("var_id_resource", Types.INTEGER, carga.getIdResource())
                .addParameter("var_nombre_archivo", Types.VARCHAR, carga.getNombreArchivo())
                .addParameter("var_folder_archivo", Types.VARCHAR, carga.getFolderArchivo())
                .addParameter("var_bucket", Types.VARCHAR, carga.getBucket())
                .setReturnDaoParameter("resultado", Types.INTEGER)
                .build(Integer.class);
    }

    @Override
    public boolean existenciaCarga(String nombreArchivo) {
        return DaoBuilder.getInstance(this)
                .setLogger(ConstantesCommon.LOGGER)
                .setSchema(ConstantesFacturacionDAO.SCHEMA_NAME)
                .setProcedureName(ConstantesFacturacionDAO.SP_CARGA_VALIDAR_EXISTENCIA)
                .addParameter("var_nombre_archivo", Types.VARCHAR, nombreArchivo)
                .setReturnDaoParameter("resultado", Types.BOOLEAN)
                .build(Boolean.class);
    }

    @Override
    public Integer registroCargaWeb(CargaWeb cargaWeb) {
        return DaoBuilder.getInstance(this)
                .setLogger(ConstantesCommon.LOGGER)
                .setSchema(ConstantesWebDAO.SCHEMA_NAME)
                .setProcedureName(ConstantesWebDAO.SP_CARGA_WEB_REGISTRAR)
                .addParameter("var_id_tipo_carga", Types.INTEGER, cargaWeb.getIdTipoCarga())
                .addParameter("var_archivo", Types.VARCHAR, cargaWeb.getArchivo())
                .addParameter("var_usuario_carga", Types.VARCHAR, cargaWeb.getUsuarioCarga())
                .setReturnDaoParameter("resultado", Types.INTEGER)
                .build(Integer.class);
    }

    @Override
    public Integer actualizarCargaWeb(CargaWeb cargaWeb) {
        return DaoBuilder.getInstance(this)
                .setLogger(ConstantesCommon.LOGGER)
                .setSchema(ConstantesWebDAO.SCHEMA_NAME)
                .setProcedureName(ConstantesWebDAO.SP_CARGA_WEB_ACTUALIZAR)
                .addParameter("var_id_carga", Types.INTEGER, cargaWeb.getIdCarga())
                .addParameter("var_archivo_error", Types.VARCHAR, cargaWeb.getArchivoError())
                .addParameter("var_correctos", Types.INTEGER, cargaWeb.getCorrectos())
                .addParameter("var_errores", Types.INTEGER, cargaWeb.getErrores())
                .addParameter("var_total", Types.INTEGER, cargaWeb.getTotal())
                .addParameter("var_estado", Types.BOOLEAN, cargaWeb.getEstado())
                .addParameter("var_comentario", Types.VARCHAR, cargaWeb.getComentario())
                .setReturnDaoParameter("resultado", Types.INTEGER)
                .build(Integer.class);
    }
}
