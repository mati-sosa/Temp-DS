package ar.edu.utn.frba.ddsi.incentivos.models.entities.misiones;

import java.time.YearMonth;
import java.util.List;
import java.util.TreeSet;
import java.util.stream.Collectors;

import ar.edu.utn.frba.ddsi.incentivos.models.entities.Categoria;
import ar.edu.utn.frba.ddsi.incentivos.models.eventos.EventoDonacion;

/**
 * Racha: donar durante X meses consecutivos.
 * Progreso: cantidad de meses consecutivos (hasta el último mes con donación) con al menos una
 */
public class MisionRacha extends Mision {

  public MisionRacha(String nombre, String descripcion, Categoria categoria, int orden, int objetivoMeses) {
    super(nombre, descripcion, categoria, orden, objetivoMeses);
  }

  // Respecto a la pérdida de progreso si se pierde la racha de meses consecutivos:
  // se asume que el progreso se mantiene hasta el final del mes siguiente, 
  // donde se evaluará si se perdió la racha o no
  @Override
  public int calcularProgreso(List<EventoDonacion> historial) {
    TreeSet<YearMonth> meses = historial.stream()
        .map(evento -> YearMonth.from(evento.getFecha()))
        .collect(Collectors.toCollection(TreeSet::new));

    int racha = 0;
    YearMonth cursor = YearMonth.now();
    while (meses.contains(cursor)) {
      racha++;
      cursor = cursor.minusMonths(1);
    }

    return racha;
  }
}
