package ar.edu.utn.frba.ddsi.donaciones.models.entities.estados_donaciones;

import ar.edu.utn.frba.ddsi.donaciones.models.entities.donaciones.Donacion;

public class EstadoEnTraslado implements EstadoDonacion {

    @Override
    public String nombre() { return "En traslado"; }

    @Override
    public void entregar(Donacion donacion) {
        donacion.cambiarEstado(new EstadoEntregada(), null);
    }

    @Override
    public void cancelar(Donacion donacion, String justificacion) {
        donacion.cambiarEstado(new EstadoEntregaFallida(), justificacion);
    }
}