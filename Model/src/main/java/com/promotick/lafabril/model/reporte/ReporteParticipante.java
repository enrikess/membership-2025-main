package com.promotick.lafabril.model.reporte;

import com.promotick.apiclient.utils.file.excel.annotations.ExcelClass;
import com.promotick.apiclient.utils.file.excel.annotations.ExcelColumn;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
@ExcelClass(filename = "Reporte-participante", sheetName = "Reporte de Participantes")
public class ReporteParticipante implements Serializable {
    private static final long serialVersionUID = -3282340118664155273L;

    @ExcelColumn("ID Participante")
    private Integer idParticipante;

    @ExcelColumn("Nombre Catalogo")
    private String catalogo;

//    @ExcelColumn("Tipo Documento")
//    private String nombreTipoDocumento;

    @ExcelColumn("Nro documento")
    private String nroDocumento;

    @ExcelColumn("Nombres")
    private String nombresParticipante;

    @ExcelColumn("Apellido Paterno")
    private String appaternoParticipante;

    @ExcelColumn("Apellido Materno")
    private String apmaternoParticipante;

    @ExcelColumn("Email")
    private String emailParticipante;

    @ExcelColumn("Telefono")
    private String telefonoParticipante;

    @ExcelColumn("Movil")
    private String movilParticipante;

    @ExcelColumn("Id Broker")
    private String idBroker;

    @ExcelColumn("Broker")
    private String nombreBroker;

    @ExcelColumn("Id Regional")
    private String idRegional;

    @ExcelColumn("Regional")
    private String nombreRegional;

    @ExcelColumn(value = "Fecha Registro", dateFormat = "dd-MM-yyyy")
    private Date fechaCreacion;

    @ExcelColumn(value = "Estado", methodEvaluate = "estadoParticipanteEval")
    private String estado;

    @ExcelColumn("Fecha de Nacimiento")
    private String fecha_nacimiento;

    @ExcelColumn(value = "Genero", methodEvaluate = "generoEval")
    private String generParticipante;

    @ExcelColumn("Estado Civil")
    private String estadoCivil;

    @ExcelColumn("Nro. Hijos")
    private String nro_hijos;

//    @ExcelColumn("Tipo Participante")
//    private String nombreTipoParticipante;

//    @ExcelColumn("Cedula")
//    private String cedula;

    @ExcelColumn("Ciudad")
    private String ciudad;

    @ExcelColumn("Region")
    private String nombreRegion;

//    @ExcelColumn("Razon Social")
//    private String nombreRazonsocial;

//    @ExcelColumn("RUC Comercial")
//    private String rucComercial;

//    @ExcelColumn("Representante")
//    private String nombreVendedor;

//    @ExcelColumn("Promocion")
//    private String promocion;

    @ExcelColumn(value = "Check tyc", methodEvaluate = "checkTyceval")
    private String checkTyc;

    @ExcelColumn(value = "Check Uso Info.", methodEvaluate = "checkUsoDatoseval")
    private String checkUsoDatos;

    @ExcelColumn(value = "Fecha Aceptacion", dateFormat = "dd-MM-yyyy")
    private Date fechaAceptacion;

    @ExcelColumn(value = "Estado Canje", methodEvaluate = "estadoCanjeeval")
    private String estadoCanjear;

    @ExcelColumn("Puntos disponibles")
    private String disponibles;

    private Integer estadoParticipante;

    private Boolean estadoCanjes;

    private Boolean aceptaTyc;

    private Boolean aceptaUsoDatos;

    private String genero;

    private String estadoParticipanteEval() {
        if (this.estadoParticipante == 1) {
            return "ACTIVO";
        }
        return "INACTIVO";
    }

    private String estadoCanjeeval() {
        if (this.estadoCanjes) {
            return "Desbloqueado";
        }
        return "Bloqueado";
    }

    private String checkTyceval() {
        if (this.aceptaTyc) {
            return "SI";
        }
        return "NO";
    }

    private String checkUsoDatoseval() {
        if (this.aceptaUsoDatos) {
            return "SI";
        }
        return "NO";
    }

    private String generoEval() {
        if(this.genero == null){
            return "";
        }else if (this.genero.equals("M")) {
            return "Masculino";
        }else if(this.genero.equals("F")) {
            return "Femenino";
        }else{
            return "";
        }
    }
}
