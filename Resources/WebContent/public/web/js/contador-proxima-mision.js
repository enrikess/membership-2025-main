/**
 * Clase para manejar el contador regresivo hasta la próxima misión
 */
class ContadorProximaMision {
    constructor(fechaObjetivo, elementoContador) {
        this.fechaObjetivo = new Date(fechaObjetivo);
        this.elementoContador = elementoContador;
        this.intervalo = null;
        this.iniciar();
    }

    /**
     * Calcula el tiempo restante hasta la fecha objetivo
     */
    calcularTiempoRestante() {
        const ahora = new Date();
        const diferencia = this.fechaObjetivo - ahora;

        if (diferencia <= 0) {
            return {
                total: 0,
                horas: 0,
                minutos: 0,
                segundos: 0,
                expirado: true
            };
        }

        const horas = Math.floor(diferencia / (1000 * 60 * 60));
        const minutos = Math.floor((diferencia % (1000 * 60 * 60)) / (1000 * 60));
        const segundos = Math.floor((diferencia % (1000 * 60)) / 1000);

        return {
            total: diferencia,
            horas: horas,
            minutos: minutos,
            segundos: segundos,
            expirado: false
        };
    }

    /**
     * Formatea el tiempo en formato HH:MM:SS
     */
    formatearTiempo(tiempo) {
        const { horas, minutos, segundos } = tiempo;
        
        // Asegurar que siempre tengan 2 dígitos
        const horasStr = String(horas).padStart(2, '0');
        const minutosStr = String(minutos).padStart(2, '0');
        const segundosStr = String(segundos).padStart(2, '0');
        
        return `${horasStr}:${minutosStr}:${segundosStr}`;
    }

    /**
     * Actualiza el elemento DOM con el tiempo actual
     */
    actualizarDisplay() {
        const tiempo = this.calcularTiempoRestante();
        
        if (tiempo.expirado) {
            this.elementoContador.textContent = '00:00:00';
            this.detener();
            this.onExpirado();
        } else {
            this.elementoContador.textContent = this.formatearTiempo(tiempo);
        }
    }

    /**
     * Inicia el contador regresivo
     */
    iniciar() {
        // Actualizar inmediatamente
        this.actualizarDisplay();
        
        // Continuar actualizando cada segundo
        this.intervalo = setInterval(() => {
            this.actualizarDisplay();
        }, 1000);
    }

    /**
     * Detiene el contador regresivo
     */
    detener() {
        if (this.intervalo) {
            clearInterval(this.intervalo);
            this.intervalo = null;
        }
    }

    /**
     * Callback cuando el contador expira
     * Puede ser sobrescrito por el usuario
     */
    onExpirado() {
        console.log('⏰ Contador de próxima misión expirado');
        // Aquí podrías recargar la página o hacer otra acción
        // window.location.reload();
    }
}

// Exportar para uso global
window.ContadorProximaMision = ContadorProximaMision;
