package ar.edu.utn.frba.ddsi.incentivos.services.analitica;

/**
 * Interfaz saliente hacia el Servicio de Donaciones, para los datos de la analítica que NO viven
 * en este servicio (por ejemplo, a cuántas entidades beneficiarias distintas ayudó un donante).
 *
 * <p>La implementación es {@code MetricasDonacionesAdapter}, que consulta al Servicio de
 * Donaciones por su API REST. En los tests se puede inyectar un doble (lambda).
 */
@FunctionalInterface
public interface MetricasDonacionesPort {
  int organizacionesAyudadas(Long donanteId);
}
