package ar.edu.utn.frba.ddsi.notificaciones.services;

import ar.edu.utn.frba.ddsi.notificaciones.dto.NotificacionRequest;
import org.springframework.stereotype.Service;

@Service
public class NotificacionService {
    public void enviar(NotificacionRequest request){
        System.out.println("--- ENVIANDO NOTIFICACIÓN ---");
        System.out.println("Destinatario: " + request.getDestinatario());
        System.out.println("Medio: " + request.getMedioDeContacto());
        System.out.println("Dirección: " + request.getMedioDeContacto().getDireccion());
        System.out.println("Mensaje: " + request.getMensaje());
        System.out.println("Estado: COMPLETADO");
    }
}
