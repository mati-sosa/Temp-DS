package ar.edu.utn.frba.ddsi.incentivos.models.entities;

import java.time.LocalDate;

import lombok.Getter;

/** Insignia otorgada al donante al completar una misión. */
@Getter
public class Insignia {
  private final String nombre;
  private final Categoria categoria;
  private final LocalDate fechaObtenida;

  public Insignia(String nombre, Categoria categoria) {
    if (nombre == null || nombre.isBlank()) {
      throw new IllegalArgumentException("La insignia debe tener un nombre");
    }
    if (categoria == null) {
      throw new IllegalArgumentException("La insignia debe pertenecer a una categoría");
    }
    this.nombre = nombre;
    this.categoria = categoria;
    this.fechaObtenida = LocalDate.now();
  }
}
