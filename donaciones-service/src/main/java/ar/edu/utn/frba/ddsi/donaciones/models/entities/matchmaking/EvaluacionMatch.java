package ar.edu.utn.frba.ddsi.donaciones.models.entities.matchmaking;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EvaluacionMatch {
    private PosibleMatch posibleMatch;
    private double score;

    public EvaluacionMatch(PosibleMatch unPosibleMatch, double unScore){
        posibleMatch = unPosibleMatch;
        score = unScore;
    }

    @Override
    public String toString() {
        return posibleMatch + ", " + score;
    }
}
