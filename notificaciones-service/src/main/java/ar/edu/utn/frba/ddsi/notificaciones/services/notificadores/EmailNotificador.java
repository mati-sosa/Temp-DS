package ar.edu.utn.frba.ddsi.notificaciones.services.notificadores;

import org.springframework.stereotype.Service;

@Service
public class EmailNotificador implements Notificador{
    public void enviar(String destino, String mensaje){
        System.out.println("[EMAIL SERVICE] Enviando email a " + destino);
        System.out.println("Mensaje: " + mensaje);
        System.out.println("[EMAIL SERVICE] COMPLETADO");
    }
}
