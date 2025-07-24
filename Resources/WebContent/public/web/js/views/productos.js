function Productos(uri, idCategoria) {
    this.uri = uri;
    this.idCategoria = idCategoria;
    this.productosDinamicos = $('.productosDinamicos');
    this.orderby = $('#orderby');
    this.sliderRange = $("#slider-range");
    this.paginado = $(".paginado");
    this.inptBuscar = $("#inptBuscar");
    this.rangeMin = parseInt(this.sliderRange.attr('data-min'));
    this.rangeMax = parseInt(this.sliderRange.attr('data-max'));
    this.filter = {};
    this.subcategoria = $(".subcategoria");
    this.tabsProductos = $('#tabs-productos');
    this.idSubCategoriaProducto;
    this.btnVerMas = $('#vermas');
    this.pagina=1;
    this.categoriaSesion = $('#idcategoriax');
    this.chkProductosDisponibles = $('#productosDisponibles');
    this.chkEnvioExpress = $('#productosEnvioExpress');
    this.puntosMax;
    this.imagenEnvioExpress = -1;
    this.loading = false;
}

Productos.prototype.init = function() {
    this.handle();
    this.iniciarProductos();
    // this.contarProductos(1);
};

Productos.prototype.handle = function() {
    var obj = this;

    // $(window).on("scroll.once", function() {
    //     if ($(window).scrollTop() == $(document).height() - $(window).height()){
    //         if(obj.loading === false){
    //             obj.loading = true;
    //             obj.pagina = obj.pagina +1;
    //             obj.contarProductos(obj.pagina);
    //         }
    //     }
    // });

    $(window).on("scroll.once", function () {
        if ($(window).scrollTop() + $(window).height() >= $(document).height() - 50) {
            if (!obj.loading) {
                obj.loading = true;
                obj.pagina += 1;
                obj.contarProductos(obj.pagina);
            }
        }
    });

    obj.orderby.select2({
        minimumResultsForSearch: -1
    });

    if (obj.rangeMin === obj.rangeMax) {
        obj.rangeMin = 0;
    }

    obj.sliderRange.slider({
        range: true,
        min: obj.rangeMin,
        max: obj.rangeMax,
        values: [ obj.rangeMin, obj.rangeMax ],
        slide: function( event, ui ) {
            obj.sliderRange.parent().find('input[id=amount]').val(ui.values[0] + " - " + ui.values[1]);
            obj.filter.puntosMin = ui.values[0];
            obj.filter.puntosMax = ui.values[1];
        }
    });

    obj.sliderRange.parent().find('input[id=amount]').val(obj.sliderRange.slider('values', 0) + " - " + obj.sliderRange.slider('values', 1));
    obj.filter.puntosMin = obj.sliderRange.slider('values', 0);
    obj.filter.puntosMax = obj.sliderRange.slider('values', 1);

    // obj.btnVerMas.on('click', function(e) {
    //     e.preventDefault();
    //     obj.pagina = obj.pagina +1;
    //     obj.puntosMax = undefined;
    //     obj.contarProductos(obj.pagina);
    //     // obj.btnVerMas.css("display", "none");
    // });

    obj.chkProductosDisponibles.on('change', function (e) {
        obj.pagina = 1;
        if($(this).is(":checked")){
            obj.puntosMax = $('#puntosDisponibles').val();
        } else {
            obj.puntosMax = '';
        }
        obj.contarProductos(obj.pagina);
        obj.obtenerProductos(obj.pagina);
    });

    obj.chkEnvioExpress.on('change', function (e) {
        obj.pagina = 1;
        if($(this).is(":checked")){
            obj.imagenEnvioExpress = 1;
            console.log('checked');
        } else {
            obj.imagenEnvioExpress = -1;
            console.log('no checked');
        }
        obj.contarProductos(obj.pagina);
        obj.obtenerProductos(obj.pagina);
    });

    /*
    obj.orderby.selectmenu({
        change: function( event, ui ) {
            obj.contarProductos(1);
        }
    });
     */

    obj.orderby.on('change', function (e) {
        obj.pagina = 1;
        obj.puntosMax = undefined;
        obj.contarProductos(obj.pagina);
    });

    $('li', obj.tabsProductos).on('click', function (e) {
        e.preventDefault();
        obj.pagina = 1;
        obj.puntosMax = undefined;
        var tab = $(this).attr('data-tab');
        $(this).parent().parent().find('.subcategoria').removeClass('activo');
        $(this).parent().parent().find('#tab-' + tab).find('a[data-index-categoria="0"]').addClass('activo');
        obj.idSubCategoriaProducto =
            $(this).parent().parent().find('#tab-' + tab).find('a[data-index-categoria="0"]').attr('data-id-categoria');

        obj.contarProductos(obj.pagina);

        if(obj.categoriaSesion.val()!=null && obj.categoriaSesion.val()>0){
            $(this).parent().parent().find('.subcategoria').removeClass('activo');
            $(this).parent().parent().find('.tab-panel').removeClass('active');
            $(this).removeClass('active');

            $(this).parent().parent().find('#sub-cat-' + obj.categoriaSesion.val()).parent().parent().parent().addClass('active');
            $(this).parent().parent().find('#sub-cat-' + obj.categoriaSesion.val()).addClass('activo');
            var categoriaParent = $(this).parent().parent().find('.tab-panel.active').attr('data-id-tab');
            $(this).parent().find('li[data-tab="' + categoriaParent + '"]').addClass('active');

            obj.idSubCategoriaProducto = obj.categoriaSesion.val();
            obj.categoriaSesion.val(0);
        }

    });

    obj.subcategoria.on('click', function (e) {
       e.preventDefault();
       obj.subcategoria.removeClass('activo');
       $(this).addClass('activo');
       obj.idSubCategoriaProducto = $(this).attr('data-id-categoria');
       obj.pagina = 1;
       obj.puntosMax = undefined;
       obj.contarProductos(obj.pagina);
    });

    // $('a[data-select=true]').parent().parent().show();
};

Productos.prototype.obtenerProductos = function(pagina) {
    var obj = this;

    Promotick.render.post({
        url : obj.uri + 'productos/viewListarProductos',
        data : obj.obtenerFiltroProductos(pagina),
        success : function(response) {
            if(pagina==1){
                obj.productosDinamicos.html('');
            }
            obj.productosDinamicos.append(response);
            obj.loading = false;
        }
        // context : obj.productosDinamicos
    });

};

Productos.prototype.iniciarProductos = function(){
    this.pagina = 1;
    if(this.inptBuscar.val()!=null && this.inptBuscar.val()!=''){
        this.contarProductos(this.pagina);
    }
    this.tabsProductos.find('li[data-tab="0"]').addClass('active');
    this.tabsProductos.find('.active').click();
};

Productos.prototype.contarProductos = function(pagina) {
    var obj = this;

    Promotick.ajax.post({
        url : obj.uri + 'productos/contarProductos',
        data : obj.obtenerFiltroProductos(pagina),
        messageError : true,
        messageTitle : 'Error Productos',
        before : function() {
            // obj.productosDinamicos.html('');
            // obj.productosDinamicos.LoadingOverlay('show');
        },
        success : function(response) {
            if (response.status) {
                var conteo = response.data;
                if (conteo != null || conteo > 0) {
                    obj.btnVerMas.show();
                    obj.obtenerProductos(pagina);
                } else {
                    obj.btnVerMas.hide();
                    obj.loading = false;
                }
            }

        },
        complete : function() {
            obj.productosDinamicos.LoadingOverlay("hide", true);
        }
    });

};

Productos.prototype.obtenerFiltroProductos = function(pagina) {
    var obj = this;
    return JSON.stringify({
        // categoria : obj.idCategoria,
        categoria : obj.idSubCategoriaProducto,
        orden : obj.orderby.val(),
        pagina : pagina,
        buscar : obj.inptBuscar.val(),
        rangoMax : obj.puntosMax,
        imagenEnvioExpress: obj.imagenEnvioExpress
        // rangoMax : obj.filter.puntosMax
        // rangoMin : obj.filter.puntosMin,
    });
};

let currentIndex = 0;
const totalSlides = document.querySelectorAll('.slide').length;
const dots = document.querySelectorAll('.dot');

// Mueve el carrusel en una dirección (izquierda o derecha)
function moveSlide(direction) {
    currentIndex += direction;

    // Si el índice es menor que 0, volvemos al último slide
    if (currentIndex < 0) {
        currentIndex = totalSlides - 1;
    }

    // Si el índice excede el total de slides, volvemos al primer slide
    if (currentIndex >= totalSlides) {
        currentIndex = 0;
    }

    updateCarousel();
}

// Mueve el carrusel al slide específico basado en el índice
function goToSlide(index) {
    currentIndex = index;
    updateCarousel();
}

// Actualiza la posición del carrusel y los puntos
function updateCarousel() {
    // Mueve el carrusel
    const newTransformValue = -currentIndex * 100;
    document.querySelector('.slider').style.transform = `translateX(${newTransformValue}%)`;

    // Actualiza los puntos (dots)
    dots.forEach((dot, idx) => {
        if (idx === currentIndex) {
            dot.classList.add('active');
        } else {
            dot.classList.remove('active');
        }
    });
}

// Mueve el carrusel automáticamente cada 3 segundos
function autoMove() {
    setInterval(() => {
        moveSlide(1); // Mueve hacia el siguiente slide
    }, 3000); // 3000 milisegundos = 3 segundos
}

// Llama a autoMove cuando la página cargue
autoMove();

// Inicializar los dots
updateCarousel();
