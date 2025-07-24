var uriGeneral;
function ProductosDestacados(uri) {
    this.uri = uri;
    uriGeneral = uri;
    this.grid = $('#gridProductosDestacados');
    this.modalBuscarProducto = $('#modalBuscarProducto');
    this.slcCatalogo = $('#slcCatalogo');
    this.btnRegistrarProdDest = $('#btnRegistrarProdDest');
    this.panelProductos = $('#panelProductos');
    this.frmBuscarProducto = $('#frmBuscarProducto');
    this.btnFiltrarProducto = $('#btnFiltrarProducto');
    this.gridProductoCatalogo = $('#gridProductoCatalogo');
    this.filtroProducto = $('#filtroProducto');
}

ProductosDestacados.prototype.init = function() {
    this.handle();
    this.listarProductosDestacados();
    this.listarBusquedaProductos();
};

ProductosDestacados.prototype.handle = function() {
    var obj = this;

    obj.grid.on('click', 'label.removeProductoDestacado', function(e) {
        e.preventDefault();

        var data = obj.grid.dataTable().fnGetData($(this).parents('tr'));

        swal({
            title: 'Eliminacion de producto destacado',
            text: 'Estas seguro de eliminar a este producto destacado ?',
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
                    obj.eliminarProductoDestacado(data.idProductoDestacado);
                }
            }
        );
    });

    obj.gridProductoCatalogo.on('click', 'label.addProductoDestacado', function(e) {
        var data = obj.gridProductoCatalogo.dataTable().fnGetData($(this).parents('tr'));
        obj.agregarProductoDestacado(data);
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

ProductosDestacados.prototype.agregarProductoDestacado = function(productoDestacado){
    var obj = this;

    Promotick.ajax.post({
        url : obj.uri + 'catalogos/producto-destacado/registrar',
        data: JSON.stringify(productoDestacado),
        messageError : true,
        messageTitle : 'Agregar producto destacado',
        before : function() {
            obj.modalBuscarProducto.find('.modal-body').LoadingOverlay('show');
        },
        success : function(response) {
            if (response.status) {
                Promotick.toast.success(response.message, 'Agregar producto destacado');
                obj.modalBuscarProducto.modal('hide');
                obj.gridReload();
            }
        },
        complete : function() {
            obj.modalBuscarProducto.find('.modal-body').LoadingOverlay('hide', true);
        }
    });
};

ProductosDestacados.prototype.eliminarProductoDestacado = function(idProductoDestacado) {
    var obj = this;

    var $button = document.querySelector( 'button.confirm');
    var loader = null;

    Promotick.ajax.get({
        url : obj.uri + 'catalogos/producto-destacado/eliminar/' + idProductoDestacado,
        messageError : true,
        messageTitle : 'Eliminacion de producto destacado',
        before : function() {
            loader = Ladda.create($button);
            loader.start();
        },
        success : function(response) {
            if (response.status) {
                Promotick.toast.success(response.message, 'Eliminacion de producto destacado');
                obj.gridReload();
            }
        },
        complete : function() {
            loader.stop();
            swal.close();
        }
    });
};

ProductosDestacados.prototype.listarProductosDestacados = function(){
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
            "url" : obj.uri + 'catalogos/producto-destacado/listar-productos-destacados',
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
            {"data" :  null, "orderable": false, "render" :this.accionEliminarProductoDestacado, "sClass": "text-center"}
        ],
        "lengthMenu": [[10, 15, 20], [10, 15, 20]],
        "initComplete":function(settings,json){
        },
        "fnDrawCallback": function(){

        }
    });
};

ProductosDestacados.prototype.listarBusquedaProductos = function(){
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
            "url" : this.uri + 'catalogos/producto-destacado/buscar-producto-catalogo',
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

ProductosDestacados.prototype.accionEliminarProductoDestacado = function(data, type, full) {
    return '<label class="btn btn-danger btn-xs removeProductoDestacado" style="margin: 0"><i class="fa fa-remove"></i></label>';
};

ProductosDestacados.prototype.accionAsociar = function(data, type, full) {
    return '<label class="btn btn-success btn-xs addProductoDestacado" style="margin: 0"><i class="fa fa-plus"></i></label>';
};


ProductosDestacados.prototype.gridReload = function(){
    this.grid.DataTable().ajax.reload();
};

ProductosDestacados.prototype.gridProductoCatalogoReload = function(){
    this.gridProductoCatalogo.DataTable().ajax.reload();
};
