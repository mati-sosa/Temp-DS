package ar.edu.utn.frba.ddsi.donaciones.models.entities.estados_donaciones;

import ar.edu.utn.frba.ddsi.donaciones.models.entities.donaciones.Donacion;

public class EstadoEnDeposito implements EstadoDonacion {
    public void asignarDestinatario(Donacion unaDonacion){}
    public void desecharPorVencimiento(Donacion unaDonacion){}

    @Override
    public void confirmarEnvio(Donacion unaDonacion) {

    }

    @Override
    public void despachar(Donacion unaDonacion) {

    }

    @Override
    public void entregar(Donacion unaDonacion) {

    }

    @Override
    public void cancelar(Donacion unaDonacion) {

    }



    @Override
    public void almacenar(Donacion unaDonacion) {

    }
}
