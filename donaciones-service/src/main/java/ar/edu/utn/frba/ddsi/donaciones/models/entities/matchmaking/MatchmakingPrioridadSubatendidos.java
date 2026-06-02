package ar.edu.utn.frba.ddsi.donaciones.models.entities.matchmaking;

public class MatchmakingPrioridadSubatendidos implements EstrategiaMatchmaking {
    private double factorDeSuavizado;
    /** ESTO HAY QUE CAMBIARLO, LA CANTIDAD DE DONACIONES LA TENGO QUE OBTENER DESDE UNPOSIBLEMATCH **/
    private double CantDonacionesTrimestre;

    public MatchmakingPrioridadSubatendidos(double unFactorDeSuavizado, double unaCantDonacionesTrimestre){
        factorDeSuavizado = unFactorDeSuavizado;
        CantDonacionesTrimestre = unaCantDonacionesTrimestre;
    }

    @Override
    public EvaluacionMatch evaluar(PosibleMatch unPosibleMatch){
        double score = 1 / (1 + factorDeSuavizado * CantDonacionesTrimestre);

        EvaluacionMatch unaEvaluacionMatch = new EvaluacionMatch(unPosibleMatch,score);
        return unaEvaluacionMatch;
    }
}
