package ar.edu.utn.frba.ddsi.donaciones.models.entities.necesidades;

import ar.edu.utn.frba.ddsi.donaciones.models.entities.bienes.SubcategoriaBien;

public class NecesidadExtraordinaria extends Necesidad {

    public NecesidadExtraordinaria(String descripcion, SubcategoriaBien categoria, Double cantidadRequerida) {
        super(descripcion, categoria, cantidadRequerida);
    }

    @Override
    public Boolean estaSatisfecha() {
        return getCantidadCubierta() >= getCantidadRequerida();
    }
}