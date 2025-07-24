package com.promotick.lafabril.dao.web.impl;

import com.promotick.lafabril.common.ConstantesCommon;
import com.promotick.lafabril.dao.utils.ConstantesAdminDAO;
import com.promotick.lafabril.dao.web.CampaniaDao;
import com.promotick.lafabril.dao.web.DescuentoDao;
import com.promotick.lafabril.dao.web.definition.CampaniaDaoDefinition;
import com.promotick.lafabril.dao.web.definition.DescuentoDaoDefinition;
import com.promotick.lafabril.model.web.Campania;
import com.promotick.lafabril.model.web.Descuento;
import com.promotick.configuration.utils.dao.DaoBuilder;
import com.promotick.configuration.utils.dao.DaoGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.Types;
import java.util.List;

@Repository
public class DescuentoDaoImpl extends DaoGenerator implements DescuentoDao {

    private DescuentoDaoDefinition descuentoDaoDefinition;

    @Autowired
    public DescuentoDaoImpl(JdbcTemplate jdbcTemplate, DescuentoDaoDefinition descuentoDaoDefinition) {
        super(jdbcTemplate);
        this.descuentoDaoDefinition = descuentoDaoDefinition;
    }

    @Override
    public List<Descuento> listarDescuentos() {
        return DaoBuilder.getInstance(this)
                .setLogger(ConstantesCommon.LOGGER)
                .setSchema(ConstantesAdminDAO.SCHEMA_NAME)
                .setProcedureName(ConstantesAdminDAO.SP_DESCUENTO_LISTAR)
                .setDaoDefinition(descuentoDaoDefinition)
                .build();
    }

    @Override
    public List<Descuento> listarDescuentos(Integer idDescuento, Integer orden) {
        return DaoBuilder.getInstance(this)
                .setLogger(ConstantesCommon.LOGGER)
                .setSchema(ConstantesAdminDAO.SCHEMA_NAME)
                .setProcedureName(ConstantesAdminDAO.SP_DESCUENTO_LISTAR)
                .addParameter("var_id_descuento", Types.INTEGER, idDescuento)
                .addParameter("var_orden", Types.INTEGER, orden)
                .setDaoDefinition(descuentoDaoDefinition)
                .build();
    }

    @Override
    public Integer actualizarDescuento(Descuento descuento) {
        return DaoBuilder.getInstance(this)
                .setLogger(ConstantesCommon.LOGGER)
                .setSchema(ConstantesAdminDAO.SCHEMA_NAME)
                .setProcedureName(ConstantesAdminDAO.SP_DESCUENTO_ACTUALIZAR)
                .addParameter("var_id_descuento", Types.INTEGER, descuento.getIdDescuento())
                .addParameter("var_nombre_descuento", Types.VARCHAR, descuento.getNombreDescuento())
                .addParameter("var_fecha_inicio", Types.DATE, descuento.getFechaInicio())
                .addParameter("var_fecha_fin", Types.DATE, descuento.getFechaFin())
                .addParameter("var_id_catalogo", Types.INTEGER, descuento.getCatalogo().getIdCatalogo())
                .addParameter("var_codigo_descuento", Types.VARCHAR, descuento.getCodigoDescuento())
                .addParameter("var_tipo_descuento", Types.INTEGER, descuento.getTipoDescuento().getIdTipoDescuento())
                .addParameter("var_valor_descuento", Types.INTEGER, descuento.getValorDescuento())
                .addParameter("var_usuario_creacion", Types.VARCHAR, descuento.getAuditoria().getUsuarioCreacion())
                .setReturnDaoParameter("resultado", Types.INTEGER)
                .build(Integer.class);
    }

    @Override
    public Integer actualizarEstadoDescuento(Integer idDescuento, Integer estado) {
        return DaoBuilder.getInstance(this)
                .setLogger(ConstantesCommon.LOGGER)
                .setSchema(ConstantesAdminDAO.SCHEMA_NAME)
                .setProcedureName(ConstantesAdminDAO.SP_DESCUENTO_ACTUALIZAR_ESTADO)
                .addParameter("var_id_descuento", Types.INTEGER, idDescuento)
                .addParameter("var_estado", Types.INTEGER, estado)
                .setReturnDaoParameter("resultado", Types.INTEGER)
                .build(Integer.class);

    }

}
