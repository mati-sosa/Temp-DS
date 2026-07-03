package ar.edu.utn.frba.ddsi.logistica.models.entities.estados_ruta;

import ar.edu.utn.frba.ddsi.logistica.models.entities.rutas.Entrega;
import ar.edu.utn.frba.ddsi.logistica.models.entities.rutas.Ruta;

public class EstadoRutaPlanificada implements EstadoRuta {

    @Override
    public String descripcion() { return "Planificada"; }

    @Override
    public void iniciar(Ruta ruta) {
        ruta.cambiarEstado(new EstadoRutaEnCurso(), null);
        ruta.getDestinos().forEach(destino ->
                destino.getEntregas().forEach(Entrega::iniciarTraslado));
    }
}
