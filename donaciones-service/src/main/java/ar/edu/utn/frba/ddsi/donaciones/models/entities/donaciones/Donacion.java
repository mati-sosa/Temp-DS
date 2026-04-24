package ar.edu.utn.frba.ddsi.donaciones.models.entities.donaciones;

import ar.edu.utn.frba.ddsi.donaciones.models.entities.bienes.Bien;
import ar.edu.utn.frba.ddsi.donaciones.models.entities.donantes.Donante;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
public class Donacion {
    private String descripcion;
    private List<Bien> bienes;
    @Setter
    private EstadoDonacion estado;
    private Donante donante;

    public Donacion(String descripcion, List<Bien> bienes, Donante donante) {
        this.descripcion = descripcion;
        this.bienes = bienes;
        this.estado = EstadoDonacion.EN_DEPOSITO;
        this.donante = donante;
    }
}
