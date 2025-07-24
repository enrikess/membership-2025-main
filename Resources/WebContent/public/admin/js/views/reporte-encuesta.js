function ReporteEncuesta(uri) {
    this.uri = uri;
    this.rangoDatePicker = $('#rangoDatePicker');
    this.grid = $('#gridEncuestas');
    this.btnFiltrar = $('#btnFiltrar');
    this.fechaInicio = $('#fechaInicio');
    this.fechaFin = $('#fechaFin');
    this.btnDescargar = $('#btnDescargar');
}

ReporteEncuesta.prototype.init = function() {
    this.handle();
    this.listarResultados();
};

ReporteEncuesta.prototype.handle = function() {
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


ReporteEncuesta.prototype.listarResultados = function(){
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
            "url" : obj.uri + 'reportes/encuesta/listar-detalles',
            "type": "POST",
            "data" : function(d) {
                d.finicio = obj.fechaInicio.data('datepicker').getFormattedDate('yyyy-mm-dd');
                d.ffin = obj.fechaFin.data('datepicker').getFormattedDate('yyyy-mm-dd');
            }
        },
        "columns" : [
            {"data" : "encuesta.participante.idParticipante", "orderable": false},
            {"data" : "encuesta.participante.nroDocumento", "orderable": false},
            {"data" : "encuesta.idEncuesta", "orderable": false},
            {"data" : "encuesta.nombreTipoEncuesta", "orderable": false},
            {"data" : "encuesta.pedido.idPedido", "orderable": false},
            {"data" : "auditoria.fechaCreacionString", "orderable": false},
            {"data" : "pregunta", "orderable": false},
            {"data" : "respuesta", "orderable": false},

        ],
        "lengthMenu": [[10, 15, 20], [10, 15, 20]],
        "initComplete":function(settings,json){

        },
        "fnDrawCallback": function(){

        }
    });
};

ReporteEncuesta.prototype.gridReload = function(){
    this.grid.DataTable().ajax.reload();
};
