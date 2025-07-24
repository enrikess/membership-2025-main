function ReporteTyc(uri) {
    this.uri = uri;
    this.rangoDatePicker = $('#rangoDatePicker');
    this.grid = $('#gridVentas');
    this.btnFiltrar = $('#btnFiltrar');
    this.fechaInicio = $('#fechaInicio');
    this.fechaFin = $('#fechaFin');
    this.btnDescargar = $('#btnDescargar');
    this.cleanCheckboxs = $('#clean-checkboxs');
    this.containerEliminar = $('#container-eliminar');
    this.textEliminar = $('#text-eliminar');
    this.btnBorrar = $('#btnBorrar');
    this.modalBorrarConfirm = $('#modalBorrarConfirm');
    this.modalBorrarAceptar = $('#modalBorrarAceptar');
    this.modalEditarAceptar = $('#modalEditarAceptar');
    this.slcCatalogo = $('#slcCatalogo');
    this.errorCatalogo = $('#errorCatalogo');
    this.textareaEditor = $('#editor');
    this.slcCatalogoCrear = $('#slcCatalogoCrear');
    this.modalRegistrar = $('#modalRegistrar');
    this.btnCrear = $('#btnCrear');
    this.modalCrearAceptar = $('#modalCrearAceptar');
    this.selectedidAvances = [];
}

ReporteTyc.prototype.init = function() {
    this.handle();
    this.listar();
};

ReporteTyc.prototype.handle = function() {
    var obj = this;

    obj.rangoDatePicker.datepicker({
        endDate: "0m"
    });

    obj.btnCrear.on('click', function(e){
        e.preventDefault();
        sceditor.instance(document.getElementById('editorCrear')).val('');
        obj.obtenerCatalogosDisponibles();
    });

    obj.btnFiltrar.on('click', function(e){
        e.preventDefault();
        obj.gridReload();
    });

    obj.modalEditarAceptar.on('click', function(e){
        e.preventDefault();

        if (obj.slcCatalogo.val() === '0') {
            obj.slcCatalogo.css('border-color', 'red');
            obj.errorCatalogo.text('Debe ingresar una fecha (DD/MM/YYYY).');
            return;
        } else {
            obj.slcCatalogo.css('border-color', '#e5e6e7');
            obj.errorCatalogo.text('');
        }

        obj.editar({
            idCatalogo: Number(obj.slcCatalogo.val()),
            descripcion: sceditor.instance(document.getElementById('editor')).val(),
            idTyc: Number($(this).attr('idtyc')),
        })
    });


    obj.btnDescargar.on('click', function(e) {
        e.preventDefault();
        window.location.href = $(this).attr('data-descarga');
    })

    obj.cleanCheckboxs.on('change', function(e) {

        if ($(this).is(":checked")) {
            $('.meta-checkbox').each(function() {
                obj.selectedidAvances.push($(this).attr("idtyc"));
                $(`.meta-checkbox`).prop("checked", true);
            } )
        } else {
            $('.meta-checkbox').each(function() {
                obj.selectedidAvances = obj.selectedidAvances.filter(meta => `${meta}` !== `${$(this).attr("idtyc")}`);
                $(`.meta-checkbox`).prop("checked", false);
            })
        }
        obj.actualizarSeleccionados();
    })

    $(document).on('click', '.btn-editar', function() {

        let tyc = $(this).data();
        obj.slcCatalogo.val(tyc.idcatalogo);
        sceditor.instance(document.getElementById('editor')).val(decodeURIComponent(escape(window.atob(tyc.texto))));
        obj.modalEditarAceptar.attr("idtyc", tyc.idtyc);
        //obj.fechaOperacionEditar.val(fecha);
        $('#modalEditar').modal({});
    })

    $(document).on("change", ".meta-checkbox", function() {
        let idtyc = $(this).attr("idtyc");

        if ($(this).is(":checked")) {
            // Add idtyc if checked
            if (!obj.selectedidAvances.includes(idtyc)) {
                obj.selectedidAvances.push(idtyc);
            }
        } else {
            // Remove idtyc if unchecked
            obj.selectedidAvances = obj.selectedidAvances.filter(meta => meta !== idtyc);
        }
        obj.actualizarSeleccionados();
    });

    obj.btnBorrar.on('click', function(e){
        e.preventDefault();
        obj.modalBorrarConfirm.modal({
            backdrop: 'static',
            keyboard: false
        });
    });

    obj.modalBorrarAceptar.on('click', function(e){
        e.preventDefault();
        obj.borrar(obj.selectedidAvances.join(","));
    });

    obj.modalCrearAceptar.on('click', function(e){
        e.preventDefault();

        if (obj.slcCatalogoCrear.val() === '0') {
            obj.slcCatalogoCrear.css('border-color', 'red');
            $('#errorCatalogoCrear').text('Debe ingresar una fecha (DD/MM/YYYY).');
            return;
        } else {
            obj.slcCatalogoCrear.css('border-color', '#e5e6e7');
            $('#errorCatalogoCrear').text('');
        }

        obj.crear({
            idCatalogo: Number(obj.slcCatalogoCrear.val()),
            descripcion: sceditor.instance(document.getElementById('editorCrear')).val(),
        });
    });

};

ReporteTyc.prototype.actualizarSeleccionados = function() {
    var obj = this;
    obj.selectedidAvances = [...new Set(obj.selectedidAvances)];
    if (obj.selectedidAvances.length) {
        obj.containerEliminar.css('display', 'flex');
        obj.textEliminar.text('Se seleccionaron: ' + obj.selectedidAvances.length);
    } else {
        obj.containerEliminar.css('display', 'none');
    }
}


ReporteTyc.prototype.crear = function(venta){
    var obj = this;
    Promotick.ajax.post({
        url : obj.uri + 'reportes/tyc/crear',
        data : JSON.stringify(venta),
        messageError : true,
        messageTitle : 'Crear terminos',
        before : function() {
            obj.modalEditarAceptar.text('Cargando...');
            obj.modalEditarAceptar.prop('disabled', true);
        },
        success : function(response) {
            if (response.status) {
                Promotick.toast.success(response.message, 'Crear terminos');
                obj.modalRegistrar.modal('hide');
                obj.gridReload();
            }
        },
        complete : function() {
            obj.modalEditarAceptar.text('Aceptar');
            obj.modalEditarAceptar.prop('disabled', false);
        },
        error: function(status, error) {
            console.log('error ' + status + ': ' + error);
        }
    });
}


ReporteTyc.prototype.editar = function(venta){
    var obj = this;
    Promotick.ajax.post({
        url : obj.uri + 'reportes/tyc/editar',
        data : JSON.stringify(venta),
        messageError : true,
        messageTitle : 'Editar terminos',
        before : function() {
            obj.modalEditarAceptar.text('Cargando...');
            obj.modalEditarAceptar.prop('disabled', true);
        },
        success : function(response) {
            if (response.status) {
                Promotick.toast.success(response.message, 'Editar terminos');
                $('#modalEditar').modal('hide');
                obj.gridReload();
            }
        },
        complete : function() {
            obj.modalEditarAceptar.text('Aceptar');
            obj.modalEditarAceptar.prop('disabled', false);
        },
        error: function(status, error) {
            console.log('error ' + status + ': ' + error);
        }
    });
}

ReporteTyc.prototype.obtenerCatalogosDisponibles = function(){
    const obj = this;
    Promotick.ajax.get({
        url : obj.uri + 'reportes/tyc/catalogos',
        before : function() {
        },
        success : function(response) {
            if (response && response.length) {
                obj.slcCatalogoCrear.empty();
                obj.slcCatalogoCrear.append('<option value="0">Seleccionar</option>');
                response.forEach(function(catalogo) {
                    obj.slcCatalogoCrear.append(`<option value="${catalogo.idCatalogo}">${catalogo.nombreCatalogo}</option>`);
                });

                obj.modalRegistrar.modal({
                    backdrop: 'static',
                    keyboard: false
                });
            } else if (response && response.length === 0) {
                $('#modalSinCatalogos').modal({
                    backdrop: 'static',
                    keyboard: false
                });
            }
        },
        complete : function() {
        }
    });
}

ReporteTyc.prototype.borrar = function(listaId){
    var obj = this;
    Promotick.ajax.post({
        url : obj.uri + 'reportes/tyc/borrar',
        data : JSON.stringify({
            listaId,
        }),
        messageError : true,
        messageTitle : 'Eliminar ventas',
        before : function() {
            obj.btnBorrar.text('Cargando...');
            obj.btnBorrar.prop('disabled', true);
            obj.modalBorrarConfirm.modal('hide');
        },
        success : function(response) {
            if (response.status) {
                Promotick.toast.success(response.message, 'Eliminar ventas');
            }
        },
        complete : function() {
            obj.btnBorrar.text(' Eliminar registros');
            obj.btnBorrar.prop('disabled', false);
            obj.selectedidAvances = [];
            $(`.meta-checkbox`).prop("checked", false);
            obj.gridReload();
            obj.actualizarSeleccionados();
        }
    });
}

ReporteTyc.prototype.listar = function(){
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
            "url" : obj.uri + 'reportes/tyc/listar',
            "type": "POST",
            "data" : function(d) {
            }
        },
        "columns" : [
            {"data" : "idTyc", "orderable": false, "render" : this._formatCheck, "sClass" : "text-center align-middle"},
            {"data" : "catalogo.nombreCatalogo", "orderable": false, "sClass" : "text-center align-middle"},
            {"data" : "descripcionTyc", "orderable": false, "sClass" : "text-center align-middle", "render" : this._formatTextarea},
            {"data" : "idTyc", "orderable": false, "render" : this._formatEditar, "sClass" : "text-center p-0 align-middle"},
        ],
        "lengthMenu": [[10, 20, 30], [10, 20, 30]],
        "initComplete":function(settings,json){

        },
        "columnDefs": [
            { orderDataType: 'dom-text', render: $.fn.dataTable.render.text(),  "targets": "_all" },
        ],
        "fnDrawCallback": function(){
            for (let i = 0; i < obj.selectedidAvances.length; i++) {
                $(`.meta-checkbox[idAvance='${obj.selectedidAvances[i]}']`).prop("checked", true);

            }
            $('#clean-checkboxs').prop("checked", false);
            obj.actualizarSeleccionados();
        }
    });
};

ReporteTyc.prototype._formatDate = function(data, type, full){
    return data && data.length > 10 ? data.substring(0, 10) : data;
};


ReporteTyc.prototype._formatCheck = function(data, type, full) {
    return `<input type="checkbox" class="meta-checkbox" idtyc="${data}"/>`;
};

ReporteTyc.prototype._formatTextarea = function(data, type, full) {
    return `<textarea id="story" name="story" disabled="disabled" rows="2" style="width: 100%" >${data}</textarea>`;
};

ReporteTyc.prototype._formatEditar = function(data, type, full) {
    return `<button type="button" class="btn btn-primary btn-sm btn-editar" data-idcatalogo="${full.catalogo.idCatalogo}" data-texto="${btoa(unescape(encodeURIComponent((full.descripcionTyc))))}" data-idtyc="${data}">Editar</button>`;
};

ReporteTyc.prototype.gridReload = function(){
    this.grid.DataTable().ajax.reload();
};
