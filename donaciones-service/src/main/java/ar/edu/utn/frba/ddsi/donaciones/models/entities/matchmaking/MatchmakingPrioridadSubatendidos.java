package ar.edu.utn.frba.ddsi.donaciones.models.entities.matchmaking;

public class MatchmakingPrioridadSubatendidos implements EstrategiaMatchmaking {
    private final double factorDeSuavizado;

    public MatchmakingPrioridadSubatendidos(double unFactorDeSuavizado) {
        factorDeSuavizado = unFactorDeSuavizado;
    }

    @Override
    public EvaluacionMatch evaluar(PosibleMatch unPosibleMatch) {
        int cantDonacionesTrimestre = unPosibleMatch.getEntidadBeneficiaria().getDonacionesRecibidasEnTrimestre();
        double score = 1.0 / (1 + factorDeSuavizado * cantDonacionesTrimestre);
        return new EvaluacionMatch(unPosibleMatch, score);
    }
}
