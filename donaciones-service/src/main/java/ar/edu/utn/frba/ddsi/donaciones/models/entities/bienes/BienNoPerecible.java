package ar.edu.utn.frba.ddsi.donaciones.models.entities.bienes;

public class BienNoPerecible extends Bien {

    public BienNoPerecible(String descripcion, SubcategoriaBien subcategoria, Double cantidad, String foto) {
        super(descripcion, subcategoria, cantidad, foto);
    }

    @Override
    public String toString(){
        return getDescripcion() + " " +
                getSubcategoria() + " " +
                getCantidad() + " " +
                getFoto();
    }
}
