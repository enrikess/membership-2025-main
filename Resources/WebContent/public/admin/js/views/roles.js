var uriGeneral;
function Roles(uri) {
    this.uri = uri;
    uriGeneral = uri;
    this.grid = $('#gridRoles');
    this.btnAccion = $('.btnAccion');
    this.gridMenus = $('#gridMenus');
    this.modalRoles = $('#modalRoles');
    this.alert = null;
}

Roles.prototype.init = function() {
    this.handle();
    this.listarUsuarios();
    this.iniciarMenus();
};

Roles.prototype.handle = function() {
    var obj = this;

    obj.btnAccion.on('click', function(e) {
        e.preventDefault();
        window.location.href = $(this).attr('data-url');
    });

    obj.grid.on('click','label.ver-menus', function (e){
        e.preventDefault();

        var data = obj.grid.dataTable().fnGetData($(this).parents('tr'));

        $.each(data.menus, function(i, e) {
            obj.gridMenus.DataTable().row.add(e).draw( false );
        });

        obj.modalRoles.modal();

    });

    obj.grid.on('click','label.btnEditar', function (e){
        e.preventDefault();
        window.location.href = $(this).attr('data-url');
    });

    obj.modalRoles.on('hidden.bs.modal', function (e) {
        obj.gridMenus.DataTable().rows().remove().draw();
    })
};


Roles.prototype.listarUsuarios = function(){
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
            "url" : obj.uri + 'administracion/roles/listar-roles',
            "type": "POST",
            "data" : function(d) {

            }
        },
        "columns" : [
            {"data" : "nombreRol", "orderable": true , "class" : "text-center", "width" : "20%"},
            {"data" : "descripcionRol", "orderable": true, "class" : "text-center", "width" : "70%"},
            {"data" : null, "orderable": false, "class" : "text-center", "width" : "10%", "render":obj._formatMenus},
            {"data" : null, "orderable": false, "class" : "text-center", "width" : "10%", "render":obj._formatEditar}
        ],
        "columnDefs": [
            { "width": "20%", "targets": 0 },
            { "width": "70%", "targets": 1 },
            { "width": "10%", "targets": 2 },
            { "width": "10%", "targets": 3 }
        ],
        "lengthMenu": [[10, 15, 20], [10, 15, 20]],
        "initComplete":function(settings,json){
        },
        "fnDrawCallback": function(){

        }
    });
};

Roles.prototype.iniciarMenus = function() {
    var obj = this;
    obj.gridMenus.dataTable({
        "bProcessing" : false,
        "bFilter" : false,
        "bLengthChange": false,
        "bPaginate": false,
        "bInfo" : false,
        "serverSide" : false,
        "bSort": false,
        "columns" : [
            {"data" : "nombreMenu", "orderable": false}
        ]
    });
};

Roles.prototype._formatEditar = function(data, type, full) {
    console.log(full);
    if (!full.esSuperUsuario) {
        return '<label class="btn btn-danger btn-xs btnEditar" data-url="' + uriGeneral + 'administracion/roles/editar/' + full.idRol + '" style="margin: 0"><i class="fa fa-edit"></i></label>';
    }
    return '';
};

Roles.prototype._formatMenus = function(data, type, full) {
    return '<label class="label label-success ver-menus" style="cursor: pointer">VER PERMISOS</label>';
};

Roles.prototype.gridReload = function(){
    this.grid.DataTable().ajax.reload();
};
