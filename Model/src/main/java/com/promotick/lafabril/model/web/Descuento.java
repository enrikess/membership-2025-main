package com.promotick.lafabril.model.web;



import com.promotick.lafabril.common.UtilCommon;
import com.promotick.lafabril.model.util.BeanBase;
import lombok.Data;

import java.util.Date;

@Data
public class Descuento extends BeanBase {

	private static final long serialVersionUID = 3198980267248047773L;

	private Integer idDescuento;
	private TipoDescuento tipoDescuento;
	private String nombreDescuento;
	private Date fechaInicio;
	private Date fechaFin;
	private Catalogo catalogo;
	private Integer valorDescuento;
	private Integer estadoDescuento;
	private String codigoDescuento;
	private Integer puntosDescuento = 0;

	//no persiste
	private String fechaInicioString;
	private String fechaFinString;

	public String getFechaInicioString() {
		if( fechaInicio != null ){
			fechaInicioString = UtilCommon.dateFormat(fechaInicio, UtilCommon.FORMATO_FECHA_DIA_MES_ANIO_HORA_2);
		}
		return fechaInicioString;
	}


	public String getFechaFinString() {
		if(fechaFin != null) {
			fechaFinString = UtilCommon.dateFormat(fechaFin, UtilCommon.FORMATO_FECHA_DIA_MES_ANIO_HORA_2);
		}
		return fechaFinString;
	}
}
