package ar.edu.utn.frba.ddsi.incentivos.services;

@FunctionalInterface
public interface NotificadorDonante {
    void notificarDonante(Long donanteId, String mensaje);
}
