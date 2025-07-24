package com.promotick.lafabril.admin.controller.excel;

import com.promotick.apiclient.utils.file.excel.generator.AbstractPOIExcelView;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.DataFormat;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public class ReporteEstadoCuentaExcelView extends AbstractPOIExcelView<Map<String, Object>> {

    private CellStyle textStyle;

    @Override
    public List<Map<String, Object>> list() {
        return this.findList("listaEstadoCuenta");
    }

    @Override
    public String fileName() {
        return (String) this.findAttribute("fileName");
    }

    @Override
    public String sheetName() {
        return "Reporte-estado-cuenta";
    }

    @Override
    public String[] headersName() {
        if (list() != null && !list().isEmpty()) {
            String[] cabeceras = new String[list().get(0).size()];

            int i = 0;
            for (String key :  list().get(0).keySet()) {
                cabeceras[i]=key;
                i++;
            }

            return cabeceras;
        } else {
            return new String[0];
        }
    }

    @Override
    public boolean isExcelError() {
        return false;
    }

    @Override
    public void buildExcelDocument(Map<String, Object> stringObjectMap, SXSSFWorkbook sxssfWorkbook, SXSSFSheet sxssfSheet, Row row, Integer integer, Map<Integer, Integer> map) throws Exception {
        int c = 0;
        Cell cell;
        for (String key : list().get(integer - 1).keySet()) {
            cell = row.createCell(c);
            cell.setCellValue(stringObjectMap.get(key).toString());
            cell.setCellStyle(textStyle);
            c++;
        }
    }

    @Override
    public void startWorkbook(SXSSFWorkbook sxssfWorkbook) {
        DataFormat fmt = sxssfWorkbook.createDataFormat();
        textStyle = sxssfWorkbook.createCellStyle();
        textStyle.setDataFormat(fmt.getFormat("@"));
    }
}