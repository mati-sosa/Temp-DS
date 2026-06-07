package ar.edu.utn.frba.ddsi.incentivos.models.entities;

import java.time.LocalDate;

import lombok.Getter;

/** Insignia otorgada al donante al completar una misión. */
@Getter
public class Insignia {
  private final String nombre;
  private final Categoria categoria;
  private final LocalDate fechaObtenida;

  // TODO: validaciones de campos
  public Insignia(String nombre, Categoria categoria) {
    this.nombre = nombre;
    this.categoria = categoria;
    this.fechaObtenida = LocalDate.now();
  }
}
