package ar.edu.utn.frba.ddsi.donaciones.models.entities.matchmaking;

import ar.edu.utn.frba.ddsi.donaciones.models.entities.donaciones.Donacion;
import ar.edu.utn.frba.ddsi.donaciones.models.entities.necesidades.Necesidad;
import lombok.Getter;

@Getter
public class PosibleMatch {
    private Donacion donacion;
    private Necesidad necesidad;

    public PosibleMatch(Donacion unaDonacion, Necesidad unaNecesidad) {
        donacion = unaDonacion;
        necesidad = unaNecesidad;
    }

    @Override
    public String toString() {
        return "[" +donacion + "," + necesidad + "]";
    }
}
