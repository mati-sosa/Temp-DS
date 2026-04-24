package ar.edu.utn.frba.ddsi.donaciones.models.entities.necesidades;

import ar.edu.utn.frba.ddsi.donaciones.models.entities.bienes.CategoriaBien;
import ar.edu.utn.frba.ddsi.donaciones.models.entities.bienes.CantidadDeBien;
import lombok.Getter;

@Getter
public class NecesidadRecurrente extends Necesidad {
    private Double cantidadPorPeriodo;
    private Periodo periodo;

    public NecesidadRecurrente(String descripcion, CategoriaBien categoria, CantidadDeBien cantidadDeBien, Double cantidadPorPeriodo, Periodo periodo) {
        super(descripcion, categoria, cantidadDeBien);
        this.cantidadPorPeriodo = cantidadPorPeriodo;
        this.periodo = periodo;
    }

    @Override
    public Boolean estaSatisfecha() {
        return getCantidadCubierta() >= cantidadPorPeriodo;
    }
}
