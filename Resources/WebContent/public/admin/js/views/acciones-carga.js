function AccionesCarga(uri) {
    this.uri = uri;
    this.modalResumenCarga = $('#modalResumenCarga');
    this.contentResumenCarga = $('#contentResumenCarga');
    this.inputFile = $('#archivo');
    this.btnLimpiar = $('#btnLimpiar');
    this.btnCargar = $('#btnCargar');
    this.resumen = false;
}

AccionesCarga.prototype.init = function() {
    this.handle();
};

AccionesCarga.prototype.handle = function() {
    var obj = this;

    obj.inputFile.fileinput({
        theme: 'fa',
        language: 'es',
        uploadUrl: obj.uri + "acciones/subir-excel",
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
            Promotick.toast.error(data.response.message, 'Carga de acciones de sellout');
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

AccionesCarga.prototype.mostrarResumen = function(response) {
    var obj = this;
    if (!obj.resumen) {
        obj.resumen = true;

        Promotick.render.post({
            url: obj.uri + 'acciones/viewResumenCargaAcciones',
            data: JSON.stringify(response),
            context: obj.contentResumenCarga,
            complete: function () {
                obj.modalResumenCarga.modal({backdrop: 'static', keyboard: false});
                obj.resumen = false;
            }
        });
    }
};

