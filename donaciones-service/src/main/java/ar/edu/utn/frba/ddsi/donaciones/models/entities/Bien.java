package ar.edu.utn.frba.ddsi.donaciones.models.entities;

public class Bien {
    private String descripcion;
    private SubcategoriaBien subcategoria;
    // private CantidadDeBien cantidadDeBien;
    private Double cantidadDeBien;

    public Bien(String descripcion, SubcategoriaBien subcategoria, Double cantidadDeBien) {
        if (descripcion == null) {
            throw new IllegalArgumentException("¡El bien debe tener una descripción!");
        }
        if (subcategoria == null) {
            throw new IllegalArgumentException("¡El bien debe tener una subcategoría asociada!");
        }

        this.descripcion = descripcion;
        this.subcategoria = subcategoria;
        this.cantidadDeBien = cantidadDeBien;
    }

    public SubcategoriaBien getSubcategoria() {
        return subcategoria;
    }
    public Double getCantidad() {
        return cantidadDeBien;
    }
    // TO-DO: implementar métodos para modificar (+/-) la cantidad a donar
}
