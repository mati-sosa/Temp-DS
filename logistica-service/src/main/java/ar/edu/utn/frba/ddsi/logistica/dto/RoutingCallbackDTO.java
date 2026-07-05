package ar.edu.utn.frba.ddsi.logistica.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter @Setter @NoArgsConstructor
public class RoutingCallbackDTO {
    private RoutingData data;

    @JsonProperty("event_id")
    private String eventId;

    private String timestamp;

    @JsonProperty("event_type")
    private String eventType;

    @JsonProperty("request_id")
    private String requestId;

    @Getter
    @Setter
    @NoArgsConstructor
    public static class RoutingData {
        private List<RouteDTO> routes;
        private List<Object> unassignedDeliveries;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    public static class RouteDTO {
        private List<StopDTO> stops;
        private String truckId;
        private String assignedRouteId;
        private Double totalDistanceKm;
        private Integer totalDurationMins;
        private String estimatedStartTime;
        private String estimatedEndTime;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    public static class StopDTO {
        private Integer stopNumber;
        private String deliveryCode;
        private String estimatedArrivalTime;
    }

}
