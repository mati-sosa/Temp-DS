package ar.edu.utn.frba.ddsi.donaciones.services;

import ar.edu.utn.frba.ddsi.donaciones.models.entities.contacto.MedioDeContacto;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;
import java.util.Map;

@Service
public class NotificacionClient{
    private final RabbitTemplate rabbitTemplate;
    private static final String QUEUE_NAME = "cola.notificaciones";

    public NotificacionClient(RabbitTemplate rabbitTemplate){
        this.rabbitTemplate = rabbitTemplate;
    }

    public void enviar(MedioDeContacto medio, String mensaje){
        try {
            Map<String, Object> body = Map.of(
                    "medioDeContacto", Map.of(
                            "tipoMedioContacto", medio.getTipoMedioContacto().name(),
                            "direccion", medio.getDireccion()
                    ),
                    "mensaje", mensaje
            );
            rabbitTemplate.convertAndSend(QUEUE_NAME, body);

            System.out.println("[Notificaciones] Mensaje enviado a la cola de RabbitMQ");
        } catch (Exception e) {
            System.err.println("[Notificaciones] Error al enviar a la cola: " + e.getMessage());
        }
    }
}