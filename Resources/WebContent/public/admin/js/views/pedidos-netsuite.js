var uriGeneral;
function PedidosNetsuite(uri) {
    this.uri = uri;
    uriGeneral = uri;
    this.grid = $('#gridPedidos');
    this.btnFiltrar = $('#btnFiltrar');
    this.buscarPedido = $('#buscarPedido');
    this.slcOrden = $('#slcOrden');
}

PedidosNetsuite.prototype.init = function() {
    this.handle();
    this.listarPedidos();
};

PedidosNetsuite.prototype.handle = function() {
    var obj = this;

    obj.slcOrden.select2({
        minimumResultsForSearch: -1
    });

    obj.btnFiltrar.on('click', function(e){
        e.preventDefault();
        obj.gridReload();
    });

    obj.slcOrden.on('change', function() {
        obj.gridReload();
    });

    obj.grid.on('click', 'label.btn-reenviar', function(e) {

        var idPedido = $(this).attr('data-id');

        swal({
            title: 'Reenvio a netsuite',
            text: 'Estas seguro de reenviar este pedido al tomador de netsuite ?',
            type: "warning",
            showCancelButton: true,
            confirmButtonColor: "#DD6B55",
            confirmButtonText: 'Si, enviar!',
            cancelButtonText: "No, cancelar!",
            closeOnConfirm: false,
            closeOnCancel: true
            },
            function (isConfirm) {
                if (isConfirm){
                    obj.enviarNetsuite(idPedido);
                }
            }
        );
    });
};


PedidosNetsuite.prototype.enviarNetsuite = function(idPedido) {
    var obj = this;

    var $button = document.querySelector( 'button.confirm');
    var loader = null;

    Promotick.ajax.get({
        url : obj.uri + 'pedidos/netsuite/reenvioNetsuite/' + idPedido,
        messageError : true,
        messageTitle : 'Reenvio a netsuite',
        before : function() {
            loader = Ladda.create($button);
            loader.start();
        },
        success : function(response) {
            if (response.status) {
                Promotick.toast.success(response.message, 'Reenvio a netsuite');
                obj.gridReload();
            }
        },
        complete : function() {
            loader.stop();
            swal.close();
        }
    });
};

PedidosNetsuite.prototype.listarPedidos = function(){
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
            "url" : obj.uri + 'pedidos/netsuite/listarPedidosNetsuite',
            "type": "POST",
            "data" : function(d) {
                d.buscar = obj.buscarPedido.val();
                d.orden = obj.slcOrden.val();
            }
        },
        "columns" : [
            {"data" : "idPedido", "orderable": false, "render" : obj._formatID},
            {"data" : "nroDocumentoPedido", "orderable": false},
            {"data" : "nombrePedido", "orderable": false},
            {"data" : "apellidoPaternoPedido", "orderable": false},
            {"data" : "apellidoMaternoPedido", "orderable": false},
            {"data" : "emailPedido", "orderable": false},
            {"data" : "telefonoPedido", "orderable": false},
            {"data" : "movilPedido", "orderable": false},
            {"data" : "fechaPedidoString", "orderable": false},
            {"data" : "fechaEntregaString", "orderable": false},
            {"data" : "puntosTotal", "orderable": false, "render" : obj._formatPuntos},
            {"data" : null, "orderable": false,"class":"estado", "render" : obj._formatReenvio}
        ],
        "lengthMenu": [[10, 15, 20], [10, 15, 20]],
        "initComplete":function(settings,json){
        },
        "fnDrawCallback": function(){

        }
    });
};

PedidosNetsuite.prototype._formatID = function(data, type, full) {
    var obj = this;
    return '<a href="' + uriGeneral + 'pedidos/netsuite/' + full.idPedido + '"><u># ' + data + '</u></a>';
};

PedidosNetsuite.prototype._formatPuntos = function(data, type, full) {
    return data + ' Pts.';
};

PedidosNetsuite.prototype._formatReenvio = function(data, type, full) {
    return "<label class='label label-danger btn-reenviar' data-id='" + full.idPedido + "' style='cursor: pointer'>REENVIAR A NETSUITE</label>";
};

PedidosNetsuite.prototype.gridReload = function(){
    this.grid.DataTable().ajax.reload();
};
