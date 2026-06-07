package ar.edu.utn.frba.ddsi.incentivos.models.entities;

/**
 * Categorías de donante. Son secuenciales: al completar todas las misiones de una,
 * se asciende a la siguiente.
 */
public enum Categoria {
  COLABORADOR(1),
  SOSTENEDOR(2),
  TRANSFORMADOR(3);

  private final int orden;

  Categoria(int orden) {
    this.orden = orden;
  }

  public int getOrden() {
    return orden;
  }

  public boolean esMaxima() {
    return this == TRANSFORMADOR;
  }

  public Categoria siguiente() {
    return switch (this) {
      case COLABORADOR -> SOSTENEDOR;
      case SOSTENEDOR -> TRANSFORMADOR;
      case TRANSFORMADOR -> TRANSFORMADOR;
    };
  }
}
