function Recomendado(uri) {
    this.uri = uri;
    this.formularioRecomendado = $('#frmNuevoRecomendado');
    this.nroDocumentoRecomendado = $('#nroDocRecomendado');
    this.nombresRecomendado = $('#nombresRecomendado');
    this.apellidosRecomendado = $('#apellidosRecomendado');
    this.celularRecomendado = $('#celularRecomendado');
    this.emailRecomendado = $('#correoRecomendado');
    this.slccompra = $('#slcTiempoCompra');
    this.slcfinan = $('#slcFinanciamiento');
    this.provincia = $('#provinciaRecomendado');
    this.ciudad = $('#ciudadRecomendado');
    this.observacionRecomendado = $('#observacionRecomendado');
    this.btnRegistrarRecomendado = $('#btnRegistrarRecomendado');
    this.ecuadorCoddep = '01';

}

Recomendado.prototype.init = function () {
    this.handler();
};

Recomendado.prototype.handler = function () {
    var obj = this;

    obj.provincia.on('change', function() {
        var provincia = $(this).val();
        obj.obtenerCiudad(provincia);
    });

    obj.formularioRecomendado.validate({
        rules: {
            nroDocRecomendado: {
                required: true,
                digits: true,
                minlength: 9
            },
            nombresRecomendado: {
                required: true
            },
            apellidosRecomendado: {
                required: true
            },
            celularRecomendado: {
                required: true
            },
            correoRecomendado: {
                required: true,
                email: true
            },
            provinciaRecomendado: {
                required: true,
                min : 1
            },
            ciudadRecomendado: {
                required: true,
                min : 1
            },
            // observacionRecomendado: {
            //     required: true,
            // }

        },
        messages: {
            nroDocRecomendado: {
                required: 'Ingrese el nro de documento',
                digits: 'ingrese solo dígitos',
                minlength: 'Ingrese mínimo 9 dígitos'
            },
            nombresRecomendado: {
                required: 'Ingrese el nombre',
            },
            apellidosRecomendado: {
                required: 'Ingrese el apellido',
            },
            celularRecomendado: {
                required: 'Ingrese el celular',
            },
            correoRecomendado: {
                required: 'Ingrese un email',
                email: 'Formato de email incorrecto'
            },
            provinciaRecomendado: {
                required: 'Seleccione su provincia',
                min : 'Seleccione una provincia'
            },
            ciudadRecomendado: {
                required: 'Seleccione su cuidad',
                min : 'Seleccione una ciudad'
            },
            // observacionRecomendado: {
            //     required: 'Ingrese una observacion',
            // }
        }
    });

    obj.btnRegistrarRecomendado.on('click', function(e) {
        e.preventDefault();
        if (obj.formularioRecomendado.valid()) {
            obj.registrarRecomendado(this);
        } else {
            obj.formularioRecomendado.validate().focusInvalid();
        }
    });

};

Recomendado.prototype.registrarRecomendado = function(context) {
    var obj = this;

    var request = {
        nroDocumentoRecomendado: obj.nroDocumentoRecomendado.val(),
        nombresRecomendado: obj.nombresRecomendado.val(),
        appaternoRecomendado: obj.apellidosRecomendado.val(),
        celularRecomendado: obj.celularRecomendado.val(),
        emailRecomendado: obj.emailRecomendado.val(),
        provincia: $("#provinciaRecomendado option:selected").text(),
        ciudad: $("#ciudadRecomendado option:selected").text(),
        tiempoCompra: obj.slccompra.val(),
        financiamiento: obj.slcfinan.val(),
        observacionRecomendado: obj.observacionRecomendado.val()
    };

    var loader = null;

    Promotick.ajax.post({
        url : obj.uri + 'recomendado/registrarRecomendado',
        data : JSON.stringify(request),
        messageError: true,
        messageTitle: 'Registro de Recomendado',
        before : function() {
            loader = Ladda.create(context);
            loader.start();
        },
        success : function(response) {
            if (response.status) {
                obj.formularioRecomendado[0].reset();
                Promotick.toast.success(response.message, 'Registro correcto',
                    {
                        timeOut : 5000,
                        onHidden:
                            function () {
                                window.location.href = obj.uri + 'recomendado';
                            }
                    });
            }
        },
        complete : function () {
            loader.stop();
        }
    });
};

Recomendado.prototype.obtenerCiudad = function(codprov){
    var obj = this;

    Promotick.ajax.get({
        url : obj.uri + 'obtenerDistritos/' + obj.ecuadorCoddep + '/' + codprov,
        messageError : true,
        messageTitle : 'Checkout',
        before : function() {
            obj.ciudad
                .next()
                .LoadingOverlay("show");

            obj.ciudad
                .val(null)
                .trigger('change')
                .html('');

            obj.ciudad
                .append(new Option('Seleccionar', '0', false, false))
                .trigger('change');

            obj.ciudad
                .trigger("chosen:updated");
        },
        success : function(response) {
            if (response.status) {

                $.each(response.data, function(i, d) {
                    option = new Option(d.nombreUbigeo, d.coddist, false, false);
                    obj.ciudad.append(option).trigger('change');
                });
            }
        },
        complete : function() {
            obj.ciudad
                .next()
                .LoadingOverlay("hide", true);
            obj.ciudad
                .trigger("chosen:updated");
        }
    });
};