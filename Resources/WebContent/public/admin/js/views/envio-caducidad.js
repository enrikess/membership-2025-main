function EnvioCaducidad(uri, registro) {
    this.registro = registro;
    this.uri = uri;
    this.btnEnviar = $('#btnEnviar');
    this.puntosMinimo = $('#puntosMinimo');
    // this.horaVencimiento = $('#horaVencimiento');
    this.formCaducidad = $('#form-caducidad');

}

EnvioCaducidad.prototype.init = function() {
    this.handle();
    this.validarFormCaducidad();
};

EnvioCaducidad.prototype.handle = function() {
    var obj = this;

    obj.btnEnviar.on('click', function(e) {
        e.preventDefault();

        if (obj.formCaducidad.valid()) {
            obj.guardarDatos(this);
        } else {
            obj.formCaducidad.validate().focusInvalid();
        }
    });
};

EnvioCaducidad.prototype.validarFormCaducidad = function() {
    var obj = this;
    this.formCaducidad.validate({
        rules: {
            puntosMinimo: {
                required: true,
                digits : true,
                min: 1
            },
        },
        messages: {
            puntosMinimo: {
                required: 'Puntos requerido',
                digits: 'Debe ser un valor entero',
                min : 'Valor entero mayor que 0',
            },
        }
    });
};

EnvioCaducidad.prototype.guardarDatos = function(context) {
    var obj = this;

    var configuracion = {
        puntosMinimo : obj.puntosMinimo.val(),
    };

    var loader = null;

    Promotick.ajax.post({
        url : obj.uri + 'administracion/envio-caducidad/guardar-caducidad',
        data : JSON.stringify(configuracion),
        messageError : true,
        messageTitle : 'Mantenimiento de caducidad',
        before : function() {
            loader = Ladda.create(context);
            loader.start();
        },
        success : function(response) {
            if (response.status) {
                Promotick.toast.success(response.message, 'Mantenimiento de caducidad');
                $('html, body').animate({scrollTop:0}, 'slow');
                if (obj.registro === '1') {
                    obj.formCaducidad[0].reset();
                }
            }
        },
        complete : function() {
            loader.stop();
        }
    });


};