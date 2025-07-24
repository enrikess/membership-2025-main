function AceleradorWeb(uri) {
    this.uri = uri;
    this.rangoDatePicker = $('#rangoDatePicker');
    this.grid = $('#gridVentas');
    this.btnFiltrar = $('#btnFiltrar');
    this.fechaInicio = $('#fechaInicio');
    this.fechaFin = $('#fechaFin');
    this.btnDescargar = $('#btnDescargar');
    this.cleanCheckboxs = $('#clean-checkboxs');
    this.containerEliminar = $('#container-eliminar');
    this.textEliminar = $('#text-eliminar');
    this.btnBorrar = $('#btnBorrar');
    this.modalBorrarConfirm = $('#modalBorrarConfirm');
    this.modalBorrarAceptar = $('#modalBorrarAceptar');
    this.modalEditarAceptar = $('#modalEditarAceptar');
    this.slcCatalogo = $('#slcCatalogo');
    this.errorCatalogo = $('#errorCatalogo');
    this.textareaEditor = $('#editor');
    this.slcCatalogoCrear = $('#slcCatalogoCrear');
    this.modalRegistrar = $('#modalRegistrar');
    this.btnCrear = $('#btnCrear');
    this.modalCrearAceptar = $('#modalCrearAceptar');
    this.imagen = $('#imagen');
    this.imagenCrear = $('#imagenCrear');
    this.selectedidAvances = [];
}

AceleradorWeb.prototype.init = function() {
    this.handle();
    this.listar();
};

AceleradorWeb.prototype.handle = function() {
    var obj = this;


    obj.makeFileInput(obj.imagen, '#principalError');
    obj.makeFileInput(obj.imagenCrear, '#principalErrorCrear');

    obj.rangoDatePicker.datepicker({
        endDate: "0m"
    });

    obj.btnCrear.on('click', function(e){
        e.preventDefault();
        // sceditor.instance(document.getElementById('editorCrear')).val('');
        obj.obtenerCatalogosDisponibles();
    });

    obj.btnFiltrar.on('click', function(e){
        e.preventDefault();
        obj.gridReload();
    });

    obj.modalEditarAceptar.on('click', function(e){
        e.preventDefault();

        if (obj.slcCatalogo.val() === '0') {
            obj.slcCatalogo.css('border-color', 'red');
            obj.errorCatalogo.text('Debe ingresar una fecha (DD/MM/YYYY).');
            return;
        } else {
            obj.slcCatalogo.css('border-color', '#e5e6e7');
            obj.errorCatalogo.text('');
        }

        const validateImagen = obj.validateImagenesEditar();

        obj.editar({
            idAcelerador: Number($(this).attr('idtyc')),
            idCatalogo: Number(obj.slcCatalogo.val()),
            titulo: $('#tituloEditar').val(),
        }, Number($(this).attr('idtyc')), validateImagen)
    });


    obj.btnDescargar.on('click', function(e) {
        e.preventDefault();
        window.location.href = $(this).attr('data-descarga');
    })

    obj.cleanCheckboxs.on('change', function(e) {

        if ($(this).is(":checked")) {
            $('.meta-checkbox').each(function() {
                obj.selectedidAvances.push($(this).attr("idtyc"));
                $(`.meta-checkbox`).prop("checked", true);
            } )
        } else {
            $('.meta-checkbox').each(function() {
                obj.selectedidAvances = obj.selectedidAvances.filter(meta => `${meta}` !== `${$(this).attr("idtyc")}`);
                $(`.meta-checkbox`).prop("checked", false);
            })
        }
        obj.actualizarSeleccionados();
    })

    $(document).on('click', '.btn-editar', function() {

        let tyc = $(this).data();
        obj.slcCatalogo.val(tyc.idcatalogo);

        $('#imagen').attr('data-uri', tyc.imagen);
        obj.makeFileInput(obj.imagen, '#principalError');

        $('#tituloEditar').val(atob(tyc.texto));
        obj.modalEditarAceptar.attr("idtyc", tyc.id);

        $('#valueImage').val('1');
        $('#modalEditar').modal({});
    })

    $(document).on("change", ".meta-checkbox", function() {
        let idtyc = $(this).attr("idtyc");

        if ($(this).is(":checked")) {
            // Add idtyc if checked
            if (!obj.selectedidAvances.includes(idtyc)) {
                obj.selectedidAvances.push(idtyc);
            }
        } else {
            // Remove idtyc if unchecked
            obj.selectedidAvances = obj.selectedidAvances.filter(meta => meta !== idtyc);
        }
        obj.actualizarSeleccionados();
    });

    obj.btnBorrar.on('click', function(e){
        e.preventDefault();
        obj.modalBorrarConfirm.modal({
            backdrop: 'static',
            keyboard: false
        });
    });

    obj.modalBorrarAceptar.on('click', function(e){
        e.preventDefault();
        obj.borrar(obj.selectedidAvances.join(","));
    });

    obj.modalCrearAceptar.on('click', function(e){
        e.preventDefault();

        if (obj.slcCatalogoCrear.val() === '0') {
            obj.slcCatalogoCrear.css('border-color', 'red');
            $('#errorCatalogoCrear').text('Debe ingresar una fecha (DD/MM/YYYY).');
            return;
        } else {
            obj.slcCatalogoCrear.css('border-color', '#e5e6e7');
            $('#errorCatalogoCrear').text('');
        }

        const validateImagen = obj.validateImagenes();

        obj.crear({
            titulo: $('#tituloCrear').val(),
            idCatalogo: $('#slcCatalogoCrear').val(),
        }, validateImagen);
    });

};

AceleradorWeb.prototype.actualizarSeleccionados = function() {
    var obj = this;
    obj.selectedidAvances = [...new Set(obj.selectedidAvances)];
    if (obj.selectedidAvances.length) {
        obj.containerEliminar.css('display', 'flex');
        obj.textEliminar.text('Se seleccionaron: ' + obj.selectedidAvances.length);
    } else {
        obj.containerEliminar.css('display', 'none');
    }
}


AceleradorWeb.prototype.crear = function(venta, validateImagen){
    var obj = this;
    Promotick.ajax.post({
        url : obj.uri + 'administracion/acelerador/crear',
        data : JSON.stringify(venta),
        messageError : true,
        messageTitle : 'Crear',
        before : function() {
            obj.modalCrearAceptar.text('Cargando...');
            obj.modalCrearAceptar.prop('disabled', true);
        },
        success : function(response) {
            if (response.status) {
                if (validateImagen.status) {
                    obj.cargaImagenes(validateImagen, response.data);
                } else {
                    obj.modalCrearAceptar.text('Aceptar');
                    obj.modalCrearAceptar.prop('disabled', false);
                    Promotick.toast.success('Creacion exitosa', 'Crear');
                    obj.modalRegistrar.modal('hide');
                    obj.gridReload();
                }
            }
        },
        complete : function() {
        },
        error: function(status, error) {
            console.log('error ' + status + ': ' + error);
        }
    });
}


AceleradorWeb.prototype.editar = function(venta, id, validateImagen){
    var obj = this;
    Promotick.ajax.post({
        url : obj.uri + 'administracion/acelerador/editar',
        data : JSON.stringify(venta),
        messageError : true,
        messageTitle : 'Editar terminos',
        before : function() {
            obj.modalEditarAceptar.text('Cargando...');
            obj.modalEditarAceptar.prop('disabled', true);
        },
        success : function(response) {
            if (response.status) {
                if (validateImagen.status) {
                    obj.cargaImagenes(validateImagen, response.data);
                } else {
                    obj.modalEditarAceptar.text('Aceptar');
                    obj.modalEditarAceptar.prop('disabled', false);
                    Promotick.toast.success('Modificación exitosa', 'Editar');
                    $('#modalEditar').modal('hide');
                    obj.gridReload();
                }
            }
        },
        complete : function() {
        },
        error: function(status, error) {
            console.log('error ' + status + ': ' + error);
        }
    });
}

AceleradorWeb.prototype.obtenerCatalogosDisponibles = function(){
    const obj = this;
    Promotick.ajax.get({
        url : obj.uri + 'administracion/acelerador/catalogos',
        before : function() {
        },
        success : function(response) {
            if (response && response.length) {
                obj.slcCatalogoCrear.empty();
                obj.slcCatalogoCrear.append('<option value="0">Seleccionar</option>');
                response.forEach(function(catalogo) {
                    obj.slcCatalogoCrear.append(`<option value="${catalogo.idCatalogo}">${catalogo.nombreCatalogo}</option>`);
                });

                $("#imagenCrear").fileinput('clear');
                $('#tituloCrear').val('');
                $('#imagenCrear').val('');

                obj.modalRegistrar.modal({
                    backdrop: 'static',
                    keyboard: false
                });
            } else if (response && response.length === 0) {
                $('#modalSinCatalogos').modal({
                    backdrop: 'static',
                    keyboard: false
                });
            }
        },
        complete : function() {
        }
    });
}

AceleradorWeb.prototype.borrar = function(listaId){
    var obj = this;
    Promotick.ajax.post({
        url : obj.uri + 'administracion/acelerador/borrar',
        data : JSON.stringify({
            listaId,
        }),
        messageError : true,
        messageTitle : 'Eliminar',
        before : function() {
            obj.btnBorrar.text('Cargando...');
            obj.btnBorrar.prop('disabled', true);
            obj.modalBorrarConfirm.modal('hide');
        },
        success : function(response) {
            if (response.status) {
                Promotick.toast.success(response.message, 'Eliminar');
            }
        },
        complete : function() {
            obj.btnBorrar.text(' Eliminar registros');
            obj.btnBorrar.prop('disabled', false);
            obj.selectedidAvances = [];
            $(`.meta-checkbox`).prop("checked", false);
            obj.gridReload();
            obj.actualizarSeleccionados();
        }
    });
}

AceleradorWeb.prototype.listar = function(){
    var obj = this;
    obj.grid.DataTable({
        "bProcessing" : true,
        "bFilter" : false,
        "bLengthChange": false,
        "bPaginate": true,
        "bInfo" : true,
        "serverSide" : true,
        "bSort": false,
        "ajax": {
            "url" : obj.uri + 'administracion/acelerador/listar',
            "type": "POST",
            "data" : function(d) {
            }
        },
        "columns" : [
            {"data" : "idAcelerador", "orderable": false, "render" : this._formatCheck, "sClass" : "text-center align-middle"},
            {"data" : "catalogo.nombreCatalogo", "orderable": false, "sClass" : "text-center align-middle"},
            {"data" : "texto", "orderable": false, "sClass" : "text-center align-middle" },
            {"data" : "imagen", "orderable": false, "sClass" : "text-center align-middle","render" :this._formatImagen },
            {"data" : "idAcelerador", "orderable": false, "render" : this._formatEditar, "sClass" : "text-center p-0 align-middle"},
        ],
        "lengthMenu": [[10, 20, 30], [10, 20, 30]],
        "initComplete":function(settings,json){

        },
        "columnDefs": [
            { orderDataType: 'dom-text', render: $.fn.dataTable.render.text(),  "targets": "_all" },
        ],
        "fnDrawCallback": function(){
            for (let i = 0; i < obj.selectedidAvances.length; i++) {
                $(`.meta-checkbox[idAvance='${obj.selectedidAvances[i]}']`).prop("checked", true);

            }
            $('#clean-checkboxs').prop("checked", false);
            obj.actualizarSeleccionados();
        }
    });
};

AceleradorWeb.prototype._formatDate = function(data, type, full){
    return data && data.length > 10 ? data.substring(0, 10) : data;
};


AceleradorWeb.prototype._formatCheck = function(data, type, full) {
    return `<input type="checkbox" class="meta-checkbox" idtyc="${data}"/>`;
};

AceleradorWeb.prototype._formatTextarea = function(data, type, full) {
    return `<textarea id="story" name="story" disabled="disabled" rows="2" style="width: 100%" >${data}</textarea>`;
};

AceleradorWeb.prototype._formatEditar = function(data, type, full) {
    return `<button type="button" class="btn btn-primary btn-sm btn-editar" data-idcatalogo="${full.catalogo.idCatalogo}" data-imagen="${full.imagen}" data-texto="${btoa(full.texto)}" data-id="${data}">Editar</button>`;
};

AceleradorWeb.prototype.gridReload = function(){
    this.grid.DataTable().ajax.reload();
};

AceleradorWeb.prototype.makeFileInput = function(instance, idError) {
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
        $('#valueImageCrear').val('0');
    });
};

AceleradorWeb.prototype.cargaImagenes = function(objValidateImagenes, idBanner) {
    var obj = this;

    var loader = null;

    objValidateImagenes.formData.append('idAcelerador', idBanner);

    Promotick.ajax.post({
        url : obj.uri + 'administracion/acelerador/cargar-imagenes',
        data : objValidateImagenes.formData,
        processData : false,
        contentType : false,
        messageError : true,
        messageTitle : 'Carga de imagenes',
        before : function() {
        },
        success : function(response) {
            if (response.status) {
                // Promotick.toast.success(response.message, 'Carga de imagenes');
                // obj.gridReload();
            }
        },
        complete : function() {
            obj.modalRegistrar.modal('hide');
            $('#modalEditar').modal('hide');
            obj.modalEditarAceptar.text('Aceptar');
            obj.modalEditarAceptar.prop('disabled', false);
            obj.modalCrearAceptar.text('Aceptar');
            obj.modalCrearAceptar.prop('disabled', false);
            obj.gridReload();
        }
    });
};

AceleradorWeb.prototype.validateImagenes = function() {

    var objValid = {
        status : false,
        mensaje : null,
        handle : null,
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
    } else if ($('#valueImageCrear').val() === '0') {
        objValid.formData = data;
        objValid.status = true;
    }

    return objValid;
};

AceleradorWeb.prototype.validateImagenesEditar = function() {
    var objValid = {
        status: false,
        mensaje: null,
        handle: null,
        formData: null
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

AceleradorWeb.prototype._formatImagen = function(data, type, full) {
    var obj = this;
    if (data != null && data != '') {
        return '<a class="image-popup" href="' +  data + '"><img src="' +  data + '" height="60px"/></a>';
    }
    return 'Sin imagen';
};
