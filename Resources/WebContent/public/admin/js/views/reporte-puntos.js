function ReportePuntos(uri) {
    this.uri = uri;
    this.rangoDatePicker = $('#rangoDatePicker');
    this.grid = $('#gridPuntos');
    this.btnFiltrar = $('#btnFiltrar');
    this.fechaInicio = $('#fechaInicio');
    this.fechaFin = $('#fechaFin');
    this.btnDescargar = $('#btnDescargar');
    this.detalleVisible = false;
}

ReportePuntos.prototype.init = function() {
    this.handle();
    this.listarPuntos();
};

ReportePuntos.prototype.handle = function() {
    var obj = this;

    obj.btnDescargar.on('click', function(e) {
        e.preventDefault();
        window.location.href = $(this).attr('data-descarga');
    })

};

ReportePuntos.prototype.listarPuntos = function(){
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
            "url" : obj.uri + 'puntos/reporte/listar-transacciones',
            "type": "POST",
            "data" : function(d) {
            }
        },
        "columns" : [
            {"data" : "participante.nombresParticipante", "orderable": false},
            {"data" : "participante.appaternoParticipante", "orderable": false},
            {"data" : "participante.nroDocumento", "orderable": false},
            {"data" : "participante.genero", "orderable": false, "render": this._tipoSexo},
            {"data" : "participante.catalogo.nombreCatalogo", "orderable": false},
            {"data" : "fechaOperacionString", "orderable": false},
            {"data" : "descripcion", "orderable": false},
            {"data" : "valorPuntos", "orderable": false}
        ],
        "lengthMenu": [[10, 15, 20], [10, 15, 20]],
        "initComplete":function(settings,json){

        },
        "fnDrawCallback": function(){

        }
    });
};

ReportePuntos.prototype.gridReload = function(){
    this.grid.DataTable().ajax.reload();
};


ReportePuntos.prototype._tipoSexo = function (data, type, full) {
    var tipo = '';
    if (data == 'M') {
        tipo = 'Masculino';
    }else if(data == 'F'){
        tipo = 'Femenino';
    }
    return tipo;
};