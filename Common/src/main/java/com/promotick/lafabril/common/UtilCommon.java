package com.promotick.lafabril.common;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.text.WordUtils;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.DateFormat;
import java.text.DateFormatSymbols;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class UtilCommon {

    public static final String FORMATO_FECHA_ANIO_MES_DIA = "yyyy-MM-dd";
    public static final String FORMATO_FECHA_DIA_MES_ANIO = "dd-MM-yyyy";
    public static final String FORMATO_FECHA_DIA_MES_ANIO_2 = "dd/MM/yyyy";
    public static final String FORMATO_FECHA_DIA_MES_ANIO_HORA = "dd-MM-yyyy HH:mm";
    public static final String FORMATO_FECHA_DIA_MES_ANIO_HORA_2 = "dd/MM/yyyy HH:mm";
    public static final String FORMATO_FECHA_HORA = "HH:mm:ss";
    public static final Integer YEAR_DEBUT = 2021;
    public static String removeDecimals(String text) {
        if(StringUtils.isBlank(text)){
            return text;
        }
        if (text.endsWith(".0")) {
            text = text.replace(".0", "");
        }
        return text;
    }

    public static String dateFormat(String format) {
        try {
            return new SimpleDateFormat(format).format(new Date());
        } catch (Exception e) {
            return UUID.randomUUID().toString().replace("-", "");
        }
    }

    public static String dateFormat(Date date, String format) {
        try {
            return new SimpleDateFormat(format).format(date);
        } catch (Exception e) {
            return UUID.randomUUID().toString().replace("-", "");
        }
    }

    public static Integer[] obtenerAnios(String fechainicio, String fechafin){
        try {
            DateFormat format = new SimpleDateFormat(FORMATO_FECHA_ANIO_MES_DIA);
            Date datefi = format.parse(fechainicio);
            Date dateff = format.parse(fechafin);
            Calendar startCalendar = new GregorianCalendar();
            startCalendar.setTime(datefi);
            Calendar endCalendar = new GregorianCalendar();
            endCalendar.setTime(dateff);

            return new Integer[]{startCalendar.get(Calendar.YEAR) , endCalendar.get(Calendar.YEAR) };
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Integer obtenerDiferenciaMeses(String fechainicio, String fechafin){
        int diferenciaMeses = 0;
        try {
            DateFormat format = new SimpleDateFormat(FORMATO_FECHA_ANIO_MES_DIA);
            Date datefi = format.parse(fechainicio);
            Date dateff = format.parse(fechafin);
            Calendar startCalendar = new GregorianCalendar();
            startCalendar.setTime(datefi);
            Calendar endCalendar = new GregorianCalendar();
            endCalendar.setTime(dateff);
            int diffYear = endCalendar.get(Calendar.YEAR)- startCalendar.get(Calendar.YEAR);
            diferenciaMeses = diffYear * 12 + endCalendar.get(Calendar.MONTH)- startCalendar.get(Calendar.MONTH);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return diferenciaMeses;
    }

    public static Date stringToDate(String date) {
        DateFormat fechadate = new SimpleDateFormat(FORMATO_FECHA_ANIO_MES_DIA);
        Date datefecha = new Date();
        try {
            datefecha = fechadate.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return datefecha;
    }

    public static String obtenerMes(int mes) {

        DateFormatSymbols dfs = new DateFormatSymbols(Locale.forLanguageTag("ES"));
        String[] names = dfs.getShortMonths();
        if (mes <=  names.length) {
            return WordUtils.capitalize(names[mes-1]).replace(".","");
        }
        return "";
    }

    public static String parseUrl(Integer id, String name) {
        if (id == null || StringUtils.isEmpty(name)) {
            return StringUtils.EMPTY;
        }
        String titleInUrl = name.trim().replaceAll("[^a-zA-Z0-9\\-\\s\\.]", "");
        titleInUrl = titleInUrl.replaceAll("[\\-| |\\.]+", "-");
        return id + "/" + titleInUrl.toLowerCase();
    }

    public static String firstDateMonth(String formato){
        SimpleDateFormat sdf = new SimpleDateFormat(formato);
        Integer firsDayMonth = Calendar.getInstance().getActualMinimum(Calendar.DAY_OF_MONTH);
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_MONTH, firsDayMonth);
        Date fecha = calendar.getTime();
        String fechaFormato = sdf.format(fecha);
        return fechaFormato;
    }

    public static String lastDateMonth(String formato){
        SimpleDateFormat sdf = new SimpleDateFormat(formato);
        Integer lastDayMonth = Calendar.getInstance().getActualMaximum(Calendar.DAY_OF_MONTH);
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_MONTH, lastDayMonth);
        Date fecha = calendar.getTime();
        String fechaFormato = sdf.format(fecha);
        return fechaFormato;
    }

    public static Integer getCurrentYear(){
        return Calendar.getInstance().get(Calendar.YEAR);
    }

    public static String removeHTMLTags(String str){
        return str.replaceAll("\\<.*?\\>", "");
    }
}
