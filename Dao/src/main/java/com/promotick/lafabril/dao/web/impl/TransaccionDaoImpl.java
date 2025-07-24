package com.promotick.lafabril.dao.web.impl;

import com.promotick.lafabril.dao.utils.ConstantesWebDAO;
import com.promotick.lafabril.dao.web.TransaccionDao;
import com.promotick.lafabril.common.ConstantesCommon;
import com.promotick.lafabril.model.web.Transaccion;
import com.promotick.configuration.utils.dao.DaoBuilder;
import com.promotick.configuration.utils.dao.DaoGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.Types;

@Repository
public class TransaccionDaoImpl extends DaoGenerator implements TransaccionDao {

    @Autowired
    public TransaccionDaoImpl(JdbcTemplate jdbcTemplate) {
        super(jdbcTemplate);
    }

    @Override
    public Integer guardarTransaccionWeb(Transaccion transaccion) {
        return DaoBuilder
                .getInstance(this)
                .setLogger(ConstantesCommon.LOGGER)
                .setSchema(ConstantesWebDAO.SCHEMA_NAME)
                .setProcedureName(ConstantesWebDAO.SP_TRANSACCION_REGISTRAR_WEB)
                .addParameter("var_tipo_operacion", Types.INTEGER, transaccion.getTipoOperacionVisita().getCodigo())
                .addParameter("var_id_entidad", Types.INTEGER, transaccion.getIdEntidad())
                .addParameter("var_id_categoria", Types.INTEGER, transaccion.getIdCategoria())
                .addParameter("var_id_participante", Types.INTEGER, transaccion.getIdParticipante())
                .addParameter("var_tipo_dispositivo", Types.INTEGER, transaccion.getTipoDispositivo())
                .addParameter("var_usuario_creacion", Types.VARCHAR, transaccion.getAuditoria().getUsuarioCreacion())
                .addParameter("var_descripcion_operacion", Types.VARCHAR, transaccion.getTipoOperacionVisita().getTexto())
                .addParameter("var_id_catalogo", Types.INTEGER, transaccion.getIdCatalogo())
                .setReturnDaoParameter("resultado", Types.INTEGER)
                .build(Integer.class);
    }
}
