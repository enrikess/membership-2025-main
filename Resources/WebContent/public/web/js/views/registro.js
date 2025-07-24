function Registro(uri) {
    this.uri = uri;
    this.ecuadorPais = '02';
    this.ecuadorCoddep = '01';
    this.frmRegistro = $('#frmRegistro');
    this.inptNombres = $('#inptNombres');
    this.inptApPaterno = $('#inptApPaterno');
    this.inptApMaterno = $('#inptApMaterno');
    this.inptNroDocumento = $('#inptNroDocumento');
    this.inptCorreo = $('#inptCorreo');
    this.inptCelular = $('#inptCelular');
    this.slcProvincia = $('#slcProvincia');
    this.slcPetshop = $('#slcPetshop');
    this.inptCiudad = $('#inptCiudad');
    this.inptContrasena = $('#inptContrasena');
    this.chkTyc = $('#chkTyc');
    this.chkDatos = $('#chkDatos');
    this.btnRegistrar = $('#btnRegistrar');
    this.divForm = $('#divForm');
    this.divMensaje = $('#divMensaje');
    this.btnLoader = null;
    this.cambioOk = false;
}

Registro.prototype.init = function() {
    this.handler();
};

Registro.prototype.handler = function() {
    var obj = this;

    obj.slcProvincia.select2();
    obj.slcPetshop.select2({
        placeholder: "Seleccionar",
        minimumResultsForSearch: -1
    });

    obj.frmRegistro.validate({
        rules: {
            inptNombres : {
                required : true,
            },
            inptApPaterno : {
                required : true,
            },
            inptApMaterno : {
                required : true,
            },
            inptNroDocumento : {
                required : true,
            },
            inptCorreo : {
                required : true,
                email : true
            },
            inptCelular : {
                required : true,
                digits : true,
                maxlength: 10
            },
            slcProvincia: {
                required: true,
                min: 1
            },
            slcPetshop: {
                required: true,
                min: 1
            },
            inptCiudad : {
                required : true
            },
            chkTyc : {
                required : true
            },
            chkDatos : {
                required : true
            },
            inptContrasena: {
                required: true
            }
        },
        messages: {
            inptNombres : {
                required : 'Ingrese sus nombres',
            },
            inptApPaterno : {
                required : 'Ingrese su apellido paterno',
            },
            inptApMaterno : {
                required : 'Ingrese su apellido materno',
            },
            inptNroDocumento : {
                required : 'Ingrese su nro de documento',
            },
            inptCorreo : {
                required : 'Ingrese su correo',
                email : 'Formato de correo incorrecto'
            },
            inptCelular : {
                required : 'Ingrese su celular',
                digits : 'Solo se permiten digitos',
                maxlength: 'Se permite como maximo 10 digitos'
            },
            slcProvincia: {
                required: 'Seleccione una provincia',
                min: 'Seleccione una provincia'
            },
            slcPetshop: {
                required: 'Seleccione un petshop',
                min: 'Seleccione un petshop'
            },
            inptCiudad : {
                required : 'Ingrese una ciudad'
            },
            chkTyc : {
                required : 'Debe aceptar la politica de comunicacion'
            },
            chkDatos : {
                required : 'Debe aceptar la politica de uso de datos'
            },
            inptContrasena: {
                required: 'Ingrese una contraseña'
            }
        }
    });

    obj.btnRegistrar.on('click', function(e) {
        e.preventDefault();
        if (!obj.cambioOk) {
            if (obj.frmRegistro.valid()) {
                obj.enviarRegistro(this);
            } else {
                obj.frmRegistro.validate().focusInvalid();
            }
        } else {
            window.location.href = obj.uri;
        }
    });

};

Registro.prototype.enviarRegistro = function(handle) {
    var obj = this;

    var request = {
        nombresParticipante: obj.inptNombres.val(),
        appaternoParticipante: obj.inptApPaterno.val(),
        apmaternoParticipante: obj.inptApMaterno.val(),
        nroDocumento : obj.inptNroDocumento.val(),
        emailParticipante : obj.inptCorreo.val(),
        movilParticipante : obj.inptCelular.val(),
        direccion : {
            ubigeo : {
                codpais: obj.ecuadorPais,
                coddep: obj.ecuadorCoddep,
                codprov : obj.slcProvincia.val(),
                coddist : '00'
            }
        },
        ciudad: obj.inptCiudad.val(),
        aceptaTyc : obj.chkTyc.is(':checked'),
        aceptaUsoDatos : obj.chkDatos.is(':checked'),
        petshop: {
            idPetshop: obj.slcPetshop.val()
        },
        claveParticipante: obj.inptContrasena.val()
    };

    Promotick.ajax.post({
        url : obj.uri + 'registro/guardarDatos',
        data : JSON.stringify(request),
        messageError: true,
        messageTitle: 'Actualizacion de datos',
        before : function() {
            obj.btnLoader = Ladda.create(handle);
            obj.btnLoader.start();
            $.LoadingOverlay('show');
        },
        success : function(response) {
            if (response.status) {
                obj.cambioOk = true;
                obj.frmRegistro[0].reset();
                obj.divForm.hide();
                obj.divMensaje.show();
                Promotick.toast.success(response.message, 'Registro');
            }
        },
        complete : function() {
            obj.btnLoader.stop();
            $.LoadingOverlay('hide', true);
        }
    });
};
