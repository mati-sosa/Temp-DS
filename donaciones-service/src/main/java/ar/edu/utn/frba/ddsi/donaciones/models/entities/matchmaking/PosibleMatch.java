package ar.edu.utn.frba.ddsi.donaciones.models.entities.matchmaking;

import ar.edu.utn.frba.ddsi.donaciones.models.entities.donaciones.Donacion;
import ar.edu.utn.frba.ddsi.donaciones.models.entities.entidades_beneficiarias.EntidadBeneficiaria;
import ar.edu.utn.frba.ddsi.donaciones.models.entities.necesidades.Necesidad;
import lombok.Getter;

@Getter
public class PosibleMatch {
    private Donacion donacion;
    private Necesidad necesidad;
    private EntidadBeneficiaria entidadBeneficiaria;

    public PosibleMatch(Donacion unaDonacion, Necesidad unaNecesidad, EntidadBeneficiaria unaEntidadBeneficiaria) {
        donacion = unaDonacion;
        necesidad = unaNecesidad;
        entidadBeneficiaria = unaEntidadBeneficiaria;
    }

    @Override
    public String toString() {
        return "[--------\n" +
                "Donacion: " + donacion + "\n" +
                "Necesidad: " + necesidad + "\n" +
                "Entidad Beneficiaria: " + entidadBeneficiaria + "\n" +
                "--------]";
    }
}
