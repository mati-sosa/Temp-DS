package ar.edu.utn.frba.ddsi.donaciones.models.entities.bienes;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class CategoriaBien {
    private String descripcion;
    private List<SubcategoriaBien> subcategorias = null;

    public CategoriaBien(String descripcion, List<SubcategoriaBien> subcategorias) {
        this.descripcion = descripcion;
    }

    public void agregarSubcategoria(List<SubcategoriaBien> nuevaSubcategoria) {
        if (nuevaSubcategoria == null) {
            throw new IllegalArgumentException("¡La subcategoría a agregar no puede ser NULL!");
        }

        this.subcategorias.addAll(nuevaSubcategoria);
    }

    public void eliminarSubcategoria(SubcategoriaBien subcategoriaEliminada) {
        this.subcategorias.remove(subcategoriaEliminada);
    }

    public Boolean contieneSubcategoria(SubcategoriaBien subcategoria) {
        return this.subcategorias.contains(subcategoria);
    }
}
