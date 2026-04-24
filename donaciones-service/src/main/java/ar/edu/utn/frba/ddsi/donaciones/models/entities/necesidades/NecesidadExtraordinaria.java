package ar.edu.utn.frba.ddsi.donaciones.models.entities.necesidades;

import ar.edu.utn.frba.ddsi.donaciones.models.entities.bienes.CategoriaBien;
import ar.edu.utn.frba.ddsi.donaciones.models.entities.bienes.CantidadDeBien;

public class NecesidadExtraordinaria extends Necesidad {

    public NecesidadExtraordinaria(String descripcion, CategoriaBien categoria, CantidadDeBien cantidadDeBien) {
        super(descripcion, categoria, cantidadDeBien);
    }

    @Override
    public Boolean estaSatisfecha() {
        return getCantidadCubierta() >= getCantidadDeBien().getCantidad();
    }
}
