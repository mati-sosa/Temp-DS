package ar.edu.utn.frba.ddsi.logistica.routing;

import ar.edu.utn.frba.ddsi.logistica.dto.PlanRouteDTO;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@Component
public class RoutingApiClient {
    private final String urlApi = "https://api-ddsi-route-generator.dds.apps.disilab.ar/api/v1/plan-route";
    private RestClient restClient;

    public RoutingApiClient(RestClient.Builder restClientBuilder){
        restClient = restClientBuilder.build();
    }

    public void solicitarPlanificacion(PlanRouteDTO request){
        restClient.post()
                .uri(urlApi)
                .body(request)
                .retrieve()
                .toBodilessEntity();
    }
}
