function Inicio(uri, urlR) {
    this.uri = uri;
    this.urlRecurso = urlR;
    this.contentPromociones = $('#contentPromociones');
    this.contentDestacados = $('#contentDestacados');
    this.contentNovedades = $('#contentNovedades');
    this.contentVistos = $('#contentVistos');
    this.videoModal = $('#videoModal');
}

Inicio.prototype.init = function() {
    this.handle();
    this.obtenerProductos('productos/viewProductosPromociones', this.contentPromociones, "contentPromocionesWrap");
    // this.obtenerProductos('productos/viewProductosDestacados', this.contentDestacados);
    // this.obtenerProductos('productos/viewProductosNovedosos', this.contentNovedades);
    // this.obtenerProductos('productos/viewProductosVisitados', this.contentVistos);
};

Inicio.prototype.handle = function() {
    var obj = this;

    $(document).on('click', '.btnVideo', function(e) {
        e.preventDefault();

        var video = obj.videoModal.attr('data-video');
        var preview = obj.videoModal.attr('data-preview');

        if (video === undefined || video === '') {
            obj.videoModal.find('.modal-body').html('<div style="text-align: center">No tenemos un video promocional en estos momentos.</div>');
        } else {

            var htmlVideo = '<video id="videoID" class="video-js vjs-default-skin vjs-fluid video-item" controls preload="none"' +
                (preview !== undefined && preview !== '' ? ' poster="' + obj.urlRecurso + preview + '"': '') +
                ' >' +
                '<source src="' + obj.urlRecurso + video +'" type="video/mp4">' +
                '<p class="vjs-no-js">Este video no puede ser reproducido desde este dispositivo, por favor contacte al administrador desde el area de contacto.</p></video>';

            obj.videoModal.find('.modal-body').html(htmlVideo);

            videojs('#videoID');
        }
        obj.videoModal.modal('show');

    });

    obj.videoModal.on('hidden.bs.modal', function () {
        if ($('#videoID').length > 0) {
            videojs('#videoID').dispose();
        }
    });



};

Inicio.prototype.obtenerProductos = function(uri, context,contextSpecific) {
    var obj = this;

    Promotick.render.get({
        url : obj.uri + uri,
        context : context,
        complete : function() {

            if(contextSpecific != undefined){
                $("#" + contextSpecific).owlCarousel(Promotick.defaults.owlCarousel);
            }else {
                context.owlCarousel(Promotick.defaults.owlCarousel);
            }
        }

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