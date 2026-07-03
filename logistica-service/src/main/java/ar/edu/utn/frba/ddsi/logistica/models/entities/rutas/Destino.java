package ar.edu.utn.frba.ddsi.logistica.models.entities.rutas;

import ar.edu.utn.frba.ddsi.logistica.models.entities.Direccion;
import lombok.Getter;

import java.util.List;

@Getter
public class Destino {
    private final Direccion direccion;
    private final List<Entrega> entregas;
    private final String entidadBeneficiariaID;
    private final Integer orden;

    public Destino(Direccion direccion, String entidadBeneficiariaID, Integer orden, List<Entrega> entregas) {
        if (direccion == null || entidadBeneficiariaID == null || entidadBeneficiariaID.isBlank()) {
            throw new IllegalArgumentException("El destino debe tener dirección y entidad beneficiaria");
        }
        this.direccion = direccion;
        this.entidadBeneficiariaID = entidadBeneficiariaID;
        this.orden = orden;
        this.entregas = entregas;
    }
}
