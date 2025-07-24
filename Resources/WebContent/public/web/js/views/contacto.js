function Contacto(uri) {
    this.uri = uri;
    this.btnEnviarContacto = $('#btnEnviarContacto');
    this.frmContacto = $('#frmContacto');
    this.contactoCedula = $('#contactoCedula');
    this.contactoNombres = $('#contactoNombres');
    this.contactoApellidos = $('#contactoApellidos');
    this.contactoEmail = $('#contactoEmail');
    this.contactoTelefono = $('#contactoTelefono');
    this.contactoCelular = $('#contactoCelular');
    this.contactoMensaje = $('#contactoMensaje');
}

Contacto.prototype.init = function() {
    this.handle();
};

Contacto.prototype.handle = function() {
    var obj = this;

    obj.btnEnviarContacto.on('click', function(e) {
        if (obj.frmContacto.valid()) {
            obj.enviarContacto(this);
        }  else {
            obj.frmContacto.validate().focusInvalid()
        }
    });

    obj.frmContacto.validate({
        rules : {
            contactoCedula : {
                required : true
            },
            contactoNombres : {
                required : true
            },
            contactoApellidos : {
                required : true
            },
            contactoEmail : {
                required : true,
                email : true
            },
            contactoTelefono : {
                required : true
            },
            contactoCelular : {
                required : true
            },
            contactoMensaje : {
                required : true
            }
        },
        messages : {
            contactoCedula : {
                required : 'Ingrese su documento'
            },
            contactoNombres : {
                required : 'Ingrese su nombre'
            },
            contactoApellidos : {
                required : 'Ingrese sus apellidos'
            },
            contactoEmail : {
                required : 'Ingrese su email',
                email : 'Formato de correo incorrecto'
            },
            contactoTelefono : {
                required : 'Ingrese su telefono fijo'
            },
            contactoCelular : {
                required : 'Ingrese su telefono celular'
            },
            contactoMensaje : {
                required : 'Ingrese su consulta'
            }
        }
    });
};

Contacto.prototype.enviarContacto = function(context) {
    var obj = this;

    var request = {
        nroDocumento : obj.contactoCedula.val(),
        nombres : obj.contactoNombres.val(),
        apellidos : obj.contactoApellidos.val(),
        email : obj.contactoEmail.val(),
        telefono : obj.contactoTelefono.val(),
        celular : obj.contactoCelular.val(),
        mensaje : obj.contactoMensaje.val()
    };

    var loader = null;

    Promotick.ajax.post({
        url : obj.uri + 'contacto/guardarContacto',
        data : JSON.stringify(request),
        messageError: true,
        messageTitle: 'Contacto',
        before : function() {
            loader = Ladda.create(context);
            loader.start();
        },
        success : function(response) {
            if (response.status) {
                obj.frmContacto[0].reset();
                Promotick.toast.success(response.message, 'Contacto');
            }
        },
        complete : function () {
            loader.stop();
        }
    });
};