function ReporteMundialPolla(uri) {
    this.uri = uri;
    this.rangoDatePicker = $('#rangoDatePicker');
    this.grid = $('#grid');
    this.btnFiltrar = $('#btnFiltrar');
    this.fechaInicio = $('#fechaInicio');
    this.fechaFin = $('#fechaFin');
    this.btnDescargar = $('#btnDescargar');
    this.inptBuscar = $('#buscarParticipante');
    this.btnBuscar = $('#btnBuscar');
}

ReporteMundialPolla.prototype.init = function() {
    this.handle();
    this.listar();
};

ReporteMundialPolla.prototype.handle = function() {
    var obj = this;

    obj.rangoDatePicker.datepicker({
        endDate: "0m"
    });

    obj.btnFiltrar.on('click', function(e){
        e.preventDefault();
        obj.gridReload();
    });

    obj.btnBuscar.on('click', function(e){
        e.preventDefault();
        obj.gridReload();
    });

    obj.btnDescargar.on('click', function(e) {
        e.preventDefault();
        window.location.href = $(this).attr('data-descarga');
    })

};

ReporteMundialPolla.prototype.listar = function(){
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
            "url" : obj.uri + 'reportes/mundial-polla/listar',
            "type": "POST",
            "data" : function(d) {
                d.nroDocumento = obj.inptBuscar.val();
                // d.finicio = obj.fechaInicio.data('datepicker').getFormattedDate('yyyy-mm-dd');
                // d.ffin = obj.fechaFin.data('datepicker').getFormattedDate('yyyy-mm-dd');
            }
        },
        "columns" : [
            {"data" : "nombreCatalogo", "orderable": false},
            {"data" : "usuarioParticipante", "orderable": false},
            {"data" : "nombreParticipante", "orderable": false},
            {"data" : "appaterno", "orderable": false},
            {"data" : "apmaterno", "orderable": false},
            {"data" : "broker", "orderable": false},
            {"data" : "ciudad", "orderable": false},
            {"data" : "regional", "orderable": false},
            {"data" : "statusParticipante", "orderable": false},
            {"data" : "fechaRespuesta", "orderable": false, "render": this._formatNull},
            {"data" : "horaRespuesta", "orderable": false, "render": this._formatNull},
            {"data" : "completado", "orderable": false, "render": this._formatNull},
            {"data" : "a1", "orderable": false, "render": this._formatNR},
            {"data" : "a2", "orderable": false, "render": this._formatNR},
            {"data" : "b1", "orderable": false, "render": this._formatNR},
            {"data" : "b2", "orderable": false, "render": this._formatNR},
            {"data" : "c1", "orderable": false, "render": this._formatNR},
            {"data" : "c2", "orderable": false, "render": this._formatNR},
            {"data" : "d1", "orderable": false, "render": this._formatNR},
            {"data" : "d2", "orderable": false, "render": this._formatNR},
            {"data" : "e1", "orderable": false, "render": this._formatNR},
            {"data" : "e2", "orderable": false, "render": this._formatNR},
            {"data" : "f1", "orderable": false, "render": this._formatNR},
            {"data" : "f2", "orderable": false, "render": this._formatNR},
            {"data" : "g1", "orderable": false, "render": this._formatNR},
            {"data" : "g2", "orderable": false, "render": this._formatNR},
            {"data" : "h1", "orderable": false, "render": this._formatNR},
            {"data" : "h2", "orderable": false, "render": this._formatNR},
            {"data" : "a1b2", "orderable": false, "render": this._formatNR},
            {"data" : "b1a2", "orderable": false, "render": this._formatNR},
            {"data" : "c1d2", "orderable": false, "render": this._formatNR},
            {"data" : "d1c2", "orderable": false, "render": this._formatNR},
            {"data" : "e1f2", "orderable": false, "render": this._formatNR},
            {"data" : "f1e2", "orderable": false, "render": this._formatNR},
            {"data" : "g1h2", "orderable": false, "render": this._formatNR},
            {"data" : "h1g2", "orderable": false, "render": this._formatNR},
            {"data" : "a1b2c1d2", "orderable": false, "render": this._formatNR},
            {"data" : "b1a2d1c2", "orderable": false, "render": this._formatNR},
            {"data" : "e1f2g1h2", "orderable": false, "render": this._formatNR},
            {"data" : "f1e2h1g2", "orderable": false, "render": this._formatNR},
            {"data" : "a1b2c1d2e1f2g1h2", "orderable": false, "render": this._formatNR},
            {"data" : "b1a2d1c2f1e2h1g2", "orderable": false, "render": this._formatNR},
            {"data" : "finalPartido", "orderable": false, "render": this._formatNR},


        ],
        "lengthMenu": [[10, 15, 20], [10, 15, 20]],
        "initComplete":function(settings,json){

        },
        "fnDrawCallback": function(){

        }
    });
};

ReporteMundialPolla.prototype.gridReload = function(){
    this.grid.DataTable().ajax.reload();
};

ReporteMundialPolla.prototype._formatArchivo = function (data, type, full) {
    return '<a href="' + data + '" target="_blank" class="btn btn-sm btn-success"><i class="fa fa-download" aria-hidden="true"></i> Descargar</a>';
};

ReporteMundialPolla.prototype._formatNull = function(data, type, full) {
    return data != null ? data : '-';
};

ReporteMundialPolla.prototype._formatNR = function(data, type, full) {
    return data != null ? data : '-';
};