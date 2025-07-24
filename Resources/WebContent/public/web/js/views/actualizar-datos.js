function ActualizarDatos(uri) {
    this.uri = uri;
    this.ecuadorPais = '02';
    this.ecuadorCoddep = '01';
    this.formActualizarDatos = $('#form-actualiza-datos');
    this.fechaNacimiento = $('#fechaNacimiento');
    this.telefonoFijo = $('#telefonoFijo');
    this.celular = $('#celular');
    this.correo = $('#correo');
    this.provincia = $('#provincia');
    this.genero = $('#genero');
    this.estadoCivil = $('#estadoCivil');
    this.slcRegion = $('#slcRegion');
    this.nroHijos = $('#nroHijos');
    this.ciudad = $('#ciudad');
    this.chkTyc = $('#chkTyc');
    this.chkDatos = $('#chkDatos');
    this.chkComunicacion = $('#chkComunicacion');
    this.chkSiHijos = $('#chkSi');
    this.btnActualizar = $('#btnActualizar');
    this.btnLoader = null;
    this.cambioOk = false;
    this.fechaAniversario = $('#fechaAniversario');
}

ActualizarDatos.prototype.init = function() {
    this.handler();
};

ActualizarDatos.prototype.handler = function() {
    var obj = this;

    obj.fechaNacimiento.on('change', function() {
        obj.fechaCambiada = true;
    });

    obj.fechaAniversario.on('change', function() {
        obj.fechaCambiada = true;
    });

    obj.provincia.select2();
    obj.genero.select2();
    obj.nroHijos.select2();
    obj.estadoCivil.select2();
    obj.slcRegion.select2();

    obj.formActualizarDatos.validate({
        rules: {
            telefonoFijo : {
                required : true,
                digits : true
            },
            celular : {
                required : true,
                digits : true,
                maxlength: 10
            },
            correo : {
                required : true,
                email : true
            },
            fechaNacimiento : {
                required : true,
            },
            genero : {
                required : true,
            },
            estadoCivil : {
                required : true,
            },
            nroHijos: {
                required: true,
            },
            provincia: {
                required: true,
                min: 1
            },
            ciudad : {
                required : true
            },
            slcRegion: {
                required: true,
                min: 1,
            },
            chkTyc : {
                required : true
            },
            chkDatos : {
                required : true
            },
            chkComunicacion : {
                required : true
            },
            fechaAniversario : {
                required : true,
            }
        },
        messages: {
            telefonoFijo : {
                required : 'Ingrese su telefono fijo',
                digits : 'Solo se permiten digitos'
            },
            celular : {
                required : 'Ingrese su telefono celular',
                digits : 'Solo se permiten digitos',
                maxlength: 'Solo se permiten maximo 10 digitos'
            },
            correo : {
                required : 'Ingrese su correo electronico',
                email : 'Formato de correo incorrecto'
            },
            fechaNacimiento : {
                required : 'Ingrese su fecha de nacimiento',
            },
            genero : {
                required : 'Seleccione su genero',
            },
            estadoCivil : {
                required : 'Seleccione su estado civil',
            },
            nroHijos: {
                required: 'Seleccione la cantidad de hijos',
            },
            provincia: {
                required: 'Seleccione una provincia',
                min: 'Seleccione una provincia',
            },
            ciudad : {
                required : 'Ingrese su ciudad'
            },
            slcRegion: {
                required: 'Seleccione una region',
                min: 'Seleccione una region',
            },
            chkTyc : {
                required : 'Debe aceptar los terminos y condiciones'
            },
            chkDatos : {
                required : 'Debe aceptar la politica de uso de datos'
            },
            chkComunicacion : {
                required : 'Debe aceptar la politica de comunicacion'
            },
            fechaAniversario : {
                required : 'Ingrese su fecha de aniversario del local',
            },
        }
    });

    obj.btnActualizar.on('click', function(e) {
        e.preventDefault();
        if (!obj.cambioOk) {
            if (obj.formActualizarDatos.valid()) {
                obj.enviarActualizacion(this);
            } else {
                obj.formActualizarDatos.validate().focusInvalid();
            }
        } else {
            window.location.href = obj.uri;
        }
    });

};

ActualizarDatos.prototype.enviarActualizacion = function(handle) {
    var obj = this;

    var estadoCivil = parseInt(obj.estadoCivil.val());
    switch (estadoCivil) {
        case 1:
            estadoCivil = 'SOLTERO/A';
            break;
        case 2:
            estadoCivil = 'CASADO/A';
            break;
        case 3:
            estadoCivil = 'DIVORCIADO/A';
            break;
        case 4:
            estadoCivil = 'VIUDO/A';
            break;
        case 5:
            estadoCivil = 'OTRO';
            break;
    }

    var nroHijos = parseInt(obj.nroHijos.val());
    if (nroHijos === 99) {
        nroHijos = 0;
    }

    var request = {
        fechaNacimientoString : obj.fechaNacimiento.val(),
        fechaAniversarioLocalString : obj.fechaAniversario.val(),
        telefonoParticipante : obj.telefonoFijo.val(),
        movilParticipante : obj.celular.val(),
        emailParticipante : obj.correo.val(),
        ciudad : obj.ciudad.val(),
        region : {
            idRegion :  obj.slcRegion.val(),
        },
        direccion : {
            ubigeo : {
                codpais: obj.ecuadorPais,
                coddep: obj.ecuadorCoddep,
                codprov : obj.provincia.val(),
                coddist : '00'
            }
        },

        genero : parseInt(obj.genero.val()) === 1 ? 'M' : 'F',
        aceptaTyc : obj.chkTyc.is(':checked'),
        aceptaUsoDatos : obj.chkDatos.is(':checked'),
        aceptaComunicacion : obj.chkComunicacion.is(':checked'),
        // nroHijos : nroHijos,
        nroHijos : obj.chkSiHijos.is(':checked') ? 1: 0,
        estadoCivil : estadoCivil,
    };

    Promotick.ajax.post({
        url : obj.uri + 'actualizar/guardarDatos',
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
                Promotick.toast.success(response.message, 'Actualizacion de datos', {
                    timeOut : 2000,
                    onHidden : function() {
                        window.location.href = obj.uri;
                    }
                })
            }
        },
        complete : function() {
            obj.btnLoader.stop();
            $.LoadingOverlay('hide', true);
        }
    });
};
