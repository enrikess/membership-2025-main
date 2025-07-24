function ReportePedido(uri) {
    this.uri = uri;
    this.rangoDatePicker = $('#rangoDatePicker');
    this.grid = $('#gridPedidos');
    this.btnFiltrar = $('#btnFiltrar');
    this.fechaInicio = $('#fechaInicio');
    this.fechaFin = $('#fechaFin');
    this.btnDescargar = $('#btnDescargar');
    this.detalleVisible = false;
}

ReportePedido.prototype.init = function() {
    this.handle();
    this.listarPedidos();
};

ReportePedido.prototype.handle = function() {
    var obj = this;

    obj.rangoDatePicker.datepicker({
        endDate: "0m"
    });

    obj.btnFiltrar.on('click', function(e){
        e.preventDefault();
        obj.gridReload();
    });

    obj.grid.on('click','a.ver-detalle', function (e){
        e.preventDefault();
        obj.grid.DataTable().rows().every(function() {
            if (this.child.isShown()) {
                this.child.hide();
            }
        });

        if (!obj.detalleVisible) {
            var $tr = $(this).closest('tr');
            var row = obj.grid.DataTable().row($tr);
            if (row.child.isShown()) {
                row.child.hide();
                $tr.removeClass('shown');
            } else {
                var data = obj.grid.dataTable().fnGetData($(this).parents('tr'));
                obj.obtenerPedidoDetalle(data.idPedido, row, $tr);
                $tr.addClass('shown');
            }
        } else {
            obj.detalleVisible = false;
        }

    });

    obj.btnDescargar.on('click', function(e) {
        e.preventDefault();
        window.location.href = $(this).attr('data-descarga');
    })

};

ReportePedido.prototype.obtenerPedidoDetalle = function(idPedido, $row, $tr) {
    var obj = this;

    Promotick.render.get({
        url : obj.uri + 'reportes/pedidos/viewPedidoDetalleList/' + idPedido,
        loader : $tr,
        success : function(response) {
            $row.child(response).show();
        },
        complete : function() {
            var $ro = $('#gridPedidos_wrapper').find('table#gridPedidos>tbody').children().eq($row.index() + 1);
            $ro.addClass('custom');
            $ro.find('td').css('padding','15px');
            obj.detalleVisible = true;
        }
    });
};

ReportePedido.prototype.listarPedidos = function(){
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
            "url" : obj.uri + 'reportes/pedidos/listar-pedidos',
            "type": "POST",
            "data" : function(d) {
                d.finicio = obj.fechaInicio.data('datepicker').getFormattedDate('yyyy-mm-dd');
                d.ffin = obj.fechaFin.data('datepicker').getFormattedDate('yyyy-mm-dd');
            }
        },
        "columns" : [
            {"data" : "participante.catalogo.nombreCatalogo", "orderable": false},
            {"data" : "idPedido", "orderable": false, "sClass": "text-center"},
            {"data" : "participante.nroDocumento", "orderable": false},
            {"data" : "participante.nombresParticipante", "orderable": false},
            {"data" : null, "orderable": false, "render": obj._formatApellidos},
            {"data" : "movilPedido", "orderable": false},
            {"data" : "fechaPedidoString", "orderable": false},
            {"data" : "fechaEntregaString", "orderable": false},
            {"data" : "puntosTotal", "orderable": false},
            {"data" :  null, "orderable": false, "render" :obj._formatVerDetalle, "sClass": "text-center"}

        ],
        "lengthMenu": [[10, 15, 20], [10, 15, 20]],
        "initComplete":function(settings,json){

        },
        "fnDrawCallback": function(){

        }
    });
};

ReportePedido.prototype.gridReload = function(){
    this.grid.DataTable().ajax.reload();
};

ReportePedido.prototype._formatVerDetalle = function (data, type, full) {
    return '<a href="#" class="ver-detalle"><i class="fa fa-search" aria-hidden="true"></i></a>';
};


ReportePedido.prototype._formatApellidos = function(data, type, full) {
    var apellidos = '';
    if(full.participante.appaternoParticipante != null){
        apellidos = full.participante.appaternoParticipante;
    }
    if(full.participante.apmaternoParticipante != null) {
        apellidos += ' '+ full.participante.apmaternoParticipante;
    }
    return apellidos;
};