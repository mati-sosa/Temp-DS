package ar.edu.utn.frba.ddsi.notificaciones.services.notificadores;

public interface Notificador {
    void enviar(String destino, String mensaje);
}