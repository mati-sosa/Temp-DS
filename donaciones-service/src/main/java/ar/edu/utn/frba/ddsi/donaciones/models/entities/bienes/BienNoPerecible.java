package ar.edu.utn.frba.ddsi.donaciones.models.entities.bienes;

import lombok.Getter;
import lombok.Setter;

public class BienNoPerecible extends Bien {
    private boolean tieneEstado;
    @Getter
    @Setter
    private boolean estado;

    public BienNoPerecible(String descripcion, SubcategoriaBien subcategoria, Double cantidad, String foto, boolean tieneEstado, boolean estado) {
        super(descripcion, subcategoria, cantidad, foto);
        this.tieneEstado = tieneEstado;
        this.estado = estado;
    }

    @Override
    public String toString(){
        return getDescripcion() + " " +
                getSubcategoria() + " " +
                getCantidad() + " " +
                getFoto();
    }
}
