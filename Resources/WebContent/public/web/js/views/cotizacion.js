function Cotizacion(uri) {
    this.uri = uri;
    this.origen = $('#origen');
    this.destino = $('#destino');
    this.vueloidavuelta = $('#vueloidavuelta');
    this.fechaIda = $('#fechaIda');
    this.fechaVuelta = $('#fechaVuelta');
    this.adultos = $('#adultos');
    this.boys2a11 = $('#boys2a11');
    this.bebe0a2 = $('#bebe0a2');
    this.edadboy1 = $('#edadboy1');
    this.edadboy2 = $('#edadboy2');
    this.edadboy3 = $('#edadboy3');
    this.edadboy4 = $('#edadboy4');
    this.edadbb1 = $('#edadbb1');
    this.edadbb2 = $('#edadbb2');
    this.edadbb3 = $('#edadbb3');
    this.edadbb4 = $('#edadbb4');
    this.clase = $('#clase');
    this.ckMayores = $('#ckMayores');
    this.mas65 = $('#mas65');
    this.discapacitados = $('#discapacitados');
    this.numero65 = $('#numero65');
    this.divMayoresDiscapacidad = $('#divMayoresDiscapacidad');
    this.divNumero = $('#divNumero');

    this.tipoVuelo = $('#tipoVuelo');
    this.btnCotizar = $('#btnCotizar');
    this.btnPasajeros = $('#btnPasajeros');
    this.btnCotizarFinal = $('#btnCotizarFinal');
    this.formCotizacion = $('#formCotizacion');
    this.formPasajeros = $('#formPasajeros');
    this.formFinalizar = $('#formFinalizar');

}

Cotizacion.prototype.init = function () {
    this.handler();
    this.initValidate();
};

Cotizacion.prototype.handler = function () {
    var obj = this;

    $('input.initDisabled').prop('disabled', true);

    obj.adultos.select2({
        placeholder: "Seleccionar",
        minimumResultsForSearch: -1
    });

    // obj.clase.select2({
    //     placeholder: "Seleccionar",
    //     minimumResultsForSearch: -1
    // });

    obj.btnCotizar.on('click', function(e) {
        if (obj.formPasajeros.valid()) {
            $('#cotizacion').modal('show');
        } else {
            obj.formPasajeros.validate().focusInvalid();
        }
    });

    obj.btnPasajeros.on('click', function(e) {
        if (obj.formCotizacion.valid()) {
            obj.mostrarPasajeros();
        } else {
            obj.formCotizacion.validate().focusInvalid();
        }
    });

    obj.btnCotizarFinal.on('click', function(e) {
        if (obj.formFinalizar.valid()) {
            obj.enviarCotizacion();
            $('#enviar').hide();
            $('#ok').show();
        } else {
            obj.formFinalizar.validate().focusInvalid();
        }
    });

    obj.tipoVuelo.on('change', function () {
       if(parseInt(this.value) === 1) {
           obj.fechaVuelta.val('');
           obj.fechaVuelta.prop('disabled', true);
       } else {
           obj.fechaVuelta.prop('disabled', false);
       }
    });

    $(".input-fecha-nacimiento").attr('max',this.setearFechaHoy());

    obj.fechaIda.on('change', function() {
        obj.fechaVuelta.val('');
        obj.fechaVuelta.attr('min',obj.fechaIda.val());
    });
};

Cotizacion.prototype.cleanAll = function() {
    var obj = this;
    $('input').val('');
};

Cotizacion.prototype.setearFechaHoy = function() {
    var today = new Date();
    var dd = today.getDate();
    var MM = today.getMonth()+1;
    var yyyy = today.getFullYear();
    if (dd < 10) {
        dd = '0'+dd;
    }

    if (MM < 10) {
        MM = '0'+MM;
    }

    today = yyyy+'-'+MM+'-'+dd;

    return today;
};

Cotizacion.prototype.initValidate = function () {
    var obj = this;

    obj.formCotizacion.validate({
        rules: {
            origen: {required: true},
            destino: {required: true},
            fechaIda: {required: true},
            clase: {required: true},
            fechaVuelta: {
                required: function () {
                    return parseInt(obj.tipoVuelo.val())=== 2 ;
                }
            }
        },
        messages: {
            origen: {required: "Ingrese Origen"},
            clase: {required: "Ingrese Clase"},
            destino: {required: "Ingrese Destino"},
            fechaIda: {required: "Ingrese Fecha de Ida"},
            fechaVuelta: {required: "Ingrese Fecha de Vuelta"}

        }
    });

    obj.formPasajeros.validate({
//        rules: {
//            nombres: {required: true},
//            apellidos: {required: true},
//            nroDocumento: {required: true},
//            fechaNacimiento: {required: true}
//        },
//        messages: {
//            nombres: {required: "Ingrese Nombres"},
//            apellidos: {required: "Ingrese Apellidos"},
//            nroDocumento: {required: "Ingrese N° Documento"},
//            fechaNacimiento: {required: "Ingrese Fecha de Nacimiento"}
//        },
        highlight: function (element, error, validClass) {
            $(element).addClass(error).removeClass(validClass);
            $(element.form).find("input[id="+ element.id +"]").addClass(error);
        },
        unhighlight: function (element, error, validClass) {
            $(element).removeClass(error).addClass(validClass);
            $(element.form).find("input[id="+ element.id +"]").removeClass(error);
        },
        errorClass: 'error-login'
    });

    obj.formFinalizar.validate({
        rules: {
            correo : {required: true},
            telefono : {required: true}
        },
        messages: {
            correo: {required: "Ingrese Correo"},
            telefono: {required: "Ingrese Telefono"}
        }
    });
};

Cotizacion.prototype.enviarCotizacion = function() {
    var obj = this;

    var listPasajero = obj.obtenerPasajeros(parseInt(obj.adultos.val()));

    var request = {
        origen: obj.origen.val(),
        destino: obj.destino.val(),
        tipoBoleto: obj.vueloidavuelta.is(':checked') ? 1 : 0,
        fechaIda: obj.fechaIda.val(),
        fechaVuelta: obj.fechaVuelta.val(),
        clase: obj.clase.val(),
        // mayores: obj.ckMayores.is(':checked') ? 1 : 0,
        // terceraEdad: obj.mas65.is(':checked') ? 1 : 0,
        // discapacitado: obj.discapacitados.is(':checked') ? 1 : 0,
        listaPasajeros : listPasajero,
        correo :  $('#correo').val(),
        telefono : $('#telefono').val()
    };

    Promotick.ajax.post({
        url : obj.uri + 'cotizacion/registrarCotizacion',
        data : JSON.stringify(request),
        messageError : true,
        messageTitle : 'Envio de cotizacion',
        before : function() {
            $.LoadingOverlay('show');
        },
        success : function(response) {
            if (response.status) {
                Promotick.toast.success(response.message, 'Envio de cotizacion');
                obj.formCotizacion[0].reset();

                obj.clase.val(0).trigger('change');
                // obj.ckMayores.prop('checked', false);
                // obj.divMayoresDiscapacidad.hide();
                // obj.divNumero.hide();
                // obj.mas65.prop('checked', true);
            }
        },
        complete : function() {
            Promotick.scrollTop(null, 600);
            $.LoadingOverlay('hide', true);
        }
    });
};

Cotizacion.prototype.mostrarPasajeros = function() {
    var obj = this;
    console.log('pasajeros ' + obj.adultos.val());
    var npasajero = parseInt(obj.adultos.val());

    Promotick.render.get({
        url : obj.uri + 'cotizacion/viewPasajerosCotizacion/' + npasajero,
        context: $(document).find('#contentPasajerosCotizacion'),
        complete : function() {
            obj.btnCotizar.show();
        }
    });
};

Cotizacion.prototype.obtenerPasajeros = function(npasajeros) {
    var data = [];
    debugger;;
    $.each(new Array(npasajeros), function(n){
        var pasajero = {
            nombrePasajero : $('#nom-psj-' + (n + 1)).val(),
            apellidoPasajero : $('#ape-psj-' + (n + 1)).val(),
            tipoDocumento : {
                idTipoDocumento : $('#tdoc-psj-' + (n + 1)).val()
            },
            nroDocumento : $('#ndoc-psj-' + (n + 1)).val(),
            fechaNacimiento : $('#fec-psj-' + (n + 1)).val(),
            terceraEdad : $('#discapacidad-' + (n + 1)).is(':checked') ? 1 : 0,
            discapacitado : $('#mayor65-' + (n + 1)).is(':checked') ? 1 : 0
        };

        console.log(pasajero);
        data.push(pasajero);

    });

    return data;
};