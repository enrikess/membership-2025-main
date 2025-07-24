function RecomendadosCargaEstado(uri) {
    this.uri = uri;
    this.modalResumenCarga = $('#modalResumenCarga');
    this.contentResumenCarga = $('#contentResumenCarga');
    this.inputFile = $('#archivo');
    this.btnLimpiar = $('#btnLimpiar');
    this.btnCargar = $('#btnCargar');
    this.resumen = false;
}

RecomendadosCargaEstado.prototype.init = function() {
    this.handle();
};

RecomendadosCargaEstado.prototype.handle = function() {
    var obj = this;

    obj.inputFile.fileinput({
        theme: 'fa',
        language: 'es',
        uploadUrl: obj.uri + "recomendados/importar/subir-excel/estado",
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
        if (data.response) {
            obj.mostrarResumen(data.response.data);
        } else {
            Promotick.toast.error(data.response.message, 'Carga de estados referidos');
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

RecomendadosCargaEstado.prototype.mostrarResumen = function(response) {
    var obj = this;

    if (!obj.resumen) {
        obj.resumen = true;

        Promotick.render.post({
            url : obj.uri + 'recomendados/importar/viewResumenCargaRecomendadoEstado',
            data : JSON.stringify(response),
            context : obj.contentResumenCarga,
            complete : function() {
                obj.modalResumenCarga.modal({backdrop: 'static', keyboard: false});
                obj.resumen = false;
            }
        });
    }

};