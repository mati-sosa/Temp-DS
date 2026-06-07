package ar.edu.utn.frba.ddsi.incentivos.services.difusion;

import ar.edu.utn.frba.ddsi.incentivos.models.eventos.InsigniaObtenida;

/**
 * Contrato (dominio) para difundir una insignia obtenida hacia afuera del sistema.
 * La implementación concreta (Adapter) decide el medio; el dominio no se acopla a esa herramienta.
 */
@FunctionalInterface
public interface DifusorDeInsignias {
  void difundir(InsigniaObtenida insignia);
}
