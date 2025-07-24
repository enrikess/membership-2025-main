package com.promotick.lafabril.dao.admin.definition;

import com.promotick.lafabril.common.UtilCommon;
import com.promotick.configuration.utils.dao.DaoDefinition;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
import java.util.stream.IntStream;

@Component
public class ReporteEstadoCuentaDaoDefinition extends DaoDefinition<Map> {

    private Calendar calendar = Calendar.getInstance();

    public ReporteEstadoCuentaDaoDefinition() {
        super(Map.class);
    }

    @Override
    public Map mapRow(ResultSet rs, int rowNumber) throws SQLException {
        Map<String, Object> mapa = new LinkedHashMap<>();
        Calendar c = Calendar.getInstance();
        Date date;
        try {
            String fechaInicio = rs.getString("fecha_inicio");
            String fechaFin = rs.getString("fecha_fin");

            mapa.put("ID Participante", StringUtils.isBlank(rs.getString("id_participante")) ? StringUtils.EMPTY : rs.getString("id_participante"));
            mapa.put("Catalogo", StringUtils.isBlank(rs.getString("nombre_catalogo")) ? StringUtils.EMPTY : rs.getString("nombre_catalogo"));
            mapa.put("Nro Documento", StringUtils.isBlank(rs.getString("nro_documento")) ? StringUtils.EMPTY : rs.getString("nro_documento"));
            mapa.put("Nombre", StringUtils.isBlank(rs.getString("nombres_participante")) ? StringUtils.EMPTY : rs.getString("nombres_participante"));
            mapa.put("Apellido paterno", StringUtils.isBlank(rs.getString("appaterno_participante")) ? StringUtils.EMPTY : rs.getString("appaterno_participante"));
            mapa.put("Apellido materno", StringUtils.isBlank(rs.getString("apmaterno_participante")) ? StringUtils.EMPTY : rs.getString("apmaterno_participante"));
            mapa.put("Broker", StringUtils.isBlank(rs.getString("nombre_broker")) ? StringUtils.EMPTY : rs.getString("nombre_broker"));
            mapa.put("Ciudad", StringUtils.isBlank(rs.getString("ciudad")) ? StringUtils.EMPTY : rs.getString("ciudad"));
            mapa.put("Regional", StringUtils.isBlank(rs.getString("regional")) ? StringUtils.EMPTY : rs.getString("regional"));
            mapa.put("Email", StringUtils.isBlank(rs.getString("email_participante")) ? StringUtils.EMPTY : rs.getString("email_participante"));
            mapa.put("Telefono", StringUtils.isBlank(rs.getString("telefono_participante")) ? StringUtils.EMPTY : rs.getString("telefono_participante"));
            mapa.put("Movil", StringUtils.isBlank(rs.getString("movil_participante")) ? StringUtils.EMPTY : rs.getString("movil_participante"));
//            mapa.put("Fecha de Nacimiento", StringUtils.isBlank(rs.getString("fecha_nacimiento")) ? StringUtils.EMPTY : rs.getString("fecha_nacimiento"));
//            mapa.put("Razon Social", StringUtils.isBlank(rs.getString("razon_social")) ? StringUtils.EMPTY : rs.getString("razon_social"));
//            mapa.put("Genero", StringUtils.isBlank(rs.getString("genero")) ? StringUtils.EMPTY : rs.getString("genero"));
//            mapa.put("Cedula", StringUtils.isBlank(rs.getString("cedula")) ? StringUtils.EMPTY : rs.getString("cedula"));
//            mapa.put("Ciudad", StringUtils.isBlank(rs.getString("ciudad")) ? StringUtils.EMPTY : rs.getString("ciudad"));
//            mapa.put("Ruc Comercial", StringUtils.isBlank(rs.getString("ruc_comercial")) ? StringUtils.EMPTY : rs.getString("ruc_comercial"));
//            mapa.put("Promocion", StringUtils.isBlank(rs.getString("promocion")) ? StringUtils.EMPTY : rs.getString("promocion"));
//            mapa.put("Representante", StringUtils.isBlank(rs.getString("nombre_vendedor")) ? StringUtils.EMPTY : rs.getString("nombre_vendedor"));
//            mapa.put("Razon Social", StringUtils.isBlank(rs.getString("nombre_razonsocial")) ? StringUtils.EMPTY : rs.getString("nombre_razonsocial"));
//            mapa.put("Estado Canjes", StringUtils.isBlank(rs.getString("estado_canjes")) ? StringUtils.EMPTY : rs.getString("estado_canjes"));
//            mapa.put("Meta Anual", StringUtils.isBlank(rs.getString("meta_anual")) ? StringUtils.EMPTY : rs.getString("meta_anual"));
            mapa.put("Estado", StringUtils.isBlank(rs.getString("estado_participante")) ? StringUtils.EMPTY : rs.getString("estado_participante"));

           /* IntStream.
                    range(0, UtilCommon.obtenerDiferenciaMeses(fechaInicio, fechaFin) + 1)
                    .forEach(i -> {
                        obtenerColumna(i, "Posible_", "_pos", fechaInicio, calendar, mapa, rs);
                    });*/
            IntStream.
                    range(0, UtilCommon.obtenerDiferenciaMeses(fechaInicio, fechaFin) + 1)
                    .forEach(i -> {
                        obtenerColumna(i, "Canjeado_", "_can", fechaInicio, calendar, mapa, rs);
                    });

            IntStream.
                    range(0, UtilCommon.obtenerDiferenciaMeses(fechaInicio, fechaFin) + 1)
                    .forEach(i -> {
                        obtenerColumna(i, "Acreditado_", "_acu", fechaInicio, calendar, mapa, rs);
                    });

            IntStream.
                    range(0, UtilCommon.obtenerDiferenciaMeses(fechaInicio, fechaFin) + 1)
                    .forEach(i -> {
                        obtenerColumna(i, "Descargado_", "_des", fechaInicio, calendar, mapa, rs);
                    });

//            IntStream.
//                    range(0, UtilCommon.obtenerDiferenciaMeses(fechaInicio, fechaFin) + 1)
//                    .forEach(i -> {
//                        obtenerColumna(i, "Devolucion_", "_dev", fechaInicio, calendar, mapa, rs);
//                    });
//
//            IntStream.
//                    range(0, UtilCommon.obtenerDiferenciaMeses(fechaInicio, fechaFin) + 1)
//                    .forEach(i -> {
//                        obtenerColumna(i, "Acelerador_", "_ace", fechaInicio, calendar, mapa, rs);
//                    });

            IntStream.
                    range(0, UtilCommon.obtenerDiferenciaMeses(fechaInicio, fechaFin) + 1)
                    .forEach(i -> {
                        obtenerColumna(i, "Campaña_", "_cam", fechaInicio, calendar, mapa, rs);
                    });

//            IntStream.
//                    range(0, UtilCommon.obtenerDiferenciaMeses(fechaInicio, fechaFin) + 1)
//                    .forEach(i -> {
//                        obtenerColumna(i, "Referidos_", "_ref", fechaInicio, calendar, mapa, rs);
//                    });
//
//            IntStream.
//                    range(0, UtilCommon.obtenerDiferenciaMeses(fechaInicio, fechaFin) + 1)
//                    .forEach(i -> {
//                        obtenerColumna(i, "Capacitaciones_", "_cap", fechaInicio, calendar, mapa, rs);
//                    });
//
//            IntStream.
//                    range(0, UtilCommon.obtenerDiferenciaMeses(fechaInicio, fechaFin) + 1)
//                    .forEach(i -> {
//                        obtenerColumna(i, "Meta_", "_meta", fechaInicio, calendar, mapa, rs);
//                    });
//
//            IntStream.
//                    range(0, UtilCommon.obtenerDiferenciaMeses(fechaInicio, fechaFin) + 1)
//                    .forEach(i -> {
//                        obtenerColumna(i, "Venta_", "_ven", fechaInicio, calendar, mapa, rs);
//                    });
//
//            mapa.put("Puntos por Campaña", StringUtils.isBlank(rs.getString("puntos_campania")) ? StringUtils.EMPTY : rs.getString("puntos_campania"));
//            mapa.put("Puntos por Mundial", StringUtils.isBlank(rs.getString("puntos_mundial")) ? StringUtils.EMPTY : rs.getString("puntos_mundial"));


            Integer[] anios = UtilCommon.obtenerAnios(fechaInicio,fechaFin);
            IntStream.
                    range(anios[0], anios[1] + 1)
                    .forEach(i -> {
                        obtenerVencimiento(i, mapa, rs);
                    });
            /*
            IntStream.
                    range(0, UtilCommon.obtenerDiferenciaMeses(fechaInicio, fechaFin) + 1)
                    .forEach(i -> {
                        obtenerColumna(i, "Externo_", "_ext", fechaInicio, calendar, mapa, rs);
                    });
            */
            mapa.put("Disponibles", rs.getInt("disponibles"));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return mapa;
    }

    private void obtenerVencimiento(Integer year, Map<String, Object> mapa, ResultSet rs){
        try {
            mapa.put("Vencimiento_" +  year, StringUtils.isBlank(rs.getString("Vencimiento_" +  year)) ? StringUtils.EMPTY : rs.getString("Vencimiento_" +  year));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void obtenerColumna(int i, String prefix, String postfix, String finicio, Calendar calendar, Map<String, Object> mapa, ResultSet rs) {
        try {
            calendar.setTime(UtilCommon.stringToDate(finicio));
            calendar.add(Calendar.MONTH, i);
            Object result = rs.getObject("mes_" + UtilCommon.obtenerMes(calendar.get(Calendar.MONTH) + 1) + "_" + calendar.get(Calendar.YEAR) + postfix);
            mapa.put(prefix + UtilCommon.obtenerMes(calendar.get(Calendar.MONTH) + 1) + "_" + calendar.get(Calendar.YEAR), result == null ? 0 : result);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
