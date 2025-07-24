function ReporteRecomendado(uri){
    this.uri = uri;
    this.rangoDatePicker = $('#rangoDatePicker');
    this.grid = $('#gridRecomendados');
    this.btnFiltrar = $('#btnFiltrar');
    this.fechaInicio = $('#fechaInicio');
    this.fechaFin = $('#fechaFin');
    this.btnDescargar = $('#btnDescargar');
    this.btnAccion = $('.btnAccion');
}

ReporteRecomendado.prototype.init = function() {
    this.handle();
    this.listarRecomendados()
};

ReporteRecomendado.prototype.handle = function(){
    var obj = this;

    obj.rangoDatePicker.datepicker({
        endDate: "0m"
    });

    obj.btnFiltrar.on('click', function(e){
        e.preventDefault();
        obj.gridReload();

    });

    obj.btnDescargar.on('click', function(e){
       e.preventDefault();
       window.location.href = $(this).attr('data-descarga');
    });

    obj.btnAccion.on('click', function(e) {
        e.preventDefault();
        window.location.href = $(this).attr('data-url');
    });

}

ReporteRecomendado.prototype.listarRecomendados = function () {
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
            "url" : obj.uri + 'reportes/recomendados/listar-recomendados',
            "type": "POST",
            "data" : function (d) {
                d.finicio = obj.fechaInicio.data('datepicker').getFormattedDate('yyyy-mm-dd');
                d.ffin = obj.fechaFin.data('datepicker').getFormattedDate('yyyy-mm-dd');
            }
        },
        "columns" : [
            {"data" : "idRecomendado", "orderable": false, "sClass" : "text-center"},
            {"data" : "participante.nroDocumento", "orderable": false},
            {"data" : "participante.nombresParticipante", "orderable": false},
            {"data" : null, "orderable": false, "render": obj._formatApellidos},
            {"data" : "participante.emailParticipante", "orderable": false},
            {"data" : "nroDocumentoRecomendado", "orderable": false},
            {"data" : "nombresRecomendado", "orderable": false},
            {"data" : "apellidosRecomendado", "orderable": false},
            {"data" : "celularRecomendado", "orderable": false},
            {"data" : "emailRecomendado", "orderable": false},
            {"data" : "tiempoCompra", "orderable": false},
            {"data" : "financiamiento", "orderable": false},
            {"data" : "observacionRecomendado", "orderable": false},
            {"data" : "fechaRegistroString", "orderable": false},
            {"data" : "estadoRecomendadoString", "orderable": false}

        ],

        "lengthMenu": [[10, 15, 20], [10, 15, 20]],
        "initComplete":function(settings,json){

        },
        "fnDrawCallback": function(){

        }

    });
};

ReporteRecomendado.prototype.gridReload = function (){
    this.grid.DataTable().ajax.reload();
};

ReporteRecomendado.prototype._formatApellidos = function(data, type, full) {
    var apellidos = '';
    if(full.participante.appaternoParticipante != null){
        apellidos = full.participante.appaternoParticipante;
    }
    if(full.participante.apmaternoParticipante != null) {
        apellidos += ' '+ full.participante.apmaternoParticipante;
    }
    return apellidos;
};

