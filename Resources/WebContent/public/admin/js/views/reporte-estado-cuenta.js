function ReporteEstadoCuenta(uri) {
    this.uri = uri;
    this.grid = $('#gridEstadoCuenta');
    this.rangoDatePicker = $('#rangoDatePicker');
    this.btnFiltrar = $('#btnFiltrar');
    this.fechaInicio = $('#fechaInicio');
    this.fechaFin = $('#fechaFin');
    this.btnDescargar = $('#btnDescargar');
}

ReporteEstadoCuenta.prototype.init = function() {
    this.handle();
    this.obtenerEstadoCuenta();
};

ReporteEstadoCuenta.prototype.handle = function() {
    var obj = this;

    obj.rangoDatePicker.datepicker({
        endDate: "0m"
    });

    obj.btnFiltrar.on('click', function(e){
        e.preventDefault();

        obj.recontruirEstadoCuenta();
    });

    obj.btnDescargar.on('click', function(e) {
        e.preventDefault();
        window.location.href = $(this).attr('data-descarga');
    })
};

ReporteEstadoCuenta.prototype.gridReload = function(){
    this.grid.DataTable().ajax.reload();
};


ReporteEstadoCuenta.prototype.obtenerColumnas = function(callback) {
    var obj = this;

    Promotick.ajax.post({
        url : obj.uri + 'reportes/estado-cuenta/obtenerColumnas',
        data : JSON.stringify({
            finicio : obj.fechaInicio.data('datepicker').getFormattedDate('yyyy-mm-dd'),
            ffin : obj.fechaFin.data('datepicker').getFormattedDate('yyyy-mm-dd')
        }),
        messageError : true,
        messageTitle : 'Reporte estado de cuenta',
        success : function(response) {
            if (response.status) {
                $.each(response.data,function(i,e){
                    $("#gridEstadoCuenta > thead > tr").append('<th>' + e.data + '</th>');
                });

                callback(response.data);
            }
        }
    });

};

ReporteEstadoCuenta.prototype.obtenerEstadoCuenta = function() {
    var obj = this;
    obj.obtenerColumnas(function(response) {
        obj.listarEstadoCuenta(response);
    });
};

ReporteEstadoCuenta.prototype.recontruirEstadoCuenta = function() {
    var obj = this;
    obj.grid.DataTable().destroy();

    $('#gridEstadoCuenta_paginate').remove();
    $("#gridEstadoCuenta > thead > tr").empty();
    $("#gridEstadoCuenta > tbody > tr").empty();

    obj.obtenerEstadoCuenta();
};

ReporteEstadoCuenta.prototype.listarEstadoCuenta = function(columnas) {
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
            "url" : obj.uri + 'reportes/estado-cuenta/listar',
            "type": "POST",
            "data" : function(d) {
                d.finicio = obj.fechaInicio.data('datepicker').getFormattedDate('yyyy-mm-dd');
                d.ffin = obj.fechaFin.data('datepicker').getFormattedDate('yyyy-mm-dd');
            }
        },
        "columns" : columnas,
        "lengthMenu": [[10, 15, 20], [10, 15, 20]],
        "initComplete":function(settings,json){
        },
        "fnDrawCallback": function(){
        }
    });
};