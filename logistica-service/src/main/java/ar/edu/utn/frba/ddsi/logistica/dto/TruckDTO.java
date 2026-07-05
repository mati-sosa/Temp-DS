package ar.edu.utn.frba.ddsi.logistica.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class TruckDTO {
    private String truckId;
    private float weightCapacityKg;
    private float volumeCapacityM3;
}
