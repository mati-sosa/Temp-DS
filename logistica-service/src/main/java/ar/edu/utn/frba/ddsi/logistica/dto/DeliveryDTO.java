package ar.edu.utn.frba.ddsi.logistica.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class DeliveryDTO {
     private String deliveryCode;
     private double longitude;
     private double latitude;
     private String adress;
     private double WeightKg;
     private double VolumeM3;
}
