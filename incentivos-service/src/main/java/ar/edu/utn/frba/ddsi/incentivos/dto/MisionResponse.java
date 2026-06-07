package ar.edu.utn.frba.ddsi.incentivos.dto;

public record MisionResponse(
    String nombre,
    String descripcion,
    String categoria,
    String estado,
    int progreso,
    int objetivo) {
}
