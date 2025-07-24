package com.promotick.lafabril.model.reporte;

import com.promotick.apiclient.utils.file.excel.annotations.ExcelClass;
import com.promotick.apiclient.utils.file.excel.annotations.ExcelColumn;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
@ExcelClass(filename = "Reporte-Recomendados", sheetName="Reporte de Recomendados")
public class ReporteRecomendado implements Serializable {

    private static final long serialVersionUID = -270131486232502783L;

    @ExcelColumn("ID Recomendado")
    private Integer idRecomendado;

    @ExcelColumn("Nro Documento")
    private String nroDocumento;

    @ExcelColumn("Nombres")
    private String nombresParticipante;

    @ExcelColumn(value = "Apellidos", methodEvaluate = "apellidosParticipante")
    private String apellidos;

    @ExcelColumn("Correo")
    private String emailParticipante;

    @ExcelColumn("Nro Documento")
    private String nroDocumentoRecomendado;

    @ExcelColumn("Nombres")
    private String nombresRecomendado;

    @ExcelColumn("Apellidos")
    private String apellidosRecomendado;

    @ExcelColumn("Celular")
    private String celularRecomendado;

    @ExcelColumn("Email")
    private String  emailRecomendado;

    @ExcelColumn("Tiempo compra")
    private String tiempoCompra;

    @ExcelColumn("Financiamiento")
    private String financiamiento;

    @ExcelColumn("Observacion")
    private String observacionRecomendado;

    @ExcelColumn(value = "Fecha de Registro", dateFormat = "dd-MM-yyyy")
    private Date fechaRegistro;

    @ExcelColumn("Ciudad")
    private String ciudad;

    @ExcelColumn("Provincia")
    private String provincia;

    @ExcelColumn(value = "Estado", methodEvaluate = "estadoReferido")
    private String estado;


    private String appaternoParticipante;
    private String apmaternoParticipante;
    private Integer estadoRecomendado;


    private String apellidosParticipante(){
        if (this.appaternoParticipante.isEmpty()){
            return appaternoParticipante;
        }else {
            return appaternoParticipante +" "+apmaternoParticipante;
        }
    }

    private String estadoReferido() {
        switch (this.estadoRecomendado){
            case 0:
                return "Negado";
            case 1:
                return "Aprobado";
            default:
                return "Registrado";
        }
    }

}
