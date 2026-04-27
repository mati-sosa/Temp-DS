package ar.edu.utn.frba.ddsi.notificaciones.services.notificadores;

public class WhatsappNotificador implements Notificador{
    public void enviar(String destino, String mensaje){
        System.out.println("[WHATSAPP SERVICE] Enviando mensaje por Whatsapp a " + destino);
        System.out.println("Mensaje: " + mensaje);
        System.out.println("[WHATSAPP SERVICE] COMPLETADO");
    }
}
