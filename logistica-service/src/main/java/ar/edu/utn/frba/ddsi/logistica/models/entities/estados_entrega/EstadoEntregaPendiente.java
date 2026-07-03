package ar.edu.utn.frba.ddsi.logistica.models.entities.estados_entrega;

import ar.edu.utn.frba.ddsi.logistica.models.entities.rutas.Entrega;

public class EstadoEntregaPendiente implements EstadoEntrega {

    @Override
    public String descripcion() { return "Pendiente"; }

    @Override
    public void iniciarTraslado(Entrega entrega) {
        entrega.cambiarEstado(new EstadoEntregaEnTraslado(), null);
    }
}
