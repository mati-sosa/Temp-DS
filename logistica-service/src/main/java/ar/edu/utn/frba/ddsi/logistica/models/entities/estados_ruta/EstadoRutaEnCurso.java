package ar.edu.utn.frba.ddsi.logistica.models.entities.estados_ruta;

import ar.edu.utn.frba.ddsi.logistica.models.entities.rutas.Ruta;

public class EstadoRutaEnCurso implements EstadoRuta {

    @Override
    public String descripcion() { return "En curso"; }

    @Override
    public void finalizar(Ruta ruta) {
        ruta.cambiarEstado(new EstadoRutaFinalizada(), null);
    }
}
