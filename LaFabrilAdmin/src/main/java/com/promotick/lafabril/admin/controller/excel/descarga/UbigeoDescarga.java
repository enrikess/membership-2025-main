package com.promotick.lafabril.admin.controller.excel.descarga;

import com.promotick.apiclient.utils.file.excel.annotations.ExcelClass;
import com.promotick.apiclient.utils.file.excel.annotations.ExcelColumn;
import com.promotick.lafabril.model.web.Ubigeo;
import lombok.Data;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

@Data
@ExcelClass(filename = "UBIGEOS-ECUADOR", sheetName = "Ubigeos de ecuador", versioned = false)
public class UbigeoDescarga implements Serializable {
    private static final long serialVersionUID = 2453713791060015541L;

    @ExcelColumn("Codigo Ubigeo")
    private String codubigeo;

    @ExcelColumn("Pais")
    private String pais;

    @ExcelColumn("Provincia")
    private String provincia;

    @ExcelColumn("Ciudad")
    private String ciudad;

    public static UbigeoDescarga parseEntity(Ubigeo ubigeo) {
        return new UbigeoDescarga()
                .setCodubigeo(ubigeo.getCodubigeo())
                .setPais(ubigeo.getDepartamento())
                .setProvincia(ubigeo.getProvincia())
                .setCiudad(ubigeo.getDistrito());
    }

    public static List<UbigeoDescarga> parseEntities(List<Ubigeo> list) {
        return list.stream().map(UbigeoDescarga::parseEntity).collect(Collectors.toList());
    }
}
