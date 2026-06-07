package ar.edu.utn.frba.ddsi.incentivos.services.analitica;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

@Service
@Primary
public class MetricasDonacionesAdapter implements MetricasDonacionesPort {

    private final RestClient restClient;
    private final String donacionesUrl;

    public MetricasDonacionesAdapter(
            @Value("${donaciones.service.url:http://localhost:8080}") String donacionesUrl) {
        this.donacionesUrl = donacionesUrl;
        this.restClient = RestClient.create();
    }

    @Override
    public int organizacionesAyudadas(Long donanteId) {
        try {
            Long count = restClient.get()
                    .uri(donacionesUrl + "/donaciones/donante/" + donanteId + "/organizaciones-ayudadas")
                    .retrieve()
                    .body(Long.class);
            return count != null ? count.intValue() : 0;
        } catch (RuntimeException e) {
            System.out.println("[metricas] Error al consultar organizaciones ayudadas: " + e.getMessage());
            return 0;
        }
    }
}
