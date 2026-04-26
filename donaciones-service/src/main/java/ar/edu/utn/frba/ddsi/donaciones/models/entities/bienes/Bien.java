package ar.edu.utn.frba.ddsi.donaciones.models.entities.bienes;

import lombok.Getter;

@Getter
public abstract class Bien {
    private String descripcion;
    private SubcategoriaBien subcategoria;
    private String foto;
    private double cantidad;
    private Boolean yaFueDonado = false;

    public Bien(String descripcion, SubcategoriaBien subcategoria, Double cantidad, String foto) {
        if (descripcion == null) {
            throw new IllegalArgumentException("¡El bien debe tener una descripción!");
        }
        if (subcategoria == null) {
            throw new IllegalArgumentException("¡El bien debe tener una subcategoría asociada!");
        }
        if (cantidad == null || cantidad <= 0) {
            throw new IllegalArgumentException("¡Debe indicarse una cantidad válida de bienes!");
        }

        this.descripcion = descripcion;
        this.subcategoria = subcategoria;
        this.cantidad = cantidad;
        this.foto = foto;
    }

    public void fueDonado() {
        this.yaFueDonado = true;
    }
}