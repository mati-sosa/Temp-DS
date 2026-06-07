package ar.edu.utn.frba.ddsi.donaciones.models.entities.estados_donaciones;

import ar.edu.utn.frba.ddsi.donaciones.models.entities.donaciones.Donacion;

public interface EstadoDonacion {
    String nombre();

    default void asignarDestinatario(Donacion donacion) {
        throw new IllegalStateException("Transición inválida desde el estado: " + nombre());
    }
    default void confirmarEnvio(Donacion donacion) {
        throw new IllegalStateException("Transición inválida desde el estado: " + nombre());
    }
    default void despachar(Donacion donacion) {
        throw new IllegalStateException("Transición inválida desde el estado: " + nombre());
    }
    default void entregar(Donacion donacion) {
        throw new IllegalStateException("Transición inválida desde el estado: " + nombre());
    }
    default void cancelar(Donacion donacion, String justificacion) {
        throw new IllegalStateException("Transición inválida desde el estado: " + nombre());
    }
    default void desecharPorVencimiento(Donacion donacion) {
        throw new IllegalStateException("Transición inválida desde el estado: " + nombre());
    }
    default void almacenar(Donacion donacion) {
        throw new IllegalStateException("Transición inválida desde el estado: " + nombre());
    }
}