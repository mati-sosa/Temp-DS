package ar.edu.utn.frba.ddsi.incentivos.models.repositories;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.springframework.stereotype.Repository;

import ar.edu.utn.frba.ddsi.incentivos.models.entities.ranking.RankingMensual;

/** Repositorio en memoria de los rankings mensuales (la persistencia real llega en la Entrega 4). */
@Repository
public class RankingMensualRepository {
  private final Map<String, RankingMensual> rankings = new HashMap<>();

  public RankingMensual guardar(RankingMensual ranking) {
    rankings.put(clave(ranking.getAnio(), ranking.getMes()), ranking);
    return ranking;
  }

  public Optional<RankingMensual> buscar(int anio, int mes) {
    return Optional.ofNullable(rankings.get(clave(anio, mes)));
  }

  public List<RankingMensual> buscarTodos() {
    return new ArrayList<>(rankings.values());
  }

  private String clave(int anio, int mes) {
    return anio + "-" + mes;
  }
}
