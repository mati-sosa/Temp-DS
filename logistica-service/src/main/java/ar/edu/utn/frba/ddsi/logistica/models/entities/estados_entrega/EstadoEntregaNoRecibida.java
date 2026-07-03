package ar.edu.utn.frba.ddsi.logistica.models.entities.estados_entrega;

import ar.edu.utn.frba.ddsi.logistica.models.entities.rutas.Entrega;

public class EstadoEntregaNoRecibida implements EstadoEntrega {

    @Override
    public String descripcion() { return "No recibida"; }

    @Override
    public void reingresarDeposito(Entrega entrega) {
        entrega.cambiarEstado(new EstadoEntregaPendiente(), null);
    }
}
