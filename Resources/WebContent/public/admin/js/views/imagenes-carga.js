function ImagenesCarga(uri) {
    this.uri = uri;
    this.modalResumenCarga = $('#modalResumenCarga');
    this.contentResumenCarga = $('#contentResumenCarga');
    this.inputFile = $('#archivo');
    this.btnLimpiar = $('#btnLimpiar');
    this.btnCargar = $('#btnCargar');
    this.slcTipoArchivo = $('#slcTipoArchivo');
    this.slcTipoImagen = $('#slcTipoImagen');
    this.divRestricciones = $('#divRestricciones');
    this.resumen = false;
}

ImagenesCarga.prototype.init = function() {
    this.handle();
    this.initInputFile(false, false);
    this.inputFile.fileinput('disable');
};

ImagenesCarga.prototype.handle = function() {
    var obj = this;

    obj.slcTipoArchivo.select2({
        placeholder: "Seleccionar",
        minimumResultsForSearch: -1
    });

    obj.slcTipoImagen.select2({
        placeholder: "Seleccionar",
        minimumResultsForSearch: -1
    });

    obj.btnCargar.on("click", function(e) {
        e.preventDefault();
        var tipoCarga = parseInt(obj.slcTipoArchivo.val());
        var tipoImagen = parseInt(obj.slcTipoImagen.val());
        if (tipoImagen === 0) {
            Promotick.toast.warning('Debe seleccionar un tipo de imagen', 'Carga de imagenes');
        } else if (tipoCarga === 0) {
            Promotick.toast.warning('Debe seleccionar un tipo de archivo', 'Carga de imagenes');
        } else {
            var filesCount = obj.inputFile.fileinput('getFilesCount');
            if (filesCount > 0) {
                obj.inputFile.fileinput('upload');
            } else {
                Promotick.toast.warning('Debe seleccionar un archivo de carga', 'Carga de imagenes');
            }
        }

    });

    obj.btnLimpiar.on("click", function() {
        obj.inputFile.fileinput('clear');
        obj.inputFile.fileinput('enable');
    });

    obj.slcTipoImagen.on('change', function(e){

        obj.slcTipoArchivo
            .val(null)
            .trigger('change');


    });

    obj.slcTipoArchivo.on('change', function(e){
        var tipo = parseInt(obj.slcTipoArchivo.val());
        if (tipo === 1) {
            obj.initInputFile(false, true);
            obj.setRestricciones(
                [
                    {key : 'Formatos permitidos', value : 'jpg, jpeg, png, gif'},
                    {key : 'Tamaño maximo', value : '10 MB'}
                ]
            );
            obj.inputFile.fileinput('enable');
        } else if (tipo === 2){
            obj.initInputFile(true, true);
            obj.setRestricciones(
                [
                    {key : 'Formatos permitidos', value : 'rar'},
                    {key : 'Tamaño maximo', value : '100 MB'}
                ]
            );
            obj.inputFile.fileinput('enable');
        } else {
            obj.divRestricciones.html('');
        }

    });
};

ImagenesCarga.prototype.setRestricciones = function(restric) {
    var obj = this;

    var $ul = $('<ul/>');
    $.each(restric,function(i,e) {
        $ul.append($('<li/>').append('<b>' + e.key + '</b> ' + e.value));
    });
    obj.divRestricciones.html($ul);
};

ImagenesCarga.prototype.initInputFile = function(esComprimido, enable) {
    var obj = this;

    var extensiones = [];
    var url = '';
    var conteo = 1;
    var size = 0;
    var tipoImagen = parseInt(obj.slcTipoImagen.val());
    console.log('tipo imagen ---> ' + tipoImagen);

    if (enable && !esComprimido) {
        extensiones = ['jpg', 'jpeg', 'png', 'gif'];
        url = 'catalogos/importar-imagen/cargar-archivo-imagen/' + tipoImagen;
        conteo = 10;
        size = 10;
    } else if (enable && esComprimido) {
        extensiones = ['rar'];
        url = 'catalogos/importar-imagen/cargar-archivo-rar/' + tipoImagen;
        conteo = 1;
        size = 100;
    }

    obj.inputFile.fileinput('destroy');
    obj.inputFile.fileinput({
        theme: 'fa',
        language: 'es',
        uploadUrl: obj.uri + url,
        allowedFileExtensions: extensiones,
        showPreview: false,
        elErrorContainer: '.error-imagenes',
        browseOnZoneClick: true,
        maxFileCount: conteo,
        maxFileSize : (size * 1024),
        showRemove: false,
        showUpload: false,
        showCancel: false,
        required: true,
        showDownload: false,
        showZoom : false,
        uploadAsync : false,
        uploadExtraData: function() {
            return {

            }
        }
    }).on('fileuploaded', function(event, data, id, index) {
        if (data.response.status) {
            obj.mostrarResumen(data.response.data);
        } else {
            Promotick.toast.error(data.response.message, 'Carga de puntos');
        }
        obj.inputFile.fileinput('clear');
        obj.inputFile.fileinput('enable');
    }).on('filebatchuploadsuccess', function(event, data, id, index) {
        if (data.response.status) {
            obj.mostrarResumen(data.response.data);
        } else {
            Promotick.toast.error(data.response.message, 'Carga de puntos');
        }
        obj.inputFile.fileinput('clear');
        obj.inputFile.fileinput('enable');
    }).on('filepreupload', function(event, data, previewId, index) {
    }).on('fileselect', function(event, numFiles, label) {
    }).on('filepreajax', function(event, previewId, index) {
    });
};

ImagenesCarga.prototype.mostrarResumen = function(response) {
    var obj = this;
    if (!obj.resumen) {
        obj.resumen = true;
        Promotick.render.post({
            url : obj.uri + 'catalogos/importar-imagen/viewResumenCargaImagenes',
            data : JSON.stringify(response),
            context : obj.contentResumenCarga,
            complete : function() {
                obj.modalResumenCarga.modal({backdrop: 'static', keyboard: false});
                obj.resumen = false;
            }
        });
    }
};