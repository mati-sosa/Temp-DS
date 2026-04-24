package ar.edu.utn.frba.ddsi.donaciones.models.entities.bienes;

import lombok.Getter;

@Getter
public abstract class Bien {
    private String descripcion;
    private SubcategoriaBien subcategoria;
    private CantidadDeBien cantidadDeBien;
    private String foto;

    public Bien(String descripcion, SubcategoriaBien subcategoria, CantidadDeBien cantidadDeBien, String foto) {
        if (descripcion == null) {
            throw new IllegalArgumentException("¡El bien debe tener una descripción!");
        }
        if (subcategoria == null) {
            throw new IllegalArgumentException("¡El bien debe tener una subcategoría asociada!");
        }

        this.descripcion = descripcion;
        this.subcategoria = subcategoria;
        this.cantidadDeBien = cantidadDeBien;
        this.foto = foto;
    }
}
