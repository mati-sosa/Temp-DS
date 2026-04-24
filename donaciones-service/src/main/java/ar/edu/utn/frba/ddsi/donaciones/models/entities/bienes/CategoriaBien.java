package ar.edu.utn.frba.ddsi.donaciones.models.entities.bienes;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class CategoriaBien {
    private String descripcion;
    private List<SubcategoriaBien> subcategorias;

    public CategoriaBien(String descripcion, List<SubcategoriaBien> subcategorias) {
        if (subcategorias == null || subcategorias.isEmpty()) {
            throw new IllegalArgumentException("¡Una categoría debe tener al menos una subcategoría asociada!");
        }

        this.descripcion = descripcion;
        this.subcategorias = new ArrayList<>(subcategorias);
    }

    public void agregarSubcategoria(SubcategoriaBien nuevaSubcategoria) {
        if (nuevaSubcategoria == null) {
            throw new IllegalArgumentException("¡La subcategoría a agregar no puede ser NULL!");
        }
        if (this.subcategorias.contains(nuevaSubcategoria)) {
            throw new IllegalArgumentException("¡Esta subcategoría ya está asociada a la categoría!");
        }

        this.subcategorias.add(nuevaSubcategoria);
    }

    public void eliminarSubcategoria(SubcategoriaBien subcategoriaEliminada) {
        this.subcategorias.remove(subcategoriaEliminada);
    }

    public Boolean contieneSubcategoria(SubcategoriaBien subcategoria) {
        return this.subcategorias.contains(subcategoria);
    }
}
