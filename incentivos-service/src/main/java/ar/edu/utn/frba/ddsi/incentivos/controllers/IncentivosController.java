package ar.edu.utn.frba.ddsi.incentivos.controllers;

import ar.edu.utn.frba.ddsi.incentivos.dto.DashboardResponse;
import ar.edu.utn.frba.ddsi.incentivos.dto.DonacionRequest;
import ar.edu.utn.frba.ddsi.incentivos.dto.InsigniaResponse;
import ar.edu.utn.frba.ddsi.incentivos.dto.MisionesResponse;
import ar.edu.utn.frba.ddsi.incentivos.dto.PerfilResponse;
import ar.edu.utn.frba.ddsi.incentivos.dto.RegistrarDonanteRequest;
import ar.edu.utn.frba.ddsi.incentivos.dto.mappers.IncentivosMapper;
import ar.edu.utn.frba.ddsi.incentivos.models.entities.EventoDonacion;
import ar.edu.utn.frba.ddsi.incentivos.services.AnaliticaService;
import ar.edu.utn.frba.ddsi.incentivos.services.IncentivosService;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/incentivos/donantes")
public class IncentivosController {

  private final IncentivosService incentivosService;
  private final AnaliticaService analiticaService;
  private final IncentivosMapper mapper;

  public IncentivosController(IncentivosService incentivosService, AnaliticaService analiticaService,
                              IncentivosMapper mapper) {
    this.incentivosService = incentivosService;
    this.analiticaService = analiticaService;
    this.mapper = mapper;
  }

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public PerfilResponse registrar(@RequestBody RegistrarDonanteRequest request) {
    return mapper.toPerfilResponse(
        incentivosService.registrarDonante(request.donanteId(), request.user()));
  }

  @PostMapping("/{donanteId}/donaciones")
  public PerfilResponse registrarDonacion(@PathVariable Long donanteId,
                                          @RequestBody DonacionRequest request) {
    EventoDonacion evento = new EventoDonacion(
        request.fecha(), request.categoria(), request.cantidadBienes(), request.exitosa());
    incentivosService.registrarDonacion(donanteId, evento);
    return mapper.toPerfilResponse(incentivosService.obtenerPerfil(donanteId));
  }

  @GetMapping("/{donanteId}/perfil")
  public PerfilResponse perfil(@PathVariable Long donanteId) {
    return mapper.toPerfilResponse(incentivosService.obtenerPerfil(donanteId));
  }

  @GetMapping("/{donanteId}/misiones")
  public MisionesResponse misiones(@PathVariable Long donanteId) {
    return mapper.toMisionesResponse(incentivosService.obtenerPerfil(donanteId));
  }

  @GetMapping("/{donanteId}/insignias")
  public List<InsigniaResponse> insignias(@PathVariable Long donanteId) {
    return incentivosService.insignias(donanteId).stream()
        .map(mapper::toInsigniaResponse)
        .toList();
  }

  @GetMapping("/{donanteId}/dashboard")
  public DashboardResponse dashboard(@PathVariable Long donanteId) {
    return analiticaService.armarDashboard(donanteId);
  }
}
