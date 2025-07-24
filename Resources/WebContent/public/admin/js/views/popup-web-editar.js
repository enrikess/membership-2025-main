function PopupEditar(uri) {
    this.uri = uri;
    this.idCatalogo = $('#idCatalogo');
    this.popupImagen = $('#popupImagen');
    this.tipoProducto = $('#tipoProducto');
    this.btnGuardarImagen = $('#btnGuardarImagen');
    this.tabImagenes = $('#tabImagenes');
}

PopupEditar.prototype.init = function() {
    this.handle();
    this.validateImagenes();
};

PopupEditar.prototype.handle = function() {
    var obj = this;
    $.fn.modal.Constructor.prototype._enforceFocus = function() {};

    var imagenDefecto = obj.popupImagen.attr('data-uri');

    obj.makeFileInput(obj.popupImagen, '#principalError');

    obj.btnGuardarImagen.on('click', function(e) {
        e.preventDefault();

        var objValidateImagenes = obj.validateImagenes(this);
        if (objValidateImagenes.status) {
            obj.cargaImagenes(objValidateImagenes);
        } else if (objValidateImagenes.mensaje != null) {
            Promotick.toast.warning(objValidateImagenes.mensaje, 'Carga de imagenes')
        }
    });

};

PopupEditar.prototype.makeFileInput = function(instance, idError) {
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

PopupEditar.prototype.validateImagenes = function(e) {
    var obj = this;

    var objValid = {
        status : false,
        mensaje : null,
        handle : e,
        formData : null
    };


    if (obj.popupImagen[0].files.length === 0) {
        objValid.mensaje = 'Seleccione al menos una imagen para cargar';
    } else {
        var data = new FormData();
        $.each($('#popupImagen')[0].files, function(i, file) {
            data.append('popupImagen', file);
        });

        data.append('idCatalogo', obj.idCatalogo.val());

        objValid.formData = data;
        objValid.status = true;
    }

    return objValid;
};

PopupEditar.prototype.cargaImagenes = function(objValidateImagenes) {
    var obj = this;

    var loader = null;

    Promotick.ajax.post({
        url : obj.uri + 'administracion/popups-web/cargar-imagenes',
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
