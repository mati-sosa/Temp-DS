package ar.edu.utn.frba.ddsi.incentivos.models.factories;

import ar.edu.utn.frba.ddsi.incentivos.models.entities.Categoria;
import ar.edu.utn.frba.ddsi.incentivos.models.entities.misiones.Mision;
import ar.edu.utn.frba.ddsi.incentivos.models.entities.misiones.MisionCompletitud;
import ar.edu.utn.frba.ddsi.incentivos.models.entities.misiones.MisionDonacionesExitosas;
import ar.edu.utn.frba.ddsi.incentivos.models.entities.misiones.MisionHabilDonador;
import ar.edu.utn.frba.ddsi.incentivos.models.entities.misiones.MisionRacha;

import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Component;

/**
 * Construye y conoce el catálogo de misiones, ordenado por categoría y orden.
 * Encapsula la creación (Simple Factory) porque hoy son pocas pero podrán sumarse más.
 *
 * <p>Extension point (P4 - Francisco): ajustar misiones, objetivos y orden según la definición final.
 */
@Component
public class CatalogoMisionesFactory {
  private final List<Mision> catalogo;

  public CatalogoMisionesFactory() {
    this.catalogo = crearCatalogo();
  }

  private List<Mision> crearCatalogo() {
    List<Mision> misiones = new ArrayList<>();
    // Colaborador
    misiones.add(new MisionRacha("Racha x2", "Realizá una donación durante 2 meses consecutivos",
        Categoria.COLABORADOR, 1, 2));
    misiones.add(new MisionCompletitud("Completitud x2", "Realizá donaciones de 2 categorías distintas",
        Categoria.COLABORADOR, 2, 2));
    // Sostenedor
    misiones.add(new MisionRacha("Racha x6", "Realizá una donación durante 6 meses consecutivos",
        Categoria.SOSTENEDOR, 1, 6));
    misiones.add(new MisionCompletitud("Completitud x3", "Realizá donaciones de 3 categorías distintas",
        Categoria.SOSTENEDOR, 2, 3));
    misiones.add(new MisionHabilDonador("Hábil Donador", "Realizá una donación que supere los 10 bienes",
        Categoria.SOSTENEDOR, 3, 10));
    // Transformador
    misiones.add(new MisionRacha("Racha x12", "Realizá una donación durante 12 meses consecutivos",
        Categoria.TRANSFORMADOR, 1, 12));
    misiones.add(new MisionCompletitud("Completitud x6", "Realizá donaciones de 6 categorías distintas",
        Categoria.TRANSFORMADOR, 2, 6));
    misiones.add(new MisionDonacionesExitosas("Donaciones Exitosas", "Lográ 5 donaciones recibidas con éxito",
        Categoria.TRANSFORMADOR, 3, 5));
    return misiones;
  }

  public List<Mision> todas() {
    return List.copyOf(catalogo);
  }

  public Mision primera() {
    return catalogo.get(0);
  }

  /** La misión siguiente en el orden global, o null si la dada es la última. */
  public Mision siguienteDe(Mision mision) {
    int indice = indiceDe(mision);
    if (indice < 0 || indice + 1 >= catalogo.size()) {
      return null;
    }
    return catalogo.get(indice + 1);
  }

  private int indiceDe(Mision mision) {
    for (int i = 0; i < catalogo.size(); i++) {
      if (catalogo.get(i).getNombre().equals(mision.getNombre())) {
        return i;
      }
    }
    return -1;
  }
}
