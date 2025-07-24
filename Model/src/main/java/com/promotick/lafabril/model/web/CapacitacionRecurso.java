package com.promotick.lafabril.model.web;

import com.promotick.lafabril.model.util.BeanBase;
import com.promotick.lafabril.model.util.enums.TipoRecursoEnum;
import lombok.Data;

@Data
public class CapacitacionRecurso extends BeanBase {
    private static final long serialVersionUID = 5083456468707422466L;

    private Integer idCapacitacionRecurso;
    private Integer idCapacitacion;
    private Integer idTipoRecurso;
    private String contenido;
    private Boolean estadoCapacitacionRecurso;
    private String nombreCapacitacionRecurso;
    private Integer ordenRecurso;
    private String fechaCreacionString;

    public TipoRecursoEnum getTipoRecursoEnum() {
        return TipoRecursoEnum.getByCode(idTipoRecurso);
    }

    public boolean esTexto() {
        return getTipoRecursoEnum().equals(TipoRecursoEnum.TEXTO);
    }

    public boolean esImagen() {
        return getTipoRecursoEnum().equals(TipoRecursoEnum.IMAGEN);
    }

    public boolean esVideo() {
        return getTipoRecursoEnum().equals(TipoRecursoEnum.VIDEO);
    }

    public boolean esPDF() {
        return getTipoRecursoEnum().equals(TipoRecursoEnum.PDF);
    }
}
