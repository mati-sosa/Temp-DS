package ar.edu.utn.frba.ddsi.logistica.services;

import ar.edu.utn.frba.ddsi.logistica.dto.*;
import ar.edu.utn.frba.ddsi.logistica.dto.mappers.PlanificacionRutaMapper;
import ar.edu.utn.frba.ddsi.logistica.models.repositories.DeliveryRepository;
import ar.edu.utn.frba.ddsi.logistica.models.repositories.TruckRepository;
import ar.edu.utn.frba.ddsi.logistica.routing.RoutingApiClient;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class PlanificacionRutasService {
    private String urlCallback;
    private RoutingApiClient routingApiClient;
    private TruckRepository truckRepository;
    private DeliveryRepository deliveryRepository;
    private PlanificacionRutaMapper planificacionRutaMapper;

    public PlanificacionRutasService(
            RoutingApiClient unRoutingApiClient,
            TruckRepository untruckRepository,
            DeliveryRepository undeliveryRepository
    ) {
        routingApiClient = unRoutingApiClient;
        truckRepository = untruckRepository;
        deliveryRepository = undeliveryRepository;
        planificacionRutaMapper = new PlanificacionRutaMapper();
    }

    private String generarUUIDv4(){
        return UUID.randomUUID().toString();
    }

    private TimeWindowDTO generarVentanaHoraria(){
        return new TimeWindowDTO(
                "2025-09-21T09:00:00Z",
                "2025-09-21T18:00:00Z"
        );
    }

    private WarehouseDTO generarWarehouse(){
        return new WarehouseDTO(
                -34.3556,
                -58.42015,
                "Av. Corrientes 1234, CABA"
        );
    }

    private List<DeliveryDTO> obtenerDeliveries(){
        return deliveryRepository.
                listar().
                stream().
                map(planificacionRutaMapper::toDeliveryDTO).
                toList();
    }

    private List<TruckDTO> obtenerCamiones() {
        return truckRepository.
                listar().
                stream().
                map(planificacionRutaMapper::toTruckDTO).
                toList();
    }

    public PlanRouteDTO generarRequest(){
        return new PlanRouteDTO(
                generarUUIDv4(),
                generarVentanaHoraria(),
                generarWarehouse(),
                obtenerDeliveries(),
                obtenerCamiones()
        );
    }

    public void planificar(){

        routingApiClient.solicitarPlanificacion(generarRequest());
    }

}
