package ar.edu.utn.frba.ddsi.incentivos.dto;

public record PerfilResponse(
    Long donanteId,
    String user,
    String categoria,
    String misionActual,
    int progresoActual,
    int objetivoActual,
    int distanciaRestante,
    int totalInsignias,
    int misionesCompletadas) {
}
