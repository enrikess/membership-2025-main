package com.promotick.lafabril.admin.controller.excel.descarga;

import com.promotick.apiclient.utils.file.excel.annotations.ExcelClass;
import com.promotick.apiclient.utils.file.excel.annotations.ExcelColumn;
import com.promotick.lafabril.model.web.Capacitacion;
import lombok.Data;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

@Data
@ExcelClass(filename = "CAPACITACIONES", sheetName = "CAPACITACIONES")
public class CapacitacionDescarga implements Serializable {

    private static final long serialVersionUID = -4121589747912847985L;

    @ExcelColumn("ID CAPACITACION")
    private Integer idCapacitacion;

    @ExcelColumn("NOMBRE CAPACITACION")
    private String nombreCapacitacion;

    @ExcelColumn("CATALOGO")
    private String nombreCatalogo;

    @ExcelColumn("DESCRIPCION CAPACITACION")
    private String descripcionCapacitacion;

    @ExcelColumn("IMAGEN PRINCIPAL")
    private String imagenUno;

    @ExcelColumn("IMAGEN DETALLE")
    private String imagenDetalle;

    @ExcelColumn("FECHA INICIO")
    private String fechaInicio;

    @ExcelColumn("FECHA FIN")
    private String fechaFin;

    @ExcelColumn("CANTIDAD RECURSOS")
    private Integer cantidadRecursos;

    @ExcelColumn("CANTIDAD PREGUNTAS")
    private Integer cantidadPreguntas;

    @ExcelColumn("CONDICION")
    private String estado;

    @ExcelColumn("PUNTAJE CAPACITACION")
    private Integer puntos;

    public static CapacitacionDescarga parseEntity(Capacitacion capacitacion) {
        return new CapacitacionDescarga()
                .setIdCapacitacion(capacitacion.getIdCapacitacion())
                .setNombreCapacitacion(capacitacion.getNombreCapacitacion())
                .setNombreCatalogo(capacitacion.getNombreCatalogo())
                .setDescripcionCapacitacion(capacitacion.getDescripcionCapacitacion())
                .setImagenUno(capacitacion.getImagenUno())
                .setImagenDetalle(capacitacion.getImagenDetalle())
                .setFechaInicio(capacitacion.getFechaInicioString())
                .setFechaFin(capacitacion.getFechaFinString())
                .setCantidadRecursos(capacitacion.getConteoRecursos())
                .setCantidadPreguntas(capacitacion.getConteoPreguntas())
                .setPuntos(capacitacion.getPuntosCapacitacion())
                .setEstado(capacitacion.getEstadoCapacitacionString());

    }

    public static List<CapacitacionDescarga> parseEntities(List<Capacitacion> list) {
        return list.stream().map(CapacitacionDescarga::parseEntity).collect(Collectors.toList());
    }
}
