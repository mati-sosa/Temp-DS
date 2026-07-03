package ar.edu.utn.frba.ddsi.logistica.controllers;

import ar.edu.utn.frba.ddsi.logistica.dto.*;
import ar.edu.utn.frba.ddsi.logistica.dto.mappers.EntregaMapper;
import ar.edu.utn.frba.ddsi.logistica.services.EntregaService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/entregas")
public class EntregaController {

    private final EntregaService entregaService;
    private final EntregaMapper entregaMapper;

    public EntregaController(EntregaService entregaService, EntregaMapper entregaMapper) {
        this.entregaService = entregaService;
        this.entregaMapper = entregaMapper;
    }

    @GetMapping
    public List<EntregaResponseDTO> listar() {
        return entregaService.listar().stream()
                .map(entregaMapper::toResponseDTO)
                .toList();
    }

    @GetMapping("/{id}")
    public EntregaResponseDTO buscarPorId(@PathVariable Long id) {
        return entregaMapper.toResponseDTO(entregaService.buscarPorId(id));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public EntregaResponseDTO crear(@Valid @RequestBody EntregaRequestDTO dto) {
        return entregaMapper.toResponseDTO(entregaService.crear(entregaMapper.toEntity(dto)));
    }

    @PutMapping("/{id}")
    public EntregaResponseDTO actualizar(@PathVariable Long id, @Valid @RequestBody EntregaRequestDTO dto) {
        return entregaMapper.toResponseDTO(entregaService.actualizar(id, dto.getFechaHoraEntrega()));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void eliminar(@PathVariable Long id) {
        entregaService.eliminar(id);
    }

    @PutMapping("/{id}/estado")
    public EntregaResponseDTO cambiarEstado(@PathVariable Long id,
                                            @Valid @RequestBody CambioEstadoEntregaRequestDTO dto) {
        return entregaMapper.toResponseDTO(entregaService.cambiarEstado(
                id, dto.getAccion().name(), dto.getJustificacion(), dto.getFotos()));
    }

    @GetMapping("/{id}/auditoria")
    public List<AuditoriaTransicionDTO> obtenerHistorial(@PathVariable Long id) {
        return entregaService.obtenerHistorial(id).stream()
                .map(entregaMapper::toAuditoriaDTO)
                .toList();
    }
}
