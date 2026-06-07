package ar.edu.utn.frba.ddsi.donaciones.controllers;

import ar.edu.utn.frba.ddsi.donaciones.models.entities.matchmaking.EvaluacionMatch;
import ar.edu.utn.frba.ddsi.donaciones.services.MatchmakingService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/matchmaking")
public class MatchmakingController {

    private final MatchmakingService matchmakingService;

    public MatchmakingController(MatchmakingService matchmakingService) {
        this.matchmakingService = matchmakingService;
    }

    @PostMapping("/ejecutar")
    public List<List<Map<String, Object>>> ejecutar() {
        return toResponse(matchmakingService.ejecutarAhora());
    }

    @GetMapping("/rankings")
    public List<List<Map<String, Object>>> obtenerRankings() {
        return toResponse(matchmakingService.obtenerUltimosRankings());
    }

    @PostMapping("/confirmar/{donacionId}/{entidadId}")
    public Map<String, Object> confirmarAsignacion(@PathVariable Long donacionId,
                                                   @PathVariable Long entidadId) {
        var donacion = matchmakingService.confirmarAsignacion(donacionId, entidadId);
        return Map.of(
                "donacionId", donacion.getId(),
                "entidadId", donacion.getEntidadBeneficiariaId(),
                "estadoActual", donacion.getEstado().nombre()
        );
    }

    private List<List<Map<String, Object>>> toResponse(List<List<EvaluacionMatch>> rankings) {
        return rankings.stream()
                .map(ranking -> ranking.stream()
                        .map(em -> Map.<String, Object>of(
                                "score", em.getScore(),
                                "donacionId", em.getPosibleMatch().getDonacion().getId() != null
                                        ? em.getPosibleMatch().getDonacion().getId() : -1L,
                                "entidadId", em.getPosibleMatch().getEntidadBeneficiaria().getId() != null
                                        ? em.getPosibleMatch().getEntidadBeneficiaria().getId() : -1L,
                                "necesidad", em.getPosibleMatch().getNecesidad().getDescripcion(),
                                "subcategoria", em.getPosibleMatch().getDonacion()
                                        .getSubcategoriaBien().getDescripcion()
                        ))
                        .toList())
                .toList();
    }
}
