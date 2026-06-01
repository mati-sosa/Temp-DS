package ar.edu.utn.frba.ddsi.donaciones.models.entities.estados_donaciones;

import ar.edu.utn.frba.ddsi.donaciones.models.entities.donaciones.Donacion;

public interface EstadoDonacion {
    public void asignarDestinatario(Donacion unaDonacion);
    public void confirmarEnvio(Donacion unaDonacion);
    public void despachar(Donacion unaDonacion);
    public void entregar(Donacion unaDonacion);
    public void cancelar(Donacion unaDonacion);
    public void desecharPorVencimiento(Donacion unaDonacion);
    public void almacenar(Donacion unaDonacion);
}
