package ar.edu.utn.frba.ddsi.donaciones.models.entities.necesidades;

import lombok.Getter;

@Getter
public class Periodo {
    private TipoPeriodo tipoPeriodo;
    private int cantidad;

    public Periodo(TipoPeriodo tipoPeriodo, int cantidad) {
        this.tipoPeriodo = tipoPeriodo;
        this.cantidad = cantidad;
    }

    @Override
    public String toString(){
        return tipoPeriodo + " " + cantidad;
    }
}
