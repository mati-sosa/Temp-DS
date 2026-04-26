package ar.edu.utn.frba.ddsi.donaciones.models.entities.bienes;

import lombok.Getter;

@Getter
public class SubcategoriaBien {
    private String descripcion;
    private Boolean esPerecedero;
    private CategoriaBien categoriaBien;
    private UnidadDeMedida unidadDeMedida;

    public SubcategoriaBien(String descripcion, Boolean esPerecedero, CategoriaBien categoriaBien, UnidadDeMedida unidadDeMedida) {
        if (descripcion == null) {
            throw new IllegalArgumentException("¡La subcategoría debe contener una descripción!");
        }
        if (esPerecedero == null) {
            throw new IllegalArgumentException("¡Debe indicarse si la subcategoría es perecedera o no!");
        }
        if (unidadDeMedida == null) {
            throw new IllegalArgumentException("¡Debe indicarse una unidad de medida!");
        }

        this.descripcion = descripcion;
        this.esPerecedero = esPerecedero;
        this.categoriaBien = categoriaBien;
        this.unidadDeMedida = unidadDeMedida;
    }

    @Override
    public String toString(){
        return descripcion  + " " +
                esPerecedero + " " +
                categoriaBien + " " +
                unidadDeMedida;
    }
}
