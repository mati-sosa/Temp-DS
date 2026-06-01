package ar.edu.utn.frba.ddsi.donaciones.models.entities.estados_donaciones;

public class EstadoVencida implements EstadoDonacion {

    @Override
    public String nombre() { return "Vencida"; }

    // Estado terminal — ninguna transición es válida
}