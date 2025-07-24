package com.promotick.lafabril.dao.utils;

public class ConstantesAdminDAO {

    public static final String SCHEMA_NAME = "sch_admin";
    public static final String SCHEMA_GENERAL = "sch_general";
    public static final String SCHEMA_FACTURACION = "sch_facturacion";

    //Usuario
    public static final String SP_USUARIO_AUTENTICAR = "sp_usuario_autenticar";
    public static final String SP_USUARIO_LISTAR = "sp_usuario_listar";
    public static final String SP_USUARIO_ACTUALIZAR = "sp_usuario_actualizar";

    public static final String SP_MENU_LISTAR_POR_IDROL = "sp_menu_listar_por_idrol";
    public static final String SP_MENU_LISTAR = "sp_menu_listar";
    public static final String SP_ROL_URL_LISTAR = "sp_rol_url_listar";
    public static final String SP_ROL_LISTAR = "sp_rol_listar";
    public static final String SP_ROL_ACTUALZAR = "sp_rol_actualizar";
    public static final String SP_ROL_CONTAR = "sp_rol_contar";

    public static final String SP_CARGA_PARTICIPANTE_REGISTRAR = "sp_participante_carga";
    public static final String SP_CARGA_PARTICIPANTE_ESTADO_REGISTRAR = "sp_participante_estado_carga";

    public static final String SP_CATALOGO_LISTAR = "sp_catalogo_listar";
    public static final String SP_CATALOGO_MULTI_LISTAR = "sp_catalogo_multi_listar";
    public static final String SP_DASHBOARD_OBTENER = "sp_dashboard";

    public static final String SP_USUARIO_PRUEBA_LISTAR = "sp_usuario_prueba_listar";
    public static final String SP_USUARIO_PRUEBA_REGISTRAR = "sp_usuario_prueba_registrar";
    public static final String SP_USUARIO_PRUEBA_ACTUALIZAR_ESTADO = "sp_usuario_prueba_actualizar_estado";
    public static final String SP_PARTICIPANTE_PRUEBA_REGISTRAR = "sp_participante_prueba_registrar";
    public static final String SP_PARTICIPANTE_PRUEBA_OBTENER = "sp_usuario_prueba_obtener";

    public static final String SP_REPORTE_PEDIDO_LISTAR = "sp_reporte_pedido_listar";
    public static final String SP_REPORTE_PEDIDO_CONTAR = "sp_reporte_pedido_contar";
    public static final String SP_REPORTE_PEDIDO_DETALLE_LISTAR_BY_FECHAS = "sp_reporte_pedido_detalle_listar_by_fechas";

    public static final String SP_CATALOGO_ACTUALIZAR = "sp_catalogo_actualizar";
    public static final String SP_CATALOGO_ACTUALIZAR_ESTADO = "sp_catalogo_estado_actualizar";
    public static final String SP_CATALOGO_POPUP_ACTUALIZAR = "sp_catalogo_popup_actualizar";
    public static final String SP_CATALOGO_BANNER_FLOTANTE_ACTUALIZAR = "sp_catalogo_banner_flotante_actualizar";
    public static final String SP_CATEGORIA_LISTAR = "sp_categoria_listar";
    public static final String SP_CATEGORIA_REGISTRAR = "sp_categoria_registrar";
    public static final String SP_SUBCATEGORIA_ORDEN = "sp_categorias_orden";
    public static final String SP_SUBCATEGORIA_ACTUALIZAR = "sp_subcategoria_actualizar";
    public static final String SP_SUBCATEGORIA_LISTAR = "sp_subcategoria_listar";

    public static final String SP_PRODUCTOS_LISTAR = "sp_producto_listar";
    public static final String SP_PRODUCTO_ACTUALIZAR = "sp_producto_actualizar";
    public static final String SP_PRODUCTO_REGISTRAR = "sp_producto_registrar";
    public static final String SP_PRODUCTO_CONTAR = "sp_productos_contar";
    public static final String SP_PRODUCTO_CATALOGO_POR_PRODUCTO = "sp_producto_catalogo_por_producto";
    public static final String SP_PRODUCTO_CATALOGO_ACTUALIZAR_STOCK = "sp_producto_catalogo_stock_actualizar";
    public static final String SP_PRODUCTO_CATALOGO_ACTUALIZAR_ESTADO = "sp_producto_catalogo_cambiar_estado";

    public static final String SP_PRODUCTO_CATEGORIA_REGISTRAR = "sp_producto_destacado_registrar";
    public static final String SP_PRODUCTO_CATEGORIA_ELIMINAR = "sp_producto_destacado_eliminar";

    public static final String SP_PRODUCTO_DESTACADOS_LISTAR = "sp_producto_destacado_listar";
    public static final String SP_PRODUCTO_DESTACADOS_CONTAR = "sp_producto_destacado_contar";
    public static final String SP_PRODUCTO_CATALOGO_LISTADO = "sp_producto_catalogo_listar";
    public static final String SP_PRODUCTO_CATALOGO_CONTAR = "sp_producto_catalogo_contar";
    public static final String SP_PRODUCTO_CATALOGO_REGISTRAR = "sp_producto_catalogo_registrar";

    public static final String SP_CARGA_PUNTOS_EXCEL = "sp_carga_puntos_excel";


    public static final String SP_PARTICIPANTE_OBTENER_ESTADO_CUENTA = "sp_participante_estado_cuenta";
    public static final String SP_PARTICIPANTE_OBTENER_ESTADO_CUENTA_TOTAL = "sp_participante_estado_cuenta_total";
    public static final String SP_PARTICIPANTE_OBTENER_ESTADO_CUENTA_CRON = "sp_participante_estado_cuenta_cron";
    public static final String SP_REPORTE_VISITAS_LISTAR = "sp_reporte_visitas_listar";
    public static final String SP_REPORTE_VISITAS_CONTAR = "sp_reporte_visitas_contar";
    public static final String SP_META_TRIMESTRAL_LISTAR = "sp_meta_trimestral_listar";
    public static final String SP_META_TRIMESTRAL_CONTAR = "sp_meta_trimestral_contar";
    public static final String SP_PARTICIPANTES_OBTENER = "sp_participante_obtener";
    public static final String SP_PARTICIPANTES_LISTAR = "sp_participante_listar";
    public static final String SP_PARTICIPANTES_CONTAR = "sp_participante_contar";
    public static final String SP_PARTICIPANTES_ACTUALIZAR_ESTADO = "sp_participante_actualizar_estado";
    public static final String SP_PARTICIPANTES_ACTUALIZAR_ESTADO_CANJE = "sp_participante_actualizar_estado_canje";
    public static final String SP_PARTICIPANTES_ACTUALIZAR = "sp_participante_actualizar";
    public static final String SP_PRODUCTO_NOVEDOSO_LISTAR = "sp_producto_novedoso_listar";
    public static final String SP_PRODUCTO_NOVEDOSO_CONTAR = "sp_producto_novedoso_contar";
    public static final String SP_PRODUCTO_VISITADO_LISTAR = "sp_producto_visitados_listar";
    public static final String SP_PARTICIPANTES_APROBAR = "sp_participante_aprobar";
    public static final String SP_PARTICIPANTES_APROBAR_DOCUMENTO = "sp_participante_aprobar_by_documento";
    public static final String SP_PARTICIPANTES_ACTIVACION_INDIVIDAL_OBTENER = "sp_participante_activacion_individual_obtener";
    public static final String SP_ACCIONES_LISTAR = "sp_acciones_listar";


    public static final String SP_GRAFICO_VISITAS = "sp_visitas_grafico";

    public static final String SP_REPORTE_VENTAS_LISTAR = "sp_reporte_ventas_listar";
    public static final String SP_REPORTE_VENTAS_CONTAR = "sp_reporte_ventas_contar";
    public static final String SP_REPORTE_PUNTOS_ACREDITADOS_LISTAR = "sp_reporte_puntos_acreditados_listar";
    public static final String SP_REPORTE_PUNTOS_ACREDITADOS_CONTAR = "sp_reporte_puntos_acreditados_contar";
    public static final String SP_CUMPLEADOS_LISTAR = "sp_cumpleanos_listar";
    public static final String SP_TYC_ACTUALIZAR = "sp_tyc_actualizar";

    public static final String SP_PEDIDO_NETSUITE_ERROR_LISTAR = "sp_pedido_netsuite_error_listar";
    public static final String SP_PEDIDO_NETSUITE_ERROR_CONTAR = "sp_pedido_netsuite_error_contar";
    public static final String SP_PEDIDO_NETSUITE_ACTUALIZAR = "sp_pedido_netsuite_actualizar";
    public static final String SP_PEDIDO_NETSUITE_REENVIO = "sp_pedido_netsuite_reenvio";

    public static final String SP_CONCESIONARIO_LISTAR = "sp_concesionarios_listar";
    public static final String SP_TIPO_PARTICIPANTE_LISTAR = "sp_tipo_participante_listar";
    public static final String SP_CATEGORIA_PARTICIPANTE_LISTAR = "sp_categoria_participante_listar";
    public static final String SP_UBIGEO_LISTAR = "sp_ubigeos_listar";
    public static final String SP_UBIGEO_ZONA_LISTAR = "sp_zona_listar";
    public static final String SP_UBIGEO_TIPO_VIVIENDA_LISTAR = "sp_tipo_vivienda_listar";
    public static final String SP_DIRECCIONES_CARGAR = "sp_direcciones_carga";
    public static final String SP_PARTICIPANTE_PORCENTAJE = "sp_participante_porcentaje";

    public static final String SP_BANNER_FILTRO_CONTAR = "sp_banner_filtro_contar";
    public static final String SP_BANNER_FILTRO_LISTAR = "sp_banner_filtro_listar";
    public static final String SP_ACELERADOR_LISTAR = "sp_acelerador_listar";
    public static final String SP_BANNER_REGISTRO = "sp_banner_registrar";
    public static final String SP_ACELERADOR_ACTUALIZAR = "sp_acelerador_actualizar";
    public static final String SP_CONFIGURACION_BANNER_OBTENER = "sp_banner_obtener";

    public static final String SP_CONFIGURACION_WEB_ACTUALIZAR = "sp_configuracion_web_actualizar";

    public static final String SP_PRODUCTOS_REPORTE_EXCEL = "sp_producto_excel_reporte";
    public static final String SP_PARTICIPANTE_REPORTE_EXCEL = "sp_participante_excel_reporte";
    public static final String SP_CATEGORIAS_REPORTE_EXCEL = "sp_categorias_excel_reporte";

    public static final String SP_TAG_PRODUCTO_LISTAR = "sp_tag_producto_listar";

    public static final String SP_PRODUCTO_PROMOCION_REGISTRAR = "sp_producto_promocion_registrar";
    public static final String SP_PRODUCTO_PROMOCION_ELIMINAR = "sp_producto_promocion_eliminar";
    public static final String SP_PRODUCTO_PROMOCION_LISTAR = "sp_producto_promocion_listar";
    public static final String SP_PRODUCTO_PROMOCION_CONTAR = "sp_producto_promocion_contar";

    public static final String SP_ENCUESTA_DETALLE_LISTAR = "sp_encuesta_detalle_listar";
    public static final String SP_ENCUESTA_DETALLE_CONTAR = "sp_encuesta_detalle_contar";

    public static final String SP_REPORTE_FACTURAS_LISTAR = "sp_reporte_factura_listar";
    public static final String SP_REPORTE_FACTURAS_CONTAR = "sp_reporte_factura_contar";

    public static final String SP_CAPACITACION_ADMIN_LISTAR = "sp_capacitacion_admin_listar";
    public static final String SP_CAPACITACION_ADMIN_CONTAR = "sp_capacitacion_admin_contar";
    public static final String SP_CAPACITACION_ADMIN_OBTENER = "sp_capacitacion_admin_obtener";
    public static final String SP_CAPACITACION_ADMIN_ESTADO_CAMBIAR = "sp_capacitacion_admin_estado_cambiar";
    public static final String SP_CAPACITACION_ADMIN_MANTENIMIENTO = "sp_capacitacion_admin_mantenimiento";
    public static final String SP_CAPACITACION_ADMIN_RECURSOS_OBTENER = "sp_capacitacion_admin_recursos_obtener";
    public static final String SP_CAPACITACION_ADMIN_RECURSOS_ORDENAR = "sp_capacitacion_admin_recursos_ordenar";
    public static final String SP_CAPACITACION_ADMIN_RECURSOS_ELIMINAR = "sp_capacitacion_admin_recursos_eliminar";
    public static final String SP_CAPACITACION_ADMIN_RECURSOS_ACTIVAR = "sp_capacitacion_admin_recursos_activar";
    public static final String SP_CAPACITACION_ADMIN_RECURSOS_LISTAR_TODOS = "sp_capacitacion_admin_recursos_listar_todos";
    public static final String SP_CAPACITACION_ADMIN_RECURSOS_MANTENIMIENTO = "sp_capacitacion_admin_recurso_mantenimiento";
    public static final String SP_CAPACITACION_ADMIN_PREGUNTAS_LISTAR = "sp_capacitacion_admin_preguntas_listar";
    public static final String SP_CAPACITACION_ADMIN_PREGUNTAS_OBTENER = "sp_capacitacion_admin_preguntas_obtener";
    public static final String SP_CAPACITACION_ADMIN_RESPUESTAS_LISTAR = "sp_capacitacion_admin_respuestas_listar";
    public static final String SP_CAPACITACION_ADMIN_RESPUESTAS_OBTENER_BY_PREGUNTA = "sp_capacitacion_admin_respuestas_listar_by_pregunta";
    public static final String SP_CAPACITACION_ADMIN_PREGUNTAS_ESTADO_CAMBIAR = "sp_capacitacion_admin_preguntas_estado_cambiar";
    public static final String SP_CAPACITACION_ADMIN_PREGUNTAS_ORDENAR = "sp_capacitacion_admin_preguntas_ordenar";
    public static final String SP_CAPACITACION_ADMIN_PREGUNTAS_ACTUALIZAR = "sp_capacitacion_admin_pregunta_actualizar";
    public static final String SP_CAPACITACION_ADMIN_RESPUESTA_MANTENIMIENTO = "sp_capacitacion_admin_respuesta_mantenimiento";
    public static final String SP_CAPACITACION_ADMIN_RESPUESTAS_ORDENAR = "sp_capacitacion_admin_respuestas_ordenar";
    public static final String SP_CAPACITACION_ADMIN_PREGUNTA_NUEVO = "sp_capacitacion_admin_pregunta_nuevo";
    public static final String SP_CAPACITACION_REPORTE_LISTAR = "sp_reporte_capacitacion_listar";
    public static final String SP_CAPACITACION_REPORTE_CONTAR = "sp_reporte_capacitacion_contar";
    public static final String SP_CAPACITACION_PARTICIPANTE_REGISTRAR_PUNTOS = "sp_capacitacion_participante_registrar_puntos";

    public static final String SP_BROKER_LISTAR = "sp_broker_listar";
    public static final String SP_REGIONAL_LISTAR = "sp_regional_listar";

    public static final String SP_REPORTE_PASARELA_LISTAR = "sp_reporte_pasarela_listar";
    public static final String SP_REPORTE_PASARELA_CONTAR = "sp_reporte_pasarela_contar";

    public static final String SP_REPORTE_DESCUENTO_LISTAR = "sp_reporte_descuento_listar";
    public static final String SP_REPORTE_DESCUENTO_CONTAR = "sp_reporte_descuento_contar";

    public static final String SP_CAMPANIA_LISTAR = "sp_campania_listar";
    public static final String SP_CAMPANIA_ACTUALIZAR = "sp_campania_actualizar";
    public static final String SP_CAMPANIA_ACTUALIZAR_ESTADO = "sp_campania_estado_actualizar";

    public static final String SP_DESCUENTO_LISTAR = "sp_descuento_listar";
    public static final String SP_DESCUENTO_ACTUALIZAR = "sp_descuento_actualizar";
    public static final String SP_DESCUENTO_ACTUALIZAR_ESTADO = "sp_descuento_estado_actualizar";

    /******************** MUNDIAL ***********************/
    public static final String SP_REPORTE_MUNDIAL_TRIVIA_LISTAR = "sp_reporte_mundial_trivia_listar";
    public static final String SP_REPORTE_MUNDIAL_TRIVIA_CONTAR = "sp_reporte_mundial_trivia_contar";

    public static final String SP_REPORTE_MUNDIAL_RANKING_LISTAR = "sp_reporte_mundial_ranking_listar";
    public static final String SP_REPORTE_MUNDIAL_RANKING_CONTAR = "sp_reporte_mundial_ranking_contar";

    public static final String SP_REPORTE_MUNDIAL_ESTADO_CUENTA = "sp_reporte_mundial_estado_cuenta";
    public static final String SP_REPORTE_MUNDIAL_ESTADO_CUENTA_TOTAL = "sp_reporte_mundial_estado_cuenta_total";

    public static final String SP_REPORTE_MUNDIAL_POLLA_LISTAR = "sp_reporte_mundial_polla_listar";
    public static final String SP_REPORTE_MUNDIAL_POLLA_CONTAR = "sp_reporte_mundial_polla_contar";

    public static final String SP_REPORTE_MUNDIAL_PRONOSTICO_LISTAR = "sp_reporte_mundial_pronostico_listar";
    public static final String SP_REPORTE_MUNDIAL_PRONOSTICO_CONTAR = "sp_reporte_mundial_pronostico_contar";
    public static final String SP_REPORTE_MUNDIAL_FECHAS_LISTAR = "sp_reporte_mundial_fechas_listar";

    public static final String SP_REPORTE_MUNDIAL_CONSOLIDADO_LISTAR = "sp_reporte_mundial_consolidado_listar";
    public static final String SP_REPORTE_MUNDIAL_CONSOLIDADO_CONTAR = "sp_reporte_mundial_consolidado_contar";

    public static final String SP_REPORTE_RECOMENDADO_LISTAR = "sp_reporte_recomendado_listar";
    public static final String SP_REPORTE_RECOMENDADO_CONTAR = "sp_reporte_recomendado_contar";
    public static final String SP_REPORTE_RECOMENDADO_LISTAR_BY_FECHAS = "sp_reporte_recomendado_listar_by_fechas";
    public static final String SP_CARGA_RECOMENDADOS_ESTADO_REGISTRAR = "sp_recomendado_estado_carga";

    /************************* INTEGRATION ***************************/
    public static final String SP_MARCA_ACTUALIZAR_INTEGRATION = "sp_marca_actualizar_integration";
    public static final String SP_CATEGORIA_ACTUALIZAR_INTEGRATION = "sp_categoria_actualizar_integration";
    public static final String SP_PRODUCTO_ACTUALIZAR_INTEGRATION = "sp_producto_actualizar_integration";
    public static final String SP_PRODUCTO_CATALOGO_REGISTRAR_INTEGRATION = "sp_producto_catalogo_registrar_integration";
    public static final String SP_CAMPANIA_ACTUALIZAR_INTEGRATION = "sp_campania_actualizar_integration";

    /************************* CRON INTEGRATION ***************************/
    public static final String SP_RESTAURAR_PRODUCTOS = "sp_restaurar_productos";
}
