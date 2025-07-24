function Recuperar(uri) {
    this.uri = uri;
    this.btnAceptar = $('#btn-aceptar');
    this.btnCancelar = $('#btn-cancelar');
    this.correo = $('#correo');
    this.frmRecuperar = $('#form-recuperar');
    this.recuperacionOk = false;
    this.btnLoader = null;
}

Recuperar.prototype.init = function() {
    this.handler();
};

Recuperar.prototype.handler = function() {
    var obj = this;

    obj.correo.keypress(function (e) {
        if (e.which == 13) {
            obj.btnAceptar.click();
            return false;
        }
    });

    obj.btnAceptar.on('click',function(e){
        e.preventDefault();
        if (!obj.recuperacionOk) {
            if (obj.frmRecuperar.valid()) {
                obj.recuperarClave(this);
            }
        } else {
            window.location.href = obj.uri;
        }
    });

    obj.frmRecuperar.validate({
        rules: {
            correo: {
                required : true,
                email: true
            }
        },
        messages: {
            correo: {
                required : "Ingrese su correo electronico",
                email: "Formato de correo incorrecto"
            }
        }
    });
};

Recuperar.prototype.recuperarClave = function(handle) {

    var obj = this;

    var objRequest = {
        emailParticipante : obj.correo.val()
    };

    Promotick.ajax.post({
        url : obj.uri + 'recuperar/solicitudRestaurar',
        data : JSON.stringify(objRequest),
        messageError : true,
        messageTitle : 'Recuperacion de contraseña',
        before : function() {
            obj.btnLoader = Ladda.create(handle);
            obj.btnLoader.start();
        },
        success : function(response) {
            if(response.status) {
                obj.btnAceptar.text('IR AL LOGIN');
                Promotick.toast.success(response.message, 'Recuperacion de contraseña', {
                    timeOut : 4000,
                    onHidden : function() {
                        window.location.href = obj.uri + 'login';
                    }
                });
                obj.frmRecuperar[0].reset();
                obj.frmRecuperar.hide();
                obj.btnCancelar.hide();
                obj.recuperacionOk = true;
            }
        },
        complete : function() {
            obj.btnLoader.stop();
        }
    });

};