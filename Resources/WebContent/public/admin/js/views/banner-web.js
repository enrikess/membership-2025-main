var uriGeneral;
var urlImagenBanner = "";
function BannerWeb(uri, uriImagenes) {
    this.uri = uri;
    uriGeneral = uri;
    urlImagenBanner = uriImagenes;
    this.gridHome = $('#gridBannersHome');
    this.gridProductos = $('#gridBannersProductos');
    this.slcOrdenHome = $('#slcOrdenHome');
    this.slcOrdenProductos = $('#slcOrdenProductos');
    this.btnAccion = $('.btnAccion');
    this.alert = null;
}

BannerWeb.prototype.init = function() {
    this.handle();
    this.listarBannersHome();
    this.listarBannersProductos();
};

BannerWeb.prototype.handle = function() {
    var obj = this;

    obj.slcOrdenHome.select2({
        minimumResultsForSearch: -1
    });

    obj.slcOrdenHome.on('change', function() {
        obj.gridReloadHome();
    });

    obj.slcOrdenProductos.select2({
        minimumResultsForSearch: -1
    });

    obj.slcOrdenProductos.on('change', function() {
        obj.gridReloadProductos();
    });

    obj.btnAccion.on('click', function(e) {
        e.preventDefault();
        window.location.href = $(this).attr('data-url');
    });

    $(document).on('click', '.onoffswitch', function(e) {

        var $checkbox = $(this).parent().find('input[type=checkbox]');
        var checked = $checkbox.is(':checked');
        var idConfiguracionBanner = $checkbox.attr('data-id');

        var estado = 'activar';
        if (checked) {
            estado = 'desactivar';
        }
        swal({
            title: $.camelCase("-" + estado),
            text: 'Estas seguro de ' + estado + ' este banner ? ',
            type: "warning",
            showCancelButton: true,
            confirmButtonColor: "#DD6B55",
            confirmButtonText: 'Si, ' + estado + '!',
            cancelButtonText: "No, cancelar!",
            closeOnConfirm: false,
            closeOnCancel: true
            },
            function (isConfirm) {
                if (isConfirm){
                    obj.actualizarEstado(idConfiguracionBanner, checked, $checkbox);
                }
            }
        );
    });
};

BannerWeb.prototype.actualizarEstado = function(idConfiguracionBanner, checked, $checkbox) {
    var obj = this;

    var request = {
        idConfiguracionBanner : idConfiguracionBanner,
        estadoConfiguracionBanner : checked ? 1 : 0
    };

    var $button = document.querySelector( 'button.confirm');
    var loader = null;

    Promotick.ajax.post({
        url : obj.uri + 'administracion/banners-web/actualizar-estado',
        data : JSON.stringify(request),
        messageError : true,
        messageTitle : 'Actualizacion Estado',
        before : function() {
            loader = Ladda.create($button);
            loader.start();
        },
        success : function(response) {
            if (response.status) {
                Promotick.toast.success(response.message, 'Actualizacion Estado');
                $checkbox.prop('checked', !checked);
            }
        },
        complete : function() {
            loader.stop();
            swal.close();
        }
    });
};

BannerWeb.prototype.listarBannersHome = function(){
    var obj = this;
    obj.gridHome.DataTable({
        "bProcessing" : true,
        "bFilter" : false,
        "bLengthChange": false,
        "bPaginate": false,
        "bInfo" : true,
        "serverSide" : true,
        "bSort": false,
        "ajax": {
            "url" : obj.uri + 'administracion/banners-web/listar/',
            "type": "POST",
            "data" : function(d) {
                d.tipoBanner = 1; //banner home
                d.orden = obj.slcOrdenHome.val();
            }
        },
        "columns" : [
            {"data" : "idConfiguracionBanner", "orderable": false, "render" : this._formatNombre},
            {"data" : "catalogo.nombreCatalogo", "orderable": false},
            {"data" : "textoBoton", "orderable": false},
            {"data" : "imagenBanner", "orderable": false, "sClass" : "text-center", "render" : this._formatImagen},
            {"data" : "urlBoton", "orderable": false, "render" : this._formatUrl},
            // {"data" : "textoUno", "orderable": false, "render" : this._formatTexto},
            // {"data" : "textoDos", "orderable": false, "render" : this._formatTexto},
            // {"data" : "textoBoton", "orderable": false, "render" : this._formatBoton},
            {"data" : "orden", "orderable": false},
            {"data" : "estadoConfiguracionBanner", "orderable": false,"class":"estado", "render" : obj._formatEstadoSwitch}
        ],
        "lengthMenu": [[5, 10, 20], [5, 10, 20]],
        "initComplete":function(settings,json){
        },
        "fnDrawCallback": function(){

        }
    });
};

BannerWeb.prototype.listarBannersProductos = function(){
    var obj = this;
    obj.gridProductos.DataTable({
        "bProcessing" : true,
        "bFilter" : false,
        "bLengthChange": false,
        "bPaginate": false,
        "bInfo" : true,
        "serverSide" : true,
        "bSort": false,
        "ajax": {
            "url" : obj.uri + 'administracion/banners-web/listar/',
            "type": "POST",
            "data" : function(d) {
                d.tipoBanner = 0;
                d.orden = obj.slcOrdenProductos.val();
            }
        },
        "columns" : [
            {"data" : "idConfiguracionBanner", "orderable": false, "render" : this._formatNombre},
            {"data" : "catalogo.nombreCatalogo", "orderable": false},
            {"data" : "textoBoton", "orderable": false},
            {"data" : "imagenBanner", "orderable": false, "sClass" : "text-center", "render" : this._formatImagen},
            {"data" : "urlBoton", "orderable": false, "render" : this._formatUrl},
            // {"data" : "textoUno", "orderable": false, "render" : this._formatTexto},
            // {"data" : "textoDos", "orderable": false, "render" : this._formatTexto},
            // {"data" : "textoBoton", "orderable": false, "render" : this._formatBoton},
            {"data" : "orden", "orderable": false},
            {"data" : "estadoConfiguracionBanner", "orderable": false,"class":"estado", "render" : obj._formatEstadoSwitch}
        ],
        "lengthMenu": [[5, 10, 20], [5, 10, 20]],
        "initComplete":function(settings,json){
        },
        "fnDrawCallback": function(){

        }
    });
};

BannerWeb.prototype._formatUrl = function(data, type, full) {
    if (data) {
        return "/" + data;
    }
    return '---';
};

BannerWeb.prototype._formatTexto = function(data, type, full) {
    if (data) {
        return data;
    }
    return '---';
};

BannerWeb.prototype._formatBoton = function(data, type, full) {
    var obj = this;
    if (data) {
        return "SI";
    }
    return 'NO';
};

BannerWeb.prototype._formatNombre = function(data, type, full) {
    var obj = this;
    return '<a href="' + uriGeneral + 'administracion/banners-web/editar/' + full.idConfiguracionBanner + '"><u>' + data + '</u></a>';
};

BannerWeb.prototype._formatImagen = function(data, type, full) {
    var obj = this;
    if (data != null && data != '') {
        return '<a class="image-popup" href="' +  urlImagenBanner + data + '"><img src="' +  urlImagenBanner + data + '" height="60px"/></a>';
    }
    return 'Sin imagen';
};

BannerWeb.prototype._formatEstadoSwitch = function(data, type, full) {
    var estado = '';
    var checked= '';

    if(data === 1){
        checked = 'checked';
    }
    estado += '<div class="switch">';
    estado +=   '<div class="onoffswitch">';
    estado +=       '<input type="checkbox" ' + checked + ' disabled class="onoffswitch-checkbox" id="s-' + full.idConfiguracionBanner + '" data-id="' + full.idConfiguracionBanner + '">';
    estado +=       '<label class="onoffswitch-label" for="s-' + full.idConfiguracionBanner + '">';
    estado +=           '<span class="onoffswitch-inner"></span>';
    estado +=           '<span class="onoffswitch-switch"></span>';
    estado +=       '</label>';
    estado +=   '</div>';
    estado += '</div>';

    return estado;
};

BannerWeb.prototype.gridReloadHome = function(){
    this.gridHome.DataTable().ajax.reload();
};

BannerWeb.prototype.gridReloadProductos = function(){
    this.gridProductos.DataTable().ajax.reload();
};
