package ar.edu.utn.frba.ddsi.incentivos.models.entities;

import java.time.YearMonth;
import java.util.List;
import java.util.TreeSet;
import java.util.stream.Collectors;

/**
 * Racha: donar durante X meses consecutivos.
 * Progreso = cantidad de meses consecutivos (hasta el último mes con donación) con al menos una.
 *
 * <p>Extension point (P4 - Francisco): contemplar la pérdida de progreso si pasa un mes
 * completo sin donar.
 */
public class MisionRacha extends Mision {

  public MisionRacha(String nombre, String descripcion, Categoria categoria, int orden, int objetivoMeses) {
    super(nombre, descripcion, categoria, orden, objetivoMeses);
  }

  @Override
  public int calcularProgreso(List<EventoDonacion> historial) {
    if (historial.isEmpty()) {
      return 0;
    }
    TreeSet<YearMonth> meses = historial.stream()
        .map(evento -> YearMonth.from(evento.getFecha()))
        .collect(Collectors.toCollection(TreeSet::new));

    int racha = 0;
    YearMonth cursor = meses.last();
    while (meses.contains(cursor)) {
      racha++;
      cursor = cursor.minusMonths(1);
    }
    return racha;
  }
}
