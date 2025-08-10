package com.promotick.membership.common;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.text.WordUtils;

import java.text.DateFormat;
import java.text.DateFormatSymbols;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class UtilCommon {

    public static final String FORMATO_FECHA_ESCRITA = "d 'de' MMMM yyyy";
    public static final String FORMATO_FECHA_DIA_MES_ANIO = "dd-MM-yyyy";
    public static final String FORMATO_FECHA_DIA_MES_ANIO_HORA_2 = "dd/MM/yyyy HH:mm";


    public static String dateFormat(String fechaIso) {
        LocalDateTime fecha = LocalDateTime.parse(fechaIso);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(UtilCommon.FORMATO_FECHA_ESCRITA, new Locale("es", "ES"));
        return fecha.format(formatter);
    }

    public static String dateFormat(Date date, String format) {
        try {
            return new SimpleDateFormat(format).format(date);
        } catch (Exception e) {
            return UUID.randomUUID().toString().replace("-", "");
        }
    }

    public static String parseUrl(Integer id, String name) {
        if (id == null || StringUtils.isEmpty(name)) {
            return StringUtils.EMPTY;
        }
        String titleInUrl = name.trim().replaceAll("[^a-zA-Z0-9\\-\\s\\.]", "");
        titleInUrl = titleInUrl.replaceAll("[\\-| |\\.]+", "-");
        return id + "/" + titleInUrl.toLowerCase();
    }

}
