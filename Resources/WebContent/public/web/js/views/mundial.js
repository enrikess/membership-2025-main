function Mundial(uri, uriResources, cantidadPuntosMundial, cantidadPuntosSig, cantidadPuntosNivel, puntosTrivia, imagenUsuario, duracionPreguntaTrivia) {
    this.uri = uri;
    this.uriResources = uriResources;
    this.flagTrivia = false;
    this.respuestas = [];
    this.contentPregunta = $("#content-pregunta");
    this.contentRanking = $(".q-ranking__list");
    this.contentMatch = $(".q-forecast__matches");
    this.contentPronosticos = $("#q-dates .q-match-pronosticos");
    this.buttonRefreshRanking = $(".q-ranking__refresh");
    // this.sourcePregunta = $("#pregunta-template").html();
    // this.templatePregunta = Handlebars.compile(this.sourcePregunta);
    // this.sourceGrupoMundial = $("#grupo-pais-template").html();
    // this.templateGrupoMundial = Handlebars.compile(this.sourceGrupoMundial);
    // this.sourceMatch = $("#match-template").html();
    // this.templateMatch = Handlebars.compile(this.sourceMatch);
    // this.sourceRanking = $("#ranking-template").html();
    // this.templateRanking = Handlebars.compile(this.sourceRanking);
    // this.sourceMatchPronostico = $("#match-pronostico-template").html();
    // this.templateMatchPronostico = Handlebars.compile(this.sourceMatchPronostico);
    // this.sourceMatchInicio = $("#match-inicio-template").html();
    // this.templateMatchInicio = Handlebars.compile(this.sourceMatchInicio);
    this.resultPolla = [];
    this.cantidadPuntosMundial = cantidadPuntosMundial;
    this.cantidadPuntosSig = cantidadPuntosSig;
    this.cantidadPuntosNivel = cantidadPuntosNivel;
    this.tipoPolla = "";
    this.puntosTrivia = puntosTrivia;
    this.imagenUsuario = imagenUsuario;
    this.duracionPreguntaTrivia = duracionPreguntaTrivia;
}

Mundial.prototype.init = function () {
    let porcentajeAvance = 1;

    if (this.cantidadPuntosSig !== '') {
        porcentajeAvance = (this.cantidadPuntosMundial - this.cantidadPuntosNivel) / (this.cantidadPuntosSig - this.cantidadPuntosNivel);
        if (porcentajeAvance >= 1) {
            porcentajeAvance = 1;
        }
    } else {
        $('.q-points__level').css('display', 'none');
    }

    progressCircularBar(Math.round(porcentajeAvance * 100));
    eventCuartosPolla((round) => {
        this.eventPollaFases(this, round);
    }, "pendiente", this);
    eventPollaNextButton(this, () => {
        this.eventButtonPolla(this);
    }, (round) => {
        this.eventPollaFases(this, round);
    }, (cambiarPag) => {
        this.eventGuardarPolla(this, cambiarPag);
    }, "pendiente");

    this.obtenerRanking();
    this.eventButtonMenu();
    this.obtenerPronostico(() => {});
    this.obtenerTrivia();
    this.buttonRefreshRanking.on('click', (e) => {
        this.obtenerRanking();
    });
};

Mundial.prototype.obtenerTrivia = function () {

    var obj = this;
    obj.contentPregunta.empty();
    $("#q-quiz").find(".q-stepper").empty();

    Promotick.ajax.get({
        url : obj.uri + 'mundial/obtenerTrivia',
        messageError : false,
        before : function() {},
        success : function(response) {
            $("#q-quiz").find(".q-quiz__next").off("click");
            try {
                if (response.estadoTriviaParticipante !== "PEN") {
                    obj.resumen = response.data.result;
                    obj.mostrarResumenTriviaFinalizado(obj);
                    return;
                }
                obj.mostrarTriviaActiva(obj, response);
            } catch (e) {
                console.log(e);
            }
        }
    });
};

Mundial.prototype.mostrarTriviaActiva = function (obj, data) {
    obj.respuestas = [];
    obj.preguntas = data.result[0];

    $('.q-quiz__step').removeAttr('style');
    $('.q-quiz__step').removeClass('q-quiz__step--active');
    $('.q-quiz__step[data-step="info"]').addClass('q-quiz__step--active');
    $('.q-quiz__step[data-step="info"]').css('display', 'block');

    if (obj.preguntas.length) {
        $("#message-quiz").css("display", "flex");
        $("#text-message-quiz").text(`Tienes ${obj.preguntas.length} preguntas para seguir acumulando puntos y canjearlos por más beneficios.`);
    }
    let htmlIndicesPreguntas = '<div class="q-step q-step--active" data-step="info"><i class="icon-info"></i></div>';

    for (var i = 0; i < obj.preguntas.length; i++) {
        const pregunta = obj.preguntas[i];

        const alternativas = JSON.parse(pregunta.jsonAlternativasTrivia);

        htmlIndicesPreguntas = htmlIndicesPreguntas + '<div class="q-step" data-step="question-' + (i + 1) + '"><span>' + (i + 1) + "</span></div>";

        var alternativasHtml = "";
        for (var j = 0; j < alternativas.length; j++) {
            alternativasHtml = alternativasHtml + '<button type="button" data-descripcion="' + alternativas[j].descripcion + '" data-id="' + alternativas[j].id + '" class="q-question__option q-question-option-' + (i + 1) + '">' + '<span class="q-option__letter">' + alternativas[j].id + "</span>" + '<span class="q-option__name">' + alternativas[j].descripcion + "</span>" + "</button>";
        }

        obj.contentPregunta.append(obj.templatePregunta({
            className: i === 0 ? "q-question--active" : "", indice: i + 1, pregunta: pregunta.preguntaTrivia, opciones: alternativasHtml
        }));
    }
    htmlIndicesPreguntas = htmlIndicesPreguntas + '<div class="q-step" data-step="resume"><i class="icon-flag"></i></div>';
    $("#q-quiz").find(".q-stepper").append(htmlIndicesPreguntas);
    addQuizEvent();
    nextButtonTrivia(obj.preguntas.length, obj.duracionPreguntaTrivia, (counter) => {
        return obj.eventQuizNextButton(counter, obj);
    }, () => {
        obj.eventResumeFinal(obj);
    }, () => {
        return obj.eventStart(obj);
    });
};

Mundial.prototype.mostrarResumenTriviaFinalizado = function (obj) {
    let htmlIndicesPreguntas = '<div class="q-step" data-step="info"><i class="icon-info"></i></div>';
    let classStep;
    $("#q-quiz").find(".q-stepper").empty();

    if (obj.resumen[0].length === 0 && obj.resumen[2].length) {
        for (var i = 0; i < obj.resumen[2].length; i++) {
            htmlIndicesPreguntas = htmlIndicesPreguntas +
                `<div class="q-step q-step--failed" data-step="question-${ i + 1
                }"><span>${i + 1}</span></div>`;
        }
    } else {
        for (var i = 0; i < obj.resumen[0].length; i++) {
            if (obj.resumen[0][i].acertoRespuesta === 1) {
                classStep = "q-step--success";
            } else {
                classStep = "q-step--failed";
            }
            htmlIndicesPreguntas = htmlIndicesPreguntas +
                `<div class="q-step ${classStep}" data-step="question-${i + 1}"><span>${ i + 1
                }</span></div>`;
        }
    }
    htmlIndicesPreguntas = htmlIndicesPreguntas + '<div class="q-step q-step--active" data-step="resume"><i class="icon-flag"></i></div>';
    $("#q-quiz").find(".q-stepper").append(htmlIndicesPreguntas);
    $(".q-quiz__step").removeClass("q-quiz__step--active");
    $(".q-quiz__step").css("display", "none");
    $(".q-quiz__step").css("opacity", 0);
    $("#step_resumen").addClass("q-quiz__step--active");
    $("#step_resumen").css("opacity", 1);
    $("#step_resumen").css("display", "block");

    $("#cantidad-acertadas").html(obj.resumen[1].cantidadAcertoTrivia ? obj.resumen[1].cantidadAcertoTrivia : 0);
    $("#cantidad-erradas").html(obj.resumen[1].cantidadErradoTrivia ? obj.resumen[1].cantidadErradoTrivia : 0);
    $("#cantidad-puntos-obtenidos").html(obj.resumen[1].cantidadPuntosTrivia ? obj.resumen[1].cantidadPuntosTrivia : 0);

    $("#cantidad-acertadas-tot").html(obj.resumen[1].cantidadAcertoTotal ? obj.resumen[1].cantidadAcertoTotal : 0);
    $("#cantidad-erradas-tot").html(obj.resumen[1].cantidadErradoTotal ? obj.resumen[1].cantidadErradoTotal : 0);
    $("#cantidad-puntos-obtenidos-tot").html(obj.resumen[1].cantidadPuntosTotal ? obj.resumen[1].cantidadPuntosTotal : 0);

    return;
};

function capitalizeFirstLetter(string) {
    let result = string && string.length ? string.split(' ') : '';
    return result.map((r) => `${r.charAt(0).toUpperCase()}${r.slice(1).toLowerCase()}`).join(' ');
}

Mundial.prototype.obtenerRanking = function () {
    var obj = this;
    obj.contentRanking.empty();

    Promotick.render.get({
        url : obj.uri + 'mundial/obtenerRanking',
        context : obj.contentRanking
        // response.data.forEach((e, i) => {
        //     obj.contentRanking.append(obj.templateRanking({
        //         indice: i + 1, usuario: capitalizeFirstLetter(e.nombres), nivel: e.nivel, puntos: e.puntosMundial,
        //     }));
        // });
    });

};

Mundial.prototype.matchActivo = function (partidosDiaActivo) {
    var obj = this;

    Promotick.render.post({
        url : obj.uri + 'mundial/matchActivo',
        data : JSON.stringify(partidosDiaActivo),
        context : obj.contentMatch
    });

};

Mundial.prototype.obtenerPronostico = function (callback) {
    var obj = this;
    obj.pronosticos = null;
    $(".btn-pronostico-guardar").off("click");
    $('#q-dates .q-match-pronosticos').empty();
    $('.q-forecast__matches').empty();
    destroyGlide();

    Promotick.ajax.get({
        url : obj.uri + 'mundial/obtenerPronostico',
        messageError : false,
        before : function() {},
        success : function(response) {
            if (response.status) {
                obj.pronosticos = response.data.result[0];
                obj.pronosticosParticipante = response.data.result[1];
                const fechas = [...new Set(obj.pronosticos.map(item => `${item.dia},${item.mes},${item.anio}`))];
                let fechasHtml = '';

                for (let i = 0; i < fechas.length; i++) {
                    const dia = fechas[i].split(",")[0].length === 1 ? `0${fechas[i].split(",")[0]}` : fechas[i].split(",")[0];
                    const mes = obj.obtenerMesPronostico(fechas[i].split(",")[1]);
                    fechasHtml = `${fechasHtml}<div data-indice="${i}" 
                        data-dia="${fechas[i].split(",")[0]}" 
                        data-mes="${fechas[i].split(",")[1]}" 
                        data-anio="${fechas[i].split(",")[2]}" class="q-calendar__date">
                        <b>${dia}</b>
                        <span>${mes}</span>
                    </div>`;
                }
                let indexActivo = 0;
                let indexData = 0;
                let flagFinalizadoPartidos = true;
                for (let i = 0; i < obj.pronosticos.length; i++) {
                    if (obj.pronosticos[i].partidoEstado === 'ACT' || !obj.pronosticos[i].partidoEstado) {
                        flagFinalizadoPartidos = false;
                        indexData = i;
                        break
                    }
                }

                if (flagFinalizadoPartidos) {
                    indexData = obj.pronosticos.length - 1;
                }


                const partidosDiaActivo  = obj.pronosticos.filter((pr) => obj.pronosticos[indexData].dia === pr.dia
                    && obj.pronosticos[indexData].mes === pr.mes && obj.pronosticos[indexData].anio === pr.anio);

                obj.matchActivo(partidosDiaActivo);

                // for (let i = 0; i < partidosDiaActivo.length && i < 2; i++) {
                //     const monthDatePartido = (obj.obtenerMesDescripcion(parseInt(partidosDiaActivo[i].mes)) || '').slice(0,3);
                //     $('.q-forecast__matches').append(obj.templateMatchInicio({
                //         indice: partidosDiaActivo[i].orden,
                //         nombreMatch: (partidosDiaActivo[i].grupoPartido ?
                //             capitalizeFirstLetter(partidosDiaActivo[i].grupoPartido) :
                //             partidosCodigo[partidosDiaActivo[i].codigoPartido]),
                //         fechaPartido: `${partidosDiaActivo[i].dia} de ${monthDatePartido}`,
                //         nombrePais1: partidosDiaActivo[i].nombrePaisMundial1,
                //         nombrePais2: partidosDiaActivo[i].nombrePaisMundial2,
                //         imagenPais1: partidosDiaActivo[i].imagenPaisMundial1,
                //         imagenPais2: partidosDiaActivo[i].imagenPaisMundial2,
                //     }));
                // }

                for (let j = 0; j < fechas.length; j++) {
                    if (`${obj.pronosticos[indexData].dia}` === fechas[j].split(',')[0] && `${obj.pronosticos[indexData].mes}` === fechas[j].split(',')[1] && `${obj.pronosticos[indexData].anio}` === fechas[j].split(',')[2]) {
                        indexActivo = j;
                        break;
                    }
                }

                for (let i = 0; i < obj.pronosticos.length; i++) {
                    for (let j = 0; j < obj.pronosticosParticipante.length; j++) {

                        if (obj.pronosticos[i].idPartidoMundial === obj.pronosticosParticipante[j].idPartidoMundial) {
                            obj.pronosticos[i].respuesta = obj.pronosticosParticipante[j];
                            continue;
                        }
                    }
                }

                $('.glide__slides').html(fechasHtml);
                obj.insertarPartidosPronosticos(fechas, obj.pronosticos);
                callback(indexActivo, obj.handlerPronosticos, obj, fechas.length);

                $('.btn-pronostico-guardar').click((e) => {
                    const datePartido = $('#q-dates .glide__slide--active').data();
                    const pronosticoEstado = obj.pronosticos.find(p => p.dia === `${datePartido.dia}` && p.mes === `${datePartido.mes}` && p.anio === `${datePartido.anio}`);
                    if (pronosticoEstado.respuesta || !pronosticoEstado.partidoEstado) {

                    } else if (pronosticoEstado.partidoEstado === 'FIN') {
                        obj.mostrarAlerta(3, 'Esta fecha ya no se encuentra activa. Recuerda que cada pronóstico ' +
                            'se activará 24 horas antes para que puedas registrar tu marcador.');
                    } else {
                        obj.guardarPronostico();
                    }

                });
            }

        }
    });
};

Mundial.prototype.buscarReplicas = function(array, partido, pais1, pais2) {
    var obj = this;
    var replica = false;

    $.each(array, function(i, e) {
        if (e.idPartidoMundial === partido && e.idPaisMundial1 === pais1 && e.idPaisMundial2 === pais2) {
            replica = true;
        }
    });

    return replica;
};

Mundial.prototype.guardarPronostico = function () {
    var obj = this;
    const datePartido = $('#q-dates .glide__slide--active').data();
    const partidoPronosticos = $(`.q-pronostico-${datePartido.dia}${datePartido.mes}${datePartido.anio}`);
    const pronosticosRegistrar = [];
    for (let i = 0; i < partidoPronosticos.length; i++) {
        const partidoTemporal = partidoPronosticos[i];

        var item = {
            idPartidoMundial: $(partidoTemporal).data('id'),
            idPaisMundial1: $(partidoTemporal).data('idPais1'),
            idPaisMundial2: $(partidoTemporal).data('idPais2'),
            scorePais1: $(partidoTemporal).find('.score-pais1').val(),
            scorePais2: $(partidoTemporal).find('.score-pais2').val()
        }
        if (!obj.buscarReplicas(pronosticosRegistrar, item.idPartidoMundial, item.idPaisMundial1, item.idPaisMundial2)) {
            pronosticosRegistrar.push(item);
        }
    }
    const pronosticoValidar = pronosticosRegistrar.find(e => { return !e.scorePais1 || !e.scorePais2 });
    if (pronosticoValidar) {
        return;
    }
    $('.btn-pronostico-guardar').prop('disabled', true);
    $.ajax({
        url: obj.uri + "mundial/registrarPronosticoParticipante",
        type: "post",
        data: JSON.stringify(pronosticosRegistrar),
        contentType: "application/json; charset=utf-8",
        dataType: "json",
        beforeSend: function () {},
        error: function (a, b) {
            $('.btn-pronostico-guardar').prop('disabled', false);
        },
        success: function (data) {
            $('.btn-pronostico-guardar').prop('disabled', false);
            if (data > 0) {
                obj.mostrarAlerta(1, 'Se registró exitosamente la información ingresada.');
                obj.obtenerPronostico((index, handlerPronosticos, obj, cantidadFechas) => {

                    startCalendar(index, handlerPronosticos, obj, cantidadFechas)
                })
            }
        }
    });
};

Mundial.prototype.limpiarAlerta = function () {
    $('#mensajeMostrarAlerta .close').click();
};

Mundial.prototype.mostrarAlerta = function (estado, mensaje) {
    var obj = this;
    var glyphicon = '';
    var alert = '';

    switch (parseInt(estado)) {
        case 1:
            alert = 'success';
            $('body').css('cursor', 'default');
            break;

        case 2:
            alert = 'info';
            $('body').css('cursor', 'wait');
            break;

        case 3:
            alert = 'danger';
            glyphicon = 'remove-circle';
            $('body').css('cursor', 'default');
            break;
    }

    var html = '<div class="alert alert-' + alert + ' alert-dismissable">';
    html += '<button type="button" class="close" data-dismiss="alert" aria-hidden="true">x</button>';
    html += '<span>' + mensaje + '</span>';
    html += '</div>';
    $('#mensajeMostrarAlerta').html(html);
    $('#mensajeMostrarAlerta').show();
};

Mundial.prototype.insertarPartidosPronosticos = function (fechas, data) {
    var obj = this;

    Promotick.render.post({
        url : obj.uri + 'mundial/partidosPronosticos',
        data : JSON.stringify(data),
        context : obj.contentPronosticos
    });

    // for (let i = 0; i < data.length; i++) {
    //     const partidoPronostico = data[i];
    //
    //     const monthDatePartido = obj.obtenerMesDescripcion(parseInt(partidoPronostico.mes));
    //     $("#q-dates .q-match-pronosticos").append(obj.templateMatchPronostico({
    //         idPartidoMundial: partidoPronostico.idPartidoMundial,
    //         nombrePais1: partidoPronostico.nombrePaisMundial1,
    //         imagenPais1: partidoPronostico.imagenPaisMundial1,
    //         idPais1: partidoPronostico.idPaisMundial1,
    //         nombrePais2: partidoPronostico.nombrePaisMundial2,
    //         imagenPais2: partidoPronostico.imagenPaisMundial2,
    //         idPais2: partidoPronostico.idPaisMundial2,
    //         fechaPartidoCodigo: `q-pronostico-${partidoPronostico.dia}${partidoPronostico.mes}${partidoPronostico.anio}`,
    //         nombreMatch: (partidoPronostico.grupoPartido ?
    //             capitalizeFirstLetter(partidoPronostico.grupoPartido) :
    //             partidosCodigo[partidoPronostico.codigoPartido]),
    //         fechaPartido: `${partidoPronostico.dia} ${monthDatePartido}`,
    //         respuesta: !!partidoPronostico.respuesta || partidoPronostico.partidoEstado === 'FIN' || !partidoPronostico.partidoEstado,
    //         scorePais1: partidoPronostico.respuesta ? partidoPronostico.respuesta.scorePais1 : '',
    //         scorePais2: partidoPronostico.respuesta ? partidoPronostico.respuesta.scorePais2 : '',
    //         indice: (i + 1)
    //     }));
    // }

};

Mundial.prototype.handlerPronosticos = function (obj, prevIndex, actualIndex) {
    if (prevIndex !== actualIndex) {
        const datePartido = $('#q-dates .glide__slide--active').data();
        $('.q-title-pronostico').html(`${datePartido.dia} ${obj.obtenerMesDescripcion(datePartido.mes)}`);
        $('.q-match-pronostico').css('opacity', '0');
        setTimeout(() => {
            $('.q-match-pronostico').css('display', 'none');
            $(`.q-pronostico-${datePartido.dia}${datePartido.mes}${datePartido.anio}`).css('display', 'flex');
            setTimeout(() => {
                $(`.q-pronostico-${datePartido.dia}${datePartido.mes}${datePartido.anio}`).css('opacity', '1');
            }, 250)
        }, 250)
    }
};

Mundial.prototype.eventGuardarPolla = function (obj, cambiarPag) {
    function registrarPolla() {
        cambiarPag();
        const {
            A1, A2, A3, A4, B1, B2, B3, B4, C1, C2, C3, C4, D1, D2, D3, D4, E1, E2, E3, E4, F1, F2, F3, F4, G1, G2, G3, G4, H1, H2, H3, H4,
        } = obj.obtenerGanadoresGrupos();

        obj.resultPolla.push({
            idPaisMundial: A1.id, nombrePais: A1.nombre, resultadoCodigo: "A1",
        });
        obj.resultPolla.push({
            idPaisMundial: A2.id, nombrePais: A2.nombre, resultadoCodigo: "A2",
        });
        obj.resultPolla.push({
            idPaisMundial: A3.id, nombrePais: A3.nombre, resultadoCodigo: "A3",
        });
        obj.resultPolla.push({
            idPaisMundial: A4.id, nombrePais: A4.nombre, resultadoCodigo: "A4",
        });

        obj.resultPolla.push({
            idPaisMundial: B1.id, nombrePais: B1.nombre, resultadoCodigo: "B1",
        });
        obj.resultPolla.push({
            idPaisMundial: B2.id, nombrePais: B2.nombre, resultadoCodigo: "B2",
        });
        obj.resultPolla.push({
            idPaisMundial: B3.id, nombrePais: B3.nombre, resultadoCodigo: "B3",
        });
        obj.resultPolla.push({
            idPaisMundial: B4.id, nombrePais: B4.nombre, resultadoCodigo: "B4",
        });

        obj.resultPolla.push({
            idPaisMundial: C1.id, nombrePais: C1.nombre, resultadoCodigo: "C1",
        });
        obj.resultPolla.push({
            idPaisMundial: C2.id, nombrePais: C2.nombre, resultadoCodigo: "C2",
        });
        obj.resultPolla.push({
            idPaisMundial: C3.id, nombrePais: C3.nombre, resultadoCodigo: "C3",
        });
        obj.resultPolla.push({
            idPaisMundial: C4.id, nombrePais: C4.nombre, resultadoCodigo: "C4",
        });

        obj.resultPolla.push({
            idPaisMundial: D1.id, nombrePais: D1.nombre, resultadoCodigo: "D1",
        });
        obj.resultPolla.push({
            idPaisMundial: D2.id, nombrePais: D2.nombre, resultadoCodigo: "D2",
        });
        obj.resultPolla.push({
            idPaisMundial: D3.id, nombrePais: D3.nombre, resultadoCodigo: "D3",
        });
        obj.resultPolla.push({
            idPaisMundial: D4.id, nombrePais: D4.nombre, resultadoCodigo: "D4",
        });

        obj.resultPolla.push({
            idPaisMundial: E1.id, nombrePais: E1.nombre, resultadoCodigo: "E1",
        });
        obj.resultPolla.push({
            idPaisMundial: E2.id, nombrePais: E2.nombre, resultadoCodigo: "E2",
        });
        obj.resultPolla.push({
            idPaisMundial: E3.id, nombrePais: E3.nombre, resultadoCodigo: "E3",
        });
        obj.resultPolla.push({
            idPaisMundial: E4.id, nombrePais: E4.nombre, resultadoCodigo: "E4",
        });

        obj.resultPolla.push({
            idPaisMundial: F1.id, nombrePais: F1.nombre, resultadoCodigo: "F1",
        });
        obj.resultPolla.push({
            idPaisMundial: F2.id, nombrePais: F2.nombre, resultadoCodigo: "F2",
        });
        obj.resultPolla.push({
            idPaisMundial: F3.id, nombrePais: F3.nombre, resultadoCodigo: "F3",
        });
        obj.resultPolla.push({
            idPaisMundial: F4.id, nombrePais: F4.nombre, resultadoCodigo: "F4",
        });

        obj.resultPolla.push({
            idPaisMundial: G1.id, nombrePais: G1.nombre, resultadoCodigo: "G1",
        });
        obj.resultPolla.push({
            idPaisMundial: G2.id, nombrePais: G2.nombre, resultadoCodigo: "G2",
        });
        obj.resultPolla.push({
            idPaisMundial: G3.id, nombrePais: G3.nombre, resultadoCodigo: "G3",
        });
        obj.resultPolla.push({
            idPaisMundial: G4.id, nombrePais: G4.nombre, resultadoCodigo: "G4",
        });

        obj.resultPolla.push({
            idPaisMundial: H1.id, nombrePais: H1.nombre, resultadoCodigo: "H1",
        });
        obj.resultPolla.push({
            idPaisMundial: H2.id, nombrePais: H2.nombre, resultadoCodigo: "H2",
        });
        obj.resultPolla.push({
            idPaisMundial: H3.id, nombrePais: H3.nombre, resultadoCodigo: "H3",
        });
        obj.resultPolla.push({
            idPaisMundial: H4.id, nombrePais: H4.nombre, resultadoCodigo: "H4",
        });

        const {A1B2, B1A2, C1D2, D1C2, E1F2, F1E2, G1H2, H1G2} = obj.obtenerGanadoresOctavos();

        obj.resultPolla.push({
            idPaisMundial1: A1.id, idPaisMundial2: B2.id, idPaisMundial: A1B2.id, nombrePais: A1B2.nombre, resultadoCodigo: "A1B2",
        });
        obj.resultPolla.push({
            idPaisMundial1: B1.id, idPaisMundial2: A2.id, idPaisMundial: B1A2.id, nombrePais: B1A2.nombre, resultadoCodigo: "B1A2",
        });
        obj.resultPolla.push({
            idPaisMundial1: C1.id, idPaisMundial2: D2.id, idPaisMundial: C1D2.id, nombrePais: C1D2.nombre, resultadoCodigo: "C1D2",
        });
        obj.resultPolla.push({
            idPaisMundial1: D1.id, idPaisMundial2: C2.id, idPaisMundial: D1C2.id, nombrePais: D1C2.nombre, resultadoCodigo: "D1C2",
        });
        obj.resultPolla.push({
            idPaisMundial1: E1.id, idPaisMundial2: F2.id, idPaisMundial: E1F2.id, nombrePais: E1F2.nombre, resultadoCodigo: "E1F2",
        });
        obj.resultPolla.push({
            idPaisMundial1: F1.id, idPaisMundial2: E2.id, idPaisMundial: F1E2.id, nombrePais: F1E2.nombre, resultadoCodigo: "F1E2",
        });
        obj.resultPolla.push({
            idPaisMundial1: G1.id, idPaisMundial2: H2.id, idPaisMundial: G1H2.id, nombrePais: G1H2.nombre, resultadoCodigo: "G1H2",
        });
        obj.resultPolla.push({
            idPaisMundial1: H1.id, idPaisMundial2: G2.id, idPaisMundial: H1G2.id, nombrePais: H1G2.nombre, resultadoCodigo: "H1G2",
        });

        const {A1B2_C1D2, B1A2_D1C2, E1F2_G1H2, F1E2_H1G2} = obj.obtenerGanadoresCuartos();

        obj.resultPolla.push({
            idPaisMundial1: A1B2.id, idPaisMundial2: C1D2.id, idPaisMundial: A1B2_C1D2.id, nombrePais: A1B2_C1D2.nombre, resultadoCodigo: "A1B2-C1D2",
        });
        obj.resultPolla.push({
            idPaisMundial1: B1A2.id, idPaisMundial2: D1C2.id, idPaisMundial: B1A2_D1C2.id, nombrePais: B1A2_D1C2.nombre, resultadoCodigo: "B1A2-D1C2",
        });
        obj.resultPolla.push({
            idPaisMundial1: E1F2.id, idPaisMundial2: G1H2.id, idPaisMundial: E1F2_G1H2.id, nombrePais: E1F2_G1H2.nombre, resultadoCodigo: "E1F2-G1H2",
        });
        obj.resultPolla.push({
            idPaisMundial1: F1E2.id, idPaisMundial2: H1G2.id, idPaisMundial: F1E2_H1G2.id, nombrePais: F1E2_H1G2.nombre, resultadoCodigo: "F1E2-H1G2",
        });

        const {A1B2_C1D2_E1F2_G1H2, B1A2_D1C2_F1E2_H1G2} = obj.obtenerGanadoresSemi();

        obj.resultPolla.push({
            idPaisMundial1: A1B2_C1D2.id, idPaisMundial2: E1F2_G1H2.id, idPaisMundial: A1B2_C1D2_E1F2_G1H2.id, nombrePais: A1B2_C1D2_E1F2_G1H2.nombre, resultadoCodigo: "A1B2-C1D2-E1F2-G1H2",
        });
        obj.resultPolla.push({
            idPaisMundial1: B1A2_D1C2.id, idPaisMundial2: F1E2_H1G2.id, idPaisMundial: B1A2_D1C2_F1E2_H1G2.id, nombrePais: B1A2_D1C2_F1E2_H1G2.nombre, resultadoCodigo: "B1A2-D1C2-F1E2-H1G2",
        });

        const ganador = obj.obtenerGanadoreFinal();
        obj.resultPolla.push({
            idPaisMundial1: A1B2_C1D2_E1F2_G1H2.id, idPaisMundial2: B1A2_D1C2_F1E2_H1G2.id, idPaisMundial: ganador.id, nombrePais: ganador.nombre, resultadoCodigo: ganador.resultadoCodigo,
        });

        $.ajax({
            url: obj.uri + "mundial/registrarPollaParticipante",
            type: "post",
            data: JSON.stringify(obj.resultPolla),
            contentType: "application/json; charset=utf-8",
            dataType: "json",
            beforeSend: function () {},
            error: function (a, b) {},
            success: function (data) {
                console.log(data);
            },
        });
        obj.resultPolla = [];
    }

    $(".q-polla__next_final").off("click");
    $("#previa").modal();
    $(".q-polla__next_final").on("click", (e) => {
        registrarPolla();
    });
};


Mundial.prototype.mostrarPollaActiva = function (obj, data) {
    obj.tipoPolla = "pendiente";

    const listaGrupoPais = [];
    const listaPaises = data.result[0];
    obj.partidos = data.result[1];

    for (let i = 0; i < listaPaises.length; i++) {
        let pais = listaPaises[i];
        if (!listaGrupoPais.length || !listaGrupoPais.find((e) => e.idGrupoMundial === pais.idGrupoMundial)) {
            listaGrupoPais.push({
                idGrupoMundial: pais.idGrupoMundial, codigoGrupoMundial: pais.codigoGrupoMundial, nombreGrupoMundial: pais.nombreGrupoMundial, paises: [],
            });
        }
        if (listaGrupoPais.find((e) => e.idGrupoMundial === pais.idGrupoMundial)) {
            listaGrupoPais
                .find((e) => e.idGrupoMundial === pais.idGrupoMundial)
                .paises.push(pais);
        }
    }

    obj.paises = listaGrupoPais;
    for (let i = 0; i < listaGrupoPais.length; i++) {
        let contentPaises = "";

        for (let j = 0; j < listaGrupoPais[i].paises.length; j++) {
            contentPaises = contentPaises + `<li data-imagen="${listaGrupoPais[i].paises[j].imagenPaisMundial}" 
                                data-grupo-codigo="${listaGrupoPais[i].paises[j].codigoGrupoMundial}"
                                data-mapa="${listaGrupoPais[i].paises[j].mapaPaisMundial}"
                                data-imagen-m="${listaGrupoPais[i].paises[j].imagenMPaisMundial}"
                                data-id="${listaGrupoPais[i].paises[j].idPaisMundial}" 
                                class="q-group__country">
                            <img src="${obj.uriResources}${listaGrupoPais[i].paises[j].imagenPaisMundial}" alt="${listaGrupoPais[i].paises[j].nombrePaisMundial}">
                            <span>${listaGrupoPais[i].paises[j].nombrePaisMundial}</span>
                        </li>`;
        }
        $("#grupos-pais-container").append(obj.templateGrupoMundial({
            nombreGrupoMundial: listaGrupoPais[i].nombreGrupoMundial,
            idGrupoMundial: listaGrupoPais[i].idGrupoMundial,
            codigoGrupoMundial: listaGrupoPais[i].codigoGrupoMundial,
            paises: contentPaises,
        }));
        eventPaisesDrag();
    }
};

Mundial.prototype.mostrarResumenPollaFinalizado = function (obj, data) {
    obj.tipoPolla = "finalizado";

    $(".q-polla__next").html("Revisar");
    obj.partidos = data.result[2];

    const resultadosPolla = obj.rellenarPaises(data.result[0], data.result[1]);

    //GRUPOS
    const gruposCodigos = ["A", "B", "C", "D", "E", "F", "G", "H"];

    for (let j = 0; j < gruposCodigos.length; j++) {
        const paises = resultadosPolla.filter((p) => p.resultadoCodigo.length === 2 && p.resultadoCodigo[0] === gruposCodigos[j]);
        let paisesAHtml = "";
        for (let i = 0; i < paises.length; i++) {
            paisesAHtml = `${paisesAHtml}<li data-imagen="${paises[i].paisMundial.imagenPaisMundial}" 
                            data-grupo-codigo="${paises[i].paisMundial.codigoGrupoMundial}"
                            data-mapa="${paises[i].paisMundial.mapaPaisMundial}"
                            data-imagen-m="${paises[i].paisMundial.imagenMPaisMundial}"
                            data-id="${paises[i].paisMundial.idPaisMundial}" 
                            class="q-group__country">
                        <img src="${obj.uriResources}${paises[i].paisMundial.imagenPaisMundial}" alt="${paises[i].paisMundial.nombrePaisMundial}">
                        <span>${paises[i].paisMundial.nombrePaisMundial}</span>
                        </li>`;
        }
        $("#grupos-pais-container").append(obj.templateGrupoMundial({
            nombreGrupoMundial: paises[0].paisMundial.nombreGrupoMundial,
            idGrupoMundial: paises[0].paisMundial.idGrupoMundial,
            codigoGrupoMundial: paises[0].paisMundial.codigoGrupoMundial,
            paises: paisesAHtml,
        }));
    }

    //OCTAVOS

    $("#match-octavos").empty();
    const A1B2 = [obj.obtPais(resultadosPolla, "A1B2", 1), obj.obtPais(resultadosPolla, "A1B2", 2), obj.obtPais(resultadosPolla, "A1B2", ""),];
    $("#match-octavos").append(obj.templateMatch({
        nombrePais1: A1B2[0].nombrePaisMundial,
        imagenPais1: A1B2[0].imagenPaisMundial,
        idPais1: A1B2[0].idPaisMundial,
        ganador1: A1B2[2].idPaisMundial === A1B2[0].idPaisMundial,
        nombrePais2: A1B2[1].nombrePaisMundial,
        imagenPais2: A1B2[1].imagenPaisMundial,
        idPais2: A1B2[1].idPaisMundial,
        ganador2: A1B2[2].idPaisMundial === A1B2[1].idPaisMundial,
        codigoMatch: "A1B2",
        nombreMatch: partidosCodigo["A1B2"],
        fechaPartido: obj.obtenerFechaPartido(obj.partidos, 'A1B2'),
        indice: 1,
    }));
    const B1A2 = [obj.obtPais(resultadosPolla, "B1A2", 1), obj.obtPais(resultadosPolla, "B1A2", 2), obj.obtPais(resultadosPolla, "B1A2", ""),];
    $("#match-octavos").append(obj.templateMatch({
        nombrePais1: B1A2[0].nombrePaisMundial,
        imagenPais1: B1A2[0].imagenPaisMundial,
        idPais1: B1A2[0].idPaisMundial,
        ganador1: B1A2[2].idPaisMundial === B1A2[0].idPaisMundial,
        nombrePais2: B1A2[1].nombrePaisMundial,
        imagenPais2: B1A2[1].imagenPaisMundial,
        idPais2: B1A2[1].idPaisMundial,
        ganador2: B1A2[2].idPaisMundial === B1A2[1].idPaisMundial,
        codigoMatch: "B1A2",
        nombreMatch: partidosCodigo["B1A2"],
        fechaPartido: obj.obtenerFechaPartido(obj.partidos, 'B1A2'),
        indice: 2,
    }));
    const C1D2 = [obj.obtPais(resultadosPolla, "C1D2", 1), obj.obtPais(resultadosPolla, "C1D2", 2), obj.obtPais(resultadosPolla, "C1D2", ""),];
    $("#match-octavos").append(obj.templateMatch({
        nombrePais1: C1D2[0].nombrePaisMundial,
        imagenPais1: C1D2[0].imagenPaisMundial,
        idPais1: C1D2[0].idPaisMundial,
        ganador1: C1D2[2].idPaisMundial === C1D2[0].idPaisMundial,
        nombrePais2: C1D2[1].nombrePaisMundial,
        imagenPais2: C1D2[1].imagenPaisMundial,
        idPais2: C1D2[1].idPaisMundial,
        ganador2: C1D2[2].idPaisMundial === C1D2[1].idPaisMundial,
        codigoMatch: "C1D2",
        nombreMatch: partidosCodigo["C1D2"],
        fechaPartido: obj.obtenerFechaPartido(obj.partidos, 'C1D2'),
        indice: 3,
    }));
    const D1C2 = [obj.obtPais(resultadosPolla, "D1C2", 1), obj.obtPais(resultadosPolla, "D1C2", 2), obj.obtPais(resultadosPolla, "D1C2", ""),];
    $("#match-octavos").append(obj.templateMatch({
        nombrePais1: D1C2[0].nombrePaisMundial,
        imagenPais1: D1C2[0].imagenPaisMundial,
        idPais1: D1C2[0].idPaisMundial,
        ganador1: D1C2[2].idPaisMundial === D1C2[0].idPaisMundial,
        nombrePais2: D1C2[1].nombrePaisMundial,
        imagenPais2: D1C2[1].imagenPaisMundial,
        idPais2: D1C2[1].idPaisMundial,
        ganador2: D1C2[2].idPaisMundial === D1C2[1].idPaisMundial,
        codigoMatch: "D1C2",
        nombreMatch: partidosCodigo["D1C2"],
        fechaPartido: obj.obtenerFechaPartido(obj.partidos, 'D1C2'),
        indice: 4,
    }));
    const E1F2 = [obj.obtPais(resultadosPolla, "E1F2", 1), obj.obtPais(resultadosPolla, "E1F2", 2), obj.obtPais(resultadosPolla, "E1F2", ""),];
    $("#match-octavos").append(obj.templateMatch({
        nombrePais1: E1F2[0].nombrePaisMundial,
        imagenPais1: E1F2[0].imagenPaisMundial,
        idPais1: E1F2[0].idPaisMundial,
        ganador1: E1F2[2].idPaisMundial === E1F2[0].idPaisMundial,
        nombrePais2: E1F2[1].nombrePaisMundial,
        imagenPais2: E1F2[1].imagenPaisMundial,
        idPais2: E1F2[1].idPaisMundial,
        ganador2: E1F2[2].idPaisMundial === E1F2[1].idPaisMundial,
        codigoMatch: "E1F2",
        nombreMatch: partidosCodigo["E1F2"],
        fechaPartido: obj.obtenerFechaPartido(obj.partidos, 'E1F2'),
        indice: 5,
    }));
    const F1E2 = [obj.obtPais(resultadosPolla, "F1E2", 1), obj.obtPais(resultadosPolla, "F1E2", 2), obj.obtPais(resultadosPolla, "F1E2", ""),];
    $("#match-octavos").append(obj.templateMatch({
        nombrePais1: F1E2[0].nombrePaisMundial,
        imagenPais1: F1E2[0].imagenPaisMundial,
        idPais1: F1E2[0].idPaisMundial,
        ganador1: F1E2[2].idPaisMundial === F1E2[0].idPaisMundial,
        nombrePais2: F1E2[1].nombrePaisMundial,
        imagenPais2: F1E2[1].imagenPaisMundial,
        idPais2: F1E2[1].idPaisMundial,
        ganador2: F1E2[2].idPaisMundial === F1E2[1].idPaisMundial,
        codigoMatch: "F1E2",
        nombreMatch: partidosCodigo["F1E2"],
        fechaPartido: obj.obtenerFechaPartido(obj.partidos, 'F1E2'),
        indice: 6,
    }));
    const G1H2 = [obj.obtPais(resultadosPolla, "G1H2", 1), obj.obtPais(resultadosPolla, "G1H2", 2), obj.obtPais(resultadosPolla, "G1H2", ""),];
    $("#match-octavos").append(obj.templateMatch({
        nombrePais1: G1H2[0].nombrePaisMundial,
        imagenPais1: G1H2[0].imagenPaisMundial,
        idPais1: G1H2[0].idPaisMundial,
        ganador1: G1H2[2].idPaisMundial === G1H2[0].idPaisMundial,
        nombrePais2: G1H2[1].nombrePaisMundial,
        imagenPais2: G1H2[1].imagenPaisMundial,
        idPais2: G1H2[1].idPaisMundial,
        ganador2: G1H2[2].idPaisMundial === G1H2[1].idPaisMundial,
        codigoMatch: "G1H2",
        nombreMatch: partidosCodigo["G1H2"],
        fechaPartido: obj.obtenerFechaPartido(obj.partidos, 'G1H2'),
        indice: 7,
    }));
    const H1G2 = [obj.obtPais(resultadosPolla, "H1G2", 1), obj.obtPais(resultadosPolla, "H1G2", 2), obj.obtPais(resultadosPolla, "H1G2", ""),];
    $("#match-octavos").append(obj.templateMatch({
        nombrePais1: H1G2[0].nombrePaisMundial,
        imagenPais1: H1G2[0].imagenPaisMundial,
        idPais1: H1G2[0].idPaisMundial,
        ganador1: H1G2[2].idPaisMundial === H1G2[0].idPaisMundial,
        nombrePais2: H1G2[1].nombrePaisMundial,
        imagenPais2: H1G2[1].imagenPaisMundial,
        idPais2: H1G2[1].idPaisMundial,
        ganador2: H1G2[2].idPaisMundial === H1G2[1].idPaisMundial,
        codigoMatch: "H1G2",
        nombreMatch: partidosCodigo["H1G2"],
        fechaPartido: obj.obtenerFechaPartido(obj.partidos, 'H1G2'),
        indice: 8,
    }));

    //CUARTOS
    $("#match-cuartos").empty();

    const A1B2_C1D2 = [obj.obtPais(resultadosPolla, "A1B2-C1D2", 1), obj.obtPais(resultadosPolla, "A1B2-C1D2", 2), obj.obtPais(resultadosPolla, "A1B2-C1D2", ""),];
    $("#match-cuartos").append(obj.templateMatch({
        nombrePais1: A1B2_C1D2[0].nombrePaisMundial,
        imagenPais1: A1B2_C1D2[0].imagenPaisMundial,
        idPais1: A1B2_C1D2[0].idPaisMundial,
        ganador1: A1B2_C1D2[2].idPaisMundial === A1B2_C1D2[0].idPaisMundial,
        nombrePais2: A1B2_C1D2[1].nombrePaisMundial,
        imagenPais2: A1B2_C1D2[1].imagenPaisMundial,
        idPais2: A1B2_C1D2[1].idPaisMundial,
        ganador2: A1B2_C1D2[2].idPaisMundial === A1B2_C1D2[1].idPaisMundial,
        codigoMatch: "A1B2-C1D2",
        nombreMatch: partidosCodigo["A1B2-C1D2"],
        fechaPartido: obj.obtenerFechaPartido(obj.partidos, 'A1B2-C1D2'),
        indice: 9,
    }));

    const B1A2_D1C2 = [obj.obtPais(resultadosPolla, "B1A2-D1C2", 1), obj.obtPais(resultadosPolla, "B1A2-D1C2", 2), obj.obtPais(resultadosPolla, "B1A2-D1C2", ""),];
    $("#match-cuartos").append(obj.templateMatch({
        nombrePais1: B1A2_D1C2[0].nombrePaisMundial,
        imagenPais1: B1A2_D1C2[0].imagenPaisMundial,
        idPais1: B1A2_D1C2[0].idPaisMundial,
        ganador1: B1A2_D1C2[2].idPaisMundial === B1A2_D1C2[0].idPaisMundial,
        nombrePais2: B1A2_D1C2[1].nombrePaisMundial,
        imagenPais2: B1A2_D1C2[1].imagenPaisMundial,
        idPais2: B1A2_D1C2[1].idPaisMundial,
        ganador2: B1A2_D1C2[2].idPaisMundial === B1A2_D1C2[1].idPaisMundial,
        codigoMatch: "B1A2-D1C2",
        nombreMatch: partidosCodigo["B1A2-D1C2"],
        fechaPartido: obj.obtenerFechaPartido(obj.partidos, 'B1A2-D1C2'),
        indice: 10,
    }));

    const E1F2_G1H2 = [obj.obtPais(resultadosPolla, "E1F2-G1H2", 1), obj.obtPais(resultadosPolla, "E1F2-G1H2", 2), obj.obtPais(resultadosPolla, "E1F2-G1H2", ""),];
    $("#match-cuartos").append(obj.templateMatch({
        nombrePais1: E1F2_G1H2[0].nombrePaisMundial,
        imagenPais1: E1F2_G1H2[0].imagenPaisMundial,
        idPais1: E1F2_G1H2[0].idPaisMundial,
        ganador1: E1F2_G1H2[2].idPaisMundial === E1F2_G1H2[0].idPaisMundial,
        nombrePais2: E1F2_G1H2[1].nombrePaisMundial,
        imagenPais2: E1F2_G1H2[1].imagenPaisMundial,
        idPais2: E1F2_G1H2[1].idPaisMundial,
        ganador2: E1F2_G1H2[2].idPaisMundial === E1F2_G1H2[1].idPaisMundial,
        codigoMatch: "E1F2-G1H2",
        nombreMatch: partidosCodigo["E1F2-G1H2"],
        fechaPartido: obj.obtenerFechaPartido(obj.partidos, 'E1F2-G1H2'),
        indice: 11,
    }));

    const F1E2_H1G2 = [obj.obtPais(resultadosPolla, "F1E2-H1G2", 1), obj.obtPais(resultadosPolla, "F1E2-H1G2", 2), obj.obtPais(resultadosPolla, "F1E2-H1G2", ""),];
    $("#match-cuartos").append(obj.templateMatch({
        nombrePais1: F1E2_H1G2[0].nombrePaisMundial,
        imagenPais1: F1E2_H1G2[0].imagenPaisMundial,
        idPais1: F1E2_H1G2[0].idPaisMundial,
        ganador1: F1E2_H1G2[2].idPaisMundial === F1E2_H1G2[0].idPaisMundial,
        nombrePais2: F1E2_H1G2[1].nombrePaisMundial,
        imagenPais2: F1E2_H1G2[1].imagenPaisMundial,
        idPais2: F1E2_H1G2[1].idPaisMundial,
        ganador2: F1E2_H1G2[2].idPaisMundial === F1E2_H1G2[1].idPaisMundial,
        codigoMatch: "F1E2-H1G2",
        nombreMatch: partidosCodigo["F1E2-H1G2"],
        fechaPartido: obj.obtenerFechaPartido(obj.partidos, 'F1E2-H1G2'),
        indice: 12,
    }));

    //SEMI
    $("#match-semi").empty();

    const semi1 = [obj.obtPais(resultadosPolla, "A1B2-C1D2-E1F2-G1H2", 1), obj.obtPais(resultadosPolla, "A1B2-C1D2-E1F2-G1H2", 2), obj.obtPais(resultadosPolla, "A1B2-C1D2-E1F2-G1H2", ""),];
    $("#match-semi").append(obj.templateMatch({
        nombrePais1: semi1[0].nombrePaisMundial,
        imagenPais1: semi1[0].imagenPaisMundial,
        idPais1: semi1[0].idPaisMundial,
        ganador1: semi1[2].idPaisMundial === semi1[0].idPaisMundial,
        nombrePais2: semi1[1].nombrePaisMundial,
        imagenPais2: semi1[1].imagenPaisMundial,
        idPais2: semi1[1].idPaisMundial,
        ganador2: semi1[2].idPaisMundial === semi1[1].idPaisMundial,
        codigoMatch: "A1B2-C1D2-E1F2-G1H2",
        nombreMatch: partidosCodigo["A1B2-C1D2-E1F2-G1H2"],
        fechaPartido: obj.obtenerFechaPartido(obj.partidos, 'A1B2-C1D2-E1F2-G1H2'),
        indice: 13,
    }));

    const semi2 = [obj.obtPais(resultadosPolla, "B1A2-D1C2-F1E2-H1G2", 1), obj.obtPais(resultadosPolla, "B1A2-D1C2-F1E2-H1G2", 2), obj.obtPais(resultadosPolla, "B1A2-D1C2-F1E2-H1G2", ""),];
    $("#match-semi").append(obj.templateMatch({
        nombrePais1: semi2[0].nombrePaisMundial,
        imagenPais1: semi2[0].imagenPaisMundial,
        idPais1: semi2[0].idPaisMundial,
        ganador1: semi2[2].idPaisMundial === semi2[0].idPaisMundial,
        nombrePais2: semi2[1].nombrePaisMundial,
        imagenPais2: semi2[1].imagenPaisMundial,
        idPais2: semi2[1].idPaisMundial,
        ganador2: semi2[2].idPaisMundial === semi2[1].idPaisMundial,
        codigoMatch: "B1A2-D1C2-F1E2-H1G2",
        nombreMatch: partidosCodigo["B1A2-D1C2-F1E2-H1G2"],
        fechaPartido: obj.obtenerFechaPartido(obj.partidos, 'B1A2-D1C2-F1E2-H1G2'),
        indice: 14,
    }));

    //FINAL
    $("#match-final").empty();
    const final = [obj.obtPais(resultadosPolla, "A1B2_C1D2_E1F2_G1H2-B1A2_D1C2_F1E2_H1G2", 1), obj.obtPais(resultadosPolla, "A1B2_C1D2_E1F2_G1H2-B1A2_D1C2_F1E2_H1G2", 2), obj.obtPais(resultadosPolla, "A1B2_C1D2_E1F2_G1H2-B1A2_D1C2_F1E2_H1G2", ""),];
    $("#match-final").append(obj.templateMatch({
        nombrePais1: final[0].nombrePaisMundial,
        imagenPais1: final[0].imagenPaisMundial,
        idPais1: final[0].idPaisMundial,
        ganador1: final[2].idPaisMundial === final[0].idPaisMundial,
        imagenMPais1: final[0].imagenMPaisMundial,
        mapaPais1: final[0].mapaPaisMundial,
        nombrePais2: final[1].nombrePaisMundial,
        imagenPais2: final[1].imagenPaisMundial,
        idPais2: final[1].idPaisMundial,
        ganador2: final[2].idPaisMundial === final[1].idPaisMundial,
        imagenMPais2: final[1].imagenMPaisMundial,
        mapaPais2: final[1].mapaPaisMundial,
        codigoMatch: "A1B2_C1D2_E1F2_G1H2-B1A2_D1C2_F1E2_H1G2",
        nombreMatch: partidosCodigo["A1B2_C1D2_E1F2_G1H2-B1A2_D1C2_F1E2_H1G2"],
        fechaPartido: obj.obtenerFechaPartido(obj.partidos, 'A1B2_C1D2_E1F2_G1H2-B1A2_D1C2_F1E2_H1G2'),
        indice: 15,
    }));

    const ballSquares = $("#q-polla").find(".q-match__score-square");
    ballSquares.each((i, sq) => {
        sq.removeEventListener("click", eventBallAction);
    });
};

Mundial.prototype.listarPaisMundial = function () {
    var obj = this;
    $("#grupos-pais-container").empty();
    $.ajax({
        url: obj.uri + "trivia/obtenerPolla",
        type: "get",
        contentType: "application/json; charset=utf-8",
        dataType: "json",
        beforeSend: function () {},
        error: function (a, b) {},
        success: function (data) {
            // const pageActive = $(".q-polla__step").filter((i, e) => $(e).css("display") === "block")[0];
            // changePage(pageActive, $('.q-polla__step[data-step="info"]')[0], "q-page--active", () => {
            $("#q-polla .q-polla__step").removeAttr("style");
            $("#q-polla .q-polla__step").removeClass('q-polla__step--active');
            $('#q-polla .q-polla__step[data-step="info"]').addClass('q-polla__step--active');
            $('#q-polla .q-polla__step[data-step="info"]').css('display', 'block');
            $('#q-polla .q-polla__step[data-step="info"]').css('opacity', '1');
            // if (1) {
            if (data.estadoPollaParticipante === "EXP") {
                $('#q-polla .q-step').addClass('q-step--passed');
                $('#q-polla .q-step[data-step="info"]').removeClass('q-step--active');
                $('#q-polla .q-step[data-step="resume"]').removeClass('q-step--passed');
                $('#q-polla .q-step[data-step="resume"]').addClass('q-step--active');
                $('#q-polla .q-polla__step').css('display', 'none');
                $('#q-polla .q-polla__step').css('opacity', '0');

                $('#q-polla .q-polla__step[data-step="resume"]').addClass('q-polla__step--active');
                $('#q-polla .q-polla__step[data-step="resume"]').css('display', 'block');
                $('#q-polla .q-polla__step[data-step="resume"]').css('opacity', '1');
                $('.q-winner').css('display', 'none');

                $('.q-description').html('El tiempo para completar la polla mundialista ' + 'finalizó</br></br>Te invitamos a continuar participando en el resto de dinamícas' + ' que tenemos para ti.')

            } else {
                $('.q-winner').css('display', 'flex');

                $("#q-polla .q-step").removeClass("q-step--passed");
                pollaStep = "info";
                const currentStepper = document.querySelector(`#q-polla .q-step[data-step="info"]`);
                currentStepper.classList.add("q-step--active");

                const otherStepper = getSiblings(currentStepper);
                otherStepper.forEach((stepper) => stepper.classList.remove("q-step--active"));

                const prevStepper = getPreviousSiblings(currentStepper);
                prevStepper.forEach((stepper) => stepper.classList.add("q-step--passed"));
            }
            // }
            // });
            cambiarFasePolla("octavos", $('.q-rounds__btn[data-round="octavos"]')[0], () => {}, "finalizado");

            if (data.estadoPollaParticipante === "PEN"){
                obj.mostrarPollaActiva(obj, data);
            } else if (data.estadoPollaParticipante === "FIN") {
                obj.mostrarResumenPollaFinalizado(obj, data);
            } else if (data.estadoPollaParticipante === "EXP") {
                $('#q-polla .q-step').addClass('q-step--passed');
                $('#q-polla .q-step[data-step="resume"]').removeClass('q-step--passed');
                $('#q-polla .q-step[data-step="resume"]').addClass('q-step--active');

            }
        },
    });
};

Mundial.prototype.obtPais = function (paises, codigo, indice) {
    return paises.find((p) => p.resultadoCodigo === codigo)[`paisMundial${indice}`];
};

Mundial.prototype.obtenerFechaPartido = function (partidos, codigoPartido) {
    const partidoMundial = partidos.find((p) => p.codigoPartido === codigoPartido);
    if (partidoMundial) {
        const datePartido = new Date(partidoMundial.fechaPartido);
        const monthDatePartido = datePartido.getMonth();
        let monthPartido = '';
        switch (monthDatePartido) {
            case 0: monthPartido = 'Enero'; break;
            case 1: monthPartido = 'Febrero'; break;
            case 2: monthPartido = 'Marzo'; break;
            case 3: monthPartido = 'Abril'; break;
            case 4: monthPartido = 'Mayo'; break;
            case 5: monthPartido = 'Junio'; break;
            case 6: monthPartido = 'Julio'; break;
            case 7: monthPartido = 'Agosto'; break;
            case 8: monthPartido = 'Septiembre'; break;
            case 9: monthPartido = 'Octubre'; break;
            case 10: monthPartido = 'Noviembre'; break;
            case 11: monthPartido = 'Diciembre'; break;
        }
        return `${datePartido.getDate()} ${monthPartido}`;
    }
    return '';
}


Mundial.prototype.obtenerMesDescripcion = function (mes) {
    let monthPartido;
    if (mes) {
        switch (mes) {
            case 1: monthPartido = 'Enero'; break;
            case 2: monthPartido = 'Febrero'; break;
            case 3: monthPartido = 'Marzo'; break;
            case 4: monthPartido = 'Abril'; break;
            case 5: monthPartido = 'Mayo'; break;
            case 6: monthPartido = 'Junio'; break;
            case 7: monthPartido = 'Julio'; break;
            case 8: monthPartido = 'Agosto'; break;
            case 9: monthPartido = 'Septiembre'; break;
            case 10: monthPartido = 'Octubre'; break;
            case 11: monthPartido = 'Noviembre'; break;
            case 12: monthPartido = 'Diciembre'; break;
        }
        return monthPartido;
    }
    return '';
}

Mundial.prototype.obtenerMesPronostico = function (mes) {
    let monthPartido;
    if (mes) {
        switch (mes) {
            case '1': monthPartido = 'ENE'; break;
            case '2': monthPartido = 'FEB'; break;
            case '3': monthPartido = 'MAR'; break;
            case '4': monthPartido = 'ABR'; break;
            case '5': monthPartido = 'MAY'; break;
            case '6': monthPartido = 'JUN'; break;
            case '7': monthPartido = 'JUL'; break;
            case '8': monthPartido = 'AGO'; break;
            case '9': monthPartido = 'SEP'; break;
            case '10': monthPartido = 'OCT'; break;
            case '11': monthPartido = 'NOV'; break;
            case '12': monthPartido = 'DIC'; break;
        }
        return monthPartido;
    }
    return '';
}

Mundial.prototype.rellenarPaises = function (resultadosPolla, listaPaises) {
    return resultadosPolla.map((p) => {
        if (p.idPaisMundial) {
            p.paisMundial = listaPaises.find((pa) => pa.idPaisMundial === p.idPaisMundial);
        }

        if (p.idPaisMundial1) {
            p.paisMundial1 = listaPaises.find((pa) => pa.idPaisMundial === p.idPaisMundial1);
        }

        if (p.idPaisMundial2) {
            p.paisMundial2 = listaPaises.find((pa) => pa.idPaisMundial === p.idPaisMundial2);
        }
        return p;
    });
};

Mundial.prototype.registrarTriviaParticipante = async function (data, callback) {
    var obj = this;

    var promise = new Promise((resolve, reject) => {
        $.ajax({
            url: obj.uri + "trivia/registrarTriviaParticipante",
            type: "post",
            data: JSON.stringify(data),
            contentType: "application/json; charset=utf-8",
            dataType: "json",
            beforeSend: function () {},
            error: function (a, b) {
                reject(a);
            },
            success: function (data) {
                try {
                    if (callback) {
                        callback();
                    }
                } catch (e) {
                    console.log(e);
                }
                resolve(data);
            },
        });
    });
    let respuesta;
    try {
        respuesta = await promise;
    } catch (e) {
        console.log(e);
    }
    return respuesta;
};

Mundial.prototype.eventQuizNextButton = function (counter, obj) {
    const respuestaSeleccionada = $(".q-question-option-" + counter + ".q-question__option--active");
    const idTriviaMundial = obj.preguntas[counter - 1].idTriviaMundial;
    if (!respuestaSeleccionada.length) {
        obj.respuestas.push({
            idRespuesta: null, descripcionRespuesta: "NO SELECCIONO", acertoRespuesta: 0, idTriviaMundial,
        });
        return false;
    }

    const respIndice = $($(".q-question-option-" + counter + ".q-question__option--active")[0]);

    if (respIndice.data("id") === obj.preguntas[counter - 1].idAlternativaCorrecta) {
        obj.respuestas.push({
            idRespuesta: respIndice.data("id"), descripcionRespuesta: `${respIndice.data("descripcion")}`, idTriviaMundial, acertoRespuesta: 1,
        });
        return true;
    }

    obj.respuestas.push({
        idRespuesta: respIndice.data("id"), descripcionRespuesta: `${respIndice.data("descripcion")}`, idTriviaMundial, acertoRespuesta: 0,
    });
    return false;
};

Mundial.prototype.eventResumeFinal = async function (obj) {
    obj.flagTrivia = false;
    window.onbeforeunload = function () {
        return undefined;
    };
    const respuestasAcertadas = obj.respuestas.filter((e) => e.acertoRespuesta).length;
    $("#cantidad-acertadas").html(respuestasAcertadas);
    $("#cantidad-erradas").html(obj.preguntas.length - respuestasAcertadas);
    $("#cantidad-puntos-obtenidos").html(respuestasAcertadas === obj.preguntas.length ? `${obj.puntosTrivia}` : "0");

    const payload = {
        listAlternativaRespuesta: obj.respuestas,
        idTriviaMundial: obj.preguntas[0].idTriviaMundial,
        idTriviaGrupo: obj.preguntas[0].idTriviaGrupoMundial,
        estadoTriviaGrupoParticipante: "FIN",
        cantidadRespuestasCorrectas: respuestasAcertadas,
        cantidadPreguntas: obj.preguntas.length,
    };

    const result = await obj.registrarTriviaParticipante(payload);

    $("#cantidad-acertadas-tot").html(result.cantidadAcertoTotal ? result.cantidadAcertoTotal : 0);
    $("#cantidad-erradas-tot").html(result.cantidadErradoTotal ? result.cantidadErradoTotal : 0);
    $("#cantidad-puntos-obtenidos-tot").html(result.cantidadPuntosTotal ? result.cantidadPuntosTotal : 0);
    return result;
};

Mundial.prototype.obtenerGanadoresGrupos = function () {

    const gruposL = ['A','B','C','D','E','F','G','H'];
    let resultado = {};
    for (let j = 0; j < gruposL.length; j++) {
        for (let i = 1; i < 5; i++) {
            resultado = {
                ...resultado,
                [`${gruposL[j]}${i}`]: {
                    id: $($(`#grupo-pais-${gruposL[j]}`).find(".q-group__country")[i - 1]).data("id"),
                    imagen: $($(`#grupo-pais-${gruposL[j]}`).find(".q-group__country")[i - 1]).data("imagen"),
                    nombre: $($(`#grupo-pais-${gruposL[j]}`).find(".q-group__country")[i - 1]).text().trim(),
                    mapa: $($(`#grupo-pais-${gruposL[j]}`).find(".q-group__country")[i - 1]).data("mapa"),
                    imagenM: $($(`#grupo-pais-${gruposL[j]}`).find(".q-group__country")[i - 1]).data("imagenM")
                }
            }

        }
    }

    return resultado;
};

Mundial.prototype.obtenerGanadoresOctavos = function () {
    let A1B2 = $("#A1B2")
        .find(".icon-futbol")
        .filter((e) => {
            return $($("#A1B2").find(".icon-futbol")[e]).css("opacity") === "1";
        });
    A1B2 = {
        id: A1B2.data("id"), imagen: A1B2.data("imagen"), nombre: A1B2.data("nombre"), mapa: A1B2.data("imagenMapa"), imagenM: A1B2.data("imagenM")
    };

    let B1A2 = $("#B1A2")
        .find(".icon-futbol")
        .filter((e) => {
            return $($("#B1A2").find(".icon-futbol")[e]).css("opacity") === "1";
        });
    B1A2 = {
        id: B1A2.data("id"), imagen: B1A2.data("imagen"), nombre: B1A2.data("nombre"), mapa: B1A2.data("imagenMapa"), imagenM: B1A2.data("imagenM")
    };

    let C1D2 = $("#C1D2")
        .find(".icon-futbol")
        .filter((e) => {
            return $($("#C1D2").find(".icon-futbol")[e]).css("opacity") === "1";
        });
    C1D2 = {
        id: C1D2.data("id"), imagen: C1D2.data("imagen"), nombre: C1D2.data("nombre"), mapa: C1D2.data("imagenMapa"), imagenM: C1D2.data("imagenM")
    };

    let D1C2 = $("#D1C2")
        .find(".icon-futbol")
        .filter((e) => {
            return $($("#D1C2").find(".icon-futbol")[e]).css("opacity") === "1";
        });
    D1C2 = {
        id: D1C2.data("id"), imagen: D1C2.data("imagen"), nombre: D1C2.data("nombre"), mapa: D1C2.data("imagenMapa"), imagenM: D1C2.data("imagenM")
    };

    let E1F2 = $("#E1F2")
        .find(".icon-futbol")
        .filter((e) => {
            return $($("#E1F2").find(".icon-futbol")[e]).css("opacity") === "1";
        });
    E1F2 = {
        id: E1F2.data("id"), imagen: E1F2.data("imagen"), nombre: E1F2.data("nombre"), mapa: E1F2.data("imagenMapa"), imagenM: E1F2.data("imagenM")
    };

    let F1E2 = $("#F1E2")
        .find(".icon-futbol")
        .filter((e) => {
            return $($("#F1E2").find(".icon-futbol")[e]).css("opacity") === "1";
        });
    F1E2 = {
        id: F1E2.data("id"), imagen: F1E2.data("imagen"), nombre: F1E2.data("nombre"), mapa: F1E2.data("imagenMapa"), imagenM: F1E2.data("imagenM")
    };

    let G1H2 = $("#G1H2")
        .find(".icon-futbol")
        .filter((e) => {
            return $($("#G1H2").find(".icon-futbol")[e]).css("opacity") === "1";
        });
    G1H2 = {
        id: G1H2.data("id"), imagen: G1H2.data("imagen"), nombre: G1H2.data("nombre"), mapa: G1H2.data("imagenMapa"), imagenM: G1H2.data("imagenM")
    };

    let H1G2 = $("#H1G2")
        .find(".icon-futbol")
        .filter((e) => {
            return $($("#H1G2").find(".icon-futbol")[e]).css("opacity") === "1";
        });
    H1G2 = {
        id: H1G2.data("id"), imagen: H1G2.data("imagen"), nombre: H1G2.data("nombre"), mapa: H1G2.data("imagenMapa"), imagenM: H1G2.data("imagenM")
    };

    return {A1B2, B1A2, C1D2, D1C2, E1F2, F1E2, G1H2, H1G2};
};

Mundial.prototype.obtenerGanadoresCuartos = function () {
    let A1B2_C1D2 = $("#A1B2-C1D2")
        .find(".icon-futbol")
        .filter((e) => {
            return $($("#A1B2-C1D2").find(".icon-futbol")[e]).css("opacity") === "1";
        });
    A1B2_C1D2 = {
        id: A1B2_C1D2.data("id"), imagen: A1B2_C1D2.data("imagen"), nombre: A1B2_C1D2.data("nombre"), mapa: A1B2_C1D2.data("imagenMapa"), imagenM: A1B2_C1D2.data("imagenM")
    };

    let B1A2_D1C2 = $("#B1A2-D1C2")
        .find(".icon-futbol")
        .filter((e) => {
            return $($("#B1A2-D1C2").find(".icon-futbol")[e]).css("opacity") === "1";
        });
    B1A2_D1C2 = {
        id: B1A2_D1C2.data("id"), imagen: B1A2_D1C2.data("imagen"), nombre: B1A2_D1C2.data("nombre"), mapa: B1A2_D1C2.data("imagenMapa"), imagenM: B1A2_D1C2.data("imagenM")
    };

    let E1F2_G1H2 = $("#E1F2-G1H2")
        .find(".icon-futbol")
        .filter((e) => {
            return $($("#E1F2-G1H2").find(".icon-futbol")[e]).css("opacity") === "1";
        });
    E1F2_G1H2 = {
        id: E1F2_G1H2.data("id"), imagen: E1F2_G1H2.data("imagen"), nombre: E1F2_G1H2.data("nombre"), mapa: E1F2_G1H2.data("imagenMapa"), imagenM: E1F2_G1H2.data("imagenM")
    };

    let F1E2_H1G2 = $("#F1E2-H1G2")
        .find(".icon-futbol")
        .filter((e) => {
            return $($("#F1E2-H1G2").find(".icon-futbol")[e]).css("opacity") === "1";
        });
    F1E2_H1G2 = {
        id: F1E2_H1G2.data("id"), imagen: F1E2_H1G2.data("imagen"), nombre: F1E2_H1G2.data("nombre"), mapa: F1E2_H1G2.data("imagenMapa"), imagenM: F1E2_H1G2.data("imagenM")
    };

    return {A1B2_C1D2, B1A2_D1C2, E1F2_G1H2, F1E2_H1G2};
};

Mundial.prototype.obtenerGanadoresSemi = function () {
    let A1B2_C1D2_E1F2_G1H2 = $("#A1B2-C1D2-E1F2-G1H2")
        .find(".icon-futbol")
        .filter((e) => {
            return ($($("#A1B2-C1D2-E1F2-G1H2").find(".icon-futbol")[e]).css("opacity") === "1");
        });
    A1B2_C1D2_E1F2_G1H2 = {
        id: A1B2_C1D2_E1F2_G1H2.data("id"), imagen: A1B2_C1D2_E1F2_G1H2.data("imagen"), nombre: A1B2_C1D2_E1F2_G1H2.data("nombre"), mapa: A1B2_C1D2_E1F2_G1H2.data("imagenMapa"), imagenM: A1B2_C1D2_E1F2_G1H2.data("imagenM")
    };

    let B1A2_D1C2_F1E2_H1G2 = $("#B1A2-D1C2-F1E2-H1G2")
        .find(".icon-futbol")
        .filter((e) => {
            return ($($("#B1A2-D1C2-F1E2-H1G2").find(".icon-futbol")[e]).css("opacity") === "1");
        });
    B1A2_D1C2_F1E2_H1G2 = {
        id: B1A2_D1C2_F1E2_H1G2.data("id"), imagen: B1A2_D1C2_F1E2_H1G2.data("imagen"), nombre: B1A2_D1C2_F1E2_H1G2.data("nombre"), mapa: B1A2_D1C2_F1E2_H1G2.data("imagenMapa"), imagenM: B1A2_D1C2_F1E2_H1G2.data("imagenM")
    };

    return {A1B2_C1D2_E1F2_G1H2, B1A2_D1C2_F1E2_H1G2};
};

Mundial.prototype.obtenerGanadoreFinal = function () {
    const ganador = $("#A1B2_C1D2_E1F2_G1H2-B1A2_D1C2_F1E2_H1G2")
        .find(".icon-futbol")
        .filter((e) => {
            return ($($("#A1B2_C1D2_E1F2_G1H2-B1A2_D1C2_F1E2_H1G2").find(".icon-futbol")[e]).css("opacity") === "1");
        });
    return {
        id: ganador.data("id"),
        imagen: ganador.data("imagen"),
        nombre: ganador.data("nombre"),
        resultadoCodigo: "A1B2_C1D2_E1F2_G1H2-B1A2_D1C2_F1E2_H1G2",
        mapa: ganador.data('imagenMapa'),
        imagenM: ganador.data('imagenM'),
    };
};

Mundial.prototype.eventButtonPolla = function (obj) {
    $("#match-octavos").empty();
    const {A1, A2, B1, B2, C1, C2, D1, D2, E1, E2, F1, F2, G1, G2, H1, H2} = obj.obtenerGanadoresGrupos();

    $("#match-octavos").append(obj.templateMatch({
        nombrePais1: A1.nombre,
        imagenPais1: A1.imagen,
        idPais1: A1.id,
        mapaPais1: A1.mapa,
        imagenMPais1: A1.imagenM,
        mapaPais2: B2.mapa,
        imagenMPais2: B2.imagenM,
        nombrePais2: B2.nombre,
        imagenPais2: B2.imagen,
        idPais2: B2.id,
        codigoMatch: "A1B2",
        nombreMatch: partidosCodigo["A1B2"],
        fechaPartido: obj.obtenerFechaPartido(obj.partidos, 'A1B2'),
        indice: 1,
    }));
    $("#match-octavos").append(obj.templateMatch({
        nombrePais1: B1.nombre,
        imagenPais1: B1.imagen,
        idPais1: B1.id,
        mapaPais1: B1.mapa,
        imagenMPais1: B1.imagenM,
        mapaPais2: A2.mapa,
        imagenMPais2: A2.imagenM,
        nombrePais2: A2.nombre,
        imagenPais2: A2.imagen,
        idPais2: A2.id,
        codigoMatch: "B1A2",
        nombreMatch: partidosCodigo["B1A2"],
        fechaPartido: obj.obtenerFechaPartido(obj.partidos, 'B1A2'),
        indice: 2,
    }));
    $("#match-octavos").append(obj.templateMatch({
        nombrePais1: C1.nombre,
        imagenPais1: C1.imagen,
        idPais1: C1.id,
        mapaPais1: C1.mapa,
        imagenMPais1: C1.imagenM,
        mapaPais2: D2.mapa,
        imagenMPais2: D2.imagenM,
        nombrePais2: D2.nombre,
        imagenPais2: D2.imagen,
        idPais2: D2.id,
        codigoMatch: "C1D2",
        nombreMatch: partidosCodigo["C1D2"],
        fechaPartido: obj.obtenerFechaPartido(obj.partidos, 'C1D2'),
        indice: 3,
    }));
    $("#match-octavos").append(obj.templateMatch({
        nombrePais1: D1.nombre,
        imagenPais1: D1.imagen,
        idPais1: D1.id,
        mapaPais1: D1.mapa,
        imagenMPais1: D1.imagenM,
        mapaPais2: C2.mapa,
        imagenMPais2: C2.imagenM,
        nombrePais2: C2.nombre,
        imagenPais2: C2.imagen,
        idPais2: C2.id,
        codigoMatch: "D1C2",
        nombreMatch: partidosCodigo["D1C2"],
        fechaPartido: obj.obtenerFechaPartido(obj.partidos, 'D1C2'),
        indice: 4,
    }));
    $("#match-octavos").append(obj.templateMatch({
        nombrePais1: E1.nombre,
        imagenPais1: E1.imagen,
        idPais1: E1.id,
        mapaPais1: E1.mapa,
        imagenMPais1: E1.imagenM,
        mapaPais2: F2.mapa,
        imagenMPais2: F2.imagenM,
        nombrePais2: F2.nombre,
        imagenPais2: F2.imagen,
        idPais2: F2.id,
        codigoMatch: "E1F2",
        nombreMatch: partidosCodigo["E1F2"],
        fechaPartido: obj.obtenerFechaPartido(obj.partidos, 'E1F2'),
        indice: 5,
    }));
    $("#match-octavos").append(obj.templateMatch({
        nombrePais1: F1.nombre,
        imagenPais1: F1.imagen,
        idPais1: F1.id,
        mapaPais1: F1.mapa,
        imagenMPais1: F1.imagenM,
        mapaPais2: E2.mapa,
        imagenMPais2: E2.imagenM,
        nombrePais2: E2.nombre,
        imagenPais2: E2.imagen,
        idPais2: E2.id,
        codigoMatch: "F1E2",
        nombreMatch: partidosCodigo["F1E2"],
        fechaPartido: obj.obtenerFechaPartido(obj.partidos, 'F1E2'),
        indice: 6,
    }));
    $("#match-octavos").append(obj.templateMatch({
        nombrePais1: G1.nombre,
        imagenPais1: G1.imagen,
        idPais1: G1.id,
        mapaPais1: G1.mapa,
        imagenMPais1: G1.imagenM,
        mapaPais2: H2.mapa,
        imagenMPais2: H2.imagenM,
        nombrePais2: H2.nombre,
        imagenPais2: H2.imagen,
        idPais2: H2.id,
        codigoMatch: "G1H2",
        nombreMatch: partidosCodigo["G1H2"],
        fechaPartido: obj.obtenerFechaPartido(obj.partidos, 'G1H2'),
        indice: 7,
    }));
    $("#match-octavos").append(obj.templateMatch({
        nombrePais1: H1.nombre,
        imagenPais1: H1.imagen,
        idPais1: H1.id,
        mapaPais1: H1.mapa,
        imagenMPais1: H1.imagenM,
        mapaPais2: G2.mapa,
        imagenMPais2: G2.imagenM,
        nombrePais2: G2.nombre,
        imagenPais2: G2.imagen,
        idPais2: G2.id,
        codigoMatch: "H1G2",
        nombreMatch: partidosCodigo["H1G2"],
        fechaPartido: obj.obtenerFechaPartido(obj.partidos, 'H1G2'),
        indice: 8,
    }));
};

Mundial.prototype.eventStart = async function (obj) {
    obj.flagTrivia = true;
    window.onbeforeunload = function () {
        return "Are you sure you want to leave?";
    };
    const payload = {
        listAlternativaRespuesta: obj.respuestas,
        idTriviaMundial: obj.preguntas[0].idTriviaMundial,
        idTriviaGrupo: obj.preguntas[0].idTriviaGrupoMundial,
        estadoTriviaGrupoParticipante: "INI",
        cantidadRespuestasCorrectas: null,
        cantidadPreguntas: null,
    };
    return await obj.registrarTriviaParticipante(payload, () => { $('#message-quiz').css('display', 'none'); });
};

const partidosCodigo = {
    A1B2: "1A VS 2B",
    B1A2: "1B VS 2A",
    C1D2: "1C VS 2D",
    D1C2: "1D VS 2C",
    E1F2: "1E VS 2F",
    F1E2: "1F VS 2E",
    G1H2: "1G VS 2H",
    H1G2: "1H VS 2G",
    "A1B2-C1D2": "1AB2 VS 1C2D",
    "B1A2-D1C2": "1B2A VS 1D2C",
    "E1F2-G1H2": "1E2F VS 1G2H",
    "F1E2-H1G2": "1F2E VS 1H2G",
    "A1B2-C1D2-E1F2-G1H2": "1A2B/1C2D VS 1E2F/1G2H",
    "B1A2-D1C2-F1E2-H1G2": "1B2A/1D2C VS 1F2E/1H2G",
    "TERCER_PUESTO": "Tercer Puesto",
    //"A1B2_C1D2_E1F2_G1H2-B1A2_D1C2_F1E2_H1G2": "1A2B/1C2D/1E2F/1G2H VS 1B2A/1D2C/1F2E/1H2G",
    "A1B2_C1D2_E1F2_G1H2-B1A2_D1C2_F1E2_H1G2": "Final",
};

Mundial.prototype.eventPollaFases = function (obj, round) {

    if (round == "cuartos") {
        $("#match-cuartos").empty();

        const {A1B2, B1A2, C1D2, D1C2, E1F2, F1E2, G1H2, H1G2} = obj.obtenerGanadoresOctavos();

        $("#match-cuartos").append(obj.templateMatch({
            nombrePais1: A1B2.nombre,
            imagenPais1: A1B2.imagen,
            idPais1: A1B2.id,
            mapaPais1: A1B2.mapa,
            imagenMPais1: A1B2.imagenM,
            mapaPais2: C1D2.mapa,
            imagenMPais2: C1D2.imagenM,
            nombrePais2: C1D2.nombre,
            imagenPais2: C1D2.imagen,
            idPais2: C1D2.id,
            codigoMatch: "A1B2-C1D2",
            nombreMatch: partidosCodigo["A1B2-C1D2"],
            fechaPartido: obj.obtenerFechaPartido(obj.partidos, 'A1B2-C1D2'),
            indice: 9,
        }));
        $("#match-cuartos").append(obj.templateMatch({
            nombrePais1: B1A2.nombre,
            imagenPais1: B1A2.imagen,
            idPais1: B1A2.id,
            mapaPais1: B1A2.mapa,
            imagenMPais1: B1A2.imagenM,
            mapaPais2: D1C2.mapa,
            imagenMPais2: D1C2.imagenM,
            nombrePais2: D1C2.nombre,
            imagenPais2: D1C2.imagen,
            idPais2: D1C2.id,
            codigoMatch: "B1A2-D1C2",
            nombreMatch: partidosCodigo["B1A2-D1C2"],
            fechaPartido: obj.obtenerFechaPartido(obj.partidos, 'B1A2-D1C2'),
            indice: 10,
        }));
        $("#match-cuartos").append(obj.templateMatch({
            nombrePais1: E1F2.nombre,
            imagenPais1: E1F2.imagen,
            idPais1: E1F2.id,
            mapaPais1: E1F2.mapa,
            imagenMPais1: E1F2.imagenM,
            mapaPais2: G1H2.mapa,
            imagenMPais2: G1H2.imagenM,
            nombrePais2: G1H2.nombre,
            imagenPais2: G1H2.imagen,
            idPais2: G1H2.id,
            codigoMatch: "E1F2-G1H2",
            nombreMatch: partidosCodigo["E1F2-G1H2"],
            fechaPartido: obj.obtenerFechaPartido(obj.partidos, 'E1F2-G1H2'),
            indice: 11,
        }));
        $("#match-cuartos").append(obj.templateMatch({
            nombrePais1: F1E2.nombre,
            imagenPais1: F1E2.imagen,
            idPais1: F1E2.id,
            mapaPais1: F1E2.mapa,
            imagenMPais1: F1E2.imagenM,
            mapaPais2: H1G2.mapa,
            imagenMPais2: H1G2.imagenM,
            nombrePais2: H1G2.nombre,
            imagenPais2: H1G2.imagen,
            idPais2: H1G2.id,
            codigoMatch: "F1E2-H1G2",
            nombreMatch: partidosCodigo["F1E2-H1G2"],
            fechaPartido: obj.obtenerFechaPartido(obj.partidos, 'F1E2-H1G2'),
            indice: 12,
        }));

        ballActions($("#match-cuartos")[0]);
    } else if (round == "semi") {
        $("#match-semi").empty();

        const {A1B2_C1D2, B1A2_D1C2, E1F2_G1H2, F1E2_H1G2} = obj.obtenerGanadoresCuartos();

        $("#match-semi").append(obj.templateMatch({
            nombrePais1: A1B2_C1D2.nombre,
            imagenPais1: A1B2_C1D2.imagen,
            idPais1: A1B2_C1D2.id,
            mapaPais1: A1B2_C1D2.mapa,
            imagenMPais1: A1B2_C1D2.imagenM,
            mapaPais2: E1F2_G1H2.mapa,
            imagenMPais2: E1F2_G1H2.imagenM,
            nombrePais2: E1F2_G1H2.nombre,
            imagenPais2: E1F2_G1H2.imagen,
            idPais2: E1F2_G1H2.id,
            codigoMatch: "A1B2-C1D2-E1F2-G1H2",
            nombreMatch: partidosCodigo["A1B2-C1D2-E1F2-G1H2"],
            fechaPartido: obj.obtenerFechaPartido(obj.partidos, 'A1B2-C1D2-E1F2-G1H2'),
            indice: 13,
        }));
        $("#match-semi").append(obj.templateMatch({
            nombrePais1: B1A2_D1C2.nombre,
            imagenPais1: B1A2_D1C2.imagen,
            idPais1: B1A2_D1C2.id,
            mapaPais1: B1A2_D1C2.mapa,
            imagenMPais1: B1A2_D1C2.imagenM,
            mapaPais2: F1E2_H1G2.mapa,
            imagenMPais2: F1E2_H1G2.imagenM,
            nombrePais2: F1E2_H1G2.nombre,
            imagenPais2: F1E2_H1G2.imagen,
            idPais2: F1E2_H1G2.id,
            codigoMatch: "B1A2-D1C2-F1E2-H1G2",
            nombreMatch: partidosCodigo["B1A2-D1C2-F1E2-H1G2"],
            fechaPartido: obj.obtenerFechaPartido(obj.partidos, 'B1A2-D1C2-F1E2-H1G2'),
            indice: 14,
        }));

        ballActions($("#match-semi")[0]);
    } else {
        $("#match-final").empty();

        const {A1B2_C1D2_E1F2_G1H2, B1A2_D1C2_F1E2_H1G2} = obj.obtenerGanadoresSemi();

        $("#match-final").append(obj.templateMatch({
            nombrePais1: A1B2_C1D2_E1F2_G1H2.nombre,
            imagenPais1: A1B2_C1D2_E1F2_G1H2.imagen,
            idPais1: A1B2_C1D2_E1F2_G1H2.id,
            mapaPais1: A1B2_C1D2_E1F2_G1H2.mapa,
            imagenMPais1: A1B2_C1D2_E1F2_G1H2.imagenM,
            mapaPais2: B1A2_D1C2_F1E2_H1G2.mapa,
            imagenMPais2: B1A2_D1C2_F1E2_H1G2.imagenM,
            nombrePais2: B1A2_D1C2_F1E2_H1G2.nombre,
            imagenPais2: B1A2_D1C2_F1E2_H1G2.imagen,
            idPais2: B1A2_D1C2_F1E2_H1G2.id,
            codigoMatch: "A1B2_C1D2_E1F2_G1H2-B1A2_D1C2_F1E2_H1G2",
            nombreMatch: partidosCodigo["A1B2_C1D2_E1F2_G1H2-B1A2_D1C2_F1E2_H1G2"],
            fechaPartido: obj.obtenerFechaPartido(obj.partidos, 'A1B2_C1D2_E1F2_G1H2-B1A2_D1C2_F1E2_H1G2'),
            indice: 15,
        }));

        ballActions($("#match-final")[0]);
    }
};

Mundial.prototype.eventButtonMenu = function () {
    var obj = this;
    const menuButtons = document.querySelectorAll(".q-menu-item");
    const menuMobButtons = document.querySelectorAll(".q-menu-mob__item");
    const btns = [...menuButtons, ...menuMobButtons];
    btns.forEach((btn) => {
        btn.addEventListener("click", (e) => {

            $('#mensajeMostrarAlerta .close').click();
            if (obj.flagTrivia) {
                var answer = confirm("Are you sure you want to leave?");
                if (!answer) {
                    return;
                }
                endAnimationQuiz();
                window.onbeforeunload = function () {
                    return undefined;
                };
                obj.flagTrivia = false;
            }
            const t = e.target;
            const current = document.querySelector(".q-page--active");
            const page = document.querySelector(`#q-${t.getAttribute("data-page")}`);

            if (t.getAttribute("data-page") === "quiz") {
                obj.obtenerTrivia();
            } else if (t.getAttribute("data-page") === "polla") {
                obj.listarPaisMundial();
            } else if (t.getAttribute("data-page") === "dates") {
                obj.obtenerPronostico((index, handlerPronosticos, obj, cantidadFechas) => {
                    changePage(current, page, "q-page--active", () => {
                        siblings.forEach((sib) => {
                            sib.classList.remove("q-page--active");
                            sib.removeAttribute("style");
                        });
                        setMenuIndicator();
                        startCalendar(index, handlerPronosticos, obj, cantidadFechas)
                    });

                });
            }
            const siblings = getSiblings(page);

            if (t.getAttribute("data-page") !== "dates") {
                changePage(current, page, "q-page--active", () => {
                    siblings.forEach((sib) => {
                        sib.classList.remove("q-page--active");
                        sib.removeAttribute("style");
                    });
                    setMenuIndicator();
                });
            }
        });
    });
};
