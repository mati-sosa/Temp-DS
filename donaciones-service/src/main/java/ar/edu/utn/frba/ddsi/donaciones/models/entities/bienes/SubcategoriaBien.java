package ar.edu.utn.frba.ddsi.donaciones.models.entities.bienes;

import lombok.Getter;

@Getter
public class SubcategoriaBien {
    private String descripcion;
    private Boolean esPerecedero;
    private CategoriaBien categoriaBien;

    public SubcategoriaBien(String descripcion, Boolean esPerecedero, CategoriaBien categoriaBien) {
        if (descripcion == null) {
            throw new IllegalArgumentException("¡La subcategoría debe contener una descripción!");
        }
        if (esPerecedero == null) {
            throw new IllegalArgumentException("¡Debe indicarse si la subcategoría es perecedera o no!");
        }

        this.descripcion = descripcion;
        this.esPerecedero = esPerecedero;
        this.categoriaBien = categoriaBien;
    }
}
