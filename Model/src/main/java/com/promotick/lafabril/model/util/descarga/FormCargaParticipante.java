package com.promotick.lafabril.model.util.descarga;

import com.promotick.apiclient.utils.file.excel.annotations.ExcelClass;
import com.promotick.apiclient.utils.file.excel.annotations.ExcelColumn;
import lombok.Data;

import java.io.Serializable;

@Data
@ExcelClass(filename = "Carga-Participantes-Error", sheetName = "Carga Participantes Error", error = true)
public class FormCargaParticipante implements Serializable {
    private static final long serialVersionUID = 4277188805443742889L;

    @ExcelColumn("Nombres*")
    private String nombresParticipante;

    @ExcelColumn("Apellido Paterno*")
    private String appaternoParticipante;

    @ExcelColumn("Apellido Materno*")
    private String apmaternoParticipante;

    @ExcelColumn("Email*")
    private String emailParticipante;

//    @ExcelColumn("Tipo Documento*")
//    private String tipoDocumento;

    @ExcelColumn("Clave")
    private String clave;

    @ExcelColumn("Nro Documento*")
    private String nroDocumento;

    @ExcelColumn("Telefono Fijo*")
    private String telefonoParticipante;

    @ExcelColumn("Celular")
    private String movilParticipante;

    @ExcelColumn("Estado*")
    private String estadoParticipante;

    @ExcelColumn("Catalogo*")
    private String idCatalogo;

    @ExcelColumn("Broker*")
    private String idBroker;

    @ExcelColumn("Ciudad")
    private String ciudad;

//    @ExcelColumn("Vendedor")
//    private String nombreVendedor;
//
//    @ExcelColumn("Cliente")
//    private String cliente;

    @ExcelColumn("ID Regional")
    private String regional;

//    @ExcelColumn("Tipo Participante")
//    private String tipoParticipante;

    @ExcelColumn("Estado Canje")
    private String estadoCanje;

//    @ExcelColumn("Codigo Cliente")
//    private String codigoCliente;
//
//    @ExcelColumn("Categoria")
//    private String categoria;

    @ExcelColumn("Genero")
    private String genero;

    @ExcelColumn("Fecha nacimiento")
    private String fechaNacimiento;

    @ExcelColumn("Estado civil")
    private String estadoCivil;

    @ExcelColumn("Nro Hijos")
    private String nroHijos;

    @ExcelColumn("Provincia")
    private String provincia;

    @ExcelColumn("ID Region")
    private String region;

    @ExcelColumn("Error")
    private String error;
}
