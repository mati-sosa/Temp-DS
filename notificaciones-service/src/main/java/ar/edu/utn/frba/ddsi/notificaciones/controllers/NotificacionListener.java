package ar.edu.utn.frba.ddsi.notificaciones.controllers;

import ar.edu.utn.frba.ddsi.notificaciones.config.RabbitConfig;
import ar.edu.utn.frba.ddsi.notificaciones.dto.NotificacionRequest;
import ar.edu.utn.frba.ddsi.notificaciones.services.NotificacionService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class NotificacionListener {
    @Autowired
    private NotificacionService notificacionService;

    @RabbitListener(queues = RabbitConfig.QUEUE_NAME)
    public void recibirNotificacion(NotificacionRequest request){
        try{
            System.out.println("[RABBITMQ] Mensaje recibido de la cola.");
            notificacionService.enviar(request);
        } catch (Exception e) {
            System.err.println("[RABBITMQ ERROR] Error al procesar la notificación: " + e.getMessage());
        }
    }
}
