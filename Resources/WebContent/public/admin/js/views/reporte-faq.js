function ReporteFaq(uri) {
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
    this.errorCatalogo = $('#errorCatalogoEditar');
    this.textareaEditor = $('#editor');
    this.slcCatalogoCrear = $('#slcCatalogoCrear');
    this.errorCatalogoCrear = $('#errorCatalogoCrear');
    this.modalRegistrar = $('#modalRegistrar');
    this.btnCrear = $('#btnCrear');
    this.modalCrearAceptar = $('#modalCrearAceptar');
    this.errorPreguntaCrear = $('#errorPreguntaCrear');
    this.errorRespuestaCrear = $('#errorRespuestaCrear');
    this.errorOrdenCrear = $('#errorOrdenCrear');
    this.selectedidAvances = [];
}

ReporteFaq.prototype.init = function() {
    this.handle();
    this.listar();
};

ReporteFaq.prototype.handle = function() {
    var obj = this;

    obj.rangoDatePicker.datepicker({
        endDate: "0m"
    });

    obj.btnCrear.on('click', function(e){
        e.preventDefault();
        $('#inputCrearOrden').val('');
        $('#textCrearRespuesta').val('');
        $('#textCrearPregunta').val('');

        obj.modalRegistrar.modal({
            backdrop: 'static',
            keyboard: false
        });
    });

    obj.btnFiltrar.on('click', function(e){
        e.preventDefault();
        obj.gridReload();
    });

    obj.modalEditarAceptar.on('click', function(e){
        e.preventDefault();

        if (obj.slcCatalogo.val() === '0') {
            obj.slcCatalogo.css('border-color', 'red');
            obj.errorCatalogo.text('Debe seleccionar un catálogo.');
            return;
        } else {
            obj.slcCatalogo.css('border-color', '#e5e6e7');
            obj.errorCatalogo.text('');
        }

        if ($('#textEditarPregunta').val() === null || $('#textEditarPregunta').val() === '') {
            $('#textEditarPregunta').css('border-color', 'red');
            $('#errorPreguntaEditar').text('Debe ingresar una pregunta.');
            return;
        } else {
            $('#textEditarPregunta').css('border-color', '#e5e6e7');
            $('#errorPreguntaEditar').text('');
        }

        if ($('#textEditarRespuesta').val() === null || $('#textEditarRespuesta').val() === '') {
            $('#textEditarRespuesta').css('border-color', 'red');
            $('#errorRespuestaEditar').text('Debe ingresar una respuesta.');
            return;
        } else {
            $('#textEditarRespuesta').css('border-color', '#e5e6e7');
            $('#errorRespuestaEditar').text('');
        }

        if ($('#inputEditarOrden').val() === null || $('#inputEditarOrden').val() === '') {
            $('#inputEditarOrden').css('border-color', 'red');
            $('#errorOrdenEditar').text('Debe ingresar número de orden.');
            return;
        } else {
            $('#inputEditarOrden').css('border-color', '#e5e6e7');
            $('#errorOrdenEditar').text('');
        }

        obj.editar({
            idCatalogo: Number(obj.slcCatalogo.val()),
            pregunta: $('#textEditarPregunta').val(),
            respuesta: $('#textEditarRespuesta').val(),
            orden: $('#inputEditarOrden').val(),
            idFaq: Number($(this).attr('idfaq')),
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

        let faq = $(this).data();
        obj.slcCatalogo.val(faq.idcatalogo);
        $('#textEditarPregunta').val(atob(faq.pregunta));
        $('#textEditarRespuesta').val(atob(faq.respuesta));
        $('#inputEditarOrden').val(faq.orden);
        obj.modalEditarAceptar.attr("idfaq", faq.idfaq);
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

    $("#slcCatalogoMain").change(function() {
        obj.gridReload();
    });

    obj.modalBorrarAceptar.on('click', function(e){
        e.preventDefault();
        obj.borrar(obj.selectedidAvances.join(","));
    });

    obj.modalCrearAceptar.on('click', function(e){
        e.preventDefault();

        if (obj.slcCatalogoCrear.val() === '0') {
            obj.slcCatalogoCrear.css('border-color', 'red');
            obj.errorCatalogoCrear.text('Debe seleccionar un catálogo.');
            return;
        } else {
            obj.slcCatalogoCrear.css('border-color', '#e5e6e7');
            obj.errorCatalogoCrear.text('');
        }

        if ($('#textCrearPregunta').val() === null || $('#textCrearPregunta').val() === '') {
            $('#textCrearPregunta').css('border-color', 'red');
            obj.errorPreguntaCrear.text('Debe ingresar una pregunta.');
            return;
        } else {
            $('#textCrearPregunta').css('border-color', '#e5e6e7');
            obj.errorPreguntaCrear.text('');
        }

        if ($('#textCrearRespuesta').val() === null || $('#textCrearRespuesta').val() === '') {
            $('#textCrearRespuesta').css('border-color', 'red');
            obj.errorRespuestaCrear.text('Debe ingresar una respuesta.');
            return;
        } else {
            $('#textCrearRespuesta').css('border-color', '#e5e6e7');
            obj.errorRespuestaCrear.text('');
        }

        if ($('#inputCrearOrden').val() === null || $('#inputCrearOrden').val() === '') {
            $('#inputCrearOrden').css('border-color', 'red');
            obj.errorOrdenCrear.text('Debe ingresar número de orden.');
            return;
        } else {
            $('#inputCrearOrden').css('border-color', '#e5e6e7');
            obj.errorOrdenCrear.text('');
        }

        obj.crear({
            idCatalogo: Number(obj.slcCatalogoCrear.val()),
            pregunta: $('#textCrearPregunta').val(),
            respuesta: $('#textCrearRespuesta').val(),
            orden: $('#inputCrearOrden').val(),
        });
    });

};

ReporteFaq.prototype.actualizarSeleccionados = function() {
    var obj = this;
    obj.selectedidAvances = [...new Set(obj.selectedidAvances)];
    if (obj.selectedidAvances.length) {
        obj.containerEliminar.css('display', 'flex');
        obj.textEliminar.text('Se seleccionaron: ' + obj.selectedidAvances.length);
    } else {
        obj.containerEliminar.css('display', 'none');
    }
}


ReporteFaq.prototype.crear = function(venta){
    var obj = this;
    Promotick.ajax.post({
        url : obj.uri + 'reportes/faq/crear',
        data : JSON.stringify(venta),
        messageError : true,
        messageTitle : 'Crear',
        before : function() {
            obj.modalEditarAceptar.text('Cargando...');
            obj.modalEditarAceptar.prop('disabled', true);
        },
        success : function(response) {
            if (response.status) {
                Promotick.toast.success(response.message, 'Crear');
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


ReporteFaq.prototype.editar = function(venta){
    var obj = this;
    Promotick.ajax.post({
        url : obj.uri + 'reportes/faq/editar',
        data : JSON.stringify(venta),
        messageError : true,
        messageTitle : 'Editar registros',
        before : function() {
            obj.modalEditarAceptar.text('Cargando...');
            obj.modalEditarAceptar.prop('disabled', true);
        },
        success : function(response) {
            if (response.status) {
                Promotick.toast.success(response.message, 'Editar registros');
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

ReporteFaq.prototype.obtenerCatalogosDisponibles = function(){
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

ReporteFaq.prototype.borrar = function(listaId){
    var obj = this;
    Promotick.ajax.post({
        url : obj.uri + 'reportes/faq/borrar',
        data : JSON.stringify({
            listaId,
        }),
        messageError : true,
        messageTitle : 'Eliminar',
        before : function() {
            obj.btnBorrar.text('Cargando...');
            obj.btnBorrar.prop('disabled', true);
            obj.modalBorrarConfirm.modal('hide');
        },
        success : function(response) {
            if (response.status) {
                Promotick.toast.success(response.message, 'Eliminar');
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

ReporteFaq.prototype.listar = function(){
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
            "url" : obj.uri + 'reportes/faq/listar',
            "type": "POST",
            "data" : function(d) {
                d.idCatalogo = $('#slcCatalogoMain').val();
            }
        },
        "columns" : [
            {"data" : "idFaq", "orderable": false, "render" : this._formatCheck, "sClass" : "text-center align-middle"},
            {"data" : "catalogo.nombreCatalogo", "orderable": false, "sClass" : "text-center align-middle"},
            {"data" : "pregunta", "orderable": false, "sClass" : "text-center align-middle", "render" : this._formatTextarea},
            {"data" : "respuesta", "orderable": false, "sClass" : "text-center align-middle", "render" : this._formatTextarea},
            {"data" : "orden", "orderable": false, "sClass" : "text-center align-middle" },
            {"data" : "idFaq", "orderable": false, "render" : this._formatEditar, "sClass" : "text-center p-0 align-middle"},
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

ReporteFaq.prototype._formatDate = function(data, type, full){
    return data && data.length > 10 ? data.substring(0, 10) : data;
};


ReporteFaq.prototype._formatCheck = function(data, type, full) {
    return `<input type="checkbox" class="meta-checkbox" idtyc="${data}"/>`;
};

ReporteFaq.prototype._formatTextarea = function(data, type, full) {
    return `<textarea id="story" name="story" disabled="disabled" rows="2" style="width: 100%" >${data}</textarea>`;
};

ReporteFaq.prototype._formatEditar = function(data, type, full) {
    return `<button type="button" class="btn btn-primary btn-sm btn-editar" data-idcatalogo="${full.catalogo.idCatalogo}" data-orden="${full.orden}" data-pregunta="${btoa(full.pregunta)}"  data-respuesta="${btoa(full.respuesta)}" data-idfaq="${data}">Editar</button>`;
};

ReporteFaq.prototype.gridReload = function(){
    this.grid.DataTable().ajax.reload();
};
