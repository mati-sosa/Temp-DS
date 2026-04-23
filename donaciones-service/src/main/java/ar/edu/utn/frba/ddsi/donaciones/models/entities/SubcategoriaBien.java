package ar.edu.utn.frba.ddsi.donaciones.models.entities;

public class SubcategoriaBien {
    private String descripcion;
    private Boolean esPerecedero;

    public SubcategoriaBien(String descripcion, Boolean esPerecedero) {
        if (descripcion == null) {
            throw new IllegalArgumentException("¡La subcategoría debe contener una descripción!");
        }
        if (esPerecedero == null) {
            throw new IllegalArgumentException("¡Debe indicarse si la subcategoría es perecedera o no!");
        }

        this.descripcion = descripcion;
        this.esPerecedero = esPerecedero;
    }
}