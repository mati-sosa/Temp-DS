package ar.edu.utn.frba.ddsi.incentivos.models.entities;

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

  // Se define que la categoría máxima es la de mayor orden
  public boolean esMaxima() {
    return ordinal() == values().length - 1;
  }

  // Devuelve la siguiente categoría en orden, o la misma si ya es la máxima
  public Categoria siguiente() {
    if (esMaxima()) {
      return this;
    }
    return values()[ordinal() + 1];
  }
}