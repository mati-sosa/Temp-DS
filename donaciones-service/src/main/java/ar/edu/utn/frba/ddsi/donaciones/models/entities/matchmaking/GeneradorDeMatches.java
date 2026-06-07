package ar.edu.utn.frba.ddsi.donaciones.models.entities.matchmaking;

import ar.edu.utn.frba.ddsi.donaciones.models.entities.donaciones.Donacion;
import ar.edu.utn.frba.ddsi.donaciones.models.entities.entidades_beneficiarias.EntidadBeneficiaria;

import java.util.ArrayList;
import java.util.stream.Collectors;

public class GeneradorDeMatches {

    public ArrayList<PosibleMatch> generarMatches(ArrayList<Donacion> donaciones, ArrayList<EntidadBeneficiaria> entidades) {
        return donaciones.stream().flatMap(
                donacion -> entidades.stream().flatMap(
                        entidad -> entidad.getNecesidades().stream()
                                .filter(necesidad -> donacion.getSubcategoriaBien().getDescripcion()
                                        .equals(necesidad.getSubcategoria().getDescripcion()))
                                .map(necesidad -> new PosibleMatch(donacion, necesidad, entidad))
                )
        ).collect(Collectors.toCollection(ArrayList::new));
    }
}
