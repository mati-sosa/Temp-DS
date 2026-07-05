package ar.edu.utn.frba.ddsi.logistica.models.repositories;

import ar.edu.utn.frba.ddsi.logistica.models.entities.Camion;

import java.util.ArrayList;
import java.util.List;

public class TruckRepository {
    private final List<Camion> camiones = new ArrayList<>();

    public void guardarCamion(Camion camion){
        camiones.add(camion);
    }

    public List<Camion> listar(){
        return camiones;
    }
}
