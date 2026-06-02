package ar.edu.utn.frba.ddsi.donaciones.models.entities.matchmaking;

import ar.edu.utn.frba.ddsi.donaciones.models.entities.donaciones.Donacion;
import ar.edu.utn.frba.ddsi.donaciones.models.entities.necesidades.Necesidad;

import java.util.ArrayList;
import java.util.stream.Collectors;

public class GeneradorDeMatches {

    public ArrayList<PosibleMatch> generarMatches(ArrayList<Donacion> donaciones, ArrayList<Necesidad> necesidades){
        ArrayList<PosibleMatch> posiblesMatches = donaciones.stream().flatMap(
                        donacion -> necesidades.stream().filter(
                            necesidad -> donacion.getSubcategoriaBien().getDescripcion() == necesidad.getSubcategoria().getDescripcion()
                        ).map(necesidad -> new PosibleMatch(donacion, necesidad))
                ).collect(Collectors.toCollection(ArrayList::new));

        return posiblesMatches;
    }
}
