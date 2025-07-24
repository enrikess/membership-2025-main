function ReporteMundialEC(uri) {
    this.uri = uri;
    this.grid = $('#gridMundialEc');
    this.rangoDatePicker = $('#rangoDatePicker');
    this.btnFiltrar = $('#btnFiltrar');
    this.fechaInicio = $('#fechaInicio');
    this.fechaFin = $('#fechaFin');
    this.btnDescargar = $('#btnDescargar');
}

ReporteMundialEC.prototype.init = function() {
    this.handle();
    this.obtenerEstadoCuenta();
};

ReporteMundialEC.prototype.handle = function() {
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

ReporteMundialEC.prototype.gridReload = function(){
    this.grid.DataTable().ajax.reload();
};


ReporteMundialEC.prototype.obtenerColumnas = function(callback) {
    var obj = this;

    Promotick.ajax.post({
        url : obj.uri + 'reportes/mundial-ec/obtenerColumnas',
        data : JSON.stringify({
            fechaInicio : obj.fechaInicio.data('datepicker').getFormattedDate('yyyy-mm-dd'),
            fechaFin : obj.fechaFin.data('datepicker').getFormattedDate('yyyy-mm-dd')
        }),
        messageError : true,
        messageTitle : 'Reporte estado de cuenta',
        success : function(response) {
            if (response.status) {
                $.each(response.data,function(i,e){
                    $("#gridMundialEc > thead > tr").append('<th>' + e.data + '</th>');
                });

                callback(response.data);
            }
        }
    });

};

ReporteMundialEC.prototype.obtenerEstadoCuenta = function() {
    var obj = this;
    obj.obtenerColumnas(function(response) {
        obj.listarEstadoCuenta(response);
    });
};

ReporteMundialEC.prototype.recontruirEstadoCuenta = function() {
    var obj = this;
    obj.grid.DataTable().destroy();

    $('#gridMundialEc_paginate').remove();
    $("#gridMundialEc > thead > tr").empty();
    $("#gridMundialEc > tbody > tr").empty();

    obj.obtenerEstadoCuenta();
};

ReporteMundialEC.prototype.listarEstadoCuenta = function(columnas) {
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
            "url" : obj.uri + 'reportes/mundial-ec/listar',
            "type": "POST",
            "data" : function(d) {
                d.fechaInicio = obj.fechaInicio.data('datepicker').getFormattedDate('yyyy-mm-dd');
                d.fechaFin = obj.fechaFin.data('datepicker').getFormattedDate('yyyy-mm-dd');
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