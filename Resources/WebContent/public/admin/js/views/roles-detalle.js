function RolesDetalle(uri, registro) {
    this.registro = parseInt(registro);
    this.uri = uri;
    this.btnGuardar = $('#btnGuardar');
    this.idRol = $('#idRol');
    this.formRegistro = $('#form-registro');
    this.selectMenus = $('#select-menus');
    this.selectRol = $('#select-rol');
    this.btnAgregar = $('#btn-agregar');
    this.btnRemover = $('#btn-remover');
    this.nombreRol = $('#nombreRol');
    this.descripcionRol = $('#descripcionRol');
}

RolesDetalle.prototype.init = function() {
    this.handle();
    this.validarFormRegistro();
    if (this.registro === 1) {
        this.listarTodosMenus();
    } else {
        this.obtenerRol();
    }
};

RolesDetalle.prototype.handle = function() {
    var obj = this;

    obj.btnGuardar.on('click', function(e) {
        e.preventDefault();

        if (obj.formRegistro.valid()) {
            obj.guardarRol(this);
        } else {
            obj.formRegistro.validate().focusInvalid();
        }
    });

    obj.btnAgregar.on('click', function(event){
        event.preventDefault();
        var destino = "#select-rol";
        var origen = "#select-menus";
        obj.moverElementos(origen, destino);
    });

    obj.btnRemover.on('click', function(event){
        event.preventDefault();
        var destino = "#select-menus";
        var origen = "#select-rol";
        obj.moverElementos(origen, destino);
    });
};

RolesDetalle.prototype.listarTodosMenus= function(){
    var obj =  this;

    Promotick.ajax.get({
        url : obj.uri + 'administracion/roles/listar-menus',
        messageError : true,
        messageTitle : 'Obtencion de menus',
        before : function() {
            obj.formRegistro.LoadingOverlay('show');
        },
        success : function(response) {
            if (response.status) {
                obj.dibujarMultiselect(response.data, obj.selectMenus);
            }
        },
        complete : function() {
            obj.formRegistro.LoadingOverlay('hide', true);
        }
    });
};

RolesDetalle.prototype.obtenerRol = function (){
    var obj = this;

    Promotick.ajax.get({
        url : obj.uri + 'administracion/roles/detalle/' + obj.idRol.val(),
        messageError : true,
        messageTitle : 'Obtencion de menus',
        before : function() {
            obj.formRegistro.LoadingOverlay('show');
        },
        success : function(response) {
            if (response.status) {
                obj.dibujarMultiselect(response.data.menus, obj.selectRol);
                obj.listarMenusFaltantes(JSON.stringify(response.data.menus));
            }
        },
        complete : function() {
            obj.formRegistro.LoadingOverlay('hide', true);
        }
    });
};

RolesDetalle.prototype.listarMenusFaltantes = function(menus){
    var obj =  this;

    Promotick.ajax.post({
        url : obj.uri + 'administracion/roles/listar-menusFaltantes',
        data : menus,
        messageError : true,
        messageTitle : 'Obtencion de menus',
        before : function() {
            obj.formRegistro.LoadingOverlay('show');
        },
        success : function(response) {
            if (response.status) {
                obj.dibujarMultiselect(response.data, obj.selectMenus);
            }
        },
        complete : function() {
            obj.formRegistro.LoadingOverlay('hide', true);
        }
    });

};

RolesDetalle.prototype.dibujarMultiselect = function(menus, select){
    var obj = this;
    var  menumap =  new Map();
    var  dbmenu = "";
    var submenus = [];
    $.each(menus,function(i,e){
        dbmenu = e.modulo;
        if(menumap.get(dbmenu)) {
            submenus = menumap.get(dbmenu);
            submenus.push(e);
        }
        else{
            submenus = [];
            submenus.push(e);
            menumap.set(dbmenu, submenus);
        }
    });
    var html;
    select.empty();

    menumap.forEach(function(v, key, map) {
        var $optgroup = $('<optgroup/>').prop('label', key);
        $.each(v,function(i,e){
            $optgroup.append($('<option/>').attr('value', e.idMenu).html(e.nombreMenu));
        });
        select.append($optgroup);
    });

};

RolesDetalle.prototype.moverElementos = function(origen, destino) {
    var obj = this;
    $(origen+' option:selected').each(function(i, selected){
        var valSel =  $(selected).text();
        var valGrupo = $(selected).closest('optgroup').prop('label');

        if($(destino).find('optgroup[label="'+valGrupo+'"]').length>0){
            $(destino).find('optgroup[label="'+valGrupo+'"]').append($(selected));
            obj.removePadreOption(origen,valGrupo);
        }else{
            var htmlGroup = '<optgroup label="'+valGrupo+'"></optgroup>';
            $(htmlGroup).appendTo(destino).append($(selected));
            obj.removePadreOption(origen,valGrupo);
        }

    });
    return true;
};

RolesDetalle.prototype.removePadreOption = function(origen,valGrupo) {
    var origenPadre =  $(origen).find('optgroup[label="'+valGrupo+'"]');
    if(origenPadre.children().length === 0){
        origenPadre.remove();
    }
};

RolesDetalle.prototype.validarFormRegistro = function() {
    var obj = this;

    $.validator.addMethod("empty", function(value, element, arg){
        return element.length !== 0;
    }, "Debe ingresar al menos una opción");

    this.formRegistro.validate({
        rules: {
            nombreRol: {
                required: true
            },
            descripcionRol: {
                required: true
            },
            roles: {
                empty: true
            }
        },
        messages: {
            nombreRol: {
                required: 'Ingrese nombre del rol'
            },
            descripcionRol: {
                required: 'Ingrese la descripcion del rol'
            },
            roles: {
                empty: 'Debe seleccionar al menos un menu'
            }
        }
    });
};

RolesDetalle.prototype.guardarRol = function(context) {
    var obj = this;

    var rol = {
        idRol: obj.idRol.val(),
        nombreRol : obj.nombreRol.val(),
        descripcionRol : obj.descripcionRol.val(),
        idMenus : obj.obtenerModulosSeleccionados()
    };

    var loader = null;

    Promotick.ajax.post({
        url : obj.uri + 'administracion/roles/guardarRol',
        data : JSON.stringify(rol),
        messageError : true,
        messageTitle : 'Mantenimiento de roles',
        before : function() {
            loader = Ladda.create(context);
            loader.start();
            obj.formRegistro.LoadingOverlay('show');
        },
        success : function(response) {
            if (response.status) {
                Promotick.toast.success(response.message, 'Mantenimiento de roles');
                $('html, body').animate({scrollTop:0}, 'slow');
                if (obj.registro === 1) {
                    obj.formRegistro[0].reset();
                    setTimeout(function() {
                        window.location.href = obj.uri + "administracion/roles"
                    }, 1000)
                }
            }
        },
        complete : function() {
            obj.formRegistro.LoadingOverlay('hide', true);
            loader.stop();
        }
    });

};

RolesDetalle.prototype.obtenerModulosSeleccionados = function() {
    var obj = this;
    var arraySelect = [];
    $('#select-rol option').each(function(i, selected){
        arraySelect.push($(selected).val());
    });
    console.log('arraySelect.join(","): '+arraySelect.join(","));
    return arraySelect.join(",");
};