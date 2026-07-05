package ar.edu.utn.frba.ddsi.logistica.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PlanRouteDTO {
    private String requestId;
    private TimeWindowDTO timeWindow;
    private WarehouseDTO warehouse;
    private List<DeliveryDTO> deliveries;
    private List<TruckDTO> trucks;
}
