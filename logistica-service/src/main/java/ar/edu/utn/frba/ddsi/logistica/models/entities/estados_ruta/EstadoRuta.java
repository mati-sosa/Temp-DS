package ar.edu.utn.frba.ddsi.logistica.models.entities.estados_ruta;

import ar.edu.utn.frba.ddsi.logistica.models.entities.rutas.Ruta;

public interface EstadoRuta {
    String descripcion();

    default void iniciar(Ruta ruta) {
        throw new IllegalStateException("Transición inválida desde el estado: " + descripcion());
    }
    default void finalizar(Ruta ruta) {
        throw new IllegalStateException("Transición inválida desde el estado: " + descripcion());
    }
}
