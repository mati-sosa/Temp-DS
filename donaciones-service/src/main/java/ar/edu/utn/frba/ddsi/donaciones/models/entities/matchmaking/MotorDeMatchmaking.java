package ar.edu.utn.frba.ddsi.donaciones.models.entities.matchmaking;

import ar.edu.utn.frba.ddsi.donaciones.models.entities.donaciones.Donacion;
import ar.edu.utn.frba.ddsi.donaciones.models.entities.entidades_beneficiarias.EntidadBeneficiaria;
import ar.edu.utn.frba.ddsi.donaciones.models.entities.necesidades.Necesidad;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class MotorDeMatchmaking {
    private final List<EstrategiaMatchmaking> estrategias;
    private final GeneradorDeMatches generadorDeMatches;

    public MotorDeMatchmaking(List<EstrategiaMatchmaking> estrategias) {
        this.estrategias = estrategias;
        this.generadorDeMatches = new GeneradorDeMatches();
    }

    /**
     * Ejecuta todos los algoritmos de matchmaking sobre las donaciones en depósito y las
     * entidades beneficiarias. Devuelve la intersección de los top-10 de cada algoritmo;
     * si no hay intersección, devuelve los rankings de cada algoritmo por separado.
     */
    public List<List<EvaluacionMatch>> ejecutar(ArrayList<Donacion> donaciones, ArrayList<EntidadBeneficiaria> entidades) {
        ArrayList<PosibleMatch> posiblesMatches = generadorDeMatches.generarMatches(donaciones, entidades);

        List<List<EvaluacionMatch>> rankingsPorEstrategia = estrategias.stream()
                .map(estrategia -> generarRanking(estrategia, posiblesMatches))
                .collect(Collectors.toList());

        return intersectarRankings(rankingsPorEstrategia);
    }

    private List<EvaluacionMatch> generarRanking(EstrategiaMatchmaking estrategia, List<PosibleMatch> posiblesMatches) {
        return posiblesMatches.stream()
                .map(estrategia::evaluar)
                .sorted((em1, em2) -> Double.compare(em2.getScore(), em1.getScore()))
                .limit(10)
                .collect(Collectors.toList());
    }

    /**
     * Devuelve las evaluaciones que aparecen en el top-10 de TODOS los algoritmos.
     * Si la intersección es vacía, devuelve los rankings por separado para que el
     * administrador pueda elegir.
     */
    private List<List<EvaluacionMatch>> intersectarRankings(List<List<EvaluacionMatch>> rankings) {
        if (rankings.isEmpty()) return rankings;

        List<EvaluacionMatch> listaBase = rankings.get(0);
        List<EvaluacionMatch> interseccion = new ArrayList<>();

        for (EvaluacionMatch em : listaBase) {
            Necesidad necesidad = em.getPosibleMatch().getNecesidad();
            boolean apareceEnTodas = true;

            for (int j = 1; j < rankings.size(); j++) {
                boolean apareceEnEste = rankings.get(j).stream()
                        .anyMatch(em2 -> em2.getPosibleMatch().getNecesidad()
                                .getSubcategoria().getDescripcion()
                                .equals(necesidad.getSubcategoria().getDescripcion()));
                if (!apareceEnEste) {
                    apareceEnTodas = false;
                    break;
                }
            }
            if (apareceEnTodas) interseccion.add(em);
        }

        return interseccion.isEmpty() ? rankings : List.of(interseccion);
    }
}
