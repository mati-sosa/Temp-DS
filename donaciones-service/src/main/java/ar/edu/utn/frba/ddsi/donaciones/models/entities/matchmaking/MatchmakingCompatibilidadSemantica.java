package ar.edu.utn.frba.ddsi.donaciones.models.entities.matchmaking;

import ar.edu.utn.frba.ddsi.donaciones.models.entities.bienes.Bien;
import ar.edu.utn.frba.ddsi.donaciones.models.entities.necesidades.NecesidadRecurrente;

import static ar.edu.utn.frba.ddsi.donaciones.models.entities.matchmaking.LevenshteinDistance.computeLevenshteinDistance;

public class MatchmakingCompatibilidadSemantica implements EstrategiaMatchmaking{
    private double pesoTextual;
    private double pesoVolumen;
    private double pesoTipo;

    public MatchmakingCompatibilidadSemantica(double unPesoTextual,double unPesoVolumen,double unPesoTipo){
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
        return peso >= 0 && peso < 1;
    }


    @Override
    public EvaluacionMatch evaluar(PosibleMatch unPosibleMatch) {
        double score =
                pesoTextual * Math.pow(similitudTextual(unPosibleMatch),2) +
                pesoVolumen * coberturaVolumen(unPosibleMatch) +
                pesoTipo * tipoNecesidad(unPosibleMatch);

        EvaluacionMatch unaEvaluacionMatch = new EvaluacionMatch(unPosibleMatch,score);
        return unaEvaluacionMatch;
    }

    private double similitudTextual(PosibleMatch unPosibleMatch){
        return 1 - ((double) computeLevenshteinDistance(
                unPosibleMatch.getDonacion().getSubcategoriaBien().getDescripcion(),
                unPosibleMatch.getNecesidad().getSubcategoria().getDescripcion()
        ) /Math.max(
                unPosibleMatch.getDonacion().getSubcategoriaBien().getDescripcion().length(),
                unPosibleMatch.getNecesidad().getSubcategoria().getDescripcion().length()
        ));
    }

    private double coberturaVolumen(PosibleMatch unPosibleMatch) {
        double cantDonada = unPosibleMatch.getDonacion().getBienes().stream()
                .mapToDouble(Bien::getCantidad).sum();
        double cantRequerida = unPosibleMatch.getNecesidad().getCantidadRequerida();
        double r = cantDonada / cantRequerida;
        return r <= 1 ? r : 1.0 / r;
    }

    private double tipoNecesidad(PosibleMatch unPosibleMatch) {
        return unPosibleMatch.getNecesidad() instanceof NecesidadRecurrente ? 0.5 : 1.0;
    }

}
