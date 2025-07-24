function CategoriasDetalle(uri, registro) {
    this.registro = registro;
    this.uri = uri;
    this.btnGuardar = $('#btnGuardar');
    this.idCategoria = $('#idCategoria');
    this.formRegistro = $('#form-registro');
    this.ordenCategoria = $('#ordenCategoria');
    this.chkEstadoCategoria = $('#chkEstadoCategoria');
    this.slctipoCategoria = $('#slctipoCategoria');
    this.slccategoriaLista = $('#categoriaLista');
    this.dvSubCategoria = $('#dvSubCategoria');
    this.nombresCategoria = $('#nombresCategoria');
    this.imagen = $('#imagen');
}

CategoriasDetalle.prototype.init = function() {
    this.handle();
    this.validarFormRegistro();
};

CategoriasDetalle.prototype.handle = function() {
    var obj = this;

    obj.makeFileInput(obj.imagen, '#principalError');

    if (parseInt(obj.slctipoCategoria.val()) == 1){
        obj.dvSubCategoria.hide();
    }else {
        obj.dvSubCategoria.show();
    }

    obj.slctipoCategoria.on('change', function() {
        var tipo = parseInt(obj.slctipoCategoria.val());
        if (tipo === 1) {
            obj.dvSubCategoria.hide();
        }else {
            obj.dvSubCategoria.show();
        }
    });

    obj.btnGuardar.on('click', function(e) {
        e.preventDefault();

        if (obj.formRegistro.valid()) {
            const validateImagen = obj.validateImagenes();
            obj.guardarCategoria(this, validateImagen);
        } else {
            obj.formRegistro.validate().focusInvalid();
        }
    });
};


CategoriasDetalle.prototype.makeFileInput = function(instance, idError) {
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

    instance.on('change', function(event) {
        let files = event.target.files;
        if (files.length > 0) {
            $('#valueImage').val('0');
        }
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
    instance.on('filecleared', function(event) {
        instance.fileinput('reset');
        $('#valueImage').val('0');
    });
};

CategoriasDetalle.prototype.validarFormRegistro = function() {
    var obj = this;
    this.formRegistro.validate({
        rules: {
            nombresCategoria: {
                required: true,
                minlength: 3
            },
            slctipoCategoria: {
                required: true,
                min: 1
            },
            categoriaLista: {
                required: true,
                min: 1
            },
            ordenCategoria: {
                required: true,
                digits: true,
                min: 1
            }
        },
        messages: {
            nombresCategoria: {
                required: 'Ingrese nombre de la categoria',
                minlength: 'Ingrese por lo menos 3 caracteres'
            },
            slctipoCategoria: {
                required: 'Seleccione un tipo de categoria',
                min: 'Seleccione un tipo de categoria'
            },
            categoriaLista: {
                required: 'Seleccione una categoria',
                min: 'Seleccione una categoria'
            },
            ordenCategoria: {
                required: 'Ingrese un orden',
                digits: 'El valor minimo es 1',
                min: 'El valor minimo es 1'
            }
        }
    });

};

CategoriasDetalle.prototype.guardarCategoria = function(context, validateImagen) {
    var obj = this;

    var categoriaParent = parseInt(obj.slctipoCategoria.val()) === 2 ? parseInt(obj.slccategoriaLista.val()) : null;

    var categoria = {
        idCategoria: obj.idCategoria.val(),
        nombreCategoria: obj.nombresCategoria.val(),
        idTipoCategoria: parseInt(obj.slctipoCategoria.val()),
        idCategoriaParent: categoriaParent,
        ordenCategoria: obj.ordenCategoria.val(),
        estadoCategoria: obj.chkEstadoCategoria.is(':checked') ? 1 : -1
    };

    var loader = null;

    Promotick.ajax.post({
        url: obj.uri + 'catalogos/categorias/guardar-categoria',
        data: JSON.stringify(categoria),
        messageError: true,
        messageTitle: 'Mantenimiento de Categoria',
        before: function () {
            loader = Ladda.create(context);
            loader.start();
        },
        success: function (response) {
            if (response.status) {
                if (validateImagen.status) {
                    obj.cargaImagenes(validateImagen, response.data);
                } else {
                    Promotick.toast.success(response.message, 'Mantenimiento de Categoria', {
                        timeOut: 3000,
                        onHidden: function () {
                            window.location.href = obj.uri + 'catalogos/categorias';
                        }
                    });
                    $('html, body').animate({scrollTop:0}, 'slow');

                    if (obj.registro === '1') {
                        obj.formRegistro[0].reset();
                    }
                }
            }
        },
        complete: function () {
            loader.stop();
        }
    });
}

CategoriasDetalle.prototype.validateImagenes = function(e) {
    var objValid = {
        status : false,
        mensaje : null,
        handle : e,
        formData : null
    };
    var data = new FormData();
    var obj = this;
    if (obj.imagen[0].files.length) {
        $.each($('#imagen')[0].files, function (i, file) {
            data.append('imagen', file);
        });
        objValid.formData = data;
        objValid.status = true;
    } else if ($('#valueImage').val() === '0') {
        objValid.formData = data;
        objValid.status = true;
    }

    return objValid;
};


CategoriasDetalle.prototype.cargaImagenes = function(objValidateImagenes, idCategoria) {
    var obj = this;

    var loader = null;

    objValidateImagenes.formData.append('idCategoria', idCategoria);

    Promotick.ajax.post({
        url : obj.uri + 'catalogos/categorias/cargar-imagenes',
        data : objValidateImagenes.formData,
        processData : false,
        contentType : false,
        messageError : true,
        messageTitle : 'Carga de imagenes',
        before : function() {
            // loader = Ladda.create(objValidateImagenes.handle);
            // loader.start();
        },
        success : function(response) {
            if (response.status) {
                Promotick.toast.success(response.message, 'Mantenimiento de Categoria', {
                    timeOut: 3000,
                    onHidden: function () {
                        window.location.href = obj.uri + 'catalogos/categorias';
                    }
                });
                $('html, body').animate({scrollTop:0}, 'slow');

                if (obj.registro === '1') {
                    obj.formRegistro[0].reset();
                }
            }
        },
        complete : function() {
            // loader.stop();
        }
    });
};