package ar.edu.utn.frba.ddsi.incentivos.services.analitica;

import org.springframework.stereotype.Service;

/**
 * Stub temporal de {@link MetricasDonacionesPort}. Devuelve 0 hasta que exista la integración real
 * con el Servicio de Donaciones.
 *
 * <p>Extension point: reemplazar por un Adapter (RestClient hacia donaciones-service) cuando el
 * CRUD de Donaciones esté disponible en Entrega-2.
 */
@Service
public class MetricasDonacionesStub implements MetricasDonacionesPort {

  @Override
  public int organizacionesAyudadas(Long donanteId) {
    return 0;
  }
}
