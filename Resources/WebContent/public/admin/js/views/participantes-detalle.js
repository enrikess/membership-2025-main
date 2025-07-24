function ParticipantesDetalle(uri, registro) {
    this.registro = registro;
    this.uri = uri;
    this.btnGuardar = $('#btnGuardar');
    this.idParticipante = $('#idParticipante');
    this.formRegistro = $('#form-registro');
    this.slcCatalogos = $('#slcCatalogos');
    this.nroDocumento = $('#nroDocumento');
    this.nombresParticipante = $('#nombresParticipante');
    this.appaternoParticipante = $('#appaternoParticipante');
    this.apmaternoParticipante = $('#apmaternoParticipante');
    this.emailParticipante = $('#emailParticipante');
    this.telefonoParticipante = $('#telefonoParticipante');
    this.movilParticipante = $('#movilParticipante');
    this.fechaNacimiento = $('#fechaNacimiento');
    this.tipoDocumento = $('#tipoDocumento');
    this.cedula = $('#cedula');
    /*this.razonSocial = $('#razonSocial');*/
    this.codCliente = $('#codCliente');
    this.clasificacion = $('#clasificacion');
    this.chkEstadoParticipante = $('#chkEstadoParticipante');
    this.madrepadre = $('#madrepadre');
    this.slcConcesionario = $('#slcConcesionario');
    this.slcNroHijos = $('#slcNroHijos');
    this.slcEstadoCivil = $('#slcEstadoCivil');
    this.cliente = $('#cliente');
    this.slcTipoParticipante = $('#slcTipoParticipante');
    this.slcCategoriaPart = $('#slcCategoriaPart');
    this.slcRegion = $('#slcRegion');
    this.vendedor = $('#vendedor');
    this.divSubtipo = $('#divSubtipo');
    this.divVendedor = $('#divVendedor');
    this.metaAnual = $('#metaAnual');
    this.slcBroker = $('#slcBroker');
    this.ciudad = $('#ciudad');
    this.regional = $('#regional');
}

ParticipantesDetalle.prototype.init = function() {
    this.handle();
    this.validarFormRegistro();
};

ParticipantesDetalle.prototype.handle = function() {
    var obj = this;

    $.validator.addMethod(
        "validDate",
        function(value, element) {
            return value.match(/(?:0[1-9]|[12][0-9]|3[01])-(?:0[1-9]|1[0-2])-(?:19|20\d{2})/);
        },
        "Ingrese una fecha valida - formato: DD-MM-YYYY");

    $.validator.addMethod("metaFormato",function(value, element) {
            var n = Math.floor(Number(value));
            return n !== Infinity && String(n) === value && n >= -1;
        }, "Formato de meta incorrecto");

    $.validator.addMethod("metaDivisible",function(value, element) {
        var n = Math.floor(Number(value));
        if(n !== Infinity && String(n) === value && n >= -1) {
            if (parseInt(obj.slcTipoParticipante.val()) === 1 && n !== -1) { // interno
                return n % 12 === 0;
            }
            return true;
        }
        return true;
    }, "La meta anual no es divisible entre 12");

    obj.fechaNacimiento.datepicker( {
        format: 'dd-mm-yyyy',
        endDate: "0m"
    }).on('changeDate', function (ev) {

    });

    obj.slcCatalogos.select2({
        placeholder: "Seleccionar",
        minimumResultsForSearch: -1
    });

    obj.tipoDocumento.select2({
        placeholder: "Seleccionar",
        minimumResultsForSearch: -1
    });

    obj.slcConcesionario.select2({
        placeholder: "Seleccionar",
        minimumResultsForSearch: -1
    });

    obj.slcNroHijos.select2({
        placeholder: "Seleccionar",
        minimumResultsForSearch: -1
    });

    obj.slcEstadoCivil.select2({
        placeholder: "Seleccionar",
        minimumResultsForSearch: -1
    });

    obj.slcTipoParticipante.select2({
        placeholder: "Seleccionar",
        minimumResultsForSearch: -1
    });

    obj.slcCategoriaPart.select2({
        placeholder: "Seleccionar",
        minimumResultsForSearch: -1
    });

    obj.btnGuardar.on('click', function(e) {
        e.preventDefault();

        if (obj.formRegistro.valid()) {
            obj.guardarParticipante(this);
        } else {
            obj.formRegistro.validate().focusInvalid();
        }
    });

    // obj.slcTipoParticipante.on('change', function() {
    //     var value = parseInt($(this).val());
    //     if (value === 1) { // Interno
    //         obj.divSubtipo.show();
    //         obj.divVendedor.hide();
    //     } else { // externo
    //         obj.divSubtipo.hide();
    //         obj.divVendedor.show();
    //     }
    // });
};

ParticipantesDetalle.prototype.validarFormRegistro = function() {
    var obj = this;
    this.formRegistro.validate({
        rules: {
            tipoDocumento: {
                required: {
                    depends: function(element){
                        return obj.registro === '1';
                    }
                },
                min: 1
            },
            nroDocumento: {
                required: {
                    depends: function(element){
                        return obj.registro === '1';
                    }
                },
                minlength: 8
            },
            nombresParticipante: {
                required: true,
                minlength: 3
            },
            appaternoParticipante: {
                required: true,
                minlength: 3
            },
            apmaternoParticipante: {
                required: true,
                minlength: 3
            },
            emailParticipante: {
                required: true,
                email: true
            },
            telefonoParticipante: {
                required: true,
                minlength: 5
            },
            movilParticipante: {
                required: true,
                minlength: 5
            },
            slcCatalogos: {
                required: true,
                min: 1
            },
            fechaNacimiento: {
                required: true,
                validDate : true
            },
            cedula: {
                required: true
            },
            clasificacion: {
                required: true
            },
            codCliente: {
                required: true
            },
            madrepadre: {
                required: true
            },
            estadoCivil : {
                required : true,
                min: 1
            },
            slcConcesionario : {
                required : true,
                min: 1
            },
            slcBroker : {
                required : true,
                min: 1
            },
            regional : {
                required : true,
                min: 1
            },
            vendedor : {
                required : true
                // required : function() {
                //     return parseInt(obj.slcTipoParticipante.val() === 2)
                // }
            },
            metaAnual : {
                required : true,
                metaFormato: true,
                metaDivisible: true
            }
        },
        messages: {
            tipoDocumento: {
                required: 'Seleccione un tipo de documento',
                minlength: 'Seleccione un tipo de documento'
            },
            nroDocumento: {
                required: 'Ingrese un numero de documento',
                minlength: 'Ingrese al menos 8 caracteres'
            },
            nombresParticipante: {
                required: 'Ingrese un nombre',
                minlength: 'Ingrese al menos 3 caracteres'
            },
            appaternoParticipante: {
                required: 'Ingrese el apellido paterno',
                minlength: 'Ingrese al menos 3 caracteres'
            },
            apmaternoParticipante: {
                required: 'Ingrese el apellido materno',
                minlength: 'Ingrese al menos 3 caracteres'
            },
            emailParticipante: {
                required: 'Ingrese el email',
                email: 'Formato de correo invalido'
            },
            telefonoParticipante: {
                required: 'Ingrese un telefono fijo',
                minlength: 'Ingrese al menos 5 caracteres'
            },
            movilParticipante: {
                required: 'Ingrese un telefono movil',
                minlength: 'Ingrese al menos 5 caracteres'
            },
            slcCatalogos: {
                required: 'Seleccione un catalogo',
                min: 'Seleccione un catalogo'
            },
            fechaNacimiento: {
                required: 'Ingrese la fecha de nacimiento',
                validDate : 'Formato de fecha incorrecto: DD/MM/YYYY'
            },
            cedula: {
                required: 'Ingrese la cedula'
            },
            clasificacion: {
                required: 'Ingrese la clasificacion'
            },
            codCliente: {
                required: 'Ingrese el codigo de cliente'
            },
            madrepadre: {
                required: 'Ingrese si es madre o padre'
            },
            estadoCivil : {
                required : 'Seleccione su estado civil',
                min: 'Seleccione su estado civil'
            },
            slcConcesionario : {
                required : 'Seleccione un concesionario',
                min: 'Seleccione un concesionario'
            },
            slcBroker : {
                required : 'Seleccione un broker',
                min: 'Seleccione un broker'
            },
            regional : {
                required : 'Seleccione un regional',
                min: 'Seleccione un regional'
            },
            vendedor : {
                required : 'Ingrese el nombre de un vendedor'
            },
            metaAnual : {
                required : 'Ingrese una meta anual',
                metaFormato: 'El formato de meta es enteros positivos y -1 para deshabilitar',
                metaDivisible: 'La meta no es divisible entre 12 meses'
            }
        }
    });
};

ParticipantesDetalle.prototype.guardarParticipante = function(context) {
    var obj = this;

    var estadoCivil = parseInt(obj.slcEstadoCivil.val());
    switch (estadoCivil) {
        case 1:
            estadoCivil = 'SOLTERO';
            break;
        case 2:
            estadoCivil = 'CASADO';
            break;
        case 3:
            estadoCivil = 'DIVORCIADO';
            break;
    }

    var participante = {
        idParticipante : obj.idParticipante.val(),
        nombresParticipante : obj.nombresParticipante.val(),
        appaternoParticipante : obj.appaternoParticipante.val(),
        apmaternoParticipante : obj.apmaternoParticipante.val(),
        emailParticipante : obj.emailParticipante.val(),
        telefonoParticipante : obj.telefonoParticipante.val(),
        movilParticipante : obj.movilParticipante.val(),
        catalogo : {
            idCatalogo : obj.slcCatalogos.val()
        },
        fechaNacimientoString : obj.fechaNacimiento.data('datepicker').getFormattedDate('yyyy-mm-dd'),
        cedula : obj.cedula.val(),
        clasificacion : obj.clasificacion.val(),
        codCliente : obj.codCliente.val(),
        genero : $("input[name='rbtGenero']:checked").val(),
        estadoParticipante : obj.chkEstadoParticipante.is(':checked') ? 1 : -1,
        madrePadre: obj.ciudad.val(),
        cliente: obj.regional.val(),
        estadoCivil : estadoCivil,
        nroHijos : obj.slcNroHijos.val(),
        concesionario : {
            idConcesionario : obj.regional.val()
        },
        idTipoParticipante : obj.slcBroker.val(),
        idCategoriaParticipante : obj.slcRegion.val(),
        // subtipoParticipante : {
        //     idSubtipoParticipante : obj.slcSubtipoParticipante.val()
        // },
        nombreVendedor : obj.vendedor.val(),
        metaAnual : obj.metaAnual.val()
    };

    if (obj.registro === '1') {
        participante.nroDocumento = obj.nroDocumento.val();
        participante.tipoDocumento = {
            idTipoDocumento : obj.tipoDocumento.val()
        }
    }

    var loader = null;

    Promotick.ajax.post({
        url : obj.uri + 'participantes/guardar-participante',
        data : JSON.stringify(participante),
        messageError : true,
        messageTitle : 'Mantenimiento de participante',
        before : function() {
            loader = Ladda.create(context);
            loader.start();
        },
        success : function(response) {
            if (response.status) {
                Promotick.toast.success(response.message, 'Mantenimiento de participante');
                $('html, body').animate({scrollTop:0}, 'slow');
                if (obj.registro === '1') {
                    obj.formRegistro[0].reset();
                    obj.slcCatalogos.select2("val", "");
                    obj.tipoDocumento.select2("val", "");
                }
            }
        },
        complete : function() {
            loader.stop();
        }
    });


};