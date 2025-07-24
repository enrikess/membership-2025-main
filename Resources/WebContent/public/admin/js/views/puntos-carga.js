function PuntosCarga(uri) {
    this.uri = uri;
    this.modalResumenCarga = $('#modalResumenCarga');
    this.contentResumenCarga = $('#contentResumenCarga');
    this.inputFile = $('#archivo');
    this.btnLimpiar = $('#btnLimpiar');
    this.btnCargar = $('#btnCargar');
    this.resumen = false;
}

PuntosCarga.prototype.init = function() {
    this.handle();
};

PuntosCarga.prototype.handle = function() {
    var obj = this;

    obj.inputFile.fileinput({
        theme: 'fa',
        language: 'es',
        uploadUrl: obj.uri + "puntos/subir-excel",
        allowedFileExtensions: ['xlsx'],
        showPreview: false,
        elErrorContainer: '.error',
        browseOnZoneClick: true,
        maxFileCount: 1,
        showRemove: false,
        showUpload: false,
        showCancel: false,
        required: true,
        showDownload: false,
        showZoom : false
    }).on('fileuploaded', function(event, data, id, index) {
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

    obj.btnCargar.on("click", function() {
        var filesCount = obj.inputFile.fileinput('getFilesCount');
        if (filesCount > 0) {
            obj.inputFile.fileinput('upload');
        }
    });

    obj.btnLimpiar.on("click", function() {
        obj.inputFile.fileinput('clear');
        obj.inputFile.fileinput('enable');
    });
};

PuntosCarga.prototype.mostrarResumen = function(response) {
    var obj = this;
    if (!obj.resumen) {
        obj.resumen = true;

        Promotick.render.post({
            url: obj.uri + 'puntos/viewResumenCargaPuntos',
            data: JSON.stringify(response),
            context: obj.contentResumenCarga,
            complete: function () {
                obj.modalResumenCarga.modal({backdrop: 'static', keyboard: false});
                obj.resumen = false;
            }
        });
    }
};