package ar.edu.utn.frba.ddsi.donaciones.models.entities.estados_donaciones;

import ar.edu.utn.frba.ddsi.donaciones.models.entities.donaciones.Donacion;

public class EstadoEntregaFallida implements EstadoDonacion {

    @Override
    public String nombre() { return "Entrega fallida"; }

    @Override
    public void almacenar(Donacion donacion) {
        donacion.cambiarEstado(new EstadoEnDeposito(), null);
    }
}