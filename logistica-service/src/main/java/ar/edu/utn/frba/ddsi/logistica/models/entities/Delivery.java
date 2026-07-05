package ar.edu.utn.frba.ddsi.logistica.models.entities;


import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class Delivery {
    private String id;
    private double latitud;
    private double longitud;
    private String direccion;
    private double pesoKg;
    private double volumenM3;

}
