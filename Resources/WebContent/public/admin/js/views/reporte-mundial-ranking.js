function ReporteMundialRanking(uri) {
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

ReporteMundialRanking.prototype.init = function() {
    this.handle();
    this.listar();
};

ReporteMundialRanking.prototype.handle = function() {
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

ReporteMundialRanking.prototype.listar = function(){
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
            "url" : obj.uri + 'reportes/mundial-ranking/listar',
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
            {"data" : "principiante", "orderable": false, "render": this._formatNull},
            {"data" : "aficionado", "orderable": false, "render": this._formatNull},
            {"data" : "semi", "orderable": false, "render": this._formatNull},
            {"data" : "pro", "orderable": false, "render": this._formatNull},
            {"data" : "mundial", "orderable": false, "render": this._formatNull},
            {"data" : "legendario", "orderable": false, "render": this._formatNull},
            {"data" : "puntosTotal", "orderable": false, "render": this._formatNull},


        ],
        "lengthMenu": [[10, 15, 20], [10, 15, 20]],
        "initComplete":function(settings,json){

        },
        "fnDrawCallback": function(){

        }
    });
};

ReporteMundialRanking.prototype.gridReload = function(){
    this.grid.DataTable().ajax.reload();
};

ReporteMundialRanking.prototype._formatArchivo = function (data, type, full) {
    return '<a href="' + data + '" target="_blank" class="btn btn-sm btn-success"><i class="fa fa-download" aria-hidden="true"></i> Descargar</a>';
};

ReporteMundialRanking.prototype._formatNull = function(data, type, full) {
    return data != null ? data : '-';
};