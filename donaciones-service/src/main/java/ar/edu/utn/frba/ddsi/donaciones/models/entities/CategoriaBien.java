package ar.edu.utn.frba.ddsi.donaciones.models.entities;
import java.util.ArrayList;
import java.util.List;

public class CategoriaBien {
    private String descripcion;
    private List<SubcategoriaBien> subcategorias;
    // falta modelar las categorías que tienen un ESTADO... 
    // esto va en la categoría, o en el bien?

    public CategoriaBien(String descripcion, List<SubcategoriaBien> subcategorias) {
        // Una categoría no podría empezar con una lista de subcategorías vacía? 
        // Mi respuesta: ¡¡NO!! A mi entender, como una subcategoría es la unidad atómica de asignación, 
        // si se crea una categoría sin subcategorías asociadas entonces ningún bien va a poder sumarse a esta categoría,
        // ya que todo bien debe tener una subcategoría asociada... O al menos así lo entiendo yo!
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
        // VER eliminarMedio en Donante.java
        // if (!this.subcategorias.contains(subcategoriaEliminada)) {
        //     throw new IllegalArgumentException("La subcategoría a eliminar no puede ser NULL!");
        // }

        this.subcategorias.remove(subcategoriaEliminada);
    }

    public Boolean contieneSubcategoria(SubcategoriaBien subcategoria) {
        return this.subcategorias.contains(subcategoria);
    }
}
