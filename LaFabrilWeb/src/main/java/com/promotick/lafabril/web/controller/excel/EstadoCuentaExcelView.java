package com.promotick.lafabril.web.controller.excel;

import com.promotick.apiclient.utils.file.excel.generator.AbstractPOIExcelView;
import com.promotick.lafabril.model.web.ParticipanteTransaccion;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.DataFormat;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;

@Component
public class EstadoCuentaExcelView extends AbstractPOIExcelView<ParticipanteTransaccion> {

    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/YYYY");
    private CellStyle textStyle;

    @Override
    public List<ParticipanteTransaccion> list() {
        return this.findList("listParticipanteTransaccion");
    }

    @Override
    public String fileName() {
        return (String) this.findAttribute("filename");
    }

    @Override
    public String sheetName() {
        return "Estado de cuenta";
    }

    @Override
    public String[] headersName() {
        return new String[]{
                "Fecha Operacion", "Tipo transaccion", "Detalle", "Puntos"
        };
    }

    @Override
    public boolean isExcelError() {
        return false;
    }

    @Override
    public void buildExcelDocument(ParticipanteTransaccion participanteTransaccion, SXSSFWorkbook sxssfWorkbook, SXSSFSheet sxssfSheet, Row row, Integer integer, Map<Integer, Integer> map) throws Exception {

        if (participanteTransaccion.getFechaOperacion() != null) {
            String fecha = simpleDateFormat.format(participanteTransaccion.getFechaOperacion());
            map.put(0, fecha.length());
            Cell cell = row.createCell(0);
            cell.setCellValue(fecha);
            cell.setCellStyle(textStyle);
        }

        if (participanteTransaccion.getIdOrigen() != null) {
            String tipoOperacion = "";

            if (participanteTransaccion.getIdOrigen() == 1) {
                tipoOperacion = "Carga de puntos";
            } else if (participanteTransaccion.getIdOrigen() == 5) {
                tipoOperacion = "Vencimiento de puntos";
            } else if (participanteTransaccion.getIdOrigen() == 2 || participanteTransaccion.getIdOrigen() == 3) {
                tipoOperacion = "Canje de productos";
            } else if (participanteTransaccion.getIdOrigen() == 9) {
                tipoOperacion = "Resta de puntos";
            }

            map.put(2, tipoOperacion.length());

            Cell cell = row.createCell(1);
            cell.setCellValue(tipoOperacion);
            cell.setCellStyle(textStyle);
        }

        if (participanteTransaccion.getDescripcion() != null) {
            String cadena = participanteTransaccion.getDescripcion();
            map.put(3, cadena.length());
            Cell cell = row.createCell(2);
            cell.setCellValue(cadena);
            cell.setCellStyle(textStyle);
        }

        if (participanteTransaccion.getValorPuntos() != null) {
            map.put(4, participanteTransaccion.getValorPuntos().toString().length());

            Cell cell = row.createCell(3);
            cell.setCellValue(participanteTransaccion.getValorPuntos());
            cell.setCellStyle(textStyle);
        }
    }

    @Override
    public void startWorkbook(SXSSFWorkbook sxssfWorkbook) {
        DataFormat fmt = sxssfWorkbook.createDataFormat();
        textStyle = sxssfWorkbook.createCellStyle();
        textStyle.setDataFormat(fmt.getFormat("@"));
    }
}
