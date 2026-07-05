package ar.edu.utn.frba.ddsi.logistica.models.repositories;

import ar.edu.utn.frba.ddsi.logistica.models.entities.Delivery;

import java.util.ArrayList;
import java.util.List;

public class DeliveryRepository {
    private List<Delivery> deliveries = new ArrayList<>();

    public void guardarDelivery(Delivery delivery){
        deliveries.add(delivery);
    }

    public List<Delivery> listar(){
        return deliveries;
    }
}
