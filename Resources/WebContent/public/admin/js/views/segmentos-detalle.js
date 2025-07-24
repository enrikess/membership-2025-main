function SegmentosDetalle(uri, registro) {
    this.registro = registro;
    this.uri = uri;
    this.btnGuardar = $('#btnGuardar');
    this.idCatalogo = $('#idCatalogo');
    this.formRegistro = $('#form-registro');
    this.nombreSegmento = $('#nombreSegmento');
    this.codigoSegmento = $('#codigoSegmento');
    this.diasEntrega = $('#diasEntrega');
    this.idCatalogoNetsuite = $('#idCatalogoNetsuite');
}

SegmentosDetalle.prototype.init = function() {
    this.handle();
    this.validarFormRegistro();
};

SegmentosDetalle.prototype.handle = function() {
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

SegmentosDetalle.prototype.validarFormRegistro = function() {
    var obj = this;
    this.formRegistro.validate({
        rules: {
            nombreSegmento: {
                required: true,
                minlength: 3
            },
            diasEntrega: {
                required: true,
                digits: true
            },
            idCatalogoNetsuite: {
                required: true,
                digits: true
            }
        },
        messages: {
            nombreSegmento: {
                required: 'Ingrese nombre del segmento',
                minlength: 'Ingrese por lo menos 3 caracteres'
            },
            diasEntrega: {
                required: 'Ingrese los dias de entrega para los pedido',
                digits: 'Ingrese un numero entero positivo'
            },
            idCatalogoNetsuite: {
                required: 'Ingrese el ID de catalogo netsuite',
                digits: 'Ingrese un numero entero positivo'
            }
        }
    });
};

SegmentosDetalle.prototype.guardarMarca = function(context) {
    var obj = this;

    var catalogo = {
        idCatalogo : obj.idCatalogo.val(),
        nombreCatalogo : obj.nombreSegmento.val(),
        codigoCatalogo : obj.codigoSegmento.val(),
        diasEnvio : obj.diasEntrega.val(),
        idCatalogoNetsuite : obj.idCatalogoNetsuite.val()
    };

    var loader = null;

    Promotick.ajax.post({
        url : obj.uri + 'administracion/segmentos/guardar-segmento',
        data : JSON.stringify(catalogo),
        messageError : true,
        messageTitle : 'Mantenimiento de segmentos',
        before : function() {
            loader = Ladda.create(context);
            loader.start();
        },
        success : function(response) {
            if (response.status) {
                Promotick.toast.success(response.message, 'Mantenimiento de segmentos');
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