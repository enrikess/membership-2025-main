function CampaniasDetalle(uri, registro) {
    this.registro = registro;
    this.uri = uri;
    this.btnGuardar = $('#btnGuardar');
    this.formCampania = $('#form-campania');
    this.idCampania = $('#idCampania');
    this.nombreCampania = $('#nombreCampania');
    this.nganadores = $('#nganadores');
    this.puntos = $('#puntos');
    this.fechaInicio = $('#inptFechaInicio');
    this.fechaFin = $('#inptFechaFin');
}

CampaniasDetalle.prototype.init = function() {
    this.handle();
    this.validarFormRegistro();
};

CampaniasDetalle.prototype.handle = function() {
    var obj = this;

    $.validator.addMethod(
        "validDate",
        function(value, element) {
            return value.match(/(?:0[1-9]|[12][0-9]|3[01])-(?:0[1-9]|1[0-2])-(?:19|20\d{2})/);
        },
        "Ingrese una fecha valida - formato: DD-MM-YYYY");

    obj.fechaInicio.datepicker( {
        format: 'dd-mm-yyyy',
        endDate: "0m"
    }).on('changeDate', function (ev) {

    });

    obj.fechaFin.datepicker( {
        format: 'dd-mm-yyyy',
        endDate: "0m"
    }).on('changeDate', function (ev) {

    });

    obj.btnGuardar.on('click', function(e) {
        e.preventDefault();

        if (obj.formCampania.valid()) {
            obj.guardarMarca(this);
        } else {
            obj.formCampania.validate().focusInvalid();
        }
    });
};

CampaniasDetalle.prototype.validarFormRegistro = function() {
    var obj = this;
    this.formCampania.validate({
        rules: {
            nombreCampania: {
                required: true,
                minlength: 3
            },
            nganadores: {
                required: true,
                digits: true
            },
            puntos: {
                required: true,
                digits: true
            },
            inptFechaInicio: {
                required: true,
                validDate : true
            },
            inptFechaFin: {
                required: true,
                validDate : true
            }
        },
        messages: {
            nombreCampania: {
                required: 'Ingrese nombre',
                minlength: 'Ingrese por lo menos 3 caracteres'
            },
            nganadores: {
                required: 'Ingrese los ganadores',
                digits: 'Ingrese un numero entero positivo'
            },
            puntos: {
                required: 'Ingrese los puntos',
                digits: 'Ingrese un numero entero positivo'
            },
            inptFechaInicio: {
                required: 'Ingrese la fecha de inicio',
                validDate : 'Formato de fecha incorrecto: dd-mm-yyyy'
            },
            inptFechaFin: {
                required: 'Ingrese la fecha de fin',
                validDate : 'Formato de fecha incorrecto: dd-mm-yyyy'
            }
        }
    });
};

CampaniasDetalle.prototype.guardarMarca = function(context) {
    var obj = this;

    var campania = {
        idCampania : obj.idCampania.val(),
        nombreCampania : obj.nombreCampania.val(),
        numGanadores : obj.nganadores.val(),
        valorPuntos : obj.puntos.val(),
        fechaInicioString : obj.fechaInicio.data('datepicker').getFormattedDate('yyyy-mm-dd'),
        fechaFinString : obj.fechaFin.data('datepicker').getFormattedDate('yyyy-mm-dd')
    };

    var loader = null;

    Promotick.ajax.post({
        url : obj.uri + 'administracion/campania/guardar-campania',
        data : JSON.stringify(campania),
        messageError : true,
        messageTitle : 'Mantenimiento de campañas',
        before : function() {
            loader = Ladda.create(context);
            loader.start();
        },
        success : function(response) {
            if (response.status) {
                Promotick.toast.success(response.message, 'Mantenimiento de campañas');
                $('html, body').animate({scrollTop:0}, 'slow');
                if (obj.registro === '1') {
                    obj.formCampania[0].reset();
                }
            }
        },
        complete : function() {
            loader.stop();
        }
    });


};