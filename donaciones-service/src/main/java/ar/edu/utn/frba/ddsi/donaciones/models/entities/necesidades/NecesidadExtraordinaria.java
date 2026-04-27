package ar.edu.utn.frba.ddsi.donaciones.models.entities.necesidades;

import ar.edu.utn.frba.ddsi.donaciones.models.entities.bienes.SubcategoriaBien;

public class NecesidadExtraordinaria extends Necesidad {

    public NecesidadExtraordinaria(String descripcion, SubcategoriaBien subCategoria, Double cantidadRequerida) {
        super(descripcion, subCategoria, cantidadRequerida);
    }

    @Override
    public Boolean estaSatisfecha() {
        return getCantidadCubierta() >= getCantidadRequerida();
    }

    @Override
    public String toString(){
        return getDescripcion() + " " +
                getSubcategoria() + " " +
                getCantidadRequerida();
    }
}