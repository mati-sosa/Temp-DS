package ar.edu.utn.frba.ddsi.donaciones.models.entities.estados_donaciones;

import ar.edu.utn.frba.ddsi.donaciones.models.entities.donaciones.Donacion;

public class EstadoListaParaEntregar implements EstadoDonacion {

    @Override
    public String nombre() { return "Lista para entregar"; }

    @Override
    public void despachar(Donacion donacion) {
        donacion.cambiarEstado(new EstadoEnTraslado(), null);
    }

    @Override
    public void desecharPorVencimiento(Donacion donacion) {
        donacion.cambiarEstado(new EstadoVencida(), null);
    }
}