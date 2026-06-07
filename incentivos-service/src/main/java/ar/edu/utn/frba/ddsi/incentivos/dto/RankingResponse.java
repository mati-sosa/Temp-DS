package ar.edu.utn.frba.ddsi.incentivos.dto;

import java.time.LocalDate;
import java.util.List;

public record RankingResponse(int anio, int mes, LocalDate fechaPublicacion, List<PuestoResponse> puestos) {
}
