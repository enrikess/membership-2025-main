package com.promotick.lafabril.dao.utils;

public class ConstantesFacturacionDAO {
    public static final String SCHEMA_NAME = "sch_facturacion";

    //Participantes
    public static final String SP_PARTICIPANTE_OBTENER_META = "fn_obtener_participante_meta";
    public static final String SP_PARTICIPANTE_OBTENER_META_FECHA = "fn_obtener_participante_meta_fecha";
    public static final String SP_PARTICIPANTE_EMAILS_LISTAR = "sp_participantes_email_listar";

    //Producto catalogo
    public static final String SP_PRODUCTO_CATALOGO_ENVIO_EMAIL = "sp_producto_catalogo_envio_email";
    public static final String SP_PRODUCTO_NUEVOS_LISTAR = "sp_productos_nuevos";

    //Factura
    public static final String SP_FACTURA_REPORTE_AGRUPADO = "sp_factura_reporte_agrupado";
    public static final String SP_FACTURA_PARTICIPANTE_LISTAR = "sp_factura_participante_listar";
    public static final String SP_FACTURA_PARTICIPANTE_CONTAR = "sp_factura_participante_contar";
    public static final String SP_FACTURA_ENVIO_EMAIL = "sp_factura_envio_email";
    public static final String SP_CARGA_MANUAL_ENVIO_EMAIL = "sp_carga_manual_envio_email";

    //Periodo participante
    public static final String SP_PERIODO_PARTICIPANTE_ENVIO_EMAIL = "sp_periodo_participante_envio_email";
    public static final String SP_PERIODO_PARTICIPANTE_ENVIO_EMAIL_INDEPENDIENTE = "sp_periodo_participante_envio_email_independiente";

    //Periodo
    public static final String SP_PERIODO_VALIDACION = "fn_periodo_validacion";
    public static final String SP_PERIODO_META_OBTENER = "fn_obtener_meta_participante";
    public static final String SP_PERIODO_CIERRE = "sp_cierre_periodo";
    public static final String SP_PERIODO_RECALCULO_METAS = "sp_participantes_meta_recalculo";

    //Proceso
    public static final String SP_PROCESO_REGISTRAR = "sp_proceso_creacion";
    public static final String SP_PROCESO_FINALIZAR = "sp_proceso_finalizar";
    public static final String SP_PROCESO_EN_EJECUCION = "fn_proceso_en_ejecucion";
    public static final String SP_PROCESO_VENCIMIENTO = "sp_proceso_vencimiento";

    //Carga
    public static final String SP_CARGA_REGISTRAR = "sp_carga_creacion";
    public static final String SP_CARGA_VALIDAR_EXISTENCIA = "sp_carga_validar_existencia";

    //CargaProceso
    public static final String SP_CARGA_PROCESO_REGISTRAR = "sp_carga_proceso_creacion";
    public static final String SP_CARGA_PROCESO_ACTUALIZAR = "sp_carga_proceso_actualizar";

    //Bulk
    public static final String SP_BULK_PARTICIPANTE_REGISTRO = "sp_bulk_participantes_registro";
    public static final String SP_BULK_PARTICIPANTE_PROCESAR = "sp_bulk_participantes_procesar";
    public static final String SP_BULK_FACTURAS_REGISTRO = "sp_bulk_facturas_registro";
    public static final String SP_BULK_FACTURAS_PROCESAR = "sp_bulk_facturas_procesar";

    public static final String SP_CARGA_METAS_EXCEL = "sp_carga_metas_excel";
    public static final String SP_CARGA_METAS_EXCEL_V2 = "sp_carga_metas_excel_v2";
    public static final String SP_META_TRIMESTRAL_CREAR_ACTUALIZAR = "sp_meta_trimestral_crear_actualizar";

    public static final String SP_CARGA_VENTAS_EXCEL = "sp_carga_ventas_excel";
    public static final String SP_CARGA_VENTAS_EXCEL_V2 = "sp_carga_ventas_excel_v2";

    public static final String SP_TYC_ACTUALIZAR = "sp_tyc_actualizar";
    public static final String SP_FAQ_ACTUALIZAR = "sp_faq_actualizar";
    public static final String SP_CARGA_ACCIONES_EXCEL_V2 = "sp_carga_acciones_excel_v2";

}
