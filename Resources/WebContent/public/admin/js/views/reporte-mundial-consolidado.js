function ReporteMundialConsolidado(uri) {
    this.uri = uri;
    this.rangoDatePicker = $('#rangoDatePicker');
    this.grid = $('#grid');
    this.btnFiltrar = $('#btnFiltrar');
    this.fechaInicio = $('#fechaInicio');
    this.fechaFin = $('#fechaFin');
    this.btnDescargar = $('#btnDescargar');
    this.inptBuscar = $('#buscar');
    this.btnBuscar = $('#btnBuscar');
}

ReporteMundialConsolidado.prototype.init = function() {
    this.handle();
    this.listar();
};

ReporteMundialConsolidado.prototype.handle = function() {
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

ReporteMundialConsolidado.prototype.listar = function(){
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
            "url" : obj.uri + 'reportes/mundial-consolidado/listar',
            "type": "POST",
            "data" : function(d) {
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
            {"data" : "polla", "orderable": false, "render": this._formatNull},
            {"data" : "puntosPolla", "orderable": false, "render": this._formatNull},
            {"data" : "triviasJugadas", "orderable": false, "render": this._formatNull},
            {"data" : "triviasCorrectas", "orderable": false, "render": this._formatNull},
            {"data" : "puntosTrivia", "orderable": false, "render": this._formatNull},
            {"data" : "pronosticosJugados", "orderable": false, "render": this._formatNull},
            {"data" : "pronosticosGruposJugados", "orderable": false, "render": this._formatNull},
            {"data" : "pronosticosEtapasJugados", "orderable": false, "render": this._formatNull},
            {"data" : "puntosPronosticos", "orderable": false, "render": this._formatNull},
            {"data" : "categoriaParticipante", "orderable": false, "render": this._formatNull},
            {"data" : "puntosBono", "orderable": false, "render": this._formatNull},
            {"data" : "puntosTotal", "orderable": false, "render": this._formatNull},


        ],
        "lengthMenu": [[10, 15, 20], [10, 15, 20]],
        "initComplete":function(settings,json){

        },
        "fnDrawCallback": function(){

        }
    });
};

ReporteMundialConsolidado.prototype.gridReload = function(){
    this.grid.DataTable().ajax.reload();
};

ReporteMundialConsolidado.prototype._formatArchivo = function (data, type, full) {
    return '<a href="' + data + '" target="_blank" class="btn btn-sm btn-success"><i class="fa fa-download" aria-hidden="true"></i> Descargar</a>';
};

ReporteMundialConsolidado.prototype._formatNull = function(data, type, full) {
    return data != null ? data : '-';
};