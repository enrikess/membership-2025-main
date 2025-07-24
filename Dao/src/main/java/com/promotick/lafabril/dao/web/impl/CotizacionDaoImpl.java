package com.promotick.lafabril.dao.web.impl;

import com.promotick.lafabril.model.web.Pasajero;
import com.promotick.configuration.utils.dao.DaoBuilder;
import com.promotick.configuration.utils.dao.DaoGenerator;
import com.promotick.lafabril.dao.utils.ConstantesWebDAO;
import com.promotick.lafabril.dao.web.CotizacionDao;
import com.promotick.lafabril.common.ConstantesCommon;
import com.promotick.lafabril.model.web.Cotizacion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.Types;

@Repository
public class CotizacionDaoImpl extends DaoGenerator implements CotizacionDao {

    @Autowired
    public CotizacionDaoImpl(JdbcTemplate jdbcTemplate) {
        super(jdbcTemplate);
    }

//    @Override
//    public Integer registrarCotizacion(Cotizacion cotizacion) {
//        return DaoBuilder.getInstance(this)
//                .setLogger(ConstantesCommon.LOGGER)
//                .setSchema(ConstantesWebDAO.SCHEMA_NAME)
//                .setProcedureName(ConstantesWebDAO.SP_GUARDAR_COTIZACION)
//                .addParameter("var_origen", Types.VARCHAR, cotizacion.getOrigen())
//                .addParameter("var_destino", Types.VARCHAR, cotizacion.getDestino())
//                .addParameter("var_tipo_boleto", Types.INTEGER, cotizacion.getTipoBoleto())
//                .addParameter("var_fecha_ida", Types.VARCHAR, cotizacion.getFechaIda())
//                .addParameter("var_fecha_vuelta", Types.VARCHAR, cotizacion.getFechaVuelta())
//                .addParameter("var_adultos", Types.INTEGER, cotizacion.getAdultos())
//                .addParameter("var_boys", Types.INTEGER, cotizacion.getBoys())
//                .addParameter("var_bebes", Types.INTEGER, cotizacion.getBebes())
//                .addParameter("var_boy1", Types.INTEGER, cotizacion.getBoy1())
//                .addParameter("var_boy2", Types.INTEGER,  cotizacion.getBebe2())
//                .addParameter("var_boy3", Types.INTEGER, cotizacion.getBoy3())
//                .addParameter("var_boy4", Types.INTEGER, cotizacion.getBoy4())
//                .addParameter("var_bebe1", Types.INTEGER, cotizacion.getBebe1())
//                .addParameter("var_bebe2", Types.INTEGER, cotizacion.getBebe2())
//                .addParameter("var_bebe3", Types.INTEGER, cotizacion.getBebe3())
//                .addParameter("var_bebe4", Types.INTEGER, cotizacion.getBebe4())
//                .addParameter("var_clase", Types.VARCHAR, cotizacion.getClase())
//                .addParameter("var_mayores", Types.INTEGER, cotizacion.getMayores())
////                .addParameter("var_tercera_edad", Types.INTEGER, cotizacion.getTerceraEdad())
////                .addParameter("var_discapacitado", Types.INTEGER, cotizacion.getDiscapacitado())
//                .addParameter("var_edadMayor", Types.INTEGER, cotizacion.getEdadMayor())
//                .setReturnDaoParameter("resultado", Types.INTEGER)
//                .build(Integer.class);
//    }

    @Override
    public Integer registrarCotizacion(Cotizacion cotizacion) {
        return DaoBuilder.getInstance(this)
                .setLogger(ConstantesCommon.LOGGER)
                .setSchema(ConstantesWebDAO.SCHEMA_NAME)
                .setProcedureName(ConstantesWebDAO.SP_GUARDAR_COTIZACION)
                .addParameter("var_tipo_boleto", Types.INTEGER, cotizacion.getTipoBoleto())
                .addParameter("var_clase", Types.VARCHAR, cotizacion.getClase())
                .addParameter("var_origen", Types.VARCHAR, cotizacion.getOrigen())
                .addParameter("var_destino", Types.VARCHAR, cotizacion.getDestino())
                .addParameter("var_fecha_ida", Types.VARCHAR, cotizacion.getFechaIda())
                .addParameter("var_fecha_vuelta", Types.VARCHAR, cotizacion.getFechaVuelta())
                .addParameter("var_cant_pasajeros", Types.INTEGER, cotizacion.getListaPasajeros().size())
                .addParameter("var_correo", Types.VARCHAR, cotizacion.getCorreo())
                .addParameter("var_telefono", Types.VARCHAR, cotizacion.getTelefono())
                .setReturnDaoParameter("resultado", Types.INTEGER)
                .build(Integer.class);
    }

    @Override
    public void registrarPasajeros(Pasajero pasajero, Integer cotizacionId) {
        DaoBuilder.getInstance(this)
                .setLogger(ConstantesCommon.LOGGER)
                .setSchema(ConstantesWebDAO.SCHEMA_NAME)
                .setProcedureName(ConstantesWebDAO.SP_GUARDAR_DATOS_PASAJERO)
                .addParameter("var_cotizacion_id", Types.INTEGER, cotizacionId)
                .addParameter("var_nombres", Types.VARCHAR, pasajero.getNombrePasajero())
                .addParameter("var_apellidos", Types.VARCHAR, pasajero.getApellidoPasajero())
                .addParameter("var_tipo_doc", Types.INTEGER, pasajero.getTipoDocumento().getIdTipoDocumento())
                .addParameter("var_nro_doc", Types.VARCHAR, pasajero.getNroDocumento())
                .addParameter("var_fecha_nac", Types.VARCHAR, pasajero.getFechaNacimiento())
                .addParameter("var_discapacidad", Types.BOOLEAN, pasajero.getDiscapacitado() == 1)
                .addParameter("var_edad_mayor65", Types.BOOLEAN, pasajero.getTerceraEdad() == 1)
                .setReturnDaoParameter("resultado", Types.INTEGER)
                .build(Integer.class);

//        LOGGER.info("Se registro correctamente el pasajero a la cotizacion de ID :: {}", cotizacionId);
    }
}
