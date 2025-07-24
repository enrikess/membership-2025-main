function BannerRegistrar(uri) {
    this.uri = uri;
    this.idCatalogo = $('#idCatalogo');
    this.imagenBanner = $('#imagenBanner');
    this.btnGuardar = $('#btnGuardar');
    this.tabImagenes = $('#tabImagenes');

    this.chkBoton = $('#chkBoton');
    this.inptTextoBoton = $('#inptTextoBoton');
    this.inptTextoUno = $('#inptTextoUno');
    this.inptTextoDos = $('#inptTextoDos');
    this.inptUrlBoton = $('#inptUrlBoton');
    this.inptOrden = $('#inptOrden');
    this.slcCatalogo = $('#slcCatalogo');
    this.idConfiguracionBanner = $('#idConfiguracionBanner');
    this.tipoBanner = $('#slctipoBanner');
}

BannerRegistrar.prototype.init = function() {
    this.handle();
    // this.validateImagenes();
};

BannerRegistrar.prototype.handle = function() {
    var obj = this;
    $.fn.modal.Constructor.prototype._enforceFocus = function() {};

    var imagenDefecto = obj.imagenBanner.attr('data-uri');

    obj.makeFileInput(obj.imagenBanner, '#principalError');

    obj.btnGuardar.on('click', function(e) {
        e.preventDefault();

        var objValidateImagenes = obj.validateImagenes(this);

        if (objValidateImagenes.status) {
            var banner = {
                idConfiguracionBanner: obj.idConfiguracionBanner.val(),
                textoUno: obj.inptTextoUno.val(),
                textoDos: obj.inptTextoDos.val(),
                urlBoton: obj.inptUrlBoton.val(),
                // textoBoton: obj.chkBoton.is(':checked') ? obj.inptTextoBoton.val() : '',
                textoBoton: obj.inptTextoBoton.val(),
                orden: obj.inptOrden.val(),
                idCatalogo: obj.slcCatalogo.val(),
                tipoBanner: obj.tipoBanner.val()
            };

            if (!obj.slcCatalogo.val()) {
                Promotick.toast.warning('Seleccione un catalogo.', 'Error');
                return;
            }

            obj.guardarBanner(banner, objValidateImagenes);
        } else if (objValidateImagenes.mensaje != null) {
            Promotick.toast.warning(objValidateImagenes.mensaje, 'Carga de imagenes')
        }

    });

    obj.chkBoton.on('change', function() {
        if ($(this).is(':checked')) {
            obj.inptTextoBoton.show();
        } else {
            obj.inptTextoBoton.hide();
        }
    });
};

BannerRegistrar.prototype.makeFileInput = function(instance, idError) {
    var imagenDefault = instance.attr('data-default');
    var imagen = instance.attr('data-uri');
    instance.fileinput({
        overwriteInitial: true,
        maxFileSize: 5*1204,
        showClose: false,
        showCaption: false,
        browseLabel: '',
        removeLabel: '',
        browseIcon: '<i class="glyphicon glyphicon-folder-open"></i>',
        removeIcon: '<i class="glyphicon glyphicon-remove"></i>',
        removeTitle: 'Cancelar o limpiar cambios',
        browseClass : 'btn btn-primary btn-upload input-file-button',
        removeClass : 'btn btn-secondary btn-upload input-file-button',
        elErrorContainer: idError,
        msgErrorClass: 'alert alert-block alert-danger',
        defaultPreviewContent: '<img src="' + imagen + '" alt="" style="max-height: 250px">',
        layoutTemplates: {main2: '{preview} {remove} {browse}'},
        allowedFileExtensions: ["jpg", "png", "gif", "jpeg"]
    });

    var $objImg = instance.parent().parent().find('.file-preview').find('.file-default-preview').find('img');

    var image = new Image();
    image.src = imagen;
    image.onload = function() {
        $objImg.attr('src', imagen);
    };
    image.onerror = function() {
        $objImg.attr('src', imagenDefault);
    }
};

BannerRegistrar.prototype.prepararObjeto = function(){
    var obj = this;
    var objValidateImagenes;
    var banner = {
        idConfiguracionBanner: obj.idConfiguracionBanner.val(),
        textoUno: obj.inptTextoUno.val(),
        textoDos: obj.inptTextoDos.val(),
        urlBoton: obj.inptUrlBoton.val(),
        // textoBoton: obj.chkBoton.is(':checked') ? obj.inptTextoBoton.val() : '',
        textoBoton: obj.inptTextoBoton.val(),
        orden: obj.inptOrden.val(),
        idCatalogo: obj.slcCatalogo.val(),
        tipoBanner: obj.tipoBanner.val()
    };

    if (!obj.slcCatalogo.val()) {
        Promotick.toast.warning('Seleccione un catalogo.', 'Error');
        return;
    }

    obj.guardarBanner(banner, objValidateImagenes);

};

BannerRegistrar.prototype.validateImagenes = function(e) {
    var obj = this;

    var objValid = {
        status : false,
        mensaje : null,
        handle : e,
        formData : null
    };

    if (obj.imagenBanner[0].files.length === 0 ) {
        if (obj.idConfiguracionBanner.val() > 0){
            swal({
                    title: $.camelCase("Confirmar"),
                    text: 'La imagen del banner no se modifico, desea continuar ?',
                    type: "info",
                    showCancelButton: true,
                    confirmButtonColor: "#DD6B55",
                    confirmButtonText: 'Si',
                    cancelButtonText: "No, cancelar!",
                    closeOnConfirm: false,
                    closeOnCancel: true
                },
                function (isConfirm) {
                    if (isConfirm){
                        obj.prepararObjeto();
                    }
                }
            );
        }else{
            objValid.mensaje = 'Seleccione al menos una imagen para cargar';
        }
    } else {
        var data = new FormData();
        $.each($('#imagenBanner')[0].files, function(i, file) {
            data.append('imagenBanner', file);
        });

        // data.append('idConfiguracionBanner', obj.idConfiguracionBanner.val());

        objValid.formData = data;
        objValid.status = true;
    }

    return objValid;
};

BannerRegistrar.prototype.guardarBanner = function(banner, objValidateImagenes) {
    var obj = this;

    var loader = null;

    Promotick.ajax.post({
        url : obj.uri + 'administracion/banners-web/actualizar-banner',
        data : JSON.stringify(banner),
        messageError : true,
        messageTitle : 'Actualizacion de banner',
        before : function() {
            // loader = Ladda.create(banner.handle);
            // loader.start();
        },
        success : function(response) {
            if (response.status) {
                var idBanner = response.data;
                Promotick.toast.success(response.message, 'Actualizacion de Banner', {
                    timeOut : 3000,
                    onHidden : function() {
                        window.location.href = obj.uri + 'administracion/banners-web';
                    }
                });
                $('html, body').animate({scrollTop:0}, 'slow');
                obj.cargaImagenes(objValidateImagenes, idBanner);
            }
        },
        complete : function() {
            // loader.stop();
        }
    });
};

BannerRegistrar.prototype.cargaImagenes = function(objValidateImagenes, idBanner) {
    var obj = this;

    var loader = null;

    objValidateImagenes.formData.append('idConfiguracionBanner', idBanner);

    Promotick.ajax.post({
        url : obj.uri + 'administracion/banners-web/cargar-imagenes',
        data : objValidateImagenes.formData,
        processData : false,
        contentType : false,
        messageError : true,
        messageTitle : 'Carga de imagenes',
        before : function() {
            loader = Ladda.create(objValidateImagenes.handle);
            loader.start();
        },
        success : function(response) {
            if (response.status) {
                Promotick.toast.success(response.message, 'Carga de imagenes');
                $('html, body').animate({scrollTop:0}, 'slow');
            }
        },
        complete : function() {
            loader.stop();
        }
    });
};
