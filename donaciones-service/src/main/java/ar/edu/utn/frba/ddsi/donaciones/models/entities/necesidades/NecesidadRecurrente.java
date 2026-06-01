package ar.edu.utn.frba.ddsi.donaciones.models.entities.necesidades;

import ar.edu.utn.frba.ddsi.donaciones.models.entities.bienes.SubcategoriaBien;
import lombok.Getter;

@Getter
public class NecesidadRecurrente extends Necesidad {
    private Double cantidadPorPeriodo;
    private Periodo periodo;

    public NecesidadRecurrente(
            String descripcion,
            SubcategoriaBien subCategoria,
            Double cantidadRequerida,
            Double cantidadPorPeriodo, Periodo periodo) {
        super(descripcion, subCategoria, cantidadRequerida);
        this.cantidadPorPeriodo = cantidadPorPeriodo;
        this.periodo = periodo;
    }

    @Override
    public Boolean estaSatisfecha() {
        return getCantidadCubierta() >= cantidadPorPeriodo;
    }

    @Override
    public String toString(){
        return getDescripcion() + " " +
                getSubcategoria() + " " +
                getCantidadRequerida() + " " +
                cantidadPorPeriodo  + " " +
                periodo;
    }
}
