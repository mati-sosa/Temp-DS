package ar.edu.utn.frba.ddsi.incentivos.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.Map;

@Service
public class NotificacionClient implements NotificadorDonante {

    private final RestClient restClient;
    private final String notificacionesUrl;
    private final String donacionesUrl;

    public NotificacionClient(
            @Value("${notificaciones.service.url:http://localhost:8081}") String notificacionesUrl,
            @Value("${donaciones.service.url:http://localhost:8080}") String donacionesUrl) {
        this.notificacionesUrl = notificacionesUrl;
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

            restClient.post()
                    .uri(notificacionesUrl + "/enviar-notificacion")
                    .body(body)
                    .retrieve()
                    .toBodilessEntity();
        } catch (RuntimeException e) {
            System.out.println("[incentivos-notif] Error al notificar donante " + donanteId + ": " + e.getMessage());
        }
    }
}
