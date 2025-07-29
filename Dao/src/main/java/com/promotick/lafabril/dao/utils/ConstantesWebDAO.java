package com.promotick.lafabril.dao.utils;

public class ConstantesWebDAO {

    /******************** LOGIN ***********************/
    public static final String SCHEMA_NAME = "sch_general";
    public static final String SCHEMA_NAME_API = "sch_api";
    public static final String SP_LOGIN_PARTICIPANTE = "sp_login_participante";

    /******************** CONFIGURACION ***********************/
    public static final String SP_CONFIGURACION_BANNER_LISTAR = "sp_configuracion_banner_listar";
    public static final String SP_TIPO_DOCUMENTO_LISTAR = "sp_tipo_documento_listar";
    public static final String SP_CONFIGURACION_WEB_OBTENER = "sp_configuracion_web_obtener";

    /******************** PRODUCTO ***********************/
    public static final String SP_PRODUCTO_CATALOGO_LISTAR = "sp_producto_catalogo_listar";
    public static final String SP_PRODUCTO_CATALOGO_CONTAR = "sp_producto_catalogo_contar";
    public static final String SP_PRODUCTO_CATALOGO_INTERES_LISTAR = "sp_producto_catalogo_interes_listar";
    public static final String SP_PRODUCTO_OBTENER = "sp_producto_obtener";
    public static final String SP_PRODUCTO_OBTENER_RANGO_PUNTOS = "sp_producto_obtener_rango_puntos";
    public static final String SP_PRODUCTO_OBTENER_CATEGORIA = "sp_producto_obtener_categoria";
    public static final String SP_PRODUCTO_ACTUALIZAR_STOCK = "sp_producto_actualizar_stock";

    /******************** CATEGORIA ***********************/
    public static final String SP_CATEGORIA_LISTAR = "sp_subcategoria_listar";
    public static final String SP_CATEGORIA_GENERAL_LISTAR = "sp_categoria_general_listar";
    public static final String SP_CATEGORIA_OBTENER = "sp_categoria_obtener";
    public static final String SP_CATEGORIA_LISTAR_HIJAS = "sp_categoria_hijos_listar";

    /******************** WISHLIST *********************/
    public static final String SP_WISHLIST_PARTICIPANTE_LISTAR = "sp_wishlist_participante_listar";
    public static final String SP_WISHLIST_PRODUCTO_UPDATE = "sp_wishlist_producto_update";

    /******************** PARTICIPANTE *********************/
    public static final String SP_PARTICIPANTE_DIRECCION = "sp_participante_direccion";
    public static final String SP_PARTICIPANTE_PUNTOS_DISPONIBLES = "fn_puntos_disponibles";
    public static final String SP_PARTICIPANTE_PUNTOS_ACUMULADOS = "fn_puntos_calcular_acumulados";
    public static final String SP_PARTICIPANTE_PUNTOS_CANJEADOS = "fn_puntos_calcular_canjeados";
    public static final String SP_PARTICIPANTE_PUNTOS_POSIBLES = "fn_puntos_calcular_posibles";
    public static final String SP_PARTICIPANTE_PUNTOS_ACUMULADOS_POR_FECHA = "fn_puntos_calcular_acumulados_por_fecha";
    public static final String SP_PARTICIPANTE_PUNTOS_CANJEADOS_POR_FECHA = "fn_puntos_calcular_canjeados_por_fecha";
    public static final String SP_PARTICIPANTE_PUNTOS_VENCIDOS_POR_FECHA = "fn_puntos_calcular_vencidos_por_fecha";
    public static final String SP_PARTICIPANTE_PUNTOS_POSIBLES_POR_FECHA = "fn_puntos_calcular_posibles_por_fecha";
    public static final String SP_PARTICIPANTE_PUNTOS_DESCARGADOS_POR_FECHA = "fn_puntos_calcular_descargados_por_fecha";
    public static final String SP_PARTICIPANTE_ULTIMA_ACTUALIZACION = "fn_puntos_ultima_actualizacion";
    public static final String SP_PARTICIPANTE_OBTENER_POR_NRO_DOCUMENTO = "sp_participante_obtener_nro_documento";
    public static final String SP_PARTICIPANTE_TRANSACCION_LISTAR = "sp_participante_transaccion_listar";
    public static final String SP_PARTICIPANTE_TRANSACCION_CONTAR = "sp_participante_transaccion_contar";
    public static final String SP_PARTICIPANTE_OBTENER_EMAIL = "sp_participante_obtener_email";
    public static final String SP_PARTICIPANTE_ACTUALIZAR_DATOS = "sp_participante_actualizar_datos";
    public static final String SP_PARTICIPANTE_REGISTRO = "sp_participante_registro";
    public static final String SP_PARTICIPANTE_ACTUALIZAR_PERFIL = "sp_participante_actualizar_perfil";
    public static final String SP_PARTICIPANTE_REGISTRAR_FACTURA = "sp_participante_registrar_factura";

    /********************* DIRECCION ***********************/
    public static final String SP_DIRECCION_REGISTRAR = "sp_direccion_registrar";
    public static final String SP_DIRECCION_PARTICIPANTE_REGISTRAR = "sp_participante_direccion_registrar";

    /********************* PEDIDO ***********************/
    public static final String SP_PEDIDO_DETALLE_LISTAR = "sp_pedido_detalle_listar";
    public static final String SP_PEDIDO_REGISTRAR = "sp_pedido_registrar";
    public static final String SP_PEDIDO_CANJE_REGISTRAR = "sp_participante_puntos_canje_registrar";
    public static final String SP_PEDIDO_DETALLE_REGISTRAR = "sp_pedido_detalle_registrar";
    public static final String SP_PEDIDO_LISTAR_BY_ID = "sp_pedido_listar_by_id";
    public static final String SP_PEDIDO_NETSUITE_ACTUALIZAR = "sp_pedido_netsuite_actualizar";

    public static final String SP_FAQ_LISTAR = "sp_faq_listar";
    public static final String SP_TYC_LISTAR = "sp_tyc_listar";

    /********************* CONTACTO *********************/
    public static final String SP_CONTACTO_REGISTRAR = "sp_contacto_registrar";

    /****************************************************/
    public static final String SP_MARCAS_LISTAR = "sp_marca_listar";
    public static final String SP_MARCA_ACTUALIZAR = "sp_marca_actualizar";
    public static final String SP_MARCA_REGISTRAR = "sp_marca_registrar";
    public static final String SP_MARCA_CONTAR = "sp_marca_contar";
    public static final String SP_MARCAS_FILTRO_LISTAR = "sp_marca_filtro_listar";
    public static final String SP_MARCA_FILTRO_CONTAR = "sp_marca_filtro_contar";

    public static final String SP_TRANSACCION_TOKEN_REGISTRAR = "sp_transaccion_token_registrar";
    public static final String SP_TRANSACCION_TOKEN_VALIDAR = "sp_transaccion_token_validar";

    public static final String SP_TRANSACCION_REGISTRAR_WEB = "sp_transaccion_registrar_web";

    /*UBIGEO*/
    public static final String SP_UBIGEO_DEPARTAMENTOS = "sp_ubigeo_listar_departamentos";
    public static final String SP_UBIGEO_PROVINCIAS = "sp_ubigeo_listar_provincias";
    public static final String SP_UBIGEO_DISTRITOS = "sp_ubigeo_listar_distritos";
    public static final String SP_UBIGEO_OBTENER_ID = "sp_ubigeo_obtener_id";

    public static final String SP_DIRECCIONES_PARTICIPANTE_LISTAR = "sp_direcciones_participante_listar";
    public static final String SP_DIRECCIONES_PARTICIPANTE_ELIMINAR = "sp_direcciones_participante_eliminar";
    public static final String SP_DIRECCION_OBTENER = "sp_direccion_obtener";
    /*ZONA*/
    public static final String SP_ZONAS_LISTAR = "sp_zonas_listar";

    /*TIPO VIVIENDA*/
    public static final String SP_TIPO_VIVIENDA_LISTAR = "sp_tipo_vivienda_listar";

    /*SubtipoParticipante*/
    public static final String SP_SUB_TIPO_PARTICIPANTE_LISTAR = "sp_subtipo_participante_listar";

    //Cotizacion
    public static final String SP_GUARDAR_COTIZACION = "sp_guardar_cotizacion";
    public static final String SP_GUARDAR_DATOS_PASAJERO = "sp_guardar_datos_pasajero";

    //Festividades
    public static final String SP_LISTAR_FESTIVIDADES = "sp_festividad_listar_mail";
    public static final String SP_LISTAR_FESTIVIDADES_PARTICIPANTES = "sp_festividad_listar_participantes";

    //Mensaje
    public static final String SP_MENSAJE_OBTENER = "sp_obtener_mensaje";

    //Razon Social
    public static final String SP_RAZONSOCIAL_LISTAR = "sp_razonsocial_listar";

    //Razon Social
    public static final String SP_REGION_LISTAR = "sp_region_listar";

    //Encuesta
    public static final String SP_ENCUESTA_REGISTRAR = "sp_encuesta_registrar";
    public static final String SP_ENCUESTA_REGISTRAR_DETALLE = "sp_encuesta_registrar_detalle";
    public static final String SP_ENCUESTA_PEDIDO_OBTENER = "sp_encuesta_pedido_obtener";
    public static final String SP_ENCUESTA_OMITIR = "sp_encuesta_omitir";

    public static final String SP_PATICIPANTE_META_OBTENER ="sp_participante_meta_obtener";

    //Petshop
    public static final String SP_PETSHOP_LISTAR = "sp_petshop_listar";

    //Top10
    public static final String SP_PARTICIPANTE_TOP_BROKER = "sp_participante_top_broker";
    public static final String SP_PARTICIPANTE_ACTUALIZAR_FOTO = "sp_participante_actualizar_foto";
    public static final String SP_PARTICIPANTE_META_LISTAR = "sp_participante_meta_listar";
    public static final String SP_PARTICIPANTE_META_OBTENER_BY_AVANCE = "sp_participante_meta_obtener_by_avance";

    //CARGA WEB
    public static final String SP_CARGA_WEB_REGISTRAR = "sp_carga_registrar";
    public static final String SP_CARGA_WEB_ACTUALIZAR = "sp_carga_actualizar";

    //Capacitacion
    public static final String SP_CAPACITACION_LISTAR = "sp_capacitacion_listar";
    public static final String SP_CAPACITACION_OBTENER = "sp_capacitacion_obtener";
    public static final String SP_CAPACITACION_RECURSOS_LISTAR = "sp_capacitacion_recursos_listar";
    public static final String SP_CAPACITACION_PREGUNTAS_LISTAR = "sp_capacitacion_preguntas_listar";
    public static final String SP_CAPACITACION_RESPUESTAS_LISTAR = "sp_capacitacion_respuestas_listar";
    public static final String SP_CAPACITACION_PARTICIPANTE_MANTENIMIENTO = "sp_capacitacion_participante_mantenimiento";
    public static final String SP_CAPACITACION_PARTICIPANTE_DETALLE_REGISTRAR = "sp_capacitacion_participante_detalle_registrar";
    public static final String SP_CAPACITACION_PARTICIPANTE_OBTENER = "sp_capacitacion_particpante_obtener";
    public static final String SP_CAPACITACION_PARTICIPANTE_DETALLE_LISTAR = "sp_capacitacion_particpante_detalle_listar";
    public static final String SP_CAPACITACION_PARTICIPANTE_PREGUNTA_REGISTRAR = "sp_capacitacion_participante_pregunta_registrar";


    public static final String SP_PAYMENT_REGISTRAR = "sp_payment_registrar";
    public static final String SP_PAYMENT_ACTUALIZAR = "sp_payment_actualizar";
    public static final String SP_PAYMENT_OBTENER = "sp_payment_obtener";

    /************** Mis recomendados (referidos) **************/
    public static final String SP_REGISTRAR_RECOMENDADO = "sp_recomendado_registrar";
    public static final String SP_LISTAR_RECOMENDADO = "sp_recomendado_listar";


    public static final String SP_PARTICIPANTE_TOKEN_GENERAR = "sp_participante_token_generar";
    public static final String SP_PARTICIPANTE_TOKEN_VALIDAR = "sp_participante_token_validar";
    public static final String SP_PARTICIPANTE_OBTENER_TOKEN = "sp_participante_obtener_por_token";

    //Descuento
    public static final String FN_PEDIDO_DESCUENTO_OBTENER = "fn_pedido_descuento_obtener";
    public static final String FN_PEDIDO_DESCUENTO_VALIDAR = "fn_pedido_descuento_validar";

    /******************** MUNDIAL ***********************/
    public static final String SP_MUNDIAL_TRIVIA_OBTENER = "sp_mundial_trivia_obtener";
    public static final String SP_MUNDIAL_TRIVIA_RESPUESTA_REGISTRAR = "sp_mundial_trivia_respuesta_registrar";
    public static final String SP_MUNDIAL_TRIVIA_ESTADO_ACTUALIZAR = "sp_mundial_trivia_estado_actualizar";
    public static final String SP_MUNDIAL_TRIVIA_RESUMEN_OBTENER = "sp_mundial_trivia_resumen_obtener";
    public static final String SP_MUNDIAL_TRIVIA_RESPUESTA_OBTENER = "sp_mundial_trivia_respuesta_obtener";
    public static final String SP_MUNDIAL_PAIS_LISTAR = "sp_mundial_pais_listar";
    public static final String SP_MUNDIAL_POLLA_PARTICIPANTE_REGISTRAR = "sp_mundial_polla_participante_registrar";
    public static final String SP_MUNDIAL_POLLA_PARTICIPANTE_RESUMEN = "sp_mundial_polla_participante_resumen";
    public static final String SP_MUNDIAL_RESUMEN_OBTENER = "sp_mundial_resumen_obtener";
    public static final String SP_MUNDIAL_RANKING_OBTENER = "sp_mundial_ranking_obtener";
    public static final String SP_MUNDIAL_CRON_ACTUALIZAR_CATEGORIA_PARTICIPANTE = "sp_mundial_cron_actualizar_categoria_participante";
    public static final String SP_MUNDIAL_CONFIGURACION_OBTENER = "sp_mundial_configuracion_obtener";
    public static final String SP_MUNDIAL_PARTIDO_LISTAR = "sp_mundial_partido_listar";
    public static final String SP_MUNDIAL_PARTIDO_PRONOSTICO_LISTAR = "sp_mundial_partido_pronostico_listar";
    public static final String SP_MUNDIAL_PRONOSTICO_PARTICIPANTE_REGISTRAR = "sp_mundial_pronostico_participante_registrar";
    public static final String SP_MUNDIAL_PRONOSTICO_RESPUESTA_OBTENER = "sp_mundial_pronostico_respuesta_obtener";


    //META
    public static final String SP_PARTICIPANTE_META_AVANCE_OBTENER = "sp_participante_meta_avance_obtener";
    public static final String SP_META_TRIMESTRAL_OBTENER = "sp_meta_trimestral_obtener";


    //LOG
    public static final String LOG_SCHEMA_NAME = "public";
    public static final String SP_LOG_GUARDAR = "fn_log_guardar";


}
