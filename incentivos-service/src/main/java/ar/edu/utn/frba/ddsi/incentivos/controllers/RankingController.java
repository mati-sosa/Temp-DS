package ar.edu.utn.frba.ddsi.incentivos.controllers;

import ar.edu.utn.frba.ddsi.incentivos.dto.RankingResponse;
import ar.edu.utn.frba.ddsi.incentivos.dto.mappers.IncentivosMapper;
import ar.edu.utn.frba.ddsi.incentivos.services.RankingService;
import java.util.NoSuchElementException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/incentivos/ranking")
public class RankingController {

  private final RankingService rankingService;
  private final IncentivosMapper mapper;

  public RankingController(RankingService rankingService, IncentivosMapper mapper) {
    this.rankingService = rankingService;
    this.mapper = mapper;
  }

  /** Genera (a demanda) y persiste el ranking del período. */
  @PostMapping("/{anio}/{mes}")
  @ResponseStatus(HttpStatus.CREATED)
  public RankingResponse generar(@PathVariable int anio, @PathVariable int mes) {
    return mapper.toRankingResponse(rankingService.generarRanking(anio, mes));
  }

  @GetMapping("/{anio}/{mes}")
  public RankingResponse obtener(@PathVariable int anio, @PathVariable int mes) {
    return rankingService.obtenerRanking(anio, mes)
        .map(mapper::toRankingResponse)
        .orElseThrow(() -> new NoSuchElementException("No hay ranking generado para " + anio + "-" + mes));
  }
}
