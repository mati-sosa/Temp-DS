package ar.edu.utn.frba.ddsi.incentivos.dto;

import java.util.Map;

/** Vista consolidada para el perfil/dashboard del donante (datos de incentivos + de donaciones). */
public record DashboardResponse(
    Long donanteId,
    String user,
    String categoria,
    int totalDonaciones,
    int totalBienesDonados,
    int organizacionesAyudadas,
    Map<String, Integer> donacionesPorMes,
    int donacionesMesActual,
    int donacionesMesAnterior,
    int totalInsignias,
    int misionesCompletadas,
    Integer posicionEnRanking) {
}
