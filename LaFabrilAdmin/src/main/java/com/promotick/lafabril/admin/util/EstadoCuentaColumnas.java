package com.promotick.lafabril.admin.util;

import com.promotick.lafabril.common.UtilCommon;
import com.promotick.lafabril.model.util.FiltroEstadoCuenta;

import java.util.Calendar;
import java.util.Collections;
import java.util.LinkedList;
import java.util.Map;
import java.util.stream.IntStream;

public class EstadoCuentaColumnas extends LinkedList<Map<String, Object>> {
    private static final long serialVersionUID = -3717308907083608597L;

    private Calendar calendar = Calendar.getInstance();

    private EstadoCuentaColumnas(FiltroEstadoCuenta filtroEstadoCuenta) {
        String finicio = filtroEstadoCuenta.getFinicio();
        String ffin = filtroEstadoCuenta.getFfin();

        this.add(Collections.singletonMap("data", "ID Participante"));
        this.add(Collections.singletonMap("data", "Catalogo"));
        this.add(Collections.singletonMap("data", "Nro Documento"));
        this.add(Collections.singletonMap("data", "Nombre"));
        this.add(Collections.singletonMap("data", "Apellido paterno"));
        this.add(Collections.singletonMap("data", "Apellido materno"));
//        this.add(Collections.singletonMap("data", "Cod Cliente"));
        this.add(Collections.singletonMap("data", "Email"));
        this.add(Collections.singletonMap("data", "Telefono"));
        this.add(Collections.singletonMap("data", "Movil"));
//        this.add(Collections.singletonMap("data", "Fecha de Nacimiento"));
//        this.add(Collections.singletonMap("data", "Genero"));
//        this.add(Collections.singletonMap("data", "Razon Social"));
//        this.add(Collections.singletonMap("data", "Cedula"));
//        this.add(Collections.singletonMap("data", "Ciudad"));
//        this.add(Collections.singletonMap("data", "Ruc Comercial"));
//        this.add(Collections.singletonMap("data", "Promocion"));
//        this.add(Collections.singletonMap("data", "Representante"));
//        this.add(Collections.singletonMap("data", "Razon Social"));
//        this.add(Collections.singletonMap("data", "Estado Canjes"));
//        this.add(Collections.singletonMap("data", "Meta Anual"));
        this.add(Collections.singletonMap("data", "Estado"));

      /*  IntStream.
                range(0, UtilCommon.obtenerDiferenciaMeses(finicio, ffin) + 1)
                .forEach(i -> {
                    add(obtenerColumna(i, "Posible_", finicio, calendar));
                });*/

//        IntStream.
//                range(0, UtilCommon.obtenerDiferenciaMeses(finicio, ffin) + 1)
//                .forEach(i -> {
//                    add(obtenerColumna(i, "Meta_", finicio, calendar));
//                });

        IntStream.
                range(0, UtilCommon.obtenerDiferenciaMeses(finicio, ffin) + 1)
                .forEach(i -> {
                    add(obtenerColumna(i, "Canjeado_", finicio, calendar));
                });

        IntStream.
                range(0, UtilCommon.obtenerDiferenciaMeses(finicio, ffin) + 1)
                .forEach(i -> {
                    add(obtenerColumna(i, "Acreditado_", finicio, calendar));
                });

        IntStream.
                range(0, UtilCommon.obtenerDiferenciaMeses(finicio, ffin) + 1)
                .forEach(i -> {
                    add(obtenerColumna(i, "Descargado_", finicio, calendar));
                });

//        IntStream.
//                range(0, UtilCommon.obtenerDiferenciaMeses(finicio, ffin) + 1)
//                .forEach(i -> {
//                    add(obtenerColumna(i, "Devolucion_", finicio, calendar));
//                });

        Integer[] anios = UtilCommon.obtenerAnios(finicio,ffin);
        IntStream.
                range(anios[0], anios[1] + 1)
                .forEach(i -> {
                    add(Collections.singletonMap("data", "Vencimiento_" +  i));
                });

        /*
        IntStream.
                range(0, UtilCommon.obtenerDiferenciaMeses(finicio, ffin) + 1)
                .forEach(i -> {
                    add(obtenerColumna(i, "Externo_", finicio, calendar));
                });

        */
        this.add(Collections.singletonMap("data", "Disponibles"));
    }

    public static EstadoCuentaColumnas getInstance(FiltroEstadoCuenta filtroEstadoCuenta) {
        return new EstadoCuentaColumnas(filtroEstadoCuenta);
    }

    private Map<String, Object> obtenerColumna(int i, String prefix, String finicio, Calendar calendar) {
        calendar.setTime(UtilCommon.stringToDate(finicio));
        calendar.add(Calendar.MONTH, i);
        return Collections.singletonMap("data", prefix + UtilCommon.obtenerMes(calendar.get(Calendar.MONTH) + 1) + "_" + calendar.get(Calendar.YEAR));
    }
}
