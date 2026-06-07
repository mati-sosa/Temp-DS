package ar.edu.utn.frba.ddsi.incentivos.dto;

import java.time.LocalDate;

public record InsigniaResponse(String nombre, String categoria, LocalDate fechaObtenida) {
}
