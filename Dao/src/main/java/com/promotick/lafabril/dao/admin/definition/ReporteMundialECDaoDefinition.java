package com.promotick.lafabril.dao.admin.definition;

import com.promotick.lafabril.common.UtilCommon;
import com.promotick.configuration.utils.dao.DaoDefinition;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;


@Repository("ReporteMundialECDaoDefinition")
public class ReporteMundialECDaoDefinition extends DaoDefinition<Map> {

    private Calendar calendar = Calendar.getInstance();

    public ReporteMundialECDaoDefinition() {
        super(Map.class);
    }

    @Override
    public Map mapRow(ResultSet rs, int rowNumber) throws SQLException {
        Map<String, Object> mapa = new LinkedHashMap<>();
        Calendar c = Calendar.getInstance();
        Date d = new Date();
        try {
            String finicio = rs.getString("fecha_inicio");
            String ffin = rs.getString("fecha_fin");

            mapa.put("Catalogo", StringUtils.isBlank(rs.getString("nombre_catalogo")) ? StringUtils.EMPTY : rs.getString("nombre_catalogo"));
//            mapa.put("Distribuidor", StringUtils.isBlank(rs.getString("nombre_distribuidor")) ? StringUtils.EMPTY : rs.getString("nombre_distribuidor"));
            mapa.put("Usuario", StringUtils.isBlank(rs.getString("usuario_participante")) ? StringUtils.EMPTY : rs.getString("usuario_participante"));
            mapa.put("Nombre", StringUtils.isBlank(rs.getString("nombre_participante")) ? StringUtils.EMPTY : rs.getString("nombre_participante"));
            mapa.put("Apellido paterno", StringUtils.isBlank(rs.getString("appaterno_participante")) ? StringUtils.EMPTY : rs.getString("appaterno_participante"));
            mapa.put("Apellido materno", StringUtils.isBlank(rs.getString("apmaterno_participante")) ? StringUtils.EMPTY : rs.getString("apmaterno_participante"));
            mapa.put("Nro Documento", StringUtils.isBlank(rs.getString("nro_documento")) ? StringUtils.EMPTY : rs.getString("nro_documento"));
            mapa.put("Telefono", StringUtils.isBlank(rs.getString("telefono_participante")) ? StringUtils.EMPTY : rs.getString("telefono_participante"));
            mapa.put("Movil", StringUtils.isBlank(rs.getString("movil_participante")) ? StringUtils.EMPTY : rs.getString("movil_participante"));
            mapa.put("Email", StringUtils.isBlank(rs.getString("email_participante")) ? StringUtils.EMPTY : rs.getString("email_participante"));
            mapa.put("Broker", StringUtils.isBlank(rs.getString("broker")) ? StringUtils.EMPTY : rs.getString("broker"));
            mapa.put("Ciudad", StringUtils.isBlank(rs.getString("ciudad")) ? StringUtils.EMPTY : rs.getString("ciudad"));
            mapa.put("Regional", StringUtils.isBlank(rs.getString("regional")) ? StringUtils.EMPTY : rs.getString("regional"));
            mapa.put("Fecha Nacimiento", StringUtils.isBlank(rs.getString("nacimiento")) ? StringUtils.EMPTY : rs.getString("nacimiento"));
            mapa.put("Estado Civil", StringUtils.isBlank(rs.getString("estado_civil")) ? StringUtils.EMPTY : rs.getString("estado_civil"));
            mapa.put("Hijos", StringUtils.isBlank(rs.getString("hijos")) ? StringUtils.EMPTY : rs.getString("hijos"));
            mapa.put("Status Participante", StringUtils.isBlank(rs.getString("estado_participante")) ? StringUtils.EMPTY : rs.getString("estado_participante"));
            mapa.put("Fecha Registro", StringUtils.isBlank(rs.getString("fecha_registro")) ? StringUtils.EMPTY : rs.getString("fecha_registro"));
            mapa.put("Fecha Inactivacion", StringUtils.isBlank(rs.getString("fecha_inactivacion")) ? StringUtils.EMPTY : rs.getString("fecha_inactivacion"));

            for (int i = 0; i <= UtilCommon.obtenerDiferenciaMeses(finicio,ffin); i++) {
                d = UtilCommon.stringToDate(finicio);
                c.setTime(d);
                c.add(Calendar.MONTH, i);
                mapa.put("Trivia_"+UtilCommon.obtenerMes(c.get(Calendar.MONTH)+1)+"_"+c.get(Calendar.YEAR),
                        rs.getInt("mes_"+UtilCommon.obtenerMes(c.get(Calendar.MONTH)+1)+"_"+c.get(Calendar.YEAR)+"_tri"));
            }

            for (int i = 0; i <= UtilCommon.obtenerDiferenciaMeses(finicio,ffin); i++) {
                d = UtilCommon.stringToDate(finicio);
                c.setTime(d);
                c.add(Calendar.MONTH, i);
                mapa.put("Pronostico_"+UtilCommon.obtenerMes(c.get(Calendar.MONTH)+1)+"_"+c.get(Calendar.YEAR),
                        rs.getInt("mes_"+UtilCommon.obtenerMes(c.get(Calendar.MONTH)+1)+"_"+c.get(Calendar.YEAR)+"_pro"));
            }

            for (int i = 0; i <= UtilCommon.obtenerDiferenciaMeses(finicio,ffin); i++) {
                d = UtilCommon.stringToDate(finicio);
                c.setTime(d);
                c.add(Calendar.MONTH, i);
                mapa.put("Polla_"+UtilCommon.obtenerMes(c.get(Calendar.MONTH)+1)+"_"+c.get(Calendar.YEAR),
                        rs.getInt("mes_"+UtilCommon.obtenerMes(c.get(Calendar.MONTH)+1)+"_"+c.get(Calendar.YEAR)+"_pol"));
            }

            for (int i = 0; i <= UtilCommon.obtenerDiferenciaMeses(finicio,ffin); i++) {
                d = UtilCommon.stringToDate(finicio);
                c.setTime(d);
                c.add(Calendar.MONTH, i);
                mapa.put("Bono_"+UtilCommon.obtenerMes(c.get(Calendar.MONTH)+1)+"_"+c.get(Calendar.YEAR),
                        rs.getInt("mes_"+UtilCommon.obtenerMes(c.get(Calendar.MONTH)+1)+"_"+c.get(Calendar.YEAR)+"_bon"));
            }

            mapa.put("Puntos por Mundial", rs.getInt("puntos_mundial"));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return mapa;
    }
}