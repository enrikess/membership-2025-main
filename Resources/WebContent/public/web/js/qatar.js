/** Obtiene todos los hermanos de un elemento del DOM */
const getSiblings = (element, selector = null) => {
    let siblings = []

    if (!element.parentNode) {
        return siblings
    }

    const allChilds = element.parentNode.children
    Array.from(allChilds, child => {
        if (child != element && !child.matches(selector)
        ) {
            siblings.push(child)
        }
    })

    return siblings
}

/** Obtiene todos los hermanos anteriores de un elemento del DOM */
function getPreviousSiblings(elem) {
    var sibs = [];
    while (elem = elem.previousSibling) {
        if (elem.nodeType === 3) continue; // text node
        sibs.push(elem);
    }
    return sibs;
}

/** Oculta una vista para mostrar otra */
const changePage = (from, to, activeClass, callback = () => { }) => {
    from.style.opacity = '0'
    setTimeout(() => {
            from.style.display = 'none'
            to.style.display = 'block'
            setTimeout(() => {
                    to.style.opacity = '1'
                    from.classList.remove(activeClass)
                    to.classList.add(activeClass)
                    callback()
                },
                250
            )
        },
        250
    )
}

/** Posiciona el indicador del menu activo */
const setMenuIndicator = () => {
    const nodeList = Array.prototype.slice.call(document.querySelectorAll('.q-page'))
    const active = document.querySelector('.q-page--active')
    const index = nodeList.indexOf(active)

    if (window.innerWidth > 1230) {
        const indicator = document.querySelector('.q-menu-indicator')
        indicator.style.top = `${62 * (index - 1)}px`
    }
    else {
        const indicator = document.querySelector('.q-menu-mob__indicator')
        const data = active.getAttribute('id').split('q-')[1]
        const buttonActive = document.querySelector(`.q-menu-mob__item[data-page="${data}"]`)
        const rects = buttonActive.getBoundingClientRect()
        indicator.style.left = `${rects.left - (window.innerWidth < 530 ? 10 : 15)}px`
    }
}

setMenuIndicator();


/** Se muestra la pantalla de trivias desde el acceso rápido */
const quizButton = document.querySelector('.q-quiz__start')
quizButton.addEventListener('click', () => {
    const quizMenuButton = document.querySelector('.q-menu-item[data-page="quiz"]')
    const event = new Event('click')
    quizMenuButton.dispatchEvent(event)
})

/** Se muestra la pantalla de trivias desde el acceso rápido */
const pronosticosButton = document.querySelector('.q-forecast__more')
pronosticosButton.addEventListener('click', () => {
    const quizMenuButton = document.querySelector('.q-menu-item[data-page="dates"]')
    const event = new Event('click')
    quizMenuButton.dispatchEvent(event)
})

/** Quiz timer */
let interval = null
let timeout = null
const startTimer = (duracionPreguntaTrivia) => {
    let timer = duracionPreguntaTrivia
    clearTimeout(timeout)
    clearInterval(interval)

    const fnInterval = () => {
        const tags = document.querySelectorAll('.q-timer__sec')
        const progressBar = document.querySelector('.q-timer__progress')

        progressBar.style.width = `calc(100% / ${duracionPreguntaTrivia} * ${timer})`
        tags.forEach(tag => {
            tag.innerHTML = timer
        })

        if (timer <= 0) {
            endTimer()
        }

        timer--
    }

    fnInterval()
    interval = setInterval(fnInterval, 1000)
}

const endTimer = () => {
    clearInterval(interval)
    interval = null
}

const endAnimationQuiz = () => {
    clearInterval(interval)
    interval = null
    clearTimeout(timeout);
    timeout = null;
}


const eventButtonTriviaNext = async (e, duracionPreguntaTrivia, cantidadPreguntas, eventNextButton, eventResumeFinal, eventStart) => {

    const t = e.target
    const fromPage = t.closest('.q-quiz__step')
    const step = fromPage.getAttribute('data-step')

    if (step === 'info') {
        const result = await eventStart();
        if (!result) {
            return;
        }
        console.log(`result ${result}`)
    }

    const getNextPage = () => {
        if (step == 'info'
        ) {
            nextStep = 'questions'
        }
        else
        if (step == 'questions' && questCounter < cantidadPreguntas) {
            nextStep = 'questions'
        }
        else {
            nextStep = 'resume'
        }

        return document.querySelector(`#q-quiz .q-quiz__step[data-step="${nextStep}"]`)
    }

    const toPage = getNextPage()
    const callback = () => {
        let activeId = null

        if (step == 'info') {
            questCounter = 1
            activeId = document.querySelector(`#q-quiz .q-step[data-step="question-${questCounter}"]`)

            const stepInfo = document.querySelector(`#q-quiz .q-step[data-step="info"]`)
            stepInfo.classList.add('q-step--passed')
        }
        else if (step == 'questions' && questCounter > 0 && questCounter < cantidadPreguntas) {
            questCounter++
            activeId = document.querySelector(`#q-quiz .q-step[data-step="question-${questCounter}"]`)
        }
        else if (step == 'questions' && questCounter == cantidadPreguntas) {
            activeId = document.querySelector('#q-quiz .q-step[data-step="resume"]')
        }

        if (nextStep != 'resume') {
            startTimer(duracionPreguntaTrivia)
            addQuizEvent()
            timeout = setTimeout(() => {
                endTimer()
                removeQuizEvent()
                $('#q-quiz-next-'+questCounter).click();
            }, (duracionPreguntaTrivia + 1) * 1000
            )
        }

        activeId.classList.add('q-step--active')
        const stepSibs = getSiblings(activeId)
        stepSibs.forEach(sib => sib.classList.remove('q-step--active')
        )
    }

    const answerMarker = (counter, exito) => {
        const marker = document.querySelector(`#q-quiz .q-step[data-step="question-${counter}"]`)

        if (exito) {
            marker.classList.add('q-step--success')
        }
        else {
            marker.classList.add('q-step--failed')
        }
    }

    if (fromPage != toPage) {
        endTimer()
        changePage(fromPage, toPage, 'q-quiz__step--active', () => {
            callback()

            if (step == 'questions') {
                const answerResult = eventNextButton(questCounter);
                clearTimeout(timeout)
                answerMarker(questCounter, answerResult)
                eventResumeFinal();
            }
        })
    } else {

        const answerResult = eventNextButton(questCounter);
        const fromQuestion = document.querySelector(`[data-question="${questCounter}"]`)
        const toQuestion = document.querySelector(`[data-question="${questCounter + 1}"]`)

        const defaultTimer = document.querySelectorAll('.q-timer__sec')
        defaultTimer.forEach(t => t.innerHTML = `${duracionPreguntaTrivia}`
        )

        const defaultProgress = document.querySelector('.q-timer__progress')
        defaultProgress.removeAttribute('style')

        if (questCounter == cantidadPreguntas - 1) {
            const nextButton = document.querySelector('.q-questions .q-quiz__next')
            nextButton.innerHTML = 'Finalizar'
        }

        endTimer()
        changePage(fromQuestion, toQuestion, 'q-question--active', () => {
            callback()

            if (step == 'questions'
            ) {
                answerMarker(questCounter - 1,answerResult)
            }
        })

    }

}
let questCounter = 0
let nextStep = ''
function nextButtonTrivia(cantidadPreguntas, duracionPreguntaTrivia, eventNextButton, eventResumeFinal, eventStart) {
    /** Cambia el contenido en la sección quiz */
    const nextButtons = document.querySelectorAll('#q-quiz .q-quiz__next')

    nextButtons.forEach(it => {
        $(it).on('click', (e) => {
            eventButtonTriviaNext(e, duracionPreguntaTrivia, cantidadPreguntas, eventNextButton, eventResumeFinal, eventStart)
        })
    })
}

var _eventHandlers = {}; // somewhere global

const addListener = (node, event, handler, capture = false) => {
    if (!(event in _eventHandlers)) {
        _eventHandlers[event] = []
    }
    // here we track the events and their nodes (note that we cannot
    // use node as Object keys, as they'd get coerced into a string
    _eventHandlers[event].push({ node: node, handler: handler, capture: capture })
    node.addEventListener(event, handler, capture)
}

const removeAllListeners = (targetNode, event) => {
    // remove listeners from the matching nodes
    _eventHandlers[event]
        .filter(({ node }) => node === targetNode)
        .forEach(({ node, handler, capture }) => node.removeEventListener(event, handler, capture))

    // update _eventHandlers global
    _eventHandlers[event] = _eventHandlers[event].filter(
        ({ node }) => node !== targetNode,
    )
}

const eventQuestFunction = (e) => {
    const t = e.target
    const sibs = getSiblings(t)
    t.classList.add('q-question__option--active')
    sibs.forEach(sib => sib.classList.remove('q-question__option--active')
    )
}

/** Choose quiz option */
const addQuizEvent = () => {
    const quizOptions = document.querySelectorAll('#q-quiz .q-question__option')
    quizOptions.forEach(opt => {
        opt.addEventListener('click', eventQuestFunction);
    })
}


const removeQuizEvent = () => {
    const quizOptions = document.querySelectorAll('#q-quiz .q-question__option')
    quizOptions.forEach(opt => {
        opt.removeEventListener('click', eventQuestFunction)
    })
}

/** Pollas */
let pollaStep = 'info'
const eventPollaNextButton = (obj, eventNextPolla, eventCuartos, eventRegistrarPolla, tipoPolla) => {
    const pollaNextButtons = document.querySelectorAll('#q-polla .q-polla__next')
    pollaNextButtons.forEach(btn => {
        btn.addEventListener('click', () => {

            let nextStep = ''
            if (pollaStep == 'info') {
                nextStep = 'round-1'
            }
            else if (pollaStep == 'round-1') {
                if (obj.tipoPolla === 'pendiente') {
                    eventNextPolla();
                }
                $('.q-rounds__btn').prop('disabled', true);
                nextStep = 'round-2'
            }
            else {
                nextStep = 'resume';
                const botonActivo = $('.q-rounds__btn--active')
                const roundActive = $($('.q-rounds__btn--active')[0]).data('round');
                if (obj.tipoPolla === 'pendiente') {
                    if (botonActivo.length) {
                        if (roundActive === 'octavos') {
                            const cantidadSel = $('#match-octavos').find('.icon-futbol').filter(function () {
                                return $(this).css('opacity') == 1
                            }).length
                            if (cantidadSel !== 8) return;
                            cambiarFasePolla('cuartos', $('.q-rounds__btn[data-round="cuartos"]')[0], eventCuartos, obj.tipoPolla)
                            return;
                        } else if (roundActive === 'cuartos') {
                            const cantidadSel = $('#match-cuartos').find('.icon-futbol').filter(function () {
                                return $(this).css('opacity') == 1
                            }).length
                            if (cantidadSel !== 4) return;
                            cambiarFasePolla('semi', $('.q-rounds__btn[data-round="semi"]')[0], eventCuartos, obj.tipoPolla)
                            return;
                        } else if (roundActive === 'semi') {
                            const cantidadSel = $('#match-semi').find('.icon-futbol').filter(function () {
                                return $(this).css('opacity') == 1
                            }).length
                            if (cantidadSel !== 2) return;
                            cambiarFasePolla('final', $('.q-rounds__btn[data-round="final"]')[0], eventCuartos, obj.tipoPolla)
                            return;
                        } else {
                            const cantidadSel = $('#match-final').find('.icon-futbol').filter(function () {
                                return $(this).css('opacity') == 1
                            }).length
                            if (cantidadSel !== 1) return;
                            const ganador = obj.obtenerGanadoreFinal();

                            eventRegistrarPolla( ()=> {
                                const fromPage = document.querySelector(`.q-polla__step[data-step="${pollaStep}"]`)
                                const toPage = document.querySelector(`.q-polla__step[data-step="${nextStep}"]`)

                                changePage(fromPage, toPage, '.q-polla__step--active', () => {
                                    pollaStep = nextStep
                                    const currentStepper = document.querySelector(`#q-polla .q-step[data-step="${nextStep}"]`)
                                    currentStepper.classList.add('q-step--active')

                                    const otherStepper = getSiblings(currentStepper)
                                    otherStepper.forEach(stepper => stepper.classList.remove('q-step--active')
                                    )

                                    const prevStepper = getPreviousSiblings(currentStepper)
                                    prevStepper.forEach(stepper => stepper.classList.add('q-step--passed'))

                                    if (obj.tipoPolla === 'pendiente') {
                                        ballActions(toPage)
                                    }
                                })
                            });

                            $('#titulo-campeon-polla').html(`¡${ganador.nombre} campeón!`);
                            $('#nombre-polla').html(ganador.nombre);
                            $('.q-winner__flag').attr('src', `${obj.uriResources}${ganador.imagenM}`)
                            $('.q-winner__map').attr('src', `${obj.uriResources}${ganador.mapa}`)

                            return;
                        }
                    }
                } else {
                    if (roundActive === 'octavos') {
                        cambiarFasePolla('cuartos', $('.q-rounds__btn[data-round="cuartos"]')[0], eventCuartos, obj.tipoPolla)
                        return;
                    } else if (roundActive === 'cuartos') {
                        cambiarFasePolla('semi', $('.q-rounds__btn[data-round="semi"]')[0], eventCuartos, obj.tipoPolla)
                        return;
                    } else if (roundActive === 'semi') {
                        cambiarFasePolla('final', $('.q-rounds__btn[data-round="final"]')[0], eventCuartos, obj.tipoPolla)
                        return;
                    } else {
                        const ganador = obj.obtenerGanadoreFinal();
                        $('#titulo-campeon-polla').html(`¡${ganador.nombre} campeón!`);
                        $('#nombre-polla').html(ganador.nombre);
                        $('.q-winner__flag').attr('src', `${obj.uriResources}${ganador.imagenM}`)
                        $('.q-winner__map').attr('src', `${obj.uriResources}${ganador.mapa}`)
                    }
                }
            }

            const fromPage = document.querySelector(`.q-polla__step[data-step="${pollaStep}"]`)
            const toPage = document.querySelector(`.q-polla__step[data-step="${nextStep}"]`)

            changePage(fromPage, toPage, '.q-polla__step--active', () => {
                pollaStep = nextStep
                const currentStepper = document.querySelector(`#q-polla .q-step[data-step="${nextStep}"]`)
                currentStepper.classList.add('q-step--active')

                const otherStepper = getSiblings(currentStepper)
                otherStepper.forEach(stepper => stepper.classList.remove('q-step--active')
                )

                const prevStepper = getPreviousSiblings(currentStepper)
                prevStepper.forEach(stepper => stepper.classList.add('q-step--passed'))

                if (obj.tipoPolla === 'pendiente') {
                    ballActions(toPage)
                }
            })
        })
    })
}

const eventPaisesDrag = () => {
    const groups = document.querySelectorAll('#q-polla .q-group__countries')
    groups.forEach(group => {
            new Sortable(group, {
                animation: 150
            })
        }
    )
}

const roundSquare = (round = '', event, pollaTipo) => {
    const square = document.querySelector('#q-polla .q-rounds__btn-square')

    let roundNumber = 0

    if (pollaTipo === 'pendiente') {
        event(round);
        if (round == 'octavos') {
            $('.q-rounds__btn').prop('disabled', true);
            roundNumber = 0
        }
        else if (round == 'cuartos') {
            $('.q-rounds__btn').prop('disabled', true);
            $('.q-rounds__btn[data-round="octavos"]').prop('disabled', false)

            roundNumber = 1
        }
        else if (round == 'semi') {
            $('.q-rounds__btn').prop('disabled', true);
            $('.q-rounds__btn[data-round="octavos"]').prop('disabled', false)
            $('.q-rounds__btn[data-round="cuartos"]').prop('disabled', false)
            roundNumber = 2
        }
        else {
            $('.q-rounds__btn').prop('disabled', true);
            $('.q-rounds__btn[data-round="octavos"]').prop('disabled', false)
            $('.q-rounds__btn[data-round="cuartos"]').prop('disabled', false)
            $('.q-rounds__btn[data-round="semi"]').prop('disabled', false)
            roundNumber = 3
        }
    } else {

        if(round == 'octavos') { roundNumber = 0 }
        else if(round == 'cuartos') { roundNumber = 1 }
        else if(round == 'semi') { roundNumber = 2 }
        else { roundNumber = 3 }
    }

    square.style.top = `${60 * roundNumber}px`
}

const eventCuartosPolla = (event, pollaTipo, obj) => {
    const roundButtons = document.querySelectorAll('#q-polla .q-rounds__btn')
    roundButtons.forEach(btn => {
        btn.addEventListener('click', e => {
            const t = e.target
            const round = t.getAttribute('data-round')
            cambiarFasePolla(round, t, event, obj.tipoPolla)

        })
    })
}

const cambiarFasePolla = (round, t, event, pollaTipo) => {

    const fromRound = document.querySelector(`#q-polla .q-rounds__matches--active`)
    const toRound = document.querySelector(`#q-polla .q-rounds__matches[data-round="${round}"]`)

    changePage(fromRound, toRound, 'q-rounds__matches--active')

    t.classList.add('q-rounds__btn--active')
    const sibs = getSiblings(t)
    sibs.forEach(sib => sib.classList.remove('q-rounds__btn--active'))

    roundSquare(round, event, pollaTipo)

}

let indexPrevGlide;
let glideGlobal;

const destroyGlide = () => {
    if (glideGlobal) {
        glideGlobal.destroy()
        indexPrevGlide = null;
    }
}
// Fechas
const startCalendar = (indexActivo, handlerPronosticos, obj, cantidadFechas) => {

    // function DisableControls(Glide, Components, Events) {
    //     const PREV_CONTROL_SELECTOR = "[data-glide-dir='<']";
    //     const NEXT_CONTROL_SELECTOR = "[data-glide-dir='>']";
    //     const component = {
    //         buildAfter() {
    //             this.prevBtn = Components.Html.root.querySelector(PREV_CONTROL_SELECTOR);
    //             this.nextBtn = Components.Html.root.querySelector(NEXT_CONTROL_SELECTOR);
    //         },
    //         handleDisable() {
    //
    //             this.prevBtn.disabled = (Glide.index <= 0);
    //             this.nextBtn.disabled = (Glide.index >= indexActivo);
    //
    //          },
    //     };
    //
    //     Events.on("build.after", function () {
    //         component.buildAfter();
    //         component.handleDisable();
    //     });
    //     Events.on("run.after", function () {
    //         console.log(213)
    //         component.handleDisable();
    //     });
    //     return component;
    // }

    const calendar = document.querySelector('.q-calendar')
    if (calendar != null) {
        const glide = new Glide('.q-calendar', {
            type: 'carousel',
            perView: 8,
            gap: 15,
            rewind: false,
            startAt: indexActivo,
            breakpoints: {
                770: { perView: 5 },
                530: { perView: 3 },
            }
        }).mount({
            // DisableControls: DisableControls
        });
        glideGlobal = glide;
        $('.q-calendar__date').click((e) => {
            const dataDate = $(e.target).data();
            if (dataDate.indice > indexActivo) {
                obj.mostrarAlerta(3, 'La fecha seleccionada se habilitará 24 horas antes para el ingreso de tus pronósticos.')
            }
        })

        glide.on('move.after', () => {
            handlerPronosticos(obj, indexPrevGlide, glide.index);
            indexPrevGlide = glide.index;
        })
        glide.on('move', () => {
            if (indexPrevGlide + 1 === glide.index && indexActivo + 1 === glide.index) {
                glide.update({ startAt: indexActivo });
                obj.mostrarAlerta(3, 'La fecha seleccionada se habilitará 24 horas antes para el ingreso de tus pronósticos.')
            } else if (indexPrevGlide === 0 && cantidadFechas - 1 === glide.index) {
                glide.update({ startAt: 0 });
                obj.mostrarAlerta(3, 'La fecha seleccionada se habilitará 24 horas antes para el ingreso de tus pronósticos.')
            } else {
                obj.limpiarAlerta();
            }
        })
    }
}

const progressCircularBar = (porcentaje) => {
    let progressBar = document.querySelector(".circular-progress");
    let valueContainer = document.querySelector(".value-container-circular");

    progressBar.style.background = `conic-gradient(
      #08098d ${porcentaje * 3.6}deg,
      #0076a666 ${porcentaje * 3.6}deg
  )`;

}

const ballActions = (page) => {
    const ballSquares = page.querySelectorAll('.q-match__score-square')
    ballSquares.forEach(sq => {
        sq.addEventListener('click', e => {
            eventBallAction(sq, e)
        })
    })
}

const eventBallAction =  (sq, e) => {
    const t = e.target
    const ball = t.querySelector('.icon-futbol')
    ball.style.opacity = '1'

    const sibs = getSiblings(t)
    sibs.forEach(sib => {
        if(sib.classList.contains('q-match__score-square')) {
            const ball = sib.querySelector('.icon-futbol')
            ball.style.opacity = '0'
        }
    })
}