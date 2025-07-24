function CambiarClave(uri, token) {
    this.uri = uri;
    this.token = token;
    this.btnAceptar = $('#btn-aceptar');
    this.password = $('#password');
    this.repitePassword = $('#repitePassword');
    this.frmCambiarClave = $('#form-cambiar-clave');
    this.cambioOk = false;
    this.btnLoader = null;
}

CambiarClave.prototype.init = function() {
    this.handler();
};

CambiarClave.prototype.handler = function() {
    var obj = this;

    obj.repitePassword.keypress(function (e) {
        if (e.which == 13) {
            obj.btnAceptar.click();
            return false;
        }
    });

    obj.btnAceptar.on('click',function(e){
        e.preventDefault();
        if (!obj.cambioOk) {
            if (obj.frmCambiarClave.valid()) {
                obj.cambiarClave(this);
            }
        } else {
            window.location.href = obj.uri;
        }
    });

    obj.frmCambiarClave.validate({
        rules: {
            password: {
                required : true,
                minlength: 6
            },
            repitePassword: {
                required : true,
                equalTo  : "#password"
            }
        },
        messages: {
            password: {
                required : 'Ingrese nueva contraseña',
                minlength: 'Ingrese contraseña no menor a 6 digitos'
            },
            repitePassword: {
                required : 'Repita nueva contraseña',
                equalTo  : "Las contraseñas son diferentes"
            }
        }
    });

    obj.password.pwstrength({
        ui : {
            container: "#form-cambiar-clave",
            viewports: {
                progress: ".pwstrength_viewport_progress",
                verdict: ".pwstrength_viewport_verdict"
            }
        },
        common : {
            debug: false
        }
    });
};

CambiarClave.prototype.cambiarClave = function(handle) {

    var obj = this;

    var objRequest = {
        clave : obj.password.val(),
        token : obj.token
    };

    Promotick.ajax.post({
        url : obj.uri + 'recuperar/restaurarClave',
        data : JSON.stringify(objRequest),
        messageError : true,
        messageTitle : 'Nueva contraseña',
        before : function() {
            obj.btnLoader = Ladda.create(handle);
            obj.btnLoader.start();
        },
        success : function(response) {
            if(response.status) {
                Promotick.toast.success(response.message, 'Nueva contraseña', {
                    timeOut : 4000,
                    onHidden : function() {
                        window.location.href = obj.uri + 'login';
                    }
                });
                obj.frmCambiarClave[0].reset();
                obj.frmCambiarClave.hide();
                obj.cambioOk = true;
                obj.btnAceptar.html('IR AL LOGIN');
            }
        },
        complete : function() {
            obj.btnLoader.stop();
        }
    });

};