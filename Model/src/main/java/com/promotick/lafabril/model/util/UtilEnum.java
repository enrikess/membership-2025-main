package com.promotick.lafabril.model.util;

import lombok.AllArgsConstructor;
import lombok.Getter;

public class UtilEnum {

    @AllArgsConstructor
    public enum ESTADO {
        ACTIVO(1, "Activo"),
        INACTIVO(0,"Inactivo");

        @Getter
        private Integer codigo;
        @Getter
        private String value;
    }

    @AllArgsConstructor
    public enum MANTENIMIENTO {
        REGISTRAR(1, "Registrar"),
        ACTUALIZAR(2, "Actualizar"),
        CAMBIAR_CLAVE(3, "Cambiar clave"),
        CAMBIAR_ESTADO(4, "Cambiar estado"),
        ELIMINAR(5, "Eliminar"),
        IMAGENES(6, "Imagenes"),
        CARGA_EXCEL(7, "Carga de excel"),
        ENVIO_EMAIL(8, "Envio de email"),
        DIRECCION(9, "Direccion"),
        ELIMINAR_DIRECCION(10, "Direccion Multiple"),
        CARGA_PDF(11, "Carga de pdf"),;

        @Getter
        private Integer codigo;
        @Getter
        private String value;
    }

    @AllArgsConstructor
    public enum ESTADO_OPERACION {

        EXITO (0,"EXITO","msg.operacion.exito");

        @Getter
        private Integer codigo;
        @Getter
        private String texto;
        @Getter
        private String key;
    }

    @AllArgsConstructor
    public enum EXCEPTION {
        EXEPTION_TOKEN_NO_VALIDO(-100, "Token no valido","msg.exception.token.no.valido"),
        EXCEPTION(-2, "Error en el servidor", "msg.exception"),
        IO_EXCEPTION(-6,"Error de IO", "msg.exception.io"),
        PUNTOS_INSUFICIENTES(-5,"Puntos no disponibles","msg.exception.puntos.insuficientes"),
        EXCEPTION_ERROR_GENERICO(-1, "ocurrio un error en el proceso","msg.exception.error.generico"),
        EXCEPTION_CORREO_EXISTE(-150, "correo existe","msg.exception.correo.existe"),
        EXCEPTION_TELEFONO_EXISTE(-160, "documento existe","msg.exception.documento.existe"),
        EXCEPTION_LIST_EMPTY(-15,"Resultado vacio",""),
        CORREO_USUARIO_EXISTE(-7,"Usuario existe","msg.exception.usuario.correo.existe"),
        USUARIO_EXISTE (-8,"Usuario existe","msg.exception.usuario.existe"),
        EXCEL_IMPORTAR_LISTA_VACIA(-24,"No se importo ningun registro","msg.exception.importar.excel.vacio"),
        EXCEPTION_CLAVE_INCORRECTA_ACTUALIZAR(-11,"Clave incorrecta","msg.exception.usuario.clave.incorrecta.actualizar"),
        EXCEPTION_NOMBRE_CAMPANIA_EXISTE(-9,"Nombre Campania existe","msg.exception.campania.nombre.existe"),
        EXCEPTION_CAMPANIA_DESHABILITAR(-10,"No se puede deshabilitar","msg.exception.campania.estado.deshabilitar"),
        EXCEPTION_VENTAS_CARGA_IDCAMPANIA(-12,"Debe seleccionar una campania","msg.exception.ventas.carga.idCampania"),
        EXCEPTION_VENTAS_CARGA_CAMPANIA_NOEXISTE(-13,"Campania seleccionada no existe","msg.exception.ventas.carga.campania.noexiste"),
        EXCEPTION_ROLES_EDITAR_NOEXISTE(-14,"Rol seleccionado no existe","msg.exception.roles.editar.noexiste"),
        EXCEPTION_ROLES_NOMBRE_DUPLICADO(-16,"El nombre del rol ya existe","msg.exception.roles.nombre.existe"),
        EXCEPTION_CATALOGO_YA_EXISTE(-17,"El catalogo ya existe","msg.exception.catalogo.existe"),
        EXCEPTION_CATEGORIA_CATALOGO_YA_EXISTE(-18,"La categoria ya existe para este catalogo","msg.exception.categoria.catalogo.existe"),
        EXCEPTION_CAMPANIA_EXISTE(-19,"La campania ya existe","msg.exception.campania.existe"),
        EXCEPTION_CAMPANIA_DESHABILITADA(-20,"La campania se encuentra deshabilitada","msg.exception.campania.deshabilitada"),
        EXCEPTION_MILLA_PAQUETE_EXISTE(-21,"Paquete de millas duplicado","msg.exception.millas.paquete.existe"),
        EXCEPTION_MILLA_EXISTE(-22,"Millas duplicado","msg.exception.millas.existe"),
        EXCEPTION_MARCA_DUPLICADO(-23,"Marca duplicada","msg.exception.marca.existe"),
        EXCEPTION_PRODUCTO_DESTACADO_EXISTE(-25,"Producto destacado ya existe","msg.exception.productodestacado.existe"),
        EXCEPTION_PARTICIPANTE_CATALOGO_NO_EXISTE(-26,"Catalogo no existe","msg.exception.participante.catalogo.no.existe"),
        EXCEPTION_PARTICIPANTE_ERROR_REGISTRO(-27,"Error al registrar","msg.exception.participante.registro.error"),
        EXCEPTION_PARTICIPANTE_ERROR_ACTUALIZACION(-28,"Error al actualizar","msg.exception.participante.actualizacion.error"),
        EXCEPTION_PARTICIPANTE_EMAIL_EXISTE(-29,"Email Existe","msg.exception.participante.email.existe"),
        EXCEPTION_ESTACION_EXISTE(-30,"Estacion Existe","msg.exception.estacion.existe"),
        EXCEPTION_NRO_DOCUMENTO_EXISTE(-31,"Nro de documento ya existe","msg.exception.nrodocumento.existe"),
        EXCEPTION_CODIGO_WEB_EXISTE(-32,"Codigo Web Existe","msg.exception.codigoweb.existe"),
        EXCEPTION_PRODUCTO_PROMOCION_EXISTE(-33,"Producto promocion ya existe","msg.exception.productopromocion.existe"),
        EXCEPTION_UBIGEO_NO_EXISTE(-34, "Ubigeo no encontrado","msg.exception.ubigeo.noexiste"),
        EXCEPTION_ERROR_MULTIDIRECCION(-35, "Ocurrio un error actualizando las direcciones","msg.exception.multidireccion.error"),
        EXCEPTION_USUARIO_NO_EXISTE(-40, "El número de documento no existe","msg.exception.editarventa.usuario.noexiste"),
        EXCEPTION_USUARIO_NO_ACTIVO(-41, "El usuario no se encuentra activo","msg.exception.editarventa.usuario.noactivo");
        @Getter
        private final Integer codigo;
        @Getter
        private final String mensaje;
        @Getter
        private final String key;

    }

    public static EXCEPTION obtenerError(Integer codigo) {
        for (EXCEPTION error : EXCEPTION.values()) {
            if (error.getCodigo().equals(codigo)) {
                return error;
            }
        }
        return EXCEPTION.EXCEPTION;
    }

    @AllArgsConstructor
    public enum CARGA_PRODUCTOS_RESULTS {
        OK(1, "EXITO"),
        ERROR_GENERICO(-999, "ERROR GENERICO"),
        ERROR_INSERTAR_PRODUCTO(-1, "No se pudo grabar los datos"),
        ERROR_CATALOGO_NO_EXISTE(-2, "ID Catalogo no existe"),
        ERROR_CATEGORIA_NO_EXISTE(-3, "Id de Categoria no existe"),
        ERROR_ACTUALIZAR_PRODUCTO(-4, "No se pudo actualizar el registro"),
        ERROR_REGISTRAR_INDV_PRODUCTO(-5, "Codigo web ya existe."),
        ERROR_MARCA_NO_EXISTE(-6, "ID de marca no existe."),
        ERROR_TIPO_PRODUCTO_NO_EXISTE(-7, "Tipo de producto no existe.");

        @Getter
        Integer codigo;
        @Getter
        String mensaje;

        public static CARGA_PRODUCTOS_RESULTS getMensageFromCode(Integer codigo) {
            if (codigo == null) {
                return CARGA_PRODUCTOS_RESULTS.ERROR_GENERICO;
            }

            for (CARGA_PRODUCTOS_RESULTS obj : CARGA_PRODUCTOS_RESULTS.values()) {
                if (obj.codigo.intValue() == codigo.intValue()) {
                    return obj;
                }
            }
            return codigo > 0 ? CARGA_PRODUCTOS_RESULTS.OK : CARGA_PRODUCTOS_RESULTS.ERROR_GENERICO;
        }
    }

    @AllArgsConstructor
    public enum CARGA_VENTAS_RESULTS {
        OK(1, "EXITO"),
        ERROR_GENERICO(-99, "ERROR GENERICO"),
        ERROR_PARTICIPANTE_NO_REGISTRADO(-2, "Participante no se encuentra registrado"),
        ERROR_PARTICIPANTE_NO_ACTIVO(-3, "Participante no se encuentra activo"),
        ERROR_SISTEMA(-1, "Error al procesar solicitud");

        @Getter
        Integer codigo;
        @Getter
        String mensaje;

        public static CARGA_VENTAS_RESULTS getMensageFromCode(Integer codigo) {
            if (codigo == null) {
                return CARGA_VENTAS_RESULTS.ERROR_GENERICO;
            }

            for (CARGA_VENTAS_RESULTS obj : CARGA_VENTAS_RESULTS.values()) {
                if (obj.codigo.intValue() == codigo.intValue()) {
                    return obj;
                }
            }
            return codigo > 0 ? CARGA_VENTAS_RESULTS.OK : CARGA_VENTAS_RESULTS.ERROR_GENERICO;
        }
    }

    @AllArgsConstructor
    public enum CARGA_METAS_RESULTS {
        OK(1, "EXITO"),
        ERROR_GENERICO(-99, "ERROR GENERICO"),
        ERROR_PARTICIPANTE_NO_REGISTRADO(-2, "Participante no se encuentra registrado"),
        ERROR_PARTICIPANTE_NO_ACTIVO(-3, "Participante no se encuentra activo"),
        ERROR_ID_PRODUCTO_INVALIDO(-4, "Codigo de producto invalido"),
        ERROR_SISTEMA(-1, "Error al procesar solicitud");

        @Getter
        Integer codigo;
        @Getter
        String mensaje;

        public static CARGA_METAS_RESULTS getMensageFromCode(Integer codigo) {
            if (codigo == null) {
                return CARGA_METAS_RESULTS.ERROR_GENERICO;
            }

            for (CARGA_METAS_RESULTS obj : CARGA_METAS_RESULTS.values()) {
                if (obj.codigo.intValue() == codigo.intValue()) {
                    return obj;
                }
            }
            return codigo > 0 ? CARGA_METAS_RESULTS.OK : CARGA_METAS_RESULTS.ERROR_GENERICO;
        }
    }

    @AllArgsConstructor
    public enum CARGA_PUNTOS_RESULTS {
        OK(1, "EXITO"),
        ERROR_GENERICO(-99, "ERROR GENERICO"),
        ERROR_SISTEMA(-1, "Error al procesar solicitud"),
        ERROR_PARTICIPANTE_NO_REGISTRADO(-2, "Participante no se encuentra registrado"),
        ERROR_PARTICIPANTE_NO_ACTIVO(-3, "Participante no se encuentra activo"),
        ERROR_ORIGEN_NO_VALIDO(-4, "Origen de transaccion no valida"),
        ERROR_TRANSACCION_ERROR(-5, "Error al registrar transaccion"),
        ERROR_PUNTOS_INSUFICIENTES(-6, "El participante no tiene puntos suficientes para restarle"),
        ERROR_PERIODO_NO_DEFINIDO(-7, "El participante no tiene configurado un periodo para la fecha operacion requerida (Trimestral/Anual)"),
        ERROR_PARTICIPANTE_SIN_DIRECCION(-8, "El participante no tiene una direccion registrada para generar el pedido"),
        ERROR_PUNTOS_POSIBLES_INSUFICIENTES(-9, "Los puntos posibles del participante son diferentes a los puntos que desea traspasar en el periodo establecido"),
        ERROR_PERIODO_PASADO(-10, "La fecha de operacion no coincide con el periodo actual vigente");

        @Getter
        Integer codigo;
        @Getter
        String mensaje;

        public static CARGA_PUNTOS_RESULTS getMensageFromCode(Integer codigo) {
            if (codigo == null) {
                return CARGA_PUNTOS_RESULTS.ERROR_GENERICO;
            }

            for (CARGA_PUNTOS_RESULTS obj : CARGA_PUNTOS_RESULTS.values()) {
                if (obj.codigo.intValue() == codigo.intValue()) {
                    return obj;
                }
            }
            return codigo > 0 ? CARGA_PUNTOS_RESULTS.OK : CARGA_PUNTOS_RESULTS.ERROR_GENERICO;
        }
    }

    @AllArgsConstructor
    public enum CARGA_PARTICIPANTES_RESULTS {
        OK(1, "EXITO"),
        ERROR_GENERICO(-999, "ERROR GENERICO"),
        ERROR_CATALOGO_NO_EXISTE(-1, "ID de catalogo no existe"),
        ERROR_INSERTAR_PARTICIPANTE(-2, "No se pudo grabar los datos del participante"),
        ERROR_ACTUALIZAR_PARTICIPANTE(-3, "No se pudo actualizar los datos del participante"),
        ERROR_EMAIL_EXISTE(-4, "El email ya se encuentra registrado"),
        ERROR_TIPO_DOCUMENTO_NO_EXISTE(-5, "Tipo de documento no existe"),
        ERROR_TIPO_PARTICIPANTE_NO_EXISTE(-6, "Tipo de participante no existe"),
        ERROR_RAZON_SOCIAL_NO_EXISTE(-7, "Razon social no existe"),
        ERROR_RAZON_SOCIAL_NO_CATALOGO(-8, "Razon social no corresponde a catalogo"),
        ERROR_NRO_DOCUMENTO(-9, "Nro de documento no existe")
        ;

        @Getter
        Integer codigo;
        @Getter
        String mensaje;

        public static CARGA_PARTICIPANTES_RESULTS getMensageFromCode(Integer codigo) {
            if (codigo == null) {
                return CARGA_PARTICIPANTES_RESULTS.ERROR_GENERICO;
            }

            for (CARGA_PARTICIPANTES_RESULTS obj : CARGA_PARTICIPANTES_RESULTS.values()) {
                if (obj.codigo.intValue() == codigo.intValue()) {
                    return obj;
                }
            }
            return codigo > 0 ? CARGA_PARTICIPANTES_RESULTS.OK : CARGA_PARTICIPANTES_RESULTS.ERROR_GENERICO;
        }
    }

    @AllArgsConstructor
    public enum TIPO_CARGA {

        CARGA_PUNTO_MANUAL("archivos/puntos-manuales/"),
        CARGA_PUNTO_MANUAL_ERROR("archivos/puntos-manuales/error/"),
        CARGA_PARTICIPANTE("archivos/participantes/"),
        CARGA_PARTICIPANTE_ERROR("archivos/participantes/error/"),
        CARGA_META("archivos/metas/"),
        CARGA_META_ERROR("archivos/metas/error/"),
        CARGA_VENTA("archivos/ventas/"),
        CARGA_VENTA_ERROR("archivos/ventas/error/"),
        CARGA_IMAGEN("public/web/img/productos/"),
        CARGA_IMAGEN_ERROR("public/web/img/productos/error/"),
        CARGA_PDF("public/web/pdf/"),
        CARGA_PDF_ERROR("public/web/pdf/error/"),
        CARGA_EVALUACION("archivos/evaluaciones/"),
        CARGA_EVALUACION_ERROR("archivos/evaluaciones/error/"),
        CARGA_DIRECCIONES("archivos/direcciones/"),
        CARGA_DIRECCIONES_ERROR("archivos/direcciones/error/"),
        CARGA_IMAGEN_BANNER("public/web/img/bg/"),
        CARGA_PARTICIPANTE_ESTADO("archivos/participantes/estado/"),
        CARGA_PARTICIPANTE_ESTADO_ERROR("archivos/participantes/estado/error/"),
        CARGA_FOTO("public/web/img/fotos/"),
        CARGA_CAPACITACION("public/web/img/elearning/"),
        CARGA_CAPACITACION_RECURSOS("public/web/img/elearning/recursos/"),
        CARGA_IMAGEN_APP("public/mobile/general/producto/"),;

        @Getter
        String folder;

        public static TIPO_CARGA getCargaError(TIPO_CARGA tipoCarga) {
            for (TIPO_CARGA tc : TIPO_CARGA.values()) {
                if (!tc.name().equals(tipoCarga.name()) && tc.name().startsWith(tipoCarga.name())) {
                    return tc;
                }
            }
            return null;
        }
    }

    @AllArgsConstructor
    public enum TIPO_OPERACION_VISITA {
        CONSULTA_PRODUCTO(1, "Consulta de producto"),
        VISITA_WEB(2, "Visita Web"),
        CATEGORIA_PRODUCTO(3, "Consulta categoria"),
        PRODUCTOS_CANJEADOS(4, "Productos Canjeados"),
        CANJE_EXTERNO(5, "Canjes Externos"),
        PUNTOS_DESCONTADOS(6, "Puntos descontados"),
        VISITA_WEB_DETALLADO(99, "Visita Web Detallado");

        @Getter
        private Integer codigo;
        @Getter
        private String texto;
    }

    @AllArgsConstructor
    public enum TIPO_DISPOSITVO_VISITA {
        WEB(1, "Web"),
        ANDROID(2, "Android"),
        IOS(3, "IOS");

        @Getter
        private Integer codigo;
        @Getter
        private String texto;
    }

    @AllArgsConstructor
    public enum TIPO_BANNER {
        BANNER_HOME(1),
        BANNER_FOOTER(2),
        BANNER_PRODUCTOS(3),
        BANNER_CATEGORIAS(4),
        BANNER_META(5),
        BANNER_MOBILE(6);

        @Getter
        private Integer value;
    }

    @AllArgsConstructor
    public enum TIPO_DIRECCION {

        CASA(1, "Casa"),
        OFICINA(2, "Oficina"),
        CASA_CAMPO(3, "Casa de Campo"),
        CASA_PLAYA(4, "Casa de Playa"),
        OTRO(5, "Otro");

        @Getter
        private Integer id;

        @Getter
        private String nombre;

        public static TIPO_DIRECCION fromCodigo(Integer codigo) {
            for (TIPO_DIRECCION origen : TIPO_DIRECCION.values()) {
                if (origen.getId().equals(codigo)) {
                    return origen;
                }
            }
            return null;
        }

    }
    @AllArgsConstructor
    public enum TIPO_ORIGEN {
        CARGA_MANUAL(1),
        CANJE_NORMAL(2),
        CANJE_MANUAL(3),
        ANULACION(4),
        VENCIMIENTO(5),
        REVERCION_CANJE(6),
        REVERCION_CARGA(7);

        @Getter
        private Integer codigo;

        public static TIPO_ORIGEN fromCodigo(Integer codigo) {
            for (TIPO_ORIGEN origen : TIPO_ORIGEN.values()) {
                if (origen.getCodigo().intValue() == codigo) {
                    return origen;
                }
            }
            return null;
        }
    }

    @AllArgsConstructor
    public enum TIPO_MIS_PUNTOS {
        ACUMULADOS(1),
        CANJEADOS(2),
        ESTADO_CUENTA(3),
        POSIBLES(4),
        VENCIDOS(5),
        DESCARGADOS(6),
        REPORTE_ADMIN(7);

        @Getter
        Integer value;
    }


    @AllArgsConstructor
    public enum ACTUALIZACION_DATOS_RESULTS {
        OK(1, "EXITO"),
        ERROR_GENERICO(-999, "ERROR GENERICO"),
        ERROR_ACTUALIZACION_PARTICIPANTE(-1, "No se pudo actualizar los datos del participante"),
        ERROR_PARTICIPANTE_NO_EXISTE(-2, "Participante no encontrado"),
        ERROR_UBIGEO_NO_EXISTE(-3, "Ubigeo no encontrado"),
        ERROR_REGISTRO_DIRECCION(-4, "No se pudo registrar direccion"),
        ERROR_ACTUALIZAR_DIRECCION(-5, "No se pudo actualizar direccion"),
        ERROR_EMAIL_DUPLICADO(-6, "Email ya existe en el sistema");

        @Getter
        Integer codigo;
        @Getter
        String mensaje;

        public static ACTUALIZACION_DATOS_RESULTS getMensageFromCode(Integer codigo) {
            if (codigo == null) {
                return ACTUALIZACION_DATOS_RESULTS.ERROR_GENERICO;
            }

            for (ACTUALIZACION_DATOS_RESULTS obj : ACTUALIZACION_DATOS_RESULTS.values()) {
                if (obj.codigo.intValue() == codigo.intValue()) {
                    return obj;
                }
            }
            return codigo > 0 ? ACTUALIZACION_DATOS_RESULTS.OK : ACTUALIZACION_DATOS_RESULTS.ERROR_GENERICO;
        }
    }

    @AllArgsConstructor
    public enum REGISTRO_DATOS_RESULTS {
        OK(1, "EXITO"),
        ERROR_GENERICO(-1, "ERROR GENERICO"),
        ERROR_NUMERO_DOCUMENTO_EXISTE(-2, "El numero de documento ya se encuentra en uso"),
        ERROR_EMAIL_EXISTE(-3, "El email ya se encuentra en uso"),
        ERROR_UBIGEO_NO_EXISTE(-4, "Ubigeo no encontrado");

        @Getter
        Integer codigo;
        @Getter
        String mensaje;

        public static REGISTRO_DATOS_RESULTS getMensageFromCode(Integer codigo) {
            if (codigo == null) {
                return REGISTRO_DATOS_RESULTS.ERROR_GENERICO;
            }

            for (REGISTRO_DATOS_RESULTS obj : REGISTRO_DATOS_RESULTS.values()) {
                if (obj.codigo.intValue() == codigo.intValue()) {
                    return obj;
                }
            }
            return codigo > 0 ? REGISTRO_DATOS_RESULTS.OK : REGISTRO_DATOS_RESULTS.ERROR_GENERICO;
        }
    }

    @AllArgsConstructor
    public enum TIPO_PRODUCTO {
        REGULAR(1),
        PRIMAX(2),
        RECARGA_MOVIL(3),
        RECARGA_TV(4),
        COLORES(5),
        CORREO(7);

        @Getter
        Integer value;

        public static TIPO_PRODUCTO getByCode(Integer code) {
            if (code == null) {
                return null;
            }

            for (TIPO_PRODUCTO tp : values()) {
                if (tp.getValue().intValue() == code) {
                    return tp;
                }
            }
            return null;
        }
    }

    @AllArgsConstructor
    public enum TIPO_META {
        UNACEM(0),
        ANUAL(1),
        SEMENSTRAL(2);

        @Getter
        Integer value;

        public static TIPO_META getByCode(Integer code) {
            if (code == null) {
                return null;
            }

            for (TIPO_META tp : values()) {
                if (tp.getValue().intValue() == code) {
                    return tp;
                }
            }
            return null;
        }
    }

    @AllArgsConstructor
    public enum ACTUALIZACION_PEDIDO_NETSUITE {
        OK(1, "EXITO"),
        ERROR_GENERICO(-999, "ERROR GENERICO"),
        ERROR_UBIGEO_NO_EXISTE(-1, "El ubigeo no existe"),
        ERROR_ZONA_NO_EXISTE(-2, "La zona seleccionada no existe"),
        ERROR_TIPO_VIVIENDA_NO_EXISTE(-3, "El tipo de vivienda no existe"),
        ERROR_DIRECCION_NO_ACTUALIZA(-4, "No se pudo actualizar direccion"),
        ERROR_PEDIDO_NO_ACTUALIZA(-5, "No se pudo actualizar el pedido");

        @Getter
        Integer codigo;
        @Getter
        String mensaje;

        public static ACTUALIZACION_PEDIDO_NETSUITE getMensageFromCode(Integer codigo) {
            if (codigo == null) {
                return ACTUALIZACION_PEDIDO_NETSUITE.ERROR_GENERICO;
            }

            for (ACTUALIZACION_PEDIDO_NETSUITE obj : ACTUALIZACION_PEDIDO_NETSUITE.values()) {
                if (obj.codigo.intValue() == codigo.intValue()) {
                    return obj;
                }
            }
            return codigo > 0 ? ACTUALIZACION_PEDIDO_NETSUITE.OK : ACTUALIZACION_PEDIDO_NETSUITE.ERROR_GENERICO;
        }
    }

    @AllArgsConstructor
    public enum CARGA_DIRECCIONES_RESULTS {
        OK(1, "EXITO"),
        ERROR_GENERICO(-999, "ERROR GENERICO"),
        ERROR_UBIGEO_NO_EXISTE(-1, "Codigo ubigeo no existe"),
        ERROR_PARTICIPANTE_NO_EXISTE(-2, "Participante no existe"),
        ERROR_ZONA_NO_EXISTE(-3, "ID Zona no existe"),
        ERROR_TIPO_VIVIENDA_NO_EXISTE(-4, "ID Tipo Vivienda no existe"),
        ERROR_REGISTRO(-5, "No se pudo crear la direccion"),
        ERROR_ACTUALIZAR(-6, "No se pudo actualizar la direccion");

        @Getter
        Integer codigo;
        @Getter
        String mensaje;

        public static CARGA_DIRECCIONES_RESULTS getMensageFromCode(Integer codigo) {
            if (codigo == null) {
                return CARGA_DIRECCIONES_RESULTS.ERROR_GENERICO;
            }

            for (CARGA_DIRECCIONES_RESULTS obj : CARGA_DIRECCIONES_RESULTS.values()) {
                if (obj.codigo.intValue() == codigo.intValue()) {
                    return obj;
                }
            }
            return codigo > 0 ? CARGA_DIRECCIONES_RESULTS.OK : CARGA_DIRECCIONES_RESULTS.ERROR_GENERICO;
        }
    }

    @AllArgsConstructor
    public enum TIPO_MENSAJE {
        ACTUALIZA_DATOS(1),
        CARGA_PUNTOS(2),
        CANJE_PRODUCTO(3);

        @Getter
        Integer tipo;
    }

    @AllArgsConstructor
    public enum MENSAJES {
        NOMBRE("@NOMBRE"),
        CELULAR("@CELULAR"),
        PUNTOS("@PUNTOS"),
        PUNTOS_CARGA("@PUNTOS_CARGA"),
        APELLIDOS("@APELLIDOS"),
        CORREO("@CORREO"),
        TELEFONO("@TELEFONO"),
        TIPO_DOCUMENTO("@TIPO_DOCUMENTO"),
        NUMERO_DOCUMENTO("@NUMERO_DOCUMENTO"),
        PEDIDO("@PEDIDO");

        @Getter
        private String mensajes;
    }

    @AllArgsConstructor
    public enum TIPO_ACTUALIZAR {
        CATALOGO(1, "Catalogo"),
        POPUP_WEB(2,"Popup"),
        BANNER_FLOTANTE(3,"Banner Flotante");

        @Getter
        private Integer codigo;
        @Getter
        private String value;
    }
}
