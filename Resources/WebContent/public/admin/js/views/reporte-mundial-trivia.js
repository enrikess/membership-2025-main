function ReporteMundialTrivia(uri) {
    this.uri = uri;
    this.rangoDatePicker = $('#rangoDatePicker');
    this.grid = $('#grid');
    this.btnFiltrar = $('#btnFiltrar');
    this.fechaInicio = $('#fechaInicio');
    this.fechaFin = $('#fechaFin');
    this.btnDescargar = $('#btnDescargar');
    this.inptBuscar = $('#buscar');
    this.btnBuscar = $('#btnBuscar');

    this.slcTrivia = $('#slcTrivia');
}

ReporteMundialTrivia.prototype.init = function() {
    this.handle();
    this.listar();
};

ReporteMundialTrivia.prototype.handle = function() {
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

    obj.slcTrivia.on('change', function() {
        obj.gridReload();
    });

    obj.btnDescargar.on('click', function(e) {
        e.preventDefault();
        window.location.href = $(this).attr('data-descarga');
    })

};

ReporteMundialTrivia.prototype.listar = function(){
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
            "url" : obj.uri + 'reportes/mundial-trivia/listar',
            "type": "POST",
            "data" : function(d) {
                d.nroTrivia = $('#slcTrivia').find(":selected").val();
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
            {"data" : "nombreTrivia", "orderable": false, "render": this._formatNull},
            {"data" : "completado", "orderable": false, "render": this._formatNull},
            {"data" : "respuesta1", "orderable": false, "render": this._formatNR},
            {"data" : "respuesta2", "orderable": false, "render": this._formatNR},
            {"data" : "respuesta3", "orderable": false, "render": this._formatNR},
            {"data" : "respuesta4", "orderable": false, "render": this._formatNR},
            {"data" : "respuesta5", "orderable": false, "render": this._formatNR},
            {"data" : "ganador", "orderable": false, "render": this._formatNull},


        ],
        "lengthMenu": [[10, 15, 20], [10, 15, 20]],
        "initComplete":function(settings,json){

        },
        "fnDrawCallback": function(){

        }
    });
};

ReporteMundialTrivia.prototype.gridReload = function(){
    this.grid.DataTable().ajax.reload();
};

ReporteMundialTrivia.prototype._formatArchivo = function (data, type, full) {
    return '<a href="' + data + '" target="_blank" class="btn btn-sm btn-success"><i class="fa fa-download" aria-hidden="true"></i> Descargar</a>';
};

ReporteMundialTrivia.prototype._formatNull = function(data, type, full) {
    return data != null ? data : '-';
};

ReporteMundialTrivia.prototype._formatNR = function(data, type, full) {
    return data != null ? data : '-';
};