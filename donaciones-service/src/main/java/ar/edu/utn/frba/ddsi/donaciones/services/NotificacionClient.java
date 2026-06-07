package ar.edu.utn.frba.ddsi.donaciones.services;

import ar.edu.utn.frba.ddsi.donaciones.models.entities.contacto.MedioDeContacto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.Map;

@Service
public class NotificacionClient {

    private final RestClient restClient;
    private final String notificacionesUrl;

    public NotificacionClient(@Value("${notificaciones.service.url:http://localhost:8081}") String notificacionesUrl) {
        this.notificacionesUrl = notificacionesUrl;
        this.restClient = RestClient.create();
    }

    public void enviar(MedioDeContacto medio, String mensaje) {
        try {
            Map<String, Object> body = Map.of(
                    "medioDeContacto", Map.of(
                            "tipoMedioContacto", medio.getTipoMedioContacto().name(),
                            "direccion", medio.getDireccion()
                    ),
                    "mensaje", mensaje
            );
            restClient.post()
                    .uri(notificacionesUrl + "/enviar-notificacion")
                    .body(body)
                    .retrieve()
                    .toBodilessEntity();
        } catch (RuntimeException e) {
            System.out.println("[notificaciones] Error al enviar notificación: " + e.getMessage());
        }
    }
}
