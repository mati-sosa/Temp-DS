package ar.edu.utn.frba.ddsi.donaciones.models.entities.necesidades;

import ar.edu.utn.frba.ddsi.donaciones.models.entities.bienes.SubcategoriaBien;
import lombok.Getter;

@Getter
public class NecesidadRecurrente extends Necesidad {
    private Double cantidadPorPeriodo;
    private Periodo periodo;

    public NecesidadRecurrente(String descripcion, SubcategoriaBien categoria, Double cantidadRequerida, Double cantidadPorPeriodo, Periodo periodo) {
        super(descripcion, categoria, cantidadRequerida);
        this.cantidadPorPeriodo = cantidadPorPeriodo;
        this.periodo = periodo;
    }

    @Override
    public Boolean estaSatisfecha() {
        return getCantidadCubierta() >= cantidadPorPeriodo;
    }
}
