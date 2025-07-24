function Perfil(uri) {
    this.uri = uri;
    this.frmClavePerfil = $('#frmClavePerfil');
    this.btnGuardarClave = $('#btnGuardarClave');
    this.claveActual = $('#claveActual');
    this.clave = $('#clave');
}

Perfil.prototype.init = function() {
    this.handle();
    this.initValidate();
};

Perfil.prototype.handle = function() {
    var obj = this;

    obj.btnGuardarClave.on('click', function(e) {
        e.preventDefault();

        if (obj.frmClavePerfil.valid()) {
            obj.cambiarClave();
        } else {
            obj.frmClavePerfil.validate().focusInvalid();
        }
    })
};

Perfil.prototype.initValidate = function() {
    var obj = this;

    obj.frmClavePerfil.validate({
        rules : {
            claveActual : {
                required : true
            }
            ,clave : {
                required : true,
                minlength : 6

            }
            ,confirmaClave : {
                required : true,
                equalTo : '#clave'
            }
        },
        messages : {
            claveActual : {
                required : 'Ingrese su clave actual'
            }
            ,clave : {
                required : 'Ingrese su nueva clave',
                minlength : 'Ingrese al menos 6 caracteres'

            }
            ,confirmaClave : {
                required : 'Confirme su nueva clave',
                equalTo : 'Las contraseñas no coinciden'
            }
        }
    });
};

Perfil.prototype.cambiarClave = function() {
    var obj = this;
    var usuario = {
        clave : obj.claveActual.val(),
        nuevaClave : obj.clave.val()
    };

    Promotick.ajax.post({
        url : obj.uri + 'perfil/cambiar-clave',
        data : JSON.stringify(usuario),
        messageError : true,
        messageTitle : 'Actualizacion de clave',
        before : function() {
            obj.frmClavePerfil.LoadingOverlay('show');
        },
        success : function(response) {
            if (response.status) {
                Promotick.toast.success(response.message, 'Actualizacion de clave');
                obj.frmClavePerfil[0].reset();
            }
        },
        complete : function () {
            obj.frmClavePerfil.LoadingOverlay('hide', true);
        }
    });

};