function ReportePuntosAcreditados(uri) {
    this.uri = uri;
    this.rangoDatePicker = $('#rangoDatePicker');
    this.grid = $('#gridPuntosAcreditados');
    this.btnFiltrar = $('#btnFiltrar');
    this.fechaInicio = $('#fechaInicio');
    this.fechaFin = $('#fechaFin');
    this.btnDescargar = $('#btnDescargar');
}

ReportePuntosAcreditados.prototype.init = function() {
    this.handle();
    this.listarPuntosAcreditados();
};

ReportePuntosAcreditados.prototype.handle = function() {
    var obj = this;

    obj.rangoDatePicker.datepicker({
        endDate: "0m"
    });

    obj.btnFiltrar.on('click', function(e){
        e.preventDefault();
        obj.gridReload();
    });

    obj.btnDescargar.on('click', function(e) {
        e.preventDefault();
        window.location.href = $(this).attr('data-descarga');
    })

};


ReportePuntosAcreditados.prototype.listarPuntosAcreditados = function(){
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
            "url" : obj.uri + 'reportes/puntos-acreditados/listar-puntos-acreditados',
            "type": "POST",
            "data" : function(d) {
                d.finicio = obj.fechaInicio.data('datepicker').getFormattedDate('yyyy-mm-dd');
                d.ffin = obj.fechaFin.data('datepicker').getFormattedDate('yyyy-mm-dd');
            }
        },
        "columns" : [
            {"data" : "participante.nombresParticipante", "orderable": false},
            {"data" : "participante.appaternoParticipante", "orderable": false},
            {"data" : "participante.apmaternoParticipante", "orderable": false},
            {"data" : "participante.nroDocumento", "orderable": false},
            {"data" : "tipoFactura.nombreTipoFactura", "orderable": false},
            {"data" : "nroFactura", "orderable": false},
            {"data" : "fechaEmisionString", "orderable": false},
            {"data" : "puntosAcreditados", "orderable": false}
        ],
        "lengthMenu": [[10, 15, 20], [10, 15, 20]],
        "initComplete":function(settings,json){

        },
        "fnDrawCallback": function(){

        }
    });
};

ReportePuntosAcreditados.prototype.gridReload = function(){
    this.grid.DataTable().ajax.reload();
};
