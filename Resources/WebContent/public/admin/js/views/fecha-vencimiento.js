function FechaVencimiento(uri, registro) {
    this.registro = registro;
    this.uri = uri;
    this.btnGuardar = $('#btnGuardar');
    this.fechaVencimiento = $('#fechaVencimiento');
    this.horaVencimiento = $('#horaVencimiento');
    this.formRegistro = $('#form-registro');

}

FechaVencimiento.prototype.init = function() {
    this.handle();
    this.validarFormRegistro();
};

FechaVencimiento.prototype.handle = function() {
    var obj = this;

    $.validator.addMethod(
        "validDate",
        function(value, element) {
            return value.match(/(?:0[1-9]|[12][0-9]|3[01])-(?:0[1-9]|1[0-2])-(?:19|20\d{2})/);
        },
        "Ingrese una fecha valida - formato: DD-MM-YYYY");

    obj.fechaVencimiento.datepicker( {
        format: 'dd-mm-yyyy'
        //endDate: "0m"
    }).on('changeDate', function (ev) {

    });

    obj.btnGuardar.on('click', function(e) {
        e.preventDefault();

        if (obj.formRegistro.valid()) {
            obj.guardarDatos(this);
        } else {
            obj.formRegistro.validate().focusInvalid();
        }
    });
};

FechaVencimiento.prototype.validarFormRegistro = function() {
    var obj = this;
    this.formRegistro.validate({
        rules: {
            horaVencimiento: {
                required: true,
                digits : true,
                min: 0,
                max : 24
            },
            fechaVencimiento: {
                required: 'Ingrese la fecha de vencimiento',
                validDate : 'Formato de fecha incorrecto: dd-mm-yyyy'
            },
        },
        messages: {
            horaVencimiento: {
                required: 'Hora requerida',
                digits: 'Debe ser un valor entero',
                max : 'Valor entero entre 0 y 24',
                min : 'Valor entero entre 0 y 24',
            },
            fechaVencimiento: {
                required: 'Ingrese la fecha de nacimiento',
                validDate : 'Formato de fecha incorrecto: dd-mm-yyyy'
            }
        }
    });
};

FechaVencimiento.prototype.guardarDatos = function(context) {
    var obj = this;

    var configuracion = {
        fechaVencimientoString : obj.fechaVencimiento.data('datepicker').getFormattedDate('yyyy-mm-dd'),
        horaVencimiento : obj.horaVencimiento.val(),
    };

    var loader = null;

    Promotick.ajax.post({
        url : obj.uri + 'administracion/fecha-vencimiento/guardar-vencimiento',
        data : JSON.stringify(configuracion),
        messageError : true,
        messageTitle : 'Mantenimiento de vencimiento',
        before : function() {
            loader = Ladda.create(context);
            loader.start();
        },
        success : function(response) {
            if (response.status) {
                Promotick.toast.success(response.message, 'Mantenimiento de vencimiento');
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