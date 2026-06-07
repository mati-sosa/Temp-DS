package ar.edu.utn.frba.ddsi.donaciones.models.entities.estados_donaciones;

public class EstadoEntregada implements EstadoDonacion {

    @Override
    public String nombre() { return "Entregada"; }

    // Estado terminal — ninguna transición es válida
}