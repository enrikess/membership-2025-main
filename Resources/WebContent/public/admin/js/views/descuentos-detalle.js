function DescuentosDetalle(uri, registro) {
    this.registro = registro;
    this.uri = uri;
    this.btnGuardar = $('#btnGuardar');
    this.formCampania = $('#form-campania');
    this.idDescuento = $('#idDescuento');
    this.nombreDescuento = $('#nombreDescuento');
    this.codigoDescuento = $('#codigoDescuento');
    this.puntos = $('#puntos');
    this.fechaInicio = $('#inptFechaInicio');
    this.fechaFin = $('#inptFechaFin');
    this.slcCatalogo = $('#slcCatalogo');
    this.slcTipoDescuento = $('#slcTipoDescuento');
}

DescuentosDetalle.prototype.init = function() {
    this.handle();
    this.validarFormRegistro();
};

DescuentosDetalle.prototype.handle = function() {
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

DescuentosDetalle.prototype.validarFormRegistro = function() {
    var obj = this;
    this.formCampania.validate({
        rules: {
            nombreDescuento: {
                required: true,
                minlength: 3
            },
            codigoDescuento: {
                required: true
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
            nombreDescuento: {
                required: 'Ingrese nombre',
                minlength: 'Ingrese por lo menos 3 caracteres'
            },
            codigoDescuento: {
                required: 'Ingrese el codigo'
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

DescuentosDetalle.prototype.guardarMarca = function(context) {
    var obj = this;

    var descuento = {
        idDescuento : obj.idDescuento.val(),
        nombreDescuento : obj.nombreDescuento.val(),
        catalogo:{
            idCatalogo: obj.slcCatalogo.val(),
        },
        valorDescuento : obj.puntos.val(),
        codigoDescuento : obj.codigoDescuento.val(),
        tipoDescuento : {
            idTipoDescuento : obj.slcTipoDescuento.val(),
        },
        fechaInicio : obj.fechaInicio.data('datepicker').getFormattedDate('yyyy-mm-dd'),
        fechaFin : obj.fechaFin.data('datepicker').getFormattedDate('yyyy-mm-dd')
    };

    var loader = null;

    Promotick.ajax.post({
        url : obj.uri + 'administracion/descuento/guardar-descuento',
        data : JSON.stringify(descuento),
        messageError : true,
        messageTitle : 'Mantenimiento de descuentos',
        before : function() {
            loader = Ladda.create(context);
            loader.start();
        },
        success : function(response) {
            if (response.status) {
                Promotick.toast.success(response.message, 'Mantenimiento de descuentos');
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