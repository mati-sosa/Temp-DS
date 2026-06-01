package ar.edu.utn.frba.ddsi.donaciones.models.entities.estados_donaciones;

import ar.edu.utn.frba.ddsi.donaciones.models.entities.donaciones.Donacion;

public class EstadoEnDeposito implements EstadoDonacion {

    @Override
    public String nombre() { return "En depósito"; }

    @Override
    public void asignarDestinatario(Donacion donacion) {
        donacion.cambiarEstado(new EstadoAsignacionRealizada(), null);
    }

    @Override
    public void desecharPorVencimiento(Donacion donacion) {
        donacion.cambiarEstado(new EstadoVencida(), null);
    }
}