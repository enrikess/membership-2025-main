function PdfCarga(uri) {
    this.uri = uri;
    this.modalResumenCarga = $('#modalResumenCarga');
    this.contentResumenCarga = $('#contentResumenCarga');
    this.inputFile = $('#archivo');
    this.btnLimpiar = $('#btnLimpiar');
    this.btnCargar = $('#btnCargar');
    this.slcTipoArchivo = $('#slcTipoArchivo');
    this.divRestricciones = $('#divRestricciones');
    this.resumen = false;
}

PdfCarga.prototype.init = function() {
    this.handle();
    this.initInputFile(false, false);
    this.inputFile.fileinput('disable');
};

PdfCarga.prototype.handle = function() {
    var obj = this;

    obj.slcTipoArchivo.select2({
        placeholder: "Seleccionar",
        minimumResultsForSearch: -1
    });

    obj.btnCargar.on("click", function(e) {
        e.preventDefault();
        var tipoCarga = parseInt(obj.slcTipoArchivo.val());
        if (tipoCarga === 0) {
            Promotick.toast.warning('Debe seleccionar un tipo de archivo', 'Carga de pdf');
        } else {
            var filesCount = obj.inputFile.fileinput('getFilesCount');
            if (filesCount > 0) {
                obj.inputFile.fileinput('upload');
            } else {
                Promotick.toast.warning('Debe seleccionar un archivo de carga', 'Carga de pdf');
            }
        }

    });

    obj.btnLimpiar.on("click", function() {
        obj.inputFile.fileinput('clear');
        obj.inputFile.fileinput('enable');
    });

    obj.slcTipoArchivo.on('change', function(e){
        var tipo = parseInt(obj.slcTipoArchivo.val());
        if (tipo === 1) {
            obj.initInputFile(false, true);
            obj.setRestricciones(
                [
                    {key : 'Formatos permitidos', value : 'pdf'},
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

PdfCarga.prototype.setRestricciones = function(restric) {
    var obj = this;

    var $ul = $('<ul/>');
    $.each(restric,function(i,e) {
        $ul.append($('<li/>').append('<b>' + e.key + '</b> ' + e.value));
    });
    obj.divRestricciones.html($ul);
};

PdfCarga.prototype.initInputFile = function(esComprimido, enable) {
    var obj = this;

    var extensiones = [];
    var url = '';
    var conteo = 1;
    var size = 0;

    if (enable && !esComprimido) {
        extensiones = ['pdf'];
        url = 'catalogos/importar-pdf/cargar-archivo-pdf/';
        conteo = 10;
        size = 10;
    } else if (enable && esComprimido) {
        extensiones = ['rar'];
        url = 'catalogos/importar-pdf/cargar-archivo-rar/';
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
        elErrorContainer: '.error-pdf',
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

PdfCarga.prototype.mostrarResumen = function(response) {
    var obj = this;
    if (!obj.resumen) {
        obj.resumen = true;
        Promotick.render.post({
            url : obj.uri + 'catalogos/importar-pdf/viewResumenCargaPdf',
            data : JSON.stringify(response),
            context : obj.contentResumenCarga,
            complete : function() {
                obj.modalResumenCarga.modal({backdrop: 'static', keyboard: false});
                obj.resumen = false;
            }
        });
    }
};