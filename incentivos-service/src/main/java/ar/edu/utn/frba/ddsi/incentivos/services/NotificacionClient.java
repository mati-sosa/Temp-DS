package ar.edu.utn.frba.ddsi.incentivos.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.Map;

@Service
public class NotificacionClient implements NotificadorDonante {

    private static final Logger log = LoggerFactory.getLogger(NotificacionClient.class);

    private final RestClient restClient;
    private final RabbitTemplate rabbitTemplate;
    private static final String QUEUE_NAME = "cola.notificaciones";

    private final String donacionesUrl;
    public NotificacionClient(
            RabbitTemplate rabbitTemplate,
            @Value("${donaciones.service.url:http://localhost:8080}") String donacionesUrl) {
        this.rabbitTemplate = rabbitTemplate;
        this.donacionesUrl = donacionesUrl;
        this.restClient = RestClient.create();
    }

    public void notificarDonante(Long donanteId, String mensaje) {
        try {
            Map<String, Object> donante = restClient.get()
                    .uri(donacionesUrl + "/donantes/" + donanteId)
                    .retrieve()
                    .body(Map.class);

            if (donante == null) return;

            @SuppressWarnings("unchecked")
            Map<String, Object> medio = (Map<String, Object>) donante.get("medioPredeterminado");
            if (medio == null) return;

            Map<String, Object> body = Map.of(
                    "medioDeContacto", Map.of(
                            "tipoMedioContacto", medio.get("tipo"),
                            "direccion", medio.get("valor")
                    ),
                    "mensaje", mensaje
            );

            rabbitTemplate.convertAndSend(QUEUE_NAME, body);
            log.info("[incentivos-notif] Mensaje encolado con éxito para el donante {}", donanteId);
        } catch (RuntimeException e) {
            log.warn("[incentivos-notif] Error al notificar donante {}: {}", donanteId, e.getMessage());
        }
    }
}
