function UsuariosDetalle(uri, registro, superusuario, idRol) {
    this.registro = parseInt(registro);
    this.uri = uri;
    this.idRol = parseInt(idRol);
    this.superusuario = superusuario;
    this.btnGuardar = $('#btnGuardar');
    this.idUsuario = $('#idUsuario');
    this.formRegistro = $('#form-registro');
    this.nombres = $('#nombres');
    this.apellidoPaterno = $('#apellidoPaterno');
    this.apellidoMaterno = $('#apellidoMaterno');
    this.nombreUsuario = $('#nombreUsuario');
    this.correoUsuario = $('#correoUsuario');
    this.claveUsuario = $('#claveUsuario');
    this.claveUsuarioRepite = $('#claveUsuarioRepite');
    this.rolUsuario = $('#rolUsuario');
}

UsuariosDetalle.prototype.init = function() {
    this.handle();
    this.validarFormRegistro();
};

UsuariosDetalle.prototype.handle = function() {
    var obj = this;

    obj.rolUsuario.select2();

    obj.btnGuardar.on('click', function(e) {
        e.preventDefault();

        if (obj.formRegistro.valid()) {
            obj.guardarUsuario(this);
        } else {
            obj.formRegistro.validate().focusInvalid();
        }
    });
};

UsuariosDetalle.prototype.validarFormRegistro = function() {
    var obj = this;
    this.formRegistro.validate({
        rules: {
            nombres: {
                required: true,
                minlength: 3
            },
            apellidoPaterno: {
                required: true
            },
            apellidoMaterno: {
                required: true
            },
            nombreUsuario: {
                required: true,
                minlength: 5
            },
            correoUsuario: {
                required: true,
                email: true,
                minlength: 5
            },
            rolUsuario: {
                required: {
                    depends : function() {
                        return obj.registro === 1 || obj.idRol !== 1;
                    }
                },
                min : 1
            },
            claveUsuario: {
                required: {
                    depends: function (element) {
                        return obj.registro === 1 || (obj.superusuario === 'true' && obj.claveUsuarioRepite.val().length > 0);
                    }
                },
                minlength: 5,
                maxlength: 15
            },
            claveUsuarioRepite: {
                required: {
                    depends: function (element) {
                        return obj.registro === 1 || (obj.superusuario === 'true' && obj.claveUsuario.val().length > 0);
                    }
                },
                equalTo : {
                    param: '#claveUsuario',
                    depends: function(element) {
                        return obj.registro === 1 || (obj.superusuario === 'true' && obj.claveUsuario.val().length > 0);
                    }
                }
            }
        },
        messages: {
            nombres: {
                required: 'Ingrese nombres del usuario',
                minlength: 'Ingrese al menos 3 caracteres'
            },
            apellidoPaterno: {
                required: 'Ingrese apellido paterno'
            },
            apellidoMaterno: {
                required: 'Ingrese apellido materno'
            },
            nombreUsuario: {
                required: 'Ingrese usuario',
                minlength: 'Ingrese al menos 5 catacteres'
            },
            correoUsuario: {
                required: 'Ingrese email de usuario',
                email: 'Formato incorrecto de email',
                minlength: 'Ingrese al menos 5 catacteres'
            },
            claveUsuario: {
                required: 'Ingrese la contraseña',
                minlength: 'Ingrese como minimo 5 catacteres',
                maxlength: 'Ingrese como maximo 15 caracteres'
            },
            claveUsuarioRepite: {
                required: 'Ingrese la contraseña nuevamente',
                equalTo : 'Las contraseñas no coinciden'
            },
            rolUsuario: {
                required: 'Seleccione un rol de usuario',
                min : 'Seleccione un rol de usuario'
            }
        }
    });
};

UsuariosDetalle.prototype.guardarUsuario = function(context) {
    var obj = this;

    var usuario = {
        idUsuario : obj.idUsuario.val(),
        usuario : obj.nombreUsuario.val(),
        nombresUsuario : obj.nombres.val(),
        apellidoPaterno : obj.apellidoPaterno.val(),
        apellidoMaterno : obj.apellidoMaterno.val(),
        correoUsuario : obj.correoUsuario.val(),
        rol :  {
            idRol : obj.rolUsuario.val()
        }
    };

    if (obj.registro === 1 || obj.idRol === 1) {
        usuario.clave = obj.claveUsuario.val()
    }

    var loader = null;

    Promotick.ajax.post({
        url : obj.uri + 'administracion/usuarios/guardar-usuario',
        data : JSON.stringify(usuario),
        messageError : true,
        messageTitle : 'Mantenimiento de usuarios',
        before : function() {
            loader = Ladda.create(context);
            loader.start();
        },
        success : function(response) {
            if (response.status) {
                Promotick.toast.success(response.message, 'Mantenimiento de usuarios');
                $('html, body').animate({scrollTop:0}, 'slow');
                if (obj.registro === 1) {
                    obj.formRegistro[0].reset();
                }
            }
        },
        complete : function() {
            loader.stop();
        }
    });


};