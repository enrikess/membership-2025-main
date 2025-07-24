var uriGeneral;
var urlImagenPopup = "";
function BannerFlotante(uri, uriImagenes) {
    this.uri = uri;
    uriGeneral = uri;
    urlImagenPopup = uriImagenes;
    this.grid = $('#gridSegmentos');
    this.slcOrden = $('#slcOrden');
    this.btnAccion = $('.btnAccion');
    this.alert = null;
}

BannerFlotante.prototype.init = function() {
    this.handle();
    this.listarSegmentosPopup();
};

BannerFlotante.prototype.handle = function() {
    var obj = this;

    obj.slcOrden.select2({
        minimumResultsForSearch: -1
    });

    obj.slcOrden.on('change', function() {
        obj.gridReload();
    });

    obj.btnAccion.on('click', function(e) {
        e.preventDefault();
        window.location.href = $(this).attr('data-url');
    });

    $(document).on('click', '.onoffswitch', function(e) {

        var $checkbox = $(this).parent().find('input[type=checkbox]');
        var checked = $checkbox.is(':checked');
        var idCatalogo = $checkbox.attr('data-id');

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
                    obj.actualizarEstado(idCatalogo, checked, $checkbox);
                }
            }
        );
    });
};

BannerFlotante.prototype.actualizarEstado = function(idCatalogo, checked, $checkbox) {
    var obj = this;

    var request = {
        idCatalogo : idCatalogo,
        estadoBannerFlotante : checked ? 1 : -1
    };

    var $button = document.querySelector( 'button.confirm');
    var loader = null;

    Promotick.ajax.post({
        url : obj.uri + 'administracion/banner-flotante/actualizar-estado',
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

BannerFlotante.prototype.listarSegmentosPopup = function(){
    var obj = this;
    obj.grid.DataTable({
        "bProcessing" : true,
        "bFilter" : false,
        "bLengthChange": false,
        "bPaginate": false,
        "bInfo" : true,
        "serverSide" : true,
        "bSort": false,
        "ajax": {
            "url" : obj.uri + 'administracion/banner-flotante/listar/' + obj.slcOrden.val(),
            "type": "GET",
            "data" : function(d) {

            }
        },
        "columns" : [
            {"data" : "nombreCatalogo", "orderable": false, "render" : this._formatNombre},
            {"data" : "bannerFlotante", "orderable": false, "sClass" : "text-center", "render" : this._formatImagen},
            {"data" : "urlBannerFlotante", "orderable": false},
            {"data" : "estadoBannerFlotante", "orderable": false,"class":"estado", "render" : obj._formatEstadoSwitch}
        ],
        "lengthMenu": [[5, 10, 20], [5, 10, 20]],
        "initComplete":function(settings,json){
        },
        "fnDrawCallback": function(){

        }
    });
};

BannerFlotante.prototype._formatNombre = function(data, type, full) {
    var obj = this;
    return '<a href="' + uriGeneral + 'administracion/banner-flotante/editar/' + full.idCatalogo + '"><u>' + data + '</u></a>';
};

BannerFlotante.prototype._formatImagen = function(data, type, full) {
    var obj = this;
    if (data != null && data != '') {
        return '<a class="image-popup" href="' + urlImagenPopup + data + '"><img src="' + urlImagenPopup + data + '" height="60px"/></a>';
    }
    return 'Sin imagen';
};

BannerFlotante.prototype._formatEstadoSwitch = function(data, type, full) {
    var estado = '';
    var checked= '';

    if(data === 1){
        checked = 'checked';
    }
    estado += '<div class="switch">';
    estado +=   '<div class="onoffswitch">';
    estado +=       '<input type="checkbox" ' + checked + ' disabled class="onoffswitch-checkbox" id="s-' + full.idCatalogo + '" data-id="' + full.idCatalogo + '">';
    estado +=       '<label class="onoffswitch-label" for="s-' + full.idCatalogo + '">';
    estado +=           '<span class="onoffswitch-inner"></span>';
    estado +=           '<span class="onoffswitch-switch"></span>';
    estado +=       '</label>';
    estado +=   '</div>';
    estado += '</div>';

    return estado;
};

BannerFlotante.prototype.gridReload = function(){
    this.grid.DataTable().ajax.reload();
};
