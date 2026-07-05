package ar.edu.utn.frba.ddsi.logistica.dto.mappers;

import ar.edu.utn.frba.ddsi.logistica.dto.DeliveryDTO;
import ar.edu.utn.frba.ddsi.logistica.dto.TruckDTO;
import ar.edu.utn.frba.ddsi.logistica.models.entities.Camion;
import ar.edu.utn.frba.ddsi.logistica.models.entities.Delivery;

public class PlanificacionRutaMapper {

    public DeliveryDTO toDeliveryDTO(Delivery delivery) {
        return new DeliveryDTO(
                delivery.getId(),
                delivery.getLatitud(),
                delivery.getLongitud(),
                delivery.getDireccion(),
                delivery.getPesoKg(),
                delivery.getVolumenM3()
        );
    }

    public TruckDTO toTruckDTO(Camion camion) {
        return new TruckDTO(
                camion.getPatente(),
                camion.getCapacidadCarga(),
                camion.getCapacidadVolumen()
        );
    }
}
