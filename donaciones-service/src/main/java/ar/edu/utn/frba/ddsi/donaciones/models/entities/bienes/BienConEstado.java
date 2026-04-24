package ar.edu.utn.frba.ddsi.donaciones.models.entities.bienes;

import lombok.Getter;

@Getter
public class BienConEstado extends BienNoPerecible {
    private Boolean esNuevo;

    public BienConEstado(String descripcion, SubcategoriaBien subcategoria, CantidadDeBien cantidadDeBien, String foto, Boolean esNuevo) {
        super(descripcion, subcategoria, cantidadDeBien, foto);
        this.esNuevo = esNuevo;
    }
}
