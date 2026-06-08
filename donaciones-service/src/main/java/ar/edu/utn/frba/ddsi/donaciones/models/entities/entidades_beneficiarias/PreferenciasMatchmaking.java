package ar.edu.utn.frba.ddsi.donaciones.models.entities.entidades_beneficiarias;

import lombok.Getter;
import lombok.Setter;

@Getter
public class PreferenciasMatchmaking {
    double pesoTextual;
    double pesoVolumen;
    double pesoTipo;

    public PreferenciasMatchmaking(double unPesoTextual,double unPesoVolumen,double unPesoTipo){
        if (!validarPeso(unPesoTextual))
            throw new IllegalArgumentException("El peso textual debe estar entre 0 y 1");
        if (!validarPeso(unPesoVolumen))
            throw new IllegalArgumentException("El peso textual debe estar entre 0 y 1");
        if (!validarPeso(unPesoTipo))
            throw new IllegalArgumentException("El peso textual debe estar entre 0 y 1");
        if ( unPesoTextual + unPesoVolumen + unPesoTipo != 1)
            throw new IllegalArgumentException("La suma debe dar 1");
        pesoTextual = unPesoTextual;
        pesoVolumen = unPesoVolumen;
        pesoTipo = unPesoTipo;
    }

    private boolean validarPeso(double peso){
        return peso >= 0 && peso <= 1;
    }
}
