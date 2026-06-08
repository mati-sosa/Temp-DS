package ar.edu.utn.frba.ddsi.donaciones.models.entities.matchmaking;

import ar.edu.utn.frba.ddsi.donaciones.models.entities.bienes.Bien;
import ar.edu.utn.frba.ddsi.donaciones.models.entities.necesidades.NecesidadRecurrente;

import static ar.edu.utn.frba.ddsi.donaciones.models.entities.matchmaking.LevenshteinDistance.computeLevenshteinDistance;

public class MatchmakingCompatibilidadSemantica implements EstrategiaMatchmaking{
    @Override
    public EvaluacionMatch evaluar(PosibleMatch unPosibleMatch) {
        double score =
                unPosibleMatch.getEntidadBeneficiaria().getPreferencias().getPesoTextual() * Math.pow(similitudTextual(unPosibleMatch),2) +
                unPosibleMatch.getEntidadBeneficiaria().getPreferencias().getPesoVolumen() * coberturaVolumen(unPosibleMatch) +
                unPosibleMatch.getEntidadBeneficiaria().getPreferencias().getPesoTipo() * tipoNecesidad(unPosibleMatch);

        EvaluacionMatch unaEvaluacionMatch = new EvaluacionMatch(unPosibleMatch,score);
        return unaEvaluacionMatch;
    }

    private double similitudTextual(PosibleMatch unPosibleMatch){
        String nombreBien = unPosibleMatch.getDonacion().getSubcategoriaBien().getDescripcion();
        String descNecesidad = unPosibleMatch.getNecesidad().getDescripcion();
        return 1 - ((double) computeLevenshteinDistance(nombreBien, descNecesidad)
                / Math.max(nombreBien.length(), descNecesidad.length()));
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
