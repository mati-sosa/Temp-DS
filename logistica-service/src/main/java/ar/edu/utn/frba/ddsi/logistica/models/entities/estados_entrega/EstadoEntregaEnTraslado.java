package ar.edu.utn.frba.ddsi.logistica.models.entities.estados_entrega;

import ar.edu.utn.frba.ddsi.logistica.models.entities.rutas.Entrega;

import java.time.LocalDateTime;
import java.util.List;

public class EstadoEntregaEnTraslado implements EstadoEntrega {

    @Override
    public String descripcion() { return "En traslado"; }

    @Override
    public void confirmarRecepcion(Entrega entrega, List<String> fotos) {
        entrega.setFotos(fotos);
        entrega.setFechaHoraEntrega(LocalDateTime.now());
        entrega.cambiarEstado(new EstadoEntregaEntregada(), null);
    }

    @Override
    public void marcarNoRecibida(Entrega entrega, String motivo) {
        entrega.cambiarEstado(new EstadoEntregaNoRecibida(), motivo);
    }
}
