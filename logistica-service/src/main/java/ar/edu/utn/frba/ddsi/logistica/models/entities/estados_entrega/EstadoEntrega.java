package ar.edu.utn.frba.ddsi.logistica.models.entities.estados_entrega;

import ar.edu.utn.frba.ddsi.logistica.models.entities.rutas.Entrega;

import java.util.List;

public interface EstadoEntrega {
    String descripcion();

    default void iniciarTraslado(Entrega entrega) {
        throw new IllegalStateException("Transición inválida desde el estado: " + descripcion());
    }
    default void confirmarRecepcion(Entrega entrega, List<String> fotos) {
        throw new IllegalStateException("Transición inválida desde el estado: " + descripcion());
    }
    default void marcarNoRecibida(Entrega entrega, String motivo) {
        throw new IllegalStateException("Transición inválida desde el estado: " + descripcion());
    }
    default void reingresarDeposito(Entrega entrega) {
        throw new IllegalStateException("Transición inválida desde el estado: " + descripcion());
    }
}
