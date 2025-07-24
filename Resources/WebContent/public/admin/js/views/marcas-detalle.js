function MarcasDetalle(uri, registro) {
    this.registro = registro;
    this.uri = uri;
    this.btnGuardar = $('#btnGuardar');
    this.idMarca = $('#idMarca');
    this.formRegistro = $('#form-registro');
    this.nombresMarca = $('#nombresMarca');
    this.descripcionCorta = $('#descripcionCorta');
    this.descripcionLarga = $('#descripcionLarga');
    this.chkEstadoMarca = $('#chkEstadoMarca');
}

MarcasDetalle.prototype.init = function() {
    this.handle();
    this.validarFormRegistro();
};

MarcasDetalle.prototype.handle = function() {
    var obj = this;

    obj.btnGuardar.on('click', function(e) {
        e.preventDefault();

        if (obj.formRegistro.valid()) {
            obj.guardarMarca(this);
        } else {
            obj.formRegistro.validate().focusInvalid();
        }
    });
};

MarcasDetalle.prototype.validarFormRegistro = function() {
    var obj = this;
    this.formRegistro.validate({
        rules: {
            nombresMarca: {
                required: true,
                minlength: 3
            },
            descripcionCorta: {
                required: true,
                minlength: 3
            },
            descripcionLarga: {
                required: true,
                minlength: 3
            }
        },
        messages: {
            nombresMarca: {
                required: 'Ingrese nombre de la marca',
                minlength: 'Ingrese por lo menos 3 caracteres'
            },
            descripcionCorta: {
                required: 'Ingrese una descripcion corta',
                minlength: 'Ingrese por lo menos 3 caracteres'
            },
            descripcionLarga: {
                required: 'Ingrese una descripcion larga',
                minlength: 'Ingrese por lo menos 3 caracteres'
            }
        }
    });
};

MarcasDetalle.prototype.guardarMarca = function(context) {
    var obj = this;

    var marca = {
        idMarca : obj.idMarca.val(),
        nombreMarca : obj.nombresMarca.val(),
        descripcionCorta : obj.descripcionCorta.val(),
        descripcionLarga : obj.descripcionLarga.val(),
        estadoMarca : obj.chkEstadoMarca.is(':checked') ? 1 : -1
    };

    var loader = null;

    Promotick.ajax.post({
        url : obj.uri + 'catalogos/marcas/guardar-marca',
        data : JSON.stringify(marca),
        messageError : true,
        messageTitle : 'Mantenimiento de marca',
        before : function() {
            loader = Ladda.create(context);
            loader.start();
        },
        success : function(response) {
            if (response.status) {
                Promotick.toast.success(response.message, 'Mantenimiento de marca');
                $('html, body').animate({scrollTop:0}, 'slow');
                if (obj.registro === '1') {
                    obj.formRegistro[0].reset();
                }
            }
        },
        complete : function() {
            loader.stop();
        }
    });


};