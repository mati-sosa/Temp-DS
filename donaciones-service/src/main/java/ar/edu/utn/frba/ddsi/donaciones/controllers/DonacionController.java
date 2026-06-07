package ar.edu.utn.frba.ddsi.donaciones.controllers;

import ar.edu.utn.frba.ddsi.donaciones.dto.*;
import ar.edu.utn.frba.ddsi.donaciones.dto.mappers.DonacionMapper;
import ar.edu.utn.frba.ddsi.donaciones.models.entities.donantes.Donante;
import ar.edu.utn.frba.ddsi.donaciones.services.DonacionService;
import ar.edu.utn.frba.ddsi.donaciones.services.DonanteService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/donaciones")
public class DonacionController {

    private final DonacionService donacionService;
    private final DonanteService donanteService;
    private final DonacionMapper donacionMapper;

    public DonacionController(DonacionService donacionService, DonanteService donanteService,
                              DonacionMapper donacionMapper) {
        this.donacionService = donacionService;
        this.donanteService = donanteService;
        this.donacionMapper = donacionMapper;
    }

    @GetMapping
    public List<DonacionResponseDTO> listar() {
        return donacionService.listar().stream()
                .map(donacionMapper::toResponseDTO)
                .toList();
    }

    @GetMapping("/{id}")
    public DonacionResponseDTO buscarPorId(@PathVariable Long id) {
        return donacionMapper.toResponseDTO(donacionService.buscarPorId(id));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public List<DonacionResponseDTO> registrar(@Valid @RequestBody DonacionTotalRequestDTO dto) {
        Donante donante = donanteService.buscarPorId(dto.getDonanteId());
        return donacionService.registrar(donacionMapper.toEntity(dto, donante)).stream()
                .map(donacionMapper::toResponseDTO)
                .toList();
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void eliminar(@PathVariable Long id) {
        donacionService.eliminar(id);
    }

    @PutMapping("/{id}/estado")
    public DonacionResponseDTO cambiarEstado(@PathVariable Long id,
                                             @Valid @RequestBody CambioEstadoRequestDTO dto) {
        return donacionMapper.toResponseDTO(
                donacionService.cambiarEstado(id, dto.getAccion().name(), dto.getJustificacion()));
    }

    @GetMapping("/{id}/auditoria")
    public List<AuditoriaTransicionDTO> obtenerHistorial(@PathVariable Long id) {
        return donacionService.obtenerHistorial(id).stream()
                .map(donacionMapper::toAuditoriaDTO)
                .toList();
    }

    @GetMapping("/donante/{donanteId}/organizaciones-ayudadas")
    public long organizacionesAyudadas(@PathVariable Long donanteId) {
        return donacionService.contarOrganizacionesAyudadas(donanteId);
    }
}