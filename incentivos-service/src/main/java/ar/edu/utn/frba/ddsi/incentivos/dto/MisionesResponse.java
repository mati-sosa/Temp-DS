package ar.edu.utn.frba.ddsi.incentivos.dto;

import java.util.List;

public record MisionesResponse(MisionResponse actual, List<MisionResponse> completadas) {
}
