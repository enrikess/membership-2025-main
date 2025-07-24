function Perfil(uri,tipoParticipante,urlR) {
    this.uri = uri;
    this.urlRecurso = urlR;
    this.ecuador = '02';
    this.ecuadorCoddep = '01';
    this.btnGuardarPerfil = $('#btnGuardarPerfil');
    this.btnGuardarClave = $('#btnGuardarClave');
    this.infoNombres = $('#infoNombres');
    this.infoApPaterno = $('#infoApPaterno');
    this.infoApMaterno = $('#infoApMaterno');
    this.infoFechaNacimiento = $('#infoFechaNacimiento');
    this.infoEmail = $('#infoEmail');
    this.infoTelefono = $('#infoTelefono');
    this.infoCelular = $('#infoCelular');
    this.infoCedula = $('#infoCedula');
    this.hobby = $('#hobby');
    this.passActual = $('#passActual');
    this.passNueva = $('#passNueva');
    this.frmDatosPersonales = $('#frmDatosPersonales');
    this.frmCambioClave = $('#frmCambioClave');
    this.frmDatosDireccion = $('#frmDatosDireccion');

    this.keyMaps = $('#keyMaps').val();
    this.urlValidator = $('#urlValidator').val();
    this.urlImage = $('#urlImage').val();
    this.mapsDefault = $("#mapsDefault").val();
    this.btnDireccion = $(".btn-direccion");
    this.btnDeleteDireccion = $(".btn-delete-direccion");
    this.linkTabDatos = $("#linkTabDatos");
    this.frmDatosDireccion = null;
    this.dirId = null;
    this.dirTipo = null;
    this.dirZona = null;
    this.dirVivienda = null;
    this.dirProvincia = null;
    this.dirCiudad = null;
    this.dirDireccion = null;
    this.dirReferencia = null;
    this.btnGuardarDireccion = null;
    this.formDireccion = false;

}

Perfil.prototype.init = function() {
    this.handle();
};

Perfil.prototype.handle = function() {
    var obj = this;

    obj.printGoogleMapsImages();

    obj.btnGuardarPerfil.on('click', function(e) {
        e.preventDefault();

        if (obj.frmDatosPersonales.valid()) {
            obj.guardarPerfil(this);
        } else {
            obj.frmDatosPersonales.validate().focusInvalid();
        }
    });

    obj.linkTabDatos.on('click', function(e) {
        e.preventDefault();
        if(obj.formDireccion){
            window.location.href = obj.uri + 'perfil?tab=1';
        }
    });

    obj.btnDireccion.on('click', function(e) {
       var id = $(this).attr('direccion-id');
       obj.showFormDireccion(id);
    });

    obj.btnDeleteDireccion.on('click', function(e) {
        var id = $(this).attr('direccion-id');
        obj.showAlertDelete(id);
    });

    obj.btnGuardarClave.on('click', function(e) {
        e.preventDefault();

        if (obj.frmCambioClave.valid()) {
            obj.guardarClave(this);
        } else {
            obj.frmCambioClave.validate().focusInvalid();
        }
    });

    obj.frmDatosPersonales.validate({
        rules : {
            infoNombres : {
                required : true
            },
            infoApPaterno : {
                required : true
            },
            infoApMaterno : {
                required : true
            },
            infoFechaNacimiento : {
                required : true
            },
            infoEmail : {
                required : true,
                email : true
            },
            infoTelefono : {
                required : true
            },
            infoCelular : {
                required : true
            },
            dirZona : {
                required : true,
                min : 1
            },
            dirVivienda : {
                required : true,
                min : 1
            },
            dirProvincia : {
                required : true,
                min : 1
            },
            dirCiudad : {
                required : true,
                min : 1
            },
            dirDireccion : {
                required : true
            },
            dirReferencia : {
                required : true,
                maxlength : 150
            },
            infoCedula : {
                required : true
            },
            hobby : {
                required : true
            }
        },
        messages : {
            infoNombres : {
                required : 'Ingrese su nombre'
            },
            infoApPaterno : {
                required : 'Ingrese su apellido paterno'
            },
            infoApMaterno : {
                required : 'Ingrese su apellido materno'
            },
            infoFechaNacimiento : {
                required : 'Ingrese su fecha de nacimiento'
            },
            infoEmail : {
                required : 'Ingrese su Email',
                email : 'Formato de email es incorrecto'
            },
            infoTelefono : {
                required : 'Ingrese su telefono fijo'
            },
            infoCelular : {
                required : 'Ingrese su telefono movil'
            },
            dirZona : {
                required : 'Seleccione un tipo de zona',
                min : 'Seleccione un tipo de zona'
            },
            dirVivienda : {
                required : 'Seleccione un tipo de vivienda',
                min : 'Seleccione un tipo de vivienda'
            },
            dirProvincia : {
                required : 'Seleccione una provincia',
                min : 'Seleccione una provincia'
            },
            dirCiudad : {
                required : 'Seleccione una ciudad',
                min : 'Seleccione una ciudad'
            },
            dirDireccion : {
                required : 'Ingrese una direccion'
            },
            dirReferencia : {
                required : 'Seleccione una referencia',
                maxlength : 'Cantidad maxima de caracteres es de 150'
            },
            infoCedula : {
                required : 'Ingrese su cedula'
            },
            hobby : {
                required : 'Ingrese un hobby'
            }
        }
    });

    obj.frmCambioClave.validate({
        rules : {
            passActual : {
                required : true
            },
            passNueva : {
                required : true
            },
            passNuevaRepite : {
                required : true,
                equalTo  : '#passNueva'
            }
        },
        messages : {
            passActual : {
                required : 'Ingrese su contraseña actual'
            },
            passNueva : {
                required : 'Ingrese su nueva contraseña'
            },
            passNuevaRepite : {
                required : 'Ingrese nuevamente su clave nueva',
                equalTo  : 'Las contraseñas no coinciden'
            }
        }
    });
};

Perfil.prototype.guardarPerfil = function(context) {
    var obj = this;

    var request = {
        emailParticipante : obj.cleanField(obj.infoEmail),
        telefonoParticipante : obj.cleanField(obj.infoTelefono),
        movilParticipante : obj.cleanField(obj.infoCelular),
    };

    var loader = null;

    Promotick.ajax.post({
        url : obj.uri + 'perfil/guardar',
        data : JSON.stringify(request),
        messageError: true,
        messageTitle: 'Actualizacion de perfil',
        before : function() {
            loader = Ladda.create(context);
            loader.start();
        },
        success : function(response) {
            if (response.status) {
                Promotick.toast.success(response.message, 'Actualizacion de perfil');
            }
        },
        complete : function () {
            loader.stop();
        }
    });
};

Perfil.prototype.guardarClave = function(context) {
    var obj = this;

    var request = {
        claveParticipante : obj.cleanField(obj.passNueva),
        claveAnterior : obj.cleanField(obj.passActual)
    };

    var loader = null;

    Promotick.ajax.post({
        url : obj.uri + 'perfil/actualizarClavePerfil',
        data : JSON.stringify(request),
        messageError: true,
        messageTitle: 'Actualizacion clave perfil',
        before : function() {
            loader = Ladda.create(context);
            loader.start();
        },
        success : function(response) {
            if (response.status) {
                obj.frmCambioClave[0].reset();
                Promotick.toast.success(response.message, 'Actualizacion clave perfil');
            }
        },
        complete : function () {
            loader.stop();
        }
    });
};


Perfil.prototype.cleanField = function($input) {
    if ($input == null) {
        return null;
    }
    var value = $input.val();
    if (value === '') {
        return null;
    }
    return value;
};

Perfil.prototype.printGoogleMapsImages = function(){

    var obj = this;

    $(document).find('.img-from-maps').each(function (i, e) {
        var direccion = $(e).attr("data-direccion");
        var distrito = $(e).attr("data-distrito");

        var mapsValue = (direccion != undefined && direccion != null && direccion.trim() != "");
        obj.printGoogleMapsImagesDetail(direccion + " " + distrito,$(e),mapsValue)
    });

};

Perfil.prototype.printGoogleMapsImagesDetail = function(text, image,mapsValue){
    var obj = this;
    //var urlImage = obj.replaceCharacters(obj.urlImage, [obj.mapsDefault, obj.keyMaps]);
    var urlImage = obj.urlRecurso + "img/logos/mapnotfound.jpg";
    //debugger;
    if(mapsValue) {
        var url = obj.replaceCharacters(obj.urlValidator,[text,obj.keyMaps]);
        $.ajax({
            url: url,
            type: 'get',
            dataType: 'json',
            beforeSend: function() {
            },
            error: function(a, b) {
                image.attr("src", urlImage);
            },
            success: function(data) {
                if (data.results.length > 0) {
                    var mapa = data.results[0].geometry.location.lat + ',' + data.results[0].geometry.location.lng;
                    urlImage = obj.replaceCharacters(obj.urlImage, [text, obj.keyMaps, mapa]);
                    image.attr("src", urlImage);
                }
            }
        });
    }else{
        image.attr("src", urlImage);
    }
};

Perfil.prototype.replaceCharacters = function(str, arr) {
    return str.replace(/%(\d+)/g, function(_,m) {
        return arr[--m];
    });
};

Perfil.prototype.showFormDireccion = function(id){
    var obj = this;
    var context = $("#dvDirections");
    Promotick.render.get({
        url : obj.uri + 'perfil/viewFormularioDireccion/' + id,
        context : context,
        complete : function() {
            obj.renderFormDirecion();
        }
    });
};

Perfil.prototype.showAlertDelete = function(id){
    var obj = this;
    swal({
            title: $.camelCase("-eliminar"),
            text: 'Estas seguro de eliminar esta direccion ?',
            type: "warning",
            showCancelButton: true,
            confirmButtonColor: "#DD6B55",
            confirmButtonText: 'Si, eliminar !',
            cancelButtonText: "No, cancelar!",
            closeOnConfirm: false,
            closeOnCancel: true
        },
        function (isConfirm) {
            if (isConfirm){
                obj.eliminarDireccion(id);
            }
        }
    );
}

Perfil.prototype.renderFormDirecion = function(){
    var obj = this;
    obj.dirId = $('#hfIdDireccion');
    obj.dirTipo = $("#dirTipo");
    obj.dirZona = $('#dirZona');
    obj.dirVivienda = $('#dirVivienda');
    obj.dirProvincia = $('#dirProvincia');
    obj.dirCiudad = $('#dirCiudad');
    obj.dirDireccion = $('#dirDireccion');
    obj.dirReferencia = $('#dirReferencia');
    obj.frmDatosDireccion = $('#frmDatosDireccion');
    obj.btnGuardarDireccion = $('#btnGuardarDireccion');

    obj.dirZona.select2({
        placeholder: "Seleccionar",
        minimumResultsForSearch: -1,
        width: '100%'
    });
    obj.dirVivienda.select2({
        placeholder: "Seleccionar",
        minimumResultsForSearch: -1,
        width: '100%'
    });
    obj.dirProvincia.select2();
    obj.dirCiudad.select2();

    obj.formDireccion = true;

    obj.dirProvincia.on('change', function() {
        var provincia = $(this).val();
        obj.obtenerCiudad(provincia);
    });

    obj.frmDatosDireccion.validate({
        ignore: "",
        rules : {
            dirTipo : {
                required : true,
            },
            dirZona : {
                required : true,
                min : 1
            },
            dirVivienda : {
                required : true,
                min : 1
            },
            dirProvincia : {
                required : true,
                min : 1
            },
            dirCiudad : {
                required : true,
                min : 1
            },
            dirDireccion : {
                required : true
            },
            dirReferencia : {
                required : true,
                maxlength : 150
            }
        },
        messages : {
            dirTipo : {
                required : 'Ingrese un tipo',
            },
            dirZona : {
                required : 'Seleccione un tipo de zona',
                min : 'Seleccione un tipo de zona'
            },
            dirVivienda : {
                required : 'Seleccione un tipo de vivienda',
                min : 'Seleccione un tipo de vivienda'
            },
            dirProvincia : {
                required : 'Seleccione una provincia',
                min : 'Seleccione una provincia'
            },
            dirCiudad : {
                required : 'Seleccione una ciudad',
                min : 'Seleccione una ciudad'
            },
            dirDireccion : {
                required : 'Ingrese una direccion'
            },
            dirReferencia : {
                required : 'Seleccione una referencia',
                maxlength : 'Cantidad maxima de caracteres es de 150'
            }
        }
    });

    obj.btnGuardarDireccion.on('click', function(e) {
        e.preventDefault();

        if (obj.frmDatosDireccion.valid()) {
            obj.guardarDireccion(this);
        } else {
            obj.frmDatosDireccion.validate().focusInvalid();
        }
    });

    obj.printGoogleMapsImages();
};

Perfil.prototype.guardarDireccion = function(context) {
    var obj = this;

    var request = {
        idParticipanteDireccion: obj.cleanField(obj.dirId),
        tagDireccion: obj.cleanField(obj.dirTipo),
        direccion: {
            ubigeo : {
                codpais : obj.ecuador,
                coddep : obj.ecuadorCoddep,
                codprov : obj.cleanField(obj.dirProvincia),
                coddist : obj.cleanField(obj.dirCiudad)
            },
            zona : {
                idZona : obj.cleanField(obj.dirZona)
            },
            tipoVivienda : {
                idTipoVivienda : obj.cleanField(obj.dirVivienda)
            },
            direccionCalle : obj.cleanField(obj.dirDireccion),
            referencia : obj.cleanField(obj.dirReferencia),
        }
    }

    var loader = null;


    Promotick.ajax.post({
        url : obj.uri + 'pedido/registrarDireccion',
        messageError : true,
        messageTitle: 'Actualizacion de direccion',
        data : JSON.stringify(request),
        before : function() {
            loader = Ladda.create(context);
            loader.start();
        },
        success : function(response) {
            if (response.status) {
                Promotick.toast.success(response.message, 'Actualizacion de direccion',
                    {
                        timeOut : 5000,
                        onHidden:
                            function () {
                                window.location.href = obj.uri + 'perfil?tab=2';
                            }
                    });
            }
        },
        complete : function() {
            loader.stop();
        }
    });

};

Perfil.prototype.eliminarDireccion = function(id) {
    var obj = this;

    Promotick.ajax.post({
        url : obj.uri + 'pedido/eliminarDireccion/' + id,
        messageError: true,
        messageTitle: 'Actualizacion de direccion',
        before : function() {
            $.LoadingOverlay('show');
            swal.close();
        },
        success : function(response) {
            if (response.status) {
                Promotick.toast.success(response.message, 'Actualizacion de direccion',
                    {
                        timeOut : 2000,
                        onHidden:
                            function () {
                                $.LoadingOverlay('hide', true);
                                window.location.href = obj.uri + 'perfil?tab=2';
                            }
                    });
            } else {
                $.LoadingOverlay('hide', true);
            }
        },
    });
};

Perfil.prototype.obtenerCiudad = function(coddist) {
    var obj = this;

    if(coddist == ""){
        debugger;;
        obj.dirCiudad
            .val(null)
            .trigger('change')
            .html('');

        obj.dirCiudad
            .append(new Option('Seleccionar', '', false, false))
            .trigger('change');

        obj.dirCiudad
            .trigger("chosen:updated");
        return;
    }

    Promotick.ajax.get({
        url : obj.uri + 'obtenerDistritos/' + obj.ecuadorCoddep + '/' + coddist,
        messageError : true,
        messageTitle : 'Checkout',
        before : function() {
            obj.dirCiudad
                .next()
                .LoadingOverlay("show");

            obj.dirCiudad
                .val(null)
                .trigger('change')
                .html('');

            obj.dirCiudad
                .append(new Option('Seleccionar', '', false, false))
                .trigger('change');

            obj.dirCiudad
                .trigger("chosen:updated");
        },
        success : function(response) {
            if (response.status) {

                $.each(response.data, function(i, d) {
                    option = new Option(d.nombreUbigeo, d.coddist, false, false);
                    obj.dirCiudad.append(option).trigger('change');
                });
            }
        },
        complete : function() {
            obj.dirCiudad
                .next()
                .LoadingOverlay("hide", true);
            obj.dirCiudad
                .trigger("chosen:updated");
        }
    });
};
