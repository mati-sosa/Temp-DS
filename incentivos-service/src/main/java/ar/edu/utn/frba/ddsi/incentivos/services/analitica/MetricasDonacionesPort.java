package ar.edu.utn.frba.ddsi.incentivos.services.analitica;

/**
 * Interfaz saliente hacia el Servicio de Donaciones, para los datos de la analítica que NO viven
 * en este servicio (por ejemplo, a cuántas entidades beneficiarias distintas ayudó un donante).
 *
 * <p>Extension point: hoy hay un stub. La implementación real será un Adapter que consulte al
 * Servicio de Donaciones por su API REST, una vez que ese CRUD esté integrado en Entrega-2.
 */
@FunctionalInterface
public interface MetricasDonacionesPort {
  int organizacionesAyudadas(Long donanteId);
}
