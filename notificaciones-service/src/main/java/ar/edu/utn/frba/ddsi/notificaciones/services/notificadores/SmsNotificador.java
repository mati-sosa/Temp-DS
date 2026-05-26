package ar.edu.utn.frba.ddsi.notificaciones.services.notificadores;

public class SmsNotificador implements Notificador{
    public void enviar(String destino, String mensaje){
        System.out.println("[SMS SERVICE] Enviando SMS a " + destino);
        System.out.println("Mensaje: " + mensaje);
        System.out.println("[SMS SERVICE] COMPLETADO");
    }
}
