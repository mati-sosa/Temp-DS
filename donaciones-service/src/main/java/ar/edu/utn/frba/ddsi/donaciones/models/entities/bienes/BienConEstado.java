package ar.edu.utn.frba.ddsi.donaciones.models.entities.bienes;

import lombok.Getter;

@Getter
public class BienConEstado extends BienNoPerecible {
    private Boolean esNuevo;

    public BienConEstado(String descripcion, SubcategoriaBien subcategoria, Double cantidadDeBien, String foto, Boolean esNuevo) {
        super(descripcion, subcategoria, cantidadDeBien, foto);
        
        if (esNuevo == null) {
            throw new IllegalArgumentException("¡Se debe ingresar un estado!");
        }
        this.esNuevo = esNuevo;
    }

    @Override
    public String toString(){
        return getDescripcion() + " " +
                getSubcategoria() + " " +
                getCantidad() + " " +
                getFoto() + " " +
                getEsNuevo();
    }
}
