var uriGeneral;
function ProductosPromocion(uri) {
    this.uri = uri;
    uriGeneral = uri;
    this.grid = $('#gridProductosPromocion');
    this.modalBuscarProducto = $('#modalBuscarProducto');
    this.slcCatalogo = $('#slcCatalogo');
    this.btnRegistrarProdDest = $('#btnRegistrarProdDest');
    this.panelProductos = $('#panelProductos');
    this.frmBuscarProducto = $('#frmBuscarProducto');
    this.btnFiltrarProducto = $('#btnFiltrarProducto');
    this.gridProductoCatalogo = $('#gridProductoCatalogo');
    this.filtroProducto = $('#filtroProducto');
}

ProductosPromocion.prototype.init = function() {
    this.handle();
    this.listarProductosPromocion();
    this.listarBusquedaProductos();
};

ProductosPromocion.prototype.handle = function() {
    var obj = this;

    obj.grid.on('click', 'label.removeProductoPromocion', function(e) {
        e.preventDefault();

        var data = obj.grid.dataTable().fnGetData($(this).parents('tr'));

        swal({
            title: 'Eliminacion de producto promocion',
            text: 'Estas seguro de eliminar a este producto promocion ?',
            type: "warning",
            showCancelButton: true,
            confirmButtonColor: "#DD6B55",
            confirmButtonText: 'Si, eliminar!',
            cancelButtonText: "No, cancelar!",
            closeOnConfirm: false,
            closeOnCancel: true
            },
            function (isConfirm) {
                if (isConfirm){
                    obj.eliminarProductoPromocion(data.idProductoPromocion);
                }
            }
        );
    });

    obj.gridProductoCatalogo.on('click', 'label.addProductoPromocion', function(e) {
        var data = obj.gridProductoCatalogo.dataTable().fnGetData($(this).parents('tr'));
        obj.agregarProductoPromocion(data);
    });

    obj.slcCatalogo.select2({
        dropdownParent: $('#modalBuscarProducto .modal-content'),
        dropdownAutoWidth : true,
        width: '100%',
        placeholder: "Seleccionar",
        minimumResultsForSearch: -1
    });

    obj.btnRegistrarProdDest.on('click', function(e) {
        e.preventDefault();
        obj.modalBuscarProducto.modal();
    });

    obj.frmBuscarProducto.validate({
        rules : {
            slcCatalogo : {
                required : true,
                min : 1
            }
        },
        messages : {
            slcCatalogo : {
                required : 'Seleccione un catalogo',
                min : 'Seleccione un catalogo'
            }
        }
    });

    obj.btnFiltrarProducto.on('click', function(e) {
        e.preventDefault();
        if (obj.frmBuscarProducto.valid()) {
            obj.gridProductoCatalogoReload();
            obj.panelProductos.show();
        } else {
            obj.frmBuscarProducto.validate().focusInvalid();
        }
    });

    obj.modalBuscarProducto.on('hidden.bs.modal', function () {
        obj.frmBuscarProducto[0].reset();
        obj.slcCatalogo.select2("val", "");
        obj.gridProductoCatalogoReload();
        obj.panelProductos.hide();
    });
};

ProductosPromocion.prototype.agregarProductoPromocion = function(productoPromocion){
    var obj = this;

    Promotick.ajax.post({
        url : obj.uri + 'catalogos/producto-promocion/registrar',
        data: JSON.stringify(productoPromocion),
        messageError : true,
        messageTitle : 'Agregar producto promocion',
        before : function() {
            obj.modalBuscarProducto.find('.modal-body').LoadingOverlay('show');
        },
        success : function(response) {
            if (response.status) {
                Promotick.toast.success(response.message, 'Agregar producto promocion');
                obj.modalBuscarProducto.modal('hide');
                obj.gridReload();
            }
        },
        complete : function() {
            obj.modalBuscarProducto.find('.modal-body').LoadingOverlay('hide', true);
        }
    });
};

ProductosPromocion.prototype.eliminarProductoPromocion = function(idProductoPromocion) {
    var obj = this;

    var $button = document.querySelector( 'button.confirm');
    var loader = null;

    Promotick.ajax.get({
        url : obj.uri + 'catalogos/producto-promocion/eliminar/' + idProductoPromocion,
        messageError : true,
        messageTitle : 'Eliminacion de producto promocion',
        before : function() {
            loader = Ladda.create($button);
            loader.start();
        },
        success : function(response) {
            if (response.status) {
                Promotick.toast.success(response.message, 'Eliminacion de producto promocion');
                obj.gridReload();
            }
        },
        complete : function() {
            loader.stop();
            swal.close();
        }
    });
};

ProductosPromocion.prototype.listarProductosPromocion = function(){
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
            "url" : obj.uri + 'catalogos/producto-promocion/listar-productos-promocion',
            "type": "POST",
            "data" : function(d) {

            }
        },
        "columns" : [
            {"data" : "producto.codigoWeb", "orderable": false},
            {"data" : "producto.nombreProducto", "orderable": false},
            {"data" : "producto.nombreCatalogo", "orderable": false},
            {"data" : "producto.categoria.nombreCategoria", "orderable": false},
            {"data" : "producto.puntosProducto", "orderable": false},
            {"data" :  null, "orderable": false, "render" :this.accionEliminarProductoPromocion, "sClass": "text-center"}
        ],
        "lengthMenu": [[10, 15, 20], [10, 15, 20]],
        "initComplete":function(settings,json){
        },
        "fnDrawCallback": function(){

        }
    });
};

ProductosPromocion.prototype.listarBusquedaProductos = function(){
    var obj = this;
    this.gridProductoCatalogo.dataTable({
        "bProcessing" : true,
        "bFilter" : false,
        "bLengthChange": false,
        "bPaginate": true,
        "bInfo" : false,
        "serverSide" : true,
        "bSort": false,
        "ajax": {
            "url" : this.uri + 'catalogos/producto-promocion/buscar-producto-catalogo',
            "type": "POST",
            "data" : function(d) {
                d.idCatalogo = obj.slcCatalogo.val();
                d.nombreProducto = obj.filtroProducto.val();
            }
        },
        "columns" : [
            {"data" : "producto.codigoWeb", "orderable": false},
            {"data" : "producto.nombreProducto", "orderable": false},
            {"data" : "producto.nombreCatalogo", "orderable": false},
            {"data" : "producto.categoria.nombreCategoria", "orderable": false},
            {"data" :  null, "orderable": false, "render" :this.accionAsociar, "sClass": "text-center"}

        ],

        "lengthMenu": [[5, 10, 20], [5, 10, 20]],
        "initComplete":function(settings,json){
        },
        "fnDrawCallback": function(){
        }
    });
};

ProductosPromocion.prototype.accionEliminarProductoPromocion = function(data, type, full) {
    return '<label class="btn btn-danger btn-xs removeProductoPromocion" style="margin: 0"><i class="fa fa-remove"></i></label>';
};

ProductosPromocion.prototype.accionAsociar = function(data, type, full) {
    return '<label class="btn btn-success btn-xs addProductoPromocion" style="margin: 0"><i class="fa fa-plus"></i></label>';
};


ProductosPromocion.prototype.gridReload = function(){
    this.grid.DataTable().ajax.reload();
};

ProductosPromocion.prototype.gridProductoCatalogoReload = function(){
    this.gridProductoCatalogo.DataTable().ajax.reload();
};
